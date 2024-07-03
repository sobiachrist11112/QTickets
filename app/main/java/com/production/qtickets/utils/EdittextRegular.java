package com.production.qtickets.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;


/**
 * Created by Harsh on 5/8/2018.
 */
public class EdittextRegular extends EditText
{

    public EdittextRegular(Context context)
    {
        super(context);
        init(context);
    }

    public EdittextRegular(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public EdittextRegular(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    public void init(Context context)
    {
        try
        {
            Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");

            setTypeface(myFont);
        }
        catch (Exception e)
        {

        }
    }
}