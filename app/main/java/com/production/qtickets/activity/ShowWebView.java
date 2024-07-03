package com.production.qtickets.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.utils.QTUtils;

import im.delight.android.webview.AdvancedWebView;


/**
 * Created by bhagya on 10-01-2018.
 */

public class ShowWebView extends Activity {
    private String url = "", pageName = "";

    //private Button button;
    private AdvancedWebView webView;

    private ImageView toolbar_back;

    private TextView txt_title;
    ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_webview);
        txt_title = findViewById(R.id.tv_toolbar_title);
        toolbar_back = findViewById(R.id.iv_back_arrow);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            url = b.getString("webViewURL");
            pageName = b.getString("pagename");
        }
        txt_title.setText(pageName);
        //Get webview
        webView = findViewById(R.id.webView);
        startWebView(url);
        toolbar_back.setOnClickListener(view -> onBackPressed());
    }


    private void startWebView(String url) {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {



                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(ShowWebView.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (url.equals("https://uate.q-tickets.com/Events")) {
                    finish();
                }
                progressDialog.dismiss();
            }

        });


        //Load url in webview
        webView.loadUrl(url);

    }


    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        webView.clearCache(true);
        super.onPause();
    }

    @Override
    public void onStop() {
        webView.clearCache(true);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();

    }
}