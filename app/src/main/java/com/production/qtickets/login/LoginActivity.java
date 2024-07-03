package com.production.qtickets.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.production.qtickets.R;
import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.RegisterRequest;
import com.production.qtickets.model.UserINfo;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.production.qtickets.cmspages.CMSPagesActivity;
import com.production.qtickets.dashboard.DashBoardActivity;


import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.signup.SignUpActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContracter.View, AdapterView.OnItemSelectedListener {

    TextView tv_signup, tv_forgotpassword, terms, privacy, button_signin;
    TextView btn_login;
    EditText et_username, et_password;
    private CircleImageView mFacebookLoginButton;
    private CallbackManager mFacebookCallbackManager;
    String Name, FEmail, first_name, last_name, fid, gender, access_token, str_country_code;
    LoginPresenter presenter;
    SessionManager sessionManager;
    ImageView close;
    Dialog fbDialog;
    ImageView iv_closedialog;
    TextInputLayout txt_input_mobile, txt_input_email;
    EditText edt_mobile, edt_email;
    Spinner spin_country_code;
    String androidDeviceId = "";
    //list of nationalities for login with number
    String[] country_code = new String[]{"+974", "+973", "+971", "+968", "+91", "+93", "+355", "+213", "+376", "+54", "+61", "+822", "+375",
            "+975", "+591", "+55", "+359", "+86", "+57", "+269", "+506", "+53", "+31", "+20", "+33", "+220",
            "+995", "+49", "+233", "+1473", "+224", "+245", "+509", "+504", "+36", "+354", "+62", "+98", "+964", "+353",
            "+972", "+39", "+1876", "+81", "+962", "+7", "+254", "+965", "+371", "+231", "+218", "+423", "+370", "+352", "+60",
            "+223", "+356", "+52", "+377", "+976", "+212", "+264", "+674", "+977", "+64", "+505", "+92", "+685", "+378", "+966",
            "+381", "+232", "+65", "+421", "+386", "+677", "+252", "+27", "+34", "+249", "+597", "+46", "+41", "+963", "+66", "+676",
            "+216", "+90", "+256", "+44", "+1", "+998", "+58", "+84", "+260", "+263"};
    //country code list
    List<String> country_code_List = new ArrayList<String>(Arrays.asList(country_code));
    ArrayAdapter<String> adapter;
    public boolean isexception = false;
    LoginButton loginButton;
    ImageView iv_backfromlogin, iv_googlesignin;

    // Gmail Integration
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;

    private int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(LoginActivity.this);
        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(getApplication());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_new_qt5);
        presenter = new LoginPresenter();
        presenter.attachView(this);
        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        init();
        googleSignInOptions();
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = this.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = this.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", this.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    private void googleSignInOptions() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {

        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void initiateLoginWithFB() {
        loginButton.setPermissions("public_profile", "email", "user_birthday", "user_friends");
        loginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fetchUserProfile();
            }

            @Override
            public void onCancel() {
                String  sdf="";
                // Handle cancel event when the user cancels the login process
            }

            @Override
            public void onError(FacebookException error) {
                String  sdfdshjdshj="";
                // Handle error when something goes wrong during the login process
            }
        });

    }

    private void fetchUserProfile() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            String actualAccessToken = accessToken.getToken();
            // Use the actualAccessToken for making Facebook API calls
        }

     //   AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, (jsonObject, graphResponse) -> {
                if (jsonObject != null) {
                    try {
                        fid = jsonObject.getString("id");
                        Log.v("id = ", " " + fid);

                        Name = jsonObject.getString("name");
                        Log.v("Name = ", " " + Name);

//
//
                        first_name = jsonObject.getString("first_name");
                        Log.v("first_name = ", " " + first_name);
//
//                        last_name = jsonObject.getString("last_name");
//                        Log.v("last_name = ", " " + last_name);

                        try {

                            gender = jsonObject.getString("gender");
                            Log.v("gender = ", " " + gender);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            FEmail = jsonObject.getString("email");
                            Log.v("Email = ", " " + FEmail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v("access_token = ", " " + access_token);
                        if (TextUtils.isEmpty(gender)) {
                            gender = "";
                        }
                        JSONObject jsonObjectss = new JSONObject();
                        jsonObjectss.put("name", Name);
                        if(FEmail!=null){
                            jsonObjectss.put("email", FEmail);
                        }else {
                            jsonObjectss.put("email", first_name+"@gmail.com".replace(" ","_"));
                        }
                        jsonObjectss.put("imageUrl", "");
                        jsonObjectss.put("fbId", fid);
                        jsonObjectss.put("accessToken", "");
                        jsonObjectss.put("fbUserId", accessToken.getUserId());
                        jsonObjectss.put("tokenExpireTime", 0);
                        jsonObjectss.put("signedRequest", "");
                        jsonObjectss.put("graphDomain", "");
                        jsonObjectss.put("dataAccessExpiresTime", 0);
                        presenter.getLoginFacebook(jsonObjectss.toString());
                        //alertDialogbox(R.style.DialogAnimation);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Specify the fields you want to fetch
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,first_name");
            request.setParameters(parameters);

            // Execute the GraphRequest asynchronously
            request.executeAsync();
        } else {
            // Handle case where the access token is invalid or has expired
        }
    }


    //login with face will happen here
//    private void initiateLoginWithFB() {
//
//
//
//
//        loginButton.setReadPermissions(Arrays.asList(
//                "public_profile", "email", "user_birthday", "user_friends"));
//        loginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                if (accessToken != null) {
//                    String actualAccessToken = accessToken.getToken();
//                    // Use the actualAccessToken for making Facebook API calls
//                }
//
//
//              //  AccessToken accessToken = loginResult.getAccessToken();
//                Profile profile = Profile.getCurrentProfile();
//
//                AccessToken token = AccessToken.getCurrentAccessToken();
//                Log.d("access only Token is", String.valueOf(token.getToken()));
//
//                String facebook_id_token = String.valueOf(token.getToken());
//                Log.v("access", "token" + String.valueOf(token.getToken()));
//
//                access_token = String.valueOf(token.getToken());
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//                                Log.v("LoginActivity Response ", response.toString());
//
//                                try {
//                                    fid = object.getString("id");
//                                    Log.v("id = ", " " + fid);
//
//                                    Name = object.getString("name");
//                                    Log.v("Name = ", " " + Name);
//
//                                    first_name = object.getString("first_name");
//                                    Log.v("first_name = ", " " + first_name);
//
//                                    last_name = object.getString("last_name");
//                                    Log.v("last_name = ", " " + last_name);
//
//                                    try {
//
//                                        gender = object.getString("gender");
//                                        Log.v("gender = ", " " + gender);
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    try {
//                                        FEmail = object.getString("email");
//                                        Log.v("Email = ", " " + FEmail);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    Log.v("access_token = ", " " + access_token);
//                                    if (TextUtils.isEmpty(gender)) {
//                                        gender = "";
//                                    }
//                                    JSONObject jsonObject=new JSONObject();
//                                    jsonObject.put("name",first_name+last_name);
//                                    jsonObject.put("email",FEmail);
//                                    jsonObject.put("imageUrl","");
//                                    jsonObject.put("fbId",fid);
//                                    jsonObject.put("accessToken","");
//                                    jsonObject.put("fbUserId","");
//                                    jsonObject.put("tokenExpireTime",0);
//                                    jsonObject.put("signedRequest","");
//                                    jsonObject.put("graphDomain","");
//                                    jsonObject.put("dataAccessExpiresTime",0);
//                                    presenter.getLoginFacebook(jsonObject.toString());
//                                    //alertDialogbox(R.style.DialogAnimation);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,first_name,last_name,email,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(LoginActivity.this, "Login with Facebook as cancelled", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//    }

    private void alertDialogbox(int animationSource) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_facebook, null);
        // Build the dialog
        fbDialog = new Dialog(LoginActivity.this, R.style.MyDialogTheme);
        fbDialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = fbDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        //animation
        fbDialog.getWindow().getAttributes().windowAnimations = animationSource; //style id
        fbDialog.show();
        fbDialog.setCanceledOnTouchOutside(false);
        iv_closedialog = fbDialog.findViewById(R.id.iv_closedialog);
        iv_closedialog.setOnClickListener(this);
        mFacebookLoginButton.setOnClickListener(this);
        txt_input_email = fbDialog.findViewById(R.id.txt_input_email);
        txt_input_mobile = fbDialog.findViewById(R.id.txt_input_mobile);
        edt_mobile = fbDialog.findViewById(R.id.edt_mobile);
        edt_email = fbDialog.findViewById(R.id.edt_email);
        spin_country_code = fbDialog.findViewById(R.id.spin_country_code);
        spin_country_code.setOnItemSelectedListener(this);
        button_signin = fbDialog.findViewById(R.id.button_signin);
        button_signin.setOnClickListener(this);
        edt_email.setText(FEmail);
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_country_code_layout, country_code_List);
        spin_country_code.setAdapter(adapter);
    }


    public void onClickFacebookButton(View view) {
        if (view == mFacebookLoginButton) {
            loginButton.performClick();
        }
    }


    @Override
    public void onClick(View view) {
        Bundle b = new Bundle();
        int id = view.getId();
        Intent i;
        switch (id) {
            case R.id.tv_signup:
                i = new Intent(LoginActivity.this, SignUpActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.btn_login:
                final String username = et_username.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    et_username.requestFocus();
                    et_username.setError(getResources().getString(R.string.username_valid));

//                    showToast(getResources().getString(R.string.username_valid));
                } else if (!username.matches(AppConstants.REGEX_EMAIL_ADDRESS)) {
                    et_username.requestFocus();
                    et_username.setError(getResources().getString(R.string.username_valid1));

//                    showToast(getResources().getString(R.string.username_valid1));
                } else if (et_password.getText().toString().length() == 0) {
                    et_password.requestFocus();
                    et_password.setError(getResources().getString(R.string.pass_valid));
//                    showToast(getResources().getString(R.string.pass_valid));
                } else {
                    showPb();
                    presenter.getLogin(LoginActivity.this, username, password);
                }

                break;

            case R.id.tv_forgotpassword:
                Intent forgotPassIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotPassIntent);
                break;
            case R.id.close:
                finish();
                break;
            case R.id.terms:
                b.putString("cmsType", "TermsAndConditions");
                i = new Intent(LoginActivity.this, CMSPagesActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.privacy:
                b.putString("cmsType", "PrivacyPolicy");
                i = new Intent(LoginActivity.this, CMSPagesActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.iv_closedialog:
                fbDialog.dismiss();
                break;

            case R.id.button_signin:
                String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+.[a-zA-Z]+";
                if (!TextUtils.isEmpty(edt_email.getText().toString().trim())) {
                    if (edt_email.getText().toString().matches(emailPattern)) {
                        if (!TextUtils.isEmpty(edt_mobile.getText().toString())) {
                            String phoneNumber = edt_mobile.getText().toString().trim();
                            if (isValidPhoneNumber(phoneNumber)) {
                                boolean status = validateUsing_libphonenumber(str_country_code, phoneNumber);
                                if (isexception) {
                                    isexception = false;
                                } else {
                                    if (status) {
                                        showPb();
//                                        str_country_code = str_country_code.replace("+", "");
//                                        String country = sessionManager.getSelectedCountryCode();
//                                        RegisterRequest registerRequest = new RegisterRequest();
//                                        registerRequest.setName(username);
//                                        registerRequest.setEmail(email);
//                                        registerRequest.setMobile(phoneno);
//                                        registerRequest.setDialCode(country);
//                                        registerRequest.setNationality("");
//                                        registerRequest.setPassword(password);
//                                        registerRequest.setConfirmPassword(rep_password);
//                                        registerRequest.setUserId(0);
//                                        registerRequest.setRegistrationIpAddress("");
//                                        registerRequest.setIso2("");
//                                        registerRequest.setGender("");
//                                        registerRequest.setDob("");


                                        presenter.getRegister(first_name, last_name, edt_email.getText().toString(),
                                                edt_mobile.getText().toString(), "", "", fid, str_country_code,
                                                gender, androidDeviceId);
                                    } else {
                                        edt_mobile.setError(getString(R.string.inmobile));
                                        edt_mobile.requestFocus();
                                    }
                                }
                            }

                        } else {
                            edt_mobile.setError(getString(R.string.v_number));
                            edt_mobile.requestFocus();
                        }
                    } else {
                        edt_email.setError(getString(R.string.username_valid1));
                        edt_email.requestFocus();
                    }
                } else {
                    edt_email.setError(getString(R.string.username_valid));
                    edt_email.requestFocus();
                }
                break;
        }
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = null;
        String isoCode = null;
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumberUtil = PhoneNumberUtil.createInstance(LoginActivity.this);
            isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));

        } catch (Exception e) {
            isexception = true;
            e.printStackTrace();
        }
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            if (isValid) {
                String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                return true;
            } else {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.validphonenumber), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println(e);
        }


        return false;
    }

    @Override
    public void setRegister(RegisterModel registerModels) {
        try {

            if (registerModels.status.equalsIgnoreCase("True")) {
                sessionManager.setName(registerModels.data.name);
                sessionManager.setEmail(registerModels.data.email);
                sessionManager.setPrefix(registerModels.data.dialCode);
                Log.d("Prefix:" + "Setting Login", registerModels.data.dialCode);
                sessionManager.setNumber(registerModels.data.mobiless);
                sessionManager.setUserId(registerModels.data.userId.toString());
                fbDialog.dismiss();
                NavigationDrawerActivity.fLogin = true;
                sessionManager.setFb_gplus_user("true");
                if (getIntent().getBooleanExtra("from_signup", false)) {
                    Intent i1 = new Intent(this, DashBoardActivity.class);
                    startActivity(i1);
                    finish();
                } else {
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            } else {

                //   QTUtils.showDialogbox(this, registerList.get(0).errors.email.get(0));
                Toast.makeText(LoginActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }

    // login result will get here
    @Override
    public void setlogin(LoginResponse response) {
        try {
            if (response.status.equalsIgnoreCase("OK")) {
                sessionManager.setUserId(String.valueOf(response.data.userId));
                presenter.getUserDetails(sessionManager.getUserId());
            } else {
                QTUtils.showDialogbox(this, response.message);
                //Toast.makeText(LoginActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }

    @Override
    public void setForgotPassword(LoginResponse forgotPasswordData) {

    }


    @Override
    public void setUserDetails(UserINfo userDetails) {

        if (userDetails.status.equals("OK") && userDetails.isSuccess) {
            sessionManager.setUserId(String.valueOf(userDetails.data.userId));
            sessionManager.setName(String.valueOf(userDetails.data.name));
            sessionManager.setEmail(String.valueOf(userDetails.data.email));
            sessionManager.setNumber(String.valueOf(userDetails.data.mobile));
            sessionManager.setPrefix(String.valueOf(userDetails.data.dialCode));

            Log.d("21dec","Login"+ String.valueOf(userDetails.data.dialCode));

            sessionManager.setSelectedCountryCode(String.valueOf(userDetails.data.dialCode));
            sessionManager.setNatinality(String.valueOf(userDetails.data.nationality));
            sessionManager.setGender(String.valueOf(userDetails.data.gender));
            sessionManager.setDob(String.valueOf(userDetails.data.dob));
            if (getIntent().getBooleanExtra("from_signup", false)) {
                Intent i = new Intent(this, DashBoardActivity.class);
                startActivity(i);
                finish();
            } else {
                setResult(Activity.RESULT_OK);
                finish();
            }
        } else {
            QTUtils.showDialogbox(this, userDetails.message);
        }
    }

    @Override
    public void init() {
        sessionManager = new SessionManager(LoginActivity.this);
        loginButton = findViewById(R.id.login_button);
        tv_signup = findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(this);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
       /* terms = findViewById(R.id.terms);
        privacy = findViewById(R.id.privacy);
        terms.setOnClickListener(this);
        privacy.setOnClickListener(this);*/
        // close = findViewById(R.id.close);
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
        iv_googlesignin = findViewById(R.id.iv_googlesignin);
        tv_forgotpassword.setOnClickListener(this);
        mFacebookLoginButton = findViewById(R.id.button_facebookLogin);
        // close.setOnClickListener(this);
        tv_signup.setText(" " + getString(R.string.nav_signup));
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        mFacebookCallbackManager = CallbackManager.Factory.create();
        initiateLoginWithFB();
        iv_backfromlogin = findViewById(R.id.iv_backfromlogin);
        iv_backfromlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        iv_googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSignedIn()) {
                    mGoogleSignInClient.signOut();
                    signIn();
                } else {
                    signIn();
                }

            }
        });
    }


    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    @Override
    public void dismissPb() {
        if (QTUtils.progressDialog != null) {
            QTUtils.progressDialog.dismiss();
        }
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(LoginActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
        if (throwable instanceof SocketTimeoutException) {
            //  showToast("Socket Time out. Please try again.");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof UnknownHostException) {
            // showToast("No Internet");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof ConnectException) {
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, LoginActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String username = account.getDisplayName();
            String userEmail = account.getEmail();
            String familyname = account.getFamilyName();
            String givenname = account.getGivenName();
            String userId = account.getId();
            String profileimage = String.valueOf(account.getPhotoUrl());
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", username);
                jsonObject.put("email", userEmail);
                jsonObject.put("familyName", familyname);
                jsonObject.put("givenName", givenname);
                jsonObject.put("googleId", userId);
                jsonObject.put("imageUrl", profileimage);
            } catch (Exception e) {
                Log.d("exception :", e.toString());
            }
            presenter.getGmailLogin(jsonObject.toString());
        } else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        str_country_code = country_code_List.get(i);
        if (str_country_code.length() >= 4) {
            edt_mobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        } else if (str_country_code.length() >= 3) {
            edt_mobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
