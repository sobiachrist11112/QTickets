package com.production.qtickets.payment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.production.qtickets.R;
import com.production.qtickets.utils.QTUtils;

import com.production.qtickets.dashboard.DashBoardActivity;

public class PaymentWebViewActivity extends AppCompatActivity {

    WebView webView;
    String booking_id;
    boolean isInside = false;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(PaymentWebViewActivity.this);
        setContentView(R.layout.terms_conditions_activity);
        try {
            webView = (WebView) findViewById(R.id.webView);
            Intent ii = getIntent();
           /* progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
            progDailog.setCancelable(false);*/
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 //   if (!isFinishing()) {
                      if(!isInside) {
                            QTUtils.showProgressDialog(PaymentWebViewActivity.this, true);
                            isInside = true;
                  //      }
                     //   progDailog.show();
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageFinished(WebView view, final String url) {
                    try {
                      //  progDailog.dismiss();
                        QTUtils.dismissProgressDialog();
                        webView.loadUrl("javascript:HtmlViewer.showHTML" +
                                "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            String url = ii.getStringExtra("url");
            //  url = url.replaceAll("\\s+", "%20");
            webView.loadUrl(url);
            webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

            iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
            iv_back_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getConfirmation();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
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
                        }catch (Exception e){
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
