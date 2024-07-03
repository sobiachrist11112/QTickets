package com.production.qtickets.events;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.eventBookingConfirmQT5.EventBookingConfirmQT5Activity;
import com.production.qtickets.model.BookingQT5Data;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.payment.PaymentWebViewActivity;
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
    private String userAgent;

    boolean isLoading = false;

    boolean isALreadySucess = false;

    boolean isPaymentSucess = false;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_webview_payment);
        txt_title = findViewById(R.id.tv_toolbar_title);
        toolbar_back = findViewById(R.id.iv_back_arrow);
        Bundle b = getIntent().getExtras();
        data = (BookingQT5Data) getIntent().getSerializableExtra("DATA");
        txt_title.setText(getIntent().getStringExtra("txt_title"));
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);
        if (b != null) {
            url = b.getString("webViewURL");
            pageName = b.getString("pagename");
        }
        //Get webview
        webView = findViewById(R.id.webViewforpayments);
        data = (BookingQT5Data) getIntent().getSerializableExtra("DATA");
        eventTime = getIntent().getStringExtra(AppConstants.EVENT_TIME);
        typeList = (List<EventTicketQT5Type>) getIntent().getSerializableExtra(AppConstants.TICKET_TYPE_LIST);
        // toolbar_back.setOnClickListener(view -> backPress());
        toolbar_back.setOnClickListener(view -> {
            if (isPaymentSucess) {
                finish();
            } else {
                backPress();
            }
        });
        startWebView22(url);
    }

    private void startWebView22(String url) {
        webView.clearCache(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);

        /*Below lines added on 2feb-2024*/
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("7feb onPageStarted", url.toString());
                if (url.contains(AppConstants.EVENT_PAYMENT_CANCEL)) {
                    finish();
                }

                if (url.contains(AppConstants.MOVIE_PAYMENT_CANCEL)) {
                    finish();
                }

                if (url.contains(AppConstants.MOVIE_PAYMENT_CANCEL1)) {
                    finish();
                }

                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(PaymentWebView.this);
                    progressDialog.setMessage(getResources().getString(R.string.loading));
                    progressDialog.show();
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(false);

                }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.d("2feb", "Error Code: " + error);
                Log.d("7feb onReceivedError", url.toString());

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d("2feb", "Error Code: " + errorCode + ", Description: " + description);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                super.doUpdateVisitedHistory(view, url, isReload);

                webView.setWebChromeClient(new WebChromeClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAllowFileAccessFromFileURLs(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("7feb onPageFinished", url.toString());
                progressDialog.dismiss();
                webView.setWebChromeClient(new WebChromeClient());
                webView.getSettings().setAllowFileAccessFromFileURLs(true);
                // Check if the URL contains the specified substring
                if (url != null && url.contains("https://www.q-tickets.com/naps/qt5.aspx?")) {
                    // Replace the substring with the new one
                    url = url.replace("https://www.q-tickets.com/naps/qt5.aspx?", "https://www.q-tickets.com/naps/qt5test.aspx?");

                    // Load the new URL in the WebView
                    view.loadUrl(url);
                    Log.d("7feb AFTERRRR", url.toString());


                }

                if (url.contains(AppConstants.EVENT_PAYMENT_CANCEL)) {
                    gotToHome();
                }

                if (url.contains(AppConstants.EVENTBOOKINGFAILEDURL22)) {
                    showPaymentFailedDialog();
                }
                if (url.contains(AppConstants.IPAY_MOVIE_FAILEDURL)) {
                    gotToHome();
                } else if (url.contains(AppConstants.QTICKETSURL)) {
                    gotToHome();
                } else if (url.contains(AppConstants.EVENTSSURL)) {
                    gotToHome();
                } else if (url.contains(AppConstants.EVENTBOOKINGSUCESS22)) {
                    if (!isALreadySucess) {
                        isPaymentSucess = true;
                        Intent intent = new Intent(getApplicationContext(), EventBookingConfirmQT5Activity.class);
                        intent.putExtra("DATA", data);
                        intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList);
                        intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                        startActivity(intent);
                    }

                } else {

                }
            }
        });
        webView.loadUrl(url);
    }

    private void showPaymentFailedDialog() {

        AlertDialog loginAlertDialog;

        // 13oct need to show the dialog here first...
        LayoutInflater li = LayoutInflater.from(PaymentWebView.this);
        View popupDialog = li.inflate(R.layout.login_bookingfailed_dailog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PaymentWebView.this);
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
    protected void onDestroy() {
        super.onDestroy();
        webView.clearHistory();
        webView.clearCache(true);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}



