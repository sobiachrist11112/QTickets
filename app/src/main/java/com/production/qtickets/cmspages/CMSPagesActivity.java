package com.production.qtickets.cmspages;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class CMSPagesActivity extends AppCompatActivity {

    WebView webView;
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
        } catch (Exception e) {
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
        } else if (cmsType.equals("PrivacyPolicy")) {
            tv_toolbar_header.setText(getResources().getString(R.string.privacypolicy));
        } else if (cmsType.equals("FAQs")) {
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
    private void loadUrl() {
        if (cmsType.equals("TermsAndConditions")) {
            Uri uri = Uri.parse(sessionManager.getTermsLink());
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();
            String newHost = host + "/" + sessionManager.getCountryName().toLowerCase();
            String modifiedUrl = scheme + "://" + newHost + path.toLowerCase();
            cmsTypeURL = modifiedUrl;

        } else if (cmsType.equals("AboutUS")) {
            Uri uri = Uri.parse(sessionManager.getAboutUsLink());
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();
            String newHost = host + "/" + sessionManager.getCountryName().toLowerCase();
            String modifiedUrl = scheme + "://" + newHost + path.toLowerCase();
            cmsTypeURL = modifiedUrl;

        } else if (cmsType.equals("PrivacyPolicy")) {
            Uri uri = Uri.parse(sessionManager.getPrivacyLink());
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();
            String newHost = host + "/" + sessionManager.getCountryName().toLowerCase();
            String modifiedUrl = scheme + "://" + newHost + path.toLowerCase();
            cmsTypeURL = modifiedUrl;

        } else if (cmsType.equals("FAQs")) {
            Uri uri = Uri.parse(sessionManager.getFaqLink());
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();
            String newHost = host + "/" + sessionManager.getCountryName().toLowerCase();
            String modifiedUrl = scheme + "://" + newHost + path.toLowerCase();
            cmsTypeURL = modifiedUrl;

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
        Log.d("cmsTypeURL", cmsTypeURL);

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);

                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(url));
                    startActivity(intent);
                }
                if (url.startsWith("mailto:")) {
                    sendEmail(url.substring(7));
                    return true;
                } else if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
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
                if (progressDialog != null)
                    progressDialog.dismiss();
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(url);

    }

    private void sendEmail(String add) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + add)); // only email apps should handle this
//      intent.putExtra(Intent.EXTRA_EMAIL, "info@qtickets.com");
//      intent.putExtra(Intent.EXTRA_SUBJECT, data.eventName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

      /*  Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{add});
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CMSPagesActivity.this,
                            "There are no email clients installed.", Toast.LENGTH_SHORT)
                    .show();
        }*/

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
//      presenter.detach();
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
