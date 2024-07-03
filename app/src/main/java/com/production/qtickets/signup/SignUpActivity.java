package com.production.qtickets.signup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.production.qtickets.R;
import com.production.qtickets.model.CountryCodeData;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.RegisterRequest;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.utils.TextviewRegular;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.cmspages.CMSPagesActivity;
import com.production.qtickets.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, SignUpContracter.View {

    private LoginButton mFacebookLoginButton;
    private CallbackManager mFacebookCallbackManager;
    String user_name, email, phoneno, ses_password, rep_password, country, gender;
    String first_name, last_name, id, access_token, isAgree = "0";
    TextView im_male, im_female, txt_terms, txt_privacy;
    SessionManager sessionManager;
    EditText et_username, et_password, et_mail, et_phoneno, et_rep_password;
    Spinner country_spinner, gender_spinner;
    TextviewBold btn_signup, tv_login;
    TextviewRegular tv_male, tv_female;
    private int position;
    private boolean ismaleclicked = false, isfemaleclicked = false;
    SignUpPresenter presenter;
    private boolean isexecuted, isexception = false;
    //  TextInputLayout txt_input_password,txt_input_repet_pass;
    //country code list
    List<String> country_code_List = new ArrayList<String>(Arrays.asList(AppConstants.country_code));
    //adapter
    ArrayAdapter<String> adapter, nationaity_adapter;
    String androidDeviceId = "";
    ImageView img_select, iv_backfromregister;
    AlertDialog alertDialog = null;
    ArrayList<String> mCountrynames = new ArrayList<>();
    ArrayList<String> mCountryCodes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //QTUtils.setStatusBarGradiant(SignUpActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_new_qt5);
        presenter = new SignUpPresenter();
        presenter.attachView(this);
        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        init();
        getCountryCode();
        bindCountrySpinner();

    }//initialize the views

    private void getCountryCode() {
        String jsonFileString = QTUtils.getJsonFromAssets(getApplicationContext(), "countrycodes.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<CountryCodeData>>() {
        }.getType();
        List<CountryCodeData> users = gson.fromJson(jsonFileString, listUserType);
        for (int i = 0; i < users.size(); i++) {
            mCountrynames.add(users.get(i).name);
            mCountryCodes.add(users.get(i).dial_code);
        }
    }

    public void init() {

        sessionManager = new SessionManager(SignUpActivity.this);
        iv_backfromregister = (ImageView) findViewById(R.id.iv_backfromregister);
        et_username = (EditText) findViewById(R.id.edt_name);
        et_username.setLongClickable(false);
        et_username.setInputType(InputType.TYPE_CLASS_TEXT);
        et_mail = (EditText) findViewById(R.id.edt_mail);
        et_phoneno = (EditText) findViewById(R.id.edt_phone_number);
        et_password = (EditText) findViewById(R.id.edt_password);
        et_rep_password = (EditText) findViewById(R.id.edt_rep_password);
        country_spinner = (Spinner) findViewById(R.id.spin_country_code);
        //gender_spinner = (Spinner) findViewById(R.id.spinner_gender);
        tv_login = (TextviewBold) findViewById(R.id.goto_login);
        tv_login.setOnClickListener(this);
        btn_signup = (TextviewBold) findViewById(R.id.btn_login);
        btn_signup.setOnClickListener(this);
        tv_male = (TextviewRegular) findViewById(R.id.tv_male);
        tv_female = (TextviewRegular) findViewById(R.id.tv_female);
       /* im_male = (TextView) findViewById(R.id.img_male);
        im_male.setOnClickListener(this);*/
       /* im_female = (TextView) findViewById(R.id.img_female);
        im_female.setOnClickListener(this);*/
        txt_terms = findViewById(R.id.txt_terms);
        txt_terms.setOnClickListener(this);
        iv_backfromregister.setOnClickListener(this);
        txt_privacy = findViewById(R.id.txt_privacy);
        txt_privacy.setOnClickListener(this);
       /* txt_input_password = findViewById(R.id.txt_input_password);
        txt_input_repet_pass = findViewById(R.id.txt_input_repet_pass);*/
       /* mFacebookLoginButton = findViewById(R.id.button_facebookLogin);
        mFacebookCallbackManager = CallbackManager.Factory.create();*/
        img_select = findViewById(R.id.img_select);
        img_select.setOnClickListener(this);
        sessionManager.setSelectedCountryCode("");
        //initiateLoginWithFB();

        //For getting Key Hashes
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

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
                if (s.length() > 0) {
                    //txt_input_password.setError(null);
                }
            }
        });

        et_rep_password.addTextChangedListener(new TextWatcher() {
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
                if (s.length() > 0) {
                    //  txt_input_repet_pass.setError(null);
                }
            }
        });

    }//login with facebook function

    private void initiateLoginWithFB() {

        mFacebookLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        mFacebookLoginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();

                AccessToken token = AccessToken.getCurrentAccessToken();
                Log.d("access only Token is", String.valueOf(token.getToken()));
                String facebook_id_token = String.valueOf(token.getToken());
                Log.v("access", "token" + String.valueOf(token.getToken()));
                access_token = String.valueOf(token.getToken());

                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());


                                try {
                                    id = object.getString("id");
                                    Log.v("id = ", " " + id);

                                    user_name = object.getString("name");
                                    Log.v("Name = ", " " + user_name);

                                    first_name = object.getString("first_name");
                                    Log.v("first_name = ", " " + first_name);

                                    last_name = object.getString("last_name");
                                    Log.v("last_name = ", " " + last_name);

                                    gender = object.getString("gender");
                                    Log.v("gender = ", " " + gender);


                                    try {
                                        email = object.getString("email");
                                        Log.v("Email = ", " " + email);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    Log.v("access_token = ", " " + access_token);
                                    showPb();
//                                    presenter.getloginwithfacebook(SignUpActivity.this,
//                                            first_name, last_name, email, gender, id);
//                                    Toast.makeText(getApplicationContext(), "Name " + user_name, Toast.LENGTH_LONG).show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this, "Login with Facebook as cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(SignUpActivity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, SignUpActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();

    }

    public boolean getSpecialCharacterCount(String s) {
        if (s == null || s.trim().isEmpty()) {
            System.out.println("Incorrect format of string");
            return false;
        }
        Pattern p = Pattern.compile("[^A-Za-z ]");
        Matcher m = p.matcher(s);
        // boolean b = m.matches();
        boolean b = m.find();
        if (b)
            System.out.println("There is a special character in my string ");
        else
            System.out.println("There is no special char.");
        return b;
    }

    //bind the spinner for country flags
    private void bindCountrySpinner() {

        // Creating adapter for spinner
//     adapter = new FlagAdapter(this,R.layout.item_flag_spinner_adapter, AppConstants.country_code,AppConstants.flags,true,0);
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter,
                mCountrynames, mCountryCodes,
                AppConstants.flag, true, 0, false);


        country_spinner.setAdapter(adapter);
     /*   sessionManager.setSelectedCountryCode("+"+sessionManager.getPrefix());

        // attaching data adapter to spinner
        country_spinner.setAdapter(adapter);*/

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                position = i;
                // On selecting a spinner item
                country = mCountryCodes.get(position);

                // Showing selected spinner item
                if (country.length() >= 4) {
                    et_phoneno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

                } else if (country.length() >= 3) {
                    et_phoneno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

                }
              /*  //Toast.makeText(parent.getContext(), "Selected: " + country, Toast.LENGTH_LONG).show();
                sessionManager.setPrefix(country);
                if (sessionManager.getSelectedCountryCode().equals(country)) {
                    sessionManager.setSelectedCountryCode(country);
                } else {
                    et_phoneno.setText("");
                    sessionManager.setSelectedCountryCode(country);
                }
*/
                Log.v("countryCode = ", "id" + country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                QTUtils.showDialogbox(SignUpActivity.this, "select country code");
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent i;
        Bundle b = new Bundle();
        switch (id) {
            case R.id.iv_backfromregister:
                finish();
                break;


            case R.id.goto_login:
                Intent in = new Intent(SignUpActivity.this, LoginActivity.class);
                in.putExtra("from_signup", true);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);

                break;

            case R.id.img_male:

                if (!ismaleclicked) {
                    ismaleclicked = false;
                    im_male.setBackground(getResources().getDrawable(R.drawable.ic_male));
                    im_female.setBackground(getResources().getDrawable(R.drawable.ic_female_transparent));
                    gender = getString(R.string.male);
                    ismaleclicked = true;
                    Log.v("Gender is:", "" + gender);

                } else {
                    gender = getString(R.string.female);
                    ismaleclicked = false;
                    im_male.setBackground(getResources().getDrawable(R.drawable.ic_male_transparent));
                    im_female.setBackground(getResources().getDrawable(R.drawable.ic_female));

                }

                break;

            case R.id.img_female:

                if (!isfemaleclicked) {
                    isfemaleclicked = false;
                    im_male.setBackground(getResources().getDrawable(R.drawable.ic_male_transparent));
                    im_female.setBackground(getResources().getDrawable(R.drawable.ic_female));
                    gender = getString(R.string.female);
                    Log.v("Gender is:", "" + gender);
                } else {
                    gender = getString(R.string.male);
                    im_male.setBackground(getResources().getDrawable(R.drawable.ic_male));
                    im_female.setBackground(getResources().getDrawable(R.drawable.ic_female_transparent));
                    isfemaleclicked = true;
                }

                break;


            case R.id.btn_login:

                final String username = et_username.getText().toString().trim();
                final String email = et_mail.getText().toString().trim();
                final String phoneno = et_phoneno.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                final String rep_password = et_rep_password.getText().toString().trim();
                //ses_password = et_rep_password.getText().toString();
                String emailPattern = "^(([A-Za-z]|[A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]+.*)+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$";

                if (TextUtils.isEmpty(username)) {
                    // et_username.requestFocus();
//                    showToast("Please enter username");

                    et_username.requestFocus();
                    et_username.setError("Please enter name");

                } else if (getSpecialCharacterCount(username)) {
                    // et_username.requestFocus();
//                    showToast("Please enter username");

                    et_username.requestFocus();
                    et_username.setError("Name should only contains alphabets");

                } else if (TextUtils.isEmpty(email)) {
                    // et_mail.requestFocus();
//                    showToast("Please enter email");
                    et_mail.requestFocus();
                    et_mail.setError("Please enter email");
                } else if (!email.matches(emailPattern)) {
                    // et_mail.requestFocus();
                    et_mail.requestFocus();
                    et_mail.setError(getString(R.string.username_valid1));
//                    showToast(getString(R.string.username_valid1));
                } else if (TextUtils.isEmpty(phoneno)) {
                    //  et_phoneno.requestFocus();
//                    showToast("Please enter phoneno");
                    et_phoneno.requestFocus();
                    et_phoneno.setError("Please enter phoneno");
                } else if (TextUtils.isEmpty(password)) {
                    //  et_password.requestFocus();
//                    showToast("Please enter password");
                    et_password.requestFocus();
                    et_password.setError("Please enter password");
                } else if (TextUtils.isEmpty(rep_password)) {
                    // et_rep_password.requestFocus();
//                    showToast("Please enter confirm password");
                    et_rep_password.requestFocus();
                    et_rep_password.setError("Please enter confirm password");
                } else if (!rep_password.equals(password)) {
                    //txt_input_repet_pass.requestFocus();
                    showToast("Please enter same password");

                } else if (isAgree.equals("0")) {
                    showToast("Please agree terms and condition and privacy policy");
                }
               /* else if (gender == null) {
                    Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();

                } */
                else if (!TextUtils.isEmpty(country)) {
                    // String phoneNumber = phone_number.getText().toString().trim();
                    if (country.length() > 0 && phoneno.length() > 0) {
                        if (isValidPhoneNumber(phoneno)) {
                            boolean status = validateUsing_libphonenumber(country, phoneno);
                            if (isexception) {
                                isexception = false;
                            } else {
                                if (status) {

                                    Log.d("1Nov", "phoneno " + phoneno);
                                    Log.d("1Nov", "country code " + country);

                                    RegisterRequest registerRequest = new RegisterRequest();
                                    registerRequest.setName(username);
                                    registerRequest.setEmail(email);
                                    registerRequest.setMobile(phoneno);
                                    registerRequest.setDialCode(country);
                                    registerRequest.setNationality("");
                                    registerRequest.setPassword(password);
                                    registerRequest.setConfirmPassword(rep_password);
                                    registerRequest.setUserId(0);
                                    registerRequest.setRegistrationIpAddress("");
                                    registerRequest.setIso2("");
                                    registerRequest.setGender("");
                                    registerRequest.setDob("");
                                    country = country.replace("+", "");
                                    //before it cmd but this is the validate that number
                                    showPb();
                                    String country = sessionManager.getSelectedCountryCode();


                                    presenter.getRegister(SignUpActivity.this, registerRequest);
                                } else {
                                    showToast("Invalid Phone Number");
                                    // et_phoneno.requestFocus();
                                }
                            }
                        }
                    }
                }


                break;
            case R.id.txt_terms:
                b.putString("cmsType", "TermsAndConditions");
                i = new Intent(SignUpActivity.this, CMSPagesActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.txt_privacy:
                b.putString("cmsType", "PrivacyPolicy");
                i = new Intent(SignUpActivity.this, CMSPagesActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.img_select:
                if (isAgree.equals("0")) {
                    isAgree = "1";
                    img_select.setImageResource(R.drawable.ic_selected);
                } else {
                    isAgree = "0";
                    img_select.setImageResource(R.drawable.ic_select);
                }

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

/*
    private InputFilter filterString = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && AppConstants.blockCharacterSet.contains(("" + source))) {

                validString = true;
                return "";
            } else {
                validString = false;
                return "";
            }
            // subham update
//            return null;
        }
    };
*/

    @Override
    public void setRegister(RegisterModel response) {

        RegisterModel registerList;
        try {
            registerList = response;

            if (registerList.status.equalsIgnoreCase("OK")) {
                if (registerList.message.equals("User Id already Exits..")) {
                    QTUtils.showDialogbox(this, registerList.message);
                } else {
                    sessionManager.setName(registerList.data.name);
                    sessionManager.setEmail(registerList.data.email);
                    sessionManager.setPrefix(registerList.data.dialCode);
                    sessionManager.setNumber(registerList.data.mobiless);
                    sessionManager.setPassword(ses_password);
                    Log.d("Prefix:" + "Setting SIgn ", registerList.data.dialCode);

                    showDialogbox(this, registerList.message);


                }


            } else {
                QTUtils.showDialogbox(this, registerList.message);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }

    }

    public void showDialogbox(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent dashboardIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(dashboardIntent);
                            finish();
                            alertDialog.dismiss();
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
            alertDialog.show();

        }
    }

    //Validate the mobile number

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
            phoneNumberUtil = PhoneNumberUtil.createInstance(SignUpActivity.this);
            isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));

        } catch (Exception e) {
            isexception = true;
            e.printStackTrace();
        }
        try {

            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            if (isValid) {
                String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                return true;
            } else {

                //AlertDialogue


                //Toasty.info(this, "Please enter valid Mobile number", Toast.LENGTH_LONG, true).show();
                return false;
            }

        } catch (NumberParseException e) {
            System.err.println(e);
        }

        return false;
    }


}
