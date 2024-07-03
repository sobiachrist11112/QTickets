//package com.production.qtickets.ticketbookingdetaile;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alihafizji.library.CreditCardEditText;
//import com.production.qtickets.R;
//
//import com.production.qtickets.dashboard.DashBoardActivity;
//import com.production.qtickets.utils.QTUtils;
//import com.production.qtickets.utils.TextviewBold;
//import com.production.qtickets.utils.TextviewRegular;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//public class DohaBankOfferCardActivity extends AppCompatActivity {
//
//    private EditText et_one;
//    private EditText et_two;
//    private EditText et_three;
//    private EditText et_four;
//
//    ArrayList<String> monthsList = new ArrayList<String>();
//    ArrayList<String> yearList = new ArrayList<String>();
//
//    private String nationality, order_info, mAmount, userSessionID, orderID;
//    String paymentMainUrl, str_differ;
//
//    TextviewBold btn_submit;
//
//    String cardNumber = "";
//
//    EditText et_validthru_month;
//    EditText et_validthru_year;
//    EditText et_cvvcode;
//    EditText et_nameoncard;
//    String one, two, three, four;
//
//    TextviewRegular tv_total_tickets;
//    TextviewRegular tv_tickets_after_discount;
//    TextviewRegular tv_ticket_price;
//    TextviewRegular tv_service_charges;
//
//    TextviewBold tv_totalPrice;
//
//    TextView tv_payTotal;
//
//    LinearLayout payNow_layout;
//    LinearLayout discount_details_layout;
//
//    int totTickets;
//    int disTickets;
//    int ticketsRemains;
//    int tktPrice;
//    int totalAmount;
//    int serCharge;
//    String validthru ="";
//
//    String mMonth ="";
//    String mMonthType ="";
//
//    String mYear = "";
//    String mYearType = "";
//
//    Spinner spinner_select_month;
//    Spinner spinner_select_year;
//
//    ImageView iv_back_arrow;
//
//    CreditCardEditText textview_credit_card;
//
//    EditText et_validthru;
//    String currentDate ="";
//    int currentYear;
//    int currentMonth;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        QTUtils.setStatusBarGradiant(DohaBankOfferCardActivity.this);
//        setContentView(R.layout.dohabanckoffer_card_activity);
//
//        QTUtils.dohaBankOffer = "no";
//
//        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
//        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent dashIntent = new Intent(DohaBankOfferCardActivity.this, DashBoardActivity.class);
//                startActivity(dashIntent);
//                DohaBankOfferCardActivity.this.finish();
//            }
//        });
//
//
//        textview_credit_card = (CreditCardEditText)findViewById(R.id.textview_credit_card);
//        et_validthru = (EditText)findViewById(R.id.et_validthru);
//        et_validthru.addTextChangedListener(mDateEntryWatcher);
//
//
//        currentYear =Calendar.getInstance().get(Calendar.YEAR);
//        currentMonth =Calendar.getInstance().get(Calendar.MONTH);
//        currentMonth = currentMonth +1;
//        Log.v("yearMOnth","= "+currentYear);
//        Log.v("yearMOnth","= "+currentMonth);
//
//        Bundle b = getIntent().getExtras();
//        if (b != null) {
//
//            order_info = b.getString("OrderInfo");
//            mAmount = b.getString("Amount");
//            str_differ = b.getString("strdiff");
//            userSessionID = b.getString("userSessionID");
//        }
//
//        order_info = order_info.substring(1);
//        orderID = order_info;
//        Log.v("OrderDetails", "== " + order_info);
//
//        tv_payTotal = (TextView) findViewById(R.id.tv_payTotal);
//
//        payNow_layout = (LinearLayout) findViewById(R.id.payNow_layout);
//        payNow_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                paymentMainUrl = "https://q-tickets.com/Qpayment-registration.aspx?Currency=QAR&Amount= " + totalAmount
//                        + "&OrderName=online&OrderID=" + order_info + "&nationality=Qatar&paymenttype=6";
//                QTUtils.dohaBankOffer = "no";
//                Intent ii = new Intent(DohaBankOfferCardActivity.this, DohaBankWebViewActivity.class);
//                ii.putExtra("url", paymentMainUrl);
//                startActivity(ii);
//
//            }
//        });
//
//        monthsAndYearsValidation();
//
//        discount_details_layout = (LinearLayout) findViewById(R.id.discount_details_layout);
//
//        tv_total_tickets = (TextviewRegular) findViewById(R.id.tv_total_tickets);
//        tv_tickets_after_discount = (TextviewRegular) findViewById(R.id.tv_tickets_after_discount);
//        tv_ticket_price = (TextviewRegular) findViewById(R.id.tv_ticket_price);
//        tv_service_charges = (TextviewRegular) findViewById(R.id.tv_service_charges);
//
//        tv_totalPrice = (TextviewBold) findViewById(R.id.tv_totalPrice);
//
//        et_validthru_month = (EditText) findViewById(R.id.et_validthru_month);
//        et_validthru_year = (EditText) findViewById(R.id.et_validthru_year);
//        et_cvvcode = (EditText) findViewById(R.id.et_cvvcode);
//        et_nameoncard = (EditText) findViewById(R.id.et_nameoncard);
//
//        et_one = (EditText) findViewById(R.id.et_one);
//        et_two = (EditText) findViewById(R.id.et_two);
//        et_three = (EditText) findViewById(R.id.et_three);
//        et_four = (EditText) findViewById(R.id.et_four);
//
//        validthru = et_validthru_month+""+et_validthru_year;
//
//
//        btn_submit = (TextviewBold) findViewById(R.id.btn_submit);
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                cardNumber = textview_credit_card.getCreditCardNumber();
//
//                if (cardNumber.length() == 16) {
//                    String validCardNumber = cardNumber.substring(0, Math.min(cardNumber.length(), 6));
//                    String cardValidity = et_validthru.getText().toString();
//                    String dates[] = cardValidity.split("/");
//                    String dateMonth = dates[0];
//                    String dateYear = dates[1];
//
//                    if (validCardNumber.equals("471369")) {
//                        if (validthru.length() != 4) {
//                            if (et_cvvcode.getText().toString().length() == 3) {
//                                if (et_nameoncard.getText().toString().length() != 0) {
//
//                                    String cardCVV = et_cvvcode.getText().toString();
//
//                                    if (Integer.valueOf(dateMonth) >= currentMonth){
//                                        if (Integer.valueOf(dateYear) >= currentYear){
//                                            applyDohaOffer(cardNumber, cardCVV);
//                                        }else {
//                                            Toast.makeText(DohaBankOfferCardActivity.this,"Invalid Card",Toast.LENGTH_LONG).show();
//                                        }
//                                    }else if (Integer.valueOf(dateMonth) <currentMonth){
//                                        if (Integer.valueOf(dateYear) > currentYear){
//                                            applyDohaOffer(cardNumber, cardCVV);
//                                        }else {
//                                            Toast.makeText(DohaBankOfferCardActivity.this,"Invalid Card",Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//                                } else {
//                                    Toast.makeText(DohaBankOfferCardActivity.this, "Please enter name on card", Toast.LENGTH_LONG).show();
//                                }
//                            } else {
//                                Toast.makeText(DohaBankOfferCardActivity.this, "Please enter valid cvv", Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//                            Toast.makeText(DohaBankOfferCardActivity.this, "Please enter card validity", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(DohaBankOfferCardActivity.this, "Please enter valid card number", Toast.LENGTH_LONG).show();
//                    }
//                } else {
//                    Toast.makeText(DohaBankOfferCardActivity.this, "Please enter valid card number", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//        et_one.requestFocus();
//
//        doTextShift();
//
//
//    }
//
//    private TextWatcher mDateEntryWatcher = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            String working = s.toString();
//            boolean isValid = true;
//            if (working.length()==2 && before ==0) {
//                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>12) {
//                    isValid = false;
//                } else {
//                    working+="/";
//                    et_validthru.setText(working);
//                    et_validthru.setSelection(working.length());
//                }
//            }
//            else if (working.length()==7 && before ==0) {
//                String enteredYear = working.substring(3);
//                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//                if (Integer.parseInt(enteredYear) < currentYear) {
//                    isValid = false;
//                }
//            } else if (working.length()!=7) {
//                isValid = false;
//            }
//
//            if (!isValid) {
//                et_validthru.setError("Enter a valid date: MM/YYYY");
//            } else {
//                et_validthru.setError(null);
//            }
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {}
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//    };
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//        Intent dashIntent = new Intent(DohaBankOfferCardActivity.this, DashBoardActivity.class);
//        startActivity(dashIntent);
//        QTUtils.dohaBankOffer = "no";
//        DohaBankOfferCardActivity.this.finish();
//
//    }
//
//
//    private void monthsAndYearsValidation() {
//
//        spinner_select_month = (Spinner) findViewById(R.id.spinner_select_month);
//        monthsList.add("00");
//        monthsList.add("01");
//        monthsList.add("02");
//        monthsList.add("03");
//        monthsList.add("04");
//        monthsList.add("05");
//        monthsList.add("06");
//        monthsList.add("07");
//        monthsList.add("08");
//        monthsList.add("09");
//        monthsList.add("10");
//        monthsList.add("11");
//        monthsList.add("12");
//        spinner_select_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mMonth = parent.getItemAtPosition(position).toString();
//                if (!mMonth.equalsIgnoreCase("00")) {
//                    mMonthType = parent.getItemAtPosition(position).toString();
//                    Log.v("mMonthType", "== " + mMonthType);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        ArrayAdapter<String> specializationAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, monthsList);
//        specializationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_select_month.setAdapter(specializationAdapter);
//
//        spinner_select_year = (Spinner) findViewById(R.id.spinner_select_year);
//        yearList.add("00");
//        yearList.add("18");
//        yearList.add("19");
//        yearList.add("20");
//        yearList.add("21");
//        yearList.add("22");
//        yearList.add("23");
//        yearList.add("24");
//        yearList.add("25");
//        yearList.add("26");
//        yearList.add("27");
//        yearList.add("28");
//        spinner_select_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mYear = parent.getItemAtPosition(position).toString();
//                if (!mYear.equalsIgnoreCase("00")) {
//                    mYearType = parent.getItemAtPosition(position).toString();
//                    Log.v("mMonthType", "== " + mYearType);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        ArrayAdapter<String> specializationyearAdapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, yearList);
//        specializationyearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_select_year.setAdapter(specializationyearAdapter);
//    }
//
//    private void applyDohaOffer(String cardNumber, String cardCVV) {
//
//        String applyDohaOfferURL = "http://api.q-tickets.com/V5.0/checkvaliditycap?booking_id=" + orderID + "&Card6=" + cardNumber + "&Card4=" + cardCVV;
//        Log.v("applyDohaOfferURL", " == " + applyDohaOfferURL);
//        new applyDohaOfferAsuncTask().execute(applyDohaOfferURL);
//
//    }
//
//    private class applyDohaOfferAsuncTask extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog dialog;
//
//        @Override
//        protected String doInBackground(String... urls) {
//
//            return QTUtils.GET(urls[0]);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(DohaBankOfferCardActivity.this);
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
//            Log.v("dohaOfferApply", "==" + result);
//            try {
//
//
//                JSONObject jsonObject = new JSONObject(result);
//                JSONArray jsonArray = jsonObject.getJSONArray("items");
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    String Ret_Msg = jsonArray.getJSONObject(i).getString("Ret_Msg");
//                    if (Ret_Msg.equalsIgnoreCase("Allow")) {
//                        btn_submit.setVisibility(View.GONE);
//                        payNow_layout.setVisibility(View.VISIBLE);
//                        discount_details_layout.setVisibility(View.VISIBLE);
//
//                        String totalTickets = jsonArray.getJSONObject(i).getString("currentTickets");
//                        tv_total_tickets.setText(totalTickets);
//                        totTickets = Integer.valueOf(totalTickets);
//
//                        String ticketPrice = jsonArray.getJSONObject(i).getString("ticket_Price");
//                        tv_ticket_price.setText(ticketPrice);
//                        tktPrice = Integer.valueOf(ticketPrice);
//
//                        String serviceCharges = jsonArray.getJSONObject(i).getString("service_Charge");
//                        tv_service_charges.setText(serviceCharges);
//                        serCharge = Integer.valueOf(serviceCharges);
//
//                        String discountTickets = jsonArray.getJSONObject(i).getString("discount_Tickets");
//                        disTickets = Integer.valueOf(discountTickets);
//
//                        ticketsRemains = totTickets - disTickets;
//                        tv_tickets_after_discount.setText(String.valueOf(ticketsRemains));
//
//                        totalAmount = tktPrice * ticketsRemains;
//                        totalAmount = totalAmount + serCharge;
//                        tv_totalPrice.setText(String.valueOf(totalAmount));
//
//                        tv_payTotal.setText("PAY " + String.valueOf(totalAmount) + " QAR");
//
//
//                    } else {
//
//                    }
//
//
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void doTextShift() {
//
//        et_one.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_one.getText().toString().length() == 4)     //size as per your requirement
//                {
//                    et_two.requestFocus();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//
//        et_two.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_two.getText().toString().length() == 4)     //size as per your requirement
//                {
//                    et_three.requestFocus();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//
//        et_three.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_three.getText().toString().length() == 4)     //size as per your requirement
//                {
//                    et_four.requestFocus();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//        et_four.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_four.getText().toString().length() == 4)     //size as per your requirement
//                {
//                    et_validthru_month.requestFocus();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//
//        //TODO removing characters
//
//        et_four.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_four.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    if (et_three.getText().toString().length() == 4) {
//                        et_three.requestFocus();
//                    }
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//        et_three.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_three.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    if (et_two.getText().toString().length() == 4) {
//                        et_two.requestFocus();
//                    }
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//        et_two.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_two.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    if (et_one.getText().toString().length() == 4) {
//                        et_one.requestFocus();
//                    }
//
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//
//        //TODO card validity checking
//
//        et_validthru_month.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//
//                String cardValidity = et_validthru_month.getText().toString();
//                int integercardValidity;
//                String finalCardvalue = "";
//
//                if (s.length() ==1){
//                    integercardValidity = Integer.valueOf(cardValidity);
//                    if (integercardValidity == 0 || integercardValidity == 1){
//
//
//                    }else if (integercardValidity > 1 || integercardValidity <10){
//                        finalCardvalue = "0"+String.valueOf(integercardValidity);
//                        et_validthru_month.setText("");
//                        et_validthru_month.setText(finalCardvalue);
//
//                        Toast.makeText(DohaBankOfferCardActivity.this,finalCardvalue,Toast.LENGTH_LONG).show();
//
//                    }else if (integercardValidity >= 10 || integercardValidity <=12){
//                        finalCardvalue = String.valueOf(integercardValidity);
//                        et_validthru_month.setText("");
//                        et_validthru_month.setText(finalCardvalue);
//
//                        Toast.makeText(DohaBankOfferCardActivity.this,finalCardvalue,Toast.LENGTH_LONG).show();
//
//                    }
//
//
//                }else if (s.length() == 2){
//
//                }
//                if (et_validthru_month.getText().toString().length() == 2)     //size as per your requirement
//                {
//                    et_validthru_year.requestFocus();
//                }
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//
//
//
//        et_validthru_year.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//
//                String cardValidity = et_validthru_year.getText().toString();
//                int integercardValidity;
//                String finalCardvalue = "";
//
//
//                if (et_validthru_year.getText().toString().length() == 2)     //size as per your requirement
//                {
//                    et_cvvcode.requestFocus();
//                }
//
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//        et_validthru_year.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_validthru_year.getText().toString().length() == 2)     //size as per your requirement
//                {
//                    et_cvvcode.requestFocus();
//
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//        et_cvvcode.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                if (et_cvvcode.getText().toString().length() == 3)     //size as per your requirement
//                {
//                    et_nameoncard.requestFocus();
//
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });
//
//     /*   et_validthru_month.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // TODO Auto-generated method stub
//                String cardValidity = et_validthru.getText().toString();
//                int integercardValidity;
//                String finalCardvalue = "";
//
//                if (s.length() ==1){
//                    integercardValidity = Integer.valueOf(cardValidity);
//                    if (integercardValidity == 0 || integercardValidity == 1){
//
//
//                    }else if (integercardValidity > 1 || integercardValidity <10){
//                         finalCardvalue = "0"+String.valueOf(integercardValidity)+"/";
//                         et_validthru.setText("");
//                         et_validthru.setText(finalCardvalue);
//
//                        Toast.makeText(DohaBankOfferCardActivity.this,finalCardvalue,Toast.LENGTH_LONG).show();
//
//                    }else if (integercardValidity >= 10 || integercardValidity <=12){
//                        finalCardvalue = "0"+String.valueOf(integercardValidity)+"/";
//                        et_validthru.setText("");
//                        et_validthru.setText(finalCardvalue);
//
//                        Toast.makeText(DohaBankOfferCardActivity.this,finalCardvalue,Toast.LENGTH_LONG).show();
//
//                    }
//
//
//                }else if (s.length() == 2){
//
//
//
//                }else if (s.length() == 3){
//
//                }
//
//
//                et_validthru.setSelection(et_validthru.getText().length());
//
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });*/
//
//    }
//}
