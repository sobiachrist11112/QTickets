package com.production.qtickets.eventBookingSummaryQT5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.eventBookingPaymentSummaryQT5.EventBookingPaymentSummaryQT5Activity;
import com.production.qtickets.login.LoginActivity;
import com.production.qtickets.model.CouponQT5Data;
import com.production.qtickets.model.CouponQT5Model;
import com.production.qtickets.model.CuponCodeModel;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.PromoCode;
import com.production.qtickets.signup.SignUpActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class EventBookingSummaryQT5Activity extends AppCompatActivity implements View.OnClickListener, EventBookingSummaryQT5Contracter.View {

    EventBookingSummaryQT5Presenter presenter;
    int TotalTicketCount = 0;
    Double TotalTicketAmount = 0.0;
    Double TotalServiceAmount = 0.0;
    List<EventTicketQT5Type> typeList;
    int EventDateID = 0;
    String EventDate = "";
    String EventName = "";
    String EventVenue = "";
    String CountryCode = "";
    String eventTime = "";
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

    TextView tv_event_name, tv_date_time, tv_venue, event_name, tv_sub_total, tv_service_charge, tv_total_amt, coupon_discount, tv_coupon_discount;
    EditText et_coupon, et_name, et_email, et_phone_no;
    Button btn_apply, btn_book_now, btn_remove;
    ImageView btn_back;
    LinearLayout lay_login_user_option, lay_login, lay_continue_as_guest, lay_ticket_type_amt, lay_ticket_type, lay_guest_user_info;

    Spinner spinner_countrydropdown;
    ArrayAdapter<String> adapter;

    SessionManager sessionManager;
    private int position;
    String country;

    RadioButton rb_couponcode, rb_promocode;
    String isPromoorcupon = "";
    private PhoneNumberUtil util = null;
    private double couponAmount = 0;
    private boolean IS_EVENT_OFFLINE = false;
    private int viewCount = 0, totalViewCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_booking_summary_qt5);
        presenter = new EventBookingSummaryQT5Presenter();
        presenter.attachView(this);
        sessionManager = new SessionManager(EventBookingSummaryQT5Activity.this);
        TotalTicketCount = getIntent().getIntExtra(AppConstants.NO_OF_TICKETS, 0);
        TotalTicketAmount = getIntent().getDoubleExtra(AppConstants.TOTAL_AMT, 0.0);
        TotalServiceAmount = getIntent().getDoubleExtra(AppConstants.SERVICE_CHARGE, 0.0);
        EventID = getIntent().getIntExtra(AppConstants.EVENT_ID, 0);
        IS_EVENT_OFFLINE = getIntent().getBooleanExtra(AppConstants.IS_EVENT_OFFLINE, false);
        EventDateID = getIntent().getIntExtra(AppConstants.EVENT_DATE_ID, 0);
        EventDate = getIntent().getStringExtra(AppConstants.EVENT_DATE);
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        EventName = getIntent().getStringExtra(AppConstants.EVENT_NAME);
        EventVenue = getIntent().getStringExtra(AppConstants.EVENT_VENUE);
        CountryCode = getIntent().getStringExtra(AppConstants.COUNTRY_TYPE_CURRENCY);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);
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

        spinner_countrydropdown = (Spinner) findViewById(R.id.spinner_countrydropdown);
        coupon_discount = (TextView) findViewById(R.id.coupon_discount);
        tv_coupon_discount = (TextView) findViewById(R.id.tv_coupon_discount);
        tv_event_name = (TextView) findViewById(R.id.tv_event_name);
        tv_date_time = (TextView) findViewById(R.id.tv_date_time);
        tv_venue = (TextView) findViewById(R.id.tv_venue);
        event_name = (TextView) findViewById(R.id.event_name);
        tv_sub_total = (TextView) findViewById(R.id.tv_sub_total);
        tv_service_charge = (TextView) findViewById(R.id.tv_service_charge);
        tv_total_amt = (TextView) findViewById(R.id.tv_total_amt);

        et_coupon = (EditText) findViewById(R.id.et_coupon);
        et_name = (EditText) findViewById(R.id.et_name);
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

        tv_event_name.setText("" + EventName);
        event_name.setText("" + EventName);
        tv_venue.setText("" + EventVenue);

        String[] strDate = EventDate.split("T");
        if (eventTime != null && !eventTime.equals("") && !eventTime.equals("null")) {
            tv_date_time.setText("" + DateTimeUtils.getEventFullDate(strDate[0]) + " | " + eventTime);
        } else {
            tv_date_time.setText("" + DateTimeUtils.getEventFullDate(strDate[0]));
        }

        tv_sub_total.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
        tv_service_charge.setText("" + String.format("%.2f", TotalServiceAmount) + " " + CountryCode);

        int IntValue = (int) Math.round(TotalTicketAmount + TotalServiceAmount);
        tv_total_amt.setText("" + IntValue + ".00 " + CountryCode);

        if (lay_ticket_type != null) {
            lay_ticket_type.removeAllViews();
        }
        // get size of views which gonna add...

        for (EventTicketQT5Type setTicketType : typeList) {
            setTicketType(setTicketType);
        }
        bindCountrySpinner();
        rb_promocode.setOnCheckedChangeListener(new Radio_check());
        rb_couponcode.setOnCheckedChangeListener(new Radio_check());
    }


    class Radio_check implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (buttonView.getId() == R.id.rb_promocode) {
                if (rb_promocode.isChecked()) {
                    isPromoorcupon = AppConstants.IS_PROMO_COUPON;
                }
            }

            if (buttonView.getId() == R.id.rb_couponcode) {
                if (rb_couponcode.isChecked()) {
                    isPromoorcupon = AppConstants.IS_COUPON_CODE;
                }
            }
            et_coupon.setText("");
            btn_remove.setVisibility(View.GONE);
            btn_apply.setVisibility(View.VISIBLE);
            TotalTicketAmount = TotalTicketAmount + couponAmount;
            couponAmount = 0;

            int IntValue = (int) Math.round(TotalTicketAmount + TotalServiceAmount);
            tv_total_amt.setText("" + IntValue + ".00 " + CountryCode);

            //    tv_total_amt.setText("" + String.format("%.2f", TotalTicketAmount + TotalServiceAmount) + " " + CountryCode);


            tv_coupon_discount.setVisibility(View.GONE);
            coupon_discount.setVisibility(View.GONE);
//            if (rb_couponcode.isChecked()) {
//                et_coupon.setText("");
//                btn_remove.setVisibility(View.GONE);
//                btn_apply.setVisibility(View.VISIBLE);
//                TotalTicketAmount = TotalTicketAmount + couponAmount;
//                couponAmount = 0;
//                tv_total_amt.setText("" + String.format("%.2f", TotalTicketAmount + TotalServiceAmount) + " " + CountryCode);
//                tv_coupon_discount.setVisibility(View.GONE);
//                coupon_discount.setVisibility(View.GONE);
//                isPromoorcupon = AppConstants.IS_COUPON_CODE;
//                //gender.setText("Gender: Male");
//            } else if (rb_promocode.isChecked()) {
//                isPromoorcupon = AppConstants.IS_PROMO_COUPON;
//                //gender.setText("Gender: Female");
//            }
        }
    }

    //bind the spinner for country flags
    private void bindCountrySpinner() {

        // Creating adapter for spinner

        int positin = -1;

        String countrycode[] = AppConstants.country_code;
        for (int i = 0; i < countrycode.length; i++) {
            if (sessionManager.getPrefix().equals(countrycode[i])) {
                positin = i;
            }
        }
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter, AppConstants.country_code, AppConstants.flags, true, positin);
        spinner_countrydropdown.setAdapter(adapter);
        sessionManager.setSelectedCountryCode("+" + sessionManager.getPrefix());


        // attaching data adapter to spinner
        spinner_countrydropdown.setAdapter(adapter);
        spinner_countrydropdown.setSelection(positin);

        spinner_countrydropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                position = i;
                // On selecting a spinner item
                country = mCountryCodes.get(position);

                // Showing selected spinner item
                if (country.length() >= 4) {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                } else if (country.length() >= 3) {
                    et_phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

                }
                //Toast.makeText(parent.getContext(), "Selected: " + country, Toast.LENGTH_LONG).show();
                sessionManager.setPrefix(country);
                if (sessionManager.getSelectedCountryCode().equals(country)) {
                    sessionManager.setSelectedCountryCode(country);
                } else {
                    et_phone_no.setText("");
                    sessionManager.setSelectedCountryCode(country);
                }

                Log.v("countryCode = ", "id" + country);
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
            isGuestUser = false;
        } else {
            lay_guest_user_info.setVisibility(View.VISIBLE);
            lay_login_user_option.setVisibility(View.VISIBLE);
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
                jsonObject.put("ticketPrice", type.price);
                jsonArray.put(jsonObject);
                // for verification json object for booking
                JSONObject jsonfroverify = new JSONObject();
                jsonfroverify.put("ticketId", type.id);
                if (type.chargeType > 0) {
                    if (type.chargeType == 1) {
                        jsonfroverify.put("price", type.price + type.serviceCharge);
                    }
                    if (type.chargeType == 2) {
                        jsonfroverify.put("price", type.price + (type.price * type.serviceCharge) / 100);
                    }
                } else {
                    jsonfroverify.put("price", type.price);
                }
                jsonfroverify.put("ticketName", type.ticketName);
                jsonfroverify.put("quantity", type.quantity);
                jsonArrayForverify.put(jsonfroverify);
                String ticketXML = "" + jsonArray.toString();
                String strTickets = "";
                if (type.quantity > 1) {
                    strTickets = type.ticketName + " : " + type.quantity + " Tickets";
                } else {
                    strTickets = type.ticketName + " : " + type.quantity + " Ticket";
                }

                Double _Price = 0.0;
                if (type.discount > 0) {
                    if (type.discountType == 1) {
                        _Price = type.quantity * (type.price - type.discount);
                    } else if (type.discountType == 2) {
                        Double dis = (type.price * type.discount) / 100;
                        _Price = type.quantity * (type.price - dis);
                    }
                } else {
                    _Price = type.quantity * type.price;
                }
                // lay_ticket_type_amt.addView(addViewTicketAmt("" + String.format("%.2f", _Price) + " " + CountryCode));
                lay_ticket_type.addView(addViewTicketType(strTickets, "" + String.format("%.2f", _Price) + " " + CountryCode));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public View addViewTicketType(String str, String amount) {
        View v=null;

        if (viewCount == 1) {
            v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_typewithtoppadding, null, false);
            Log.d("Count:"+" EQUAL",String.valueOf(viewCount)+ str );
        } else {
            v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_type, null, false);
            Log.d("Count:"+"NOT EQUAL",String.valueOf(viewCount)+ str);

        }

        TextView tv = v.findViewById(R.id.tv_ticket_view);
        LinearLayout llone = v.findViewById(R.id.llone);

        if (viewCount == 1) {

            llone.setBackground(getResources().getDrawable(R.drawable.lightgrey_with_topcorners));
            Log.d("Count:"+" EQUAL",String.valueOf(viewCount)+ str );
        } else {

            llone.setBackground(getResources().getDrawable(R.drawable.lightgrey));
            Log.d("Count:"+"NOT EQUAL",String.valueOf(viewCount)+ str);

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


            case R.id.btn_remove:
                et_coupon.setText("");
                btn_remove.setVisibility(View.GONE);
                btn_apply.setVisibility(View.VISIBLE);
                TotalTicketAmount = TotalTicketAmount + couponAmount;
                //    tv_total_amt.setText("" + String.format("%.2f", TotalTicketAmount + TotalServiceAmount) + " " + CountryCode);

                int IntValue = (int) Math.round(TotalTicketAmount + TotalServiceAmount);
                tv_total_amt.setText("" + IntValue + ".00 " + CountryCode);

                tv_coupon_discount.setVisibility(View.GONE);
                coupon_discount.setVisibility(View.GONE);
                break;


            case R.id.btn_book_now: {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("orderDate", "" + EventDate);
                    jsonObject.put("countryId", "" + new SessionManager(getApplicationContext()).getCountryID());
                    jsonObject.put("totalAmt", "" + (TotalTicketAmount + TotalServiceAmount));
                    jsonObject.put("eventId", "" + EventID);
                    jsonObject.put("eventName", "" + EventName);
                    jsonObject.put("venueName", "" + EventVenue);
                    jsonObject.put("dateOfBirth", "");
                    jsonObject.put("resident", "");
                    jsonObject.put("createdIP", "");

                    // verify booking confirmation
                    JSONObject jsonObjectforverfiying = new JSONObject();
                    jsonObjectforverfiying.put("eventId", "" + EventID);
                    int eventoffline = 0;
                    if (IS_EVENT_OFFLINE) {
                        eventoffline = 1;
                    } else {
                        eventoffline = 0;
                    }
                    jsonObjectforverfiying.put("eventIsOffline", eventoffline);
                    jsonObjectforverfiying.put("bookingDate", "" + EventDate);
                    if (eventTime != null) {
                        String[] starttime = eventTime.split("-");
                        jsonObjectforverfiying.put("bookingStartTime", "" + starttime[0]);
                    } else {
                        jsonObjectforverfiying.put("bookingStartTime", "");
                    }
                    jsonObjectforverfiying.put("bookingTickets", jsonArrayForverify.toString());
                    //     jsonObjectforverfiying.put("couponCode", "");
                    jsonObjectforverfiying.put("totalPrice", (TotalTicketAmount + TotalServiceAmount));


                    JSONArray jsonArray = new JSONArray();
                    for (EventTicketQT5Type type : typeList) {
                        if (type.quantity > 0) {
                            JSONObject jsonObjectTicket = new JSONObject();
                            if (type.discountType == 1) {
                                jsonObjectTicket.put("EBDiscountTotalValue", +(type.price - type.discount));
                            } else if (type.discountType == 2) {
                                Double disc = (type.price * type.discount) / 100;
                                jsonObjectTicket.put("EBDiscountTotalValue", +(type.price - disc));
                            } else {
                                jsonObjectTicket.put("EBDiscountTotalValue", "0");
                            }
                            jsonObjectTicket.put("EBDiscountAmount", +type.discount);
                            jsonObjectTicket.put("EBDiscountType", +type.discountType);
                            jsonObjectTicket.put("ServiceType", +type.chargeType);
                            jsonObjectTicket.put("CategoryId", +type.categoryId);
                            jsonObjectTicket.put("TicketId", +type.id);
                            jsonObjectTicket.put("ServiceValue", +(type.serviceCharge * type.quantity));
                            jsonObjectTicket.put("Quantity", +type.quantity);
                            jsonObjectTicket.put("Price", +type.price);
                            jsonObjectTicket.put("ServiceCharge", +type.serviceCharge);
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
                            jsonObject.put("voucherName", "");
                            jsonObject.put("voucherAmount", 0);
                        } else {
                            jsonObject.put("promocodeName", "");
                            jsonObject.put("promocodeType", 0);
                            jsonObject.put("promoCodeDisType", 0);
                            jsonObject.put("promoCodeDisValue", 0);
                            jsonObject.put("promoCodeActualValue", 0);
                            jsonObject.put("voucherName", "");
                            jsonObject.put("voucherAmount", 0);
                        }

                    }

                    jsonObject.put("orderTime", "" + eventTime);
                    jsonObject.put("paymentMode", "0");
                    jsonObject.put("admit", "0");

                    if (isGuestUser) { //todo 29sep
                        if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.pleaseentefields));
                            return;
                        }
                        if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.pleaseentefields));
                            return;
                        }
                        if (!isValidEmail(et_email.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please enter valid Email ID");
                            return;

                        }
                        if (TextUtils.isEmpty(et_phone_no.getText().toString().trim())) {
                            QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, "Please Enter Phone Number");
                            return;
                        }
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
                        jsonObject.put("primaryContactNo", "" + sessionManager.getPrefix() + " " + et_phone_no.getText().toString());
                        jsonObject.put("primaryEmail", "" + et_email.getText().toString());
                        jsonObject.put("primaryUserName", "" + et_name.getText().toString());
                        jsonObject.put("primaryUserId", "" + 0);
                        jsonObject.put("createdBy", "" + 0);
                    } else {
                        jsonObject.put("primaryContactNo", "" + sessionManager.getPrefix() + " " + sessionManager.getNumber());
                        jsonObject.put("primaryEmail", "" + sessionManager.getEmail());
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
                                arrayForm.put(new JSONObject().put("AddForm_" + type.id, jsonArrayData));
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
                    intent.putExtra(AppConstants.EVENT_DATE_ID, EventDateID);
                    intent.putExtra(AppConstants.EVENT_DATE, EventDate);
                    intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                    intent.putExtra(AppConstants.TOTAL_AMT, (TotalTicketAmount + TotalServiceAmount));
                    intent.putExtra(AppConstants.NO_OF_TICKETS, TotalTicketCount);
                    intent.putExtra(AppConstants.EVENT_NAME, EventName);
                    intent.putExtra(AppConstants.EVENT_VENUE, EventVenue);
                    intent.putExtra(AppConstants.EVENT_ID, EventID);
                    intent.putExtra(AppConstants.IS_GUEST, isGuestUser);
                    intent.putExtra(AppConstants.GUEST_NAME, "" + et_name.getText().toString());
                    intent.putExtra(AppConstants.GUEST_EMAIL, "" + et_email.getText().toString());
                    intent.putExtra(AppConstants.GUEST_PHONE, sessionManager.getPrefix() + et_phone_no.getText().toString());
                    intent.putExtra(AppConstants.IS_COUPON, isCoupon);
                    intent.putExtra(AppConstants.COUPON_CODE, "" + et_coupon.getText().toString());
                    intent.putExtra(AppConstants.COUNTRY_TYPE_CURRENCY, CountryCode);
                    intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                    intent.putExtra(AppConstants.TICKET_XML, jsonObject.toString());
                    intent.putExtra(AppConstants.TICKET_VERIFY_XML, jsonObjectforverfiying.toString());
                    intent.putExtra("strdiff", "qtickets");
                    startActivityForResult(intent, 002);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            break;
            case R.id.lay_login: {
                lay_login.setBackgroundResource(R.drawable.selected_tab_with_corners);
                lay_continue_as_guest.setBackground(null);
                lay_guest_user_info.setVisibility(View.GONE);
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                isGuestUser = false;
            }
            break;
            case R.id.lay_continue_as_guest: {
                lay_continue_as_guest.setBackgroundResource(R.drawable.selected_tab_with_corners);
                lay_login.setBackground(null);
                lay_guest_user_info.setVisibility(View.VISIBLE);
                isGuestUser = true;
            }
            break;
            case R.id.btn_apply: {
//                if (isCouponApplied){
//                    QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.a_copon_code_su));
//                    return;
//                }
                if (TextUtils.isEmpty(et_coupon.getText().toString().trim())) {
                    QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.e_code));
                    return;
                }
                if (!isPromoorcupon.equals("")) {
                    if (isPromoorcupon.equals(AppConstants.IS_PROMO_COUPON)) {
                        presenter.applyPromoCOde(EventID, et_coupon.getText().toString(), jsonArray.toString());
                        et_coupon.setText("");
                    }
                    if (isPromoorcupon.equals(AppConstants.IS_COUPON_CODE)) {
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
                        presenter.applyCoupon(String.valueOf(EventID), et_coupon.getText().toString(), email, String.format("%.2f", TotalTicketAmount + TotalServiceAmount));
                        // et_coupon.setText("");
                    }
                } else {
                    QTUtils.showDialogbox(EventBookingSummaryQT5Activity.this, getResources().getString(R.string.a_copon_code_su));
                    return;

                }


            }
            break;
        }
    }

    @Override
    public void setCouponDetail(CuponCodeModel couponQT5Model) {

        if (couponQT5Model.statusCode == 200) {
            showToast(couponQT5Model.message);
            tv_coupon_discount.setVisibility(View.VISIBLE);
            coupon_discount.setVisibility(View.VISIBLE);
            couponAmount = couponQT5Model.data.voucherAmount;
            TotalTicketAmount = TotalTicketAmount - couponQT5Model.data.voucherAmount;
            //     tv_total_amt.setText("" + String.format("%.2f", TotalTicketAmount + TotalServiceAmount) + " " + CountryCode);

            int IntValue = (int) Math.round(TotalTicketAmount + TotalServiceAmount);
            tv_total_amt.setText("" + IntValue + ".00 " + CountryCode);

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
            showToast(promocodeDetails.message);
            tv_coupon_discount.setVisibility(View.VISIBLE);
            coupon_discount.setVisibility(View.VISIBLE);
            couponAmount = promocodeDetails.data.discount;
            TotalTicketAmount = TotalTicketAmount - promocodeDetails.data.discount;
            int IntValue = (int) Math.round(TotalTicketAmount + TotalServiceAmount);
            tv_total_amt.setText("" + IntValue + ".00 " + CountryCode);

            //       tv_total_amt.setText("" + String.format("%.2f", TotalTicketAmount + TotalServiceAmount) + " " + CountryCode);
            coupon_discount.setText("" + String.format("%.2f", Double.valueOf(promocodeDetails.data.discount)) + " " + CountryCode);
            //coupon_discount.setText("" + QTUtils.parseDouble(Double.valueOf(couponQT5Model.data.discount)) + " " + CountryCode);
            btn_apply.setVisibility(View.GONE);
            btn_remove.setVisibility(View.VISIBLE);

//            if(couponQT5Model.data.discountType == 1){
//                TotalTicketAmount = TotalTicketAmount - couponQT5Model.data.discount;
//                tv_total_amt.setText("" + QTUtils.parseDouble((TotalTicketAmount + TotalServiceAmount))+ " " + CountryCode);
//                coupon_discount.setText("" + QTUtils.parseDouble(couponQT5Model.data.discount) + " " + CountryCode);
//
//            }else if(couponQT5Model.data.discountType == 2) {
//                Double dis = ((TotalTicketAmount + TotalServiceAmount) * couponQT5Model.data.discount)/100;
//                TotalTicketAmount = TotalTicketAmount - dis;
//                tv_total_amt.setText("" + QTUtils.parseDouble((TotalTicketAmount + TotalServiceAmount)) + " " + CountryCode);
//                coupon_discount.setText("" + QTUtils.parseDouble(couponQT5Model.data.discount) + "%" );
//            }
            //      couponQT5Data = couponQT5Model.data;
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

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public boolean isValidEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
}