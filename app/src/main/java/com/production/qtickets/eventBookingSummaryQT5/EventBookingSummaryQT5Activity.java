package com.production.qtickets.eventBookingSummaryQT5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.production.qtickets.R;

import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.cmspages.CMSPagesActivity;
import com.production.qtickets.eventBookingDetailQT5.EventBookingDetailQT5Activity;
import com.production.qtickets.eventBookingDetailQT5.EventDateAdapter;
import com.production.qtickets.eventBookingPaymentSummaryQT5.EventBookingPaymentSummaryQT5Activity;
import com.production.qtickets.login.LoginActivity;
import com.production.qtickets.model.CountryCodeData;
import com.production.qtickets.model.CouponQT5Data;
import com.production.qtickets.model.CouponQT5Model;
import com.production.qtickets.model.CuponCodeModel;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.NationalityData;
import com.production.qtickets.model.PromoCode;

import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class EventBookingSummaryQT5Activity extends AppCompatActivity implements View.OnClickListener, EventBookingSummaryQT5Contracter.View {

    EventBookingSummaryQT5Presenter presenter;
    int TotalTicketCount = 0;
    Double TotalTicketAmount = 0.0;
    Double TotalServiceAmount = 0.0;
    Double WhatsappCharges = 0.0;
    Double SmsCharges = 0.0;
    List<EventTicketQT5Type> typeList;
    int EventDateID = 0;
    String EventDate = "";
    String EventName = "";
    String EventVenue = "";
    String CountryCode = "";
    String eventTime = "";
    String residence_name = "";


    String nationalityname = "";
    String residence_code = "";
    int EventID = 0;
    JSONArray jsonArray = new JSONArray();
    JSONArray jsonArrayForverify = new JSONArray();
    boolean isGuestUser = true;
    boolean isCoupon = false;
    boolean isCouponApplied = false;
    public CouponQT5Data couponQT5Data = null;
    public CuponCodeModel cuponcodemodel = null;
    public PromoCode prmocode = null;
    JSONObject jsonObjectTicketFields;

    TextView tv_event_name, tv_date_time, tv_venue, event_name, tv_sub_total, tvservicecharge, tv_sms_charges, tv_whatsapp_charges, tv_service_charge, tv_total_amt, coupon_discount, tv_coupon_discount;
    EditText et_coupon, et_name, et_email, et_phone_no;
    Button btn_apply, btn_book_now, btn_remove;
    ImageView btn_back;
    LinearLayout lay_login_user_option, lay_login, lay_continue_as_guest, lay_ticket_type_amt, lay_ticket_type, lay_guest_user_info;

    Spinner spinner_countrydropdown, sp_nationality, sp_residency;
    ArrayAdapter<String> adapter;

    SessionManager sessionManager;
    private int selCountryPos;
    String country;

    RadioButton rb_couponcode, rb_promocode;
    String isPromoorcupon = "";
    private PhoneNumberUtil util = null;
    private double couponAmount = 0;
    private boolean IS_EVENT_OFFLINE = false;
    private int viewCount = 0, totalViewCount = 0;
    private int totaladmit = 0;

    ArrayList<String> mCountrynames = new ArrayList<>();
    ArrayList<String> mCountryCodes = new ArrayList<>();
    private boolean isDaypass = false;
    private boolean isDTCM = false;
    String eventEndDate = "";


    ArrayList<String> nationalityValueList = new ArrayList<>();
    ArrayList<String> nationalityCodeList = new ArrayList<>();

    ArrayList<String> residencyValueList = new ArrayList<>();
    ArrayList<String> residencyCodeList = new ArrayList<>();
    TextView tv_dob;

    LinearLayout ll_dtcm;

    String isAgree = "0";
    int sendOnWhatsapp = 1;
    int sendOnSMS = 1;
    String formattedCharges = "", formettedSmsCharges = "";
//    ImageView img_select;

    TextView txt_terms, txt_privacy, tvsms, tvwhtsapp;
    RelativeLayout r_agree;
    CheckBox cb_sendonwhtsapp, cb_terms, cb_sendsms;
    DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_booking_summary_qt5);
        presenter = new EventBookingSummaryQT5Presenter();
        presenter.attachView(this);
        decimalFormat = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));

//      decimalFormat = new DecimalFormat("#.##");
        sessionManager = new SessionManager(EventBookingSummaryQT5Activity.this);
        TotalTicketCount = getIntent().getIntExtra(AppConstants.NO_OF_TICKETS, 0);
        TotalTicketAmount = getIntent().getDoubleExtra(AppConstants.TOTAL_AMT, 0.0);
        TotalServiceAmount = getIntent().getDoubleExtra(AppConstants.SERVICE_CHARGE, 0.0);
        WhatsappCharges = getIntent().getDoubleExtra(AppConstants.WHATSAPP_CHARGES, 0.0);
        SmsCharges = getIntent().getDoubleExtra(AppConstants.SMS_CHARGES, 0.0);

        Log.d("4Jan", "TotalServiceAmount : " + TotalServiceAmount);

        // Format the WhatsappCharges value to 2 decimal places
        formattedCharges = decimalFormat.format(WhatsappCharges);
        formettedSmsCharges = decimalFormat.format(SmsCharges);

        EventID = getIntent().getIntExtra(AppConstants.EVENT_ID, 0);
        IS_EVENT_OFFLINE = getIntent().getBooleanExtra(AppConstants.IS_EVENT_OFFLINE, false);
        EventDateID = getIntent().getIntExtra(AppConstants.EVENT_DATE_ID, 0);
        isDaypass = getIntent().getBooleanExtra(AppConstants.IS_DAY_PASS, false);
        isDTCM = getIntent().getBooleanExtra(AppConstants.IS_DTCM_EVENT, false);
        eventEndDate = getIntent().getStringExtra(AppConstants.END_EVENT_DATE);
        EventDate = getIntent().getStringExtra(AppConstants.EVENT_DATE);
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        EventName = getIntent().getStringExtra(AppConstants.EVENT_NAME);
        EventVenue = getIntent().getStringExtra(AppConstants.EVENT_VENUE);
        CountryCode = getIntent().getStringExtra(AppConstants.COUNTRY_TYPE_CURRENCY);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);

        spinner_countrydropdown = (Spinner) findViewById(R.id.spinner_countrydropdown);
        sp_nationality = (Spinner) findViewById(R.id.sp_nationality);
        sp_residency = (Spinner) findViewById(R.id.sp_residency);
        r_agree = findViewById(R.id.r_agree);


//        img_select = (ImageView) findViewById(R.id.img_select);


        txt_terms = (TextView) findViewById(R.id.txt_terms);
        tvsms = (TextView) findViewById(R.id.tvsms);
        tvwhtsapp = (TextView) findViewById(R.id.tvwhtsapp);
        cb_sendonwhtsapp = findViewById(R.id.cb_sendonwhtsapp);
        cb_sendsms = findViewById(R.id.cb_sendsms);
        cb_terms = findViewById(R.id.cb_terms);
        txt_privacy = (TextView) findViewById(R.id.txt_privacy);

        getCountryCode();
        getNationalityDropdown();
        getResidentDropdown();

        try {
            jsonObjectTicketFields = new JSONObject(getIntent().getStringExtra(AppConstants.TICKET_FIELDS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (EventTicketQT5Type setTicketType : typeList) {
            if (setTicketType.quantity > 0) {
                totalViewCount++;
            }
        }
        Log.d("viewCount :", "total view count " + String.valueOf(totalViewCount));
        // cupon code
        rb_couponcode = (RadioButton) findViewById(R.id.rb_couponcode);
        rb_promocode = (RadioButton) findViewById(R.id.rb_promocode);
        isPromoorcupon = AppConstants.IS_COUPON_CODE;

        util = PhoneNumberUtil.createInstance(getApplicationContext());


        tv_dob = (TextView) findViewById(R.id.tv_dob);
        ll_dtcm = (LinearLayout) findViewById(R.id.ll_dtcm);

        if (isDTCM) {
            ll_dtcm.setVisibility(View.VISIBLE);
        } else {
            ll_dtcm.setVisibility(View.GONE);
        }


        coupon_discount = (TextView) findViewById(R.id.coupon_discount);
        tv_coupon_discount = (TextView) findViewById(R.id.tv_coupon_discount);
        tv_event_name = (TextView) findViewById(R.id.tv_event_name);
        tv_date_time = (TextView) findViewById(R.id.tv_date_time);
        //   tv_venue = (TextView) findViewById(R.id.tv_venue);
        event_name = (TextView) findViewById(R.id.event_name);
        tv_sub_total = (TextView) findViewById(R.id.tv_sub_total);
        tv_whatsapp_charges = (TextView) findViewById(R.id.tv_whatsapp_charges);
        tv_sms_charges = (TextView) findViewById(R.id.tv_sms_charges);
        tv_service_charge = (TextView) findViewById(R.id.tv_service_charge);
        tv_total_amt = (TextView) findViewById(R.id.tv_total_amt);
        tvservicecharge = (TextView) findViewById(R.id.tvservicecharge);

        et_coupon = (EditText) findViewById(R.id.et_coupon);
        et_name = (EditText) findViewById(R.id.et_name);
//        et_name.setFilters(new InputFilter[]{filter});
        et_name.setLongClickable(false);
        et_name.setInputType(InputType.TYPE_CLASS_TEXT);

/*
        et_name.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
*/

        et_email = (EditText) findViewById(R.id.et_email);
        et_phone_no = (EditText) findViewById(R.id.et_phone_no);

        btn_back = (ImageView) findViewById(R.id.btn_back);

        btn_apply = (Button) findViewById(R.id.btn_apply);
        btn_book_now = (Button) findViewById(R.id.btn_book_now);
        btn_remove = (Button) findViewById(R.id.btn_remove);

        lay_login = (LinearLayout) findViewById(R.id.lay_login);
        lay_continue_as_guest = (LinearLayout) findViewById(R.id.lay_continue_as_guest);
        //     lay_ticket_type_amt = (LinearLayout) findViewById(R.id.lay_ticket_type_amt);
        lay_ticket_type = (LinearLayout) findViewById(R.id.lay_ticket_type);
        lay_guest_user_info = (LinearLayout) findViewById(R.id.lay_guest_user_info);
        lay_login_user_option = (LinearLayout) findViewById(R.id.lay_login_user_option);


//        ONCLICK
        btn_back.setOnClickListener(this);
        btn_remove.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);
        lay_login.setOnClickListener(this);
        lay_continue_as_guest.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        tv_dob.setOnClickListener(this);

        cb_sendonwhtsapp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    sendOnWhatsapp = 1;
                    tvwhtsapp.setVisibility(View.VISIBLE);
                    tv_whatsapp_charges.setVisibility(View.VISIBLE);

                    Double IntValue = 0.0;
                    String formattedValue = "";
                    if (isCouponApplied) {
                        // as TotalServiceAmount is already added in totalamount ignore  it
                        if (sendOnSMS == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + SmsCharges) - couponAmount;
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges) - couponAmount;
                        formattedValue = decimalFormat.format(IntValue);

                    } else {
                        if (sendOnSMS == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + SmsCharges);
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges);
                        formattedValue = decimalFormat.format(IntValue);

                    }


                    Log.d("24Oct", "WisChecked :formattedValue " + formattedValue);
                    Log.d("24Oct", "WisChecked WhatsappCharges:" + WhatsappCharges);

                    tv_total_amt.setText("" + formattedValue + " " + CountryCode);
                } else {
                    Double IntValue = 0.0;

                    String formattedValue = "";
                    if (isCouponApplied) {
                        if (sendOnSMS == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + SmsCharges) - couponAmount;
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount) - couponAmount;
                        formattedValue = decimalFormat.format(IntValue);
                    } else {

                        if (sendOnSMS == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + SmsCharges);
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount);
                        formattedValue = decimalFormat.format(IntValue);

                    }

                    tv_total_amt.setText("" + formattedValue + " " + CountryCode);

                    sendOnWhatsapp = 0;
                    tvwhtsapp.setVisibility(View.GONE);
                    tv_whatsapp_charges.setVisibility(View.GONE);
                    Log.d("24Oct", "WisChecked else formattedValue:" + formattedValue);

                }
            }
        });

        cb_sendsms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    sendOnSMS = 1;
                    tvsms.setVisibility(View.VISIBLE);
                    tv_sms_charges.setVisibility(View.VISIBLE);

                    Double IntValue = 0.0;
                    String formattedValue = "";
                    if (isCouponApplied) {
                        // as TotalServiceAmount is already added in totalamount ignore  it

                        if (sendOnWhatsapp == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + SmsCharges) - couponAmount;
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount + SmsCharges) - couponAmount;

                        formattedValue = decimalFormat.format(IntValue);

                    } else {
                        if (sendOnWhatsapp == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + SmsCharges);
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount + SmsCharges);

                        formattedValue = decimalFormat.format(IntValue);

                    }

                    Log.d("24Oct", "WisChecked :formattedValue " + formattedValue);
                    Log.d("24Oct", "WisChecked WhatsappCharges:" + WhatsappCharges);

                    tv_total_amt.setText("" + formattedValue + " " + CountryCode);
                } else {
                    Double IntValue = 0.0;

                    String formattedValue = "";
                    if (isCouponApplied) {
                        if (sendOnWhatsapp == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges) - couponAmount;
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount) - couponAmount;
                        formattedValue = decimalFormat.format(IntValue);
                    } else {

                        if (sendOnWhatsapp == 1)
                            IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges);
                        else
                            IntValue = (TotalTicketAmount + TotalServiceAmount);
                        formattedValue = decimalFormat.format(IntValue);

                    }

                    tv_total_amt.setText("" + formattedValue + " " + CountryCode);

                    sendOnSMS = 0;
                    tvsms.setVisibility(View.GONE);
                    tv_sms_charges.setVisibility(View.GONE);
                    Log.d("24Oct", "WisChecked else formattedValue:" + formattedValue);

                }
            }
        });

        cb_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    isAgree = "1";

                } else {

                    isAgree = "0";

                }
            }
        });

        txt_terms.setOnClickListener(this);
        txt_privacy.setOnClickListener(this);

        tv_event_name.setText("" + EventName);
        event_name.setText("" + EventName);

        String endDate = "";
        String[] strDate = EventDate.split("T");
        String[] strEndDate = eventEndDate.split("T");
        if (eventTime != null && !eventTime.equals("") && !eventTime.equals("null")) {
            String startdate = "" + DateTimeUtils.getEventFullDate(strDate[0]) + " | " + eventTime;
            if (!eventEndDate.equals("")) {
                endDate = "" + DateTimeUtils.getEventFullDate(strEndDate[0]) + " | " + eventTime;
            }
            if (!eventEndDate.equals("")) {
                tv_date_time.setText(startdate + "to " + endDate + " | " + EventVenue);
            } else {
                tv_date_time.setText(startdate + " | " + EventVenue);
            }
        } else {
            String startdate = "" + DateTimeUtils.getEventFullDate(strDate[0]);
            if (eventEndDate.equals("")) {
                tv_date_time.setText(startdate + " | " + EventVenue);
            } else {
                String endDatess = "" + DateTimeUtils.getEventFullDate(strEndDate[0]);
                tv_date_time.setText(startdate + " to " + endDatess + " | " + EventVenue);

            }
        }

        tv_sub_total.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
        tv_service_charge.setText("" + String.format("%.2f", TotalServiceAmount) + " " + CountryCode);
        tv_whatsapp_charges.setText("" + formattedCharges + " " + CountryCode);
        tv_sms_charges.setText("" + formettedSmsCharges + " " + CountryCode);

        Double IntValue = 0.0;

        if (sendOnWhatsapp == 1) {

            if (sendOnSMS == 1)
                IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + SmsCharges);
            else
                IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges);

        } else {
            if (sendOnSMS == 1)
                IntValue = (TotalTicketAmount + TotalServiceAmount + SmsCharges);
            else
                IntValue = (TotalTicketAmount + TotalServiceAmount);

        }

        String formattedValue = decimalFormat.format(IntValue);

        tv_total_amt.setText("" + formattedValue + " " + CountryCode); //one

        if (lay_ticket_type != null) {
            lay_ticket_type.removeAllViews();
        }
        // get size of views which gonna add...

        for (EventTicketQT5Type setTicketType : typeList) {
            setTicketType(setTicketType);
        }

        rb_promocode.setOnCheckedChangeListener(new Radio_check());
        rb_couponcode.setOnCheckedChangeListener(new Radio_check());
        bindCountrySpinner();
        if (TotalServiceAmount > 0) {
            //only show key values then ...
            tv_service_charge.setVisibility(View.VISIBLE);
            tvservicecharge.setVisibility(View.VISIBLE);
        } else {
            tv_service_charge.setVisibility(View.GONE);
            tvservicecharge.setVisibility(View.GONE);
        }

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

/*
    private InputFilter filterString = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && AppConstants.blockCharacterSet.contains(("" + source))) {

                validString = true;
                Log.d("ValidString : ", source + " TRUEEE");

                return "";
            } else {

                Log.d("ValidString : ", source + " FALSE");

                validString = false;
                return "";
            }
            // subham update
//            return null;
        }
    };
*/

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && AppConstants.blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    class Radio_check implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            Double TotalTicketAmount1 = 0.0;

            if (buttonView.getId() == R.id.rb_promocode) {
                et_coupon.setEnabled(true);
                isCouponApplied = false;
                if (rb_promocode.isChecked()) {
                    isPromoorcupon = AppConstants.IS_PROMO_COUPON;
                    Toast.makeText(EventBookingSummaryQT5Activity.this, "Please enter promo code", Toast.LENGTH_SHORT).show();
                }
            }

            if (buttonView.getId() == R.id.rb_couponcode) {
                et_coupon.setEnabled(true);
                isCouponApplied = false;
                if (rb_couponcode.isChecked()) {
                    isPromoorcupon = AppConstants.IS_COUPON_CODE;
                    Toast.makeText(EventBookingSummaryQT5Activity.this, "Please enter Voucher code", Toast.LENGTH_SHORT).show();
                }
            }
            et_coupon.setText("");
            btn_remove.setVisibility(View.GONE);
            btn_apply.setVisibility(View.VISIBLE);
            TotalTicketAmount1 = TotalTicketAmount + couponAmount;
            couponAmount = 0;

            Double IntValue = 0.0;
            if (sendOnWhatsapp == 1) {

                if (sendOnSMS == 1)
                    IntValue = (TotalTicketAmount1 + TotalServiceAmount + WhatsappCharges + SmsCharges);
                else
                    IntValue = (TotalTicketAmount1 + TotalServiceAmount + WhatsappCharges);
            } else {
                if (sendOnSMS == 1)
                    IntValue = (TotalTicketAmount1 + TotalServiceAmount + SmsCharges);
                else
                    IntValue = (TotalTicketAmount1 + TotalServiceAmount);

            }

            DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
            String formattedAmount = df.format(IntValue);

            tv_total_amt.setText("" + formattedAmount + " " + CountryCode); //two

            tv_coupon_discount.setVisibility(View.GONE);
            coupon_discount.setVisibility(View.GONE);

        }
    }


    private void getResidentDropdown() {

        residencyCodeList.clear();
        residencyValueList.clear();
        residencyValueList.add("Select Residential");
        residencyCodeList.add("0");
        String jsonFileString = QTUtils.getJsonFromAssets(getApplicationContext(), "nationality.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<NationalityData>>() {
        }.getType();
        List<NationalityData> users = gson.fromJson(jsonFileString, listUserType);

        try {
            for (int i = 0; i < users.size() - 1; i++) {
                Log.d("sampostion : ", String.valueOf(i));
                residencyValueList.add(users.get(i).getLabel());
                residencyCodeList.add(users.get(i).getValue());
            }

        } catch (Exception e) {


        }
        ArrayAdapter ad = new ArrayAdapter(this, R.layout.spinner_dropdownitem2, residencyValueList);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        sp_residency.setAdapter(ad);
        sp_residency.setSelection(0);

        sp_residency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    residence_name = residencyValueList.get(position);
                    residence_code = residencyCodeList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getNationalityDropdown() {
        nationalityValueList.clear();
        nationalityCodeList.clear();
        nationalityValueList.add("Select Nationality");
        nationalityCodeList.add("0");
        String jsonFileString = QTUtils.getJsonFromAssets(getApplicationContext(), "nationality.json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<NationalityData>>() {
        }.getType();
        List<NationalityData> users = gson.fromJson(jsonFileString, listUserType);

        try {
            for (int i = 0; i < users.size() - 1; i++) {
                Log.d("sampostion : ", String.valueOf(i));
                nationalityValueList.add(users.get(i).getLabel());
                nationalityCodeList.add(users.get(i).getValue());
            }

        } catch (Exception e) {


        }
        ArrayAdapter ad = new ArrayAdapter(this, R.layout.spinner_dropdownitem2, nationalityValueList);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        sp_nationality.setAdapter(ad);
        sp_nationality.setSelection(0);
        sp_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //bind the spinner for country flags
    private void bindCountrySpinner() {

        // Creating adapter for spinner

        int positin = -1;

        Log.d("11oct:", "Received : " + sessionManager.getPrefix());

        String countrycode[] = AppConstants.country_code;
        for (int i = 0; i < countrycode.length; i++) {
            if (sessionManager.getPrefix().equals(countrycode[i])) {
                positin = i;
                Log.d("Prefix:", "positin " + positin);
                break;

            }
        }
        if (positin == -1) {
            positin = 0;
        }
//      adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter, AppConstants.country_code, AppConstants.flags, true, positin);
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter,
                mCountrynames, mCountryCodes,
                AppConstants.flag, true, positin, false);

        spinner_countrydropdown.setAdapter(adapter);

        spinner_countrydropdown.setSelection(positin);

        spinner_countrydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                selCountryPos = i;
                country = mCountryCodes.get(selCountryPos);

                Log.d("9sep:", "selCountryPos :" + selCountryPos + i + "country" + country);


                if (mCountrynames.get(i).equals("Qatar")) {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                } else if (mCountrynames.get(i).equals("Dubai")) {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                } else if (mCountrynames.get(i).equals("Bahrain")) {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                } else if (mCountrynames.get(i).equals("India")) {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                } else {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
                }

                Log.d("Prefix:" + "country code", country);
                Log.d("19sep:" + "CountryName : ", mCountrynames.get(i));
                /*if (sessionManager.getSelectedCountryCode().equals(country)) {
                    sessionManager.setSelectedCountryCode(country);
                } else {
                    et_phone_no.setText("");
                    sessionManager.setSelectedCountryCode(country);
                }*/
                et_phone_no.setText("");
                sessionManager.setSelectedCountryCode(country);

                Log.v("countryCode = ", "id" + mCountrynames);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "select country code");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (new SessionManager(getApplicationContext()).getUserId() != null && !new SessionManager(getApplicationContext()).getUserId().equals("")) {
            lay_guest_user_info.setVisibility(View.GONE);
            lay_login_user_option.setVisibility(View.GONE);
            r_agree.setVisibility(View.GONE);
            isGuestUser = false;
        } else {
            lay_guest_user_info.setVisibility(View.VISIBLE);
            lay_login_user_option.setVisibility(View.VISIBLE);
            r_agree.setVisibility(View.VISIBLE);

            lay_continue_as_guest.setBackgroundResource(R.drawable.selected_tab_with_corners);
            lay_login.setBackground(null);
            isGuestUser = true;
        }
    }

    public void setTicketType(@NonNull EventTicketQT5Type type) {
        if (type.quantity > 0) {
            viewCount++;
            Log.d("viewCount :", String.valueOf(viewCount));

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("quantity", type.quantity);
                jsonObject.put("ticketId", type.id);
                jsonObject.put("ticketPrice", (type.price + type.serviceCharge));
                jsonArray.put(jsonObject);
                // for verification json object for booking
                JSONObject jsonfroverify = new JSONObject();
                jsonfroverify.put("ticketId", type.id);

                double final_Price = type.price;
                if (type.discount > 0) {
                    if (type.discountType == 1) {
                        final_Price = type.price - type.discount;
                    } else if (type.discountType == 2) {
                        final_Price = type.price - ((type.price * type.discount) / 100);
                    }
                }

                if (type.serviceCharge > 0) {
                    if (type.chargeType == 1) {
                        final_Price = final_Price + type.serviceCharge;
                    }
                    if (type.chargeType == 2) {
                        final_Price = final_Price + (final_Price * type.serviceCharge) / 100;
                    }
                }
//              jsonfroverify.put("price", final_Price);
                jsonfroverify.put("price", decimalFormat.format(final_Price));
                jsonfroverify.put("ticketName", type.ticketName);
                jsonfroverify.put("quantity", type.quantity);
                jsonArrayForverify.put(jsonfroverify);
                String ticketXML = "" + jsonArray.toString();
                String strTickets = "";
                String category = "";
                if (type.category != null && !type.category.isEmpty()) {
                    category = type.category;
                } else {
                    category = "";
                }
                if (type.quantity > 1) {
                    if (category.equals("")) {
                        if (type.discount > 0) {
                            strTickets = type.ticketName + " (Early Bird)" + "  : " + type.quantity + " Tickets";
                        } else {
                            strTickets = type.ticketName + "  : " + type.quantity + " Tickets";
                        }

                    } else {
                        if (type.discount > 0) {
                            strTickets = type.ticketName + " (Early Bird)" + "  (" + type.category + ")  : " + type.quantity + " Tickets";
                        } else {
                            strTickets = type.ticketName + "  (" + type.category + ")  : " + type.quantity + " Tickets";
                        }

                    }
                } else {
                    if (category.equals("")) {
                        if (type.discount > 0) {
                            strTickets = type.ticketName + " (Early Bird)" + "  : " + type.quantity + " Ticket";
                        } else {
                            strTickets = type.ticketName + "  : " + type.quantity + " Ticket";
                        }

                    } else {
                        if (type.discount > 0) {
                            strTickets = type.ticketName + " (Early Bird)" + "  (" + type.category + ")  : " + type.quantity + " Ticket";
                        } else {
                            strTickets = type.ticketName + "  (" + type.category + ")  : " + type.quantity + " Ticket";
                        }


                    }
                }

                Double _Price = 0.0;
                if (type.discount > 0) {
                    if (type.discountType == 1) {
                        if (!type.isFixPrice) {
                            _Price = type.quantity * (type.price - type.discount);
                        } else {
                            _Price = (type.price - type.discount);
                        }
                    } else if (type.discountType == 2) {
                        Double dis = (type.price * type.discount) / 100;

                        if (!type.isFixPrice) {
                            _Price = type.quantity * (type.price - dis);
                        } else {
                            _Price = (type.price - dis);
                        }
                    }
                } else {

                    if (!type.isFixPrice) {
                        _Price = type.quantity * type.price;
                    } else {
                        _Price = type.price;
                    }


                }
                // lay_ticket_type_amt.addView(addViewTicketAmt("" + String.format("%.2f", _Price) + " " + CountryCode));
                lay_ticket_type.addView(addViewTicketType(strTickets, "" + String.format("%.2f", _Price) + " " + CountryCode));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public View addViewTicketType(String str, String amount) {
        View v = null;

        if (viewCount == 1) {
            v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_typewithtoppadding, null, false);
            Log.d("Count:" + " EQUAL", String.valueOf(viewCount) + str);
        } else {
            v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_typewithtoppadding, null, false);
            Log.d("Count:" + "NOT EQUAL", String.valueOf(viewCount) + str);

        }

        TextView tv = v.findViewById(R.id.tv_ticket_view);
        LinearLayout llone = v.findViewById(R.id.llone);

        if (viewCount == 1) {
            //test...
            llone.setBackground(getResources().getDrawable(R.drawable.lightgrey_with_topcorners));
            Log.d("Count:" + " EQUAL", String.valueOf(viewCount) + str);
        } else {

            llone.setBackground(getResources().getDrawable(R.drawable.lightgrey));
            Log.d("Count:" + "NOT EQUAL", String.valueOf(viewCount) + str);

        }

        TextView tv_ticket_value = (TextView) v.findViewById(R.id.tv_ticket_value);
        tv.setText("" + str);
        Log.d("viewCount :", "Str : " + str);
        tv_ticket_value.setText("" + amount);
        return v;

    }

    public View addViewTicketAmt(String str) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_amt, null, false);
        TextView tv = (TextView) v.findViewById(R.id.tv_ticket_view);
        tv.setText("" + str);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;


            case R.id.tv_dob:
                setCalender();
                break;


            case R.id.txt_terms:

                Bundle b = new Bundle();
                b.putString("cmsType", "TermsAndConditions");
                Intent intents = new Intent(EventBookingSummaryQT5Activity.this, CMSPagesActivity.class);
                intents.putExtras(b);
                startActivity(intents);

                break;

            case R.id.txt_privacy:
                Bundle bbb = new Bundle();
                bbb.putString("cmsType", "PrivacyPolicy");
                Intent intentsss = new Intent(EventBookingSummaryQT5Activity.this, CMSPagesActivity.class);
                intentsss.putExtras(bbb);
                startActivity(intentsss);
                break;

           /* case R.id.img_select:
                if (isAgree.equals("0")) {
                    isAgree = "1";
                    img_select.setImageResource(R.drawable.ic_selected);
                    //20sep
                } else {
                    isAgree = "0";
                    img_select.setImageResource(R.drawable.ic_select);
                }
                break;*/


            case R.id.btn_remove:

                et_coupon.setEnabled(true);
                isCouponApplied = false;
                et_coupon.setText("");
                btn_remove.setVisibility(View.GONE);
                btn_apply.setVisibility(View.VISIBLE);

                Log.d("24Oct", "btn_remove : TotalTicketAmount" + TotalTicketAmount);
                Log.d("24Oct", "btn_remove : couponAmount" + couponAmount);


                Double IntValue1 = 0.0;
                if (sendOnWhatsapp == 1) {

                    if (sendOnSMS == 1)
                        IntValue1 = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + WhatsappCharges);
                    else
                        IntValue1 = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges);
                } else {

                    if (sendOnSMS == 1)
                        IntValue1 = (TotalTicketAmount + TotalServiceAmount + SmsCharges);
                    else
                        IntValue1 = (TotalTicketAmount + TotalServiceAmount);

                }

                String formattedValue1 = decimalFormat.format(IntValue1);

                tv_total_amt.setText("" + formattedValue1 + " " + CountryCode);
                couponAmount = 0;
                tv_coupon_discount.setVisibility(View.GONE);
                coupon_discount.setVisibility(View.GONE);

                break;


            case R.id.btn_book_now: {

                String text = tv_total_amt.getText().toString();
                String finalTotalValue = text.replace(CountryCode, "").trim();
                totaladmit = 0; // as on click of book now everytime if user doesn't go back to previous value multiplies...
                //commit this change ....

                //first commit ...

                try {
                    JSONObject jsonObject = new JSONObject();
                    String[] strDate = EventDate.split("T");
                    jsonObject.put("orderDate", "" + strDate[0]);
                    jsonObject.put("countryId", "" + new SessionManager(getApplicationContext()).getCountryID());

                    jsonObject.put("totalAmt", finalTotalValue);
//                    Log.d("24Oct", "totalAmt:" + finalTotalValue);

                    jsonObject.put("eventId", "" + EventID);
                    jsonObject.put("eventName", "" + EventName);
                    jsonObject.put("venueName", "" + EventVenue);
                    jsonObject.put("dateOfBirth", "");
                    jsonObject.put("resident", "");
                    jsonObject.put("createdIP", "");

                    if (sendOnWhatsapp == 1)
                        jsonObject.put("isWhatsAppOpted", true);
                    else
                        jsonObject.put("isWhatsAppOpted", false);

                    if (sendOnSMS == 1)
                        jsonObject.put("isSMSOpted", true);
                    else
                        jsonObject.put("isSMSOpted", false);

                    //verify booking confirmation
                    JSONObject jsonObjectforverfiying = new JSONObject();
                    jsonObjectforverfiying.put("eventId", "" + EventID);
                    jsonObjectforverfiying.put("isDayPass", isDaypass);
                    int eventoffline = 0;
                    if (IS_EVENT_OFFLINE) {
                        eventoffline = 1;
                    } else {
                        eventoffline = 0;
                    }
                    jsonObjectforverfiying.put("eventIsOffline", eventoffline);

                    Log.d("9Sep", "sendOnWhatsapp :" + sendOnWhatsapp);

                    if (sendOnWhatsapp == 1)
                        jsonObjectforverfiying.put("isWhatsAppOpted", true);
                    else
                        jsonObjectforverfiying.put("isWhatsAppOpted", false);

                    if (sendOnSMS == 1)
                        jsonObjectforverfiying.put("isSMSOpted", true);
                    else
                        jsonObjectforverfiying.put("isSMSOpted", false);

                    //whatsapp issue resolved...
                    String[] strDatetem = EventDate.split("T");
                    jsonObjectforverfiying.put("bookingDate", "" + strDatetem[0]);
                    if (eventTime != null) {
                        String[] starttime = eventTime.split("-");
                        jsonObjectforverfiying.put("bookingStartTime", "" + starttime[0]);
                    } else {
                        jsonObjectforverfiying.put("bookingStartTime", "");
                    }
                    jsonObjectforverfiying.put("bookingTickets", jsonArrayForverify.toString());
                    jsonObjectforverfiying.put("totalPrice", finalTotalValue);

                    JSONArray jsonArray = new JSONArray();
                    for (EventTicketQT5Type type : typeList) {
                        double ebTotalDiscount = 0.0;
                        double serviceValue = 0.0;
                        if (type.quantity > 0) {
                            JSONObject jsonObjectTicket = new JSONObject();
                            if (type.discountType == 1) {
                                ebTotalDiscount = type.discount * type.quantity;
                            }
                            if (type.discountType == 2) {
                                Double disc = (type.price * type.discount) / 100;
                                ebTotalDiscount = disc * type.quantity;
                            }


                            if (type.chargeType == 1) {
                                serviceValue = type.serviceCharge * type.quantity;
                            }
                            if (type.chargeType == 2) {
                                Double disc = (type.price * type.serviceCharge) / 100;
                                serviceValue = disc * type.quantity;
                            }

                            totaladmit = totaladmit + (type.admit * type.quantity);
                            jsonObjectTicket.put("EBDiscountTotalValue", ebTotalDiscount);
                            jsonObjectTicket.put("EBDiscountAmount", +type.discount);
                            jsonObjectTicket.put("EBDiscountType", +type.discountType);
                            jsonObjectTicket.put("ServiceType", +type.chargeType);
                            jsonObjectTicket.put("ServiceCharge", +type.serviceCharge);
                            jsonObjectTicket.put("ServiceValue", +serviceValue);

                            jsonObjectTicket.put("CategoryId", +type.categoryId);
                            jsonObjectTicket.put("TicketId", +type.id);
                            jsonObjectTicket.put("Quantity", +type.quantity);
                            jsonObjectTicket.put("Price", +type.price);
                            jsonObjectTicket.put("IsIndividualTicket", (type.isIndividualTicket) ? 1 : 0);
                            jsonArray.put(jsonObjectTicket);
                        }
                    }
                    jsonObject.put("orderMapXML", "" + jsonArray.toString());
                    String email = "";
                    if (isGuestUser) {
                        email = et_email.getText().toString();
                    } else {
                        email = sessionManager.getEmail();
                    }

                    if (isCouponApplied) {
                        if (cuponcodemodel != null) {
                            jsonObject.put("voucherName", "" + cuponcodemodel.data.voucherName);
                            jsonObject.put("voucherAmount", cuponcodemodel.data.voucherAmount);
                            jsonObjectforverfiying.put("voucherName", "" + cuponcodemodel.data.voucherName);
                            jsonObjectforverfiying.put("email", email);

                        } else {
                            jsonObject.put("voucherName", "");
                            jsonObject.put("voucherAmount", 0);
                            jsonObjectforverfiying.put("email", email);
                        }

                        if (prmocode != null) {
                            jsonObject.put("promocodeName", "" + prmocode.data.promoCodeName);
                            jsonObject.put("promocodeType", 1);
                            jsonObject.put("promoCodeDisType", 1);
                            jsonObject.put("promoCodeDisValue", "" + prmocode.data.discount);
                            jsonObject.put("promoCodeActualValue", "" + prmocode.data.discount);
                            jsonObject.put("couponCode", "" + prmocode.data.promoCodeName);
                            // jsonObject.put("couponCode", "" +  prmocode.data.promoCodeName);
                            jsonObjectforverfiying.put("email", email);
                            jsonObjectforverfiying.put("couponCode", "" + prmocode.data.promoCodeName);
//                            jsonObject.put("voucherName", "");
//                            jsonObject.put("voucherAmount", 0);
                        } else {
                            jsonObject.put("promocodeName", "");
                            jsonObject.put("promocodeType", 0);
                            jsonObject.put("promoCodeDisType", 0);
                            jsonObject.put("promoCodeDisValue", 0);
                            jsonObject.put("promoCodeActualValue", 0);
//                            jsonObject.put("voucherName", "");
//                            jsonObject.put("voucherAmount", 0);
                        }

                    }

                    if (eventTime != null) {
                        if (eventTime.contains("M")) {
                            jsonObject.put("orderTime", eventTime);
                        } else {
                            jsonObject.put("orderTime", "");
                        }
                    } else {
                        jsonObject.put("orderTime", "");
                    }
                    jsonObject.put("paymentMode", "0");
                    jsonObject.put("admit", totaladmit);
                    Log.d("26Oct:" + " final admit Val", String.valueOf(totaladmit));
                    if (isGuestUser) { //todo 29sep
                        if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.pleaseentefields));
                            return;
                        } else if (getSpecialCharacterCount(et_name.getText().toString().trim())) {

                            et_name.requestFocus();
                            et_name.setError("Name should only contains alphabets");
                            return;

                        } else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please enter Email ID");
                            return;
                        } else if (isAgree.equals("0")) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please agree terms and condition and privacy policy");
                            return;
                        } else if (isDTCM) {
                            if (isAgree.equals("0")) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please agree terms and condition and privacy policy");
                                return;
                            }

                            if (tv_dob.getText().toString().isEmpty()) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Date of Birth is mandatory to Fill.");
                                return;
                            }

                            if (residence_name.equals("")) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please select the residence");
                                return;
                            }

                        }

                        if (!isValidEMail22(et_email.getText().toString())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please enter valid Email ID");
                            return;
                        } else if (TextUtils.isEmpty(et_phone_no.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please Enter Phone Number");
                            return;
                        } else if (country.equals("+974")) {
                            if (et_phone_no.getText().toString().length() != 8) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please Add Valid Phone Number");
                                return;
                            }

                        } else if (country.equals("+971")) {
                            if (et_phone_no.getText().toString().length() != 9) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please Add Valid Phone Number");
                                return;
                            }

                        } else if (country.equals("+91")) {
                            if (et_phone_no.getText().toString().length() != 10) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please Add Valid Phone Number");
                                return;
                            }

                        } else if (country.equals("+973")) {
                            if (et_phone_no.getText().toString().length() != 8) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please Add Valid Phone Number");
                                return;
                            }

                        } else {
                            String countrycode = util.getRegionCodeForCountryCode(Integer.parseInt(country));
                            Phonenumber.PhoneNumber phoneNumber = null;

                            try {
                                phoneNumber = util.parse(et_phone_no.getText().toString().trim(), countrycode);
                            } catch (Exception e) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "" + e.toString());
                            }
                            if (!util.isValidNumber(phoneNumber)) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please enter valid mobile number");
                                return;
                            }
                        }

                        // we can not send saved because that is country selected here user can add any number in case of guest login
//                      jsonObject.put("primaryContactNo", "" + sessionManager.getPrefix() + " " + et_phone_no.getText().toString());
                        jsonObject.put("primaryContactNo", "" + country + " " + et_phone_no.getText().toString());
                        jsonObject.put("primaryEmail", "" + et_email.getText().toString());
                        jsonObject.put("primaryUserName", "" + et_name.getText().toString());
                        jsonObject.put("primaryUserId", "" + 0);
                        jsonObject.put("createdBy", "" + 0);

                        Log.d("9Sep", "primaryContactNo :" + "" + country + " " + et_phone_no.getText().toString());

                        // Added for DTCM
                        if (isDTCM) {
                            jsonObject.put("dateOfBirth", tv_dob.getText().toString());
                            jsonObject.put("resident", residence_name);

                        }

                    } else {

                        if (isDTCM) {

                            if (isAgree.equals("0")) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please agree terms and condition and privacy policy");
                                return;
                            }

                            if (tv_dob.getText().toString().isEmpty()) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Date of Birth is mandatory to Fill.");
                                return;
                            }
                            if (residence_name.equals("")) {
                                QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please select the residence");
                                return;
                            }

                        }


                        jsonObject.put("primaryContactNo", sessionManager.getNumber());
                        jsonObject.put("primaryEmail", "" + sessionManager.getEmail());
                        jsonObject.put("primaryUserName", "" + sessionManager.getName());


                        //    Added for DTCM

                        if (isDTCM) {
                            jsonObject.put("dateOfBirth", tv_dob.getText().toString());
                            jsonObject.put("resident", residence_name);

                        }


                        jsonObject.put("primaryUserName", "" + sessionManager.getName());


                      /*jsonObject.put("primaryContactNo", "");
                        jsonObject.put("primaryEmail", "");
                        jsonObject.put("primaryUserName", "");*/ //todo change me
                        if (new SessionManager(getApplicationContext()).getUserId() != null && !new SessionManager(getApplicationContext()).getUserId().equals("")) {
                            jsonObject.put("primaryUserId", "" + new SessionManager(getApplicationContext()).getUserId());
                            jsonObject.put("createdBy", "" + new SessionManager(getApplicationContext()).getUserId());
                        } else {
                            jsonObject.put("primaryUserId", "" + 0);
                            jsonObject.put("createdBy", "" + 0);
                        }
                    }

//                  addFormXml
                    JSONObject objForm = new JSONObject();
                    JSONArray arrayTr = new JSONArray();
                    JSONArray arrayForm = new JSONArray();

                    boolean isAddForm = false;
                    for (EventTicketQT5Type type : typeList) {
                        if (jsonObjectTicketFields.getJSONArray(type.ticketName).length() > 0) {
                            isAddForm = true;
                            for (int i = 0; i < jsonObjectTicketFields.getJSONArray(type.ticketName).length(); i++) {
                                JSONArray jsonArrayData = new JSONArray();
                                JSONObject object = jsonObjectTicketFields.getJSONArray(type.ticketName).getJSONObject(i);
                                Iterator mKeys = object.keys();
                                while (mKeys.hasNext()) {
                                    String Key = (String) mKeys.next();
                                    JSONObject jsonData = new JSONObject();
                                    jsonData.put("FieldName", Key);
                                    jsonData.put("FieldValue", object.getString(Key));
                                    jsonArrayData.put(jsonData);
                                }
                                arrayForm.put(new JSONObject().put("AddForm_" + type.id + "_" + (i + 1), jsonArrayData));
                            }
                        }
                    }

                    objForm.put("table", new JSONObject().put("tr", arrayForm));
                    if (isAddForm) {
                        jsonObject.put("addFormXML", "" + objForm);
                    } else {
                        jsonObject.put("addFormXML", "");
                    }


                    Intent intent = new Intent(getApplicationContext(), EventBookingPaymentSummaryQT5Activity.class);
                    intent.putExtra(AppConstants.IS_DAY_PASS, isDaypass);
                    intent.putExtra(AppConstants.IS_DTCM_EVENT, isDTCM);
                    intent.putExtra(AppConstants.EVENT_DATE_ID, EventDateID);
                    intent.putExtra(AppConstants.RESIDENCE_CODE, residence_code);
                    intent.putExtra(AppConstants.EVENT_DATE, EventDate);
                    intent.putExtra(AppConstants.END_EVENT_DATE, eventEndDate);
                    intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                    intent.putExtra(AppConstants.TOTAL_AMT, Double.valueOf(finalTotalValue));
//                  Log.d("24Oct", "TOTAL_AMT" + finalTotalValue);

                    intent.putExtra(AppConstants.NO_OF_TICKETS, TotalTicketCount);
                    intent.putExtra(AppConstants.EVENT_NAME, EventName);
                    intent.putExtra(AppConstants.EVENT_VENUE, EventVenue);
                    intent.putExtra(AppConstants.EVENT_ID, EventID);
                    intent.putExtra(AppConstants.IS_GUEST, isGuestUser);
                    intent.putExtra(AppConstants.GUEST_NAME, "" + et_name.getText().toString());
                    intent.putExtra(AppConstants.GUEST_EMAIL, "" + et_email.getText().toString());
//                  intent.putExtra(AppConstants.GUEST_PHONE, sessionManager.getPrefix() + et_phone_no.getText().toString());
                    intent.putExtra(AppConstants.GUEST_PHONE, country + et_phone_no.getText().toString());
                    intent.putExtra(AppConstants.IS_COUPON, isCoupon);
                    intent.putExtra(AppConstants.COUPON_CODE, "" + et_coupon.getText().toString());
                    intent.putExtra(AppConstants.COUNTRY_TYPE_CURRENCY, CountryCode);
                    intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                    intent.putExtra(AppConstants.TICKET_XML, jsonObject.toString());
                    intent.putExtra(AppConstants.TICKET_VERIFY_XML, jsonObjectforverfiying.toString());
                    startActivity(intent);
                    Log.d("9Sep", "GUEST_PHONE :" + "" + country + " " + et_phone_no.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            break;
            case R.id.lay_login: {
                lay_login.setBackgroundResource(R.drawable.selected_tab_with_corners);
                lay_continue_as_guest.setBackground(null);
                lay_guest_user_info.setVisibility(View.GONE);
                r_agree.setVisibility(View.GONE);

                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                isGuestUser = false;
            }
            break;
            case R.id.lay_continue_as_guest: {
                lay_continue_as_guest.setBackgroundResource(R.drawable.selected_tab_with_corners);
                lay_login.setBackground(null);
                lay_guest_user_info.setVisibility(View.VISIBLE);
                r_agree.setVisibility(View.VISIBLE);
                isGuestUser = true;
            }
            break;
            case R.id.btn_apply: {

                if (TextUtils.isEmpty(et_coupon.getText().toString().trim())) {
                    QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.e_code));
                    return;
                }

                if (isPromoorcupon.equals(AppConstants.IS_PROMO_COUPON)) {
                    presenter.applyPromoCOde(EventID, et_coupon.getText().toString(), jsonArray.toString(),
                            String.format("%.2f", TotalTicketAmount + TotalServiceAmount));
                    //et_coupon.setText("");

                    Log.d("4Jan", " TotalTicketAmount" + TotalTicketAmount + "TotalServiceAmount" + TotalServiceAmount);

                } else if (isPromoorcupon.equals(AppConstants.IS_COUPON_CODE)) {
                    String userID = "";
                    if (sessionManager.getUserId().equals("")) {
                        userID = "0";
                    } else {
                        userID = sessionManager.getUserId();
                    }
                    String email = "";
                    if (et_email.getText().toString().equals("")) {
                        email = sessionManager.getEmail();
                    } else {
                        email = et_email.getText().toString();
                    }
                    presenter.applyCoupon(String.valueOf(EventID), et_coupon.getText().toString(), email,
                            Double.parseDouble(String.format("%.2f", TotalTicketAmount + TotalServiceAmount)));
                    // et_coupon.setText("");
                }
               /* if (!isPromoorcupon.equals("")) {


                }*/ /*else {
                    QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.a_copon_code_su));
                    return;

                }*/


            }
            break;
        }
    }


    private void setCalender() {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.custom_calender_view_dob,
                        null);
        builder.setView(customLayout);
        CalendarView calendarView = customLayout.findViewById(R.id.calendarView);

        Calendar currentDate = Calendar.getInstance();
        // Set the maximum date to the current date
        try {
            calendarView.setMaximumDate(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 2022-06-29T00:00:00
        AlertDialog dialog
                = builder.create();
        dialog.show();
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                //Wed Mar 15 00:00:00 GMT+05:30 2023
                String date = eventDay.getCalendar().getTime().toString();
                String newdate = DateTimeUtils.getSelectedBOD(date);
                tv_dob.setText(newdate);
                dialog.dismiss();
            }

        });
    }

    public boolean isValidEMail22(String email) {

        String regex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
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

    @Override
    public void setCouponDetail(CuponCodeModel couponQT5Model) {

        if (couponQT5Model.statusCode == 200) {
            Double TotalTicketAmount1 = 0.0;

            et_coupon.setEnabled(false);
            showToast(couponQT5Model.message);
            tv_coupon_discount.setVisibility(View.VISIBLE);
            tv_coupon_discount.setText("Voucher Code");
            coupon_discount.setVisibility(View.VISIBLE);
            couponAmount = couponQT5Model.data.voucherAmount;
//          TotalTicketAmount1 = TotalTicketAmount - couponQT5Model.data.voucherAmount;

            Double IntValue = 0.0;
            if (sendOnWhatsapp == 1) {

                if (sendOnSMS == 1)
                    IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges + sendOnSMS);
                else
                    IntValue = (TotalTicketAmount + TotalServiceAmount + WhatsappCharges);

            } else {
                if (sendOnSMS == 1)

                    IntValue = (TotalTicketAmount + TotalServiceAmount + sendOnSMS);
                else
                    IntValue = (TotalTicketAmount + TotalServiceAmount);

            }
            TotalTicketAmount1 = IntValue - couponQT5Model.data.voucherAmount;

            String formattedValue = decimalFormat.format(TotalTicketAmount1);

            Log.d("23oct", "setCouponDetail " + formattedValue);

            tv_total_amt.setText("" + formattedValue + " " + CountryCode);

            coupon_discount.setText("" + String.format("%.2f", Double.valueOf(couponQT5Model.data.voucherAmount)) + " " + CountryCode);
            //coupon_discount.setText("" + QTUtils.parseDouble(Double.valueOf(couponQT5Model.data.discount)) + " " + CountryCode);
            btn_apply.setVisibility(View.GONE);
            btn_remove.setVisibility(View.VISIBLE);
            isCouponApplied = true;
            cuponcodemodel = couponQT5Model;
            //    couponQT5Model=;

        } else {
            tv_coupon_discount.setVisibility(View.GONE);
            coupon_discount.setVisibility(View.GONE);
            isCouponApplied = false;
            QTUtils.showDialogbox(this, couponQT5Model.message);
        }

    }

    @Override
    public void setPromocodeDetails(PromoCode promocodeDetails) {

        if (promocodeDetails.statusCode == 200) {
            Double TotalTicketAmount1 = 0.0;
            et_coupon.setEnabled(false);
            showToast(promocodeDetails.message);
            tv_coupon_discount.setVisibility(View.VISIBLE);
            tv_coupon_discount.setText("Promo Code");
            coupon_discount.setVisibility(View.VISIBLE);
            couponAmount = promocodeDetails.data.discount;


            Double sum = (TotalTicketAmount + TotalServiceAmount);
            TotalTicketAmount1 = sum - promocodeDetails.data.discount;

            Double IntValue1 = 0.0;
            if (sendOnWhatsapp == 1) {

                if (sendOnSMS == 1)
                    IntValue1 = (TotalTicketAmount1 + WhatsappCharges + sendOnSMS);
                else
                    IntValue1 = (TotalTicketAmount1 + WhatsappCharges);

            } else {

                if (sendOnSMS == 1)
                    IntValue1 = (TotalTicketAmount1 + sendOnSMS);
                else
                    IntValue1 = (TotalTicketAmount1);

            }

            String formattedValue = decimalFormat.format(IntValue1);

            Log.d("24Oct", "setPromocodeDetails WhatsappCharges: " + WhatsappCharges);

            tv_total_amt.setText("" + formattedValue + " " + CountryCode);
            coupon_discount.setText("" + String.format("%.2f", Double.valueOf(promocodeDetails.data.discount)) + " " + CountryCode);
            //coupon_discount.setText("" + QTUtils.parseDouble(Double.valueOf(couponQT5Model.data.discount)) + " " + CountryCode);
            btn_apply.setVisibility(View.GONE);
            btn_remove.setVisibility(View.VISIBLE);
            prmocode = promocodeDetails;
            isCouponApplied = true;
        } else {
            tv_coupon_discount.setVisibility(View.GONE);
            coupon_discount.setVisibility(View.GONE);
            isCouponApplied = false;
            QTUtils.showDialogbox(this, promocodeDetails.message);
        }

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
        QTUtils.showProgressDialog(EventBookingSummaryQT5Activity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, EventBookingSummaryQT5Activity.this, message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 002) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    public boolean isValidMobile(String str) {
        boolean isValid = false;
        if (str.length() == 8) isValid = Patterns.PHONE.matcher(str).matches();
        return isValid;
    }

    String EMAIL_PATTERN = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$";

    public final boolean validateEmail(String target) {
        if (target != null && target.length() > 1) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(target);
            return matcher.matches();
        } else if (target.length() == 0) {
            return false;
        } else {
            return false;
        }
    }


    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public boolean isValidEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}