package com.production.qtickets.eventBookingConfirmQT5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.activity.ContactUsActivity;
import com.production.qtickets.activity.DownloadInvoiceWebView;
import com.production.qtickets.activity.DownloadInvoiceWebView22;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.model.BookingQT5Data;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.util.List;

public class EventBookingConfirmQT5Activity extends AppCompatActivity {

    BookingQT5Data data = null;
    String eventTime;
    List<EventTicketQT5Type> typeList;
    LinearLayout lay_ticket_type;
    TextView tv_tickets, tv_help;
    LinearLayout ll_download;
    ImageView iv_backfromconfirmation;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_booking_confirm_qt5);
        LinearLayout lay_time = (LinearLayout) findViewById(R.id.lay_time);
        TextView tv_event_title = (TextView) findViewById(R.id.tv_event_title);
        TextView tv_barcode_id = (TextView) findViewById(R.id.tv_barcode_id);
        TextView tv_event_date = (TextView) findViewById(R.id.tv_event_date);
        TextView tv_booking_id = (TextView) findViewById(R.id.tv_booking_id);
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        TextView tv_event_location = (TextView) findViewById(R.id.tv_event_location);
        ll_download = (LinearLayout) findViewById(R.id.ll_download);
        tv_tickets = (TextView) findViewById(R.id.tv_tickets);
        tv_help = (TextView) findViewById(R.id.tv_help);
        TextView tv_download_ticket = (TextView) findViewById(R.id.tv_download_ticket);
        TextView tv_amount_paid = (TextView) findViewById(R.id.tv_amount_paid);
        ImageView img_bar_code = (ImageView) findViewById(R.id.img_bar_code);
        lay_ticket_type = (LinearLayout) findViewById(R.id.lay_ticket_type);
        iv_backfromconfirmation = (ImageView) findViewById(R.id.iv_backfromconfirmation);
        sessionManager = new SessionManager(EventBookingConfirmQT5Activity.this);
        data = (BookingQT5Data) getIntent().getSerializableExtra("DATA");
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);

        Glide.with(getApplicationContext()).load(data.barcodes.get(0)).into(img_bar_code);
        tv_event_title.setText("" + data.eventName);
        tv_download_ticket.setText("" + data.eventName + ".pdf");
        tv_booking_id.setText("" + data.orderID);//29sep
        tv_barcode_id.setText("" + data.orderID);
        tv_event_location.setText("" + data.venueName);
        String[] strDate = data.orderDate.split("T");
        tv_amount_paid.setText("" + (QTUtils.parseDouble(data.totalAmt) + " " + data.currencyCode));
        tv_event_date.setText("" + DateTimeUtils.getEventFullDateSecond(strDate[0]));
        if (eventTime != null && !eventTime.equals("") && !eventTime.equals("null")) {
            lay_time.setVisibility(View.VISIBLE);
            tv_time.setText("" + eventTime);
        } else {
            lay_time.setVisibility(View.GONE);
        }
        for (EventTicketQT5Type setTicketType : typeList) {
            setTicketType(setTicketType);
        }

        if (sessionManager.getCountryName().equals("Dubai")) {
            tv_help.setText(sessionManager.getContactEmailForDubai());
        } else if (sessionManager.getCountryName().equals("Bahrain")) {
            tv_help.setText(sessionManager.getContactEmailForBehrain());
        } else {
            tv_help.setText(sessionManager.getContactEmailForQatar());
        }
        QTUtils.showProgressDialog(this, false);
        listeners();
    }

    private void listeners() {

        iv_backfromconfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventBookingConfirmQT5Activity.this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + tv_help.getText().toString())); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, tv_help.getText().toString());
                intent.putExtra(Intent.EXTRA_SUBJECT, data.eventName);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        ll_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pkID = String.valueOf(data.pkId);
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(APIClient.PAYMENT_URL + "/Order/PrintTicket?pkId=" + pkID + "&ticketId=" + data.ticks));
                startActivity(httpIntent);

            }
        });
    }

    public void setTicketType(EventTicketQT5Type type) {
        if (type.quantity > 0) {
            String strTickets = "";
            String category = "";
            if (type.category != null && !type.category.isEmpty()) {
                category = type.category;
            }
            if (type.quantity > 1) {
                tv_tickets.setText("Tickets");
                if (!category.equals("")) {
                    strTickets = type.ticketName + "(" + category + " )" + " : " + type.quantity + " Tickets";
                } else {
                    strTickets = type.ticketName + " : " + type.quantity + " Tickets";
                }
            } else {
                tv_tickets.setText("Ticket");
                if (!category.equals("")) {
                    strTickets = type.ticketName + "(" + category + " )" + " : " + type.quantity + " Ticket";
                } else {
                    strTickets = type.ticketName + " : " + type.quantity + " Ticket";
                }
                // strTickets = type.ticketName+" : "+type.quantity+" Ticket";
            }
            lay_ticket_type.addView(addViewTicketType(strTickets));
        }
    }

    public View addViewTicketType(String str) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.row_ticket_type, null, false);
        TextView tv = (TextView) v.findViewById(R.id.tv_ticket_view);
        tv.setText("" + str);
        return v;

    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
//        super.onBackPressed();
    }
}