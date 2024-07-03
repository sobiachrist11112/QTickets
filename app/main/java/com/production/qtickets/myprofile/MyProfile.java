package com.production.qtickets.myprofile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.production.qtickets.R;
import com.production.qtickets.eventBookingDetailQT5.EventBookingDetailQT5Activity;
import com.production.qtickets.eventBookingDetailQT5.EventDateAdapter;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.MyProfileModel;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.RegisterRequest;
import com.production.qtickets.signup.SignUpActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.changepassword.ChangePasswordActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * Created by Harsh on 5/14/2018.
 */
public class MyProfile extends AppCompatActivity implements View.OnClickListener,
        MyProfileContracter.View, AdapterView.OnItemSelectedListener {

    ImageView iv_back_arrow;
    TextView tv_toolbar_title, tv_name, tv_email, tv_save, change_pass;
    //  CircleImageView img_prof_pic;
    // ConstraintLayout c1;
    EditText edt_name, edt_email, edt_phone_number;
    Spinner spinner_nationality, spinner_country_code;
    public static boolean isdenay = false, isexception = false;
    //int
    private int GALLERY = 1, CAMERA = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private String picturePath;
    File imgFile;
    SessionManager sessionManager;
    String str_nationality, str_country_code;

    //country code list
    List<String> country_code_List = new ArrayList<String>(Arrays.asList(AppConstants.country_code));
    //nationality list
    ArrayList<String> natoinality_List = new ArrayList<String>();
    //presenter
    MyprofilePresenter presenter;
    //adapter
    ArrayAdapter<String> nationaity_adapter;
    FlagAdapter adapter;

    int position;
    TextView txt_dob;
    TextInputLayout txt_input_dob;

    RadioButton rb_male, rb_female;
    String gender = "", genderstatus = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(MyProfile.this);
        setContentView(R.layout.activity_my_profiletwo);
        presenter = new MyprofilePresenter();
        presenter.attachView(this);
        init();
        presenter.getQT5countryDropdown();
    }

    //inititalize the views
    @Override
    public void init() {
        sessionManager = new SessionManager(MyProfile.this);
        //for camera without this 2 lines camera wont work
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(this);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText(getResources().getString(R.string.nav_myaccount));
        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(sessionManager.getName());
        tv_email = findViewById(R.id.tv_email);
        tv_email.setText(sessionManager.getEmail());
        tv_save = findViewById(R.id.tv_save);

        txt_dob = findViewById(R.id.txt_dob);
        txt_input_dob = findViewById(R.id.txt_input_dob);


        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        change_pass = findViewById(R.id.change_pass);
        tv_save.setOnClickListener(this);
        change_pass.setOnClickListener(this);
        if (sessionManager.getFb_gplus_user().equalsIgnoreCase("true")) {
            change_pass.setVisibility(View.GONE);
        } else {
            change_pass.setVisibility(View.VISIBLE);
        }
        //   img_prof_pic = findViewById(R.id.img_prof_pic);
        //  c1 = findViewById(R.id.c1);
        //   c1.setOnClickListener(this);
        edt_name = findViewById(R.id.edt_name);
        edt_name.setText(sessionManager.getName());
        edt_email = findViewById(R.id.edt_email);
        edt_email.setText(sessionManager.getEmail());
        txt_dob.setText(sessionManager.getDeviceToken());
        edt_phone_number = findViewById(R.id.edt_phone_number);
        edt_phone_number.setText(sessionManager.getNumber());
        txt_dob.setText(sessionManager.getdob());


        if (sessionManager.getGender().equals("1")) {
            rb_male.setChecked(true);
            rb_female.setChecked(false);
        }
        if (sessionManager.getGender().equals("2")) {
            rb_male.setChecked(false);
            rb_female.setChecked(true);
        }
        //validation for phone number based on the country selected in the home page
        if (sessionManager.getCountryName().equals("Qatar")) {
            edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } else if (sessionManager.getCountryName().equals("India")) {
            edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        }
        spinner_nationality = findViewById(R.id.spinner_nationality);
        spinner_nationality.setOnItemSelectedListener(this);
        spinner_country_code = findViewById(R.id.spin_country_code);
        spinner_country_code.setOnItemSelectedListener(this);
        //Create a ArrayAdapter using arraylist and a default spinner layout
//        if (sessionManager.getGender().equalsIgnoreCase("Male")) {
//            img_prof_pic.setImageResource(R.drawable.ic_male_user);
//        } else {
//            img_prof_pic.setImageResource(R.drawable.ic_female_profile);
//        }


        txt_input_dob.setEnabled(true);
        txt_input_dob.setFocusable(false);
        txt_input_dob.setFocusableInTouchMode(false);
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "dd/MM/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
                txt_dob.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        txt_input_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MyProfile.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        rb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "Male";
                    genderstatus = "1";
                }
            }
        });
        rb_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    gender = "Female";
                    genderstatus = "2";
                }
            }
        });

    }

    @Override
    public void dismissPb() {
      //  QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
      //  QTUtils.showProgressDialog(MyProfile.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, MyProfile.this, message);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
//            case R.id.c1:
//                showPictureDialog();
//                break;
            case R.id.change_pass:
                Intent i = new Intent(this, ChangePasswordActivity.class);
                startActivity(i);
                break;

            case R.id.tv_save:

                //validating the user details while updating the profile info
                if (TextUtils.isEmpty(edt_name.getText().toString().trim())) {
                    edt_name.setError(getResources().getString(R.string.v_name));

                } else if (TextUtils.isEmpty(edt_email.getText().toString().trim())) {
                    edt_email.setError(getString(R.string.v_email));

                } else if (!edt_email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+.[a-zA-Z]+")) {
                    edt_email.setError(getString(R.string.invalidemail));

                }
               /* else if (str_country_code.equals("Select Country Code")) {
                    showToast(getString(R.string.v_country));

                }*/
                else if (str_nationality.equals("Select Natoinality")) {
                    showToast(getString(R.string.v_national));

                /*} else if (str_country_code.length() == 4) {
                    Toast.makeText(this, "+ level 4", Toast.LENGTH_SHORT).show();*/

                } else if (edt_phone_number.getText().toString().length() == 0) {
                    edt_phone_number.requestFocus();
                    edt_phone_number.setError("Enter the new password");

                } else {
                    String phoneNumber = edt_phone_number.getText().toString().trim();
                    if (isValidPhoneNumber(phoneNumber)) {
                        boolean status = validateUsing_libphonenumber(str_country_code, phoneNumber);
                        if (isexception) {
                            isexception = false;
                        } else {
                            if (status) {
                            //    showPb();
                                RegisterRequest registerRequest = new RegisterRequest();
                                registerRequest.setName(edt_name.getText().toString());
                                registerRequest.setEmail(edt_email.getText().toString());
                                registerRequest.setMobile(edt_phone_number.getText().toString());
                                registerRequest.setDialCode(str_country_code);
                                registerRequest.setNationality(str_nationality);
                                registerRequest.setPassword("");
                                registerRequest.setConfirmPassword("");
                                registerRequest.setUserId(Integer.valueOf(sessionManager.getUserId()));
                                registerRequest.setRegistrationIpAddress("");
                                registerRequest.setIso2("");
                                registerRequest.setGender(genderstatus);
                                registerRequest.setDob(txt_dob.getText().toString());
                                //before it cmd but this is the validate that number
                             //   showPb();
                                String country = sessionManager.getSelectedCountryCode();
                                QTUtils.showProgressDialog(MyProfile.this, true);
                                presenter.updateprofile(MyProfile.this, registerRequest);


//                                str_country_code = str_country_code.replace("+", "");
//                                presenter.getData(MyProfile.this, sessionManager.getUserId(), edt_name.getText().toString(),
//                                        edt_email.getText().toString(), edt_phone_number.getText().toString(), str_country_code, str_nationality);


                            } else {
                                edt_phone_number.setError(getString(R.string.inmobile));
                            }
                        }
                    }

                }

                break;
        }
    }


    //checking the format of the phone number
    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    //checking the valid phone number for qatar and other countrys
    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = null;
        String isoCode = null;
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumberUtil = PhoneNumberUtil.createInstance(MyProfile.this);
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
                Toast.makeText(MyProfile.this, getResources().getString(R.string.validphonenumber), Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println(e);
        }


        return false;
    }

    private void showPictureDialog() {
        android.app.AlertDialog.Builder pictureDialog = new android.app.AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(R.string.action));
        String[] pictureDialogItems = {
                getResources().getString(R.string.photo),
                getResources().getString(R.string.capture)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    //for uplaoding the photo from the gallary
    public void choosePhotoFromGallary() {
        Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
        gallery_Intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(gallery_Intent, GALLERY);
    }

    //for uploading the phot from camera
    private void takePhotoFromCamera() {
        if (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MyProfile.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(MyProfile.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (isdenay) {
                        Intent gallery_Intent = new Intent(getApplicationContext(), CameraUtil.class);
                        gallery_Intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(gallery_Intent, CAMERA);
                    }
                } else {
                    ActivityCompat.requestPermissions(MyProfile.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
            } else {
                Intent gallery_Intent = new Intent(getApplicationContext(), CameraUtil.class);
                gallery_Intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(gallery_Intent, CAMERA);
            }
        } else {
            Intent gallery_Intent = new Intent(getApplicationContext(), CameraUtil.class);
            gallery_Intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(gallery_Intent, CAMERA);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                picturePath = data.getStringExtra("picturePath");
                imgFile = new File(picturePath);
                if (imgFile.exists()) {
                    Bitmap bm = BitmapFactory.decodeFile(picturePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //     img_prof_pic.setImageBitmap(myBitmap);
                }
            }
        } else if (requestCode == CAMERA) {
            picturePath = data.getStringExtra("picturePath");
            imgFile = new File(picturePath);
            if (imgFile.exists()) {
                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                // img_prof_pic.setImageBitmap(myBitmap);
            }

        }
    }

    //get the user details from the server based on the user id
    @Override
    public void setData(List<MyProfileModel> response) {
        if (response.get(0).status.equalsIgnoreCase("true")) {
            sessionManager.setName(response.get(0).name);
            sessionManager.setEmail(response.get(0).email);
            sessionManager.setNumber(response.get(0).mobileno);
            sessionManager.setPrefix(response.get(0).prefix);
            sessionManager.setSelectedCountryCode(response.get(0).prefix);
            sessionManager.setNatinality(response.get(0).nationality);
            sessionManager.setUserId(response.get(0).userid);
            QTUtils.finishshowDialogbox(MyProfile.this, getString(R.string.p_success));

        } else if (response.get(0).status.equalsIgnoreCase("false")) {
            Toast.makeText(MyProfile.this, getString(R.string.noresponse), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void setCountryDropdownQt5(GetNationalityData response) {
        for (int i = 0; i < response.data.size(); i++) {
            natoinality_List.add(response.data.get(i).name);
        }
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter, AppConstants.country_code, AppConstants.flags, false,0);
        spinner_country_code.setAdapter(adapter);
        sessionManager.setSelectedCountryCode(sessionManager.getPrefix());
        spinner_country_code.setSelection(adapter.getPosition(sessionManager.getPrefix()));

        //Create a ArrayAdapter using arraylist and a default spinner layout
        nationaity_adapter = new ArrayAdapter<String>(this, R.layout.spinner_nationality, natoinality_List);
        spinner_nationality.setAdapter(nationaity_adapter);
        spinner_nationality.setSelection(nationaity_adapter.getPosition(sessionManager.getNatinality()));
    }

    @Override
    public void setUpdateprofile(RegisterModel response) {
        if(response.status.equals("OK") && response.isSuccess){
            sessionManager.setName(response.data.name);
            sessionManager.setEmail(response.data.email);
            sessionManager.setNumber(response.data.mobiless);
            sessionManager.setPrefix(response.data.dialCode);
            sessionManager.setSelectedCountryCode(response.data.dialCode);
            sessionManager.setNatinality(response.data.nationality);
            sessionManager.setUserId(String.valueOf(response.data.userId));
            sessionManager.setDob(String.valueOf(response.data.dob));
            sessionManager.setGender(String.valueOf(response.data.gender));
            tv_name.setText(sessionManager.getName());
            tv_email.setText(sessionManager.getEmail());
            edt_name.setText(sessionManager.getName());
            edt_email.setText(sessionManager.getEmail());
            edt_phone_number.setText(sessionManager.getNumber());
            txt_dob.setText(sessionManager.getdob());
            spinner_nationality.setSelection(nationaity_adapter.getPosition(sessionManager.getNatinality()));
            if (!sessionManager.getdob().equals("")) {
                if (sessionManager.getdob().equals("1")) {
                    rb_male.setChecked(true);
                    rb_female.setChecked(false);
                }
                if (sessionManager.getdob().equals("2")) {
                    rb_male.setChecked(false);
                    rb_female.setChecked(true);
                }
            }
            QTUtils.dismissProgressDialog();
            QTUtils.showDialogbox(this,"Profile Updated SucessFully");
        }



    }

    //this for selecting the country code for the phone number
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.spinner_nationality) {
            str_nationality = natoinality_List.get(i);
        } else if (spinner.getId() == R.id.spin_country_code) {
            str_country_code = country_code_List.get(i);
            if (sessionManager.getSelectedCountryCode().equals(str_country_code)) {
                sessionManager.setSelectedCountryCode(str_country_code);
            } else {
                edt_phone_number.setText("");
                sessionManager.setSelectedCountryCode(str_country_code);
            }
        }

        if (str_country_code.length() >= 4) {
            edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        } else if (str_country_code.length() >= 3) {
            edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
