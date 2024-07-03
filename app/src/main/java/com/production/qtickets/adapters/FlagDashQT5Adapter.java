package com.production.qtickets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;

import java.util.List;

/**
 * Created by Harsh on 8/9/2018.
 */
public class FlagDashQT5Adapter extends ArrayAdapter<String> {

    // this is to list the country names with respect to the flags of that country

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<String> countryList;
    private List<String> flagList;
    private final int mResource;

    public FlagDashQT5Adapter(@NonNull Context context, @LayoutRes int resource,
                              @NonNull List<String> countryList, @NonNull List<String> flagList) {
        super(context, resource, 0, countryList);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        this.countryList = countryList;
        this.flagList = flagList;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent,true);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent,false);
    }

    private View createItemView(int position, View convertView, ViewGroup parent,boolean isdropdown){
        final View view = mInflater.inflate(mResource, parent, false);
        ImageView image = view.findViewById(R.id.img_flag);
        ImageView iv_downarrow = view.findViewById(R.id.iv_downarrow);
//        image.setImageResource(flagList.get(position));
        Glide.with(mContext).load(flagList.get(position)).into(image);
        TextView name = view.findViewById(R.id.txt_country_code);
        name.setText(countryList.get(position));
        if(isdropdown) {
           /* ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,7,0,5);
            name.setLayoutParams(params);*/
            image.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            iv_downarrow.setVisibility(View.GONE);
        }else {
           /* ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,0,5);
            name.setLayoutParams(params);*/
            image.setVisibility(View.VISIBLE);
            name.setVisibility(View.GONE);
            iv_downarrow.setVisibility(View.VISIBLE);
        }

        return view;
    }


}