package com.production.qtickets.payment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.cmspages.CMSPagesActivity;
import com.production.qtickets.events.PaymentWebView;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import com.production.qtickets.dashboard.DashBoardActivity;

public class PaymentWebViewActivity extends AppCompatActivity {

    WebView webView;
    String booking_id;
    boolean isInside = false;
    ImageView iv_back_arrow;
    ProgressDialog progressDialog;
    boolean isPaymentSucess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.terms_conditions_activity);
        try {
            webView = (WebView) findViewById(R.id.webView);
            Intent ii = getIntent();

            webView.clearCache(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAllowFileAccessFromFileURLs(true);

            /*Below lines added on 2feb-2024*/
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setDomStorageEnabled(true);


//          webView.getSettings().setJavaScriptEnabled(true);
//            webView.getSettings().setLoadWithOverviewMode(true);
//            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setBuiltInZoomControls(true);
//            webView.getSettings().setDisplayZoomControls(false);
//            webView.clearCache(true);
//            webView.setWebChromeClient(new WebChromeClient());
//            webView.getSettings().setAllowFileAccessFromFileURLs(true);
//


            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Log.d("21sep", "URL onPageStarted : " + url);
                   /*  if (url.contains(AppConstants.MOVIE_PAYMENT_CANCEL)) {
                        finish();

                        Log.d("21sep","URL Inside ON PAGE STARTED : "+url );

                     }*/
                    if (url.contains(AppConstants.TIMEOUT)) {
                        finish();

                        Log.d("21sep", "URL Inside ON PAGE STARTED : " + url);

                    }
                    if (progressDialog == null) {
                        // in standard case YourActivity.this
                        progressDialog = new ProgressDialog(PaymentWebViewActivity.this);
                        progressDialog.setMessage(getResources().getString(R.string.loading));
                        progressDialog.show();
                        progressDialog.setCancelable(true);
                        progressDialog.setCanceledOnTouchOutside(false);

                    }
                }

                @Override
                public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                    super.doUpdateVisitedHistory(view, url, isReload);
                    webView.setWebChromeClient(new WebChromeClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setAllowFileAccessFromFileURLs(true);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //   if (!isFinishing()) {
                    if (!isInside) {
                        //  progressDialog.show();
                        //      QTUtils.showProgressDialog(PaymentWebViewActivity.this, true);
                        isInside = true;
                        //      }
                        //   progDailog.show();
                    }

                    if (url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse(url));
                        startActivity(intent);
                    }
                    if (url.startsWith("mailto:")) {
                        sendEmail(url.substring(7));
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }


                @Override
                public void onPageFinished(WebView view, final String url) {
                    try {
                        progressDialog.dismiss();
                        webView.setWebChromeClient(new WebChromeClient());
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setAllowFileAccessFromFileURLs(true);

                        Log.d("21sep", "URL onPageFinished : " + url);

                        Log.d("WebUrl :", url.toString());
                        if (url.contains(AppConstants.IPAY_MOVIE_FAILEDURL22)) {
                            finish();
                        } else if (url.contains(AppConstants.EVENTBOOKINGFAILEDURL22)) {
                            finish();
                        } else if (url.contains(AppConstants.MOVIE_PAYMENT_CANCEL1)) {
                            finish();
                        } else if (url.contains(AppConstants.MOVIE_PAYMENT_CANCEL)) {
                            finish();
//                            Intent back = new Intent(PaymentWebViewActivity.this, DashBoardActivity.class);
//                            back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(back);
                        } else {
                            webView.loadUrl("javascript:HtmlViewer.showHTML" +
                                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            String url = ii.getStringExtra("url");
            //  url = url.replaceAll("\\s+", "%20");
            webView.loadUrl(url);
            Log.d("21sep", "URL received : " + url);
            webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

            iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);

            iv_back_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getConfirmation();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String add) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + add)); // only email apps should handle this
//      intent.putExtra(Intent.EXTRA_EMAIL, "info@qtickets.com");
//      intent.putExtra(Intent.EXTRA_SUBJECT, data.eventName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    class MyJavaScriptInterface {
        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            System.out.println(html);
        }

    }


    @Override
    public void onBackPressed() {
        getConfirmation();
    }

    private void getConfirmation() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PaymentWebViewActivity.this);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to cancel the transaction?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Intent back = new Intent(PaymentWebViewActivity.this, DashBoardActivity.class);
                            back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(back);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        Button nbutton = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.GRAY);
        alert11.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.GRAY);

    }
}
