package com.production.qtickets.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import com.production.qtickets.R;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;


public class ShowWebView extends Activity {

    private String url = "", pageName = "";

    //private Button button;
    private WebView webView;

    private ImageView toolbar_back;

    private TextView txt_title;

//  ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_webview);
        txt_title = findViewById(R.id.tv_toolbar_title);
        toolbar_back = findViewById(R.id.iv_back_arrow);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getString("url") != null)
                url = b.getString("url");
            if (b.getString("pagename") != null)
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

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                QTUtils.progressDialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                QTUtils.progressDialog.dismiss();

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
        QTUtils.progressDialog.dismiss();
        super.onDestroy();

    }

}