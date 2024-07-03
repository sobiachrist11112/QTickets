package com.production.qtickets.ticketbookingdetaile;

import android.app.Dialog;
import androidx.room.Room;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.BookingDetaileModel;
import com.production.qtickets.model.CodesModel;
import com.production.qtickets.multipleVoucherCode.DBHelper;
import com.production.qtickets.multipleVoucherCode.VoucherCodeDatabase;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.utils.TextviewRegular;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.login.LoginActivity;
import com.production.qtickets.payment.PaymentActivity;
import com.production.qtickets.ticketconfirmation.TicketConfirmationActivity;

import org.greenrobot.eventbus.Subscribe;

public class EventBookingDetailsActivity extends AppCompatActivity implements BookingDetaileContracter.View, AdapterView.OnItemSelectedListener {

    //presenter
    BookingDetailePresenter presenter;

    //ids
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.movie_title)
    TextviewBold movieTitle;
    @BindView(R.id.txt_movie_loca)
    TextviewRegular txtMovieLoca;
    @BindView(R.id.txt_movie_date)
    TextviewRegular txtMovieDate;
    @BindView(R.id.txt_movie_type)
    TextviewRegular txtMovieType;
    @BindView(R.id.seat_recyclerview)
    RecyclerView seat_recyclerview;

    @BindView(R.id.txt_ticket_cost)
    TextView txt_ticket_cost;
    @BindView(R.id.txt__no_tic)
    TextView txt__no_tic;
    @BindView(R.id.txt__total_price)
    TextView txt__total_price;
    @BindView(R.id.txt__service_charge)
    TextView txt__service_charge;
    @BindView(R.id.txt__txt_total_payble_amount)
    TextView txt__txt_total_payble_amount;

    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_mail)
    EditText edt_mail;
    @BindView(R.id.spin_country_code)
    Spinner spin_country_code;
    @BindView(R.id.edt_phone_number)
    EditText edt_phone_number;
    @BindView(R.id.txt__or)
    TextView txt__or;
    @BindView(R.id.btn_login)
    TextView btn_login;

    @BindView(R.id.btn_pay)
    TextView btn_pay;
    @BindView(R.id.btn_apply)
    TextView btn_apply;
    @BindView(R.id.edt_voucher)
    EditText edt_voucher;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.txt__accept_terms)
    TextView txt__accept_terms;
    @BindView(R.id.txt_txt_ticket_cost)
    TextView txt_txt_ticket_cost;
    @BindView(R.id.terms_checkbox)
    CheckBox terms_checkbox;
    @BindView(R.id.txt_movie_time)
    TextView txt_movie_time;
    //string
    String str_country_code, str_voucher_code = "null", str_result_date;
    String total_price, total_price_after_apply_copon_code;
    String ticketCount = "0";
    String ticketPrice = "0";
    String eventID = "0";
    String eventName, eventBannerUrl, eventVenue, eventStartTime, eventEndTime, str_date,str_master_id;
    int str_coupon_code_amount;

    //country code list
    List<String> country_code_List = new ArrayList<String>(Arrays.asList(AppConstants.country_code));

    //adapter
    ArrayAdapter<String> adapter;


    //sessionmanager
    SessionManager sessionManager;

    //boolean
    boolean isexception = false;
    Date date;

    @BindView(R.id.voucherCodes_layout)
    LinearLayout voucherCodes_layout;

    @BindView(R.id.couponCodes_layout)
    LinearLayout couponCodes_layout;

    //int
    String ticket_id;
    int totalCost, ticketServiceCharge;

    //general
    private List<CodesModel> codes;
    //db related
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    public static VoucherCodeDatabase voucherCodeDatabase;
    int voucherCount = 0;
    RecyclerView voucherCodes_recyclerView;

    //views related
    private CodesAdapter codesAdapter;

    String CodeOne = "";
    String CodeTwo = "";
    String CodeThree = "";
    String CodeFour = "";
    String CodeFive = "";
    String CodeSix = "";
    String CodeSeven = "";
    String CodeEight = "";
    String CodeNine = "";
    String CodeTen = "";

    String voucherCodesFormat = "";

    int couponTotalValue;
    int totalDiscountValue=0;
    int finalValue=0;

    String currency = "";
    String androidDeviceId = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(EventBookingDetailsActivity.this);
        setContentView(R.layout.activity_booking_detials);
        ButterKnife.bind(this);
        presenter = new BookingDetailePresenter();
        presenter.attachView(this);
        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        init();
    }

    @Override
    public void init() {
        sessionManager = new SessionManager(EventBookingDetailsActivity.this);
        /*getting detailes from movie list ang seat selection time*/

        voucherCodes_recyclerView = (RecyclerView) findViewById(R.id.voucherCodes_recyclerView);
        voucherCodeDatabase = Room.databaseBuilder(getApplicationContext(), VoucherCodeDatabase.class, "voucherCodes").
                allowMainThreadQueries().build();
        voucherCodes_layout.setVisibility(View.GONE);

        currency = sessionManager.getCountryCurrency();
        //create out db helper object
        dbHelper = new DBHelper(this);
        //get database object to perform db operations
        sqLiteDatabase = dbHelper.getWritableDatabase();

        dbHelper.deleteAllCodes(sqLiteDatabase);

        codes = new ArrayList<>();

        com.production.qtickets.utils.GlobalBus.getBus().register(this);

        sessionManager = new SessionManager(EventBookingDetailsActivity.this);
        sessionManager.setDiscountAmount(String.valueOf(totalDiscountValue));

        Bundle b = getIntent().getExtras();
        if (b != null) {
            ticketCount = getIntent().getExtras().getString("ticketCount");
            ticketPrice = getIntent().getExtras().getString("ticketPrice");
            ticketServiceCharge = (int) getIntent().getExtras().getDouble("ticketServiceCharge");
            eventID = getIntent().getExtras().getString("eventID");
            eventName = getIntent().getExtras().getString("eventName");
            eventBannerUrl = getIntent().getExtras().getString("eventBannerUrl");
            eventVenue = getIntent().getExtras().getString("eventVenue");
            eventStartTime = getIntent().getExtras().getString("eventStartTime");
            eventEndTime = getIntent().getExtras().getString("eventEndTime");
            ticket_id = getIntent().getExtras().getString("ticket_id");
            //Toast.makeText(EventBookingDetailsActivity.this,ticket_id,Toast.LENGTH_LONG).show();
            totalCost = getIntent().getExtras().getInt("totalCost");
            str_date = getIntent().getExtras().getString("str_date");
            str_master_id = getIntent().getExtras().getString("str_master_id");
        }
        txt_txt_ticket_cost.setVisibility(View.GONE);
        txt_ticket_cost.setVisibility(View.GONE);
        txt_movie_time.setVisibility(View.GONE);
        tvToolbarTitle.setText(getString(R.string.bookingdetaile));
        movieTitle.setText(eventName);
        txtMovieLoca.setText(eventVenue);
        /*convert time into hours*/
        txtMovieType.setText(eventStartTime + " " + getString(R.string.to) + " " + eventEndTime);
        /*date convertion according to design*/
//        dateConvertionMethod(str_date);
//        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        String inputText = str_date;
//        try {
//            date = inputFormat.parse(inputText);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week abbreviated
        // txtMovieDate.setText(simpleDateformat.format(date) + "," + " " + str_date);
        txtMovieDate.setText(str_date);
        txt__no_tic.setText(ticketCount);
        Glide.with(EventBookingDetailsActivity.this).load(eventBannerUrl)
                .thumbnail(0.5f)
                .into(movieImage);
        txt_ticket_cost.setText(ticketPrice + " " + sessionManager.getCountryCurrency());
        txt__total_price.setText(String.valueOf(totalCost) + " " + sessionManager.getCountryCurrency());
        txt__service_charge.setText(String.valueOf(ticketServiceCharge)+ " " + sessionManager.getCountryCurrency());
        txt__txt_total_payble_amount.setText(String.valueOf(totalCost + ticketServiceCharge) + " " + sessionManager.getCountryCurrency());
        btn_pay.setText(getString(R.string.pay)+" "+String.valueOf(totalCost + ticketServiceCharge) + " " + sessionManager.getCountryCurrency());
        total_price = String.valueOf(totalCost + ticketServiceCharge);
        if (TextUtils.isEmpty(sessionManager.getUserId())) {
            txt__or.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.VISIBLE);
        } else {
            txt__or.setVisibility(View.GONE);
            btn_login.setVisibility(View.GONE);
        }
        edt_name.setText(sessionManager.getName());
        edt_mail.setText(sessionManager.getEmail());
        edt_phone_number.setText(sessionManager.getNumber());
        adapter = new FlagAdapter(this,R.layout.item_flag_spinner_adapter, AppConstants.country_code,AppConstants.flags, false,0);
        spin_country_code.setAdapter(adapter);
        sessionManager.setSelectedCountryCode("+"+sessionManager.getPrefix());
        spin_country_code.setSelection(adapter.getPosition("+"+sessionManager.getPrefix()));
        spin_country_code.setOnItemSelectedListener(this);
        checkbox.setOnCheckedChangeListener(new myCheckBoxChnageClicker());

    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public void setSqLiteDatabase(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * it builds recycler view
     */
    private void buildCodesList() {
        //set layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //set orientation to recycler view
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //set layout manager ro recycler view
        voucherCodes_recyclerView.setLayoutManager(linearLayoutManager);
        //create adapter
        codesAdapter = new CodesAdapter(this, codes);
        //set adapter to recycler view
        voucherCodes_recyclerView.setAdapter(codesAdapter);
    }

    @Subscribe
    public void getMessage(com.production.qtickets.utils.Events.DiscountTotalMessage discountTotalMessage) {

     /*   Log.v("discountTotalMessage","== "+discountTotalMessage.getDiscountPrice());
        Log.v("discountTotalMessage","== "+total_price);
        Log.v("discountTotalMessage","== "+totalDiscountValue);
        Log.v("discountTotalMessage","== "+finalValue)*/;

        String discountTotalAmount = discountTotalMessage.getDiscountPrice();
        couponTotalValue = Integer.parseInt(discountTotalMessage.getDiscountPrice());
        if (couponTotalValue > 0){

            discountTotalAmount = discountTotalAmount.substring(1);
            finalValue = finalValue - Integer.parseInt(discountTotalAmount);
            Log.v("AmountToPay","== "+finalValue );

            txt__txt_total_payble_amount.setText(finalValue + " " + currency);
            btn_pay.setText(getString(R.string.pay) + " " + finalValue+ " " + currency);

        }else if (couponTotalValue < 0){

            discountTotalAmount = discountTotalAmount.substring(1);
            finalValue = finalValue + Integer.parseInt(discountTotalAmount);
            Log.v("AmountToPay","== "+finalValue );

            txt__txt_total_payble_amount.setText(finalValue + " " + currency);
            btn_pay.setText(getString(R.string.pay) + " " + finalValue+ " " + currency);
        }

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(EventBookingDetailsActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(EventBookingDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, EventBookingDetailsActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        str_country_code = country_code_List.get(i);
        if (sessionManager.getSelectedCountryCode().equals(str_country_code)) {
            sessionManager.setSelectedCountryCode(str_country_code);
        } else {
            edt_phone_number.setText("");
            sessionManager.setSelectedCountryCode(str_country_code);
        }
        if (str_country_code.length() >= 4) {
            edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});

        } else if (str_country_code.length() >= 3) {
            edt_phone_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick({R.id.iv_back_arrow, R.id.btn_login, R.id.btn_apply, R.id.txt__accept_terms, R.id.btn_pay})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
            case R.id.btn_login:
                i = new Intent(EventBookingDetailsActivity.this, LoginActivity.class);
                startActivityForResult(i, AppConstants.LOGIN);
                break;
            case R.id.btn_pay:
                if (!TextUtils.isEmpty(edt_name.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(edt_mail.getText().toString().trim())) {
                        if (edt_mail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+.[a-zA-Z]+")) {
                            if (!TextUtils.isEmpty(edt_phone_number.getText().toString().trim())) {
                                String phoneNumber = edt_phone_number.getText().toString().trim();
                                if (isValidPhoneNumber(phoneNumber)) {
                                    boolean status = validateUsing_libphonenumber(str_country_code, phoneNumber);
                                    if (isexception) {
                                        isexception = false;
                                    } else {
                                        if (status) {
                                            if (terms_checkbox.isChecked()) {
                                                if(!TextUtils.isEmpty(eventID)) {
                                                    if(!TextUtils.isEmpty( String.valueOf(ticket_id))) {
                                                        showPb();
                                                        str_country_code = str_country_code.replace("+", "");
                                                        presenter.getEventBooking(eventID, ticket_id, total_price, ticketCount, str_master_id,
                                                                "0", edt_mail.getText().toString(), edt_name.getText().toString(), edt_phone_number.getText().toString()
                                                                , str_country_code, str_date, eventStartTime, "0", str_voucher_code);
                                                    }else {
                                                        showToast("ticket_id is null");
                                                    }
                                                }else {
                                                    showToast("event id null");
                                                }

                                            } else {
                                                showToast(getString(R.string.check_terms));
                                            }
                                        } else {
                                            edt_phone_number.setError(getString(R.string.inmobile));
                                        }
                                    }
                                }
                            } else {
                                edt_phone_number.setError(getString(R.string.v_number));
                            }
                        } else {
                            edt_mail.setError(getString(R.string.invalidemail));
                        }

                    } else {
                        edt_mail.setError(getString(R.string.v_email));
                    }
                } else {
                    edt_name.setError(getResources().getString(R.string.v_name));
                }
                break;
            case R.id.btn_apply:
                if(!str_voucher_code.equals("null")) {
                    if (!str_voucher_code.equals(edt_voucher.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edt_voucher.getText().toString().trim())) {
                            if (!TextUtils.isEmpty(edt_mail.getText().toString().trim())) {
                                showPb();
                                presenter.doApplyCouponcode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString(),androidDeviceId);
                            } else {
                                edt_mail.setError(getString(R.string.forgot));
                                edt_mail.requestFocus();
                            }
                        } else {
                            edt_voucher.setError(getString(R.string.e_code));
                            edt_voucher.requestFocus();
                        }
                    }else {
                        QTUtils.showDialogbox(EventBookingDetailsActivity.this, getString(R.string.a_copon_code_su) );
                    }
                }else {
                    if (!TextUtils.isEmpty(edt_voucher.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edt_mail.getText().toString().trim())) {
                            showPb();
                            presenter.doApplyCouponcode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString(),androidDeviceId);
                        } else {
                            edt_mail.setError(getString(R.string.forgot));
                            edt_mail.requestFocus();
                        }
                    } else {
                        edt_voucher.setError(getString(R.string.e_code));
                        edt_voucher.requestFocus();
                    }
                }
                break;
            case R.id.txt__accept_terms:
                TermsandConditionAlertDailog();
                break;


        }
    }

    private String getMultipleVoucherCodes() {

        if (!CodeOne.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeOne;
            } else {
                voucherCodesFormat = CodeOne;
            }
        }
        if (!CodeTwo.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeTwo;
            } else {
                voucherCodesFormat = CodeTwo;
            }
        }
        if (!CodeThree.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeThree;
            } else {
                voucherCodesFormat = CodeThree;
            }
        }
        if (!CodeFour.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeFour;
            } else {
                voucherCodesFormat = CodeFour;
            }
        }
        if (!CodeFive.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeFive;
            } else {
                voucherCodesFormat = CodeFive;
            }
        }
        if (!CodeSix.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeSix;
            } else {
                voucherCodesFormat = CodeSix;
            }
        }
        if (!CodeSeven.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeSeven;
            } else {
                voucherCodesFormat = CodeSeven;
            }
        }
        if (!CodeEight.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeEight;
            } else {
                voucherCodesFormat = CodeEight;
            }
        }
        if (!CodeNine.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeNine;
            } else {
                voucherCodesFormat = CodeNine;
            }
        }
        if (!CodeTen.isEmpty()) {
            if (!voucherCodesFormat.isEmpty()) {
                voucherCodesFormat = voucherCodesFormat + "," + CodeTen;
            } else {
                voucherCodesFormat = CodeTen;
            }
        }

        return voucherCodesFormat;
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = null;
        String isoCode = null;
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            phoneNumberUtil = PhoneNumberUtil.createInstance(EventBookingDetailsActivity.this);
            isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));

        } catch (Exception e) {
            isexception = true;
            e.printStackTrace();
        }
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
            boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
            if (isValid) {
                String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                return true;
            } else {
                Toast.makeText(EventBookingDetailsActivity.this, getResources().getString(R.string.validphonenumber), Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println(e);
        }


        return false;
    }

    @Override
    public void setBlockSeat(List<BookingDetaileModel> bookingDetaileModels) {

    }

    @Override
    public void setSeatLock(List<BookingDetaileModel> bookingDetaileModels) {

    }

    @Override
    public void setCouponCode(List<BookingDetaileModel> bookingDetaileModels) {
        if (bookingDetaileModels.get(0).status.equals("true")) {
            QTUtils.showDialogbox(EventBookingDetailsActivity.this, getString(R.string.coponcode) + " " + bookingDetaileModels.get(0).balance + " " + sessionManager.getCountryCurrency());
            str_coupon_code_amount = Integer.parseInt(bookingDetaileModels.get(0).balance);
            str_voucher_code = bookingDetaileModels.get(0).couponCode;


            String DiscountValue = sessionManager.getDiscountAmount();
            if (DiscountValue.isEmpty()){

                totalDiscountValue = str_coupon_code_amount;

            }else {
                Log.v("totalValue"," == "+DiscountValue);
                totalDiscountValue = Integer.parseInt(DiscountValue) + str_coupon_code_amount;

            }
            Log.v("totalValue"," == "+totalDiscountValue);


            voucherCodes_layout.setVisibility(View.VISIBLE);
            //insert into table if valid data
            dbHelper.addCode(sqLiteDatabase, str_voucher_code, String.valueOf(str_coupon_code_amount));
            new DatabaseTask().execute();

            buildCodesList();


            if (Integer.parseInt(total_price) > str_coupon_code_amount) {
                txt__txt_total_payble_amount.setText(String.valueOf(Integer.parseInt(total_price) - totalDiscountValue) + " " + sessionManager.getCountryCurrency());
                btn_pay.setText(getString(R.string.pay)+" "+String.valueOf(Integer.parseInt(total_price) - totalDiscountValue) + " " + sessionManager.getCountryCurrency());
                sessionManager.setDiscountAmount(String.valueOf(totalDiscountValue));
                finalValue = Integer.parseInt(total_price) - totalDiscountValue;
            }else {
                txt__txt_total_payble_amount.setText("0"+ " " + sessionManager.getCountryCurrency());
                btn_pay.setText(getString(R.string.pay)+" "+"0"+ " " + sessionManager.getCountryCurrency());
            }
            total_price_after_apply_copon_code = txt__txt_total_payble_amount.getText().toString();
         /*   checkbox.setChecked(true);
            checkbox.setVisibility(View.VISIBLE);
            edt_voucher.setEnabled(false);*/
        } else {
            edt_voucher.setEnabled(true);
            str_voucher_code = "null";
            txt__txt_total_payble_amount.setText(total_price + " " +sessionManager.getCountryCurrency());
            btn_pay.setText(getString(R.string.pay)+" "+total_price + " " +sessionManager.getCountryCurrency());
            checkbox.setVisibility(View.GONE);
            QTUtils.showDialogbox(EventBookingDetailsActivity.this, getString(R.string.invalidcode));
        }
    }

    class DatabaseTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            codes = dbHelper.getAllCodes(sqLiteDatabase);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            codesAdapter.notifyAdapter(codes);
        }
    }

    @Override
    public void setSeatLockConfirmation(List<BookingDetaileModel> bookingDetaileModels) {

    }

    @Override
    public void setEventBooking(List<BookingDetaileModel> bookingDetaileModels) {
        if (bookingDetaileModels.get(0).status.equalsIgnoreCase("True")) {
            Bundle b = new Bundle();
            b.putString("OrderInfo", bookingDetaileModels.get(0).orderid);
            b.putString("Amount", bookingDetaileModels.get(0).balanceamount);
            String from_which_page = "event";
            b.putString("from_which_page",from_which_page);
            b.putString("type","event");
            b.putString("strdiff","qtickets");
            String balance = bookingDetaileModels.get(0).balanceamount;
            if(balance.equalsIgnoreCase("0"))
            {
                Intent i = new Intent(EventBookingDetailsActivity.this, TicketConfirmationActivity.class);
                i.putExtras(b);
                startActivity(i);
            }else {
                Intent i = new Intent(EventBookingDetailsActivity.this, PaymentActivity.class);
                i.putExtras(b);
                startActivity(i);
            }

        } else {
            QTUtils.showDialogbox(EventBookingDetailsActivity.this, getString(R.string.ev_er_msg));
        }
    }

    @Override
    public void setnovoQticketsTicketConfirmation(NovoBookingConfirmationModel novoBookingConfirmationModel) {

    }

    @Override
    public void setNovoCouponCode(List<BookingDetaileModel> bookingDetaileModel) {

    }


    @Override
    public void setNovoPay(NovoBookingConfirmationModel novoBookingConfirmationModel) {

    }

    @Override
    public void setNovoUpdateCouponCode(BookingDetaileModel bookingDetaileModel) {

    }

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();
            if (isChecked) {
                if (buttonView == checkbox) {
                    edt_voucher.setEnabled(false);
                    str_voucher_code = edt_voucher.getText().toString();
                    txt__txt_total_payble_amount.setText(total_price_after_apply_copon_code);
                    btn_pay.setText(getString(R.string.pay)+" "+total_price_after_apply_copon_code);
                }
            } else {
                edt_voucher.setEnabled(true);
                str_voucher_code = "null";
                txt__txt_total_payble_amount.setText(total_price + " " + sessionManager.getCountryCurrency());
                btn_pay.setText(getString(R.string.pay)+" "+total_price + " " + sessionManager.getCountryCurrency());
            }
        }
    }

    private void TermsandConditionAlertDailog() {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_booking_detailes_terms_and_condition, null);
        // Build the dialog
        final Dialog customDialog = new Dialog(EventBookingDetailsActivity.this, R.style.MyDialogTheme);
        customDialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        //animation
        customDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        customDialog.show();
        customDialog.setCanceledOnTouchOutside(false);
        TextView txt_ok = customDialog.findViewById(R.id.txt_ok);
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
    }


    private void dateConvertionMethod(String date) {
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            try {
                date1 = form.parse(date);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            SimpleDateFormat postFormater = new SimpleDateFormat("dd MMM yyyy");
            str_result_date = postFormater.format(date1);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.LOGIN) {
            if (TextUtils.isEmpty(sessionManager.getUserId())) {
                txt__or.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.VISIBLE);
            } else {
                txt__or.setVisibility(View.GONE);
                btn_login.setVisibility(View.GONE);
            }
            edt_name.setText(sessionManager.getName());
            edt_mail.setText(sessionManager.getEmail());
            edt_phone_number.setText(sessionManager.getNumber());
            spin_country_code.setSelection(adapter.getPosition("+" + sessionManager.getPrefix()));
            sessionManager.setSelectedCountryCode("+"+sessionManager.getPrefix());
            edt_name.setError(null) ;
            edt_mail.setError(null) ;
            edt_phone_number.setError(null);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }



    class CodesAdapter extends RecyclerView.Adapter<CodesAdapter.ViewHolder> {

        private Context context;
        String discountPrice;
        private List<CodesModel> codes;

        public CodesAdapter(Context context, List<CodesModel> codes) {
            this.codes = codes;
            this.context = context;
        }

        public void notifyAdapter(List<CodesModel> codes) {
            this.codes = codes;
            notifyDataSetChanged();
        }

        @Override
        public CodesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.vouchers_list_row, parent, false);
            CodesAdapter.ViewHolder viewHolder = new CodesAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CodesAdapter.ViewHolder holder, final int position) {
            CodesModel codesModel = codes.get(position);
            holder.tv_voucher_codes.setText(codesModel.getCodename());
            holder.tv_voucher_value.setText(codesModel.getCodevalue() + " QAR");



            holder.code_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context,position,Toast.LENGTH_LONG).show();

                }
            });

            holder.code_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.v("checkBox", " == " + holder.code_checkbox.isChecked());
                    Log.v("checkBox", "== " + codes.get(position).getCodename());

                    if (holder.code_checkbox.isChecked()){

                        discountPrice = "+"+codes.get(position).getCodevalue();
                        Log.v("checkBox", "== " + discountPrice);
                        sentPriceToBookingDetails(discountPrice);

                    }else {
                        discountPrice = "-"+codes.get(position).getCodevalue();
                        Log.v("checkBox", "== " + "-"+codes.get(position).getCodevalue());
                        sentPriceToBookingDetails(discountPrice);
                    }


                    if (position == 0){
                        CodeOne = "";
                        CodeOne = codes.get(position).getCodename();
                    }else if (position == 1){
                        CodeTwo = "";
                        CodeTwo = codes.get(position).getCodename();
                    }else if (position == 2){
                        CodeThree = "";
                        CodeThree = codes.get(position).getCodename();
                    }else if(position == 3){
                        CodeFour = "";
                        CodeFour = codes.get(position).getCodename();
                    }else if(position == 4){
                        CodeFive = "";
                        CodeFive = codes.get(position).getCodename();
                    }else if (position == 5){
                        CodeSix = "";
                        CodeSix = codes.get(position).getCodename();
                    }else if (position == 6){
                        CodeSeven = "";
                        CodeSeven = codes.get(position).getCodename();
                    }else if (position == 7){
                        CodeEight = "";
                        CodeEight = codes.get(position).getCodename();
                    }else if (position == 8){
                        CodeNine = "";
                        CodeNine = codes.get(position).getCodename();
                    }else if (position == 9){
                        CodeTen = "";
                        CodeTen = codes.get(position).getCodename();
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return codes.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            CheckBox code_checkbox;
            TextView tv_voucher_codes;
            TextView tv_voucher_value;
            LinearLayout code_layout;

            public ViewHolder(View itemView) {
                super(itemView);

                tv_voucher_codes = (TextView) itemView.findViewById(R.id.tv_voucher_codes);
                tv_voucher_value = (TextView) itemView.findViewById(R.id.tv_voucher_value);
                code_checkbox = (CheckBox) itemView.findViewById(R.id.code_checkbox);
                code_layout = (LinearLayout) itemView.findViewById(R.id.code_layout);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {


            }
        }

    }

    private void sentPriceToBookingDetails(String discountPrice) {
        com.production.qtickets.utils.Events.DiscountTotalMessage discountTotalMessage =
                new com.production.qtickets.utils.Events.DiscountTotalMessage(discountPrice);
        com.production.qtickets.utils.GlobalBus.getBus().post(discountTotalMessage);
    }
}

