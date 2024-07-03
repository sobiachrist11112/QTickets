package com.production.qtickets.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.production.qtickets.R;
import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.changepassword.ChangePasswordActivity;
import com.production.qtickets.changepassword.ChangePwdContracter;
import com.production.qtickets.changepassword.ChangePwdPresenter;
import com.production.qtickets.contactus.ContactUsPresenter;
import com.production.qtickets.contactus.ContactusContracter;
import com.production.qtickets.model.ContactResponse;
import com.production.qtickets.signup.SignUpActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.EdittextRegular;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bhagya Naik on 24/05/2018.
 */

public class ContactUsActivity extends AppCompatActivity implements ContactusContracter.View {

  /*  @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;*/
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.et_name)
    EdittextRegular etName;
    @BindView(R.id.et_email)
    EdittextRegular et_email;
    @BindView(R.id.edt_phone_number)
    TextInputEditText edt_phone_number;
    @BindView(R.id.tv_message_field)
    EdittextRegular tvMessageField;
    @BindView(R.id.call)
    RelativeLayout call;
    @BindView(R.id.send)
    RelativeLayout send;
    @BindView(R.id.contact_phoneno)
    TextviewBold contact_phoneNum;
    @BindView(R.id.contact_email)
    TextviewBold contact_email;
    //sessionmanager
    SessionManager sessionManager;
    Spinner country_spinner;
    ArrayAdapter<String> adapter;
    private int position, purposePos = 0;
    private String country = "", purpose = "";
    @BindView(R.id.spinner_nationality)
    Spinner spinner_nationality;
    ContactUsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        presenter = new ContactUsPresenter();
        presenter.attachView(this);
        sessionManager = new SessionManager(ContactUsActivity.this);
        QTUtils.setStatusBarGradiant(this);
        // check the permission to open the phone to make call to cantact us
        checkPhonePermisions();
        tvToolbarTitle.setText(getResources().getString(R.string.nav_contactus));
        country_spinner = (Spinner) findViewById(R.id.spin_country_code);
        bindCountrySpinner();
        bindPurposeSpinner();

        if (sessionManager.getCountryName().equals("Dubai")) {
//          contact_phoneNum.setText(R.string.phone_number_dubai);
            contact_email.setText(sessionManager.getDubaiContactPhone());

        } else {
//          contact_phoneNum.setText(R.string.phone_number);
            contact_email.setText(sessionManager.getQatarContactPhone());

        }

        // modifying the phone and email here in order to make it dynamic and it will load from api..

        contact_email.setText(sessionManager.getContactEmail());
//      Log.d("27Sep",sessionManager.getContactPhone());

    }

    private void bindCountrySpinner() {

        // Creating adapter for spinner
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter,
                AppConstants.country_code, AppConstants.flags, true, 0);
        country_spinner.setAdapter(adapter);
//      sessionManager.setSelectedCountryCode("+"+sessionManager.getPrefix());

        // attaching data adapter to spinner
        country_spinner.setAdapter(adapter);
        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                position = i;
                // On selecting a spinner item
                country =mCountryCodes.get(position);

                // Showing selected spinner item
                if (country.length() >= 4) {
                    edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

                } else if (country.length() >= 3) {
                    edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

                }
                //Toast.makeText(parent.getContext(), "Selected: " + country, Toast.LENGTH_LONG).show();
              /*  sessionManager.setPrefix(country);
                if (sessionManager.getSelectedCountryCode().equals(country)) {
                    sessionManager.setSelectedCountryCode(country);
                } else {
                    edt_phone_number.setText("");
                    sessionManager.setSelectedCountryCode(country);
                }*/

                Log.v("countryCode = ", "id" + country);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                QTUtils.showDialogbox(ContactUsActivity.this, "select country code");
            }
        });
    }


    private void bindPurposeSpinner() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.nationality_textview, getResources().getStringArray(R.array.purpose_array));
        dataAdapter.setDropDownViewResource(R.layout.nationality_textview);
        spinner_nationality.setAdapter(dataAdapter);

        spinner_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                purposePos = i;
                // On selecting a spinner item
                purpose = parent.getItemAtPosition(purposePos).toString();

                Log.d("purpose", purpose);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
    }

    @OnClick({R.id.iv_back_arrow, R.id.call, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                // Call the contact us function
                finish();
                break;
            case R.id.call:
                //set the default mobile number which can be used while pressing call button
                Intent intent = new Intent(Intent.ACTION_DIAL);
                if (sessionManager.getCountryName().equals("Dubai")) {
//                  intent.setData(Uri.parse("tel:+971508890218"));
                    intent.setData(Uri.parse("tel:"+sessionManager.getDubaiContactPhone()));

                } else {
//                    intent.setData(Uri.parse("tel:+97444661996"));
                    intent.setData(Uri.parse("tel:"+sessionManager.getQatarContactPhone()));

                }

                startActivity(intent);
                break;
            case R.id.send:
                String emailPattern = "^(([A-Za-z]|[A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]+.*)+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+$";

                // here the user will click the send button, it will chcek for details for name, subject and message fields in email
                if (TextUtils.isEmpty(etName.getText().toString().trim())) {
                    // et_username.requestFocus();
                    etName.requestFocus();
                    etName.setError(getResources().getString(R.string.valid_name));

                } else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
                    // et_mail.requestFocus();
                    et_email.requestFocus();
                    et_email.setError(getResources().getString(R.string.username_valid));


                } else if (!et_email.getText().toString().trim().matches(emailPattern)) {
                    // et_mail.requestFocus();
                    et_email.requestFocus();
                    et_email.setError(getResources().getString(R.string.username_valid1));
                } else if (TextUtils.isEmpty(edt_phone_number.getText().toString().trim())) {
                    // et_mail.requestFocus();
                    edt_phone_number.requestFocus();
                    edt_phone_number.setError(getResources().getString(R.string.enterphone_number));

                } else if (TextUtils.isEmpty(tvMessageField.getText().toString().trim())) {
                    // et_mail.requestFocus();
                    tvMessageField.requestFocus();
                    tvMessageField.setError(getResources().getString(R.string.valid_message));

                } else {

                    showPb();
                    presenter.sendContactDetails(etName.getText().toString().trim(),
                            edt_phone_number.getText().toString().trim(),
                            et_email.getText().toString().trim(),
                            purpose,
                            tvMessageField.getText().toString().trim()
                    );

                }


                break;
        }
    }

    private void checkPhonePermisions() {
        ActivityCompat.requestPermissions(ContactUsActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ContactUsActivity.this, "Permission denied to make call", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }


    @Override
    public void onSuccessfullySendDetails(ContactResponse contactResponse) {

        dismissPb();
        Log.d("Contacttt", "Success");

        QTUtils.showDialogbox(ContactUsActivity.this,getResources().getString(R.string.successwewill));
        etName.setText("");
        et_email.setText("");
        tvMessageField.setText("");
        edt_phone_number.setText("");
    }

    @Override
    public void init() {

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();

    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(ContactUsActivity.this, true);

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {

    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {

    }
}
