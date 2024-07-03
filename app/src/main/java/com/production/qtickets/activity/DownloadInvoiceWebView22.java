package com.production.qtickets.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.production.qtickets.R;

import im.delight.android.webview.AdvancedWebView;

public class DownloadInvoiceWebView22 extends Activity {

    WebView printWeb;
    TextView tv_toolbar_title;

    private String url = "", pageName = "";
    ImageView iv_back_arrow;
    ProgressDialog progressDialog;
    WebView webView;
    ImageView iv_download;
    // object of print job
    PrintJob printJob;

    // a boolean to check the status of printing
    boolean printBtnPressed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_downloadinvoice);
        intiview();
        listeners();
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        // webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }


    private void intiview() {
        // Initializing the WebView
        webView = (WebView) findViewById(R.id.webViewforinvoice);
        tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            url = b.getString("webViewURL");
            pageName = b.getString("pagename");
        }
        tv_toolbar_title.setText(pageName);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        iv_download.setVisibility(View.VISIBLE);

    }


    private void listeners() {
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // setting clickListener for Save Pdf Button
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (printWeb != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // Calling createWebPrintJob()
                        PrintTheWebPage(printWeb);
                    } else {
                        // Showing Toast message to user
                        Toast.makeText(getApplicationContext(), "Not available for device below Android LOLLIPOP", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Showing Toast message to user
                    Toast.makeText(getApplicationContext(), "WebPage not fully loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void PrintTheWebPage(WebView webView) {

        // set printBtnPressed true
        printBtnPressed = true;

        // Creating  PrintManager instance
        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        // setting the name of job
        String jobName = getString(R.string.app_name) + pageName;

        // Creating  PrintDocumentAdapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        assert printManager != null;
        printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

}
