package com.production.qtickets.eventBookingDetailQT5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.production.qtickets.R;
import com.production.qtickets.adapters.CustomSpinnerAdapater;
import com.production.qtickets.eventBookingSummaryQT5.EventBookingSummaryQT5Activity;
import com.production.qtickets.model.CustomFieldQT5Model;
import com.production.qtickets.model.EventBookingQT5Date;
import com.production.qtickets.model.EventBookingQT5Model;
import com.production.qtickets.model.EventCustomFieldListQT5;
import com.production.qtickets.model.EventCustomFieldQT5Model;
import com.production.qtickets.model.EventTicketQT5Model;
import com.production.qtickets.model.EventTicketQT5Type;
import com.production.qtickets.model.EventTimeQT5Data;
import com.production.qtickets.model.EventTimeQT5Model;
import com.production.qtickets.model.GetSublistview;
import com.production.qtickets.model.SelectedParentCount;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.ItemOffsetDecoration;
import com.production.qtickets.utils.OnDateSelectListener;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.view.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EventBookingDetailQT5Activity extends AppCompatActivity implements View.OnClickListener, EventBookingDetailQT5Contracter.View, OnDateSelectListener {
    int ID = 0;
    int Ticket_ID = 0;
    String eventName = "";
    Boolean isTimeSlot = false;
    String eventVenue = "";
    EventBookingDetailQT5Presenter presenter;
    EventDateAdapter eventDateAdapter;
    String CountryCode = "";
    RecyclerView recycler_date;
    RelativeLayout lay_spinner;
    ImageView btn_back;
    Button btn_book_now, btn_save_custom_field;
    LinearLayout lay_ticket_view, lay_amount, lay_fields, lay_ticket_categories;
    TextView tv_ticket_name, tv_select_tickets, tv_tickets, total_amount, event_name;
    ImageView close_dialog;
    Spinner spinner_time;
    int TotalTicketCount = 0;
    int TotalTicketPerson = 0;
    double TotalTicketAmount = 0.0;
    double TotalServiceAmount = 0.0;
    double WhatsappCharges = 0.0;
    double smsCharges = 0.0;
    String eventTime = "";
    List<EventTicketQT5Type> typeList;
    ArrayList<String> mCategoryNames = new ArrayList<>();
    Boolean isSingleSelection = false;
    List<EventTimeQT5Data> eventTimeQT5Data;
    String selectedEventDate = "";
    Dialog customDialog;
    // LinearLayout viewTicketField = null;
    String strSelectedTicketName = "";
    LinearLayout ll_childcontainer;


    JSONObject jsonObjectTicketFields = new JSONObject();
    ArrayList<EventBookingQT5Date> mEventDates = new ArrayList<>();
    ArrayList<EventBookingQT5Date> mEventDatesTemp = new ArrayList<>();


    ArrayList<LinearLayout> mContainerList = new ArrayList<LinearLayout>();

    int current_ticket_postion = -1;

    TimePickerDialog timePickerDialog;
    LinearLayout ll_selecteddate;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    TextView tv_filechoosen;
    String filetypes = "";
    LinearLayout childcontainer;
    LinearLayout childofradioconatiner;
    boolean isFirstTime = false;
    //
    LinearLayout check_box_child;
    TextView tv_timeslot;
    TextView tv_daypasscount;
    boolean IS_EVENT_OFFLINE = false;

    HashMap<String, LinearLayout> mViewsChild = new HashMap<>();
    HashMap<String, LinearLayout> mParentView = new HashMap<>();
    ArrayList<String> mChildNames = new ArrayList<>();
    CardView card_isdaypass;
    boolean isDayPassActive = false;
    boolean isDTCM = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_booking_detail_qt5);
        presenter = new EventBookingDetailQT5Presenter();
        presenter.attachView(this);

        ID = getIntent().getIntExtra(AppConstants.EVENT_ID, 0);
        eventName = getIntent().getStringExtra(AppConstants.EVENT_NAME);
        eventVenue = getIntent().getStringExtra(AppConstants.EVENT_VENUE);
        IS_EVENT_OFFLINE = getIntent().getBooleanExtra(AppConstants.IS_EVENT_OFFLINE, false);
        tv_daypasscount = (TextView) findViewById(R.id.tv_daypasscount);
        card_isdaypass = (CardView) findViewById(R.id.card_isdaypass);
        lay_spinner = (RelativeLayout) findViewById(R.id.lay_spinner);
        tv_timeslot = (TextView) findViewById(R.id.tv_timeslot);
        spinner_time = (Spinner) findViewById(R.id.spinner_time);
        btn_book_now = (Button) findViewById(R.id.btn_book_now);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_book_now.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        recycler_date = (RecyclerView) findViewById(R.id.recycler_date);
        lay_ticket_view = (LinearLayout) findViewById(R.id.lay_ticket_view);
        lay_ticket_categories = (LinearLayout) findViewById(R.id.lay_ticket_categories);
        lay_amount = (LinearLayout) findViewById(R.id.lay_amount);
        tv_select_tickets = (TextView) findViewById(R.id.tv_select_tickets);
        total_amount = (TextView) findViewById(R.id.total_amount);
        tv_tickets = (TextView) findViewById(R.id.tv_tickets);
        event_name = (TextView) findViewById(R.id.event_name);

        ll_selecteddate = (LinearLayout) findViewById(R.id.ll_selecteddate);
        ll_selecteddate.setOnClickListener(this);
        card_isdaypass.setOnClickListener(this);
        card_isdaypass.setMaxCardElevation(24);


        event_name.setText("" + eventName);

        recycler_date.setHasFixedSize(true);
        recycler_date.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_date.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), R.dimen.zero, R.dimen.five, R.dimen.google_1x, R.dimen.five));
        presenter.getEventBookingDetailsById(ID);

    }


    private void setCalender() {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.custom_calender_view,
                        null);
        builder.setView(customLayout);
        CalendarView calendarView = customLayout.findViewById(R.id.calendarView);
        List<Calendar> calendars = new ArrayList<>();
        // 2022-06-29T00:00:00
        AlertDialog dialog
                = builder.create();
        dialog.show();
        TotalTicketCount = 0;
        TotalTicketPerson = 0;
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                boolean isExist = false;
                String selected_date = "";
                Integer selected_date_poition = -1;
                for (int i = 0; i < calendars.size(); i++) {
                    if (eventDay.getCalendar().equals(calendars.get(i))) {
                        selected_date = mEventDates.get(i).date;
                        selected_date_poition = i;
                        isExist = true;
                    }
                }
                if (isExist) {
                    if (!mEventDatesTemp.contains(mEventDates.get(selected_date_poition))) {
                        if (mEventDatesTemp.size() > 2) {
                            mEventDatesTemp.set(mEventDatesTemp.size() - 1, mEventDates.get(selected_date_poition));
                        } else {
                            mEventDatesTemp.add(mEventDates.get(selected_date_poition));
                        }
                    }

                    for (int i = 0; i < mEventDatesTemp.size(); i++) {
                        if (selected_date.equals(mEventDatesTemp.get(i).date)) {
                            selected_date_poition = i;
                        }
                    }
                    dialog.dismiss();
                    eventDateAdapter = new EventDateAdapter(mEventDatesTemp, getApplicationContext(), EventBookingDetailQT5Activity.this, selected_date_poition, false);
                    recycler_date.setAdapter(eventDateAdapter);
                } else {
                    calendarView.setBackgroundColor(getResources().getColor(R.color.white));
                    calendarView.setSelectedDates(calendars);
                    Toast.makeText(EventBookingDetailQT5Activity.this, "Not an Event Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
        for (int i = 0; i < mEventDates.size(); i++) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            try {
                cal.setTime(Objects.requireNonNull(sdf.parse(mEventDates.get(i).date)));// all done
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendars.add(cal);
        }
        calendarView.setSelectedDates(calendars);
        Calendar calendarsss = Calendar.getInstance();
        int month = calendars.get(0).get(Calendar.MONTH);
        int year = calendars.get(0).get(Calendar.YEAR);
        int dates = calendars.get(0).get(Calendar.DATE);
        calendarsss.set(year, month, dates);
        try {
            calendarView.setDate(calendarsss);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back: {
                onBackPressed();
            }
            break;

            case R.id.ll_selecteddate: {
                setCalender();
            }
            break;

            case R.id.card_isdaypass: {
                isDayPassActive = true;
                card_isdaypass.setCardBackgroundColor(getResources().getColor(R.color.themecolor));
                card_isdaypass.setMaxCardElevation(24);
                presenter.getEventTicketsByDate(ID, "AllDayPass", "", "");
            }
            break;
            case R.id.btn_save_custom_field: {
                try {
                    int count = lay_fields.getChildCount();
                    JSONObject jsonObject = new JSONObject();
                    StringBuilder name = new StringBuilder();
                    String genderEmail = "";
                    if (count > 0) {
                        for (int i = 0; i < count; i++) {
                            View view1 = (View) lay_fields.getChildAt(i);
                            String strName = (String) view1.getTag();
                            TextView tv_field_title = (TextView) view1.findViewById(R.id.tv_field_title);
                            Boolean isRequired = (Boolean) tv_field_title.getTag();
                            if (strName.contains("text")) {
                                EditText editText = (EditText) view1.findViewById(R.id.et_field_type);
                                if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                                    if (isRequired) {
                                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) editText.getTag());
                                        return;
                                    }
                                }
                                if (tv_field_title.getText().toString().contains("Mobile")) {
                                    if (!isValidMobile(editText.getText().toString().trim())) {
                                        if (isRequired) {
                                            QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please Enter Valid Mobile Number");
                                            return;
                                        }
                                    }

                                } else if (tv_field_title.getText().toString().contains("Email")) {
                                    if (!isValidEmail(editText.getText().toString().trim())) {
                                        if (isRequired) {
                                            QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please Enter Valid Email ID");
                                            return;
                                        }
                                    }
                                    genderEmail = genderEmail + " | " + editText.getText().toString();
                                } else if (tv_field_title.getText().toString().contains("Prefix")) {
                                    name.append(editText.getText().toString()).append(" ");
                                } else if (tv_field_title.getText().toString().contains("First Name")) {
                                    name.append(editText.getText().toString()).append(" ");
                                } else if (tv_field_title.getText().toString().contains("Last Name")) {
                                    name.append(editText.getText().toString()).append(" ");
                                } else {

                                }
                                jsonObject.put("" + tv_field_title.getText().toString(), editText.getText().toString());
                            } else if (strName.contains("radio")) {
                                LinearLayout lay_radio_group = (LinearLayout) view1.findViewById(R.id.lay_radio_group);
                                LinearLayout ll_radiochild = (LinearLayout) view1.findViewById(R.id.ll_radiochild);
                                boolean isChecked = false;
                                TextView radio_fieldtittle = (TextView) view1.findViewById(R.id.tv_field_title);
                                boolean isRequire = (boolean) radio_fieldtittle.getTag();
                                int rbButtonCount = lay_radio_group.getChildCount();
                                int subchild = ll_radiochild.getChildCount();
                                if (rbButtonCount > 0) {
                                    for (int j = 0; j < rbButtonCount; j++) {
                                        RadioButton rb = (RadioButton) lay_radio_group.getChildAt(j).findViewById(R.id.rb);
                                        if (rb.isChecked()) {
                                            isChecked = true;
                                            if (tv_field_title.getText().toString().contains("Gender")) {
                                                genderEmail = genderEmail + rb.getText().toString();
                                            }
                                            jsonObject.put("" + tv_field_title.getText().toString(), rb.getText().toString());
                                        }
                                    }
                                }

                                if (subchild > 0) {
                                    for (int k = 0; k < subchild; k++) {
                                        View v = (View) ll_radiochild.getChildAt(k);
                                        String childType = (String) v.getTag();
                                        if (childType.contains("text")) {
                                            EditText editText = (EditText) v.findViewById(R.id.et_field_type);
                                            TextView tv_childtittle = (TextView) v.findViewById(R.id.tv_field_title);
                                            jsonObject.put("" + tv_childtittle.getText().toString(), editText.getText().toString());
                                        }
                                        if (childType.contains("checkbox")) {
                                            LinearLayout lay_checboxes = (LinearLayout) v.findViewById(R.id.lay_checboxes);
                                            TextView tv_childtittle = (TextView) v.findViewById(R.id.tv_field_title);
                                            boolean isChecsaked = false;
                                            int cbButtonCount = lay_checboxes.getChildCount();
                                            String cb_name = "";
                                            StringBuilder buffer = new StringBuilder();
                                            if (cbButtonCount > 0) {
                                                for (int j = 0; j < cbButtonCount; j++) {
                                                    CheckBox cb = (CheckBox) lay_checboxes.getChildAt(j).findViewById(R.id.cb);
                                                    if (cb.isChecked()) {
                                                        isChecked = true;
                                                        if (tv_childtittle.getText().toString().contains("Preferred Foods")) {
                                                            genderEmail = genderEmail + cb.getText().toString();
                                                        }
                                                    }

                                                    buffer.append(cb_name);
                                                    cb_name = ","; // Avoid if(); assignment is very fast!
                                                    buffer.append(cb.getText().toString());
                                                }
                                                jsonObject.put("" + tv_childtittle.getText().toString(), buffer.toString());
                                            }
                                            if (!isChecked) {
                                                QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) lay_checboxes.getTag());
                                                return;
                                            }
                                        }

                                        if (childType.contains("select")) {
                                            Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
                                            TextView tv_fieltitle = (TextView) v.findViewById(R.id.tv_field_title);
                                            String data = "";
                                            if (spinner.getSelectedItemPosition() == 0) {
                                                if (isRequired) {
                                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) spinner.getTag());
                                                    return;
                                                }
                                            } else {
                                                data = spinner.getSelectedItem().toString();
                                            }
                                            jsonObject.put("" + tv_fieltitle.getText().toString(), data);
                                        }

                                        if (childType.contains("radio")) {
                                            LinearLayout llradiochilds = (LinearLayout) v.findViewById(R.id.lay_radio_childgroup);
                                            TextView tv_field_titless = (TextView) v.findViewById(R.id.tv_field_childtitle);
                                            int rbButtonCounts = llradiochilds.getChildCount();
                                            int subchildss = llradiochilds.getChildCount();
                                            if (rbButtonCounts > 0) {
                                                for (int j = 0; j < rbButtonCounts; j++) {
                                                    RadioButton rb = (RadioButton) llradiochilds.getChildAt(j).findViewById(R.id.rb);
                                                    if (rb.isChecked()) {
                                                        isChecked = true;
                                                        if (tv_field_titless.getText().toString().contains("Gender")) {
                                                            genderEmail = genderEmail + rb.getText().toString();
                                                        }
                                                        jsonObject.put("" + tv_field_titless.getText().toString(), rb.getText().toString());
                                                    }

                                                }
                                            }

                                        }

                                        if (strName.contains("date")) {
                                            TextView textView = (TextView) view1.findViewById(R.id.tv_field_type);
                                            if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                                                if (isRequired) {
                                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) textView.getTag());
                                                    return;
                                                }
                                            }
                                            jsonObject.put("" + tv_field_title.getText().toString(), textView.getText().toString());
                                        }

                                        if (strName.contains("time")) {
                                            TextView textView = (TextView) view1.findViewById(R.id.tv_field_type);
                                            if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                                                if (isRequired) {
                                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) textView.getTag());
                                                    return;
                                                }
                                            }
                                            jsonObject.put("" + tv_field_title.getText().toString(), textView.getText().toString());
                                        }

                                        if (strName.contains("file")) {
                                            TextView tv_filechoosen = (TextView) view1.findViewById(R.id.tv_filechoosen);
                                            if (TextUtils.isEmpty(tv_filechoosen.getText().toString().trim())) {
                                                if (isRequired) {
                                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) tv_filechoosen.getTag());
                                                    return;
                                                }
                                            }
                                            jsonObject.put("" + tv_field_title.getText().toString(), tv_filechoosen.getText().toString());
                                        }
                                    }
                                }

                                if (isRequire && !isChecked) {
                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) lay_radio_group.getTag());
                                    return;
                                }

                            }

                            //  LinearLayout lay_checboxes = (LinearLayout) v.findViewById(R.id.lay_checboxes);
                            else if (strName.contains("checkbox")) {
                                LinearLayout lay_checboxes = (LinearLayout) view1.findViewById(R.id.lay_checboxes);
                                TextView checkbox_fieldtittle = (TextView) view1.findViewById(R.id.tv_field_title);
                                boolean isRequire = (boolean) checkbox_fieldtittle.getTag();
                                boolean isChecked = false;
                                int cbButtonCount = lay_checboxes.getChildCount();
                                if (cbButtonCount > 0) {
                                    for (int j = 0; j < cbButtonCount; j++) {
                                        CheckBox cb = (CheckBox) lay_checboxes.getChildAt(j).findViewById(R.id.cb);
                                        if (cb.isChecked()) {
                                            isChecked = true;
                                            if (tv_field_title.getText().toString().contains("Preferred Foods")) {
                                                genderEmail = genderEmail + cb.getText().toString();
                                            }
                                            jsonObject.put("" + tv_field_title.getText().toString(), cb.getText().toString());
                                        }
                                    }
                                }
                                if (isRequire && !isChecked) {
                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) lay_checboxes.getTag());
                                    return;
                                }


                            } else if (strName.contains("date")) {
                                TextView textView = (TextView) view1.findViewById(R.id.tv_field_type);
                                if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                                    if (isRequired) {
                                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) textView.getTag());
                                        return;
                                    }
                                }
                                jsonObject.put("" + tv_field_title.getText().toString(), textView.getText().toString());
                            } else if (strName.contains("time")) {
                                TextView textView = (TextView) view1.findViewById(R.id.tv_field_type);
                                if (TextUtils.isEmpty(textView.getText().toString().trim())) {
                                    if (isRequired) {
                                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) textView.getTag());
                                        return;
                                    }
                                }
                                jsonObject.put("" + tv_field_title.getText().toString(), textView.getText().toString());
                            } else if (strName.contains("select")) {
                                Spinner spinner = (Spinner) view1.findViewById(R.id.spinner);
                                LinearLayout ll_spinnercontainter = (LinearLayout) view1.findViewById(R.id.ll_spinnercontainter);
                                int spinner_childcount = ll_spinnercontainter.getChildCount();
                                String data = "";
                                boolean isChecked = false;
                                if (spinner.getSelectedItemPosition() == 0) {
                                    if (isRequired) {
                                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) spinner.getTag());
                                        return;
                                    }
                                } else {
                                    data = spinner.getSelectedItem().toString();
                                }
                                jsonObject.put("" + tv_field_title.getText().toString(), data);

                                if (spinner_childcount > 0) {
                                    for (int k = 0; k < spinner_childcount; k++) {
                                        View v = (View) ll_spinnercontainter.getChildAt(k);
                                        String childType = (String) v.getTag();
                                        if (childType.contains("select")) {
                                            Spinner spinnersss = (Spinner) v.findViewById(R.id.spinner);
                                            TextView tv_spinnerfieltitle = (TextView) v.findViewById(R.id.tv_field_title);
                                            String dataaaa = "";
                                            if (spinnersss.getSelectedItemPosition() == 0) {
                                                if (isRequired) {
                                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) spinnersss.getTag());
                                                    return;
                                                }
                                            } else {
                                                data = spinnersss.getSelectedItem().toString();
                                            }
                                            jsonObject.put("" + tv_spinnerfieltitle.getText().toString(), data);
                                        }

                                        if (childType.contains("radio")) {
                                            LinearLayout ll_radiochild = (LinearLayout) v.findViewById(R.id.lay_radio_childgroup);
                                            TextView tv_field_titless = (TextView) v.findViewById(R.id.tv_field_childtitle);
                                            int rbButtonCount = ll_radiochild.getChildCount();
                                            int subchild = ll_radiochild.getChildCount();
                                            if (rbButtonCount > 0) {
                                                for (int j = 0; j < rbButtonCount; j++) {
                                                    RadioButton rb = (RadioButton) ll_radiochild.getChildAt(j).findViewById(R.id.rb);
                                                    if (rb.isChecked()) {
                                                        isChecked = true;
                                                        if (tv_field_titless.getText().toString().contains("Gender")) {
                                                            genderEmail = genderEmail + rb.getText().toString();
                                                        }
                                                        jsonObject.put("" + tv_field_titless.getText().toString(), rb.getText().toString());
                                                    }

                                                }
                                            }

                                        }

                                        if (childType.contains("checkbox")) {
                                            LinearLayout lay_checboxes = (LinearLayout) v.findViewById(R.id.lay_checboxes);
                                            TextView tv_childtittle = (TextView) v.findViewById(R.id.tv_field_title);
                                            boolean isChecsaked = false;
                                            int cbButtonCount = lay_checboxes.getChildCount();
                                            String cb_name = "";
                                            StringBuilder buffer = new StringBuilder();
                                            if (cbButtonCount > 0) {
                                                for (int j = 0; j < cbButtonCount; j++) {
                                                    CheckBox cb = (CheckBox) lay_checboxes.getChildAt(j).findViewById(R.id.cb);
                                                    if (cb.isChecked()) {
                                                        isChecked = true;
                                                        if (tv_childtittle.getText().toString().contains("Preferred Foods")) {
                                                            genderEmail = genderEmail + cb.getText().toString();
                                                        }
                                                    }

                                                    buffer.append(cb_name);
                                                    cb_name = ","; // Avoid if(); assignment is very fast!
                                                    buffer.append(cb.getText().toString());
                                                }
                                                jsonObject.put("" + tv_childtittle.getText().toString(), buffer.toString());
                                            }
                                            if (!isChecked) {
                                                QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "" + (String) lay_checboxes.getTag());
                                                return;
                                            }

                                        }

                                        if (childType.contains("text")) {
                                            EditText editText = (EditText) v.findViewById(R.id.et_field_type);
                                            TextView tv_childtittle = (TextView) v.findViewById(R.id.tv_field_title);
                                            jsonObject.put("" + tv_childtittle.getText().toString(), editText.getText().toString());
                                        }

                                    }
                                }

                            } else if (strName.contains("file")) {
                                TextView tv_filechoosen = (TextView) view1.findViewById(R.id.tv_filechoosen);
                                if (TextUtils.isEmpty(tv_filechoosen.getText().toString().trim())) {
                                    if (isRequired) {
                                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please select the Media");
                                        return;
                                    }
                                }
                                if (tv_filechoosen.getText().toString().equals("No file chosen")) {
                                    if (isRequired) {
                                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please select the Media");
                                        return;
                                    }
                                }
                                jsonObject.put("" + tv_field_title.getText().toString(), tv_filechoosen.getText().toString());
                            } else if (strName.contains("disclaimer")) {
                                CheckBox cb_disclamer = (CheckBox) view1.findViewById(R.id.cb_disclamer);
                                TextView checkbox_discalimer = (TextView) view1.findViewById(R.id.tv_field_title);
                                boolean isRequire = (boolean) checkbox_discalimer.getTag();
                                boolean isChecked = (boolean) cb_disclamer.getTag();
                                if (isRequired && !isChecked) {
                                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please select the " + strName + " checkbox.");
                                    return;
                                }

                            }
                        }

                    }
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).put(current_ticket_postion, jsonObject);
//                    if (jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).length() == 1) {
//                        viewTicketField.setVisibility(View.VISIBLE);
//                        addTicketFieldData(name, genderEmail, false);
//                    } else {
//                        addTicketFieldData(name, genderEmail, true);
//                    }
                    Log.d("finaljson :", jsonObjectTicketFields.toString());
                    //   setDataInCHildFieldsseconf();
                    setDataInCHildFields();
                    customDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.iv_close: {
                customDialog.dismiss();
            }
            break;
            case R.id.btn_book_now: {
                if (eventDateAdapter != null) {
                    if (isTimeSlot) {
                        /*if (spinner_time.getSelectedItemPosition() == 0) {
                            QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please select time");
                            return;
                        }*/
                    }
                    if (TotalTicketCount == 0) {
                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, getResources().getString(R.string.e_ticket_count));
                        return;
                    }
                    Iterator keys = jsonObjectTicketFields.keys();
                    ArrayList<String> mItemList = new ArrayList<String>();
                    while (keys.hasNext()) {
                        String currentDynamicKey = (String) keys.next();
                        try {
                            if (jsonObjectTicketFields.getJSONArray("" + currentDynamicKey).length() > 0) {
                                for (int i = 0; i < jsonObjectTicketFields.getJSONArray("" + currentDynamicKey).length(); i++) {
                                    if (jsonObjectTicketFields.getJSONArray("" + currentDynamicKey).get(i).toString().equals("{}")) {
                                        mItemList.add(currentDynamicKey);
                                    }
                                }
                            }
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }

                    }
                    Set<String> hashSet = new LinkedHashSet(mItemList);
                    ArrayList<String> removedDuplicates = new ArrayList(hashSet);
                    if (removedDuplicates.size() > 0) {
                        QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Please add guest info for (" + removedDuplicates.toString().replace("[", "").replace("]", "") + ").");
                        return;
                    }
                    if (isTimeSlot) {
                        if (spinner_time != null && spinner_time.getSelectedItem() != null) {
                            eventTime = spinner_time.getSelectedItem().toString();
                        }
                    }


                    int selectedDateID = eventDateAdapter.getSelectedDateID();
                    String selectedDate = "" + eventDateAdapter.getSelectedDate();
                    String eventEndDate = "";
                    if (isDayPassActive) {
                        eventEndDate = mEventDates.get(mEventDates.size() - 1).date;
                        // eventendDate=mEventDates.get(mEventDates.size() - 1);
                    }
                    Intent intent = new Intent(getApplicationContext(), EventBookingSummaryQT5Activity.class);
                    intent.putExtra(AppConstants.EVENT_DATE_ID, selectedDateID);
                    intent.putExtra(AppConstants.EVENT_DATE, selectedDate);
                    intent.putExtra(AppConstants.END_EVENT_DATE, eventEndDate);
                    intent.putExtra(AppConstants.IS_DAY_PASS, isDayPassActive);
                    intent.putExtra(AppConstants.IS_DTCM_EVENT, isDTCM);
                    intent.putExtra(AppConstants.EVENT_TIME, eventTime);
                    intent.putExtra(AppConstants.TOTAL_AMT, TotalTicketAmount);
                    intent.putExtra(AppConstants.NO_OF_TICKETS, TotalTicketCount);
                    intent.putExtra(AppConstants.SERVICE_CHARGE, TotalServiceAmount);
                    intent.putExtra(AppConstants.WHATSAPP_CHARGES, WhatsappCharges);
                    intent.putExtra(AppConstants.SMS_CHARGES, smsCharges);
                    intent.putExtra(AppConstants.EVENT_NAME, eventName);
                    intent.putExtra(AppConstants.EVENT_VENUE, eventVenue);
                    intent.putExtra(AppConstants.IS_EVENT_OFFLINE, IS_EVENT_OFFLINE);
                    intent.putExtra(AppConstants.EVENT_ID, ID);
                    intent.putExtra(AppConstants.COUNTRY_TYPE_CURRENCY, CountryCode);
                    intent.putExtra(AppConstants.TICKET_FIELDS, jsonObjectTicketFields.toString());
                    intent.putExtra(AppConstants.TICKET_TYPE_LIST, (Serializable) typeList); // 26oct
                    startActivityForResult(intent, 001);

                    Log.d("1Jan","smsCharges"+ smsCharges);

                }
            }
            break;
        }
    }


    private void setDataInCHildFields() {
        LinearLayout mCHildView = mViewsChild.get(strSelectedTicketName);
        Ticket_ID = (Integer) mViewsChild.get(strSelectedTicketName).getTag();
        mCHildView.removeAllViews();
        mCHildView.setTag(Ticket_ID);
        try {
            Iterator iterator = jsonObjectTicketFields.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                int lenght = jsonObjectTicketFields.getJSONArray(key).length();
                if (lenght > 0) {
                    for (int k = 0; k < lenght; k++) {
                        if (strSelectedTicketName.equals(key)) {
                            inflatchildviewTemp(lenght, k, 0, strSelectedTicketName, mCHildView);
                        }
                    }
                }

            }

        } catch (Exception e) {

        }

    }

    private void showBottomSheetDialog(List<EventCustomFieldListQT5> eventCustomFieldList, int animationSource, int pos) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.event_ticket_info, null);
        // Build the dialog
        customDialog = new Dialog(EventBookingDetailQT5Activity.this);
        customDialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);
        window.setBackgroundDrawable(getResources().getDrawable(R.drawable.field_bg_cornner));
//        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lay_fields = (LinearLayout) customDialog.findViewById(R.id.lay_fields);
        tv_ticket_name = (TextView) customDialog.findViewById(R.id.tv_ticket_name);
        close_dialog = (ImageView) customDialog.findViewById(R.id.iv_close);
        btn_save_custom_field = (Button) customDialog.findViewById(R.id.btn_save_custom_field);
        btn_save_custom_field.setOnClickListener(this);

        close_dialog.setOnClickListener(this);
        tv_ticket_name.setText("" + strSelectedTicketName);
        //animation
        btn_save_custom_field.setTag(tv_ticket_name.getText().toString());
        customDialog.getWindow().getAttributes().windowAnimations = animationSource; //style id
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(false);
        lay_fields.removeAllViews();


        JSONObject jsonObject = new JSONObject();
        try {
            if (jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).get(current_ticket_postion).toString().equals("{}")) {
                btn_save_custom_field.setText("ADD GUEST");
            } else {
                jsonObject = (JSONObject) jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).get(current_ticket_postion);
                btn_save_custom_field.setText("Update Guest");
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        //  lay_fields.setTag(strSelectedTicketName);
        for (EventCustomFieldListQT5 field : eventCustomFieldList) {
            if (field.typeName.equals("text")) {
                addText(field, jsonObject);
            }
            if (field.typeName.equals("textarea")) {
                addText(field, jsonObject);
            } else if (field.typeName.equals("radio")) {
                addRadio(field, jsonObject);
            } else if (field.typeName.equals("date")) {
                addDate(field, jsonObject);
            } else if (field.typeName.equals("time")) {
                addTime(field, jsonObject);
            } else if (field.typeName.equals("select")) {
                addSpinner(field, jsonObject);
            } else if (field.typeName.equals("checkbox")) {
                addCheckBoxes(field, jsonObject);
            } else if (field.typeName.equals("file")) {
                addDocumentType(field, jsonObject);
            } else if (field.typeName.equals("disclaimer")) {
                addDiscalimertype(field, jsonObject);
            }
        }
        customDialog.show();
    }

    private void addDiscalimertype(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_discalimer, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        LinearLayout ll_download = (LinearLayout) v.findViewById(R.id.ll_download);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        CheckBox cb_disclamer = (CheckBox) v.findViewById(R.id.cb_disclamer);
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.isRequire);
        v.setTag(field.typeName);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        cb_disclamer.setTag(false);
        cb_disclamer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_disclamer.setTag(true);
                } else {
                    cb_disclamer.setTag(false);
                }
            }
        });
        ll_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(field.disclaimer));
                startActivity(i);
            }
        });

        tv_field_title.setTag(field.isRequire);
        lay_fields.addView(v);
    }


    public void addDocuemnTypeasChild(LinearLayout childcontainer, GetSublistview.CustomCHild field) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_document, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_field_type = (TextView) v.findViewById(R.id.tv_field_type);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        RelativeLayout rl_media = (RelativeLayout) v.findViewById(R.id.rl_media);
        TextView tv_filechoosenss = (TextView) v.findViewById(R.id.tv_filechoosen);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        if (field.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        filetypes = field.options;
        // tv_field_type.setTag("Please select " + field.name);
        rl_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions(EventBookingDetailQT5Activity.this);
                tv_filechoosenss.setTag(field.name);
                tv_filechoosen = tv_filechoosenss;

            }
        });
        if (!jsonObjectTicketFields.toString().equals("{}")) {
            Iterator keys = jsonObjectTicketFields.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        tv_filechoosenss.setText(jsonObjectTicketFields.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        tv_field_title.setTag(field.is_Require);
        lay_fields.addView(v);

    }

    public void addDocumentType(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_document, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_field_type = (TextView) v.findViewById(R.id.tv_field_type);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        RelativeLayout rl_media = (RelativeLayout) v.findViewById(R.id.rl_media);
        TextView tv_filechoosenss = (TextView) v.findViewById(R.id.tv_filechoosen);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        filetypes = field.options;
        // tv_field_type.setTag("Please select " + field.name);
        rl_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions(EventBookingDetailQT5Activity.this);
                tv_filechoosenss.setTag(field.name);
                tv_filechoosen = tv_filechoosenss;

            }
        });
        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        tv_filechoosenss.setText(jsonObject.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        tv_field_title.setTag(field.isRequire);
        lay_fields.addView(v);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(EventBookingDetailQT5Activity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(EventBookingDetailQT5Activity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage(EventBookingDetailQT5Activity.this);
                }
                break;
        }

    }

    public boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        } else {
            chooseImage(EventBookingDetailQT5Activity.this);
        }
        return true;
    }

    private void chooseImage(Context context) {
        ArrayList<String> mList = new ArrayList<>();
        if (filetypes.contains(AppConstants.PDF_FILES_ENABLE)) {
            mList.add("application/pdf");
        }
        if (filetypes.contains(AppConstants.PNG_FILES_ENABLE)) {
            mList.add("image/png");
        }
        if (filetypes.contains(AppConstants.JPG_FILES_ENABLE)) {
            mList.add("image/jpeg");
        }
        if (filetypes.contains(AppConstants.XLS_FILES_ENABLE)) {
            mList.add("application/vnd.ms-excel");
        }
        if (filetypes.contains(AppConstants.DOC_FILES_ENABLE)) {
            mList.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }
        if (filetypes.contains(AppConstants.TXT_FILES_ENABLE)) {
            mList.add("text/plain");
        }
        final CharSequence[] optionsMenu = {"File Folder", "Image Gallery", "Dismiss"}; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("File Folder")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("docx"), "application/msword");
                    intent.setDataAndType(Uri.parse("pdf"), "application/pdf");
                    intent.setDataAndType(Uri.parse("ppt"), "application/vnd.ms-powerpoint");
                    intent.setDataAndType(Uri.parse("uri"), "*/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 10);
                } else if (optionsMenu[i].equals("Image Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 11);
                } else if (optionsMenu[i].equals("Dismiss")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }


    public void addCheckBoxes(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_checboxes, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        LinearLayout lay_checboxes = (LinearLayout) v.findViewById(R.id.lay_checboxes);
        LinearLayout ll_checkboxcontainer = (LinearLayout) v.findViewById(R.id.ll_checkboxcontainer);
        lay_checboxes.removeAllViews();
        if (check_box_child == null) {
            check_box_child = ll_checkboxcontainer;
        }
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.isRequire);
        String[] arrayOptions = field.options.split(",");
        lay_checboxes.setTag("Please select " + field.name);
        if (arrayOptions.length > 0) {
            for (int i = 0; i < arrayOptions.length; i++) {
                addCheckboxOptions(arrayOptions[i], lay_checboxes, i, jsonObject, field, ll_checkboxcontainer);
            }
        }
        lay_fields.addView(v);
    }


    public void addText(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_text, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        EditText et_field_type = (EditText) v.findViewById(R.id.et_field_type);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.isRequire);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        et_field_type.setFilters(new InputFilter[]{new InputFilter.LengthFilter(field.length)});
        et_field_type.setTag("Please enter " + field.name);
        if (field.name.contains("Mobile")) {
            et_field_type.setInputType(InputType.TYPE_CLASS_PHONE);
            et_field_type.setMaxLines(8);
        } else if (field.name.contains("Email")) {
            et_field_type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (field.name.contains("Address")) {
            //     et_field_type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            et_field_type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }
        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        et_field_type.setText(jsonObject.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }
        lay_fields.addView(v);

    }

    public void addChildAsText(LinearLayout childcontainer, GetSublistview.CustomCHild field) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_textaschild, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        EditText et_field_type = (EditText) v.findViewById(R.id.et_field_type);

        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.is_Require);
        if (field.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        et_field_type.setFilters(new InputFilter[]{new InputFilter.LengthFilter(field.length)});
        et_field_type.setTag("Please enter " + field.name);
        if (field.name.contains("Mobile")) {
            et_field_type.setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (field.name.contains("Email")) {
            et_field_type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (field.name.contains("Address")) {
            //     et_field_type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            et_field_type.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }
        if (!jsonObjectTicketFields.toString().equals("{}")) {
            Iterator keys = jsonObjectTicketFields.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        et_field_type.setText(jsonObjectTicketFields.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }
        childcontainer.addView(v);
    }


    public void addTicketFieldData(String name, String genderEmail, Boolean isVisible) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ticket_field_info, null, false);
        TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
        TextView tv_gender_email = (TextView) v.findViewById(R.id.tv_gender_email);
        View view = (View) v.findViewById(R.id.view);
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        tv_name.setText("" + name);
        tv_gender_email.setText("" + genderEmail);
        //    viewTicketField.addView(v);

    }

    public void addRadioForChild(LinearLayout childcontainer, GetSublistview.CustomCHild childview) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_radio_child, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_childtitle);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        LinearLayout lay_radio_group = (LinearLayout) v.findViewById(R.id.lay_radio_childgroup);

        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        lay_radio_group.removeAllViews();
        v.setTag(childview.typeName);
        tv_field_title.setText("" + childview.name);
        tv_field_title.setTag(childview.is_Require);
        if (childview.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        String[] arrayOptions = childview.options.split(",");
        lay_radio_group.setTag("Please select " + childview.name);
        if (arrayOptions.length > 0) {
            for (int i = 0; i < arrayOptions.length; i++) {
                addOptionsforChild(arrayOptions[i], lay_radio_group, i, jsonObjectTicketFields, childview, childview.id);
            }
        }
        childcontainer.addView(v);
    }

    private void addOptionsforChild(String strOptions, LinearLayout lay_radio_group, int position, JSONObject jsonObjectTicketFields, GetSublistview.CustomCHild field, Integer id) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_option, null, false);

        android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioButton radioButton = (RadioButton) v.findViewById(R.id.rb);
        radioButton.setLayoutParams(params);
        radioButton.setText(strOptions);
        if (jsonObjectTicketFields.toString().equals("{}")) {
            if (position == 0) radioButton.setChecked(true);
        }
        if (!jsonObjectTicketFields.toString().equals("{}")) {
            Iterator keys = jsonObjectTicketFields.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        if (jsonObjectTicketFields.getString(currentDynamicKey).equals(strOptions)) {
                            radioButton.setChecked(true);
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }


        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chCount = lay_radio_group.getChildCount();
                if (chCount > 0) {
                    for (int i = 0; i < chCount; i++) {
                        if (i != position) {
                            RadioButton radioButton1 = (RadioButton) lay_radio_group.getChildAt(i).findViewById(R.id.rb);
                            radioButton1.setChecked(false);
                        }
                    }
                }

            }
        });
        lay_radio_group.addView(v);
    }


    public void addRadio(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_radio, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        LinearLayout lay_radio_group = (LinearLayout) v.findViewById(R.id.lay_radio_group);
        LinearLayout ll_radiochild = (LinearLayout) v.findViewById(R.id.ll_radiochild);
        lay_radio_group.removeAllViews();
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.isRequire);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        //   childofradioconatiner=ll_radiochild;
        String[] arrayOptions = field.options.split(",");
        lay_radio_group.setTag("Please select " + field.name);
        if (arrayOptions.length > 0) {
            for (int i = 0; i < arrayOptions.length; i++) {
                addOptions(arrayOptions[i], lay_radio_group, i, jsonObject, field, field.id, ll_radiochild);
            }
        }
        lay_fields.addView(v);
    }

    public void addSpinner(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_spinner, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        LinearLayout ll_spinnercontainter = v.findViewById(R.id.ll_spinnercontainter);
        v.setTag(field.typeName);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.isRequire);
        ArrayList<String> listOption = new ArrayList<>();
        if (field.name.equals("Nationality")) {


            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);
            String jsadson = sharedPreferences.getString("Countries", "");
            Gson gson = new Gson();
            if (jsadson.isEmpty()) {
            } else {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                listOption = gson.fromJson(jsadson, type);
            }


        } else {
            String[] arrayList = field.options.split(",");
            listOption.addAll(Arrays.asList(arrayList));
        }
        spinner.setTag("Please select " + field.name);
        if (listOption.size() > 0) {
            listOption.add(0, "Select");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.time_spinner_item, listOption);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (ll_spinnercontainter != null) {
                        ll_spinnercontainter.removeAllViews();
                    }
                    if (field.hasSubFields && isFirstTime) {
                        presenter.GetCustomFieldSubOptionsList(field.id, position, ll_spinnercontainter, false);
                    }
                    isFirstTime = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        for (int i = 0; i < listOption.size(); i++) {
                            if (jsonObject.getString(currentDynamicKey).equals(listOption.get(i))) {
                                spinner.setSelection(i);
                            }
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }


        lay_fields.addView(v);
    }


    public void addCheckboxChildsOptions(String strOptions, LinearLayout cbGroupView, int position, JSONObject jsonObject, GetSublistview.CustomCHild fields) {

        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_option_checboxes, null, false);
        android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CheckBox checkbox = (CheckBox) v.findViewById(R.id.cb);
        checkbox.setLayoutParams(params);
        checkbox.setText(strOptions);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(fields.name)) {
                    try {
                        if (jsonObject.getString(currentDynamicKey).equals(strOptions)) {
                            checkbox.setChecked(true);
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }
        cbGroupView.addView(v);
    }


    public void addCheckboxOptions(String strOptions, LinearLayout cbGroupView, int position, JSONObject jsonObject, EventCustomFieldListQT5 fields, LinearLayout ll_checkboxcontainer) {

        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_option_checboxes, null, false);
        android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CheckBox checkbox = (CheckBox) v.findViewById(R.id.cb);
        checkbox.setLayoutParams(params);
        checkbox.setText(strOptions);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    if (fields.hasSubFields) {
                        presenter.GetCustomFieldSubOptionsList(fields.id, position + 1, ll_checkboxcontainer, false);
                    }
                } else {
                    if (fields.hasSubFields) {
                        presenter.GetCustomFieldSubOptionsList(fields.id, position + 1, ll_checkboxcontainer, true);
                    }
                }

            }
        });

        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(fields.name)) {
                    try {
                        if (jsonObject.getString(currentDynamicKey).equals(strOptions)) {
                            checkbox.setChecked(true);
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }
        cbGroupView.addView(v);
    }


    private void addcheckboxChild() {

    }

    public void addOptions(String strOptions, LinearLayout rbGroupView, int position, JSONObject jsonObject, EventCustomFieldListQT5 field, Integer fieldID, LinearLayout container) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_option, null, false);

        android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioButton radioButton = (RadioButton) v.findViewById(R.id.rb);
        radioButton.setLayoutParams(params);
        radioButton.setText(strOptions);
//        if (jsonObject.toString().equals("{}")) {
//            if (position == 0) radioButton.setChecked(true);
//        }
        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        if (jsonObject.getString(currentDynamicKey).equals(strOptions)) {
                            radioButton.setChecked(true);
                        }
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }


        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int chCount = rbGroupView.getChildCount();
                if (chCount > 0) {
                    for (int i = 0; i < chCount; i++) {
                        if (i != position) {
                            RadioButton radioButton1 = (RadioButton) rbGroupView.getChildAt(i).findViewById(R.id.rb);
                            radioButton1.setChecked(false);
                        }
                    }
                    if (container != null) {
                        container.removeAllViews();
                    }
                    if (field.hasSubFields) {
                        presenter.GetCustomFieldSubOptionsList(field.id, position + 1, container, false);
                    }

                }


            }
        });
        rbGroupView.addView(v);
    }


    public void addDateasChild(LinearLayout childcontainer, GetSublistview.CustomCHild field) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_dateaschild, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_field_type = (TextView) v.findViewById(R.id.tv_field_type);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        if (field.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        tv_field_type.setTag("Please select " + field.name);
        tv_field_title.setTag(field.is_Require);
        tv_field_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EventBookingDetailQT5Activity.this, DatePickerDialog(tv_field_type), cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        if (!jsonObjectTicketFields.toString().equals("{}")) {
            Iterator keys = jsonObjectTicketFields.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        tv_field_type.setText(jsonObjectTicketFields.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }

        lay_fields.addView(v);
    }


    public void addDate(EventCustomFieldListQT5 field, JSONObject jsonObject) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_date, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_field_type = (TextView) v.findViewById(R.id.tv_field_type);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        tv_field_type.setTag("Please select " + field.name);
        tv_field_title.setTag(field.isRequire);
        tv_field_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EventBookingDetailQT5Activity.this, DatePickerDialog(tv_field_type), cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        tv_field_type.setText(jsonObject.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }

        lay_fields.addView(v);
    }


    public void addTimeAsChild(LinearLayout childcontainer, GetSublistview.CustomCHild field) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_datechild, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_field_type = (TextView) v.findViewById(R.id.tv_field_type);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        if (field.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        tv_field_type.setTag("Please select " + field.name);
        tv_field_title.setTag(field.is_Require);
        tv_field_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(EventBookingDetailQT5Activity.this, (view1, hourOfDay, minute) -> {
                    SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                    try {
                        Date date = displayFormat.parse(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        String daasdsdate = parseFormat.format(date);
                        tv_field_type.setText(daasdsdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timePickerDialog.dismiss();
                },
                        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(EventBookingDetailQT5Activity.this));
                timePickerDialog.show();
            }
        });

        if (!jsonObjectTicketFields.toString().equals("{}")) {
            Iterator keys = jsonObjectTicketFields.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        tv_field_type.setText(jsonObjectTicketFields.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }
        lay_fields.addView(v);
    }

    public void addTime(EventCustomFieldListQT5 field, JSONObject jsonObject) {

        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_date, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_field_type = (TextView) v.findViewById(R.id.tv_field_type);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        if (field.isRequire) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        tv_field_type.setTag("Please select " + field.name);
        tv_field_title.setTag(field.isRequire);
        tv_field_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(EventBookingDetailQT5Activity.this, (view1, hourOfDay, minute) -> {
                    SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
                    try {
                        Date date = displayFormat.parse(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                        String daasdsdate = parseFormat.format(date);
                        tv_field_type.setText(daasdsdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timePickerDialog.dismiss();
                },
                        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), DateFormat.is24HourFormat(EventBookingDetailQT5Activity.this));
                timePickerDialog.show();
            }
        });

        if (!jsonObject.toString().equals("{}")) {
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                if (currentDynamicKey.equals(field.name)) {
                    try {
                        tv_field_type.setText(jsonObject.getString(currentDynamicKey));
                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }
                }
            }

        }
        lay_fields.addView(v);
    }


    private DatePickerDialog.OnDateSetListener DatePickerDialog(TextView tvDate) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
                tvDate.setText("" + sdf.format(cal.getTime()));

            }
        };

        return dateSetListener;
    }


    @Override
    public void setEventBookingDetail(EventBookingQT5Model eventBookingQT5Model) {
        if (eventBookingQT5Model.statusCode.equals("200")) {
            CountryCode = eventBookingQT5Model.data.currencyCode;
            isDTCM = eventBookingQT5Model.data.isDTCMEvent;
            WhatsappCharges = eventBookingQT5Model.data.whatsAppCharge;
            smsCharges = eventBookingQT5Model.data.smsCharge;

            mEventDates.clear();
            if (eventBookingQT5Model.data.eventBookingDates != null && !eventBookingQT5Model.data.eventBookingDates.isEmpty()) {
                eventTime = eventBookingQT5Model.data.eventTime;
                mEventDates.addAll(eventBookingQT5Model.data.eventBookingDates);
                if (mEventDates.size() == 1) {
                    tv_daypasscount.setText("1 \n  DAY PASS");
                } else {
                    tv_daypasscount.setText(mEventDates.size() + " \n DAYS PASS");
                }
                mEventDatesTemp.clear();
                int dates_size = 0;
                if (eventBookingQT5Model.data.eventBookingDates.size() > 3) {
                    dates_size = 3;
                } else {
                    dates_size = eventBookingQT5Model.data.eventBookingDates.size();
                }
                if (eventBookingQT5Model.data.eventBookingDates.size() > 3) {
                    ll_selecteddate.setVisibility(View.VISIBLE);
                } else {
                    ll_selecteddate.setVisibility(View.GONE);
                }
                for (int i = 0; i < dates_size; i++) {
                    mEventDatesTemp.add(eventBookingQT5Model.data.eventBookingDates.get(i));
                }
                eventDateAdapter = new EventDateAdapter(mEventDatesTemp, getApplicationContext(), EventBookingDetailQT5Activity.this, 0, false);
                recycler_date.setAdapter(eventDateAdapter);
            } else {
                recycler_date.setVisibility(View.GONE);
            }
            isSingleSelection = eventBookingQT5Model.data.isSingleSelection;

            if (eventBookingQT5Model.data.isDayPass) {
                card_isdaypass.setVisibility(View.VISIBLE);
            } else {
                card_isdaypass.setVisibility(View.GONE);
            }

        } else if (eventBookingQT5Model.statusCode.equals("404")) {
            recycler_date.setVisibility(View.GONE);
        }

    }

    @Override
    public void setEventShowTimeByEventDate(EventTimeQT5Model eventTimeQT5Model) {
        if (eventTimeQT5Model.statusCode.equals("200")) {
            eventTimeQT5Data = eventTimeQT5Model.data;
            if (eventTimeQT5Data != null && eventTimeQT5Data.size() > 0) {
                eventVenue = eventTimeQT5Data.get(0).venue;
            }
            setTimeSpinner(eventTimeQT5Data);
        } else if (eventTimeQT5Model.statusCode.equals("404")) {
            isTimeSlot = false;
            lay_spinner.setVisibility(View.GONE);
            presenter.getEventTicketsByDate(ID, selectedEventDate, "", "");
        }
    }

    @Override
    public void setEventTicketsByDate(EventTicketQT5Model eventTicketQT5Model) {
        if (eventTicketQT5Model.statusCode.equals("200")) {
            if (isDayPassActive) {
                eventDateAdapter = new EventDateAdapter(mEventDatesTemp, getApplicationContext(),
                        EventBookingDetailQT5Activity.this, 0, true);
                recycler_date.setAdapter(eventDateAdapter);
            }
            lay_ticket_view.removeAllViews();
            TotalTicketCount = 0;
            TotalTicketAmount = 0.0;
            TotalServiceAmount = 0.0;
            tv_select_tickets.setText("Number of Tickets");
            tv_tickets.setText("Select No. of Tickets");
            lay_amount.setVisibility(View.VISIBLE);
            total_amount.setText("0 QAR");
            jsonObjectTicketFields = new JSONObject();
            if (eventTicketQT5Model.eventTickets != null && !eventTicketQT5Model.eventTickets.isEmpty()) {
                typeList = eventTicketQT5Model.eventTickets;
                mCategoryNames.clear();
                for (int i = 0; i < typeList.size(); i++) {
                    addTicketType(typeList.get(i), i);
                }
            } else {
                typeList = null;
            }
        } else if (eventTicketQT5Model.statusCode.equals("404")) {
        }
    }

    private void addCategoryTickets(String s) {
        View typeView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.event_category_ticket, null, false);
        TextView tv_categoryname = typeView.findViewById(R.id.tv_categoryname);
        tv_categoryname.setText(s);
        lay_ticket_categories.addView(typeView);

    }

    @Override
    public void CheckCustomField(CustomFieldQT5Model customFieldQT5Model) {
        if (customFieldQT5Model.statusCode.equals("200")) {
            presenter.GetEventCustomFieldsList(ID, Ticket_ID, 0, 0, false, null, false);
        } else if (customFieldQT5Model.statusCode.equals("404")) {

        }
    }

    @Override
    public void setUploadDocumentFile(String imagename) {
        tv_filechoosen.setText(imagename);
    }

    @Override
    public void setCustomchildcount(List<GetSublistview.CustomCHild> childlist, LinearLayout childcontainer, Boolean isForRemove, int postion) {
        if (childlist.size() > 0) {
            if (childlist.get(0) != null) {
                if (childlist.get(0).typeName.equals("select")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(current_ticket_postion);
                    } else {
                        addspinnerAsChild(childcontainer, childlist.get(0));
                    }
                }
                if (childlist.get(0).typeName.equals("radio")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(current_ticket_postion);
                    } else {
                        addRadioForChild(childcontainer, childlist.get(0));
                    }

                }
                if (childlist.get(0).typeName.equals("checkbox")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(postion);
                    } else {
                        addCheckboxForChild(childcontainer, childlist.get(0));
                    }
                }
                if (childlist.get(0).typeName.equals("text")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(postion);
                    } else {
                        addChildAsText(childcontainer, childlist.get(0));
                    }

                }

                if (childlist.get(0).typeName.equals("date")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(postion);
                    } else {
                        addDateasChild(childcontainer, childlist.get(0));
                    }

                }
                if (childlist.get(0).typeName.equals("time")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(postion);
                    } else {
                        addTimeAsChild(childcontainer, childlist.get(0));
                    }

                }

                if (childlist.get(0).typeName.equals("file")) {
                    if (isForRemove) {
                        childcontainer.removeViewAt(postion);
                    } else {
                        addDocuemnTypeasChild(childcontainer, childlist.get(0));
                    }

                }
            }
        }
    }


    public void addCheckboxForChild(LinearLayout childcontainer, GetSublistview.CustomCHild field) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_child_checboxes, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        LinearLayout lay_checboxes = (LinearLayout) v.findViewById(R.id.lay_checboxes);
//        LinearLayout ll_checkboxcontainer = (LinearLayout) v.findViewById(R.id.ll_checkboxcontainer);
        lay_checboxes.removeAllViews();
        if (field.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }

        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        v.setTag(field.typeName);
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.is_Require);
        String[] arrayOptions = field.options.split(",");
        lay_checboxes.setTag("Please select " + field.name);
        if (arrayOptions.length > 0) {
            for (int i = 0; i < arrayOptions.length; i++) {
                addCheckboxChildsOptions(arrayOptions[i], lay_checboxes, i, jsonObjectTicketFields, field);
            }
        }
        childcontainer.addView(v);
    }

    public void addspinnerAsChild(LinearLayout layoutconatiner, GetSublistview.CustomCHild field) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.field_spinner_child, null, false);
        TextView tv_field_title = (TextView) v.findViewById(R.id.tv_field_title);
        TextView tv_mandatorytext = (TextView) v.findViewById(R.id.tv_mandatorytext);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);

        v.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


        v.setTag(field.typeName);
        if (field.is_Require) {
            tv_mandatorytext.setVisibility(View.VISIBLE);
        }
        tv_field_title.setText("" + field.name);
        tv_field_title.setTag(field.is_Require);
        ArrayList<String> listOption = new ArrayList<>();
        if (field.name.equals("Nationality")) {
            SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE);
            String jsadson = sharedPreferences.getString("Countries", "");
            Gson gson = new Gson();
            if (jsadson.isEmpty()) {
            } else {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                listOption = gson.fromJson(jsadson, type);
            }
        } else {
            String[] arrayList = field.options.split(",");
            listOption.addAll(Arrays.asList(arrayList));
        }
        spinner.setTag("Please select " + field.name);
        if (listOption.size() > 0) {
            listOption.add(0, "Select");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.time_spinner_item, listOption);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        layoutconatiner.addView(v);
    }

    public void setTimeSpinner(List<EventTimeQT5Data> data) {
        if (data == null || data.isEmpty()) data = new ArrayList<>();
        if (data != null && !data.isEmpty()) {
            //  if (data.get(0).startTime.equals("")) data.remove(0);
            if (data != null && !data.isEmpty()) {
                isTimeSlot = true;
                lay_spinner.setVisibility(View.VISIBLE);
            } else {
                isTimeSlot = false;
                lay_spinner.setVisibility(View.GONE);
            }
        } else {
            isTimeSlot = false;
            lay_spinner.setVisibility(View.GONE);
        }
        if (isTimeSlot) {
            ArrayList<String> mDataList = new ArrayList<>();
            String startdate = "";
            String enddate = "";
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).startTime.isEmpty() && !data.get(i).endTime.isEmpty()) {
                    mDataList.add(data.get(i).startTime + " - " + data.get(i).endTime);
                }
                if (!data.get(i).startTime.isEmpty() && data.get(i).endTime.isEmpty()) {
                    mDataList.add(data.get(i).startTime);
                }
                if (data.get(i).startTime.isEmpty() && !data.get(i).endTime.isEmpty()) {
                    mDataList.add(data.get(i).endTime);

                }
            }
            if (mDataList.size() > 1) {
                spinner_time.setVisibility(View.VISIBLE);
                tv_timeslot.setVisibility(View.GONE);
            } else {
                spinner_time.setVisibility(View.GONE);
                if (mDataList.size() > 0) {
                    tv_timeslot.setText(mDataList.get(0));
                    tv_timeslot.setVisibility(View.VISIBLE);
                } else {
                    tv_timeslot.setVisibility(View.GONE);
                    spinner_time.setVisibility(View.GONE);
                    lay_spinner.setVisibility(View.GONE);
                }
                presenter.getEventTicketsByDate(ID, selectedEventDate, "", "");

            }

            CustomSpinnerAdapater customSpinnerAdapater = new CustomSpinnerAdapater(this, mDataList, data);
//          customSpinnerAdapater.setDropDownViewResource(R.layout.custom_spinner_dropdown_item); // Set the custom layout
            spinner_time.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            spinner_time.setAdapter(customSpinnerAdapater);
            spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //7:00 PM - 9:00 PM
                    TotalTicketCount = 0;
                    TotalTicketPerson = 0;
                    String s[] = spinner_time.getSelectedItem().toString().split("-");
                    if (s.length == 1) {
                        presenter.getEventTicketsByDate(ID, selectedEventDate, s[0].trim(), "");
                    } else {
                        presenter.getEventTicketsByDate(ID, selectedEventDate, s[0].trim(), s[1].trim());
                    }
                    customSpinnerAdapater.setSelectedItemPosition(i);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {
            presenter.getEventTicketsByDate(ID, selectedEventDate, "", "");
        }
    }

    public void addTicketType(EventTicketQT5Type type, int position) {
        View typeView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.event_ticket_item, null, false);
        LinearLayout lay_buttons = (LinearLayout) typeView.findViewById(R.id.lay_buttons);
        LinearLayout btn_remove = (LinearLayout) typeView.findViewById(R.id.btn_remove);
        LinearLayout btn_add = (LinearLayout) typeView.findViewById(R.id.btn_add);//todo 28sep
        LinearLayout lay_discount = (LinearLayout) typeView.findViewById(R.id.lay_discount);
        LinearLayout lay_ticket_field_data = (LinearLayout) typeView.findViewById(R.id.lay_ticket_field_data);

        lay_ticket_field_data.setTag(type.id);
        mViewsChild.put(type.ticketName, lay_ticket_field_data);
        mParentView.put(type.ticketName, lay_ticket_view);


        TextView text_sold_out = (TextView) typeView.findViewById(R.id.text_sold_out);
        TextView text_price = (TextView) typeView.findViewById(R.id.text_price);
        TextView text_actual_price = (TextView) typeView.findViewById(R.id.text_actual_price);
        TextView txt_minbuy = (TextView) typeView.findViewById(R.id.txt_minbuy);
        TextView txt_buyonegetone = (TextView) typeView.findViewById(R.id.txt_buyonegetone);
        //    TextView text_discount = (TextView) typeView.findViewById(R.id.text_discount);

        TextView text_ticket_type = (TextView) typeView.findViewById(R.id.text_ticket_type);
        TextView tv_count = (TextView) typeView.findViewById(R.id.tv_count);

        ll_childcontainer = (LinearLayout) typeView.findViewById(R.id.ll_childcontainer);

        String category = null;
        if (type.category != null) {
            category = type.category;
        } else {
            category = "";
        }
        if (category.equals("")) {
            if (type.discount > 0) {
                text_ticket_type.setText(type.ticketName + " (Early Bird)");
            } else {
                text_ticket_type.setText(type.ticketName);
            }
        } else {

            if (type.discount > 0) {
                text_ticket_type.setText(type.ticketName + " (" + type.category + ")" + " (Early Bird)");
            } else {
                text_ticket_type.setText(type.ticketName + " (" + type.category + ")");
            }

        }
        if (type.quantity > 0) {
            lay_buttons.setVisibility(View.VISIBLE);
            text_sold_out.setVisibility(View.GONE);
        } else {
            lay_buttons.setVisibility(View.GONE);
            text_sold_out.setVisibility(View.VISIBLE);
        }
        typeList.get(position).setTicket_quantity(typeList.get(position).quantity);
        typeList.get(position).quantity = 0;
        JSONArray jsonArray = new JSONArray();
        try {
            jsonObjectTicketFields.put("" + type.ticketName, jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double finalPrice = 0.00;

        if (type.minAllowed > 1) {
            txt_minbuy.setText("Min buy quantity " + type.minAllowed);
            lay_discount.setVisibility(View.VISIBLE);
        } else {
            lay_discount.setVisibility(View.GONE);
        }

        if (type.ticketDetails != null) {
            txt_buyonegetone.setText(type.ticketDetails);
        } else {
            txt_buyonegetone.setVisibility(View.GONE);
        }

        if (type.discount > 0) {
            if (type.discountType == 1) {
                finalPrice = type.price - type.discount;
//                int temp_finalprice = (int) finalPrice;
                text_price.setText("" + String.format("%.2f", finalPrice) + " " + CountryCode);
                //     text_price.setText("" + finalPrice + " " + CountryCode);
                //   text_actual_price.setText(QTUtils.parseDouble(type.price) + " " + CountryCode);
                //  text_discount.setText("Flat " + QTUtils.parseDouble(type.discount) + " " + CountryCode);
            } else if (type.discountType == 2) {
                Double dis = (type.price * type.discount) / 100;
                finalPrice = type.price - dis;
//                int final_temp_price = (int) finalPrice;
                text_price.setText("" +  String.format("%.2f", finalPrice) + " " + CountryCode);
                // text_price.setText("" + QTUtils.parseDouble(finalPrice) + " " + CountryCode);
                //   text_actual_price.setText(QTUtils.parseDouble(type.price) + " " + CountryCode);
                //   text_discount.setText("" + QTUtils.parseDouble(type.discount) + "%");
//                if (type.minAllowed > 1) {
//                    txt_minbuy.setText("Min buy quantity " + type.minAllowed);
//                }

            }
            text_actual_price.setVisibility(View.VISIBLE);
            // lay_discount.setVisibility(View.GONE);
        } else {
            finalPrice = type.price;
//            int final_temp_price = (int) finalPrice;
            text_price.setText("" +  String.format("%.2f", finalPrice) + " " + CountryCode);
            //  text_price.setText(QTUtils.parseDouble(type.price) + " " + CountryCode);
            text_actual_price.setVisibility(View.GONE);
            //   lay_discount.setVisibility(View.GONE);
        }
        btn_add.setTag(finalPrice);
        btn_remove.setTag(finalPrice);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    btn_add.setEnabled(false);

                btn_add.setEnabled(true);
                Log.d("2Nov", "btn_add setEnabled true");*/

                Log.d("2Nov", "btn_add setEnabled False");

                ArrayList<SelectedParentCount> mSelectedTicketList = new ArrayList<>();
                boolean isOnlyChild = false;
                if (type.chieldTicketXML != null && !type.chieldTicketXML.equals("null") && !type.chieldTicketXML.equals("")) {
                    ArrayList<String> mTicketList = new ArrayList<>();
                    Log.d("2Nov", "type.chieldTicketXML != null");

                    for (EventTicketQT5Type eventTicketQT5Type : typeList) {
                        if (type.chieldTicketXML.contains("" + eventTicketQT5Type.id)) {
                            if (eventTicketQT5Type.quantity == 0) {
                                mTicketList.add(eventTicketQT5Type.ticketName);

                                Log.d("2Nov", "eventTicketQT5Type.quantity == 0");

                            }
                            if (eventTicketQT5Type.quantity > 0) {
                                Log.d("2Nov", "eventTicketQT5Type.quantity > 0");

                                SelectedParentCount selectparent = new SelectedParentCount(eventTicketQT5Type.admit, eventTicketQT5Type.ticketName);
                                mSelectedTicketList.add(selectparent);
                                isOnlyChild = true;
                            }

                        }
                    }

                    if (mTicketList.size() > 0) {
                        Log.d("2Nov", "mTicketList.size() > 0");

                        StringBuffer sb = new StringBuffer();
                        for (String s : mTicketList) {
                            sb.append(s);
                            sb.append(",");
                        }
                        if (!isOnlyChild) {
                            Log.d("2Nov", "!isOnlyChild");

                            String str = sb.toString().substring(0, sb.length() - 1);
                            QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "You need to buy minimum 1 ticket from it's dependent (" + str + ") tickets!");
                            return;
                        }

                        Log.d("2Nov", "outof the loop");

                    }
                }

                Double _Price = (Double) view.getTag();
                int countAllow = 1;
                Ticket_ID = type.id;
                if (Integer.parseInt(tv_count.getText().toString()) == 0) {
                    countAllow = type.minAllowed;
                }
                Log.d("2Nov", "countAllow");

                if (typeList.get(position).getTicket_quantity() == Integer.parseInt(tv_count.getText().toString())) {
                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "Quantity out of stock.");
                    return;
                }

                int count = Integer.parseInt(tv_count.getText().toString()) + countAllow;
                if (count > type.maxAllowed) {
                    QTUtils.showDialogbox(EventBookingDetailQT5Activity.this, "You can book only " + type.maxAllowed + " tickets!");
                    return;
                }

                if (count > 0 && isSingleSelection && TotalTicketCount > 0) {
                    Log.d("2Nov", "count > 0 ");

                    if (strSelectedTicketName.equals(type.ticketName)) {

                    } else {
                        if (mSelectedTicketList.size() > 0) {

                        } else {
                            TotalTicketCount = 0;
                            TotalTicketAmount = 0.0;
                            TotalTicketPerson = 0;
                            TotalServiceAmount = 0.0;
                            jsonObjectTicketFields = new JSONObject();
                        }
                        resetTicket(mSelectedTicketList);
                    }

                }


                // shubham changesq
                // presenter.CheckCustomField(ID,
                tv_count.setText("" + count);
                typeList.get(position).quantity = count;
                TotalTicketCount = TotalTicketCount + countAllow;
                if (!type.isFixPrice) {
                    _Price = _Price * countAllow;
                }
                TotalTicketAmount = TotalTicketAmount + _Price;

                if (type.chargeType > 0) {
                    Log.d("2Nov", "type.chargeType > 0");

                    if (type.chargeType == 1) {
                        if (!type.isFixPrice) {
                            TotalServiceAmount = TotalServiceAmount + (Double.valueOf(type.serviceCharge) * countAllow);
                        } else {
                            TotalServiceAmount = TotalServiceAmount + (Double.valueOf(type.serviceCharge));
                        }
                    }
                    if (type.chargeType == 2) {
                        TotalServiceAmount = TotalServiceAmount + (_Price * type.serviceCharge) / 100;
                        //_Price * (Double.valueOf(type.serviceCharge) * countAllow);
                    }

                }

                lay_amount.setVisibility(View.VISIBLE);
                if (TotalTicketCount == 1) {
                    tv_select_tickets.setText("Number of Tickets");
                    Log.d("26Oct", "type.admit " + type.admit);

                    if (type.admit > 1) {
                        int personCount = countAllow * type.admit;
                        Log.d("26Oct", "personCount " + personCount);
                        Log.d("26Oct", "countAllow " + countAllow);
                        Log.d("26Oct", "TotalTicketPerson B" + TotalTicketPerson);

                        TotalTicketPerson = TotalTicketPerson + personCount;

                        Log.d("26Oct", "TotalTicketCount " + TotalTicketCount + " " + "TotalTicketPerson A" + TotalTicketPerson);

                        tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                    } else {
                        Log.d("26Oct", "countAllow else" + countAllow);

                        TotalTicketPerson = TotalTicketPerson + countAllow;
                        Log.d("26Oct", "TotalTicketPerson else" + TotalTicketPerson);

                        tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");

                        Log.d("26Oct", "TotalTicketCount " + TotalTicketCount + " " + "TotalTicketPerson A" + TotalTicketPerson);

                    }

                }

                else {
                    tv_select_tickets.setText("Number of Tickets");
                    if (type.admit > 1) {
                        int personCount = countAllow * type.admit;
                        TotalTicketPerson = TotalTicketPerson + personCount;
                        tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");

                        Log.d("26Oct", "TotalTicketCount " + TotalTicketCount + " " + "Persons A" + TotalTicketPerson);

                    } else {
                        TotalTicketPerson = TotalTicketPerson + countAllow;
                        tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                        Log.d("26Oct", "TotalTicketCount " + TotalTicketCount + " " + "Persons A" + TotalTicketPerson);

                    }
                }

//                int temp_totalamount = (int) TotalTicketAmount;
                total_amount.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
                //  total_amount.setText(QTUtils.parseDouble(TotalTicketAmount) + " " + CountryCode);

                strSelectedTicketName = type.ticketName;
                //    viewTicketField = lay_ticket_field_data;
                addChildView(Integer.parseInt(tv_count.getText().toString()), position, type, false);
                Log.d("ticket_position :", String.valueOf(position));
                Log.d("ticketperson :", String.valueOf(TotalTicketPerson));


            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("2Nov", "btn_remove setEnabled False");

                /*btn_remove.setEnabled(false);*/
                boolean isAnyChild = false;
                Double _Price = (Double) view.getTag();
                if (Integer.parseInt(tv_count.getText().toString()) == 0) return;
                int countAllow = 1;
                if (Integer.parseInt(tv_count.getText().toString()) == type.minAllowed) {
                    countAllow = type.minAllowed;
                }
                Ticket_ID = type.id;
                int count = Integer.parseInt(tv_count.getText().toString()) - countAllow;
/*
                btn_remove.setEnabled(true);
*/
                Log.d("2Nov", "btn_remove setEnabled True");

                if (count == 0) {
                    TotalTicketCount = TotalTicketCount - countAllow;

                    if (!type.isFixPrice) {
                        _Price = _Price * countAllow;
                    }
                    TotalTicketAmount = TotalTicketAmount - _Price;
                    if (type.chargeType == 1) {
                        if (!type.isFixPrice) {
                            TotalServiceAmount = TotalServiceAmount - (Double.valueOf(type.serviceCharge) * countAllow);
                        } else {
                            TotalServiceAmount = TotalServiceAmount - (Double.valueOf(type.serviceCharge));
                        }
                    }
                    if (type.chargeType == 2) {
                        TotalServiceAmount = TotalServiceAmount - (_Price * type.serviceCharge) / 100;
                        //_Price * (Double.valueOf(type.serviceCharge) * countAllow);
                    }
                    tv_count.setText("" + count);
                    typeList.get(position).quantity = count;
                    if (TotalTicketCount == 0) {
                        /*tv_select_tickets.setText("Select No. of Tickets"); //7Oct
                        tv_tickets.setText("");*/
                        tv_select_tickets.setText("Number of Tickets");
                        tv_tickets.setText("Select No. of Tickets");
                        lay_amount.setVisibility(View.VISIBLE);
                        total_amount.setText("0 QAR");
                        TotalTicketPerson = 0;
                    } else if (TotalTicketCount == 1) {
                        tv_select_tickets.setText("Number of Tickets");
                        if (type.admit > 1) {
                            int personCount = countAllow * type.admit;
                            TotalTicketPerson = TotalTicketPerson - personCount;
                            if (TotalTicketPerson > 1) {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                            } else {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                            }

                            Log.d("26Oct", "btn_remove TotalTicketCount " + TotalTicketCount + " " + "Persons A" + TotalTicketPerson);

                        } else {
                            TotalTicketPerson = TotalTicketPerson - countAllow;
                            tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                            Log.d("26Oct", "TotalTicketCount " + TotalTicketCount + " " + "Persons A" + TotalTicketPerson);

                        }

//                        int temp_totalamount = (int) TotalTicketAmount;
                        //   total_amount.setText(String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
                        total_amount.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
                    } else {
                        tv_select_tickets.setText("Number of Tickets");
                        if (type.admit > 1) {
                            int personCount = countAllow * type.admit;
                            TotalTicketPerson = TotalTicketPerson - personCount;
                            if (TotalTicketPerson > 1) {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                            } else {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                            }
                        } else {
                            TotalTicketPerson = TotalTicketPerson - countAllow;
                            tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                        }
                        Log.d("26Oct", "btn_remove TotalTicketCount " + TotalTicketCount + " " + "Persons A" + TotalTicketPerson);

//                        int temp_totalamount = (int) TotalTicketAmount;
                        total_amount.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
                        // total_amount.setText(String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
                    }

                    //problem areaaaaaaa
                    Log.d("jsonvalue :", jsonObjectTicketFields.toString());
                    Log.d("ticket_position :", String.valueOf(position));
                    if (count == 0) {

                        Log.d("2Nov :", "count == 0");

                        if (type.subChilds != null && !type.subChilds.equals("")) {
                            isAnyChild = true;
                            String[] elements = type.subChilds.split(",");
                            if (mParentView.size() > 0) {
                                for (int i = 0; i < elements.length; i++) {
                                    LinearLayout mView = mParentView.get(elements[i]);
                                    if (mView != null) {
                                        View viewItems = mView.getChildAt(i);
                                        TextView tv_countss = viewItems.findViewById(R.id.tv_count);
                                        int countdssd = Integer.parseInt(tv_countss.getText().toString());
                                        Toast.makeText(EventBookingDetailQT5Activity.this, "" + countdssd, Toast.LENGTH_SHORT).show();
                                        mView.removeAllViews();
                                    }

                                }
                            }
                        }
                    } else {
                        Log.d("2Nov :", "count else");

                    }
                    checkifanychildExit(type, position);
                    addChildView(Integer.parseInt(tv_count.getText().toString()), position, type, isAnyChild);
                    Log.d("2Nov :", "returned");

                    return; //issue
                } else {
                    tv_count.setText("" + count);
                    typeList.get(position).quantity = count;
                    TotalTicketCount = TotalTicketCount - 1;
                    TotalTicketAmount = TotalTicketAmount - _Price;

                    if (type.chargeType == 1) {
                        TotalServiceAmount = TotalServiceAmount - Double.valueOf(type.serviceCharge);
                    }
                    if (type.chargeType == 2) {
                        TotalServiceAmount = TotalServiceAmount - (_Price * type.serviceCharge) / 100;
                        //_Price * (Double.valueOf(type.serviceCharge) * countAllow);
                    }

                    if (TotalTicketCount == 1) {
                        tv_select_tickets.setText("Number of Tickets");
                        if (type.admit > 1) {
                            int personCount = countAllow * type.admit;
                            TotalTicketPerson = TotalTicketPerson - personCount;
                            if (TotalTicketPerson > 1) {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                            } else {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                            }
                        } else {
                            TotalTicketPerson = TotalTicketPerson - countAllow;
                            tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                        }
                    } else {
                        tv_select_tickets.setText("Number of Tickets");
                        if (type.admit > 1) {
                            int personCount = countAllow * type.admit;
                            TotalTicketPerson = TotalTicketPerson - personCount;
                            if (TotalTicketPerson > 1) {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                            } else {
                                tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                            }
                        } else {
                            TotalTicketPerson = TotalTicketPerson - countAllow;
                            tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                        }
                    }

//                    int temp_totalamount = (int) TotalTicketAmount;
                    total_amount.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
                    strSelectedTicketName = type.ticketName;
                    addChildView(Integer.parseInt(tv_count.getText().toString()), position, type, isAnyChild);
                    Log.d("ticketperson :", String.valueOf(TotalTicketPerson));

                }


            }
        });
        lay_ticket_view.addView(typeView);
    }

    private void checkifanychildExit(EventTicketQT5Type type, int position) {
        ArrayList<String> anychildList = new ArrayList<>();
        int posi = -1;
        if (type.subChilds != null && !type.subChilds.equals("null") && !type.subChilds.equals("")) {
            for (EventTicketQT5Type eventTicketQT5Type : typeList) {
                if (type.subChilds.contains("" + eventTicketQT5Type.id)) {
                    anychildList.add(eventTicketQT5Type.ticketName);
                }
            }

            if (anychildList.size() > 0) {
                for (int i = 0; i < anychildList.size(); i++) {
                    // temp = new JSONObject(jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).get(position).toString());
                    try {
                        jsonObjectTicketFields.getJSONArray("" + anychildList.get(i)).remove(i);
                        for (int j = 0; j < typeList.size(); j++) {
                            if (typeList.get(j).ticketName.equals(anychildList.get(i))) {
                                resetchild(j);
                            }
                        }

                    } catch (JSONException exception) {
                        exception.printStackTrace();
                    }

                }
            }


        }


    }

    private void resetchild(int position) {
        int childCount = lay_ticket_view.getChildCount();
        if (childCount > 0) {
            View view = lay_ticket_view.getChildAt(position);
            LinearLayout lay_ticket_field_data = (LinearLayout) view.findViewById(R.id.lay_ticket_field_data);
            TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
            TextView text_ticket_type = (TextView) view.findViewById(R.id.text_ticket_type);
            int count = Integer.parseInt(tv_count.getText().toString());
            if (typeList.get(position).admit > 1) {
                int personCount = count * typeList.get(position).admit;
                TotalTicketPerson = TotalTicketPerson - personCount;
                TotalTicketCount = TotalTicketCount - count;
                if (TotalTicketCount > 0) {
                    tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                } else {
                    tv_tickets.setText("Select No. of Tickets");
                }

            } else {
                TotalTicketPerson = TotalTicketPerson - count;
                TotalTicketCount = TotalTicketCount - count;
                if (TotalTicketCount > 0) {
                    tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Persons)");
                } else {
                    //     tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
                    tv_tickets.setText("Select No. of Tickets");
                }
                // tv_tickets.setText("" + TotalTicketCount + " (" + (TotalTicketPerson) + " Person)");
            }

            Log.d("26Oct", "234 TotalTicketCount " + TotalTicketCount + " " + "Persons" + TotalTicketPerson);

            Double _Price = typeList.get(position).price;
            if (!typeList.get(position).isFixPrice) {
                _Price = _Price * count;
            }
            TotalTicketAmount = TotalTicketAmount - _Price;
//            int temp_totalamount = (int) TotalTicketAmount;
            total_amount.setText("" + String.format("%.2f", TotalTicketAmount) + " " + CountryCode);
            typeList.get(position).quantity = 0;
            tv_count.setText("0");


            if (lay_ticket_field_data.getChildCount() > 0) {
                lay_ticket_field_data.removeAllViews();
            }
        }
    }


    private void setItemsData(EventTicketQT5Type type, int ticketcount, boolean hasAnychild) {
        try {
            Iterator iterator = jsonObjectTicketFields.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (type.ticketName.equals(key)) {
                    if (jsonObjectTicketFields.getJSONArray(key).length() > 0) {
                        int ticketlength = jsonObjectTicketFields.getJSONArray(key).length();
//                        while (jsonObjectTicketFields.getJSONArray(key).length() > 0) {
//                            jsonObjectTicketFields.getJSONArray(key).remove(0);
//                        }

                        if (ticketcount >= ticketlength) {
                            for (int k = 0; k < ticketcount - ticketlength; k++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObjectTicketFields.getJSONArray(key).put(jsonObject);
                            }
                        } else {
                            jsonObjectTicketFields.getJSONArray(key).remove(ticketlength - 1);
                        }


                    } else {
                        for (int k = 0; k < ticketcount; k++) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObjectTicketFields.getJSONArray(key).put(jsonObject);
                        }
                    }

                }
            }
            mChildNames.clear();
            if (hasAnychild) {
                if (typeList.size() > 0) {
                    if (!type.subChilds.equals("")) {
                        String[] elements = type.subChilds.split(",");
                        for (int i = 0; i < typeList.size(); i++) {
                            for (int j = 0; j < elements.length; j++) {
                                if (elements[j].equals(String.valueOf(typeList.get(i).id))) {
                                    mChildNames.add(typeList.get(i).ticketName);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < mChildNames.size(); i++) {
                    if (jsonObjectTicketFields.getJSONArray(mChildNames.get(i)).length() > 0) {
                        // jsonObjectTicketFields.getJSONArray(mChildNames.get(i)).remove();
                        jsonObjectTicketFields.remove(mChildNames.get(i));
                    }
                }
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        Log.d("Jsondata :", jsonObjectTicketFields.toString());
    }

    private void addChildView(int ticket_count, int postion, EventTicketQT5Type type, Boolean isANyCHild) {

        Log.d("3Nov:", "presenter");
        presenter.GetEventCustomFieldsList(ID, Ticket_ID, ticket_count, postion, true, type, isANyCHild);

    }

    @Override
    public void setEventCustomFieldsList(EventCustomFieldQT5Model eventCustomFieldQT5Model, int ticketcount, int positon, boolean isFirstTime, EventTicketQT5Type type, boolean isAnyCHild) {

//        Log.d("2Nov", "setEventCustomFieldsList");

        if (eventCustomFieldQT5Model.statusCode.equals("200")) {
            if (eventCustomFieldQT5Model.data.eventCustomFieldList != null && !eventCustomFieldQT5Model.data.eventCustomFieldList.isEmpty()) {
                if (type != null) {
                    strSelectedTicketName = type.ticketName;
                }
                if (isFirstTime) {
                    // Buyer
                    if (eventCustomFieldQT5Model.data.eventCustomFieldList.get(0).formFor == 1) {
//                        if (viewTicketField != null) {
//                            viewTicketField.removeAllViews();
//                        }
                        if (ticketcount > 0) {
                            setItemsData(type, 1, isAnyCHild);
                            addChildViewTemp(0, eventCustomFieldQT5Model.data.eventCustomFieldList, type, positon, isAnyCHild, mChildNames);
//                            for (int j = 0; j < 1; j++) {
//                                addChildViewTemp(j, eventCustomFieldQT5Model.data.eventCustomFieldList, type,positon);
//                            }
                        } else {
                            setItemsData(type, ticketcount, isAnyCHild);
                            addChildViewTemp(0, eventCustomFieldQT5Model.data.eventCustomFieldList, type, positon, isAnyCHild, mChildNames);
//                            for (int j = 0; j < ticketcount; j++) {
//                                addChildViewTemp(j, eventCustomFieldQT5Model.data.eventCustomFieldList, type,positon);
//                            }
                        }

                    }
                    // Individuals
                    if (eventCustomFieldQT5Model.data.eventCustomFieldList.get(0).formFor == 2) {
//                        if (viewTicketField != null) {
//                            viewTicketField.removeAllViews();
//                        }
                        setItemsData(type, ticketcount, isAnyCHild);
                        addChildViewTemp(0, eventCustomFieldQT5Model.data.eventCustomFieldList, type, positon, isAnyCHild, mChildNames);
//                        for (int j = 0; j < ticketcount; j++) {
//                            addChildViewTemp(j, eventCustomFieldQT5Model.data.eventCustomFieldList, type,positon);
//                        }
                    }

                } else {
                    showBottomSheetDialog(eventCustomFieldQT5Model.data.eventCustomFieldList, R.style.DialogAnimation, positon);
                }
                // showBottomSheetDialog(eventCustomFieldQT5Model.data.eventCustomFieldList, R.style.DialogAnimation);
            }
        } else {
            QTUtils.showDialogbox(this, eventCustomFieldQT5Model.message);
        }
    }


    public void addChildViewTemp(int pos, List<EventCustomFieldListQT5> list, EventTicketQT5Type type, int position, boolean isAnyChild, ArrayList<String> mChildNames) {
        //  viewTicketField.removeAllViews();
        LinearLayout mCHildView = mViewsChild.get(type.ticketName);
        mCHildView.removeAllViews();
        try {
            Iterator iterator = jsonObjectTicketFields.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                int lenght = jsonObjectTicketFields.getJSONArray(key).length();
                if (lenght > 0) {
                    for (int k = 0; k < lenght; k++) {
                        if (type.ticketName.equals(key)) {
                            inflatchildview(lenght, k, position, type, mCHildView);
                        }
                    }
                }

            }

        } catch (Exception e) {

        }

        if (isAnyChild) {
            for (int i = 0; i < mChildNames.size(); i++) {
                LinearLayout mCHildViewss = mViewsChild.get(mChildNames.get(i));
                mCHildViewss.removeAllViews();
            }
        }

    }


    private void inflatchildview(int lenght, int position, int pos, EventTicketQT5Type type, LinearLayout childview) {
        View viewss = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_ticketchildselected, null, false);
        RelativeLayout rl_child = viewss.findViewById(R.id.rl_child);
        TextView tv_guestname = viewss.findViewById(R.id.tv_guestname);
        ImageView iv_addicon = viewss.findViewById(R.id.iv_addicon);
        ImageView iv_deleteicon = viewss.findViewById(R.id.iv_deleteicon);
        rl_child.setTag(position);
        JSONObject temp = null;
        try {
            temp = new JSONObject(jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).get(position).toString());
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        if (temp.length() > 0) {
            Iterator keys = temp.keys();
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                String firstvalue = null;
                try {
                    firstvalue = temp.getString(currentDynamicKey);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                tv_guestname.setText(firstvalue);
                break;
            }
            String fullname = null;
            try {
                fullname = temp.getString("First Name");
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            tv_guestname.setText(fullname);
            iv_addicon.setVisibility(View.GONE);
            iv_deleteicon.setVisibility(View.VISIBLE);
        }

        rl_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() instanceof Integer) {
                    strSelectedTicketName = type.ticketName;
                    current_ticket_postion = (Integer) v.getTag();
                    presenter.CheckCustomField(ID, type.id);
                    Ticket_ID = type.id;
                }
            }
        });

        iv_deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).remove((Integer) rl_child.getTag());
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).put(jsonObject);
                    //        Log.d("jsonObjectvalues :",jsonObjectTicketFields.toString());

                    iv_addicon.setVisibility(View.VISIBLE);
                    tv_guestname.setText(getString(R.string.adduserinfo));
                    iv_deleteicon.setVisibility(View.GONE);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }

            }
        });
        childview.addView(viewss, -1);
        childview.setVisibility(View.VISIBLE);
    }

    private void inflatchildviewTemp(int lenght, int position, int pos, String type, LinearLayout childview) throws JSONException {
        View viewss = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_ticketchildselected, null, false);
        RelativeLayout rl_child = viewss.findViewById(R.id.rl_child);
        TextView tv_guestname = viewss.findViewById(R.id.tv_guestname);
        TextView tv_fullanme = viewss.findViewById(R.id.tv_fullanme);
        LinearLayout ll_withdata = viewss.findViewById(R.id.ll_withdata);
        LinearLayout ll_withoutdata = viewss.findViewById(R.id.ll_withoutdata);
        ImageView iv_delete = viewss.findViewById(R.id.iv_deleteicon);
        ImageView iv_deleteagain = viewss.findViewById(R.id.iv_deleteagain);
        rl_child.setTag(position);
        JSONObject temp = null;
        try {
            temp = new JSONObject(jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).get(position).toString());
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        if (temp.length() > 0) {
            ll_withdata.setVisibility(View.VISIBLE);
            ll_withoutdata.setVisibility(View.GONE);
            iv_delete.setVisibility(View.VISIBLE);
            Iterator keys = temp.keys();
            String firstvalue = null;
            boolean isAvailable = false;
            while (keys.hasNext()) {
                String currentDynamicKey = (String) keys.next();
                try {
                    firstvalue = temp.getString(currentDynamicKey);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                if (!firstvalue.isEmpty()) {
                    isAvailable = true;
                    tv_fullanme.setText(firstvalue);
                    break;
                }
            }

            if (firstvalue.isEmpty()) {
                ll_withdata.setVisibility(View.GONE);
                ll_withoutdata.setVisibility(View.VISIBLE);
                if (isAvailable) {


                }
            }
          /*  String fullname = temp.getString("Prefix")
                    + temp.getString("First Name")
                    + temp.getString("Last Name");

            tv_fullanme.setText(fullname);*/
        } else {
            ll_withdata.setVisibility(View.GONE);
            ll_withoutdata.setVisibility(View.VISIBLE);
        }

        rl_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() instanceof Integer) {
                    strSelectedTicketName = type;
                    current_ticket_postion = (Integer) v.getTag();
                    presenter.CheckCustomField(ID, (Integer) childview.getTag());
                    Ticket_ID = (Integer) childview.getTag();
                }
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).remove((Integer) rl_child.getTag());
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).put(jsonObject);
                    //        Log.d("jsonObjectvalues :",jsonObjectTicketFields.toString());
                    ll_withdata.setVisibility(View.GONE);
                    ll_withoutdata.setVisibility(View.VISIBLE);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        });

        iv_deleteagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).remove((Integer) rl_child.getTag());
                    jsonObjectTicketFields.getJSONArray("" + strSelectedTicketName).put(jsonObject);
                    //        Log.d("jsonObjectvalues :",jsonObjectTicketFields.toString());
                    ll_withdata.setVisibility(View.GONE);
                    ll_withoutdata.setVisibility(View.VISIBLE);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        });

        childview.addView(viewss, -1);
        childview.setVisibility(View.VISIBLE);
    }

    public void resetTicket(ArrayList<SelectedParentCount> mSelectedTicketList) {
        int childCount = lay_ticket_view.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View view = lay_ticket_view.getChildAt(i);
                LinearLayout lay_ticket_field_data = (LinearLayout) view.findViewById(R.id.lay_ticket_field_data);
                TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
                TextView text_ticket_type = (TextView) view.findViewById(R.id.text_ticket_type);

                if (mSelectedTicketList.size() > 0) {
                    for (int j = 0; j < mSelectedTicketList.size(); j++) {
                        if (text_ticket_type.getText().toString().equals(mSelectedTicketList.get(j).getTicketname())) {

                        } else {

                        }
                    }
                } else {
                    typeList.get(i).quantity = 0;
                    tv_count.setText("0");
                    if (lay_ticket_field_data.getChildCount() > 0) {
                        lay_ticket_field_data.removeAllViews();
                    }
                    JSONArray jsonArray = new JSONArray();
                    try {
                        jsonObjectTicketFields.put("" + text_ticket_type.getText().toString(), jsonArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(EventBookingDetailQT5Activity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
        if (throwable instanceof SocketTimeoutException) {
            //  showToast("Socket Time out. Please try again.");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof UnknownHostException) {
            // showToast("No Internet");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof ConnectException) {
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else {
            showToast(getResources().getString(R.string.noresponse));
        }

    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, EventBookingDetailQT5Activity.this, message);
    }


    @Override
    public void onClickDate(View v, String s, boolean isDayapss) {
        if (isDayapss) {
            isDayPassActive = true;
            card_isdaypass.setCardBackgroundColor(getResources().getColor(R.color.themecolor));
            card_isdaypass.setMaxCardElevation(24);
        } else {
            isDayPassActive = false;
            card_isdaypass.setCardBackgroundColor(getResources().getColor(R.color.contacttextcolor));
            card_isdaypass.setMaxCardElevation(24);
        }
        TotalTicketCount = 0;
        TotalTicketPerson = 0;
        eventTimeQT5Data = new ArrayList<>();
        selectedEventDate = s;
        isTimeSlot = false;
        lay_spinner.setVisibility(View.GONE);
        presenter.getEventShowTimeByEventDate(ID, selectedEventDate);
    }

    public boolean isValidMobile(String str) {
        boolean isValid = false;
        if (str.length() == 8) isValid = Patterns.PHONE.matcher(str).matches();
        return isValid;
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern
            .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                    + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public boolean isValidEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 001) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
            return;
        }
        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                //    tv_filechoosen.setText("");
                onCaptureImageResult(data);
            }
            return;
        }
        if (requestCode == 11) {
            if (resultCode == RESULT_OK && data != null) {
//                tv_filechoosen.setText("");
                onSelectFromGalleryResult(data);
            }
            return;
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), "qticket" + System.currentTimeMillis());
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = destination.getAbsolutePath();
        uploadProfileImage(path);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedFileUri = data.getData();
        String path = getRealPathFromURI(selectedFileUri);
        uploadProfileImage(path);
    }


    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    void uploadProfileImage(final String filePath) {
        File file = new File(filePath);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("application/text; charset=utf-8"), "file");
        presenter.uploadAttachment(body);
    }

}