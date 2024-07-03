package com.production.qtickets.utils;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Indglobal on 4/25/2017.
 */

public class CheckBold extends CheckBox {
    public CheckBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
    }
}