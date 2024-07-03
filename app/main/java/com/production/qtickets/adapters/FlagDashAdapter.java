package com.production.qtickets.adapters;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.production.qtickets.R;

import java.util.List;

/**
 * Created by Harsh on 8/9/2018.
 */
public class FlagDashAdapter extends ArrayAdapter<String> {

    // this is to list the country names with respect to the flags of that country

    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<String> countryList;
    private List<Integer> flagList;
    private final int mResource;

    public FlagDashAdapter(@NonNull Context context, @LayoutRes int resource,
                       @NonNull List<String> countryList, @NonNull List<Integer> flagList) {
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
        image.setImageResource(flagList.get(position));
        TextView name = view.findViewById(R.id.txt_country_code);
        name.setText(countryList.get(position));
        if(isdropdown) {
           /* ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,7,0,5);
            name.setLayoutParams(params);*/
            name.setVisibility(View.VISIBLE);
        }else {
           /* ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,0,5);
            name.setLayoutParams(params);*/
            image.setVisibility(View.GONE);
            name.setVisibility(View.VISIBLE);
        }

        return view;
    }


}