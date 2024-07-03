package com.production.qtickets.events;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.production.qtickets.utils.AppConstants;

public class MyWebViewClient  extends WebViewClient {





  @Override
  public boolean shouldOverrideUrlLoading(WebView view, String url) {
    view.loadUrl(url);
    System.out.println("Sanjay...url..."+url);
    return true;
  }

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    //showProgressDialog();
//            System.out.println("Sanjay...onPageStarted..."+url);
    if(url.contains("hc-action-cancel")) {
     // setUI();
    }
    super.onPageStarted(view, url, favicon);
  }

  @Override
  public void onPageFinished(WebView view, String url) {
    //hideProgressDialog();
    System.out.println("Sanjay...onPageFinished..."+url);
    super.onPageFinished(view, url);
  }

  @Override
  public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
   // hideProgressDialog();
    System.out.println("Sanjay...onPageFinished..."+error.toString());
    super.onReceivedError(view, request, error);
  }
}