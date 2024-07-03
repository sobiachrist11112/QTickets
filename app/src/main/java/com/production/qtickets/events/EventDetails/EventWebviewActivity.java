package com.production.qtickets.events.EventDetails;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.payment.PaymentWebViewActivity;
import com.production.qtickets.utils.QTUtils;

import im.delight.android.webview.AdvancedWebView;

public class EventWebviewActivity extends AppCompatActivity {

    AdvancedWebView webView;
   // private ProgressDialog progDailog;
    private int load=0;
    ImageView iv_back_arrow;
    TextView tv_toolbar_title;
    String title = "";
    boolean isInside = false;
    String notification_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_advancedwebview);
        webView = (AdvancedWebView) findViewById(R.id.webview);
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        Intent ii = getIntent();
        title =  ii.getStringExtra("pagename");
        tv_toolbar_title.setText(title);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    /*    progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);*/
        webView.setBackgroundColor(0);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if(!isInside) {
            QTUtils.showProgressDialog(EventWebviewActivity.this, true);
            isInside = true;
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }



            @Override
            public void onPageFinished(WebView view, final String url) {
                if (url.equals("https://uate.q-tickets.com/Events")) {
                    finish();
                }
                QTUtils.dismissProgressDialog();
              //  progDailog.dismiss();
            }
        });
        String url;
        if(ii.getStringExtra("redirect_url") != null){
            url = ii.getStringExtra("redirect_url");
        } else {
            url = ii.getStringExtra("webViewURL");
            if(title != null) {
                title = title.replace(" ", "_");
            }
        }


        //url = url +"/"+title+"_app";
        Log.v("eventUrl","== "+url);
        if(url != null) {
            url = url.replaceAll("\\s+", "%20");
        }
       /* webView.loadUrl(url+"/?app");*/
        webView.loadUrl(url);

    }
    @Override
    protected void onDestroy(){
       /* if (progDailog != null && progDailog.isShowing()) {
            progDailog.dismiss();
        }*/
        super.onDestroy();
    }
}
