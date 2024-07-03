package com.production.qtickets.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.core.content.ContextCompat;

import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.events.PaymentWebView;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import java.net.URISyntaxException;
import java.util.List;

import im.delight.android.webview.AdvancedWebView;

public class ShowWebView2 extends Activity {


    // new chnages to commit
    private String url = "", pageName = "";

    //private Button button;
    private WebView webView;
    private ImageView toolbar_back;
    private TextView txt_title;
    ProgressDialog progressDialog;
    private String userAgent;
    ProgressBar progressBar;
    boolean navigateToBrowser= false;

    // code pushed at 29-11-2022
    private CustomTabsSession customTabsSession;
    private CustomTabsIntent customTabsIntent;
    private CustomTabsServiceConnection customTabsServiceConnection;

    private static final int CUSTOM_TAB_REQUEST_CODE = 1001; // Choose any integer code you prefer


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_webview2);
        setupwebview();
    }

    private void setupwebview() {
        txt_title = findViewById(R.id.tv_toolbar_title);
        toolbar_back = findViewById(R.id.iv_back_arrow);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            url = b.getString("webViewURL");
            pageName = b.getString("pagename");

            Log.d("12dec", "url : " + url);
            Log.d("12dec", "pageName : " + pageName);

        }

        txt_title.setText(pageName);
        webView = findViewById(R.id.webViewforEvents);
        startWebView(url);
        toolbar_back.setOnClickListener(view -> onBackPressed());
    }


    private void startWebView(String url) {
        QTUtils.showProgressDialog(ShowWebView2.this, true);

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        // For API level below 18 (This method was deprecated in API level 18)
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.clearCache(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        // new changes for popup
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        String userAgent = "Mozilla/5.0 (Linux; Android 10; Pixel 4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.39 Mobile Safari/537.36";

        webView.getSettings().setUserAgentString(userAgent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("PageStartedWEb>>>", url.toString());

                Log.d("10Oct", "onPageStarted" + url);


            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d("10Oct", "onLoadResource" + url);

                if (url.contains("https://events.q-tickets.com/qatar/facebooklogin")) {
                    launchCustomTab(url);
                    finish();
                }

                if (url.contains("https://events.q-tickets.com/signin-facebook?")) {
                    webView.loadUrl(url);

                }

                if (url.contains(AppConstants.IPAY_MOVIE_FAILEDURL)) {
                    finish();
                }
                super.onLoadResource(view, url);
                Log.d("PageonLoadResource >>>", url.toString());
                if (url.contains(".pdf")) {
                    if (!url.contains("http://docs.google.com/gview?embedded=true&url=")) {
                        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
                    }

                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("10Oct", "onPageFinished" + url);

                super.onPageFinished(view, url);
                Log.d("PageFinishedWEbWEb>>>", url.toString());
                QTUtils.progressDialog.dismiss();
                if (url.contains(AppConstants.EVENTBOOKINGFAILEDURL)) {
                    finish();
                } else if (url.contains(AppConstants.EVENT_PAYMENT_CANCEL)) {
                    finish();
                }

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return false;
                } else {
                    if (Uri.parse(url).getScheme().equals("market")) {
                        try {
                            navigateToBrowser= true;
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            Activity host = (Activity) view.getContext();
                            host.startActivity(intent);
                            return true;

                        } catch (ActivityNotFoundException e) {
                            // Google Play app is not installed, you may want to open the app store link
                            Uri uri = Uri.parse(url);
                            view.loadUrl("http://play.google.com/store/apps/" + uri.getHost() + "?" + uri.getQuery());
                            return false;
                        }

                    }
                    return false;
                }
            }

/*
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("https://onelink.to/qtickets-badrgo")) {
                    // Load the specified URL in the WebView
                    view.loadUrl(url);
                    return true;
                } else {
                    // Handle other URLs as needed
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
*/

            @Override
            public void onReceivedError(WebView view, WebResourceRequest
                    request, WebResourceError error) {

                super.onReceivedError(view, request, error);
                Log.d("ErrorWEb>>>", error.toString());
                Log.d("10Oct", "onReceivedError : " + error.toString());

            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError
                    error) {
                super.onReceivedSslError(view, handler, error);
                Log.d("ErrorWEb>>>", url.toString());
                Log.d("10Oct", "onReceivedError : " + error.toString());


            }
        });
        webView.loadUrl(url);

    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("13dec","navigateToBrowser "+navigateToBrowser);

        if (navigateToBrowser)
            finish();
        else {


        }

    }

    private void launchCustomTab(String loginUrl) {
        customTabsIntent = new CustomTabsIntent.Builder(customTabsSession)
                .setShowTitle(true)
                .build();

        customTabsIntent.intent.setPackage("com.android.chrome");
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        customTabsIntent.intent.putExtra(CustomTabsIntent.EXTRA_TITLE_VISIBILITY_STATE, CustomTabsIntent.NO_TITLE);

        CustomTabsCallback callback = new CustomTabsCallback();

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(customTabsSession);
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        customTabsIntent.intent.putExtra(CustomTabsIntent.EXTRA_ENABLE_INSTANT_APPS, true);

        Uri uri = Uri.parse(loginUrl);
        customTabsIntent.launchUrl(this, uri);
    }


    private void gotToHome() {
        /*Intent in = new Intent(ShowWebView2.this, DashBoardActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);*/
        finish();
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {

        gotToHome();
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
    public void onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        super.onDestroy();

    }
}
