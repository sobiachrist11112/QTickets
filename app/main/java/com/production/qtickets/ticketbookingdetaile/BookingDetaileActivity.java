package com.production.qtickets.ticketbookingdetaile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.adapters.FlagAdapter;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.login.LoginActivity;
import com.production.qtickets.model.BookingDetaileModel;
import com.production.qtickets.model.CodesModel;
import com.production.qtickets.model.TicketlistModel;
import com.production.qtickets.movies.seatselection.SeatSelectionActivity;
import com.production.qtickets.multipleVoucherCode.DBHelper;
import com.production.qtickets.multipleVoucherCode.VoucherCodeDatabase;
import com.production.qtickets.novoticketconfirmation.NovoTicketConfirmationActivity;
import com.production.qtickets.payment.PaymentActivity;
import com.production.qtickets.ticketconfirmation.TicketConfirmationActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.utils.TextviewRegular;

import org.greenrobot.eventbus.Subscribe;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * Created by Harsh on 6/1/2018.
 */
public class BookingDetaileActivity extends AppCompatActivity implements BookingDetaileContracter.View, AdapterView.OnItemSelectedListener {

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
    @BindView(R.id.txt_movie_time)
    TextviewRegular txt_movie_time;


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

 /*   @BindView(R.id.tv_voucher_codes)
    TextView tv_voucher_codes;*/

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
    @BindView(R.id.terms_checkbox)
    CheckBox terms_checkbox;
    @BindView(R.id.rec_novo_ticket_cost)
    RecyclerView rec_novo_ticket_cost;
    @BindView(R.id.txt_txt_ticket_cost)
    TextView txt_txt_ticket_cost;
    @BindView(R.id.txt_novo_ticket_heder)
    TextView txt_novo_ticket_heder;

    @BindView(R.id.voucherCodes_layout)
     LinearLayout voucherCodes_layout;

    @BindView(R.id.couponCodes_layout)
    LinearLayout couponCodes_layout;

    //string
    String str_transaction_id;
    String str_country_code;
    String str_voucher_code = "null";
    int str_coupon_code_amount;
    String str_result_date;
    String screen_name;
    String str_differ;
    String tickettypes;
    String selected_ticket_info;
    String session_id;
    String booking_info_id;
    String userSessionID;
    String seat_list, str_novo_total_amount;
    String cid;
    double booking_total_price_value;
    String total_price, currency, total_price_after_apply_copon_code, show_time, result_time;
    String mMovie_name, mMall_Name, show_date, mShow_ID, mMoviesCost, duration, movie_type, movie_img_url, selected_seats, perTicketPrice,new_perTicketPrice;

    int couponTotalValue;
    int totalDiscountValue=0;
    int finalValue=0;

    //country code list
    List<String> country_code_List = new ArrayList<String>(Arrays.asList(AppConstants.country_code));
    List<String> ticket_info = new ArrayList<>();
    private List<String> mSelectedSeatsList;

    public static ArrayList<HashMap<String, String>> voucherArrayList = new ArrayList<HashMap<String, String>>();

    //adapter
    ArrayAdapter<String> adapter;
    SeatAdapter seat_adapter;
    TicketInfoAdapter ticketInfoAdapter;

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

    //general
    private List<CodesModel> codes;
    //db related
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;

    //sessionmanager
    SessionManager sessionManager;

    //boolean
    boolean isexception = false;

    private static final String SEPARATOR = ",";
    StringBuilder csvBuilder = new StringBuilder();
    Date date;
    private AlertDialog alertDialog;

    //recyclerview
    GridLayoutManager mLayoutManager;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager linearLayoutManagerTwo;
    double servicecharge;
    Integer booking_id;

    public static VoucherCodeDatabase voucherCodeDatabase;
    int voucherCount = 0;
    RecyclerView voucherCodes_recyclerView;

    VoucherAdapter voucherAdapter;
    String androidDeviceId = "";
    List<TicketlistModel> ticket_list;
    Double amount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(BookingDetaileActivity.this);
        setContentView(R.layout.activity_booking_detials);
        ButterKnife.bind(this);
        presenter = new BookingDetailePresenter();
        presenter.attachView(this);

        androidDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        voucherCodeDatabase = Room.databaseBuilder(getApplicationContext(), VoucherCodeDatabase.class, "voucherCodes").
                allowMainThreadQueries().build();
        voucherCodes_layout.setVisibility(View.GONE);


        String dohaOffer = QTUtils.dohaBankOffer;
        if (!dohaOffer.isEmpty()){
            if (dohaOffer.equalsIgnoreCase("yes")){
                couponCodes_layout.setVisibility(View.GONE);
            }else if (dohaOffer.equalsIgnoreCase("no")){
                couponCodes_layout.setVisibility(View.VISIBLE);
            }else {
                couponCodes_layout.setVisibility(View.VISIBLE);
            }

        }else {
            couponCodes_layout.setVisibility(View.VISIBLE);
        }

        //create out db helper object
        dbHelper = new DBHelper(this);
        //get database object to perform db operations
        sqLiteDatabase = dbHelper.getWritableDatabase();

        dbHelper.deleteAllCodes(sqLiteDatabase);

        codes = new ArrayList<>();

        init();
    }
// https://prnt.sc/NsJlPNOH8P5K
    @Override
    public void setBlockSeat(List<BookingDetaileModel> bookingDetaileModels) {
        if (bookingDetaileModels.get(0).status.equals("True")) {
            currency = bookingDetaileModels.get(0).currency;
            sessionManager.setCountryCurrency(currency);
            txt__total_price.setText(bookingDetaileModels.get(0).ticketprice + " " + bookingDetaileModels.get(0).currency);
            txt__txt_total_payble_amount.setText(bookingDetaileModels.get(0).totalprice + " " + bookingDetaileModels.get(0).currency);
            btn_pay.setText(getString(R.string.pay) + " " + bookingDetaileModels.get(0).totalprice + " " + bookingDetaileModels.get(0).currency);
            total_price = bookingDetaileModels.get(0).totalprice;
            txt__service_charge.setText(bookingDetaileModels.get(0).servicecharges + " " + bookingDetaileModels.get(0).currency);
            str_transaction_id = bookingDetaileModels.get(0).transactionId;
            txt_ticket_cost.setText(new_perTicketPrice + " " + bookingDetaileModels.get(0).currency);
//            try {
//                final Handler handler = new Handler();
//                final Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        timeourDilaogBox();
//                    }
//                };
//                handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(10));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } else {
            QTUtils.finishshowDialogbox(BookingDetaileActivity.this, bookingDetaileModels.get(0).errormsg);
        }
    }

    @Override
    public void setSeatLock(List<BookingDetaileModel> bookingDetaileModels) {
        if ((bookingDetaileModels.get(0).status.equalsIgnoreCase("True"))) {
            presenter.doSeatLockConfirmation(str_transaction_id);
        } else {
            // showPb();
            if(str_voucher_code.equals("")){
                str_voucher_code=edt_voucher.getText().toString();
            }
            presenter.doseatLock(str_transaction_id, edt_name.getText().toString(),
                    edt_mail.getText().toString(), edt_phone_number.getText().toString()
                    , str_country_code, str_voucher_code);
        }


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
    public void getMessage(Events.DiscountTotalMessage discountTotalMessage) {

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
    public void setCouponCode(List<BookingDetaileModel> bookingDetaileModels) {
        if (bookingDetaileModels.get(0).status.equals("true")) {
            QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.coponcode) + " " + bookingDetaileModels.get(0).balance + " " + sessionManager.getCountryCurrency());
            str_coupon_code_amount =  Integer.parseInt(bookingDetaileModels.get(0).balance);
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


            if (Integer.parseInt(total_price) > totalDiscountValue) {
                txt__txt_total_payble_amount.setText(String.valueOf(Integer.parseInt(total_price) - totalDiscountValue) + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + String.valueOf(Integer.parseInt(total_price) - totalDiscountValue) + " " + currency);
                sessionManager.setDiscountAmount(String.valueOf(totalDiscountValue));
                finalValue = Integer.parseInt(total_price) - totalDiscountValue;
            } else {
                txt__txt_total_payble_amount.setText("0" + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + "0" + " " + currency);
            }
            total_price_after_apply_copon_code = txt__txt_total_payble_amount.getText().toString();


           /* if (Integer.parseInt(total_price) > str_coupon_code_amount) {
                txt__txt_total_payble_amount.setText(String.valueOf(Integer.parseInt(total_price) - str_coupon_code_amount) + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + String.valueOf(Integer.parseInt(total_price) - str_coupon_code_amount) + " " + currency);
                sessionManager.setDiscountAmount(String.valueOf(str_coupon_code_amount));
            } else {
                txt__txt_total_payble_amount.setText("0" + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + "0" + " " + currency);
            }
            total_price_after_apply_copon_code = txt__txt_total_payble_amount.getText().toString();*/


         /*   checkbox.setChecked(true);
            checkbox.setVisibility(View.VISIBLE);
            edt_voucher.setEnabled(false);*/
        } else {
            edt_voucher.setEnabled(true);
            str_voucher_code = "null";
            txt__txt_total_payble_amount.setText(total_price + " " + sessionManager.getCountryCurrency());
            btn_pay.setText(getString(R.string.pay) + " " + total_price + " " + sessionManager.getCountryCurrency());
            checkbox.setVisibility(View.GONE);
            QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.invalidcode));
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
    public void setSeatLockConfirmation(@NonNull List<BookingDetaileModel> bookingDetaileModels) {
        if (bookingDetaileModels.get(0).status.equals("True")) {
            dismissPb();
            Bundle b = new Bundle();
            b.putString("transaction_id", bookingDetaileModels.get(0).transactionId);
            b.putString("OrderInfo", bookingDetaileModels.get(0).orderInfo);
            b.putString("Amount", String.valueOf(Integer.parseInt(bookingDetaileModels.get(0).balance) -totalDiscountValue));
       //     b.putString("Amount", bookingDetaileModels.get(0).balance);
            String from_which_page = "movie";
            b.putString("from_which_page", from_which_page);
            b.putString("email",edt_mail.getText().toString());
            b.putString("phonenumber",edt_phone_number.getText().toString());
            String balance = bookingDetaileModels.get(0).balance;

            if (balance.equalsIgnoreCase("0")) {
                Intent i = new Intent(BookingDetaileActivity.this, TicketConfirmationActivity.class);
                i.putExtras(b);
                startActivity(i);
            } else {
                String dohaOffer = QTUtils.dohaBankOffer;
                if (!dohaOffer.isEmpty()) {
                    Log.v("dohaOffer", " == not null");
                    if (dohaOffer.equalsIgnoreCase("yes")) {
//                        b.putString("strdiff", "qtickets");
//                        Intent i = new Intent(BookingDetaileActivity.this, DohaBankOfferCardActivity.class);
//                        i.putExtras(b);
//                        startActivity(i);

                    } else if (dohaOffer.equalsIgnoreCase("no")) {

                        Log.v("dohaOffer", " == no");
                        b.putString("strdiff", "qtickets");
                        b.putString("type", "Movie");
                        b.putString("cid", cid);
                        Intent i = new Intent(BookingDetaileActivity.this, PaymentActivity.class);
                        i.putExtras(b);
                        startActivity(i);

                    } else {

                        Log.v("dohaOffer", " == no");
                        b.putString("strdiff", "qtickets");
                        b.putString("type", "Movie");
                        b.putString("cid", cid);
                        Intent i = new Intent(BookingDetaileActivity.this, PaymentActivity.class);
                        i.putExtras(b);
                        startActivity(i);

                    }


                } else {
                    Log.v("dohaOffer", " == no");
                    b.putString("strdiff", "qtickets");
                    b.putString("type", "Movie");
                    b.putString("cid", cid);
                    Intent i = new Intent(BookingDetaileActivity.this, PaymentActivity.class);
                    i.putExtras(b);
                    startActivity(i);
                }

            }
        } else {
            // showPb();
            presenter.doSeatLockConfirmation(str_transaction_id);
        }
    }

    @Override
    public void setEventBooking(List<BookingDetaileModel> eventBooking) {

    }

    @Override
    public void setnovoQticketsTicketConfirmation(NovoBookingConfirmationModel novoBookingConfirmationModel) {
        if (novoBookingConfirmationModel.status.equals("1")) {
            if (novoBookingConfirmationModel.arrayData.table.size() > 0) {
                booking_id = novoBookingConfirmationModel.arrayData.table.get(0).bookid;
                Integer amount = novoBookingConfirmationModel.arrayData.table.get(0).amount;
                // servicecharge = novoBookingConfirmationModel.arrayData.table.get(0).serch;

            } else {
                finishshowDialogbox(this, getString(R.string.novosession));
            }
        } else {
            finishshowDialogbox(this, getString(R.string.novosession));
        }
    }

    @Override
    public void setNovoCouponCode(List<BookingDetaileModel> bookingDetaileModel) {
//        if (bookingDetaileModel.status.equals("valid")) {
//            QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.coponcode) + " " + bookingDetaileModel.balance + " " + sessionManager.getCountryCurrency());
//            str_coupon_code_amount = bookingDetaileModel.balance;
//            str_voucher_code = String.valueOf(bookingDetaileModel.coupon);
//            if (Integer.parseInt(total_price) > Integer.parseInt(str_coupon_code_amount)) {
//                txt__txt_total_payble_amount.setText(String.valueOf(Integer.parseInt(total_price) - Integer.parseInt(str_coupon_code_amount)) + " " + currency);
//                btn_pay.setText(getString(R.string.pay) + " " + String.valueOf(Integer.parseInt(total_price) - Integer.parseInt(str_coupon_code_amount)) + " " + currency);
//                str_novo_total_amount = String.valueOf(Integer.parseInt(total_price) - Integer.parseInt(str_coupon_code_amount));
//            } else {
//                str_novo_total_amount = "0";
//                txt__txt_total_payble_amount.setText("0" + " " + currency);
//                btn_pay.setText(getString(R.string.pay) + " " + "0" + " " + currency);
//            }
//            total_price_after_apply_copon_code = txt__txt_total_payble_amount.getText().toString();
//            checkbox.setChecked(true);
//            checkbox.setVisibility(View.VISIBLE);
//            edt_voucher.setEnabled(false);
//        } else {
//            edt_voucher.setEnabled(true);
//            str_voucher_code = "null";
//            txt__txt_total_payble_amount.setText(total_price + " " + sessionManager.getCountryCurrency());
//            btn_pay.setText(getString(R.string.pay) + " " + total_price + " " + sessionManager.getCountryCurrency());
//            str_novo_total_amount = total_price;
//            checkbox.setVisibility(View.GONE);
//            QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.invalidcode));
//        }
        if (bookingDetaileModel.get(0).status.equals("true")) {
            QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.coponcode) + " " + bookingDetaileModel.get(0).balance + " " + sessionManager.getCountryCurrency());
            str_coupon_code_amount = Integer.parseInt(bookingDetaileModel.get(0).balance);
            str_voucher_code = bookingDetaileModel.get(0).couponCode;

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

            if (Integer.parseInt(total_price) > totalDiscountValue) {
                txt__txt_total_payble_amount.setText(String.valueOf(Integer.parseInt(total_price) - totalDiscountValue) + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + String.valueOf(Integer.parseInt(total_price) - totalDiscountValue) + " " + currency);
                sessionManager.setDiscountAmount(String.valueOf(totalDiscountValue));
                finalValue = Integer.parseInt(total_price) - totalDiscountValue;
            } else {
                str_novo_total_amount = "0";
                txt__txt_total_payble_amount.setText("0" + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + "0" + " " + currency);
            }
            total_price_after_apply_copon_code = txt__txt_total_payble_amount.getText().toString();



//            checkbox.setChecked(true);
//            checkbox.setVisibility(View.VISIBLE);
//            edt_voucher.setEnabled(false);
         /*   checkbox.setChecked(true);
            checkbox.setVisibility(View.VISIBLE);
            edt_voucher.setEnabled(false);*/
        } else {
            edt_voucher.setEnabled(true);
            str_voucher_code = "null";
            txt__txt_total_payble_amount.setText(total_price + " " + sessionManager.getCountryCurrency());
            btn_pay.setText(getString(R.string.pay) + " " + total_price + " " + sessionManager.getCountryCurrency());
            str_novo_total_amount = total_price;
            checkbox.setVisibility(View.GONE);
            QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.invalidcode));
        }
    }

    @Override
    public void setNovoPay(NovoBookingConfirmationModel novoBookingConfirmationModel) {
        if (novoBookingConfirmationModel.status.equals("1")) {
            showPb();
            if (str_voucher_code.equals("null")) {
                Bundle b = new Bundle();
                b.putString("OrderInfo", String.valueOf(booking_id));
                b.putString("Amount", str_novo_total_amount);
                b.putString("strdiff", "Novo");
                b.putString("type", "Novo");
                b.putString("cid", cid);
                b.putString("email",edt_mail.getText().toString());
                b.putString("phonenumber",edt_phone_number.getText().toString());
                Intent i = new Intent(BookingDetaileActivity.this, PaymentActivity.class);
                i.putExtras(b);
                startActivity(i);
            } else {
                String multipleVoucherCodes = getMultipleVoucherCodes();
                multipleVoucherCodes = multipleVoucherCodes.replace(",","|");

                presenter.doNovoUpdateCouponCode(edt_mail.getText().toString().trim(), multipleVoucherCodes, String.valueOf(booking_id));
                //presenter.doNovoUpdateCouponCode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString(), String.valueOf(booking_id));
            }
        }
    }

    @Override
    public void setNovoUpdateCouponCode(BookingDetaileModel bookingDetaileModel) {
        if (bookingDetaileModel.status.equals("valid")) {
            String balance = bookingDetaileModel.balance;
            if (balance.equalsIgnoreCase("0")) {
                Bundle b = new Bundle();
                b.putString("booking_id", String.valueOf(booking_id));
                b.putString("userSessionID", userSessionID);
                b.putString("movie_img_url", movie_img_url);
                Intent i = new Intent(BookingDetaileActivity.this, NovoTicketConfirmationActivity.class);
                i.putExtras(b);
                startActivity(i);
            } else {
                Bundle b = new Bundle();
                b.putString("OrderInfo", String.valueOf(booking_id));
                b.putString("Amount", balance);
                b.putString("strdiff", "Novo");
                b.putString("type","Novo");
                b.putString("email",edt_mail.getText().toString());
                b.putString("phonenumber",edt_phone_number.getText().toString());
                b.putString("cid", cid);
                Intent i = new Intent(BookingDetaileActivity.this, PaymentActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        } else {
            showToast(getString(R.string.noresponse));
        }
    }

    public void finishshowDialogbox(final Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialog.dismiss();
                            Intent i = new Intent(BookingDetaileActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void init() {
        /*getting detailes from movie list ang seat selection time*/
        com.production.qtickets.utils.GlobalBus.getBus().register(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            str_differ = b.getString("strdiff");
            mMovie_name = b.getString("MOVIE_NAME");
            mMall_Name = b.getString("MOVIE_MALL");
            show_date = b.getString("MOVIE_DATE");
            mSelectedSeatsList = b.getStringArrayList("TOTAL PERSON");
            for (String city : mSelectedSeatsList) {
                csvBuilder.append(city);
                csvBuilder.append(SEPARATOR);
            }
            selected_seats = csvBuilder.toString();
            /*remove comma at the end of text*/
            if (selected_seats.endsWith(",")) {
                selected_seats = selected_seats.substring(0, selected_seats.length() - 1);
            }
            Log.e("selectedSeat","========="+selected_seats);
            duration = b.getString("duration");
            movie_type = b.getString("movie_type");
            movie_img_url = b.getString("movie_img_url");
            screen_name = b.getString("screen_name");
            show_time = b.getString("show_time");
        }
        tvToolbarTitle.setText(getString(R.string.bookingdetaile));
        movieTitle.setText(mMovie_name);
        txtMovieLoca.setText(mMall_Name);

        voucherCodes_recyclerView = (RecyclerView) findViewById(R.id.voucherCodes_recyclerView);
/*        linearLayoutManagerTwo = new LinearLayoutManager(BookingDetaileActivity.this, LinearLayoutManager.VERTICAL, false);
        voucherCodes_recyclerView.setLayoutManager(linearLayoutManagerTwo);*/
        /*convert time into hours*/
        txtMovieType.setText(movie_type + "," + " " + screen_name);

//        try {
//            timeconvertionMethod(show_time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        if(!duration.isEmpty()) {
            txt_movie_time.setText(show_time + "," + " " + convertMinutesToHours(duration));
        }
        txt__no_tic.setText(String.valueOf(mSelectedSeatsList.size()));
        Glide.with(BookingDetaileActivity.this).load(movie_img_url)
                .thumbnail(0.5f)
                .into(movieImage);

        sessionManager = new SessionManager(BookingDetaileActivity.this);
        sessionManager.setDiscountAmount(String.valueOf(totalDiscountValue));
        /*date convertion according to design*/
        if (str_differ.equals("qtickets")) {
            mShow_ID = b.getString("MOVIE_SHOW_TIME_ID");
            dateConvertionMethod(show_date);
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String inputText = show_date;
            try {
                date = inputFormat.parse(inputText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week abbreviated
            txtMovieDate.setText(simpleDateformat.format(date) + "," + " " + str_result_date);
            perTicketPrice = String.valueOf(b.getDouble("PER_TICKET_PRICE"));
            new_perTicketPrice = perTicketPrice.replace(".0","");
            mMoviesCost = String.valueOf(b.getDouble("TOTAL Amount"));
            showPb();
            presenter.getBlockseat(mShow_ID, selected_seats);
            rec_novo_ticket_cost.setVisibility(View.GONE);
            txt_novo_ticket_heder.setVisibility(View.GONE);
            txt_txt_ticket_cost.setVisibility(View.VISIBLE);
            txt_ticket_cost.setVisibility(View.VISIBLE);
        } else {
            DateFormat inputFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.US);
            String inputText = show_date;
            try {
                date = inputFormat.parse(inputText);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week abbreviated
            txtMovieDate.setText(simpleDateformat.format(date) + "," + " " + show_date);
            tickettypes = b.getString("tickettypes");
            mMoviesCost = b.getString("TOTAL_Amount");
            txt_novo_ticket_heder.setVisibility(View.VISIBLE);
            rec_novo_ticket_cost.setVisibility(View.VISIBLE);
            txt__total_price.setText(mMoviesCost);
            txt_txt_ticket_cost.setVisibility(View.GONE);
            txt_ticket_cost.setVisibility(View.GONE);
            txt__txt_total_payble_amount.setText(mMoviesCost);
            selected_ticket_info = b.getString("selected_ticket_info");
            ticket_info = Arrays.asList(tickettypes.split(","));
            linearLayoutManager = new LinearLayoutManager(BookingDetaileActivity.this, LinearLayoutManager.VERTICAL, false);
            rec_novo_ticket_cost.setLayoutManager(linearLayoutManager);
            ticketInfoAdapter = new TicketInfoAdapter(ticket_info, this);
            rec_novo_ticket_cost.setAdapter(ticketInfoAdapter);
            session_id = b.getString("session_id");
            booking_info_id = b.getString("booking_info_id");
            userSessionID = b.getString("userSessionID");
            seat_list = b.getString("seat_list");
            booking_total_price_value = b.getDouble("booking_total_price_value");
            currency = b.getString("currency");
            cid = b.getString("cid");
            ticket_list =
                    (List<TicketlistModel>)b.getSerializable("ticketlist");
            amount = Double.parseDouble(mMoviesCost.replace("QAR ",""));
            servicecharge = 0.00;
            //   servicecharge = servicecharge / 100;
            /*calculating service charge for Novo cinemas*/
            for(int i = 0 ; i < ticket_list.size() ; i++){
                Log.e("========",""+ticket_list.get(i).noOfSeats);
                if(Double.parseDouble(ticket_list.get(i).ticketPrice) <= 75.00){
                    servicecharge = servicecharge + ticket_list.get(i).noOfSeats * 5.00;//commented by sudhir

                    servicecharge = Math.ceil(servicecharge);
                    txt__service_charge.setText(String.valueOf(servicecharge) + " " + currency);
                }else  if(Double.parseDouble(ticket_list.get(i).ticketPrice) > 75.00 && Double.parseDouble(ticket_list.get(i).ticketPrice) <= 150.00){
                    servicecharge = servicecharge + ticket_list.get(i).noOfSeats * 8.00;
                    servicecharge = Math.ceil(servicecharge);
                    txt__service_charge.setText(String.valueOf(servicecharge) + " " + currency);
                }else if(Double.parseDouble(ticket_list.get(i).ticketPrice) > 150.00){
                    double t_amount = Double.parseDouble(ticket_list.get(i).ticketPrice);
                    servicecharge = servicecharge +  Math.ceil(((t_amount / 100.0f) * 5))*ticket_list.get(i).noOfSeats;// commented by udhir
                    servicecharge = Math.ceil(servicecharge);
                    txt__service_charge.setText(String.valueOf(servicecharge) + " " + currency);
                }
            }
            txt__total_price.setText(String.valueOf(amount) + " " + currency);
            Double total_amount = amount + servicecharge;
            str_novo_total_amount = String.valueOf(total_amount);
            txt__txt_total_payble_amount.setText(String.valueOf(total_amount) + " " + currency);
            btn_pay.setText(getString(R.string.pay) + " " + String.valueOf(total_amount) + " " + currency);
            total_price = String.valueOf(total_amount);
            showPb();
            presenter.donovoQticketsTicketConfirmation(cid, mMall_Name, sessionManager.getMovieId(), mMovie_name, movie_img_url,
                    session_id, booking_info_id, userSessionID, show_date, show_time, String.valueOf(mSelectedSeatsList.size()), String.valueOf(servicecharge), String.valueOf(booking_total_price_value),
                    String.valueOf(booking_total_price_value), selected_ticket_info, seat_list);

        }

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
        adapter = new FlagAdapter(this, R.layout.item_flag_spinner_adapter, AppConstants.country_code, AppConstants.flags, false,0);
        spin_country_code.setAdapter(adapter);
        sessionManager.setSelectedCountryCode("+" + sessionManager.getPrefix());
        spin_country_code.setSelection(adapter.getPosition("+" + sessionManager.getPrefix()));
        spin_country_code.setOnItemSelectedListener(this);
        checkbox.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
        mLayoutManager = new GridLayoutManager(this, 8);
        seat_recyclerview.setLayoutManager(mLayoutManager);
        seat_recyclerview.setVisibility(View.VISIBLE);
        seat_adapter = new SeatAdapter(mSelectedSeatsList, this);
        seat_recyclerview.setAdapter(seat_adapter);


    }

    private void timeconvertionMethod(String time) throws ParseException {
        DateFormat f1 = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
        Date d = f1.parse(time);
        DateFormat f2 = new SimpleDateFormat("hh:mm a");
        result_time = f2.format(d).toLowerCase();
    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(BookingDetaileActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(BookingDetaileActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, BookingDetaileActivity.this, message);
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
        switch (view.getId()) {
            case R.id.iv_back_arrow:

                onBackPressed();
                break;
            case R.id.btn_login:
                Intent i1 = new Intent(BookingDetaileActivity.this, LoginActivity.class);
                startActivityForResult(i1, AppConstants.LOGIN);
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
                                                if (str_differ.equals("qtickets")) {
                                                    showPb();
                                                    String multipleVoucherCodes = getMultipleVoucherCodes();
                                                   // Toast.makeText(BookingDetaileActivity.this,multipleVoucherCodes,Toast.LENGTH_LONG).show();
                                                    str_voucher_code = multipleVoucherCodes;

                                                    if(str_voucher_code.equals("")){
                                                        str_voucher_code=edt_voucher.getText().toString();
                                                    }

                                                    str_country_code = str_country_code.replace("+", "");
                                                    presenter.doseatLock(str_transaction_id, edt_name.getText().toString(), edt_mail.getText().toString(), edt_phone_number.getText().toString()
                                                            , str_country_code, str_voucher_code);
                                                    //E53CE7F151
                                                  // https://prnt.sc/GjnoXmjMlapq  timer();
                                                } else {
                                                    showPb();


                                                    str_country_code = str_country_code.replace("+", "");
                                                    String user_id = sessionManager.getUserId();
                                                    if (TextUtils.isEmpty(user_id)) {
                                                        user_id = "0";
                                                    }
                                                    presenter.doNovopay(user_id, cid, mMall_Name, sessionManager.getMovieId()
                                                            , mMovie_name, movie_img_url, session_id, booking_info_id, userSessionID, show_date, show_time,
                                                            String.valueOf(mSelectedSeatsList.size()), String.valueOf(servicecharge), str_novo_total_amount, str_novo_total_amount
                                                            , selected_ticket_info, seat_list, String.valueOf(booking_id), edt_name.getText().toString(), edt_phone_number.getText().toString(), edt_mail.getText().toString()
                                                            , str_country_code);

                                                }
                                            } else {
                                                showToast(getString(R.string.check_terms));
                                            }
                                        } else {
                                            showToast(getString(R.string.inmobile));
                                           /* edt_phone_number.setError(getString(R.string.inmobile));
                                            edt_phone_number.requestFocus();*/
                                        }
                                    }
                                }
                            } else {
                                showToast(getString(R.string.v_number));
                              /*  edt_phone_number.setError(getString(R.string.v_number));
                                edt_phone_number.requestFocus();*/
                            }
                        } else {
                            showToast(getString(R.string.invalidemail));
                           /* edt_mail.setError(getString(R.string.invalidemail));
                            edt_mail.requestFocus();*/
                        }

                    } else {
                        showToast(getString(R.string.v_email));
                       /* edt_mail.setError(getString(R.string.v_email));
                        edt_mail.requestFocus();*/
                    }
                } else {
                    showToast(getString(R.string.v_name));
                 /*   edt_name.setError(getResources().getString(R.string.v_name));
                    edt_name.requestFocus();*/
                }
                break;
            case R.id.btn_apply:
                if (!str_voucher_code.equals("null")) {
                    if (!str_voucher_code.equals(edt_voucher.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edt_voucher.getText().toString().trim())) {
                            if (!TextUtils.isEmpty(edt_mail.getText().toString().trim())) {
                                if (str_differ.equals("qtickets")) {
                                    showPb();
                                    presenter.doApplyCouponcode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString(),androidDeviceId);
                                } else {
                                    showPb();
                                    presenter.doNovoApplyCouponCode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString());
                                }
                            } else {
                                showToast(getString(R.string.forgot));
                               /* edt_mail.setError(getString(R.string.forgot));
                                edt_mail.requestFocus();*/
                            }
                        } else {
                            showToast(getString(R.string.e_code));
                          /*  edt_voucher.setError(getString(R.string.e_code));
                            edt_voucher.requestFocus();*/
                        }
                    } else {
                        QTUtils.showDialogbox(BookingDetaileActivity.this, getString(R.string.a_copon_code_su));
                    }
                } else {
                    if (!TextUtils.isEmpty(edt_voucher.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edt_mail.getText().toString().trim())) {
                            if (str_differ.equals("qtickets")) {
                                showPb();
                                presenter.doApplyCouponcode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString(),androidDeviceId);
                            } else {
                                showPb();
                                presenter.doNovoApplyCouponCode(edt_mail.getText().toString().trim(), edt_voucher.getText().toString());
                            }
                        } else {
                            showToast(getString(R.string.forgot));
                          /*  edt_mail.setError(getString(R.string.forgot));
                            edt_mail.requestFocus();*/
                        }
                    } else {
                        showToast(getString(R.string.e_code));
                       /* edt_voucher.setError(getString(R.string.e_code));
                        edt_voucher.requestFocus();*/
                    }
                }
                break;
            case R.id.txt__accept_terms:
                TermsandConditionAlertDailog();
                break;


        }
    }

//    private void timer() {
//        thread = new Thread( new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(120000);
//                    Intent intent = new Intent(BookingDetaileActivity.this, DashBoardActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    finish();
//                }
//            }
//
//    });
//
//    thread.start();
//    }


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
            phoneNumberUtil = PhoneNumberUtil.createInstance(BookingDetaileActivity.this);
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
                Toast.makeText(BookingDetaileActivity.this, getResources().getString(R.string.validphonenumber), Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println(e);
        }


        return false;
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
                    btn_pay.setText(getString(R.string.pay) + " " + total_price_after_apply_copon_code);
                    if (Integer.parseInt(total_price) > str_coupon_code_amount) {
                        str_novo_total_amount = String.valueOf(Integer.parseInt(total_price) - str_coupon_code_amount);
                    } else {
                        str_novo_total_amount = "0";
                    }
                }
            } else {
                edt_voucher.setEnabled(true);
                str_voucher_code = "null";
                txt__txt_total_payble_amount.setText(total_price + " " + currency);
                btn_pay.setText(getString(R.string.pay) + " " + total_price + " " + currency);
                str_novo_total_amount = total_price;
            }
        }
    }

    private void TermsandConditionAlertDailog() {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_booking_detailes_terms_and_condition, null);
        // Build the dialog
        final Dialog customDialog = new Dialog(BookingDetaileActivity.this, R.style.MyDialogTheme);
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

    private String convertMinutesToHours(String mins) {
        int time = Integer.valueOf(mins);
        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;
        String duration = hours + " hr " + minutes + " min";
        return duration;
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
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
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
            sessionManager.setSelectedCountryCode("+" + sessionManager.getPrefix());
            edt_name.setError(null);
            edt_mail.setError(null);
            edt_phone_number.setError(null);
        }

    }

    private void timeourDilaogBox() {
        if (!((Activity) getApplicationContext()).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BookingDetaileActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.timeout));
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialog.dismiss();
                            onBackPressed();
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void onBackPressed() {
        SeatSelectionActivity.isonbackpresed = true;
        dbHelper.deleteAllCodes(sqLiteDatabase);
        if (str_differ.equals("qtickets")) {
            super.onBackPressed();
        } else {
            Intent i = new Intent(BookingDetaileActivity.this, DashBoardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
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
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.vouchers_list_row, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder,  int position) {
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
        Events.DiscountTotalMessage discountTotalMessage =
                new Events.DiscountTotalMessage(discountPrice);
        com.production.qtickets.utils.GlobalBus.getBus().post(discountTotalMessage);
    }

}

class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherAdapterViewHolder>{

    Context context;

    public VoucherAdapter(Context context){
        this.context = context;

    }

    @NonNull
    @Override
    public VoucherAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View voucherList = LayoutInflater.from(parent.getContext()).inflate(R.layout.vouchers_list_row, parent, false);
        VoucherAdapterViewHolder voucherAdapterViewHolder = new VoucherAdapterViewHolder(voucherList);

        return voucherAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAdapterViewHolder holder, int position) {

        holder.tv_voucher_codes.setText(BookingDetaileActivity.voucherArrayList.get(position).get("voucher_code"));
        holder.tv_voucher_value.setText(BookingDetaileActivity.voucherArrayList.get(position).get("voucher_value"));

    }

    @Override
    public int getItemCount() {
        return BookingDetaileActivity.voucherArrayList.size();
    }


    public class VoucherAdapterViewHolder extends RecyclerView.ViewHolder {

        CheckBox code_checkbox;
        TextView tv_voucher_codes;
        TextView tv_voucher_value;

        public VoucherAdapterViewHolder(View itemView) {
            super(itemView);

            tv_voucher_codes = (TextView) itemView.findViewById(R.id.tv_voucher_codes);
            tv_voucher_value = (TextView) itemView.findViewById(R.id.tv_voucher_value);
            code_checkbox = (CheckBox) itemView.findViewById(R.id.code_checkbox);
        }
    }
}