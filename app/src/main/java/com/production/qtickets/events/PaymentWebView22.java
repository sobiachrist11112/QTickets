package com.production.qtickets.events;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.production.qtickets.R;
import com.production.qtickets.eventBookingConfirmQT5.EventBookingConfirmQT5Activity;
import com.production.qtickets.model.BookingQT5Data;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import java.io.Serializable;
import java.util.List;

public class PaymentWebView22 extends Activity {


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
        txt_title.setText(getIntent().getStringExtra("txt_title"));
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);
        if (b != null) {
            url = b.getString("webViewURL");
            pageName = b.getString("pagename");
        }
        //Get webview
        webView = findViewById(R.id.webViewforEvents);
        data = (BookingQT5Data) getIntent().getSerializableExtra("DATA");
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);
        toolbar_back.setOnClickListener(view -> backPress());
        startWebView(url);
    }


    private void startWebView(String url) {

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.clearCache(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        // Set User Agent
     //   userAgent = System.getProperty("http.agent");
     //   webView.getSettings().setUserAgentString(userAgent + "Q-Tickets");

        webView.setWebViewClient(new WebViewClient() {
            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("PaymentWebUrl : ",url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("mailto:")) {
                    sendEmail(url.substring(7));
                    return true;
                } else if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                if (url.contains(AppConstants.EVENTBOOKINGFAILEDURL)) {
                    Log.d("payment13Oct", AppConstants.EVENTBOOKINGFAILEDURL);
                    showPaymentFailedDialog();
                }
//                else if (url.contains(AppConstants.QTICKETSURL)) {
//                    gotToHome();
//                }
                else if (url.contains(AppConstants.EVENTSSURL)) {
                    gotToHome();
                } else if (url.contains(AppConstants.EVENTBOOKINGSUCESS)) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), EventBookingConfirmQT5Activity.class);
                    intent.putExtra("DATA", data);
                    intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                    intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                    startActivity(intent);
                }
                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(PaymentWebView22.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }

            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }

        });
        webView.loadUrl(url);
    }

    private void showPaymentFailedDialog() {

        AlertDialog loginAlertDialog;

        // 13oct need to show the dialog here first...
        LayoutInflater li = LayoutInflater.from(PaymentWebView22.this);
        View popupDialog = li.inflate(R.layout.login_bookingfailed_dailog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentWebView22.this);
        alertDialogBuilder.setView(popupDialog);
        loginAlertDialog = alertDialogBuilder.create();
        loginAlertDialog.setCancelable(false);
        loginAlertDialog.setCanceledOnTouchOutside(false);
        loginAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView iv_clostdialog = (ImageView) popupDialog.findViewById(R.id.iv_closedialog);
        iv_clostdialog.setVisibility(View.GONE);
        TextView signupButton = popupDialog.findViewById(R.id.button_signin);
        TextView alert_text = popupDialog.findViewById(R.id.alert_text);
        alert_text.setText(getString(R.string.yourbookingisfailed));

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAlertDialog.dismiss();
                finish();

            }
        });

        alertDialogBuilder.setCancelable(false);
        loginAlertDialog.setCancelable(false);
        loginAlertDialog.show();


    }


    private void gotToHome() {

//        Intent in = new Intent(PaymentWebView.this, DashBoardActivity.class);
////      in.putExtra("splash",false);
//        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(in);
        finish();
    }


    private void sendEmail(String add) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + add));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

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


    private void backPress() {
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

}

