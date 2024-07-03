package com.production.qtickets.events;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.production.qtickets.R;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

public class MyDialog {

    public static void showDatesDialog(final Context context,String[] arrFriday) {

        final Dialog accountDialog = new Dialog(context);
        accountDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        accountDialog.setContentView(R.layout.date_dialog);
        accountDialog.setCanceledOnTouchOutside(false);


        GridView gridView = (GridView)accountDialog.findViewById(R.id.gridView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, arrFriday);

        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String selectedEvents = parent.getItemAtPosition(position).toString();

                QTUtils.getInstance().saveToSharedPreference(context, AppConstants.EVENET_SELECTED_DATE, selectedEvents);
                accountDialog.dismiss();
            }
        });



        accountDialog.show();
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        int width = display.getWidth() - 80;
        int height = display.getHeight() - 200;
        Window window = accountDialog.getWindow();
        window.setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}
