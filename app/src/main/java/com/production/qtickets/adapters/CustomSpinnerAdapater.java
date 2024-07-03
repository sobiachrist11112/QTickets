package com.production.qtickets.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.model.EventTimeQT5Data;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapater extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> spinnerNames;
    private List<EventTimeQT5Data> dataitem;
    private int selectedItemPosition = -1; // Track the selected item position

    public CustomSpinnerAdapater(Context mContext, ArrayList<String> spinnerNames, List<EventTimeQT5Data> data) {
        this.mContext = mContext;
        this.spinnerNames = spinnerNames;
        this.dataitem = data;
    }

    @Override
    public int getCount() {
        return spinnerNames.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.custom_spinner_dropdown_item, parent, false);
        }

        TextView tvSpinnerText = convertView.findViewById(R.id.text1);
        tvSpinnerText.setText(spinnerNames.get(position));

        // Show or hide the tickIcon based on the selected item position
        View tickIcon = convertView.findViewById(R.id.tickIcon);
//        tickIcon.setVisibility(position == selectedItemPosition ? View.VISIBLE : View.GONE);
        tickIcon.setVisibility(View.GONE);

        Log.d("9sep","getView ");

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.custom_spinner_dropdown_item, parent, false);
        }

        TextView tvSpinnerText = convertView.findViewById(R.id.text1);
        tvSpinnerText.setText(spinnerNames.get(position));

        // Show or hide the tickIcon based on the selected item position
        View tickIcon = convertView.findViewById(R.id.tickIcon);
        tickIcon.setVisibility(position == selectedItemPosition ? View.VISIBLE : View.GONE);
        Log.d("9sep","getDropDownView ");

        return convertView;
    }

    // Method to set the selected item position
    public void setSelectedItemPosition(int position) {
        selectedItemPosition = position;
        notifyDataSetChanged(); // Notify the adapter to update the views
    }
}
