package com.production.qtickets.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.production.qtickets.R;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import com.production.qtickets.dashboard.DashBoardActivity;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Harsh on 5/24/2018.
 */
public class SplashscreenActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private String str_user_id;
    //int
    private static int SPLASH_TIME_OUT = 3000;
    private VideoView mSplashVideoView;

    public static TextView textViewHomePackage;
   // public  static boolean update = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(SplashscreenActivity.this);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(SplashscreenActivity.this);
        sessionManager.setShowCovidAlert(true);
        mSplashVideoView = findViewById(R.id.videoView_splash);


        int badgeCount = 0;
        try {
            badgeCount = badgeCount;
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
        }

        ShortcutBadger.applyCount(SplashscreenActivity.this, badgeCount);

        // boolean success = ShortcutBadger.applyCount(SplashActivity.this, badgeCount);

        //find the home launcher Package
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;
        textViewHomePackage = (TextView) findViewById(R.id.textViewHomePackage);
        textViewHomePackage.setText(getResources().getString(R.string.luncher) + currentHomePackage);
        setupSplashVideoPlay();

    }

    private void setupSplashVideoPlay() {
        mSplashVideoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mp.setVolume(0, 0);
            }
        });
        mSplashVideoView.setVideoURI(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getPackageName() + "/" + R.raw.splash_video3));
        mSplashVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(final MediaPlayer mp) {
                Intent i = new Intent(SplashscreenActivity.this, DashBoardActivity.class);
                i.putExtra("splash",true);
                startActivity(i);
                finish();
            }
        });
        mSplashVideoView.start();
    }

}
