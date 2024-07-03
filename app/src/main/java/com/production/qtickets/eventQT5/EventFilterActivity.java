package com.production.qtickets.eventQT5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.eventBookingDetailQT5.EventBookingDetailQT5Activity;
import com.production.qtickets.eventBookingDetailQT5.EventDateAdapter;
import com.production.qtickets.model.AllCategoryQT5Data;
import com.production.qtickets.model.AllCategoryQT5Model;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.CarouselBannerModel;
import com.production.qtickets.utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

public class EventFilterActivity extends AppCompatActivity implements EventHomeContracter.View, View.OnClickListener, CategoryItemClickListener {

    EventHomePresenter presenter;
    ArrayList<AllCategoryQT5Data> mCategoryList = new ArrayList<>();
    RecyclerView rv_recycleviewcategory;
    EventCategoryAdapter eventCategoryAdapter;
    TextView tv_cancel, tv_restore;
    ImageView iv_select_allevent, iv_select_thisweek, iv_select_thismonth;
    LinearLayout ll_allevent, ll_thisweek, ll_thismonth;
    int select_event_date = 0;
    String select_event_name = "";
    String selected_category_id = "";
    String selected_category_name = "";
    Button btn_filter_now;
    EditText ed_minvalue, ed_maxvalue;
    Integer min_price = 0, max_price = 0;
    boolean filterApplied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_filterview);
        initview();
        listeners();
        presenter.getAllCategories();

    }

    private void initview() {
        ed_minvalue = findViewById(R.id.ed_minvalue);
        ed_maxvalue = findViewById(R.id.ed_maxvalue);

        btn_filter_now = findViewById(R.id.btn_filter_now);
        ll_allevent = findViewById(R.id.ll_allevent);
        ll_thisweek = findViewById(R.id.ll_thisweek);
        ll_thismonth = findViewById(R.id.ll_thismonth);


        iv_select_allevent = findViewById(R.id.iv_select_allevent);
        iv_select_thisweek = findViewById(R.id.iv_select_thisweek);
        iv_select_thismonth = findViewById(R.id.iv_select_thismonth);

        tv_restore = findViewById(R.id.tv_restore);
        tv_cancel = findViewById(R.id.tv_cancel);
        rv_recycleviewcategory = findViewById(R.id.rv_recycleviewcategory);
        presenter = new EventHomePresenter();
        presenter.attachView(this);
        SharedPreferences prefs = getSharedPreferences("CategoryFilter", MODE_PRIVATE);

        if (prefs.getInt("minimum_price", 0) != 0) {
            ed_minvalue.setText(String.valueOf(prefs.getInt("minimum_price", 0)));
            filterApplied = true;
        }
        if (prefs.getInt("maximum_price", 0) != 0) {
            ed_maxvalue.setText(String.valueOf(prefs.getInt("maximum_price", 0)));
            filterApplied = true;

        }
        if (!prefs.getString("selected_event_name", "").equals("")) {
            select_event_name = prefs.getString("selected_event_name", "");
            setEvents(select_event_name);
            filterApplied = true;

            Log.d("1nov", "select_event_name :" + select_event_name);

        } else {
            Log.d("1nov", "select_event_name elseeee :" + select_event_name);

        }

        if (!prefs.getString("category_id", "").equals("")) {
            selected_category_id = prefs.getString("category_id", "");
            Log.d("1nov", "selected_category_id :" + selected_category_id);
            filterApplied = true;

        } else {
            Log.d("1nov", "selected_category_id :" + "Emptyy");

        }
        setRestoreButton();

    }

    private void setRestoreButton() {
        if (filterApplied)
            tv_restore.setVisibility(View.VISIBLE);
        else
            tv_restore.setVisibility(View.GONE);
    }


    private void setCategory(String category_id) {
        eventCategoryAdapter.resetCategory(Integer.valueOf(category_id));
    }

    private void setEvents(String event_name) {

        if (event_name.equals("")) {
            iv_select_allevent.setVisibility(View.GONE);
            iv_select_thisweek.setVisibility(View.GONE);
            iv_select_thismonth.setVisibility(View.GONE);
        } else if (event_name.equals("All Event")) {
            iv_select_allevent.setVisibility(View.VISIBLE);
            iv_select_thisweek.setVisibility(View.GONE);
            iv_select_thismonth.setVisibility(View.GONE);
        } else if (event_name.equals("This Week")) {
            iv_select_allevent.setVisibility(View.GONE);
            iv_select_thisweek.setVisibility(View.VISIBLE);
            iv_select_thismonth.setVisibility(View.GONE);
        } else if (event_name.equals("This Month")) {
            iv_select_allevent.setVisibility(View.GONE);
            iv_select_thisweek.setVisibility(View.GONE);
            iv_select_thismonth.setVisibility(View.VISIBLE);
        } else {
            iv_select_allevent.setVisibility(View.GONE);
            iv_select_thisweek.setVisibility(View.GONE);
            iv_select_thismonth.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;

            case R.id.tv_restore:
                SharedPreferences.Editor editor = getSharedPreferences("CategoryFilter", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                iv_select_allevent.setVisibility(View.GONE);
                iv_select_thisweek.setVisibility(View.GONE);
                iv_select_thismonth.setVisibility(View.GONE);
                select_event_date = 0;
                select_event_name = "";
                selected_category_id = "";
                selected_category_name = "";
                eventCategoryAdapter.resetCategory(-1);
                tv_restore.setVisibility(View.GONE);
                ed_maxvalue.getText().clear();
                ed_minvalue.getText().clear();

                break;

            case R.id.ll_allevent:
                if (select_event_date == 0) ;
                select_event_date = 0;
                select_event_name = "All Event";
                iv_select_allevent.setVisibility(View.VISIBLE);
                iv_select_thisweek.setVisibility(View.GONE);
                iv_select_thismonth.setVisibility(View.GONE);
                tv_restore.setVisibility(View.VISIBLE);
                break;

            case R.id.ll_thisweek:
                if (select_event_date == 1) ;
                select_event_date = 1;
                select_event_name = "This Week";
                iv_select_allevent.setVisibility(View.GONE);
                iv_select_thisweek.setVisibility(View.VISIBLE);
                iv_select_thismonth.setVisibility(View.GONE);
                tv_restore.setVisibility(View.VISIBLE);
                break;

            case R.id.ll_thismonth:
                if (select_event_date == 2) ;
                select_event_date = 2;
                select_event_name = "This Month";
                iv_select_allevent.setVisibility(View.GONE);
                iv_select_thisweek.setVisibility(View.GONE);
                iv_select_thismonth.setVisibility(View.VISIBLE);
                tv_restore.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_filter_now:
                if (ed_minvalue.getText().toString().equals("")) {
                    min_price = 0;
                } else {
                    min_price = Integer.valueOf(ed_minvalue.getText().toString());
                }
                if (ed_maxvalue.getText().toString().equals("")) {
                    max_price = 0;
                } else {
                    max_price = Integer.valueOf(ed_maxvalue.getText().toString());
                }
                Intent intent = new Intent();
                intent.putExtra("category_id", selected_category_id);
                intent.putExtra("category_name", selected_category_name);
                intent.putExtra("selected_event", select_event_date);
                intent.putExtra("selected_event_name", select_event_name);
                intent.putExtra("minimum_price", min_price);
                intent.putExtra("maximum_price", max_price);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void listeners() {
        tv_cancel.setOnClickListener(this);
        tv_restore.setOnClickListener(this);
        ll_allevent.setOnClickListener(this);
        ll_thisweek.setOnClickListener(this);
        ll_thismonth.setOnClickListener(this);
        btn_filter_now.setOnClickListener(this);
    }


    @Override
    public void setCarouselBanners(CarouselBannerModel carouselBanners) {

    }

    @Override
    public void setCarouselBannersAgain(CarouselBannerModel carouselBanners) {

    }

    @Override
    public void setAllCategories(AllCategoryQT5Model allCategoryQT5Model) {
        mCategoryList.clear();
        if (allCategoryQT5Model.statusCode.equals("200")) {
            if (allCategoryQT5Model.data != null && !allCategoryQT5Model.data.isEmpty()) {
                mCategoryList.addAll(allCategoryQT5Model.data);
                rv_recycleviewcategory.setHasFixedSize(true);
                rv_recycleviewcategory.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                rv_recycleviewcategory.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen.zero, R.dimen.five, R.dimen.google_1x, R.dimen.five));
                eventCategoryAdapter = new EventCategoryAdapter(this, mCategoryList, this);
                rv_recycleviewcategory.setAdapter(eventCategoryAdapter);
            }
        }
        if (!selected_category_id.equals("")) {
            for (int i = 0; i < mCategoryList.size(); i++) {
                if (Integer.parseInt(selected_category_id) == mCategoryList.get(i).id) {
                    setCategory(String.valueOf(i));
                }
            }
        }
    }

    @Override
    public void setCountry(AllCountryQT5Model response) {

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
    public void onClick(int position, String value, String catname) {
        selected_category_id = value;
        selected_category_name = catname;
        tv_restore.setVisibility(View.VISIBLE);
    }
}

