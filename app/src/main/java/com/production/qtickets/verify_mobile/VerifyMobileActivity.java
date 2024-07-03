package com.production.qtickets.verify_mobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.VerifyMobileModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.production.qtickets.dashboard.DashBoardActivity;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * Created by Harris on 05-06-2018.
 */

public class VerifyMobileActivity extends AppCompatActivity implements View.OnClickListener, VerifyMobileContracter.View, TextWatcher {
    SessionManager sessionManager;
    TextView tv_toolbar_title;
    ImageView iv_back_arrow;
    StringBuilder sb;
    private boolean isReached = false;
    Spinner spin_country_code;
    LinearLayout lin_otp;
    EditText et_phoneno, et_otp01, et_otp02, et_otp03, et_otp04, et_otp;
    TextviewBold resend, verify, send_otp;
    private int position;
    String country, mobileno, str_otp, user_id;
    VerifyMobilePresenter presenter;
    List<VerifyMobileModel> verifyMobileModels;
    String[] country_code = new String[]{"+974", "+973", "+971", "+968", "+91", "+93", "+355", "+213", "+376", "+54", "+61", "+822", "+375",
            "+975", "+591", "+55", "+359", "+86", "+57", "+269", "+506", "+53", "+31", "+20", "+33", "+220",
            "+995", "+49", "+233", "+1473", "+224", "+245", "+509", "+504", "+36", "+354", "+62", "+98", "+964", "+353",
            "+972", "+39", "+1876", "+81", "+962", "+7", "+254", "+965", "+371", "+231", "+218", "+423", "+370", "+352", "+60",
            "+223", "+356", "+52", "+377", "+976", "+212", "+264", "+674", "+977", "+64", "+505", "+92", "+685", "+378", "+966",
            "+381", "+232", "+65", "+421", "+386", "+677", "+252", "+27", "+34", "+249", "+597", "+46", "+41", "+963", "+66", "+676",
            "+216", "+90", "+256", "+44", "+1", "+998", "+58", "+84", "+260", "+263"};

    //country code list
    List<String> country_code_List = new ArrayList<String>(Arrays.asList(country_code));

    private boolean isexecuted, isexception = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(VerifyMobileActivity.this);
        setContentView(R.layout.activity_verify_mobile);
        presenter = new VerifyMobilePresenter();
        presenter.attachView(this);
        init();

    }
    //initialize the views
    public void init() {
        sessionManager = new SessionManager(VerifyMobileActivity.this);
        user_id = sessionManager.getUserId();
        sb = new StringBuilder();
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("VERIFY MOBILE");
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(this);
        spin_country_code = (Spinner) findViewById(R.id.spin_country_code);
        et_phoneno = (EditText) findViewById(R.id.edt_phoneno);
        lin_otp = (LinearLayout) findViewById(R.id.otp_layout);

        et_otp = (EditText) findViewById(R.id.et_otp_main);
        et_otp01 = (EditText) findViewById(R.id.edt_otp01);
        et_otp01.addTextChangedListener(this);
        et_otp02 = (EditText) findViewById(R.id.edt_otp02);
        et_otp02.addTextChangedListener(this);
        et_otp03 = (EditText) findViewById(R.id.edt_otp03);
        et_otp03.addTextChangedListener(this);
        et_otp04 = (EditText) findViewById(R.id.edt_otp04);
        et_otp04.addTextChangedListener(this);

        resend = (TextviewBold) findViewById(R.id.tv_resend);
        resend.setOnClickListener(this);
        verify = (TextviewBold) findViewById(R.id.tv_verify);
        verify.setOnClickListener(this);
        send_otp = (TextviewBold) findViewById(R.id.tv_otp);
        send_otp.setOnClickListener(this);

        bindCountrySpinner();

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(VerifyMobileActivity.this, true);
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
        }else {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, VerifyMobileActivity.this, message);
    }
    //bind the country items and contry codes

    private void bindCountrySpinner() {

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, country_code_List);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_country_code.setAdapter(dataAdapter);

        spin_country_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                position = i;

                // On selecting a spinner item
                country = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + country, Toast.LENGTH_LONG).show();
                sessionManager.setPrefix(country);
                Log.d("Prefix:"+"Setting Verify",country);

                Log.v("countryCode = ", "id" + country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                QTUtils.showDialogbox(VerifyMobileActivity.this, "select country code");
            }
        });
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        Intent i;
        switch (id) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;

            case R.id.tv_resend:
                send_otp.setVisibility(View.VISIBLE);
                lin_otp.setVisibility(View.GONE);
                verify.setVisibility(View.GONE);

                break;

            case R.id.tv_otp:
                //>>generateOTP
                mobileno = et_phoneno.getText().toString().trim();

                if (TextUtils.isEmpty(mobileno)) {
                    et_phoneno.requestFocus();
                    et_phoneno.setError("Please enter username");

                } else {
                    if (!TextUtils.isEmpty(country)) {
                        // String phoneNumber = phone_number.getText().toString().trim();
                        if (country.length() > 0 && mobileno.length() > 0) {
                            if (isValidPhoneNumber(mobileno)) {
                                boolean status = validateUsing_libphonenumber(country, mobileno);
                                if (isexception) {
                                    isexception = false;
                                } else {
                                    if (status) {
                                        //before it cmd but this is the validate that number
                                        showPb();
                                        presenter.getOtp(VerifyMobileActivity.this, user_id, mobileno, country);

                                    } else {
                                        et_phoneno.setError("Invalid Phone Number");
                                        et_phoneno.requestFocus();
                                    }
                                }
                            }
                        }
                    }

                }
                break;

            case R.id.tv_verify:
                //>>mobileVerificartion
                //str_otp = "123456";
                str_otp = et_otp.getText().toString().trim();
                /*if (!str_otp.equals(type_otp)) {
                    et_otp.clearFocus();
                    Toast.makeText(this, "not matched", Toast.LENGTH_SHORT).show();

                } else*/
                if (et_otp.getText().toString().length() <= 0) {
                    et_otp.setError("enter the otp");
                    et_otp.requestFocus();

                } else {
                    showPb();
                    presenter.getVerify(VerifyMobileActivity.this, country, mobileno, str_otp);
                }

                break;

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    //>>mobileVerificartion
    @Override
    public void setVerified(List<VerifyMobileModel> response) {
        try {
            List<VerifyMobileModel> verifyMobileModels;
            verifyMobileModels = response;

            if (verifyMobileModels.get(0).status.equalsIgnoreCase("True")) {

                for (int i = 0; i < verifyMobileModels.size(); i++) {

                    String errormsg = verifyMobileModels.get(0).errormsg;
                    Intent inte = new Intent(VerifyMobileActivity.this, DashBoardActivity.class);
                    startActivity(inte);
                    Toast.makeText(this, "" + errormsg, Toast.LENGTH_SHORT).show();
                }

            } else {
                QTUtils.showDialogbox(this, verifyMobileModels.get(0).errormsg);
                Toast.makeText(VerifyMobileActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }

    }


    //>>generateOTP
    @Override
    public void setOtp(List<VerifyMobileModel> response) {
        try {
            List<VerifyMobileModel> verifyMobileModels;
            verifyMobileModels = response;

            if (verifyMobileModels.get(0).status.equalsIgnoreCase("True")) {

                for (int i = 0; i < verifyMobileModels.size(); i++) {

                    String errormsg = verifyMobileModels.get(0).errormsg;
                    send_otp.setVisibility(View.GONE);
                    lin_otp.setVisibility(View.VISIBLE);
                    verify.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "" + errormsg, Toast.LENGTH_SHORT).show();
                }

            } else {
                QTUtils.showDialogbox(this, verifyMobileModels.get(0).errormsg);
                Toast.makeText(VerifyMobileActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
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
            phoneNumberUtil = PhoneNumberUtil.createInstance(VerifyMobileActivity.this);
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


    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }
}
