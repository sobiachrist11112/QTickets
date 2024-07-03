package com.production.qtickets.newsfeed;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.NewsFeedModel;
import com.production.qtickets.utils.QTUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsFeedActivity extends AppCompatActivity implements NewsFeedContracter.View{


    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    com.production.qtickets.utils.TextviewBold tvToolbarTitle;
    @BindView(R.id.newsfeed_recyclerview)
    RecyclerView newsfeed_recyclerview;
    @BindView(R.id.heading)
    LinearLayout heading;
    @BindView(R.id.c1)
    ConstraintLayout c1;
    NewsFeedPresenter presenter;
    LinearLayoutManager recyclerViewLayoutManager;
    NewsFeedAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        com.production.qtickets.utils.QTUtils.setStatusBarGradiant(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_activity);
        ButterKnife.bind(this);
        presenter = new NewsFeedPresenter();
        presenter.attachView(NewsFeedActivity.this);
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

    //get the news feed details from the server
    @Override
    public void setData(List<NewsFeedModel> newsFeedModels) {
        if(newsFeedModels.size()>0) {
            adapter = new NewsFeedAdapter(NewsFeedActivity.this, newsFeedModels);
            newsfeed_recyclerview.setAdapter(adapter);
            c1.setVisibility(View.GONE);
            newsfeed_recyclerview.setVisibility(View.VISIBLE);
        }else {
            c1.setVisibility(View.GONE);
            newsfeed_recyclerview.setVisibility(View.GONE);
        }
    }

    //intitialize the views
    @Override
    public void init() {
        tvToolbarTitle.setText(getResources().getString(R.string.nav_newsfeed));
        recyclerViewLayoutManager = new LinearLayoutManager(NewsFeedActivity.this);
        newsfeed_recyclerview.setLayoutManager(recyclerViewLayoutManager);
        heading.setVisibility(View.GONE);
        showPb();
        presenter.getData();

    }

    @Override
    public void dismissPb() {
        com.production.qtickets.utils.QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        com.production.qtickets.utils.QTUtils.showProgressDialog(NewsFeedActivity.this, true);
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
        com.production.qtickets.utils.QTUtils.showAlertDialogbox(object1, object3, NewsFeedActivity.this, message);
    }
    @Override
    public void onDestroy(){
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }


}
