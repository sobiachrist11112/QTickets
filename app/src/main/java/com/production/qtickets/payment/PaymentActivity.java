package com.production.qtickets.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;*/
/*import mobi.foo.masterpass.MasterpassButton;
import mobi.foo.masterpass.MasterpassCheckout;
import mobi.foo.masterpass.com.Transaction;
import mobi.foo.masterpass.listener.CheckoutListener;
import mobi.foo.masterpass.listener.MasterpassButtonListener;*/

//21sep
public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_counter)
    TextView tv_counter;
    @BindView(R.id.btn_pay)
    TextView btn_pay;
    @BindView(R.id.visa)
    RelativeLayout visa;
    @BindView(R.id.ams)
    RelativeLayout ams;
    @BindView(R.id.doha)
    LinearLayout doha;
    @BindView(R.id.naps)
    RelativeLayout naps;


    @BindView(R.id.ipay)
    RelativeLayout ipay;
    /*    @BindView(R.id.masterpass)
        LinearLayout masterpass;*/
    @BindView(R.id.spinner_nationality)
    Spinner spinner_nationality;
    @BindView(R.id.t1)
    ImageView t1;
    @BindView(R.id.t2)
    ImageView t2;
    @BindView(R.id.t3)
    ImageView t3;
    @BindView(R.id.t4)
    ImageView t4;
    @BindView(R.id.t6)
    ImageView t6;


    @BindView(R.id.t9)
    ImageView t9;


    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.txt_visa)
    TextviewBold txtVisa;
    @BindView(R.id.txt_ame)
    TextviewBold txtAme;
    @BindView(R.id.txt_doha)
    TextviewBold txtDoha;
    @BindView(R.id.txt_naps)
    TextviewBold txtNaps;
    private int payment_Type = 0;
    String paymentMainUrl, str_differ;
    SessionManager sessionManager;
    private String type = "", cid = "";
    private String nationality, order_info, mAmount, userSessionID, email, phone_number;
    CountDownTimer yourCountDownTimer;
    private static final String APP_ID = "1000000001";
    private static final String MERCHANT_ID = "1000000010";
    private static final String APP_TOKEN = "ffKJ12H4hn5djk6sa89KJH3kj6yyK8H9hk00jKJH5aaaa";
    private static final String MOBILE_NUMBER = "123456";
    private static final String CORRELATION_ID = "1";
    String CURRENCY = "";
    String COUNTRY_NAME = "";


    /*   private MasterpassButton checkoutButton;*/
    /*    private FortCallBackManager fortCallback= null;*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        QTUtils.setStatusBarGradiant(PaymentActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        /*   fortCallback = FortCallback.Factory.create();*/
        init();

    }
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode,resultCode,data);
    }*/

    @OnClick({R.id.ipay, R.id.iv_back_arrow, R.id.visa, R.id.ams, R.id.doha, R.id.naps, R.id.paypal, R.id.btn_pay, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ipay:
                t1.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t9.setVisibility(View.VISIBLE);
                payment_Type = 7;
                break;

            case R.id.iv_back_arrow:
                getConfirmation();
                break;
            case R.id.paypal:
                Toast.makeText(this, "Clicked Paypal", Toast.LENGTH_LONG).show();
                t1.setVisibility(View.GONE);
                t6.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t9.setVisibility(View.GONE);
                payment_Type = 6;
                break;
            case R.id.visa:
              /*  txtVisa.setTextColor(getResources().getColor(R.color.buttoncolor));
                txtAme.setTextColor(getResources().getColor(R.color.colorWhite));
                txtDoha.setTextColor(getResources().getColor(R.color.colorWhite));
                txtNaps.setTextColor(getResources().getColor(R.color.colorWhite));*/
                t1.setVisibility(View.VISIBLE);
                t6.setVisibility(View.GONE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t9.setVisibility(View.GONE);
                payment_Type = 4;
                break;
            case R.id.ams:
              /*  txtAme.setTextColor(getResources().getColor(R.color.buttoncolor));
                txtDoha.setTextColor(getResources().getColor(R.color.colorWhite));
                txtNaps.setTextColor(getResources().getColor(R.color.colorWhite));
                txtVisa.setTextColor(getResources().getColor(R.color.colorWhite));*/
                t2.setVisibility(View.VISIBLE);
                t1.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                t9.setVisibility(View.GONE);
                payment_Type = 5;
                break;
            case R.id.doha:
                /*txtDoha.setTextColor(getResources().getColor(R.color.buttoncolor));
                txtNaps.setTextColor(getResources().getColor(R.color.colorWhite));
                txtVisa.setTextColor(getResources().getColor(R.color.colorWhite));
                txtAme.setTextColor(getResources().getColor(R.color.colorWhite));*/
                t3.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t4.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                t9.setVisibility(View.GONE);
                payment_Type = 1;
                break;
            case R.id.naps:
               /* txtNaps.setTextColor(getResources().getColor(R.color.buttoncolor));
                txtVisa.setTextColor(getResources().getColor(R.color.colorWhite));
                txtAme.setTextColor(getResources().getColor(R.color.colorWhite));
                txtDoha.setTextColor(getResources().getColor(R.color.colorWhite));*/
                t4.setVisibility(View.VISIBLE);
                t2.setVisibility(View.GONE);
                t3.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                t6.setVisibility(View.GONE);
                t9.setVisibility(View.GONE);
                payment_Type = 3;
                break;
            case R.id.btn_pay:
                if (!TextUtils.isEmpty(nationality)) {
                    if (payment_Type != 0) {
                        proceedPayment();
                    } else {
                        QTUtils.showDialogbox(this, getResources().getString(R.string.payment_vallid));

                    }
                } else {
                    QTUtils.showDialogbox(this, getResources().getString(R.string.nationlity));
                }
                break;
          /*  case R.id.masterpass:



                break;*/
            case R.id.cancel:
                getConfirmation();
                break;
        }
    }

    private void paypalPayment() {
    }

    private void getConfirmation() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PaymentActivity.this);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to cancel the transaction?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        Button nbutton = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.GRAY);
        alert11.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.GRAY);

    }

    private void proceedPayment() {
        yourCountDownTimer.cancel();
        if (str_differ.equals("qtickets")) {


            if (payment_Type == 7) {
                //paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+ "/Booking/IPay?bookingId=" + order_info + "&paymentType=" + mAmount + "&flag=mobile";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/Booking/IPay?bookingId=" + order_info + "&paymentType=" + "50" + "&flag=mobile";
            }


            if (payment_Type == 6) {
                //    paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/paypal/checkout/";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/paypal/checkout/";
                if (type != null && type.equals("Novo")) {
                    paymentMainUrl = paymentMainUrl +
                            "N" + order_info;
                } else if (cid != null && cid.equals("Novo")) {
                    paymentMainUrl = paymentMainUrl + "C" + order_info;
                } else if (type != null && type.equals("event")) {
                    paymentMainUrl = paymentMainUrl + "E" + order_info;
                } else {
                    paymentMainUrl = paymentMainUrl + "A" + order_info;
                }
                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 4) {
                // paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 1) {
                //    paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 5) {
                //  paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/MVCPaymentPages/Qpayment-registration-MVC.aspx?";
                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";
                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 3) {
                //    paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/Qpayment-registration1.aspx?";

//              paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/Qpayment-registration1.aspx?";
                paymentMainUrl = "https://q-tickets.com/Qpayment-registration1_test.aspx?" + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" + mAmount + "&" + "OrderName=" + "online&" + "&" + "OrderInfo=" + "IPG&" + "OrderID=" + order_info + "&" + "nationality=" + nationality + "&" + "paymenttype=" + payment_Type + "&AppSource=3";

//              paymentMainUrl = "https://www.q-tickets.com/naps/qt5test.aspx?k1=SW2K63OWAxP3r8%2flLeSWjw%3d%3d&k2=wE4VltS9bL3oJbh81isuBg%3d%3d";
                Log.v("paymentURL", "== " + paymentMainUrl);
            }

            Intent ii = new Intent(PaymentActivity.this, PaymentWebViewActivity.class);
            ii.putExtra("url", paymentMainUrl);
            ii.putExtra("booking_id", order_info.substring(1));
            startActivity(ii);


        } else {
            if (payment_Type == 7) {
                // paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/Booking/IPay?bookingId=" ;
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/Booking/IPay?bookingId=";
                if (type != null && type.equals("Novo")) {
                    paymentMainUrl = paymentMainUrl + "N" + order_info;
                } else if (cid != null && cid.equals("Novo")) {
                    paymentMainUrl = paymentMainUrl + "C" + order_info;
                } else if (type != null && type.equals("event")) {
                    paymentMainUrl = paymentMainUrl + "E" + order_info;
                } else {
                    paymentMainUrl = paymentMainUrl + "A" + order_info;
                }
                paymentMainUrl = paymentMainUrl + "&paymentType=" + mAmount + "&flag=mobile";
            }

            if (payment_Type == 6) {
                //   paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/paypal/checkout/";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/paypal/checkout/";

                if (type != null && type.equals("Novo")) {
                    paymentMainUrl = paymentMainUrl + "N" + order_info;
                } else if (cid != null && cid.equals("Novo")) {
                    paymentMainUrl = paymentMainUrl + "C" + order_info;
                } else if (type != null && type.equals("event")) {
                    paymentMainUrl = paymentMainUrl + "E" + order_info;
                } else {
                    paymentMainUrl = paymentMainUrl + "A" + order_info;
                }

                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 4 || payment_Type == 1) {
                //   paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/novo/Qpayment-registration_mvc.aspx?";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/novo/Qpayment-registration_mvc.aspx?";
                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" +
                        mAmount + "&" + "OrderName=online" + "&" + "OrderID=" + "N" + order_info + "&" + "nationality=" + nationality +
                        "&" + "paymenttype=" + payment_Type + "&AppSource=3";
                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 3) {
                //paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/Qpayment-registration1.aspx?";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/Qpayment-registration1.aspx?";
                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" +
                        mAmount + "&" + "OrderName=online" + "&" + "OrderID=" + "N" + order_info + "&" + "nationality=" + nationality +
                        "&" + "paymenttype=" + payment_Type + "&AppSource=3";
                Log.v("paymentURL", "== " + paymentMainUrl);
            } else if (payment_Type == 5) {
                // paymentMainUrl = "https://q-tickets.com/Qpayment-registration.aspx?";
                //  paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE+"/novo/Qpayment-registration_mvc.aspx?";
                paymentMainUrl = APIClient.PAYMENT_BASE_URL_QT5_FOR_MOVIE + "/novo/Qpayment-registration_mvc.aspx?";
                paymentMainUrl = paymentMainUrl + "Currency=" + sessionManager.getCountryCurrency() + "&" + "Amount=" +
                        mAmount + "&" + "OrderName=online" + "&" + "OrderID=" + "N" + order_info + "&" + "nationality=" + nationality +
                        "&" + "paymenttype=" + payment_Type + "&AppSource=3";
                Log.v("paymentURL", "== " + paymentMainUrl);
            }

            Intent ii = new Intent(PaymentActivity.this, PaymentWebViewActivity.class);
            ii.putExtra("url", paymentMainUrl);
            ii.putExtra("booking_id", order_info);
            startActivity(ii);
        }


    }

    public void init() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            order_info = b.getString("OrderInfo");
            mAmount = b.getString("Amount");
            Log.d("8Nov:", "received: " + mAmount);

            str_differ = b.getString("strdiff");
            userSessionID = b.getString("userSessionID");
            email = b.getString("email");
            phone_number = b.getString("phonenumber");
            type = b.getString("type");
            cid = b.getString("cid");

        }

//        masterpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle b = new Bundle();
//                b.putString("mAmount", mAmount);
//                b.putString("email", email);
//                b.putString("phone_number",phone_number);
//                Intent masterpassIntent = new Intent(PaymentActivity.this, MasterpassPaymentActivity.class);
//                masterpassIntent.putExtras(b);
//                startActivity(masterpassIntent);
//            }
//        });


        sessionManager = new SessionManager(this);
        CURRENCY = sessionManager.getCountryCurrency();
        COUNTRY_NAME = sessionManager.getCountryName();
       /* checkoutButton = findViewById(R.id.button_checkout);
        checkoutButton.setListener(new MasterpassButtonListener() {
            @Override
            public void onButtonClicked() {
                checkout();
            }

            @Override
            public void onFail(int reason) {
                if (reason == MasterpassCheckout.REASON_INVALID_AMOUNT) {
                    Toast.makeText(getApplicationContext(), "Enter a valid amount",
                            Toast.LENGTH_LONG).show();
                }
            }
        });*/


        if (str_differ.equals("qtickets")) {
            // doha.setVisibility(View.VISIBLE);
            doha.setVisibility(View.GONE);
        } else {
            doha.setVisibility(View.GONE);
        }


        if (COUNTRY_NAME.equals("Qatar")) {
            ipay.setVisibility(View.VISIBLE);
        } else {
            ipay.setVisibility(View.GONE);
        }

        tvToolbarTitle.setText(getResources().getString(R.string.pay_title));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.nationality_textview, getResources().getStringArray(R.array.countries_array));
        dataAdapter.setDropDownViewResource(R.layout.nationality_textview);
        spinner_nationality.setAdapter(dataAdapter);
        spinner_nationality.setOnItemSelectedListener(this);
        yourCountDownTimer = new CountDownTimer(540000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_counter.setText(String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));
            }

            @Override
            public void onFinish() {
                try {
                    Intent setIntent = new Intent(PaymentActivity.this, DashBoardActivity.class);
                    setIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(setIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        yourCountDownTimer.start();

    }

    public void onBackPressed() {
        yourCountDownTimer.cancel();
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            nationality = getResources().getStringArray(R.array.countries_array)[i];
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
