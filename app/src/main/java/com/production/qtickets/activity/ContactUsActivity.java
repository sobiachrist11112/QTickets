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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.production.qtickets.R;
import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.contactus.ContactUsPresenter;
import com.production.qtickets.contactus.ContactusContracter;
import com.production.qtickets.model.ContactResponse;
import com.production.qtickets.model.CountryCodeData;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.EdittextRegular;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactUsActivity extends AppCompatActivity implements ContactusContracter.View {

    EdittextRegular etName;
    EdittextRegular et_email;
    TextInputEditText edt_phone_number;
    EdittextRegular tvMessageField;
    RelativeLayout call;
    RelativeLayout send;
    TextviewBold contact_phoneNum;
    TextviewBold contact_email;
    SessionManager sessionManager;
    Spinner country_spinner;
    ArrayAdapter<String> adapter;
    private int position, purposePos = 0;
    private String country = "", purpose = "";
    Spinner spinner_nationality;
    private TextviewBold tvToolbarTitle;
    ContactUsPresenter presenter;
    ArrayList<String> mCountrynames = new ArrayList<>();
    ArrayList<String> mCountryCodes = new ArrayList<>();
    private ImageView iv_back_arrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        // crashing at toolbar
        presenter = new ContactUsPresenter();
        presenter.attachView(this);
        init();
        QTUtils.setStatusBarGradiant(this);
        // check the permission to open the phone to make call to cantact us
        checkPhonePermisions();
        tvToolbarTitle.setText(getResources().getString(R.string.nav_contactus));
        country_spinner = (Spinner) findViewById(R.id.spin_country_code);
        // "Bahrain"


        if (sessionManager.getCountryName().equals("Dubai")) {
//          contact_phoneNum.setText(R.string.phone_number_dubai);
            contact_phoneNum.setText(sessionManager.getDubaiContactPhone());
            contact_email.setText(sessionManager.getContactEmailForDubai());

        }

        else if (sessionManager.getCountryName().equals("Bahrain")) {
//          contact_phoneNum.setText(R.string.phone_number_dubai);
            contact_phoneNum.setText(sessionManager.getBehrainContactPhone());
            contact_email.setText(sessionManager.getContactEmailForBehrain());

        }

        else {
//          contact_phoneNum.setText(R.string.phone_number);
            contact_phoneNum.setText(sessionManager.getQatarContactPhone());
            contact_email.setText(sessionManager.getContactEmailForQatar());

        }


        // modifying the phone and email here in order to make it dynamic and it will load from api..


        getCountryCode();

        bindCountrySpinner();
        bindPurposeSpinner();
        onClicks();
//      Log.d("27Sep",sessionManager.getContactPhone());

    }

    private void onClicks() {
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set the default mobile number which can be used while pressing call button

                Intent intent = new Intent(Intent.ACTION_DIAL);
                if (sessionManager.getCountryName().equals("Dubai")) {
//                  intent.setData(Uri.parse("tel:+971508890218"));
                    intent.setData(Uri.parse("tel:" + sessionManager.getDubaiContactPhone()));

                } else {
//                    intent.setData(Uri.parse("tel:+97444661996"));
                    intent.setData(Uri.parse("tel:" + sessionManager.getQatarContactPhone()));

                }

                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });
    }

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

    private void bindCountrySpinner() {


        int positin = -1;

        Log.d("Prefix:", sessionManager.getPrefix());

        String countrycode[] = AppConstants.country_code;
        for (int i = 0; i < countrycode.length; i++) {
            if (sessionManager.getPrefix().equals(countrycode[i])) {
                positin = i;
            }
        }
        if (positin == -1) {
            positin = 0;
        }
//        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter, AppConstants.country_code, AppConstants.flags, true, positin);
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter,
                mCountrynames, mCountryCodes,
                AppConstants.flag, true, positin,false);

        country_spinner.setAdapter(adapter);

        country_spinner.setSelection(positin);
        sessionManager.setSelectedCountryCode("+"+sessionManager.getPrefix());

        // attaching data adapter to spinner
        //  country_spinner.setAdapter(adapter);
        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                position = i;
                // On selecting a spinner item
                country = mCountryCodes.get(position);

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

        QTUtils.showDialogbox(ContactUsActivity.this, getResources().getString(R.string.successwewill));
        etName.setText("");
        et_email.setText("");
        tvMessageField.setText("");
        edt_phone_number.setText("");
    }

    @Override
    public void init() {
        tvMessageField = findViewById(R.id.tv_message_field);
        contact_phoneNum = findViewById(R.id.contact_phoneno);
        contact_email = findViewById(R.id.contact_email);
        iv_back_arrow = findViewById(R.id.ivback);
        call = findViewById(R.id.call);
        send = findViewById(R.id.send);
        edt_phone_number = findViewById(R.id.edt_phone_number);
        etName = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        spinner_nationality = findViewById(R.id.spinner_nationality);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        sessionManager = new SessionManager(ContactUsActivity.this);
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
