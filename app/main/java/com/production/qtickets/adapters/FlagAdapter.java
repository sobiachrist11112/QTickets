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
import com.production.qtickets.utils.SessionManager;

/**
 * Created by Harsh on 8/9/2018.
 */
public class FlagAdapter extends ArrayAdapter<String> {

    //in this class we will list the countries and with respect to country we are binding the flag of that country

    private final LayoutInflater mInflater;
    private final Context mContext;
    private String[] countryList;
    private int[] flagList;
    private final int mResource;
    private boolean b;
    private int selected_position = -1;

    public FlagAdapter(@NonNull Context context, @LayoutRes int resource,
                       @NonNull String[] countryList, @NonNull int[] flagList, boolean b, int selectionpos) {
        super(context, resource, 0, countryList);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        this.countryList = countryList;
        this.flagList = flagList;
        this.b = b;
        this.selected_position = selectionpos;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent, true);
    }

    @Override
    public @NonNull
    View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent, false);
    }

    private View createItemView(int position, View convertView, ViewGroup parent, boolean isvalue) {
        final View view = mInflater.inflate(mResource, parent, false);
        ImageView image = view.findViewById(R.id.img_flag);
        TextView name = view.findViewById(R.id.txt_country_code);
        if (position == 0) {
            image.setImageResource(flagList[selected_position]);
            name.setText(countryList[selected_position]);
        } else {
            image.setImageResource(flagList[position]);
            name.setText(countryList[position]);
        }
        if(b) {
            if (isvalue) {
                name.setVisibility(View.VISIBLE);
            } else {
                name.setVisibility(View.GONE);
            }
        }

        return view;
    }


}