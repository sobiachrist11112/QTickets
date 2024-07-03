package com.production.qtickets.adapters;

import android.content.Context;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.utils.SessionManager;

import java.util.ArrayList;

/**
 * Created by Harsh on 8/9/2018.
 */
public class FlagAdapter extends ArrayAdapter<String> {

    //in this class we will list the countries and with respect to country we are binding the flag of that country

    private final LayoutInflater mInflater;
    private final Context mContext;
  /*  private String[] countryList;
    private String[] nationality;*/
    ArrayList<String> countryList= new ArrayList<>();
    ArrayList<String> nationality= new ArrayList<>();
    private int[] flagList;
    private final int mResource;
    private boolean b;
    private int selected_position = -1;

    private boolean isCodeVisible;

/*
    public FlagAdapter(@NonNull Context context, @LayoutRes int resource,
                       @NonNull String[] countryList,  @NonNull int[] flagList, boolean b, int selectionpos) {
        super(context, resource, 0, countryList);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        this.countryList = countryList;
        this.nationality = nationality;
        this.flagList = flagList;
        this.b = b;
        this.selected_position = selectionpos;
    }
*/

    public FlagAdapter(@NonNull Context context, @LayoutRes int resource,
                       @NonNull ArrayList<String> countryList,
                       @NonNull ArrayList<String> nationality, @NonNull int[] flagList, boolean b, int selectionpos,boolean isCodeVisible) {
        super(context, resource, 0, countryList);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        this.countryList = countryList;
        this.nationality = nationality;
        this.flagList = flagList;
        this.b = b;
        this.selected_position = selectionpos;
        this.isCodeVisible=isCodeVisible;
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
        TextView txt_country_code = view.findViewById(R.id.txt_country_code);
        TextView txt_countryname = view.findViewById(R.id.txt_countryname);
      /*  if (position == 0) {
            image.setImageResource(flagList[selected_position]);
            txt_country_code.setText(countryList.get(position));
            txt_countryname.setText(nationality.get(position));
        } else {
            image.setImageResource(flagList[position]);
            txt_country_code.setText(countryList.get(position));
            txt_countryname.setText(nationality.get(position));
        }*/

        image.setImageResource(flagList[position]);
        txt_country_code.setText(countryList.get(position));
        txt_countryname.setText(nationality.get(position));
        if(b) {
            if (isvalue) {
                txt_country_code.setVisibility(View.VISIBLE);
                image.setImageResource(flagList[position]);
                Log.d("Code: ","If "+txt_country_code.getText().toString().trim());
              //  txt_countryname.setVisibility(View.GONE);
            } else {
//                Log.d("Code: ","Else "+txt_country_code.getText().toString().trim());

                txt_country_code.setVisibility(View.GONE);
                txt_countryname.setVisibility(View.GONE);
             //   txt_countryname.setVisibility(View.GONE);
            }
        }

        if(isCodeVisible){
            txt_countryname.setVisibility(View.VISIBLE);
        }

        return view;
    }


}