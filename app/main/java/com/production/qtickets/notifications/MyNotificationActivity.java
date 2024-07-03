package com.production.qtickets.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.NotificationModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MyNotificationActivity extends AppCompatActivity implements NotificationContracter.View{

    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.no_notification)
    TextView no_notification;
    @BindView(R.id.movielist_recyclerview)
    RecyclerView movielistRecyclerview;
    @BindView(R.id.heading)
    LinearLayout heading;
  /*  @BindView(R.id.c1)
    ConstraintLayout c1;*/
    NotificationPresenter presenter;
    LinearLayoutManager recyclerViewLayoutManager;
    NotificationAdapter adapter;
    SessionManager sessionManager;

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;
    ArrayList<String> str = new ArrayList<>();
    String countryName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        QTUtils.setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);
        ButterKnife.bind(this);
        presenter = new NotificationPresenter();
        presenter.attachView(MyNotificationActivity.this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if(b.getStringArrayList("headerList") != null){
                str = b.getStringArrayList("headerList");
            }
        }
        init();

    }

    @OnClick(R.id.iv_back_arrow)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                finish();
                break;
        }
    }

    //list the notifications list from the server
    @Override
    public void setData(List<NotificationModel> notificationModel) {
        if(notificationModel.size()>0) {
            adapter = new NotificationAdapter(MyNotificationActivity.this, notificationModel,str);
            movielistRecyclerview.setAdapter(adapter);
         //   c1.setVisibility(View.GONE);
            movielistRecyclerview.setVisibility(View.VISIBLE);
            no_notification.setVisibility(View.GONE);
        }else {
           // c1.setVisibility(View.GONE);
            movielistRecyclerview.setVisibility(View.GONE);
            no_notification.setVisibility(View.VISIBLE);

        }
    }

    //intitialize the views
    @Override
    public void init() {

        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefmPrefs.edit();
        editor.putInt("notificationId", 0);
        editor.commit();

        sessionManager = new SessionManager(this);



        ShortcutBadger.applyCount(MyNotificationActivity.this, 0);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();


        tvToolbarTitle.setText(getResources().getString(R.string.nav_notification));
        recyclerViewLayoutManager = new LinearLayoutManager(MyNotificationActivity.this);
        movielistRecyclerview.setLayoutManager(recyclerViewLayoutManager);
        heading.setVisibility(View.GONE);
        if(sessionManager.getCountryCode().equals("UAE")){
           countryName="United Arab Emirates";
        } else {
            countryName=sessionManager.getCountryName();
        }
        showPb();
        presenter.getData(countryName);
        sessionManager.setBadgeCount(0);

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(MyNotificationActivity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, MyNotificationActivity.this, message);
    }
    @Override
    public void onDestroy(){
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }


}
