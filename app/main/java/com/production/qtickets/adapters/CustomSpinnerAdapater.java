package com.production.qtickets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.production.qtickets.R;
import com.production.qtickets.model.EventTimeQT5Data;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapater extends ArrayAdapter<String> {

    Context mContext;
    ArrayList<String>  spinnerNames;
    List<EventTimeQT5Data> dataitem;


    public CustomSpinnerAdapater(Context mContext, ArrayList<String>  spinnerNames, List<EventTimeQT5Data> data) {
        super(mContext, R.layout.custom_spinner, spinnerNames);
        this.mContext = mContext;
        this.spinnerNames = spinnerNames;
        this.dataitem = data;

    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_spinner, null);
        TextView tvSpinnerText = row.findViewById(R.id.lay_item);
        tvSpinnerText.setText(spinnerNames.get(position));

        convertView = super.getDropDownView(position, convertView, parent);
        convertView.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams p = convertView.getLayoutParams();
        p.height = 80; // set the height
        convertView.setLayoutParams(p);

        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_spinner, null);
        TextView tvSpinnerText = row.findViewById(R.id.lay_item);
        tvSpinnerText.setText(spinnerNames.get(position));
        return row;
    }
}