package com.production.qtickets.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Dell on 23/09/2016.
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffsetRight;
    private int mItemOffsetLeft;
    private int mItemOffsetTop;
    private int mItemOffsetBottom;

    public ItemOffsetDecoration(int itemOffsetRight, int itemOffsetLeft, int itemOffsetTop, int itemOffsetBottom) {
        mItemOffsetRight = itemOffsetRight;
        mItemOffsetLeft = itemOffsetLeft;
        mItemOffsetTop = itemOffsetTop;
        mItemOffsetBottom = itemOffsetBottom;
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetIdLeft, @DimenRes int itemOffsetIdTop, @DimenRes int itemOffsetIdRight, @DimenRes int itemOffsetIdBottom) {
        this(context.getResources().getDimensionPixelSize(itemOffsetIdRight),context.getResources().getDimensionPixelSize(itemOffsetIdLeft),
                context.getResources().getDimensionPixelSize(itemOffsetIdTop),
                context.getResources().getDimensionPixelSize(itemOffsetIdBottom)
        );
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffsetLeft,mItemOffsetTop,mItemOffsetRight,mItemOffsetBottom);
    }
}
