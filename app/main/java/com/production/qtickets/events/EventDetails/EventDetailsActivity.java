package com.production.qtickets.events.EventDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.EventsFull;
import com.production.qtickets.model.OfferList;
import com.production.qtickets.model.TicketsModel;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.events.EventDetailViewPagerAdapter;
import com.production.qtickets.events.EventFragments.EventContactFragment;
import com.production.qtickets.events.EventFragments.EventSummaryFragment;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventDetailsActivity extends AppCompatActivity implements EventDetaileContracter.View {

    @BindView(R.id.iv_eventBanner)
    ImageView iv_eventBanner;

    @BindView(R.id.tv_eventName)
    TextviewBold tv_eventName;

    @BindView(R.id.tv_share)
    TextviewBold tvShare;

    @BindView(R.id.tv_startDate)
    TextviewBold tv_startDate;

    @BindView(R.id.tv_eventTime)
    TextviewBold tv_eventTime;

    @BindView(R.id.tv_eventLocation)
    TextviewBold tv_eventLocation;

    @BindView(R.id.htab_tabs)
    TabLayout htabTabs;

    @BindView(R.id.htab_viewpager)
    ViewPager htabViewpager;
    @BindView(R.id.c1)
    ConstraintLayout c1;
    EventDetailViewPagerAdapter adapter;

    String eventID = "";
    String eventName = "";
    String eventBannerUrl = "";
    String eventStartDate = "";
    String eventEndDate = "";
    String eventStartTime = "";
    String eventEndTime = "";
    String eventVenue = "";
    String eventEmail = "";
    String eventDescription = "";
    String eventLongitude = "";
    String eventLatitude = "";
    String eventSource = "";
    String EventUrl = "";

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;

    LinearLayout bookNow_layout;
    ImageView iv_back_arrow;

    private String startDate, endDate;

    EventDetailePresenter presenter;
    List<EventsFull> eventDataList = new ArrayList<>();
    SessionManager sessionManager;
    String eventId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(EventDetailsActivity.this);
        setContentView(R.layout.event_details_activity);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        presenter = new EventDetailePresenter();
        presenter.attachView(this);
        init();
    }

    // initialize the views
    public void init() {
        sessionManager = new SessionManager(EventDetailsActivity.this);

        if(getIntent().getStringExtra("event_id") != null){
            eventId =getIntent().getStringExtra("event_id");
        }
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            String share_url = data.getPath();
            share_url = share_url.replace("/qa/Events/EventsDetails/", "");
            showPb();
            presenter.getTicketDetailss(share_url);
        } else {
            if(eventId != null){
                showPb();
                presenter.getTicketDetailss(eventId);
            } else {
                showPb();
                presenter.getTicketDetailss(sessionManager.getEventId());
            }

        }


    }

    public void initviews() {
        Log.v("eventDescription", "== " + eventDescription);
        startDate = eventStartDate;
        endDate = eventEndDate;

        // share the event details with event link
        tvShare.setVisibility(View.VISIBLE);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Qtickets");
                    i.putExtra(Intent.EXTRA_TEXT, EventUrl);
                    startActivity(Intent.createChooser(i, "Share Via"));
                } catch (Exception e) {
                    //e.toString();
                }

            }
        });

        if (eventSource.equals("homePage")) {

            Log.v("hemanthdate", "== " + eventStartDate);
            Log.v("hemanthdate", "== " + eventEndDate);

            String[] splitspaceStartDate = startDate.split(" ");
            String partOne = splitspaceStartDate[0];

            String[] splitStartDate = partOne.split("/");
            String part1 = splitStartDate[0];
            String part2 = splitStartDate[1];
            String part3 = splitStartDate[2];

            startDate = part3 + "-" + part1 + "-" + part2;
            Log.v("hemanthdatefinal", "== " + startDate);

            String[] splitspaceEndDate = endDate.split(" ");
            String endpartOne = splitspaceEndDate[0];

            String[] splitEndDate = endpartOne.split("/");
            String endpart1 = splitEndDate[0];
            String endpart2 = splitEndDate[1];
            String endpart3 = splitEndDate[2];

            endDate = endpart3 + "-" + endpart1 + "-" + endpart2;
            Log.v("hemanthdatefinal", "== " + endDate);

        } else {

            Log.v("hemanthdate", "== " + eventStartDate);
            Log.v("hemanthdate", "== " + eventEndDate);

        }

        bookNow_layout = (LinearLayout) findViewById(R.id.bookNow_layout);
        bookNow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ticketIntent = new Intent(EventDetailsActivity.this, EventTicketsActivity.class);
                ticketIntent.putExtra("eventID", eventID);
                ticketIntent.putExtra("eventBanner", eventBannerUrl);
                ticketIntent.putExtra("eventName", eventName);
                ticketIntent.putExtra("eventSource", "eventPage");
                ticketIntent.putExtra("eventStartDate", startDate);
                ticketIntent.putExtra("eventEndDate", endDate);
                ticketIntent.putExtra("eventStartTime", eventStartTime);
                ticketIntent.putExtra("eventEndTime", eventEndTime);
                ticketIntent.putExtra("eventVenue", eventVenue);
                ticketIntent.putExtra("eventDescription", eventDescription);
                startActivity(ticketIntent);
            }
        });

        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventId != null){
                    Intent i = new Intent(EventDetailsActivity.this, DashBoardActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    EventDetailsActivity.this.finish();
                }
                //EventDetailsActivity.this.finish();
            }
        });

        tv_eventName.setText(eventName);
        Glide.with(this).load(eventBannerUrl)
                .thumbnail(0.5f)
                .into(iv_eventBanner);

        tv_startDate.setText(eventStartDate + " - " + eventEndDate);
        tv_eventTime.setText(eventStartTime + " - " + eventEndTime);
        tv_eventLocation.setText(eventVenue);

        htabTabs.setupWithViewPager(htabViewpager);
        setupViewPager(htabViewpager);
        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(EventDetailsActivity.this);
        editor = prefmPrefs.edit();
        editor.putString(AppConstants.TOTAL_TICKET_COUNT, "0");
        editor.putString(AppConstants.TOTAL_TICKET_COST, "0");
        editor.commit();

    }

    @Override
    public void setTicketDetails(TicketsModel response) {
        dismissPb();
    }

    @Override
    public void setTicketDetailsss(OfferList response) {
        dismissPb();
        eventDataList = response.events;
        if (eventDataList.size() > 0) {
            c1.setVisibility(View.VISIBLE);
            eventID = eventDataList.get(0).eventid;
            eventName = eventDataList.get(0).eventname;
            eventBannerUrl = eventDataList.get(0).bannerURL;
            eventStartDate = eventDataList.get(0).startDate;
            eventEndDate = eventDataList.get(0).endDate;
            eventStartTime = eventDataList.get(0).startTime;
            eventEndTime = eventDataList.get(0).endTime;
            eventVenue = eventDataList.get(0).venue;
            eventEmail = eventDataList.get(0).contactPersonEmail;
            eventLongitude = eventDataList.get(0).longitude;
            eventLatitude = eventDataList.get(0).latitude;
            eventDescription = eventDataList.get(0).eDescription;
            EventUrl = eventDataList.get(0).eventUrl;
            initviews();
            }else {
            c1.setVisibility(View.GONE);
        }
    }


    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(EventDetailsActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(EventDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, EventDetailsActivity.this, message);
    }


    //view pager for event details and contact info
    private void setupViewPager(ViewPager viewPager) {
        adapter = new EventDetailViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new EventTicketsFragment(eventID,eventName,eventBannerUrl,eventStartTime,eventEndTime,eventVenue), getString(R.string.tickets));
        adapter.addFragment(new EventSummaryFragment(eventDescription), getString(R.string.event_summary));
        adapter.addFragment(new EventContactFragment(eventLongitude, eventLatitude, eventVenue), getString(R.string.event_contact));
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(EventDetailsActivity.this);
        editor = prefmPrefs.edit();
        editor.putString(AppConstants.TOTAL_TICKET_COUNT, "0");
        editor.putString(AppConstants.TOTAL_TICKET_COST, "0");
        editor.commit();
    }
    @Override
    protected void onDestroy(){
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(eventId != null){
            Intent i = new Intent(EventDetailsActivity.this, DashBoardActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            super.onBackPressed();
        }
    }
}
