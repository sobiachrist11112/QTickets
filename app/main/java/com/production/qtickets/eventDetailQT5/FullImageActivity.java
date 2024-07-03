package com.production.qtickets.eventDetailQT5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.view.TouchImageView;

public class FullImageActivity extends AppCompatActivity {
    TouchImageView img_touch_image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        img_touch_image_view = (TouchImageView) findViewById(R.id.img_touch_image_view);

        String imageUrl = getIntent().getStringExtra("IMAGE");
        Glide.with(getApplicationContext()).load(imageUrl).into(img_touch_image_view);
        img_touch_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}