package com.production.qtickets.eventDetailQT5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.production.qtickets.R;
import com.production.qtickets.adapters.SimilarEventsAdapter;
import com.production.qtickets.eventBookingDetailQT5.EventBookingDetailQT5Activity;
import com.production.qtickets.model.EventDetailQT5Data;
import com.production.qtickets.model.EventDetailQT5Model;
import com.production.qtickets.model.GetSimilarEventData;
import com.production.qtickets.movies.seatselection.SeatSelectionActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.ItemOffsetDecoration;
import com.production.qtickets.utils.QTUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class EventDetailQT5Activity extends AppCompatActivity implements EventDetailQT5Contracter.View, View.OnClickListener, OnMapReadyCallback, Html.ImageGetter {

    GoogleMap mMap;
    int ID = 0;
    String eventName = "";
    String eventVenue = "";
    EventDetailQT5Presenter presenter;
    EventDetailQT5Data eventDetailQT5Data;
    int position = 0;
    LinearLayoutManager linearLayoutManager;

    RecyclerView recycler, recycler_gallery, recycler_videogallery;
    LinearLayout lay_parking, lay_no_smoking, lay_no_cameras, lay_fb_available, lay_is_casual_allow, lay_is_recommended;
    TextView tv_gallery, tv_event_title, tv_event_date, tv_time, tv_event_location, tv_faqvalue, tv_faqtittle, tv_venue, tv_videogallery;

    HtmlTextView tv_summery;
    //share events
    LinearLayout ll_share, ll_addeventincalender;
    Bitmap bitmapsss;
    Uri share_image = null;
    Dialog socialMediaDialog;
    TextView tv_admissionview, tv_folowustittle, tv_similarevent;
    LinearLayout ll_socailroot;
    ImageView iv_fblogin, iv_twitterlogin, iv_instagramlogin, iv_linkdlnlogin, iv_tinderlogin, iv_seatingimage;
    LinearLayout ll_seatingarrangement;
    RecyclerView rv_similarevent;
    SimilarEventsAdapter similarEventsAdapter;
    ArrayList<GetSimilarEventData.Datum> mSimilarEventList = new ArrayList<>();
    String shareableUrl = "";
    String eventname = "";
    LinearLayout ll_datetime;

    CardView ll_mapview;
    LinearLayout ll_venue;
    boolean iseventIsOffline = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_qt5);
        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        Button btn_book_now = (Button) findViewById(R.id.btn_book_now);


        ll_mapview = (CardView) findViewById(R.id.ll_mapview);
        ll_venue = (LinearLayout) findViewById(R.id.ll_venue);
        ll_datetime = (LinearLayout) findViewById(R.id.ll_datetime);


        btn_back.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler_gallery = (RecyclerView) findViewById(R.id.recycler_gallery);
        recycler_videogallery = (RecyclerView) findViewById(R.id.recycler_videogallery);

        rv_similarevent = (RecyclerView) findViewById(R.id.rv_similarevent);

        tv_admissionview = (TextView) findViewById(R.id.tv_admissionview);
        tv_similarevent = (TextView) findViewById(R.id.tv_similarevent);

        lay_parking = (LinearLayout) findViewById(R.id.lay_parking);
        lay_no_smoking = (LinearLayout) findViewById(R.id.lay_no_smoking);
        lay_no_cameras = (LinearLayout) findViewById(R.id.lay_no_cameras);
        lay_fb_available = (LinearLayout) findViewById(R.id.lay_fb_available);
        lay_is_casual_allow = (LinearLayout) findViewById(R.id.lay_is_casual_allow);
        lay_is_recommended = (LinearLayout) findViewById(R.id.lay_is_recommended);


        ll_seatingarrangement = (LinearLayout) findViewById(R.id.ll_seatingarrangement);

        //share
        ll_share = (LinearLayout) findViewById(R.id.ll_share);
        ll_addeventincalender = (LinearLayout) findViewById(R.id.ll_addeventincalender);

        tv_videogallery = (TextView) findViewById(R.id.tv_videogallery);
        tv_gallery = (TextView) findViewById(R.id.tv_gallery);
        tv_event_title = (TextView) findViewById(R.id.tv_event_title);
        tv_event_date = (TextView) findViewById(R.id.tv_event_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_event_location = (TextView) findViewById(R.id.tv_event_location);
        tv_summery = findViewById(R.id.tv_summery);
        tv_faqvalue = (TextView) findViewById(R.id.tv_faqvalue);
        tv_faqtittle = (TextView) findViewById(R.id.tv_faqtittle);
        tv_venue = (TextView) findViewById(R.id.tv_venue);
        tv_folowustittle = (TextView) findViewById(R.id.tv_folowustittle);
        ll_socailroot = (LinearLayout) findViewById(R.id.ll_socailroot);


        iv_fblogin = (ImageView) findViewById(R.id.iv_fblogin);
        iv_twitterlogin = (ImageView) findViewById(R.id.iv_twitterlogin);
        iv_instagramlogin = (ImageView) findViewById(R.id.iv_instagramlogin);
        iv_linkdlnlogin = (ImageView) findViewById(R.id.iv_linkdlnlogin);
        iv_tinderlogin = (ImageView) findViewById(R.id.iv_tinderlogin);
        iv_seatingimage = (ImageView) findViewById(R.id.iv_seatingimage);

        recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(linearLayoutManager);


        recycler_gallery.setHasFixedSize(true);
        recycler_gallery.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recycler_gallery.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen.two, R.dimen.two, R.dimen.two, R.dimen.two));


        recycler_videogallery.setHasFixedSize(true);
        recycler_videogallery.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recycler_videogallery.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen.two, R.dimen.two, R.dimen.two, R.dimen.two));


        presenter = new EventDetailQT5Presenter();
        presenter.attachView(this);
        ID = getIntent().getIntExtra(AppConstants.EVENT_ID, 0);
        if (ID > 0) presenter.getEventDetail(ID);
        ll_share.setOnClickListener(this);
        ll_addeventincalender.setOnClickListener(this);

        iv_fblogin.setOnClickListener(this);
        iv_instagramlogin.setOnClickListener(this);
        iv_twitterlogin.setOnClickListener(this);
        iv_linkdlnlogin.setOnClickListener(this);
        iv_tinderlogin.setOnClickListener(this);
        ll_seatingarrangement.setOnClickListener(this);
        if (ID > 0) presenter.getSimilarEvents(ID);
    }

    @Override
    public void setEventDetail(EventDetailQT5Model eventDetailQT5Model) {

        if (eventDetailQT5Model.statusCode == 200) {
            eventDetailQT5Data = eventDetailQT5Model.data;
            setData(eventDetailQT5Data);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void setSimilarEvents(GetSimilarEventData getSimilarEventData) {
        mSimilarEventList = getSimilarEventData.data;
        if (mSimilarEventList.size() > 0) {
            rv_similarevent.setHasFixedSize(true);
            rv_similarevent.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            similarEventsAdapter = new SimilarEventsAdapter(mSimilarEventList, this);
            rv_similarevent.setAdapter(similarEventsAdapter);
        } else {
            tv_similarevent.setVisibility(View.GONE);
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Retrieving the url to share
    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.production.qtickets.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.ic_3d_glasses);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tv_summery.getText();
                tv_summery.setText(t);
            }
        }
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        String srcss = "";

        public MyAsyncTask(String src) {
            super();
            this.srcss = src;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL url = new URL(srcss);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmapsss = BitmapFactory.decodeStream(input);
                return bitmapsss;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            share_image = getmageToShare(bitmap);
        }
    }


    //normal dialogbox
    private void showDialogbox(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_alert_dialog, null);
        // Build the dialog
        socialMediaDialog = new Dialog(EventDetailQT5Activity.this);
        socialMediaDialog.setContentView(customView);
        socialMediaDialog.setCanceledOnTouchOutside(true);
        TextView tv_content = socialMediaDialog.findViewById(R.id.tv_popupcontent);
        ImageView iv_closebutton = socialMediaDialog.findViewById(R.id.iv_closebutton);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_content.setText(Html.fromHtml("" + message, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tv_content.setText(Html.fromHtml("" + message));
        }
        iv_closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialMediaDialog.dismiss();
            }
        });
        socialMediaDialog.getWindow().setLayout(700, 600);
        socialMediaDialog.show();

    }


    private void setData(EventDetailQT5Data eventDetailQT5Data) {
        if (eventDetailQT5Data != null) {
            try {
                new MyAsyncTask(eventDetailQT5Data.banner1).execute();
            } catch (Exception e) {
                System.out.println(e);
            }
            List<String> eventBanner = new ArrayList<>();
            if (eventDetailQT5Data.banner7 != null && !eventDetailQT5Data.banner7.equals("")) {
                eventBanner.add(eventDetailQT5Data.banner7);
            }

            if (eventBanner != null && !eventBanner.isEmpty()) {
                EventDetailBannerAdapter eventDetailBannerAdapter = new EventDetailBannerAdapter(eventBanner, getApplicationContext());
                recycler.setAdapter(eventDetailBannerAdapter);

            }


            if (eventDetailQT5Data.isSeatLayoutRequired == 1 && !eventDetailQT5Data.layoutFile.equals("")) {
                ll_seatingarrangement.setVisibility(View.VISIBLE);
                Glide.with(this).load(eventDetailQT5Data.layoutFile)
                        .into(iv_seatingimage);

            } else {
                ll_seatingarrangement.setVisibility(View.GONE);
            }

            if (eventDetailQT5Data.eventSocialLinks.size() > 0) {
                for (int i = 0; i < eventDetailQT5Data.eventSocialLinks.size(); i++) {
                    if (eventDetailQT5Data.eventSocialLinks.get(i).linkType == 1) {
                        iv_fblogin.setVisibility(View.VISIBLE);
                        iv_fblogin.setTag(eventDetailQT5Data.eventSocialLinks.get(i).link);
                    }
                    if (eventDetailQT5Data.eventSocialLinks.get(i).linkType == 2) {
                        iv_instagramlogin.setVisibility(View.VISIBLE);
                        iv_instagramlogin.setTag(eventDetailQT5Data.eventSocialLinks.get(i).link);
                    }
                    if (eventDetailQT5Data.eventSocialLinks.get(i).linkType == 3) {
                        iv_twitterlogin.setVisibility(View.VISIBLE);
                        iv_twitterlogin.setTag(eventDetailQT5Data.eventSocialLinks.get(i).link);
                    }
                    if (eventDetailQT5Data.eventSocialLinks.get(i).linkType == 4) {
                        iv_linkdlnlogin.setVisibility(View.VISIBLE);
                        iv_linkdlnlogin.setTag(eventDetailQT5Data.eventSocialLinks.get(i).link);
                    }
                    if (eventDetailQT5Data.eventSocialLinks.get(i).linkType == 5) {
                        iv_tinderlogin.setVisibility(View.VISIBLE);
                        iv_tinderlogin.setTag(eventDetailQT5Data.eventSocialLinks.get(i).link);
                    }
                }


            } else {
                tv_folowustittle.setVisibility(View.GONE);
                ll_socailroot.setVisibility(View.GONE);
            }

            if (eventDetailQT5Data.admission != null && !eventDetailQT5Data.admission.isEmpty()) {
                tv_admissionview.setText("Admission: " + eventDetailQT5Data.admission);
            } else {
                tv_admissionview.setVisibility(View.GONE);
            }

            if (eventDetailQT5Data.is_PopUp.equals("True")) {
                showDialogbox(this, eventDetailQT5Data.content);
            }

            if (eventDetailQT5Data.isParkingAllow > 0) {
                lay_parking.setVisibility(View.VISIBLE);
            } else {
                lay_parking.setVisibility(View.GONE);
            }
            if (eventDetailQT5Data.isSmokingAllow > 0) {
                lay_no_smoking.setVisibility(View.VISIBLE);
            } else {
                lay_no_smoking.setVisibility(View.GONE);
            }
            if (eventDetailQT5Data.isCameraAvailable > 0) {
                lay_no_cameras.setVisibility(View.VISIBLE);
            } else {
                lay_no_cameras.setVisibility(View.GONE);
            }
            if (eventDetailQT5Data.isFBAllow > 0) {
                lay_fb_available.setVisibility(View.VISIBLE);
            } else {
                lay_fb_available.setVisibility(View.GONE);
            }
            if (eventDetailQT5Data.isCasualAllow > 0) {
                lay_is_casual_allow.setVisibility(View.VISIBLE);
            } else {
                lay_is_casual_allow.setVisibility(View.GONE);
            }
//            if (eventDetailQT5Data.isRecommended > 0) {
//                lay_is_recommended.setVisibility(View.VISIBLE);
//            } else {
//                lay_is_recommended.setVisibility(View.GONE);
//            }

            tv_event_title.setText("" + eventDetailQT5Data.eventTitle);
            eventName = "" + eventDetailQT5Data.eventTitle;
            eventVenue = "" + eventDetailQT5Data.venue;
            iseventIsOffline = eventDetailQT5Data.eventIsOffline;
            shareableUrl = eventDetailQT5Data.eventUrl;
            String date = "";
            if (eventDetailQT5Data.startDate != null && eventDetailQT5Data.startDate.contains(", ")) {
                String[] parts = eventDetailQT5Data.startDate.split(", ");
                if (parts.length > 0) {
                    date = QTUtils.changeDateFormat(parts[0]) + " TO ";
                }
                // QTUtils.showDialogbox();


            }
            if (eventDetailQT5Data.endDate != null && eventDetailQT5Data.endDate.contains(", ")) {
                String[] parts = eventDetailQT5Data.endDate.split(", ");
                date = date + QTUtils.changeDateFormat(parts[0]);

            } else {
                if (date.contains(" TO ")) date = date.replace(" TO ", "");
            }
            tv_event_date.setText("" + date);

            if (eventDetailQT5Data.startTime != null && !eventDetailQT5Data.startTime.equals("")) {
                // String[] parts = eventDetailQT5Data.startTime;
                tv_time.setText(eventDetailQT5Data.startTime + " ONWARDS");
            } else {
                ll_datetime.setVisibility(View.GONE);
            }
            tv_event_location.setText("" + eventDetailQT5Data.venue);

            Spanned spanned = Html.fromHtml(eventDetailQT5Data.eventDescription, this, null);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                tv_summery.setText(Html.fromHtml("" + eventDetailQT5Data.eventDescription, Html.FROM_HTML_MODE_COMPACT
//                ));
//            } else {
//                tv_summery.setText(Html.fromHtml("" + eventDetailQT5Data.eventDescription));
//            }

            tv_summery.setHtml(eventDetailQT5Data.eventDescription,
                    new HtmlAssetsImageGetter(tv_summery));

            if (eventDetailQT5Data.faq != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tv_faqvalue.setText(Html.fromHtml("" + eventDetailQT5Data.faq, Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE
                    ));
                } else {
                    tv_faqvalue.setText(Html.fromHtml("" + eventDetailQT5Data.faq));
                }
            } else {
                tv_faqvalue.setVisibility(View.GONE);
                tv_faqtittle.setVisibility(View.GONE);
            }

            tv_venue.setText("" + eventDetailQT5Data.venue);

            if (eventDetailQT5Data.eventGalleryImages != null && !eventDetailQT5Data.eventGalleryImages.isEmpty()) {
                EventDetailGalleryAdapter eventDetailGalleryAdapter = new EventDetailGalleryAdapter(eventDetailQT5Data.eventGalleryImages, getApplicationContext());
                recycler_gallery.setAdapter(eventDetailGalleryAdapter);
                tv_gallery.setVisibility(View.VISIBLE);
            } else {
                tv_gallery.setVisibility(View.GONE);
            }
            if (eventDetailQT5Data.videoLinks != null && !eventDetailQT5Data.videoLinks.isEmpty()) {
                EventDetailsVideoGalleryAdapter eventDetailsVideoGalleryAdapter = new EventDetailsVideoGalleryAdapter(eventDetailQT5Data.videoLinks, getApplicationContext());
                recycler_videogallery.setAdapter(eventDetailsVideoGalleryAdapter);
                tv_videogallery.setVisibility(View.VISIBLE);
            } else {
                tv_videogallery.setVisibility(View.GONE);
            }


            if (eventDetailQT5Data.latitude != null && !eventDetailQT5Data.latitude.equals("") && !eventDetailQT5Data.latitude.equals("null")) {
                if (mMap != null) {
                    createMarker(eventDetailQT5Data.latitude, eventDetailQT5Data.longitude, eventDetailQT5Data.venue);
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(9), 2000, null);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(eventDetailQT5Data.latitude), Double.parseDouble(eventDetailQT5Data.longitude)), 9));
                }
            } else {
                ll_venue.setVisibility(View.GONE);
                ll_mapview.setVisibility(View.GONE);
            }


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
        QTUtils.showProgressDialog(EventDetailQT5Activity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, EventDetailQT5Activity.this, message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_seatingarrangement: {
                Intent intent = new Intent(this, FullImageActivity.class);
                intent.putExtra("IMAGE", eventDetailQT5Data.layoutFile);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            break;

            case R.id.iv_fblogin: {
                try {
                    String url = URLDecoder.decode(iv_fblogin.getTag().toString(), "UTF-8");
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
            break;

            case R.id.iv_instagramlogin: {
                String url = null;
                try {
                    url = URLDecoder.decode(iv_instagramlogin.getTag().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            break;

            case R.id.iv_twitterlogin: {
                String url = null;
                try {
                    url = URLDecoder.decode(iv_twitterlogin.getTag().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            break;

            case R.id.iv_linkdlnlogin: {
                String url = null;
                try {
                    url = URLDecoder.decode(iv_linkdlnlogin.getTag().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            break;

            case R.id.iv_tinderlogin: {
                String url = null;
                try {
                    url = URLDecoder.decode(iv_tinderlogin.getTag().toString(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
            break;


            case R.id.btn_back: {
                finish();
            }
            break;
            case R.id.btn_book_now: {
                Intent intent = new Intent(getApplicationContext(), EventBookingDetailQT5Activity.class);
                intent.putExtra(AppConstants.EVENT_ID, ID);
                intent.putExtra(AppConstants.EVENT_NAME, eventName);
                intent.putExtra(AppConstants.IS_EVENT_OFFLINE, iseventIsOffline);
                intent.putExtra(AppConstants.EVENT_VENUE, eventVenue);
                startActivity(intent);
            }
            break;

            case R.id.ll_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, share_image);
                intent.putExtra(Intent.EXTRA_TEXT, eventName);
                intent.putExtra(Intent.EXTRA_TEXT, shareableUrl);
                intent.setType("image/png");
                startActivity(Intent.createChooser(intent, "Share Via"));

                break;

            case R.id.ll_addeventincalender:
                Intent mIntent = new Intent(Intent.ACTION_EDIT);
                mIntent.setType("vnd.android.cursor.item/event");
                mIntent.putExtra("StartDate", eventDetailQT5Data.startDate);
                mIntent.putExtra("EndDate", eventDetailQT5Data.endDate);
                mIntent.putExtra(CalendarContract.Events.TITLE, eventDetailQT5Data.eventTitle);
                mIntent.putExtra("description", eventDetailQT5Data.eventDescription);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
//            mMap.getUiSettings().setZoomControlsEnabled(true);
            if (eventDetailQT5Data != null) {
                if (eventDetailQT5Data.latitude != null && !eventDetailQT5Data.latitude.equals("") && !eventDetailQT5Data.latitude.equals("null")) {
                    createMarker(eventDetailQT5Data.latitude, eventDetailQT5Data.longitude, eventDetailQT5Data.venue);
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(eventDetailQT5Data.latitude), Double.parseDouble(eventDetailQT5Data.longitude)), 9));
                }
            }

//            LatLng sydney = new LatLng(12.9716, 77.5946);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    String strUri = "http://maps.google.com/maps?q=loc:" + eventDetailQT5Data.latitude + "," + eventDetailQT5Data.longitude + " (" + "Label which you want" + ")";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                    return true;
                }
            });

        }
    }

    public Marker createMarker(String lat, String lng, String locationName) {
        mMap.clear();
        return mMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                        .title(locationName)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

        );

    }
}