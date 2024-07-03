package com.production.qtickets.eventQT5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.adapters.FlagDashQT5Adapter;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.AllCategoryQT5Data;
import com.production.qtickets.model.AllCategoryQT5Model;
import com.production.qtickets.model.AllCountryQT5Data;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.CarouselBannerModel;
import com.production.qtickets.searchList.SearchListActivity;
import com.production.qtickets.utils.ItemOffsetDecoration;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.view.WrapContentHeightViewPager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EventHomeActivity extends AppCompatActivity implements View.OnClickListener, EventHomeContracter.View {

    //tablayout
    private TabLayout tabLayout;
    //Viewpager
    private WrapContentHeightViewPager viewPager;
    //int
    private int position = 0;
    //string
    private String image, header, categoryId, imageType;
    //Adapter
    private ViewPageQT5Adapter adapter;
    //    SliderLayout sliderLayout;
    RecyclerView recycler;
    EventHomePresenter presenter;
    LinearLayoutManager linearLayoutManager;
    FloatingActionButton fb_filter;
    private final static int MY_REQUEST_CODE = 1;
    String selected_cat_id = "";
    String event_name = "", selected_category_name = "";
    Integer selected_min_price = 0, selected_max_price = 0;
    Integer selected_days = 0;
    List<AllCategoryQT5Data> allCategoryQT5Modeltemp = null;


    private LinearLayout ll_home, ll_movies, ll_events, ll_more;
    private TextView tv_hometittle, tv_moviewtittle, tv_eventtittle, tv_more;
    private ImageView ic_home, ic_movies, ic_event, menu_icon;
    private ArrayList<String> heading = new ArrayList<>();


    ImageView iv_search;
    RelativeLayout spinnerLayout;

    //spinner
    Spinner spinner_country;

    //sessionmanager
    SessionManager sessionManager;
    FlagDashQT5Adapter flagDashAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        ll_home = findViewById(R.id.ll_home);
        ll_movies = findViewById(R.id.ll_movies);
        ll_events = findViewById(R.id.ll_events);
        ll_more = findViewById(R.id.ll_more);

        iv_search = findViewById(R.id.iv_search);
        spinnerLayout = findViewById(R.id.spinnerLayout);
        spinner_country = findViewById(R.id.spinner_country);

        ic_home = findViewById(R.id.ic_home);
        menu_icon = findViewById(R.id.menu_icon);
        ic_movies = findViewById(R.id.ic_movies);
        ic_event = findViewById(R.id.ic_event);

        tv_hometittle = findViewById(R.id.tv_hometittle);
        tv_moviewtittle = findViewById(R.id.tv_moviewtittle);
        tv_eventtittle = findViewById(R.id.tv_eventtittle);
        tv_more = findViewById(R.id.tv_more);

        ll_home.setOnClickListener(this);
        ll_movies.setOnClickListener(this);
        ll_events.setOnClickListener(this);
        ll_more.setOnClickListener(this);

        iv_search.setOnClickListener(this);

        sessionManager = new SessionManager(EventHomeActivity.this);

        setAllHomescreenicons();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getStringArrayList("headerList") != null) {
                heading = b.getStringArrayList("headerList");
            }
        }

        if (getIntent() != null) {
            selected_cat_id = getIntent().getStringExtra("category_id");
            selected_category_name = getIntent().getStringExtra("category_name");
        }

        viewPager = (WrapContentHeightViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        fb_filter = (FloatingActionButton) findViewById(R.id.fb_filter);
        presenter = new EventHomePresenter();
        presenter.attachView(this);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(EventHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.addItemDecoration(new ItemOffsetDecoration(EventHomeActivity.this, R.dimen.five, R.dimen.five, R.dimen.five, R.dimen.five));
//        sliderLayout  = (SliderLayout) findViewById(R.id.slider);
        fb_filter.setOnClickListener(this);
        //     presenter.getCarouselBanners();
        spinnerLayout.setOnClickListener(v -> {
            spinner_country.performClick();
        });
        presenter.getCountry();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                Intent inte = new Intent(EventHomeActivity.this, SearchListActivity.class);
                startActivity(inte);
                break;

            case R.id.fb_filter: {
                Intent i = new Intent(this, EventFilterActivity.class);
                startActivityForResult(i, MY_REQUEST_CODE);
                break;
            }

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
                Intent intentss = new Intent(this, EventHomeActivity.class);
                intentss.putExtra("categoryId", "15");
                intentss.putStringArrayListExtra("headerList", heading);
                intentss.putExtra("position", 1);
                intentss.putExtra("ImageType", "others");
                startActivity(intentss);
                finish();
                break;

            case R.id.ll_more:
                Intent i = new Intent(this, NavigationDrawerActivity.class);
                i.putStringArrayListExtra("headerList", heading);
                startActivity(i);
                break;

        }

    }

    private void setAllHomescreenicons() {

        ic_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        tv_hometittle.setTextColor(getResources().getColor(R.color.plane_white));

        ic_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movies));
        tv_moviewtittle.setTextColor(getResources().getColor(R.color.plane_white));

        ic_event.setImageDrawable(getResources().getDrawable(R.drawable.ic_eventyellow));
        tv_eventtittle.setTextColor(getResources().getColor(R.color.colorAccent));

        menu_icon.setImageDrawable(getResources().getDrawable(R.drawable.more));
        tv_more.setTextColor(getResources().getColor(R.color.plane_white));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MY_REQUEST_CODE) {
                if (data != null) {
                    selected_cat_id = data.getStringExtra("category_id");
                    selected_days = data.getIntExtra("selected_event", 0);
                    selected_min_price = data.getIntExtra("minimum_price", 0);
                    selected_max_price = data.getIntExtra("maximum_price", 0);
                    event_name = data.getStringExtra("selected_event_name");
                    selected_category_name = data.getStringExtra("category_name");

//                    if(!selected_category_name.equals("")){
//                        if(selected_cat_id)
//
//                    }
                    setupViewPager(event_name, viewPager, selected_cat_id, selected_days, allCategoryQT5Modeltemp, selected_min_price, selected_max_price, data);
                }

            }
        }

    }


    private void setupViewPagerTemp(WrapContentHeightViewPager viewPager, String cat_id, Integer selected_day, List<AllCategoryQT5Data> data, Integer min_price, Integer max_price, Intent datssa) {
        Bundle webssss = new Bundle();
        TabLayout.Tab tabssss = tabLayout.getTabAt(0);
        EventsListQT5Fragment webinarsss = new EventsListQT5Fragment();
        adapter = new ViewPageQT5Adapter(getSupportFragmentManager(), getApplicationContext(), tabLayout.getTabCount());

        if (datssa != null) {
            SharedPreferences.Editor editor = getSharedPreferences("CategoryFilter", MODE_PRIVATE).edit();
            editor.putString("category_id", datssa.getStringExtra("category_id"));
            editor.putInt("selected_event", datssa.getIntExtra("selected_event", 0));
            editor.putInt("minimum_price", datssa.getIntExtra("minimum_price", 0));
            editor.putInt("maximum_price", datssa.getIntExtra("maximum_price", 0));
            editor.apply();
            //    webinarsss.setArguments(webssss);
        }
        adapter.addFragment(webinarsss, data.get(0).name);
        if (tabssss != null) {
            tabssss.setCustomView(adapter.getTabView(data.get(0).name, data.get(0).icon));
        }
        viewPager.setAdapter(adapter);
        /*viewPager.setCurrentItem(position);*/
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        viewPager.setCurrentItem(position);
//        image = String.valueOf(R.drawable.ic_events);
//        adapter.SetOnSelectView(tabLayout, position, image, data.get(0).name);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() != null) {
                    image = String.valueOf(R.drawable.ic_events);
                    adapter.SetOnSelectView(tabLayout, tab.getPosition(), image, tab.getText().toString());
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText() != null) {
                    image = String.valueOf(R.drawable.ic_events);
                    adapter.SetUnSelectView(tabLayout, tab.getPosition(), image, tab.getText().toString());
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    private void setupViewPager(String event_name, WrapContentHeightViewPager viewPager, String cat_id, Integer selected_day, List<AllCategoryQT5Data> data, Integer min_price, Integer max_price, Intent datsss) {
        SharedPreferences.Editor editor = getSharedPreferences("CategoryFilter", MODE_PRIVATE).edit();
        Bundle web = new Bundle();
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        EventsListQT5Fragment webinar = new EventsListQT5Fragment();
        adapter = new ViewPageQT5Adapter(getSupportFragmentManager(), getApplicationContext(), tabLayout.getTabCount());
        web.putInt("CategoryID", data.get(0).id);
        web.putInt("CategoryID22", data.get(1).id);
        web.putInt("CategoryID33", data.get(2).id);
        web.putInt("CategoryID44", data.get(2).id);
        if (datsss != null) {
            editor.putString("selected_event_name", datsss.getStringExtra("selected_event_name"));
            if(datsss.getStringArrayExtra("category_id")!=null){
                editor.putString("category_id", datsss.getStringExtra("category_id"));
            }else {
                editor.putString("category_id", selected_cat_id);
            }
            editor.putInt("selected_event", datsss.getIntExtra("selected_event", 0));
            editor.putInt("minimum_price", datsss.getIntExtra("minimum_price", 0));
            editor.putInt("maximum_price", datsss.getIntExtra("maximum_price", 0));
            editor.putString("category_name", datsss.getStringExtra("category_name"));
            editor.apply();

            //    webinarsss.setArguments(webssss);
        } else {
            editor.clear();
            editor.apply();
        }
        webinar.setArguments(web);
        adapter.addFragment(webinar, data.get(0).name);
        if (tab != null) {
            tab.setCustomView(adapter.getTabView(data.get(0).name, data.get(0).icon));
        }
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() != null) {
                    image = String.valueOf(R.drawable.ic_events);
                    adapter.SetOnSelectView(tabLayout, tab.getPosition(), image, tab.getText().toString());
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText() != null) {
                    image = String.valueOf(R.drawable.ic_events);
                    adapter.SetUnSelectView(tabLayout, tab.getPosition(), image, tab.getText().toString());
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


/*
    public void setBanner(List<CarouselBannerData> banner){
        if(sliderLayout != null){
            sliderLayout.removeAllSliders();
        }
        for (int i = 0; i < banner.size(); i++) {
//            DefaultSliderView textSliderView = new DefaultSliderView(EventHomeActivity.this);
            CustomSliderView textSliderView = new CustomSliderView(EventHomeActivity.this);
            textSliderView
                    // .description(mBannerList.get(i).getImageTitle())
                    .image(banner.get(i).banner);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle();
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
        sliderLayout.getPagerIndicator().setVisibility(View.GONE);
        //   sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.getPagerIndicator().setDefaultIndicatorColor(getResources().getColor(R.color.light_grey_text), getResources().getColor(R.color.light_grey_text));
        sliderLayout.setDuration(3000);
//        sliderLayout.addOnPageChangeListener(EventHomeActivity.this);
        if (banner.size() > 1) {
            sliderLayout.startAutoCycle();
        } else {
            sliderLayout.stopAutoCycle();
        }
    }
*/

    @Override
    public void setCarouselBanners(CarouselBannerModel carouselBanners) {

        presenter.getAllCategories();
        if (carouselBanners.statusCode.equals("200")) {
//            setBanner(carouselBanners.data);
            if (carouselBanners.data != null && !carouselBanners.data.isEmpty()) {
                CarouselBannerAdapter carouselBannerAdapter = new CarouselBannerAdapter(carouselBanners.data, EventHomeActivity.this);
                recycler.setAdapter(carouselBannerAdapter);
                LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
                if (recycler.getOnFlingListener() == null) {
                    linearSnapHelper.attachToRecyclerView(recycler);
                }
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < carouselBannerAdapter.getItemCount() - 1) {
                            linearLayoutManager.smoothScrollToPosition(recycler, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);//30sep
                        } else {
                            linearLayoutManager.smoothScrollToPosition(recycler, new RecyclerView.State(), 0);
                        }
                    }
                }, 0, 3000);
            }
        }
    }


    @Override
    public void setAllCategories(AllCategoryQT5Model allCategoryQT5Model) {
        if (allCategoryQT5Model.statusCode.equals("200")) {
            allCategoryQT5Modeltemp = allCategoryQT5Model.data;

            if (selected_category_name != null) {
                if (selected_cat_id==null) {
                    for (int i = 0; i < allCategoryQT5Model.data.size(); i++) {
                        if (selected_category_name.equals(allCategoryQT5Model.data.get(i).name)) {
                            selected_cat_id = String.valueOf(allCategoryQT5Model.data.get(i).id);
                        }
                    }
                }
            }

            if (allCategoryQT5Model.data != null && !allCategoryQT5Model.data.isEmpty()) {
                if (selected_cat_id != null) {
                    setupViewPager("", viewPager, selected_cat_id, selected_days, allCategoryQT5Model.data, selected_min_price, selected_max_price, getIntent());
                } else if (selected_category_name != null) {
                    if (!selected_category_name.equals("")) {
                        setupViewPager("", viewPager, selected_cat_id, selected_days, allCategoryQT5Model.data, selected_min_price, selected_max_price, getIntent());
                    }
                } else {
                    setupViewPager("", viewPager, selected_cat_id, selected_days, allCategoryQT5Model.data, selected_min_price, selected_max_price, null);
                }
            }
        }
    }

    @Override
    public void setCountry(AllCountryQT5Model response) {
        if (response.statusCode.equals("200")) {
            if (response.data != null && !response.data.isEmpty()) {
                bindCountrySpinner(response.data, sessionManager.getCountryCode());
            }
        }

    }

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
                sessionManager.setCountryCurrency(data.get(position).currencyCode);
                sessionManager.setCountryID(data.get(position).id);
                sessionManager.setCountry(data.get(position).codeAlpha2);

                presenter.getCarouselBanners(sessionManager.getCountryID());
                //  setupViewPager();
                //  EventsListQT5Fragment fragment = (EventsListQT5Fragment) getFragmentManager().findFragmentById(R.id.example_fragment);
                //fragment.<specific_function_name>();
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


    @Override
    public void init() {

    }

    @Override
    public void dismissPb() {
        //   QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        // QTUtils.showProgressDialog(EventHomeActivity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, EventHomeActivity.this, message);
    }
}