package com.production.qtickets.mybookings;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.GetHistoryData;
import com.production.qtickets.model.Items;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.model.MovieModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyBookingsActivity extends AppCompatActivity implements MyBookingsContracter.View {
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.movielist_recyclerview)
    RecyclerView movielistRecyclerview;
    MyBookingsPresenter presenter;
    LinearLayoutManager recyclerViewLayoutManager;
    MyBookingAdapter adapter;
    //sessionmanager
    SessionManager sessionManager;
    @BindView(R.id.movie_bookings)
    TextView movie_bookings;
    @BindView(R.id.event_bookings)
    TextView event_bookings;
    @BindView(R.id.c1)
    ConstraintLayout img_no_bookings;
    ArrayList<GetHistoryData> myEventsList=new ArrayList<>();
    ArrayList<MovieModel> myBookingsModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        QTUtils.setStatusBarGradiant(this);
        ButterKnife.bind(this);
        presenter = new MyBookingsPresenter();
        presenter.attachView(MyBookingsActivity.this);
        init();
    }

    //set the selected movie details before going to make payment
    @Override
    public void setData(Items items) {
        myBookingsModelList=items.data;
        myEventsList.clear();
        if(myBookingsModelList.size()>0) {
            adapter = new MyBookingAdapter(MyBookingsActivity.this, myEventsList, myBookingsModelList,200);
            movielistRecyclerview.setAdapter(adapter);
            movielistRecyclerview.setVisibility(View.VISIBLE);
            img_no_bookings.setVisibility(View.GONE);
        }else {
            img_no_bookings.setVisibility(View.VISIBLE);
            movielistRecyclerview.setVisibility(View.GONE);
        }

    }

    @Override
    public void setEventsData(ArrayList<GetHistoryData> myBookingsModelListss) {
        //this is for events booking details
        myEventsList=myBookingsModelListss;
        adapter = new MyBookingAdapter(MyBookingsActivity.this,myEventsList,myBookingsModelList,100);
        movielistRecyclerview.setAdapter(adapter);
        movielistRecyclerview.setVisibility(View.VISIBLE);
        img_no_bookings.setVisibility(View.GONE);
    }


    //intilize the views
    @Override
    public void init() {
        sessionManager = new SessionManager(MyBookingsActivity.this);

        tvToolbarTitle.setText(getResources().getString(R.string.nav_mybook));
        recyclerViewLayoutManager = new LinearLayoutManager(MyBookingsActivity.this);
        movielistRecyclerview.setLayoutManager(recyclerViewLayoutManager);
        showPb();
        presenter.getData(MyBookingsActivity.this,sessionManager.getUserId());
        //presenter.getEvents(MyBookingsActivity.this,sessionManager.getUserId());

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(MyBookingsActivity.this, true);
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
        }else if(throwable instanceof ConnectException){
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        }else {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, MyBookingsActivity.this, message);
    }

    @OnClick({R.id.iv_back_arrow,R.id.event_bookings,R.id.movie_bookings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
               finish();
                break;
                //for events booking details
            case R.id.event_bookings:
                event_bookings.setBackground(getResources().getDrawable(R.drawable.drawable_blue_event_background));
                movie_bookings.setBackground(getResources().getDrawable(R.drawable.movie_unselected_button_back));
                showPb();
                presenter.getEvents(MyBookingsActivity.this,sessionManager.getUserId());
                break;
                //for movie booking details
            case R.id.movie_bookings:
                movie_bookings.setBackground(getResources().getDrawable(R.drawable.drawable_blue_event_background));
                event_bookings.setBackground(getResources().getDrawable(R.drawable.movie_unselected_button_back));
                showPb();
                presenter.getData(MyBookingsActivity.this,sessionManager.getUserId());
                break;
        }
    }
    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }
}
