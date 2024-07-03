package com.production.qtickets.eventBookingPaymentSummaryQT5;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.production.qtickets.R;
import com.production.qtickets.eventBookingConfirmQT5.EventBookingConfirmQT5Activity;
import com.production.qtickets.eventBookingDetailQT5.EventBookingDetailQT5Activity;
import com.production.qtickets.events.PaymentWebView;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.BookingQT5Model;
import com.production.qtickets.model.CreateDTCMUserModel;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.VerifyBookingDetail;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EventBookingPaymentSummaryQT5Activity extends AppCompatActivity implements View.OnClickListener, EventBookingPaymentSummaryQT5Contracter.View {

    EventBookingPaymentSummaryQT5Presenter presenter;
    int TotalTicketCount = 0;
    Double TotalTicketAmount = 0.0;
    List<EventTicketQT5Type> typeList;
    int EventDateID = 0;
    String EventDate = "";
    String EventName = "";
    String GuestName = "";
    String GuestEmail = "";
    String GuestPhoneNo = "";
    String CouponCode = "";
    String EventVenue = "";
    String CountryCode = "";
    String eventTime = "";
    String TICKET_XML = "";
    String TICKET_VERIFY_XML = "";
    int EventID = 0;

    boolean isGuestUser = false;
    boolean isCoupon = false;

    TextView tv_event_name, tv_date_time, tv_venue, event_name, tv_ticket, tv_total_amt;
    LinearLayout lay_ticket_type;
    ImageView btn_back;
    Button btn_book_now;
    Spinner spin_nationality;

    //Payment Selection method :
    LinearLayout ll_magnite, ll_visacard, ll_americanexpress, ll_napscard, ll_ooreedo, ll_payfort, ll_naps, ll_amexpayment, ll_ipay;
    ImageView iv_tick1, iv_tick2, iv_tick3, iv_tick4, iv_tickpayfort;
    private int payment_Type = 0;
    String paymentMainUrl, str_differ;
    SessionManager sessionManager;
    String CURRENCY = "";
    private String type = "", cid = "";
    boolean isPayFord = false;

    boolean isMagnite = false;
    boolean isNaps = false;

    TextView tv_paymenttittle;
    boolean isSelectedItem = false;
    String paymenttype = "";
    private boolean isDaypass = false;
    private boolean isDTCM = false;

    BookingQT5Model bookingQT5Models;

    String url = "";

    String residence_code = "";

    String eventEndDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_booking_payment_summary_qt5);
        presenter = new EventBookingPaymentSummaryQT5Presenter();
        presenter.attachView(this);

        sessionManager = new SessionManager(this);
        CURRENCY = sessionManager.getCountryCurrency();

        residence_code = getIntent().getStringExtra(AppConstants.RESIDENCE_CODE);
        TotalTicketCount = getIntent().getIntExtra(AppConstants.NO_OF_TICKETS, 0);
        TotalTicketAmount = getIntent().getDoubleExtra(AppConstants.TOTAL_AMT, 0.0);
        isCoupon = getIntent().getBooleanExtra(AppConstants.IS_COUPON, false);
        isGuestUser = getIntent().getBooleanExtra(AppConstants.IS_GUEST, false);
        EventID = getIntent().getIntExtra(AppConstants.EVENT_ID, 0);
        isDTCM = getIntent().getBooleanExtra(AppConstants.IS_DTCM_EVENT, false);
        isDaypass = getIntent().getBooleanExtra(AppConstants.IS_DAY_PASS, false);
        EventDateID = getIntent().getIntExtra(AppConstants.EVENT_DATE_ID, 0);
        eventEndDate = getIntent().getStringExtra(AppConstants.END_EVENT_DATE);
        EventDate = getIntent().getStringExtra(AppConstants.EVENT_DATE);
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        EventName = getIntent().getStringExtra(AppConstants.EVENT_NAME);
        EventVenue = getIntent().getStringExtra(AppConstants.EVENT_VENUE);
        CountryCode = getIntent().getStringExtra(AppConstants.COUNTRY_TYPE_CURRENCY);
        GuestName = getIntent().getStringExtra(AppConstants.GUEST_NAME);
        if (!getIntent().getStringExtra(AppConstants.GUEST_EMAIL).equals("")) {
            GuestEmail = getIntent().getStringExtra(AppConstants.GUEST_EMAIL);
        } else {
            GuestEmail = sessionManager.getEmail();
        }
        GuestPhoneNo = getIntent().getStringExtra(AppConstants.GUEST_PHONE);
        CouponCode = getIntent().getStringExtra(AppConstants.COUPON_CODE);
        TICKET_XML = getIntent().getStringExtra(AppConstants.TICKET_XML);
        TICKET_VERIFY_XML = getIntent().getStringExtra(AppConstants.TICKET_VERIFY_XML);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);

        str_differ = getIntent().getStringExtra("strdiff");


        spin_nationality = (Spinner) findViewById(R.id.spin_nationality);
        tv_event_name = (TextView) findViewById(R.id.tv_event_name);
        tv_date_time = (TextView) findViewById(R.id.tv_date_time);
        tv_venue = (TextView) findViewById(R.id.tv_venue);
        event_name = (TextView) findViewById(R.id.event_name);
        tv_ticket = (TextView) findViewById(R.id.tv_ticket);
        tv_total_amt = (TextView) findViewById(R.id.tv_total_amt);

        lay_ticket_type = (LinearLayout) findViewById(R.id.lay_ticket_type);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_book_now = (Button) findViewById(R.id.btn_book_now);


        tv_paymenttittle = (TextView) findViewById(R.id.tv_paymenttittle);


        ll_ipay = (LinearLayout) findViewById(R.id.ll_ipay);
        ll_visacard = (LinearLayout) findViewById(R.id.ll_visacard);
        ll_payfort = (LinearLayout) findViewById(R.id.ll_payfort);
        ll_magnite = (LinearLayout) findViewById(R.id.ll_magnite);

        ll_naps = (LinearLayout) findViewById(R.id.ll_naps);
        //     ll_amexpayment = (LinearLayout) findViewById(R.id.ll_amexpayment);
        iv_tick1 = (ImageView) findViewById(R.id.iv_tick1);
        btn_back.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);

        ll_magnite.setOnClickListener(this);
        ll_ipay.setOnClickListener(this);
        ll_visacard.setOnClickListener(this);
        ll_payfort.setOnClickListener(this);
        ll_naps.setOnClickListener(this);
        //      ll_amexpayment.setOnClickListener(this);

        tv_event_name.setText("" + EventName);
        event_name.setText("" + EventName);
//        tv_venue.setText("" + EventVenue);


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

        Log.d("23oct", "TotalTicketAmount " + TotalTicketAmount);

//        int IntValue = (int) Math.round(TotalTicketAmount);

        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
        String formattedAmount = "Total: " + df.format(TotalTicketAmount) + " " + CountryCode;
        tv_total_amt.setText(formattedAmount);

//        tv_total_amt.setText("Total: " + TotalTicketAmount + CountryCode);

        if (TotalTicketAmount > 0) {
            btn_book_now.setText("PAY  NOW");
        } else {
            btn_book_now.setText("BOOK NOW");
        }

        for (EventTicketQT5Type setTicketType : typeList) {
            setTicketType(setTicketType);
        }

        String country = sessionManager.getCountryName();


        if (country.equals("Qatar")) {
            ll_payfort.setVisibility(View.GONE);
            ll_visacard.setVisibility(View.VISIBLE);

            // i pay visible for Qatar
            ll_ipay.setVisibility(View.VISIBLE);
            ll_magnite.setVisibility(View.GONE);

            ll_naps.setVisibility(View.VISIBLE);
            paymenttype = "visa";
            isMagnite = false;
            isPayFord = false;
            isNaps = false;
            ll_magnite.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_ipay.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_visacard.setBackgroundResource(R.drawable.selected_button_with_corners);
            ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
        } else {

            if (APIClient.BASE_URL_QT5_EVENT.equalsIgnoreCase("https://apie.q-tickets.com/"))
                ll_magnite.setVisibility(View.GONE);

            else
                ll_magnite.setVisibility(View.VISIBLE);
            ll_payfort.setVisibility(View.VISIBLE);
            ll_visacard.setVisibility(View.GONE);
            ll_ipay.setVisibility(View.GONE);
            ll_naps.setVisibility(View.GONE);
            //   ll_amexpayment.setVisibility(View.GONE);
            paymenttype = "payfort";
            isPayFord = true;
            isMagnite = true;
            ll_payfort.setBackgroundResource(R.drawable.selected_button_with_corners);
            ll_magnite.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_ipay.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
        }
        presenter.getQT5countryDropdown();

    }

    public void setTicketType(EventTicketQT5Type type) {
        if (type.quantity > 0) {
            String strTickets = "";
            String category = "";


            if (type.category != null && !type.category.isEmpty()) {
                category = type.category;
            } else {
                category = "";
            }

            if (type.quantity > 1) {
                if (category.equals("")) {
                    tv_ticket.setText("Tickets");

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
                tv_ticket.setText("Ticket");
                if (category.equals("")) {

                    if (type.discount > 0) {
                        strTickets = strTickets = type.ticketName + " (Early Bird)" + "  : " + type.quantity + " Ticket";
                    } else {
                        strTickets = strTickets = type.ticketName + "  : " + type.quantity + " Ticket";
                    }

                } else {
                    if (type.discount > 0) {
                        strTickets = type.ticketName + " (Early Bird)" + "  (" + type.category + ")  : " + type.quantity + " Ticket";
                    } else {
                        strTickets = type.ticketName + "  (" + type.category + ")  : " + type.quantity + " Ticket";
                    }
                }
            }
            lay_ticket_type.addView(addViewTicketType(strTickets));

        }
    }

    public View addViewTicketType(String str) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_type_again, null, false);
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
            case R.id.btn_book_now: {
                try {
                    if (spin_nationality.getSelectedItemPosition() == 0) {
                        QTUtils.showDialogbox(EventBookingPaymentSummaryQT5Activity.this, "Please Select Nationality");
                        return;
                    }
                    if (paymenttype.equals("")) {
                        QTUtils.showDialogbox(EventBookingPaymentSummaryQT5Activity.this, "Please Select The Payment Method");
                        return;
                    }
//                   if (payment_Type == 0){
//                       QTUtils.showDialogbox(EventBookingPaymentSummaryQT5Activity.this, "Please Select the payment");
//                       return;shu
//                   }
                    JSONObject jsonObject = new JSONObject(TICKET_XML);
                    jsonObject.put("nationality", spin_nationality.getSelectedItem().toString());

                    presenter.verifyBooking(TICKET_VERIFY_XML, jsonObject.toString());
                    Log.d("shubverifyApi :", jsonObject.toString());

//                String sTICKET_XML = TICKET_XML;
                    //QTUtils.showProgressDialog(this,true);
//                   presenter.BookTickets(jsonObject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;


//            case R.id.ll_amexpayment:
//
//                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
//                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
//                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
//
//                ll_amexpayment.setBackgroundResource(R.drawable.selected_button_with_corners);
//                isPayFord = false;
//                isNaps = false;
//                paymenttype = "amex";
//
//                break;


            case R.id.ll_magnite:
                isMagnite = true;
                ll_magnite.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_ipay.setBackgroundResource(R.drawable.unselected_button_with_corners);
                //     ll_amexpayment.setBackgroundResource(R.drawable.unselected_button_with_corners);

                paymenttype = "magnite";
                break;


            case R.id.ll_ipay:
                ll_magnite.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_ipay.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                //ll_amexpayment.setBackgroundResource(R.drawable.unselected_button_with_corners);
                isPayFord = false;
                isNaps = false;
                isMagnite = false;
                paymenttype = "ipay";

//              iv_tick2.setVisibility(View.GONE);
//              iv_tick3.setVisibility(View.GONE);
//              iv_tick4.setVisibility(View.GONE);
//              payment_Type = 4;

                break;

            case R.id.ll_visacard:
                ll_visacard.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_ipay.setBackgroundResource(R.drawable.unselected_button_with_corners);
                //     ll_amexpayment.setBackgroundResource(R.drawable.unselected_button_with_corners);
                isPayFord = false;
                isNaps = false;
                isMagnite = false;
                paymenttype = "visa";
//                iv_tick2.setVisibility(View.GONE);
//                iv_tick3.setVisibility(View.GONE);
//                iv_tick4.setVisibility(View.GONE);
//                payment_Type = 4;

                break;


            case R.id.ll_payfort:
                isPayFord = true;
                isMagnite = false;
                ll_magnite.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_ipay.setBackgroundResource(R.drawable.unselected_button_with_corners);
                //     ll_amexpayment.setBackgroundResource(R.drawable.unselected_button_with_corners);


                paymenttype = "payfort";
//                iv_tick1.setVisibility(View.VISIBLE);
//                iv_tick2.setVisibility(View.GONE);
//                iv_tick3.setVisibility(View.GONE);
//                iv_tick4.setVisibility(View.GONE);
//                payment_Type = 4;

                break;

            case R.id.ll_naps:
                isNaps = true;
                isMagnite = false;
                paymenttype = "naps";
                ll_magnite.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_ipay.setBackgroundResource(R.drawable.unselected_button_with_corners);
                //       ll_amexpayment.setBackgroundResource(R.drawable.unselected_button_with_corners);

//                iv_tick1.setVisibility(View.VISIBLE);
//                iv_tick2.setVisibility(View.GONE);
//                iv_tick3.setVisibility(View.GONE);
//                iv_tick4.setVisibility(View.GONE);
//                payment_Type = 4;

                break;

        }
    }


    @Override
    public void setUserCreateDTCMData(CreateDTCMUserModel response) {

        if (response.isSuccess && response.data) {
            if (bookingQT5Models.data.totalAmt == 0) {
                Intent intent = new Intent(getApplicationContext(), EventBookingConfirmQT5Activity.class);
                intent.putExtra("DATA", bookingQT5Models.data);
                intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                startActivityForResult(intent, 003);
            } else {
                Bundle b = new Bundle();
                b.putString("webViewURL", url);
                Intent i = new Intent(this, PaymentWebView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("DATA", bookingQT5Models.data);
                i.putExtra("txt_title", "Payment");
                i.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                i.putExtra(AppConstants.EVENT_TIME, eventTime);
                i.putExtras(b);
                startActivity(i);
            }
        }
    }


    @Override
    public void BookTickets(BookingQT5Model bookingQT5Model, boolean isDTCM) {
        if (bookingQT5Model.statusCode.equals("200")) {
            bookingQT5Models = bookingQT5Model;
            if (isPayFord) {

                if (paymenttype.equals("payfort")) {
                    url = APIClient.PAYMENT_URL + "/Payment/PayFort?pkId=" + bookingQT5Model.data.pkId + "&orderId=" + bookingQT5Model.data.orderID + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt) + "&currency=" + sessionManager.getCountryCurrency() + "&email=" + GuestEmail.replace(" ", "_");

                } else if (paymenttype.equals("magnite")) {
                    url = APIClient.PAYMENT_URL + "/payment/magnati?pkid=" + bookingQT5Model.data.pkId + "&currency=" + sessionManager.getCountryCurrency();
                }

            } else {
                if (isNaps) {
                  url = APIClient.PAYMENT_URL + "/Payment/DebitCardNAPS?pkId=" + bookingQT5Model.data.pkId + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt);
//                url = "https://www.q-tickets.com/naps/qt5test.aspx?k1=SW2K63OWAxP3r8%2flLeSWjw%3d%3d&k2=wE4VltS9bL3oJbh81isuBg%3d%3d";
                } else if (paymenttype.equals("amex")) {
                    url = APIClient.PAYMENT_URL + "/Payment/AMEX?pkId=" + bookingQT5Model.data.pkId + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt);
                } else if (paymenttype.equals("ipay")) {
                    url = APIClient.PAYMENT_URL + "/Payment/IPay?pkId=" + bookingQT5Model.data.pkId;
                } else {
                    url = APIClient.PAYMENT_URL + "/Payment/DohaBank?pkId=" + bookingQT5Model.data.pkId + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt) + "&currency=QAR";
                }
            }

            TICKET_XML = getIntent().getStringExtra(AppConstants.TICKET_XML);
            TICKET_VERIFY_XML = getIntent().getStringExtra(AppConstants.TICKET_VERIFY_XML);

            if (isDTCM) {
                try {
                    JSONObject ticketxml = new JSONObject(TICKET_XML);

                    int ticketID = 0;
                    int quantity = 0;
                    // get the ticket ID from Verify xml data
                    JSONObject ticketdetails = new JSONObject(TICKET_VERIFY_XML);

                    String ticektsDetails = ticketdetails.getString("bookingTickets");
                    try {
                        JSONArray jsonArray = new JSONArray(ticektsDetails);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            ticketID = item.getInt("ticketId");
                            quantity = item.getInt("quantity");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Map<String, Object> jsonParams = new ArrayMap<>();
                    jsonParams.put("pkId", bookingQT5Model.data.pkId);
                    jsonParams.put("firstName", ticketxml.getString("primaryUserName"));
                    jsonParams.put("lastName", ticketxml.getString("primaryUserName"));
                    jsonParams.put("email", ticketxml.getString("primaryEmail"));
                    jsonParams.put("isd", sessionManager.getPrefix());


                    jsonParams.put("mobile", ticketxml.getString("primaryContactNo"));
                    jsonParams.put("dateOfBirth", ticketxml.getString("dateOfBirth"));
                    jsonParams.put("nationality", residence_code);


                    jsonParams.put("ticketId", ticketID);
                    jsonParams.put("ticketQuantity", quantity);
                    jsonParams.put("hasSeatLayout", false);
                    jsonParams.put("selectedSeats", "");
                    jsonParams.put("residence", residence_code);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(jsonParams));
                    presenter.createUserForDTCM(requestBody);

                } catch (Throwable t) {

                }
            } else {

                if (bookingQT5Model.data.totalAmt == 0) {
                    Intent intent = new Intent(getApplicationContext(), EventBookingConfirmQT5Activity.class);
                    intent.putExtra("DATA", bookingQT5Model.data);
                    intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                    intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                    startActivityForResult(intent, 003);
                } else {

                    Log.d("31Jan", "webViewURL " + url);

                    Bundle b = new Bundle();
                    b.putString("webViewURL", url);
                    Intent i = new Intent(this, PaymentWebView.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("DATA", bookingQT5Model.data);
                    i.putExtra("txt_title", "Payment");
                    i.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                    i.putExtra(AppConstants.EVENT_TIME, eventTime);
                    i.putExtras(b);
                    startActivity(i);
                }
            }


        } else {
            QTUtils.showDialogbox(EventBookingPaymentSummaryQT5Activity.this, "" + bookingQT5Model.message);
        }
    }

    @Override
    public void setCountry(AllCountryQT5Model response) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setCountryDropdownQt5(GetNationalityData response) {
        if (response.statusCode == 200) {
            if (response.data != null && !response.data.isEmpty()) {
                bindCountrySpinner(response);
            }
        }
    }

    @Override
    public void setVerifyBooking(VerifyBookingDetail response, String bookingxml) {
        if (response.isSuccess && response.data) {
            try {
                JSONObject convertedObject = new JSONObject(bookingxml);
                convertedObject.put("WithUpdatedData", false);
                convertedObject.put("OrderFrom", "Android_PlayStore");
                convertedObject.put("isDayPass", isDaypass);
                presenter.BookTickets(convertedObject.toString(), isDTCM);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }

        } else if (!response.isSuccess && response.data) {
            showPricechangedDialogbox(this, response.message, bookingxml);
        } else {
            QTUtils.showDialogbox(this, response.message);
        }


        //    isSuccess == 1 & data == 1 -> WithUpdatedData = false
        //     isSuccess == 0 & data == 1 -> WithUpdatedData = true


    }


    public void showPricechangedDialogbox(Context context, String message, String bookingxml) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Alert");
        builder.setCancelable(false);
        builder.setPositiveButton("Proceed", (DialogInterface.OnClickListener) (dialog, which) -> {
            try {
                JSONObject convertedObject = new JSONObject(bookingxml);
                convertedObject.put("WithUpdatedData", true);
                convertedObject.put("OrderFrom", "Android_PlayStore");
                presenter.BookTickets(convertedObject.toString(), isDTCM);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        });

        builder.setNegativeButton("Go Back", (DialogInterface.OnClickListener) (dialog, which) -> {
            /* try {
             *//*JSONObject event_details = new JSONObject(bookingxml);
                String event_id = event_details.getString("eventId");
                String event_name = event_details.getString("eventName");
                String event_venue = event_details.getString("venueName");
                Intent intent = new Intent(getApplicationContext(), EventBookingDetailQT5Activity.class);
                intent.putExtra(AppConstants.EVENT_ID, Integer.parseInt(event_id));
                intent.putExtra(AppConstants.EVENT_NAME, event_name);
                // intent.putExtra(AppConstants.IS_EVENT_OFFLINE, iseventIsOffline);
                intent.putExtra(AppConstants.EVENT_VENUE, event_venue);
                //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*//*


            } catch (JSONException exception) {
                exception.printStackTrace();
            }*/

            finish();
            dialog.cancel();

            // finish();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void bindCountrySpinner(GetNationalityData data) {
        ArrayList<String> countriesname = new ArrayList<>();
        if (data != null) {
            if (data.data.size() > 0) {
                countriesname.add("Select Nationality");
                for (int i = 0; i < data.data.size(); i++) {
                    countriesname.add(data.data.get(i).name);
                }
            }

        }
        ArrayAdapter<String> flagDashAdapter = new ArrayAdapter<String>(this, R.layout.country_spinner_item, countriesname);
        spin_nationality.setAdapter(flagDashAdapter);
    }

    @Override
    public void init() {

    }

    @Override
    public void dismissPb() {

    }

    @Override
    public void showPb() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 003) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}