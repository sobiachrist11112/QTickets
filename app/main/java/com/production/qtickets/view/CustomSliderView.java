package com.production.qtickets.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.production.qtickets.R;

public class CustomSliderView extends BaseSliderView {
    Context context;

    public CustomSliderView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView() {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_slider,null,false);
        ImageView imageView = (ImageView) view.findViewById(R.id.daimajia_slider_image);
        LinearLayout frame = (LinearLayout) view.findViewById(R.id.description_layout);
        frame.setBackgroundColor(Color.TRANSPARENT);
        bindEventAndShow(view, imageView);
        return view;
    }
}
