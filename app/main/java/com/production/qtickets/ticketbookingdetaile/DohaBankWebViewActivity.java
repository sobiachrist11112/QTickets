package com.production.qtickets.ticketbookingdetaile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.production.qtickets.R;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.dashboard.DashBoardActivity;

public class DohaBankWebViewActivity extends AppCompatActivity {

    WebView webView;
    private ProgressDialog progDailog;
    String booking_id;
    private ProgressDialog dialog = null;

    ImageView iv_back_arrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(DohaBankWebViewActivity.this);
        setContentView(R.layout.dohabank_webview_activity);
        webView = (WebView)findViewById(R.id.webView);
        Intent ii = getIntent();
        progDailog = ProgressDialog.show(this, "Loading","Please wait...", true);
        progDailog.setCancelable(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!isFinishing()) {
                    progDailog.show();
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
                webView.loadUrl("javascript:HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");

            }
        });

        String url = ii.getStringExtra("url");
        //  url = url.replaceAll("\\s+", "%20");
        webView.loadUrl(url);
        webView.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        iv_back_arrow = (ImageView)findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dashIntent = new Intent(DohaBankWebViewActivity.this,DashBoardActivity.class);
                dashIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(dashIntent);

            }
        });


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
        super.onBackPressed();
        Intent back = new Intent(DohaBankWebViewActivity.this, DashBoardActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(back);
    }
}
