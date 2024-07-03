package com.production.qtickets.events;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.production.qtickets.R;
import com.production.qtickets.model.EventTicketPriceRangeModel;
import com.production.qtickets.model.PriceRange;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventFilterActivity extends AppCompatActivity implements View.OnClickListener, FilterPriceRangeContracter.View {

    private ProgressDialog dialog;
    int preMin = -1;
    int preMax = -1;
    private TextView minRs, maxRs, tvStartDate, tvEndDate, tvApplyFilter;
    ImageView ivStartDate, ivEndDate, backBtn;
    private RangeSeekBar<Integer> seekBar;
    private int slctminPrice, slctmaxPrice;
    private RadioGroup dateType;
    private String startDate, endDate;
    private String todayDateDefault, endDateDefault;
    private int startPrice = 1, endPrice = 3000;
    private String currencyType;
    private String toDayDate, toMorrowDay, weekendDayStrDay, weekendDaySunday;
    List<String> eventTicketPrices = new ArrayList<>();
    private CheckBox tickCheckBox;
    private TextView agreeTV;
    private String check;
    String dayNameToPass = "";

    String startingPrice = "";
    String endingPrice = "";

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;

    SessionManager sessionManager;
    FilterPriceRangePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(EventFilterActivity.this);
        setContentView(R.layout.event_filter_activity);

        presenter = new FilterPriceRangePresenter();
        presenter.attachView(EventFilterActivity.this);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventFilterActivity.this.finish();
            }
        });
//        try {
//            getPriceRange();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        init();
        sessionManager = new SessionManager(this);
        currencyType = sessionManager.getCountryCurrency();


        Intent ii = getIntent();
        //new EventpricerangeCall().execute();
        startDate = ii.getStringExtra("startDate");
        endDate = ii.getStringExtra("endDate");
        slctminPrice = Integer.parseInt(ii.getStringExtra("startPrice"));
        slctmaxPrice = Integer.parseInt(ii.getStringExtra("endPrice"));

        agreeTV = (TextView) findViewById(R.id.agreeTV);
        agreeTV.setText("");

        dateType = (RadioGroup) findViewById(R.id.rgBookedDays);
        minRs = (TextView) findViewById(R.id.tvMinRs);
        maxRs = (TextView) findViewById(R.id.tvMaxRs);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        ivStartDate = (ImageView) findViewById(R.id.ivStartDate);
        ivEndDate = (ImageView) findViewById(R.id.ivEndDate);
        tvApplyFilter = (TextView) findViewById(R.id.tvApplyFilter);
        backBtn = (ImageView) findViewById(R.id.backBtn);


        tickCheckBox = (CheckBox) findViewById(R.id.tickCheckBox);
        tickCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked == true) {
                    check = "Check";
                } else {
                    check = "not Check";
                }
            }
        });

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        currencyType = mPrefs.getString(AppConstants.COUNTRY_TYPE_CURRENCY, null);

        Gson gson = new Gson();
        String eventsDetailsPrices = mPrefs.getString(AppConstants.SHARED_EVENTS_TICKET_PRICES, "");
        if (!eventsDetailsPrices.equalsIgnoreCase("")) {
            eventTicketPrices.clear();

        }


        dateType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                Calendar c = null;
                SimpleDateFormat sdf = null;
                switch (checkedId) {

                    case R.id.rbAll:
                        tvStartDate.setText("Select start date");
                        tvEndDate.setText("Select end date");

                        break;

                    case R.id.rbToday:
                        toDayDate = DateTime.getToday();
                        tvStartDate.setText(toDayDate);
                        tvEndDate.setText(toDayDate);
                        dayNameToPass = "Today";
                        break;

                    case R.id.rbTomorow:
                        c = Calendar.getInstance();
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date tomorrowDate = c.getTime();
                        c.setTime(tomorrowDate);
                        c.add(Calendar.DATE, 1);


                        try {
                            tomorrowDate = sdf.parse(sdf.format(c.getTime()));
                            toMorrowDay = DateTime.getDate(tomorrowDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        tvStartDate.setText(toMorrowDay);
                        tvEndDate.setText(toMorrowDay);
                        dayNameToPass = "Tomorrow";


                        break;
                    case R.id.rbWeekend:
                        c = Calendar.getInstance();
                        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);

                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date weekendDate = null;

                        try {
                            weekendDate = sdf.parse(sdf.format(c.getTime()));

                            weekendDayStrDay = DateTime.getDate(weekendDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("", weekendDayStrDay);

                        c.setTime(weekendDate);
                        c.add(Calendar.DATE, 1);
                        //weekendDate = c.getTime();

                        try {
                            weekendDate = sdf.parse(sdf.format(c.getTime()));
                            weekendDaySunday = DateTime.getDate(weekendDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        tvStartDate.setText(weekendDayStrDay);
                        tvEndDate.setText(weekendDaySunday);
                        dayNameToPass = "Weekend";

                        break;
                }
            }
        });


        if (!startDate.equalsIgnoreCase("")) {
            tvStartDate.setText(startDate);
        }

        if (!endDate.equalsIgnoreCase("")) {
            tvEndDate.setText(endDate);
        }

        ivStartDate.setOnClickListener(this);
        ivEndDate.setOnClickListener(this);
        tvApplyFilter.setOnClickListener(this);
        backBtn.setOnClickListener(this);


        Date today = new Date(System.currentTimeMillis());
        todayDateDefault = new SimpleDateFormat("yyyy-MM-dd").format(today);


        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1); // to get previous year add -1
        Date endYear = cal.getTime();
        endDateDefault = new SimpleDateFormat("yyyy-MM-dd").format(endYear);


    }

//    private void getPriceRange() {
//        String priceRangeURL = "https://api.q-tickets.com/V5.0/getminmaxpriceevent";
//        Log.v("priceRangeURL", " == " + priceRangeURL);
//        new priceRangeUrl().execute(priceRangeURL);
//    }
//
//    private class priceRangeUrl extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog dialog;
//
//        @Override
//        protected String doInBackground(String... urls) {
//
//            return GET(urls[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(EventFilterActivity.this);
//
//            dialog.setMessage("Loading Please Wait...");
//            dialog.setCancelable(false);
//            dialog.show();
//
//        }
//
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//
//
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//            Log.v("priceRange", "==" + result);
//            try {
//
//
//                JSONObject jsonObject = new JSONObject(result);
//                String status = jsonObject.getString("status");
//                if (status.equals("1")) {
//                    JSONArray jsonArray = jsonObject.getJSONArray("items");
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        String minPrice = jsonArray.getJSONObject(i).getString("minPrice");
////                        startingPrice = minPrice;
////                        String maxPrice = jsonArray.getJSONObject(i).getString("maxPrice");
////                        endingPrice = maxPrice;
////
////                        onSeekExecute(Integer.valueOf(startingPrice), Integer.valueOf(endingPrice));
////
////                        if (slctminPrice == 0) {
////                            seekBar.setSelectedMinValue(Integer.valueOf(startingPrice));
////                            minRs.setText("Min " + currencyType + " " + startingPrice);
////                        } else {
////                            seekBar.setSelectedMinValue(slctminPrice);
////                            minRs.setText("Min " + currencyType + " " + slctminPrice);
////                        }
////
////                        if (slctmaxPrice == 0) {
////                            seekBar.setSelectedMaxValue(Integer.valueOf(endingPrice));
////                            maxRs.setText("Max " + currencyType + " " + endingPrice);
////                        } else {
////                            seekBar.setSelectedMaxValue(slctmaxPrice);
////                            maxRs.setText("Max " + currencyType + " " + slctmaxPrice);
////                        }
////                    }
//
//                } else {
//
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    public static String GET(String url) {
//        InputStream inputStream = null;
//        String result = "";
//        try {
//
//            // create HttpClient
//            HttpClient httpclient = new DefaultHttpClient();
//
//            // make GET request to the given URL
//            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
//
//            // receive response as inputStream
//            inputStream = httpResponse.getEntity().getContent();
//
//            // convert inputstream to string
//            if (inputStream != null)
//                result = convertInputStreamToString(inputStream);
//            else
//                result = "Did not work!";
//
//        } catch (Exception e) {
//            Log.d("InputStream", e.getLocalizedMessage());
//        }
//
//        return result;
//    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
        String line = "";
        String result = "";

        StringBuilder stringBuilder = new StringBuilder();


        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line + "\n");
        // result += line;

        inputStream.close();
        result = stringBuilder.toString();
        return result;

    }


    private void onSeekExecute(int minValue, int maxValue) {
        seekBar = new RangeSeekBar<>(minValue, maxValue, EventFilterActivity.this);

        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                // handle changed range values

                int diff = maxValue - minValue;
                minRs.setVisibility(View.VISIBLE);
                maxRs.setVisibility(View.VISIBLE);
                minRs.setText("Min " + currencyType + " ." + minValue);
                maxRs.setText("Max " + currencyType + " ." + maxValue);
                startPrice = minValue;
                endPrice = maxValue;


            }
        });
        // add RangeSeekBar to pre-defined layout
        ViewGroup layout = (ViewGroup) EventFilterActivity.this.findViewById(R.id.seeklayout);
        layout.addView(seekBar);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.ivStartDate:
                openCalander("1", startDate);
                break;

            case R.id.ivEndDate:
                openCalander("2", endDate);
                break;

            case R.id.tvApplyFilter:
                Intent output = new Intent();
                output.putExtra("startPrice", startPrice + "");
                output.putExtra("endPrice", endPrice + "");
//                output.putExtra("startDate", endPrice + "");
//                output.putExtra("endDate", endPrice + "");
                output.putExtra("location", check);
                if (!TextUtils.isEmpty(tvStartDate.getText().toString())) {
                    output.putExtra("startDate", tvStartDate.getText().toString());
                    output.putExtra("dayName", dayNameToPass);
                } else {
                    output.putExtra("startDate", endDateDefault);
                    tvStartDate.setText(endDateDefault);
                }
                if (!TextUtils.isEmpty(tvEndDate.getText().toString())) {
                    output.putExtra("endDate", tvEndDate.getText().toString());
                } else {
                    output.putExtra("endDate", todayDateDefault);
                    tvEndDate.setText(todayDateDefault);
                }
                setResult(RESULT_OK, output);
                finish();
                break;

            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }

    private void openCalander(final String fromto, String date) {
        final Calendar mcurrentDate = Calendar.getInstance();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        if (!date.equalsIgnoreCase("")) {
            try {
                Date date1 = sdf.parse(date);
                mcurrentDate.setTime(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EventFilterActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mcurrentDate.set(Calendar.YEAR, year);
                mcurrentDate.set(Calendar.MONTH, monthOfYear);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd";
                String showDateFormt = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                String selectDate = sdf.format(mcurrentDate.getTime());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(showDateFormt, Locale.US);
                String showDate = simpleDateFormat.format(mcurrentDate.getTime());

                if (fromto.equalsIgnoreCase("1")) {
                    tvStartDate.setText(selectDate);
                    startDate = selectDate;

                } else {
                    try {
                        if (TextUtils.isEmpty(tvStartDate.getText().toString())) {
                            Toast.makeText(EventFilterActivity.this, "Please select start date first", Toast.LENGTH_LONG).show();
                        }
                        if (sdf.parse(selectDate).after(sdf.parse(startDate))) {
                            tvEndDate.setText(selectDate);
                            endDate = selectDate;
                        } else {
                            Toast.makeText(EventFilterActivity.this, "End date should be greater than Start date", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, mYear, mMonth, mDay);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setPriceRangeData(EventTicketPriceRangeModel eventTicketPriceRangeModel) {
        List<PriceRange> priceRanges = eventTicketPriceRangeModel.priceRangeList;
               Log.v("Values", "== " + priceRanges.toString());
//        try {
//            JSONArray jsonArray = new JSONArray(priceRanges);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                slctminPrice = Integer.parseInt(jsonArray.getJSONObject(i).getString("minPrice"));
//                slctmaxPrice = Integer.parseInt(jsonArray.getJSONObject(i).getString("minPrice"));
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        slctminPrice = Integer.parseInt(eventTicketPriceRangeModel.priceRangeList.get(0).minPrice);
        slctmaxPrice = Integer.parseInt(eventTicketPriceRangeModel.priceRangeList.get(0).maxPrice);
        String minPrice = eventTicketPriceRangeModel.priceRangeList.get(0).minPrice;
        startingPrice = minPrice;
        String maxPrice = eventTicketPriceRangeModel.priceRangeList.get(0).maxPrice;
        endingPrice = maxPrice;
        onSeekExecute(Integer.valueOf(startingPrice), Integer.valueOf(endingPrice));
        if (slctminPrice == 0) {
            seekBar.setSelectedMinValue(Integer.valueOf(startingPrice));
            minRs.setText("Min " + currencyType + " " + startingPrice);
        } else {
            seekBar.setSelectedMinValue(slctminPrice);
            minRs.setText("Min " + currencyType + " " + slctminPrice);
        }
        if (slctmaxPrice == 0) {
            seekBar.setSelectedMaxValue(Integer.valueOf(endingPrice));
            maxRs.setText("Max " + currencyType + " " + endingPrice);
        } else {
            seekBar.setSelectedMaxValue(slctmaxPrice);
            maxRs.setText("Max " + currencyType + " " + slctmaxPrice);
        }

    }

    @Override
    public void init() {
        presenter.getPriceRangeData(EventFilterActivity.this);

    }

    @Override
    public void dismissPb() {
        QTUtils.dismissProgressDialog();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(EventFilterActivity.this, true);
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
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, EventFilterActivity.this, message);
    }
}
