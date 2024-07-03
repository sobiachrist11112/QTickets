package com.production.qtickets.moviedetailes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.production.qtickets.R;
import com.production.qtickets.utils.Config;
import com.production.qtickets.utils.QTUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 5/28/2018.
 */
public class YoutubeActivity extends Activity {
    @BindView(R.id.webViewforyoutube)
    WebView webView;
    private String vedio_id,url;
    private static final int RECOVERY_REQUEST = 1;
    String YOUTUBE_API_KEY = "";

    @BindView(R.id.tv_tittle)
    TextView tv_tittle;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_share)
    ImageView iv_share;

    // we are using this class for playing the trailer of the movie

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        QTUtils.setStatusBarGradiant(YoutubeActivity.this);
        setContentView(R.layout.activity_youtubeactivity);
        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        if(b!=null){
            url = b.getString("url");
            tv_tittle.setText( b.getString("tittle"));
        }
        vedio_id = QTUtils.getVideoId(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String youtubeUrl = "https://www.youtube.com/embed/" + vedio_id;
        webView.loadUrl(youtubeUrl);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Qtickets");
                    i.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(Intent.createChooser(i, "Share Via"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
    }



}
