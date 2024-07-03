package com.production.qtickets.dashboard;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.production.qtickets.AlarmReceiver;
import com.production.qtickets.R;
import com.production.qtickets.adapters.FlagDashQT5Adapter;
import com.production.qtickets.eventQT5.CarouselBannerAdapter;
import com.production.qtickets.eventQT5.EvenListInHomeAdapter;
import com.production.qtickets.eventQT5.EventHomeActivity;
import com.production.qtickets.eventQT5.EventsListQT5Adapter;
import com.production.qtickets.events.EventListAdapterForHome;
import com.production.qtickets.model.AllCountryQT5Data;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.AllEventQT5Data;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.model.BannerModel;
import com.production.qtickets.model.CategoryModel;
import com.production.qtickets.model.CustomBannerDataEvents;
import com.production.qtickets.model.DashboardModel;
import com.production.qtickets.model.Data;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.GetNewBannerEvents;
import com.production.qtickets.model.Item;
import com.production.qtickets.model.ListData;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.model.StaticLinksDataModel;
import com.production.qtickets.model.StaticPageLinksModel;
import com.production.qtickets.model.TokenModel;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.utils.ForceUpdateChecker;
import com.production.qtickets.utils.ItemOffsetDecoration;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.activity.NavigationDrawerActivity;


import com.production.qtickets.model.EventData;
import com.production.qtickets.events.EventsListAdapter;
import com.production.qtickets.movies.MovieListAdapter;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.searchList.SearchListActivity;

//import net.hockeyapp.android.CrashManager;
//import net.hockeyapp.android.UpdateManager;


/**
 * Created by hemanth on 4/9/2018.
 */

public class DashBoardActivity extends AppCompatActivity implements DashboardContracter.View,
        View.OnClickListener, ForceUpdateChecker.OnUpdateNeededListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    //adapter
    MovieListAdapter homeMoviesAdapter;
    EventsListAdapter eventsListAdapter;
    DashboardAdapter dashboardAdapter;
    headerAdapter hAdapter;

    EventListAdapterForHome eventListAdapterForHome;

    //recyclerview
    RecyclerView list_recyclerview, event_list_recyclerview, sports_list_recyclerview, leisure_list_recyclerview, headerLayout;
    RecyclerView.LayoutManager mLayoutManager, eLayoutManager, sLayoutManager, lLayoutManager, hLayoutManager;
    //  ListView headerLayout;
    //presenter
    DashboardPresenter presenter;

    //list
    List<String> countryListArray = new ArrayList<>();
    List<Integer> flag_list = new ArrayList<>();
    List<MovieModel> moviList = new ArrayList<>();
    List<ListData> listDetails = new ArrayList<>();
    List<Data> EventDetails = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;


    //sessionmanager
    SessionManager sessionManager;

    //textview
    TextView tv_movies_seeall, tv_events_seeall, tv_sports_seeall, tv_leisure_seeall;

    //spinner
    Spinner spinner_country;
    RelativeLayout spinnerLayout;
    NestedScrollView main_layout;

    //gpstracker
//    GPSTracker gps;

    LinearLayout dohabankoffer_layout;

    BannerModel bannerModelTemp;


    //linear layout
    TextView l1;
    TextView l2;
    TextView l3;
    TextView l4;
    LinearLayout l1_movies, l2_events, l3_sports, l4_liesure;

    ImageView iv_search;
    String movie_id, type, headingold;
    boolean isinside = false;
    ArrayList<String> heading = new ArrayList<>();



    public static boolean isonbackpresed = false;
    // GifImageView gifImageView;

    SharedPreferences pref;
    String device_token;
    FlagDashQT5Adapter flagDashAdapter;
    RecyclerView sliderLayout;
    private List<Item> mBannerList = new ArrayList<Item>();
    private List<StaticPageLinksModel> staticLinksDataList = new ArrayList();

    private PendingIntent pendingIntent;
    private AppBarLayout app_bar_layout;
    private Toolbar toolbar;
    private LinearLayout l1_main;
    private RelativeLayout movieLayout;
    private LinearLayout ll_home, ll_movies, ll_events, ll_more;
    private TextView tv_hometittle, tv_moviewtittle, tv_eventtittle, tv_more;
    private ImageView ic_home, ic_movies, ic_event, menu_icon;
    ArrayList<AllEventQT5Data> mAllEvents = new ArrayList<>();
    int sportsID = -1;
    int leisureID = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //   QTUtils.setStatusBarGradiant(DashBoardActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_page);
        sessionManager = new SessionManager(DashBoardActivity.this);
        /*if (sessionManager.isShowCovidAlert()) {
            QTUtils.CovidAlertDailog(DashBoardActivity.this);
            sessionManager.setShowCovidAlert(false);
        }*/

        try {
            //FirebaseVersionUpdateChecker
         //todo update me
            presenter = new DashboardPresenter();
            presenter.attachView(this);

            //getting fcm token
           /* pref = getApplicationContext().getSharedPreferences(com.production.qtickets.utils.Config.SHARED_PREF, 0);
          //  device_token = pref.getString("regId", null);
            Log.e("HemanthFCM", "== " + device_token);
*/
            QTUtils.dohaBankOffer = "no";
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //home page doha bank offer gif image urls

//    private void getDashboradBanner() {
//        String priceRangeURL = "https://api.q-tickets.com/V5.0/homepagebanners";
//        Log.v("dohaoffer", " == " + priceRangeURL);
//        new bannerAsuncTask().execute(priceRangeURL);
//    }

    //FirebaseVersionUpdateChecker
    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Q-Tickets just got better, Enjoy seamless experience with our brand new app.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // AsyncTask to get the dohabank offer gif image url
//    private class bannerAsuncTask extends AsyncTask<String, Void, String> {
//        private ProgressDialog dialog;
//
//        @Override
//        protected String doInBackground(String... urls) {
//            return QTUtils.GET(urls[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            Log.v("dohaBanner", "==" + result);
//            try {
//
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("Offers");
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    String imagegifImageView = jsonArray.getJSONObject(i).getString("Offer_Image");
//                    Glide.with(getApplicationContext()).load(imagegifImageView)
//                            .into(gifImageView);
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    // set the location data which we get in location list api
    @Override
    public void setLocationData(List<CategoryModel> response) {
        dismissPb();
        List<CategoryModel> locationList = response;
        countryListArray.clear();
        flag_list.clear();
        for (int i = 0; i < locationList.size(); i++) {
            if (!locationList.get(i).countryCode.equalsIgnoreCase("IN")) {
                countryListArray.add(locationList.get(i).countryCode);
            }
        }
//        bindCountrySpinner("QA");
    }

    @Override
    public void setStaticLinksdata(StaticLinksDataModel staticLinksdata) {
        Log.d("Size: ", String.valueOf(staticLinksdata));

        staticLinksDataList.addAll(staticLinksdata.data);

        setCorrespondingDataWithAPi();

    }

    private void setCorrespondingDataWithAPi() {

        for (int i=0;i<staticLinksDataList.size();i++){

            if (staticLinksDataList.get(i).title.equalsIgnoreCase("FAQ")) {

                sessionManager.setfaqLink(staticLinksDataList.get(i).link);

            }
             else if (staticLinksDataList.get(i).title.equalsIgnoreCase("About Us")) {
                 sessionManager.setAboutUsLink(staticLinksDataList.get(i).link);


            }
            else if (staticLinksDataList.get(i).title.equalsIgnoreCase("Privacy and Policy")) {
                sessionManager.setPrivacyLink(staticLinksDataList.get(i).link);


            }
            else if (staticLinksDataList.get(i).title.equalsIgnoreCase("Terms and Conditions")) {
                sessionManager.setTermsLink(staticLinksDataList.get(i).link);


            }
            else if (staticLinksDataList.get(i).title.equalsIgnoreCase("Email")) {
                sessionManager.setContactEmail(staticLinksDataList.get(i).link);


            }
            else if (staticLinksDataList.get(i).title.equalsIgnoreCase("Phone(Dubai)")) {
                sessionManager.setDubaiContactPhone(staticLinksDataList.get(i).link);


            }
            else if (staticLinksDataList.get(i).title.equalsIgnoreCase("Phone(Qatar)")) {
                sessionManager.setQatarContactPhone(staticLinksDataList.get(i).link);


            }


        }
    }

    // here we will list the movies, events,sportss and leisures
    @Override
    public void setDashboardData(DashboardModel dashboardModel) {
        String status_code = dashboardModel.statusCode;
        l1_main.setVisibility(View.VISIBLE);
        sessionManager.setShareUrl(dashboardModel.movieURL);
        if (status_code.equals("200")) {
            moviList = dashboardModel.movies;
            if (moviList.size() > 0) {
                l1_movies.setVisibility(View.VISIBLE);
                homeMoviesAdapter = new MovieListAdapter(DashBoardActivity.this, moviList, 100);
                list_recyclerview.setAdapter(homeMoviesAdapter);
            } else {
                l1_movies.setVisibility(View.GONE);
            }
            if (moviList.size() >= 3) {
                tv_movies_seeall.setVisibility(View.VISIBLE);
            } else {
                tv_movies_seeall.setVisibility(View.GONE);
            }
            List<EventData> eventList = dashboardModel.events;
            if (eventList.size() > 0) {
                l2_events.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(DashBoardActivity.this, eventList, 100);
                event_list_recyclerview.setAdapter(eventsListAdapter);
            } else {
                l2_events.setVisibility(View.GONE);
            }
            if (eventList.size() >= 3) {
                tv_events_seeall.setVisibility(View.VISIBLE);
            } else {
                tv_events_seeall.setVisibility(View.GONE);
            }
            List<EventData> sportsList = dashboardModel.sports;
            if (sportsList.size() > 0) {
                l3_sports.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(DashBoardActivity.this, sportsList, 100);
                sports_list_recyclerview.setAdapter(eventsListAdapter);
            } else {
                l3_sports.setVisibility(View.GONE);
            }
            if (sportsList.size() >= 3) {
                tv_sports_seeall.setVisibility(View.VISIBLE);
            } else {
                tv_sports_seeall.setVisibility(View.GONE);
            }
            List<EventData> leisureList = dashboardModel.leisure;
            if (leisureList.size() > 0) {
                l4_liesure.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(DashBoardActivity.this, leisureList, 100);
                leisure_list_recyclerview.setAdapter(eventsListAdapter);
            } else {
                l4_liesure.setVisibility(View.GONE);
            }
            if (leisureList.size() >= 3) {
                tv_leisure_seeall.setVisibility(View.VISIBLE);
            } else {
                tv_leisure_seeall.setVisibility(View.GONE);
            }
            main_layout.setVisibility(View.VISIBLE);

        } else {
            showToast(dashboardModel.message);
        }
    }

    @Override
    public void setNewDashboardData(DashboardModel dashboardModel) {
        String statusCode = dashboardModel.statusCode;
        l1_main.setVisibility(View.VISIBLE);
        /*  ArrayList<String> heading = new ArrayList<>();*/
        sessionManager.setShareUrl(dashboardModel.movieURL);
        if (listDetails.size() > 0) {
            listDetails.clear();
        }
        if (heading.size() > 0) {
            heading.clear();
        }
        if (statusCode.equals("200")) {
            if (dashboardModel.listdata != null) {
                for (int i = 0; i < dashboardModel.listdata.size(); i++) {
                    listDetails.add(dashboardModel.listdata.get(i));
                    if (dashboardModel.listdata.get(i).data.size() > 0) {
                        heading.add(dashboardModel.listdata.get(i).heading);
                    }
                }

               /*ArrayAdapter<String> headingadapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,heading);
               headerLayout.setAdapter(headingadapter);*/
            }

        }
        presenter.getQT5countryDropdown();
    }

    @Override
    public void setBanners(BannerModel bannerModel) {
        bannerModelTemp = new BannerModel();
        bannerModelTemp = bannerModel;
        presenter.getBannersByNewEvents(String.valueOf(sessionManager.getCountryID()));
    }

    @Override
    public void setBannerByNewEvents(GetNewBannerEvents getNewBannerEvents) {
        mBannerList = bannerModelTemp.items;
        ArrayList<CustomBannerDataEvents> customListBannerDataEvents = new ArrayList<>();
        customListBannerDataEvents.clear();
        for (int i = 0; i < mBannerList.size(); i++) {
            if (!mBannerList.get(i).imagetype.equals("Event")) {
                CustomBannerDataEvents customBannerDataEvents = new CustomBannerDataEvents();
                customBannerDataEvents.id = mBannerList.get(i).id;
                customBannerDataEvents.imagepath = mBannerList.get(i).imagepath;
                customBannerDataEvents.imagetype = mBannerList.get(i).imagetype;
                customBannerDataEvents.name = mBannerList.get(i).name;
                customBannerDataEvents.redirectLink = mBannerList.get(i).redirectLink;
                customBannerDataEvents.duration = mBannerList.get(i).duration;
                customBannerDataEvents.venue = "";
                customBannerDataEvents.censorRating = mBannerList.get(i).censorRating;
                customBannerDataEvents.lattitude = "";
                customBannerDataEvents.censorRating = "";
                customBannerDataEvents.isWebView = false;
                customListBannerDataEvents.add(customBannerDataEvents);
            }

        }
        if (getNewBannerEvents.status.equals("OK") && getNewBannerEvents.statusCode == 200) {
            mBannerList.clear();
//            if (sliderLayout != null) {
//                sliderLayout.removeAllSliders();
//            }
            for (int i = 0; i < getNewBannerEvents.data.size(); i++) {
                if(i<3){
                    CustomBannerDataEvents customBannerDataEvents = new CustomBannerDataEvents();
                    customBannerDataEvents.id = String.valueOf(getNewBannerEvents.data.get(i).eventId);
                    Log.d(getNewBannerEvents.data.get(i).eventTitle, String.valueOf(getNewBannerEvents.data.get(i).eventId));
                    customBannerDataEvents.imagepath = getNewBannerEvents.data.get(i).banner;
                    customBannerDataEvents.imagetype = "Event";
                    customBannerDataEvents.name = getNewBannerEvents.data.get(i).eventTitle;
                    customBannerDataEvents.redirectLink = getNewBannerEvents.data.get(i).webViewUrl;
                    customBannerDataEvents.duration = "";
                    customBannerDataEvents.venue = "";
                    customBannerDataEvents.censorRating = "";
                    customBannerDataEvents.isWebView = getNewBannerEvents.data.get(i).isWebView;
                    customListBannerDataEvents.add(customBannerDataEvents);
                }
            }
        } else {
            //  QTUtils.showDialogbox(this,getNewBannerEvents.message);

        }
        try {
            setBanner(customListBannerDataEvents);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void setBanner(ArrayList<CustomBannerDataEvents> mBannerList) throws UnsupportedEncodingException {


        sliderLayout.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sliderLayout.setLayoutManager(linearLayoutManager);
        sliderLayout.addItemDecoration(new ItemOffsetDecoration(DashBoardActivity.this, R.dimen.five, R.dimen.five, R.dimen.five, R.dimen.five));


        DashBoardCarouselBannerAdapter carouselBannerAdapter = new DashBoardCarouselBannerAdapter(mBannerList, DashBoardActivity.this);
        sliderLayout.setAdapter(carouselBannerAdapter);


        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        if (sliderLayout.getOnFlingListener() == null) {
            linearSnapHelper.attachToRecyclerView(sliderLayout);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < carouselBannerAdapter.getItemCount() - 1) {
                    linearLayoutManager.smoothScrollToPosition(sliderLayout, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                } else {
                    linearLayoutManager.smoothScrollToPosition(sliderLayout, new RecyclerView.State(), 0);
                }
            }
        }, 0, 3000);


//                DefaultSliderView textSliderView = new DefaultSliderView(DashBoardActivity.this);
//                textSliderView
//                        // .description(mBannerList.get(i).getImageTitle())
//                        .image("http:" + mBannerList.get(i).imagepath)
//                        .setOnSliderClickListener(DashBoardActivity.this);
//                textSliderView.bundle(new Bundle());
//                if (!TextUtils.isEmpty(mBannerList.get(i).id)) {
//                    textSliderView.getBundle()
//                            .putString("id", mBannerList.get(i).id);
//                    textSliderView.getBundle()
//                            .putString("imagetype", mBannerList.get(i).imagetype);
//                    textSliderView.getBundle().putString("movie_title", mBannerList.get(i).name);
//                    textSliderView.getBundle().putString("redirect_url", mBannerList.get(i).redirectLink);
//                    textSliderView.getBundle().putString("duration", mBannerList.get(i).duration);
//                    textSliderView.getBundle().putString("movie_type", "");
//                    textSliderView.getBundle().putString("movie_img_url", URLDecoder.decode(mBannerList.get(i).imagepath, "UTF-8"));
//                    textSliderView.getBundle().putString("cencor", mBannerList.get(i).censorRating);
//                }
//                textSliderView.getBundle();
//                sliderLayout.addSlider(textSliderView)
//            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
//            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
//            sliderLayout.getPagerIndicator().setVisibility(View.GONE);
//            //   sliderLayout.setCustomAnimation(new DescriptionAnimation());
//            sliderLayout.getPagerIndicator().setDefaultIndicatorColor(getResources().getColor(R.color.light_grey_text), getResources().getColor(R.color.light_grey_text));
//            sliderLayout.setDuration(3000);
//            sliderLayout.addOnPageChangeListener(DashBoardActivity.this);
//            if (mBannerList.size() > 1) {
//                sliderLayout.startAutoCycle();
//            } else {
//                sliderLayout.stopAutoCycle();
//            }

    }

    @Override
    public void setToken(TokenModel tokenModel) {

    }

    @Override
    protected void onStop() {
//        if (sliderLayout != null) {
//            sliderLayout.stopAutoCycle();
//        }
        super.onStop();
    }

    @Override
    public void setReelCinemaList(ReelCinemaMovieListModel response) {

    }

    @Override
    public void setCountry(AllCountryQT5Model response) {
        if (response.statusCode.equals("200")) {
            if (response.data != null && !response.data.isEmpty()) {
                bindCountrySpinner(response.data, sessionManager.getCountryCode());
            }
        }
    }

    @Override
    public void setCountryDropdownQt5(GetNationalityData response) {
        ArrayList<String> mCountry = new ArrayList<>();
        if (response.data.size() > 0) {
            for (int i = 0; i < response.data.size(); i++) {
                mCountry.add(response.data.get(i).name);
            }
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String json = gson.toJson(mCountry);
            editor.putString("Countries", json);
            editor.commit();
        }
        presenter.getAllEvents(sessionManager.getCountryID(), -1, "", "", 0, 0);
    }

    @Override
    public void setAllEvents(AllEventQT5Model allEventQT5Model) {

        Log.d("14nov:", "setAllEvents: Act" );

        if (allEventQT5Model.statusCode.equals("200")) {
            List<ListData> listDetailsTemp = new ArrayList<>();
            ArrayList<String> headers = new ArrayList<>();
            for (int j = 0; j < listDetails.size(); j++) {
                headers.add(listDetails.get(j).heading);
                if (listDetails.get(j).heading.equals("Movies")) {
                    listDetailsTemp.add(listDetails.get(j));
                }
            }
            dashboardAdapter = new DashboardAdapter(listDetailsTemp, headers, DashBoardActivity.this);
            list_recyclerview.setAdapter(dashboardAdapter);
            mAllEvents.clear();
            if (allEventQT5Model.data != null && !allEventQT5Model.data.isEmpty()) {
                mAllEvents.addAll(allEventQT5Model.data);
            } else {
                // event_list_recyclerview.setVisibility(View.GONE);
                QTUtils.showDialogbox(this, "" + allEventQT5Model.message);
            }

        } else if (allEventQT5Model.statusCode.equals("404")) {
            //   event_list_recyclerview.setVisibility(View.GONE);
            QTUtils.showDialogbox(this, "" + allEventQT5Model.message);
        }

        hAdapter = new headerAdapter(heading, DashBoardActivity.this);
        headerLayout.setAdapter(hAdapter);
        headerLayout.setVisibility(View.GONE);
        ArrayList<AllEventQT5Data> mAlleventsforsports = new ArrayList<>();
        ArrayList<AllEventQT5Data> mAlleventsforLeisure = new ArrayList<>();
        for (int i = 0; i < mAllEvents.size(); i++) {
            if (mAllEvents.get(i).categoryName.equals("Sports")) {
                mAlleventsforsports.add(mAllEvents.get(i));
            }
            if (mAllEvents.get(i).categoryName.equals("Leisure")) {
                mAlleventsforLeisure.add(mAllEvents.get(i));
            }
        }

        if (mAlleventsforsports.size() > 0) {
            l3_sports.setVisibility(View.VISIBLE);
            sportsID = mAlleventsforsports.get(0).categoryId;
            EvenListInHomeAdapter eventListAdapterForHome = new EvenListInHomeAdapter(DashBoardActivity.this, mAlleventsforsports);
            sports_list_recyclerview.setAdapter(eventListAdapterForHome);
        } else {
            l3_sports.setVisibility(View.GONE);
        }

        if (mAlleventsforLeisure.size() > 0) {
            l4_liesure.setVisibility(View.VISIBLE);
            leisureID = mAlleventsforLeisure.get(0).categoryId;
            EvenListInHomeAdapter eventListAdapterForHome = new EvenListInHomeAdapter(DashBoardActivity.this, mAlleventsforLeisure);
            leisure_list_recyclerview.setAdapter(eventListAdapterForHome);
        } else {
            l4_liesure.setVisibility(View.GONE);
        }
        if (mAllEvents.size() > 0) {
            l2_events.setVisibility(View.VISIBLE);
            event_list_recyclerview.setVisibility(View.VISIBLE);
            EvenListInHomeAdapter eventsListQT5Adapter = new EvenListInHomeAdapter(this, mAllEvents);
            event_list_recyclerview.setAdapter(eventsListQT5Adapter);

        }


    }

    @Override
    public void init() {

        ll_home = findViewById(R.id.ll_home);
        ll_movies = findViewById(R.id.ll_movies);
        ll_events = findViewById(R.id.ll_events);
        ll_more = findViewById(R.id.ll_more);

        tv_hometittle = findViewById(R.id.tv_hometittle);
        ic_home = findViewById(R.id.ic_home);

        tv_moviewtittle = findViewById(R.id.tv_moviewtittle);
        tv_eventtittle = findViewById(R.id.tv_eventtittle);
        tv_more = findViewById(R.id.tv_more);

        ll_home.setOnClickListener(this);
        ll_movies.setOnClickListener(this);
        ll_events.setOnClickListener(this);
        ll_more.setOnClickListener(this);

        app_bar_layout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);
        l1_main = findViewById(R.id.l1_main);
        movieLayout = findViewById(R.id.movieLayout);
        movieLayout.setVisibility(View.GONE);
        // movieLayout.setVisibility(View.VISIBLE);
        /*remove a default space of appbarlayout*/
        app_bar_layout.setOutlineProvider(null);
        /* Retrieve a PendingIntent that will perform a broadcast */
        start();
        //sessionmanager

        sessionManager.setSelectedCinema("");

        device_token = sessionManager.getDeviceToken();
       
       /* if(sessionManager.getDeviceToken() != "") {
            device_token = sessionManager.getDeviceToken();
            Log.e("device_token",sessionManager.getDeviceToken());
        } else {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if(!task.isSuccessful()){
                        return;
                    }
                    device_token = task.getResult().getToken();
                    sessionManager.setDeviceToken(device_token);
                    Log.e("device_tokennew",sessionManager.getDeviceToken());
                }
            });
        }*/


        Log.e("device_token", sessionManager.getDeviceToken());
        dohabankoffer_layout = (LinearLayout) findViewById(R.id.dohabankoffer_layout);
        // gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        sliderLayout = findViewById(R.id.slider);
        // gifImageView.setOnClickListener(this);

//        if (TextUtils.isEmpty(sessionManager.getUserId())) {
//            dohabankoffer_layout.setVisibility(View.GONE);
//            } else {
//            dohabankoffer_layout.setVisibility(View.GONE);
//            getDashboradBanner();
//
//        }

        //ids
        spinner_country = findViewById(R.id.spinner_country);
        spinnerLayout = findViewById(R.id.spinnerLayout);
        main_layout = findViewById(R.id.scroll);
        tv_movies_seeall = findViewById(R.id.tv_movies_seeall);
        tv_movies_seeall.setOnClickListener(DashBoardActivity.this);
        tv_events_seeall = findViewById(R.id.tv_events_seeall);
        tv_events_seeall.setOnClickListener(this);
        menu_icon = findViewById(R.id.menu_icon);
        ic_movies = findViewById(R.id.ic_movies);
        ic_event = findViewById(R.id.ic_event);
        menu_icon.setOnClickListener(this);
        ic_movies.setOnClickListener(this);
        ic_event.setOnClickListener(this);
        iv_search = findViewById(R.id.iv_search);
        l1_movies = findViewById(R.id.l1_movies);
        l2_events = findViewById(R.id.l2_events);
        l3_sports = findViewById(R.id.l3_sports);
        l4_liesure = findViewById(R.id.l4_liesure);
        tv_sports_seeall = findViewById(R.id.tv_sports_seeall);
        tv_sports_seeall.setOnClickListener(this);
        tv_leisure_seeall = findViewById(R.id.tv_leisure_seeall);
        tv_leisure_seeall.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        //  l1 = findViewById(R.id.l1);
        //  l1.setOnClickListener(this);
        //  l2 = findViewById(R.id.l2);
        //  l2.setOnClickListener(this);
        //   l3 = findViewById(R.id.l3);
        //  l3.setOnClickListener(this);
        //   l4 = findViewById(R.id.l4);
        //   l4.setOnClickListener(this);
        //com.production.qtickets.movies list

        setAllHomescreenicons();

        list_recyclerview = findViewById(R.id.list_recyclerview);
        //  mLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.VERTICAL, false);
        list_recyclerview.setLayoutManager(mLayoutManager);
        //  homeMoviesAdapter = new MovieListAdapter(DashBoardActivity.this, moviList, 100);
        //  movie_list_recyclerview.setAdapter(homeMoviesAdapter);
        //event list
        event_list_recyclerview = findViewById(R.id.event_list_recyclerview);
        eLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        event_list_recyclerview.setLayoutManager(eLayoutManager);
        //sport list
        sports_list_recyclerview = findViewById(R.id.sports_list_recyclerview);
        sLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sports_list_recyclerview.setLayoutManager(sLayoutManager);
        //leisure list
        leisure_list_recyclerview = findViewById(R.id.leisure_list_recyclerview);
        lLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        leisure_list_recyclerview.setLayoutManager(lLayoutManager);

        headerLayout = findViewById(R.id.headerLayout);
        hLayoutManager = new LinearLayoutManager(DashBoardActivity.this, LinearLayoutManager.HORIZONTAL, false);
        headerLayout.setLayoutManager(hLayoutManager);

        //currentlocation
//      getcurrentlocation();
       /* if (TextUtils.isEmpty(sessionManager.getCountryName())) {
            getcurrentlocation();
        } else {*/
//        QT5
        presenter.getCountry();
        presenter.getStaticLinksdata();
        //  }
        presenter.getToken(device_token, sessionManager.getCountryName());

        /*nested scrollview*/
        if (main_layout != null) {
            main_layout.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY > oldScrollY) {
                        app_bar_layout.setBackgroundColor(getResources().getColor(R.color.backcolor));
                        //Log.i(TAG, "Scroll DOWN");
                    }
                    if (scrollY < oldScrollY) {
                        //
                        //    app_bar_layout.setBackground(getDrawable(R.drawable.gradient_back));
                        //Log.i(TAG, "Scroll UP");
                    }

                    if (scrollY == 0) {
                        app_bar_layout.setBackground(getDrawable(R.drawable.gradient_back));
                        // Log.i(TAG, "TOP SCROLL");
                    }

                    if (scrollY == (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight())) {
                        app_bar_layout.setBackgroundColor(getResources().getColor(R.color.backcolor));
                        //  Log.i(TAG, "BOTTOM SCROLL");
                    }
                }
            });
        }

    }


    private void setAllHomescreenicons() {
        ic_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_homeyellow));
        tv_hometittle.setTextColor(getResources().getColor(R.color.colorAccent));

        ic_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movies));
        tv_moviewtittle.setTextColor(getResources().getColor(R.color.plane_white));


        ic_event.setImageDrawable(getResources().getDrawable(R.drawable.ic_events));
        tv_eventtittle.setTextColor(getResources().getColor(R.color.plane_white));

        menu_icon.setImageDrawable(getResources().getDrawable(R.drawable.more));
        tv_more.setTextColor(getResources().getColor(R.color.plane_white));

    }

    //get current location base on gps locations
/*
    private void getcurrentlocation() {

        if (ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Toast.makeText(DashBoardActivity.this, "You need have granted permission", Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(DashBoardActivity.this, DashBoardActivity.this);
            // Check if GPS enabled
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Geocoder geocoder = new Geocoder(DashBoardActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                    if (addresses != null) {
                        if (addresses.size() > 0) {
                            Address obj = addresses.get(0);
                            String add = obj.getAddressLine(0);
                            add = add + "\n" + obj.getCountryName();  // Country Name INDIA
                            add = add + "\n" + obj.getCountryCode();  // Country code IN
                            add = add + "\n" + obj.getAdminArea();   // State
                            add = add + "\n" + obj.getPostalCode();  // Postal code
                            add = add + "\n" + obj.getSubAdminArea(); // Bangalore Urban
                            add = add + "\n" + obj.getLocality(); // Bangalore City
                            add = add + "\n" + obj.getSubThoroughfare();
                            String countryName = obj.getCountryName();
                            String countryCode = obj.getCountryCode();
                            if (countryCode.equalsIgnoreCase("AE")) {
                                countryCode = "UAE";
                            }
                            if(!countryName.equals("Qatar") && !countryName.equals("Bahrain") && !countryName.equals("Dubai")&&
                                    !countryName.equals("United Arab Emirates") && !countryName.equals("Oman")){
                                //presenter.getToken(device_token,sessionManager.getCountryName());
                                presenter.getToken(device_token,"Qatar");
                            } else {
                                if (countryName.equals("United Arab Emirates")) {
                                    presenter.getToken(device_token,"Dubai");
                                } else {
                                    presenter.getToken(device_token,countryName);
                                }
                                presenter.getToken(device_token,countryName);
                            }
                         //  bindCountrySpinner(countryCode);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Can't get location.
                Log.v("LocationStatus", "no ");
                showPb();
                presenter.getLocationData();
                //gps.showSettingsAlert();
            }
        }
    }
*/

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(DashBoardActivity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, DashBoardActivity.this, message);
    }

    @Override
    public void onDestroy() {
//        unregisterManagers();
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    //navigating to the respective list based on the category
    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent i;
        Bundle b;
        switch (id) {
            /*case R.id.tv_movies_seeall:
                b = new Bundle();
                b.putInt("position", 0);
                i = new Intent(DashBoardActivity.this, MainActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;*/

            case R.id.tv_events_seeall:

                Intent ii = new Intent(this, EventHomeActivity.class);
                ii.putStringArrayListExtra("headerList", heading);
                ii.putExtra("position", 1);
                ii.putExtra("ImageType", "others");
                startActivity(ii);
                finish();

//               Intent intentss = new Intent(this, MainActivity.class);
//               intentss.putExtra("categoryId","7");
//               intentss.putStringArrayListExtra("headerList",heading);
//               intentss.putExtra("position",1);
//               intentss.putExtra("ImageType","others");
//               startActivity(intentss);

                break;


//            case R.id.menu_icon:
//                i = new Intent(DashBoardActivity.this, NavigationDrawerActivity.class);
//                i.putStringArrayListExtra("headerList", heading);
//                startActivity(i);
//                break;

            case R.id.ll_home:
                Intent intent = new Intent(this, DashBoardActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.ll_movies:
                Intent intentssss = new Intent(this, MainActivity.class);
                intentssss.putExtra("categoryId", "15");
                intentssss.putStringArrayListExtra("headerList", heading);
                intentssss.putExtra("position", 0);
                intentssss.putExtra("ImageType", "others");
                startActivity(intentssss);
                finish();
                break;

            case R.id.ll_events:
                Intent iii = new Intent(this, EventHomeActivity.class);
                iii.putStringArrayListExtra("headerList", heading);
                iii.putExtra("position", 1);
                iii.putExtra("ImageType", "others");
                startActivity(iii);
                finish();
                break;

            case R.id.ll_more:
                i = new Intent(DashBoardActivity.this, NavigationDrawerActivity.class);
                i.putStringArrayListExtra("headerList", heading);
                startActivity(i);
                break;


//            case R.id.ic_movies:
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("categoryId", "15");
//                intent.putStringArrayListExtra("headerList", heading);
//                intent.putExtra("position", 0);
//                intent.putExtra("ImageType", "others");
//                startActivity(intent);
//                break;
//
//
//            case R.id.ic_event:
//                Intent intentss = new Intent(this, MainActivity.class);
//                intentss.putExtra("categoryId", "15");
//                intentss.putStringArrayListExtra("headerList", heading);
//                intentss.putExtra("position", 1);
//                intentss.putExtra("ImageType", "others");
//                startActivity(intentss);
//                break;

/*            case R.id.l1:
                b = new Bundle();
                b.putInt("position", 0);
                i = new Intent(DashBoardActivity.this, MainActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.l2:
                b = new Bundle();
                b.putInt("position", 1);
                i = new Intent(DashBoardActivity.this, MainActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.l3:
                b = new Bundle();
                b.putInt("position", 2);
                i = new Intent(DashBoardActivity.this, MainActivity.class);
                i.putExtras(b);
                startActivity(i);
                break; m
            case R.id.l4:
                b = new Bundle();
                b.putInt("position", 3);
                i = new Intent(DashBoardActivity.this, MainActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;*/

            case R.id.iv_search:
                i = new Intent(DashBoardActivity.this, SearchListActivity.class);
                startActivity(i);
                break;
            case R.id.tv_sports_seeall:
                Intent iiii = new Intent(this, EventHomeActivity.class);
                iiii.putExtra("category_id", String.valueOf(sportsID));
                iiii.putExtra("category_name", "Sports");
                iiii.putStringArrayListExtra("headerList", heading);
                iiii.putExtra("position", 2);
                iiii.putExtra("ImageType", "others");
                startActivity(iiii);

                break;

            case R.id.tv_leisure_seeall:
                Intent iiiii = new Intent(this, EventHomeActivity.class);
                iiiii.putExtra("category_id", String.valueOf(leisureID));
                iiiii.putExtra("category_name", "Leisure");
                iiiii.putStringArrayListExtra("headerList", heading);
                iiiii.putExtra("position", 3);
                iiiii.putExtra("ImageType", "others");
                startActivity(iiiii);
                break;

            case R.id.gifImageView:
                break;

        }
    }

    //spinner list with country flags
//    QT5
    private void bindCountrySpinner(List<AllCountryQT5Data> data, String countryName) {
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sessionManager.setCountryCode(data.get(position).codeAlpha2);
                if (data.get(position).name.equals("UAE")) {
                    sessionManager.setCountryName("Dubai");
                } else {
                    sessionManager.setCountryName(data.get(position).name);

                }
                sessionManager.setPrefix(data.get(position).isdCode);
                sessionManager.setCountryCurrency(data.get(position).currencyCode);
                sessionManager.setCountry(data.get(position).codeAlpha2);

                sessionManager.setCountryID(data.get(position).id);
                presenter.getBanners(sessionManager.getCountryName());
                showPb();
                // presenter.getDashboardData(sessionManager.getCountryName());
                presenter.getNewDashboardData(sessionManager.getCountryName());


           //     +971

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> images = new ArrayList<>();
        List<String> country = new ArrayList<>();
        for (AllCountryQT5Data allCountryQT5Data : data) {
            images.add(allCountryQT5Data.flag);
            country.add(allCountryQT5Data.codeAlpha2);
        }

        flagDashAdapter = new FlagDashQT5Adapter(this, R.layout.item_dash_board_flag_adapter, country, images);
        spinner_country.setAdapter(flagDashAdapter);

        if (countryName.isEmpty()) {
            countryName = "QA";
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).codeAlpha2.equals(countryName)) {
                sessionManager.setCountryCode(data.get(i).codeAlpha2);
                sessionManager.setCountryName(data.get(i).name);
                sessionManager.setCountryCurrency(data.get(i).currencyCode);
                sessionManager.setCountryID(data.get(i).id);
                spinner_country.setSelection(i);
                continue;
            }
        }

        spinnerLayout.setOnClickListener(v -> {
            spinner_country.performClick();
        });

    }


/*
//    QT3
    private void bindCountrySpinner(String countryName) {
        if (countryListArray.size() == 0) {
            countryListArray.add("QA");
            //countryListArray.add("IN");
            countryListArray.add("BH");
            countryListArray.add("UAE");
            countryListArray.add("OM");
        }

        flag_list.add(R.drawable.flag_qatar);
        // flag_list.add(R.drawable.flag_india);
        flag_list.add(R.drawable.flag_bahrain);
        flag_list.add(R.drawable.flag_uae);
        flag_list.add(R.drawable.flag_oman);
        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String countryCode = parent.getItemAtPosition(position).toString();
                if (countryCode.equalsIgnoreCase("QA")) {
                    Log.v("countryCode = ", "id" + countryCode);
                    sessionManager.setCountryCode(countryCode);
                    sessionManager.setCountryName("Qatar");
                    sessionManager.setCountryCurrency("QAR");
                } else if (countryCode.equalsIgnoreCase("IN")) {
                    Log.v("countryCode = ", "id" + countryCode);
                    sessionManager.setCountryCode(countryCode);
                    sessionManager.setCountryName("India");
                    sessionManager.setCountryCurrency("INR");


                } else if (countryCode.equalsIgnoreCase("UAE")) {

                    Log.v("countryCode = ", "id" + countryCode);
                    sessionManager.setCountryCode(countryCode);
                    sessionManager.setCountryName("Dubai");
                    sessionManager.setCountryCurrency("AED");


                } else if (countryCode.equalsIgnoreCase("BH")) {

                    Log.v("countryCode = ", "id" + countryCode);
                    sessionManager.setCountryCode(countryCode);
                    sessionManager.setCountryName("Bahrain");
                    sessionManager.setCountryCurrency("BD");


                } else if (countryCode.equalsIgnoreCase("OM")) {

                    Log.v("countryCode = ", "id" + countryCode);
                    sessionManager.setCountryCode(countryCode);
                    sessionManager.setCountryName("Oman");
                    sessionManager.setCountryCurrency("OMR");


                }
//                if(QTUtils.progressDialog!=null){
//                    QTUtils.progressDialog.dismiss();
//                }
                presenter.getBanners(sessionManager.getCountryName());
                showPb();
              // presenter.getDashboardData(sessionManager.getCountryName());
               presenter.getNewDashboardData(sessionManager.getCountryName());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        flagDashAdapter = new FlagDashAdapter(this, R.layout.item_dash_board_flag_adapter, countryListArray, flag_list);
        spinner_country.setAdapter(flagDashAdapter);

        if (countryName.isEmpty()) {
            countryName = "QA";
        }

        if (!countryListArray.contains(countryName)) {
            countryName = "QA";
        }

        int spinnerPosition = flagDashAdapter.getPosition(countryName);
        spinner_country.setSelection(spinnerPosition);
        if (spinnerPosition != -1) {
            if (countryName.equalsIgnoreCase("QA")) {
                Log.v("countryCode = ", "id" + countryName);
                sessionManager.setCountryCode(countryName);
                sessionManager.setCountryName("Qatar");
                sessionManager.setCountryCurrency("QAR");

            } else if (countryName.equalsIgnoreCase("IN")) {

                Log.v("countryCode = ", "id" + countryName);
                sessionManager.setCountryCode(countryName);
                sessionManager.setCountryName("Qatar");
                sessionManager.setCountryCurrency("QAR");


            } else if (countryName.equalsIgnoreCase("UAE")) {

                Log.v("countryCode = ", "id" + countryName);
                sessionManager.setCountryCode(countryName);
                sessionManager.setCountryName("Dubai");
                sessionManager.setCountryCurrency("AED");


            } else if (countryName.equalsIgnoreCase("BH")) {

                Log.v("countryCode = ", "id" + countryName);
                sessionManager.setCountryCode(countryName);
                sessionManager.setCountryName("Bahrain");
                sessionManager.setCountryCurrency("BD");


            } else if (countryName.equalsIgnoreCase("OM")) {

                Log.v("countryCode = ", "id" + countryName);
                sessionManager.setCountryCode(countryName);
                sessionManager.setCountryName("Oman");
                sessionManager.setCountryCurrency("OMR");

            }
            spinner_country.setSelection(spinnerPosition);

        }
        spinnerLayout.setOnClickListener(v -> {
            spinner_country.performClick();
        });
    }
*/


    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            if (sliderLayout != null) {
//                sliderLayout.startAutoCycle();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        QTUtils.dohaBankOffer = "no";
//        checkForCrashes();
//        if (TextUtils.isEmpty(sessionManager.getUserId())) {
//
//            dohabankoffer_layout.setVisibility(View.GONE);
//
//        } else {
//            dohabankoffer_layout.setVisibility(View.GONE);
//
//            getDashboradBanner();
//
//        }
    }


    @Override
    public void onBackPressed() {
        finish();
        // super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

/*
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new GPSTracker(DashBoardActivity.this, DashBoardActivity.this);
                    // Check if GPS enabled
                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        Geocoder geocoder = new Geocoder(DashBoardActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                            if (addresses != null) {
                                if (addresses.size() > 0) {


                                    Address obj = addresses.get(0);
                                    String add = obj.getAddressLine(0);
                                    add = add + "\n" + obj.getCountryName();  // Country Name INDIA
                                    add = add + "\n" + obj.getCountryCode();  // Country code IN
                                    add = add + "\n" + obj.getAdminArea();   // State
                                    add = add + "\n" + obj.getPostalCode();  // Postal code
                                    add = add + "\n" + obj.getSubAdminArea(); // Bangalore Urban
                                    add = add + "\n" + obj.getLocality(); // Bangalore City
                                    add = add + "\n" + obj.getSubThoroughfare();


                                    String countryName = obj.getCountryName();
                                    String countryCode = obj.getCountryCode();
                                    if (countryName.equals("Dubai")) {
                                        countryCode = "UAE";
                                    }
                                    bindCountrySpinner(countryCode);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Can't get location.
                        Log.v("LocationStatus", "no ");
                        showPb();
                        presenter.getLocationData();
                        // gps.showSettingsAlert();
                    }
                } else {
                    Log.v("LocationStatus", "no ");
                    showPb();
                    presenter.getLocationData();
                }
                return;
            }
        }
*/
    }

    @Override
    public void onPause() {
        super.onPause();
//        unregisterManagers();
    }


//    private void checkForCrashes() {
//        CrashManager.register(this);
//    }

/*
    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }
*/

//    private void unregisterManagers() {
//        UpdateManager.unregister();
//    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String imageType = slider.getBundle().get("imagetype").toString();
        if (!TextUtils.isEmpty(imageType)) {
            if (imageType.equals("Movie")) {
                sessionManager.setMovieId(slider.getBundle().getString("id").toString());
                Intent e = new Intent(DashBoardActivity.this, MovieDetailsActivity.class);
                e.putExtra("movie_title", slider.getBundle().get("movie_title").toString());
                e.putExtra("duration", slider.getBundle().get("duration").toString());
                e.putExtra("movie_type", slider.getBundle().get("movie_type").toString());
                e.putExtra("movie_img_url", slider.getBundle().get("movie_img_url").toString());
                e.putExtra("cencor", slider.getBundle().get("cencor").toString());
                startActivity(e);
            } else if (imageType.equals("Event")) {
                Intent i = new Intent(DashBoardActivity.this, MainActivity.class);
                i.putExtra("position", 1);
                i.putStringArrayListExtra("headerList", heading);
                i.putExtra("categoryId", "15");
                startActivity(i);
            } else if (imageType.equals("AdBanner")) {
                Bundle b = new Bundle();
                b.putString("webViewURL", slider.getBundle().get("redirect_url").toString());
                b.putString("pagename", slider.getBundle().get("movie_title").toString());
                Intent i = new Intent(this, ShowWebView.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtras(b);
                startActivity(i);
            }


        }


    }

    public void start() {
        long t = System.currentTimeMillis();
        /* Set the alarm to start at 16:10 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 10);
        if (t <= calendar.getTimeInMillis()) {
            Intent alarmIntent = new Intent(DashBoardActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(DashBoardActivity.this, 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            /* Repeating on every 20 minutes interval */
            manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * 1440, pendingIntent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
