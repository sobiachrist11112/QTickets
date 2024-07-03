package com.production.qtickets.cmspages;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.model.CmsList;
import com.production.qtickets.model.StatusModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import im.delight.android.webview.AdvancedWebView;


/**
 * Created by hemanth on 4/9/2018.
 */

public class CMSPagesActivity extends AppCompatActivity {

    AdvancedWebView webView;
    String cmsType = "";
    TextView tv_toolbar_header;
    ImageView iv_back_contactus;
//    CMSpagePresenter presenter;
    String cmsTypeURL;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.terms_conditions_activity);
//        presenter = new CMSpagePresenter();
//        presenter.attachView(CMSPagesActivity.this);
        sessionManager = new SessionManager(CMSPagesActivity.this);
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init() {
        webView = findViewById(R.id.webView);
        iv_back_contactus = findViewById(R.id.iv_back_arrow);
        tv_toolbar_header = findViewById(R.id.tv_toolbar_title);
        cmsType = getIntent().getExtras().getString("cmsType");
        if (cmsType.equals("TermsAndConditions")) {
            tv_toolbar_header.setText(getResources().getString(R.string.terms));
        } else if (cmsType.equals("AboutUS")) {
            tv_toolbar_header.setText(getResources().getString(R.string.about));
        } else if (cmsType.equals("PrivacyPolicy")){
            tv_toolbar_header.setText(getResources().getString(R.string.privacypolicy));
        }
        else if (cmsType.equals("FAQs")){
            tv_toolbar_header.setText(getResources().getString(R.string.faqs));
        }
        loadUrl();
        //Calling CMS API for different data
//        presenter.getCMSData(CMSPagesActivity.this);
        iv_back_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CMSPagesActivity.this.finish();
            }
        });


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadUrl(){
        if (cmsType.equals("TermsAndConditions")) {
//            cmsTypeURL=cmsList.get(1).pageUrl;
            cmsTypeURL=sessionManager.getTermsLink();

        } else if (cmsType.equals("AboutUS")) {
//            cmsTypeURL=cmsList.get(0).pageUrl;
            cmsTypeURL=sessionManager.getAboutUsLink();

        }else  if(cmsType.equals("PrivacyPolicy"))
        {
//            cmsTypeURL=cmsList.get(2).pageUrl;
            cmsTypeURL=sessionManager.getPrivacyLink();

        }
        else  if(cmsType.equals("FAQs"))
        {
//            cmsTypeURL=cmsList.get(2).pageUrl;
            cmsTypeURL=sessionManager.getFaqLink();

        }
       /* WebSettings webSettings = webView.getSettings();
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);


        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(cmsTypeURL);*/
        startWebView(cmsTypeURL);
        Log.d("cmsTypeURL",cmsTypeURL);

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(CMSPagesActivity.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                if(progressDialog!=null)
                progressDialog.dismiss();
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }

    public void dismissPb() {
       QTUtils.dismissProgressDialog();
    }

    public void showPb() {
        QTUtils.showProgressDialog(CMSPagesActivity.this, true);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void onDestroy() {
        QTUtils.dismissProgressDialog();
//        presenter.detach();
        super.onDestroy();
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}
