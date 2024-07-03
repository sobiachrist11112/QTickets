package com.production.qtickets.eventBookingPaymentSummaryQT5;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.production.qtickets.R;
import com.production.qtickets.eventBookingConfirmQT5.EventBookingConfirmQT5Activity;
import com.production.qtickets.eventBookingDetailQT5.EventBookingDetailQT5Activity;
import com.production.qtickets.events.PaymentWebView;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.BookingQT5Model;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.VerifyBookingDetail;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    LinearLayout ll_visacard, ll_americanexpress, ll_napscard, ll_ooreedo, ll_payfort, ll_naps,ll_amexpayment;
    ImageView iv_tick1, iv_tick2, iv_tick3, iv_tick4, iv_tickpayfort;
    private int payment_Type = 0;
    String paymentMainUrl, str_differ;
    SessionManager sessionManager;
    String CURRENCY = "";
    private String type = "", cid = "";
    boolean isPayFord = false;
    boolean isNaps = false;

    TextView tv_paymenttittle;
    RelativeLayout rl_paymentoption;
    boolean isSelectedItem = false;
    String paymenttype = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_booking_payment_summary_qt5);
        presenter = new EventBookingPaymentSummaryQT5Presenter();
        presenter.attachView(this);

        sessionManager = new SessionManager(this);
        CURRENCY = sessionManager.getCountryCurrency();

        TotalTicketCount = getIntent().getIntExtra(AppConstants.NO_OF_TICKETS, 0);
        TotalTicketAmount = getIntent().getDoubleExtra(AppConstants.TOTAL_AMT, 0.0);
        isCoupon = getIntent().getBooleanExtra(AppConstants.IS_COUPON, false);
        isGuestUser = getIntent().getBooleanExtra(AppConstants.IS_GUEST, false);
        EventID = getIntent().getIntExtra(AppConstants.EVENT_ID, 0);
        EventDateID = getIntent().getIntExtra(AppConstants.EVENT_DATE_ID, 0);
        EventDate = getIntent().getStringExtra(AppConstants.EVENT_DATE);
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        EventName = getIntent().getStringExtra(AppConstants.EVENT_NAME);
        EventVenue = getIntent().getStringExtra(AppConstants.EVENT_VENUE);
        CountryCode = getIntent().getStringExtra(AppConstants.COUNTRY_TYPE_CURRENCY);
        GuestName = getIntent().getStringExtra(AppConstants.GUEST_NAME);
        if(!getIntent().getStringExtra(AppConstants.GUEST_EMAIL).equals("")){
            GuestEmail = getIntent().getStringExtra(AppConstants.GUEST_EMAIL);
        }else {
            GuestEmail=sessionManager.getEmail();
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
        rl_paymentoption = (RelativeLayout) findViewById(R.id.rl_paymentoption);

        //payment selection
        ll_visacard = (LinearLayout) findViewById(R.id.ll_visacard);
        ll_payfort = (LinearLayout) findViewById(R.id.ll_payfort);
        ll_naps = (LinearLayout) findViewById(R.id.ll_naps);
        ll_amexpayment = (LinearLayout) findViewById(R.id.ll_amexpayment);
//        ll_americanexpress = (LinearLayout) findViewById(R.id.ll_americanexpress);
//        ll_napscard = (LinearLayout) findViewById(R.id.ll_napscard);
//        ll_ooreedo = (LinearLayout) findViewById(R.id.ll_ooreedo);

        //yelllow button
        iv_tick1 = (ImageView) findViewById(R.id.iv_tick1);
//        iv_tick2=(ImageView)findViewById(R.id.iv_tick2) ;
//        iv_tick3=(ImageView)findViewById(R.id.iv_tick3) ;
//        iv_tick4=(ImageView)findViewById(R.id.iv_tick4) ;

        btn_back.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);

        ll_visacard.setOnClickListener(this);
        ll_payfort.setOnClickListener(this);
        ll_naps.setOnClickListener(this);
        ll_amexpayment.setOnClickListener(this);
//        ll_americanexpress.setOnClickListener(this);
//        ll_napscard.setOnClickListener(this);
//        ll_ooreedo.setOnClickListener(this);

        tv_event_name.setText("" + EventName);
        event_name.setText("" + EventName);
        tv_venue.setText("" + EventVenue);

        String[] strDate = EventDate.split("T");
        if (eventTime != null && !eventTime.equals("") && !eventTime.equals("null")) {
            tv_date_time.setText("" + DateTimeUtils.getEventFullDate(strDate[0]) + " | " + eventTime);
        } else {
            tv_date_time.setText("" + DateTimeUtils.getEventFullDate(strDate[0]));
        }

        tv_total_amt.setText("Total: " + QTUtils.parseDouble(TotalTicketAmount) + " " + CountryCode);

        for (EventTicketQT5Type setTicketType : typeList) {
            setTicketType(setTicketType);
        }

        String country = sessionManager.getCountryName();

//        if (TotalTicketAmount == 0) {
//            tv_paymenttittle.setVisibility(View.GONE);
//            rl_paymentoption.setVisibility(View.GONE);
//            paymenttype="payment";
//        } else {
//            tv_paymenttittle.setVisibility(View.VISIBLE);
//            rl_paymentoption.setVisibility(View.VISIBLE);
//        }

        if (country.equals("Qatar")) {
            ll_payfort.setVisibility(View.GONE);
            ll_visacard.setVisibility(View.VISIBLE);
            ll_naps.setVisibility(View.VISIBLE);
            ll_amexpayment.setVisibility(View.VISIBLE);
            paymenttype = "visa";
            isPayFord = false;
            isNaps = false;
            ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_visacard.setBackgroundResource(R.drawable.selected_button_with_corners);
            ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
        } else {
            ll_payfort.setVisibility(View.VISIBLE);
            ll_visacard.setVisibility(View.GONE);
            ll_naps.setVisibility(View.GONE);
            ll_amexpayment.setVisibility(View.GONE);
            paymenttype = "payfort";
            isPayFord = true;
            ll_payfort.setBackgroundResource(R.drawable.selected_button_with_corners);
            ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
            ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
        }
        presenter.getQT5countryDropdown();

    }

    public void setTicketType(EventTicketQT5Type type) {
        if (type.quantity > 0) {
            String strTickets = "";
            if (type.quantity > 1) {
                tv_ticket.setText("Tickets");
                strTickets = type.ticketName + " : " + type.quantity + " Tickets";
            } else {
                tv_ticket.setText("Ticket");
                strTickets = type.ticketName + " : " + type.quantity + " Ticket";
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
//                       return;
//                   }
                    JSONObject jsonObject = new JSONObject(TICKET_XML);
                    jsonObject.put("nationality", spin_nationality.getSelectedItem().toString());

                    presenter.verifyBooking(TICKET_VERIFY_XML,jsonObject.toString());

//                String sTICKET_XML = TICKET_XML;
                    //QTUtils.showProgressDialog(this,true);
//                   presenter.BookTickets(jsonObject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;



            case R.id.ll_amexpayment:

                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_amexpayment.setBackgroundResource(R.drawable.selected_button_with_corners);
                isPayFord = false;
                isNaps = false;
                paymenttype = "amex";

                break;

            case R.id.ll_visacard:
                ll_visacard.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                isPayFord = false;
                isNaps = false;
                paymenttype = "visa";
//                iv_tick2.setVisibility(View.GONE);
//                iv_tick3.setVisibility(View.GONE);
//                iv_tick4.setVisibility(View.GONE);
//                payment_Type = 4;

                break;


            case R.id.ll_payfort:
                isPayFord = true;
                ll_payfort.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_naps.setBackgroundResource(R.drawable.unselected_button_with_corners);
                paymenttype = "payfort";
//                iv_tick1.setVisibility(View.VISIBLE);
//                iv_tick2.setVisibility(View.GONE);
//                iv_tick3.setVisibility(View.GONE);
//                iv_tick4.setVisibility(View.GONE);
//                payment_Type = 4;

                break;

            case R.id.ll_naps:
                isNaps = true;
                paymenttype = "naps";
                ll_naps.setBackgroundResource(R.drawable.selected_button_with_corners);
                ll_visacard.setBackgroundResource(R.drawable.unselected_button_with_corners);
                ll_payfort.setBackgroundResource(R.drawable.unselected_button_with_corners);
//                iv_tick1.setVisibility(View.VISIBLE);
//                iv_tick2.setVisibility(View.GONE);
//                iv_tick3.setVisibility(View.GONE);
//                iv_tick4.setVisibility(View.GONE);
//                payment_Type = 4;

                break;

        }
    }

    @Override
    public void BookTickets(BookingQT5Model bookingQT5Model) {
        if (bookingQT5Model.statusCode.equals("200")) {
            //     http://admine.q-tickets.com/Payment/PayFort?pkId=1&orderId=123456&amount=1&email=shivam@gmail.com
            String url = "";
            if (isPayFord) {
                url = APIClient.PAYMENT_URL+"/Payment/PayFort?pkId=" + bookingQT5Model.data.pkId + "&orderId=" + bookingQT5Model.data.orderID + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt) + "&email=" + GuestEmail;
            } else {
                if (isNaps) {
                    url = APIClient.PAYMENT_URL+"/Payment/DebitCardNAPS?pkId=" + bookingQT5Model.data.pkId + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt) ;
                }
                else  if(paymenttype.equals("amex")){
                    url = APIClient.PAYMENT_URL+"/Payment/AMEX?pkId=" + bookingQT5Model.data.pkId + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt) ;
                }
                else {
                    url = APIClient.PAYMENT_URL+"/Payment/DohaBank?pkId=" + bookingQT5Model.data.pkId + "&amount=" + (int) Math.round(bookingQT5Model.data.totalAmt)  + "&currency=QAR";
                }
            }
            if (bookingQT5Model.data.totalAmt == 0) {
                Intent intent = new Intent(getApplicationContext(), EventBookingConfirmQT5Activity.class);
                intent.putExtra("DATA", bookingQT5Model.data);
                intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                startActivityForResult(intent, 003);
            } else {
                Bundle b = new Bundle();
                b.putString("url", url);
                Intent i = new Intent(this, PaymentWebView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("DATA", bookingQT5Model.data);
                i.putExtra("txt_title", "Payment");
                i.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                i.putExtra(AppConstants.EVENT_TIME, eventTime);
                i.putExtras(b);
                startActivity(i);
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
    public void setVerifyBooking(VerifyBookingDetail response,String bookingxml) {
        if (response.isSuccess && response.data) {
            try {
                JSONObject convertedObject = new JSONObject(bookingxml);
                convertedObject.put("WithUpdatedData",false);
                presenter.BookTickets(convertedObject.toString());
            } catch (JSONException exception) {
                exception.printStackTrace();
            }

        }
       else if(!response.isSuccess && response.data){
            showPricechangedDialogbox(this,response.message,bookingxml);
        }
       else{
            QTUtils.showDialogbox(this,response.message);
        }


    //    isSuccess == 1 & data == 1 -> WithUpdatedData = false
   //     isSuccess == 0 & data == 1 -> WithUpdatedData = true



    }

    public  void showPricechangedDialogbox(Context context, String message,String bookingxml){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle("Alert");
        builder.setCancelable(false);
        builder.setPositiveButton("Proceed", (DialogInterface.OnClickListener) (dialog, which) -> {
            try {
                JSONObject convertedObject = new JSONObject(bookingxml);
                convertedObject.put("WithUpdatedData",true);
                presenter.BookTickets(convertedObject.toString());
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
        });
        builder.setNegativeButton("Go Back", (DialogInterface.OnClickListener) (dialog, which) -> {
            try {
                JSONObject event_details = new JSONObject(bookingxml);
                String event_id=event_details.getString("eventId");
                String event_name=event_details.getString("eventName");
                String event_venue=event_details.getString("venueName");
                Intent intent = new Intent(getApplicationContext(), EventBookingDetailQT5Activity.class);
                intent.putExtra(AppConstants.EVENT_ID, Integer.parseInt(event_id));
                intent.putExtra(AppConstants.EVENT_NAME, event_name);
               // intent.putExtra(AppConstants.IS_EVENT_OFFLINE, iseventIsOffline);
                intent.putExtra(AppConstants.EVENT_VENUE, event_venue);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            dialog.cancel();

           // finish();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void proceedPayment() {
//        if (str_differ.equals("qtickets")) {
//            if (payment_Type == 6) {
//                paymentMainUrl = " http://q-tickets.com/paypal/checkout/";
//                if (type != null && type.equals("Novo")) {
//                    paymentMainUrl = paymentMainUrl + "N" + order_info;
//                } else if (cid != null && cid.equals("Novo")) {
//                    paymentMainUrl = paymentMainUrl + "C" + order_info;
//                } else if (type != null && type.equals("event")) {
//                    paymentMainUrl = paymentMainUrl + "E" + order_info;
//                } else {
//                    paymentMainUrl = paymentMainUrl + "A" + order_info;
//                }
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 4) {
//                paymentMainUrl = "https://q-tickets.com/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 1) {
//                paymentMainUrl = "https://q-tickets.com/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 5) {
//                paymentMainUrl = "https://q-tickets.com/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 3) {
//                paymentMainUrl = "https://q-tickets.com/Qpayment-registration1.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount
//                        + "&" + "OrderName=" + "online&" + "&" + "OrderInfo=" + "IPG&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            }
//            Intent ii = new Intent(PaymentActivity.this, PaymentWebViewActivity.class);
//            ii.putExtra("url", paymentMainUrl);
//            ii.putExtra("booking_id", order_info.substring(1));
//            startActivity(ii);
//        } else {
//            if (payment_Type == 6) {
//                paymentMainUrl = " http://q-tickets.com/paypal/checkout/";
//
//                if (type != null && type.equals("Novo")) {
//                    paymentMainUrl = paymentMainUrl + "N" + order_info;
//                } else if (cid != null && cid.equals("Novo")) {
//                    paymentMainUrl = paymentMainUrl + "C" + order_info;
//                } else if (type != null && type.equals("event")) {
//                    paymentMainUrl = paymentMainUrl + "E" + order_info;
//                } else {
//                    paymentMainUrl = paymentMainUrl + "A" + order_info;
//                }
//
//
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 4 || payment_Type == 1) {
//                paymentMainUrl = "https://q-tickets.com/novo/Qpayment-registration_mvc.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" +
//                        mAmount + "&" + "OrderName=online" + "&" + "OrderID=" + "N" + order_info + "&" + "nationality=" + nationality +
//                        "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 3) {
//                paymentMainUrl = "https://q-tickets.com/Qpayment-registration1.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" +
//                        mAmount + "&" + "OrderName=online" + "&" + "OrderID=" + "N" + order_info + "&" + "nationality=" + nationality +
//                        "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            } else if (payment_Type == 5) {
//                // paymentMainUrl = "https://q-tickets.com/Qpayment-registration.aspx?";
//                paymentMainUrl = "https://q-tickets.com/novo/Qpayment-registration_mvc.aspx?";
//                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" +
//                        mAmount + "&" + "OrderName=online" + "&" + "OrderID=" + "N" + order_info + "&" + "nationality=" + nationality +
//                        "&" + "paymenttype=" + payment_Type + "&AppSource=3";
//                Log.v("paymentURL", "== " + paymentMainUrl);
//            }
//
//            Intent ii = new Intent(PaymentActivity.this, PaymentWebViewActivity.class);
//            ii.putExtra("url", paymentMainUrl);
//            ii.putExtra("booking_id", order_info);
//            startActivity(ii);
//        }


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