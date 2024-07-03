package com.production.qtickets.events;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.production.qtickets.R;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.eventBookingConfirmQT5.EventBookingConfirmQT5Activity;
import com.production.qtickets.model.BookingQT5Data;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import java.io.Serializable;
import java.util.List;

public class PaymentWebView extends Activity {
    private String url = "", pageName = "";

    //private Button button;
    private WebView webView;

    private ImageView toolbar_back;

    BookingQT5Data data = null;
    String eventTime;
    List<EventTicketQT5Type> typeList;

    private TextView txt_title;
    ProgressDialog progressDialog;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_webview);
        txt_title = findViewById(R.id.tv_toolbar_title);
        toolbar_back = findViewById(R.id.iv_back_arrow);
        Bundle b = getIntent().getExtras();

        //
        data = (BookingQT5Data) getIntent().getSerializableExtra("DATA");
        txt_title.setText( getIntent().getStringExtra("txt_title"));
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);
        if (b != null) {
            url = b.getString("webViewURL");
            pageName = b.getString("pagename");
        }
        //Get webview
        webView = findViewById(R.id.webView);
        startWebView(url);

        data = (BookingQT5Data) getIntent().getSerializableExtra("DATA");
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);


        toolbar_back.setOnClickListener(view -> backPress());
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
                    progressDialog = new ProgressDialog(PaymentWebView.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (url.equals("https://uate.q-tickets.com/EventBookingSuccess")) {
                    Intent intent = new Intent(getApplicationContext(), EventBookingConfirmQT5Activity.class);
                    intent.putExtra("DATA", data);
                    intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                    intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                    startActivityForResult(intent, 003);
                }
                //https://uate.q-tickets.com/EventBookingFailed
                if (url.equals("https://events.q-tickets.com/EventBookingFailed")) {
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
            backPress();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onBackPressed();
    }


    private void backPress(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Do you want to cancel this transaction?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              finish();
                            }
                        }).setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // finish();
                                dialog.dismiss();
                            }
                        }).create();
        dialog.show();
    }

    @Override
    public void onPause() {
        webView.clearCache(true);
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        webView.clearCache(true);
        super.onStop();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();

    }
}
