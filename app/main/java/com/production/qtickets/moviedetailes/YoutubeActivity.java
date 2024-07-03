package com.production.qtickets.moviedetailes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    @BindView(R.id.videoView)
    YouTubePlayerView videoView;
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
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        YOUTUBE_API_KEY = remoteConfig.getString("API_KEY");
        videoView.initialize(YOUTUBE_API_KEY, this);
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        try {
            if (!wasRestored) {
                player.cueVideo(vedio_id); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "No Trailer Available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return videoView;
    }
}
