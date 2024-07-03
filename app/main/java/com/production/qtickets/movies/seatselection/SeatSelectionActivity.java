package com.production.qtickets.movies.seatselection;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.production.qtickets.R;
import com.production.qtickets.model.ClassModel;
import com.production.qtickets.model.Row;
import com.production.qtickets.model.SeatClassModel;
import com.production.qtickets.model.SeatModel;
import com.production.qtickets.model.ShowDetailModel;
import com.production.qtickets.ticketbookingdetaile.BookingDetaileActivity;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeatSelectionActivity extends AppCompatActivity implements SeatSelectionContracter.View,
        AdapterView.OnItemClickListener {

    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.grid2)
    TableLayout grid2;
    @BindView(R.id.grid)
    TableLayout mSeatsTableLayout;
    @BindView(R.id.txt_no_of_seats)
    TextView txtNoOfSeats;
    @BindView(R.id.txt_pay)
    TextView txtPay;
    SeatSelectionPresenter presenter;
    @BindView(R.id.tv_toolbar_location)
    TextView tvToolbarLocation;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.edit_ticket)
    LinearLayout editTicket;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.showTimes)
    ConstraintLayout showTimes;
    @BindView(R.id.l1)
    LinearLayout l1;
    @BindView(R.id.iv_screen_flik)
    LinearLayout ivScreenFlik;
    @BindView(R.id.iv_screen_roxy)
    LinearLayout iv_screen_roxy;
    @BindView(R.id.wrapper_proceedBooking)
    ConstraintLayout wrapperProceedBooking;
    private OnSeatSelectionChangedListener mOnSeatSelectionChangedListener;
    private List<String> mSelectedSeatsList = new ArrayList<>();
    private List<Double> mCostList = new ArrayList<>();
    private int seatCount = 0;
    //alertdialog
    private Dialog loginAlertDialog;
    GridLayoutManager mLayoutManager;
    EditTicketAdapter editTicketAdapter;
    private String original_mticket_count, mMovie_name, mShow_ID, mMall_Name, mTicket_count, show_timimg, show_date, duration, movie_type,
            movie_img_url, screen_name, show_time, theater_id, screen_id;
    private List<ShowDetailModel> timeList = new ArrayList<>();
    SeatTimingsAdpter adapter;
    LinearLayoutManager gridLayoutManager;
    List<ShowDetailModel> avalibletimingList = new ArrayList<>();
    List<ClassModel> classModels = new ArrayList<>();
    SessionManager sessionManager;
    double perTicketPrice;
    int shot;
    public static boolean isonbackpresed = false;
    Drawable selectorSeatDrawable;
    List<TextView> check_button;
    int index;
    List<String> all_seats = new ArrayList<>();
    List<String> aval_seats = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<String> is_family = new ArrayList<>();
    List<String> imd_pre_seats = new ArrayList<>();
    List<Boolean> ischecked_seats = new ArrayList<>();
    private AlertDialog alertDialog;
    String str15,str18,strPg,str13,pg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_movie_seating);
        ButterKnife.bind(this);
        presenter = new SeatSelectionPresenter();
        presenter.attachView(SeatSelectionActivity.this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mMovie_name = b.getString("movie_title");
            mShow_ID = b.getString("show_id");
            mMall_Name = b.getString("mall_name");
            mTicket_count = b.getString("ticket_count");
            original_mticket_count = mTicket_count;
            shot = b.getInt("show_timimg");
            show_date = b.getString("show_date");
            timeList = (List<ShowDetailModel>) b.getSerializable("show_list");
            duration = b.getString("duration");
            movie_type = b.getString("movie_type");
            movie_img_url = b.getString("movie_img_url");
            screen_name = b.getString("screen_name");
            show_time = b.getString("show_time");
            theater_id = b.getString("theater_id");
            screen_id = b.getString("screen_id");
            //getAvailableTimings(timeList);
        }
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_back_arrow, R.id.txt_pay, R.id.edit_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
            case R.id.txt_pay:
                if (mSelectedSeatsList.size() == Integer.parseInt(original_mticket_count)) {
                    proccedPayment();
                } else {
                    if (mSelectedSeatsList.size() == 0) {
                        QTUtils.showDialogbox(this, getString(R.string.e_pay_now2));
                    } else {
                        QTUtils.showDialogbox(this, getString(R.string.e_pay_now));
                    }
                }
                break;
            case R.id.edit_ticket:
                editTicketPopup();
                break;
        }
    }

    private void proccedPayment() {
        Intent intent;
        double mMoviesCost = Double.valueOf(00);

        for (int i = 0; i < mCostList.size(); i++) {
            mMoviesCost = mMoviesCost + mCostList.get(i).doubleValue();
        }
        if (mSelectedSeatsList.isEmpty()) {
            QTUtils.showDialogbox(this, getResources().getString(R.string.seat_empty));

        } else {
            if (mSelectedSeatsList.isEmpty()) {
                QTUtils.showDialogbox(this, getResources().getString(R.string.seat_empty));
            } else {
                intent = new Intent(this, BookingDetaileActivity.class);
                intent.putExtra("MOVIE_NAME", mMovie_name);
                intent.putExtra("MOVIE_MALL", mMall_Name);
                intent.putExtra("MOVIE_DATE", show_date);
                intent.putExtra("MOVIE_SHOW_TIME_ID", mShow_ID);
                intent.putExtra("TOTAL PERSON", (ArrayList) mSelectedSeatsList);
                intent.putExtra("TOTAL Amount", mMoviesCost);
                intent.putExtra("duration", duration);
                intent.putExtra("movie_type", movie_type);
                intent.putExtra("movie_img_url", movie_img_url);
                intent.putExtra("PER_TICKET_PRICE", perTicketPrice);
                intent.putExtra("screen_name", screen_name);
                intent.putExtra("show_time", show_time);
                intent.putExtra("theater_id", theater_id);
                intent.putExtra("strdiff", "qtickets");
                startActivity(intent);
            }
        }

    }

    private void editTicketPopup() {
        LayoutInflater li = LayoutInflater.from(SeatSelectionActivity.this);
        View popupDialog = li.inflate(R.layout.movie_seat_ticketedit_layout, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SeatSelectionActivity.this);
        alertDialogBuilder.setView(popupDialog);
        loginAlertDialog = alertDialogBuilder.create();
        loginAlertDialog.setCancelable(true);
        loginAlertDialog.setCanceledOnTouchOutside(true);
        loginAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageView close = popupDialog.findViewById(R.id.close);
        RecyclerView recyclerview = popupDialog.findViewById(R.id.recyclerview);

        //show dates
        int spanCount = 6;
        mLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerview.setLayoutManager(mLayoutManager);

        editTicketAdapter = new EditTicketAdapter(getResources().getStringArray(R.array.ticket_arrays), this);
        recyclerview.setAdapter(editTicketAdapter);
        editTicketAdapter.setOnItemClickListener(SeatSelectionActivity.this);
        TextView procced = popupDialog.findViewById(R.id.procced);
        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTicket_count.equals("1")) {
                    tvShare.setText(mTicket_count + " " + getString(R.string.txt_ticket));
                } else {
                    tvShare.setText(mTicket_count + " " + getString(R.string.txt_tickets));
                }
                loginAlertDialog.dismiss();
                classModels.clear();
                mSeatsTableLayout.removeAllViews();
                txtPay.setText(getString(R.string.C_pay));
                txtNoOfSeats.setText(getString(R.string.nooftickets));
                mSelectedSeatsList.clear();
                grid2.removeAllViews();
                mTicket_count = original_mticket_count;
                all_seats = new ArrayList<>();
                aval_seats = new ArrayList<>();
                price = new ArrayList<>();
                imd_pre_seats = new ArrayList<>();
                is_family = new ArrayList<>();
                mCostList = new ArrayList<>();
                ischecked_seats = new ArrayList<>();
                showPb();
                presenter.getSeatSelection(SeatSelectionActivity.this, mShow_ID);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAlertDialog.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(true);
        loginAlertDialog.setCancelable(true);
        loginAlertDialog.show();

    }

    @Override
    public void setSeatselection(SeatModel seatModel) {
        try {
            if (seatModel.response.status.equalsIgnoreCase("true")) {
                populateSeats(seatModel.response.classes);
            } else {
                showToast(getString(R.string.showexpired));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getAvailableTimings(List<ShowDetailModel> articleList) {
        for (ShowDetailModel timings : articleList) {
            if (timings.showStatus.equalsIgnoreCase("1") || timings.showStatus.equalsIgnoreCase("3")) {
                avalibletimingList.add(timings);

            }
        }
    }

    @Override
    public void init() {
//        if (theater_id.equals("11")) {
//            ivScreenFlik.setVisibility(View.GONE);
//            iv_screen_roxy.setVisibility(View.VISIBLE);
//        } else {
//            ivScreenFlik.setVisibility(View.GONE);
//            iv_screen_roxy.setVisibility(View.VISIBLE);
//        }
        sessionManager = new SessionManager(this);
        tvToolbarTitle.setText(mMovie_name);
        tvToolbarLocation.setText(mMall_Name);
        if (mTicket_count.equals("1")) {
            tvShare.setText(mTicket_count + " " + getString(R.string.txt_ticket));
        } else {
            tvShare.setText(mTicket_count + " " + getString(R.string.txt_tickets));
        }
        date.setText(show_date);
        gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter = new SeatTimingsAdpter(this, timeList, shot);
        recyclerview.setAdapter(adapter);
        gridLayoutManager.scrollToPositionWithOffset(shot, 20);
        adapter.setOnItemClickListener(SeatSelectionActivity.this);
        showPb();
        presenter.getSeatSelection(this, mShow_ID);
        if (screen_id.equals("58")) {
            showDialogbox(this,  getString(R.string.vipseat) + "\n" + "\n" + "الرجاء العلم بأن كل تذكرة لكبار الشخصيات في هذه الشاشات السينمائية تحديدا تشمل كرسيين لشخصين فقط");
        } else {
            if(sessionManager.getCensor().equals("18+")){
                str18="\n" +
                        "أنت تحاول حجز فيلم تصنيفه 18+ يمنع الدخول لمن تقل اعمارهم عن 18 ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين.\n";
                showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" +
                       str18);

            } else if(sessionManager.getCensor().equals("PG 15")) {
                strPg = "\n" +
                        "\n" +
                         " أنت تحاول حجز فيلم تصنيف "+ " PG 15 "+"لا يُسمح بالدخول لشخص أقل من 15 عامًا دون توجيه الوالدين. يحتفظ المشرف بالحق في الرفض دون استرداد";
                showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" +
                        strPg);

            } else if(sessionManager.getCensor().equals("15+")){
                str15 = "\n" +
                        "\n" +
                        " نت تحاول حجز فيلم تصنيفة 15+ يمنع الدخول لمن تقل اعمارهم عن 15 ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين. ";
                showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" +
                        str15);
            } else if(sessionManager.getCensor().equals("PG 13")){
                str13 = "\n" +
                                                " \n" +
                        "PG-13أنت تحاول حجز فيلم تصنيفه" +
                        "يسمح الدخول لمن تقل أعمارهم عن  تحت إشراف الوالدين" +
                        ".ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين ";
                showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" +
                        str13);
            } else if(sessionManager.getCensor().equals("PG")){
                pg = " \n" +
                      "PGأنت تحاول حجز فيلم تصنيفه الأطفال مع توجيه الوالدين" +
                        " .ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين";
                showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" +
                        pg);

            }else{
                showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" + sessionManager.getCensor() + " تحت إشراف الوالدين أنت تحاول حجز فيلم تصنيفه" + sessionManager.getCensor().replaceAll("\\D+", "") + ".ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين يسمح الدخول لمن تقل أعمارهم عن");
            }
            /*showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" + sessionManager.getCensor() + " تحت إشراف الوالدين أنت تحاول حجز فيلم تصنيفه" + sessionManager.getCensor().replaceAll("\\D+", "") + ".ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين يسمح الدخول لمن تقل أعمارهم عن");*/
            /*showDialogbox(this,  sessionManager.getAgeRistrict() + "\n" + "\n" + sessionManager.getCensor() + "\n" +
                    "أنت تحاول حجز فيلم تصنيفة 15+ يمنع الدخول لمن تقل اعمارهم عن 15 ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين.\n");
       */ }
        mSelectedSeatsList = new ArrayList<>();
        mCostList = new ArrayList<>();
        mOnSeatSelectionChangedListener = new OnSeatSelectionChangedListener() {
            @Override
            public void onSeatSelected(String seat, Double mCost) {
                if (mSelectedSeatsList.size() < Integer.parseInt(original_mticket_count)) {
                    mSelectedSeatsList.add(seat);
                    mCostList.add(mCost);
                    double costTicket = sum(mCostList);
                    if (costTicket == 0.0) {
                        txtPay.setText(getResources().getString(R.string.C_pay));
                    } else {
                        perTicketPrice = mCost;
                        txtPay.setText(getResources().getString(R.string.C_pay) + "  " + costTicket + " " + sessionManager.getCountryCurrency());
                    }
                    if (mSelectedSeatsList.size() > 1) {
                        //  tvShare.setText(mSelectedSeatsList.size() + " " + "TICKETS");
                        txtNoOfSeats.setText(mSelectedSeatsList.size() + " " + getString(R.string.seats) + "\n" + Arrays.toString(mSelectedSeatsList.toArray()));

                    } else if (mSelectedSeatsList.size() == 1) {
                        //    tvShare.setText(mSelectedSeatsList.size() + " " + "TICKET");
                        txtNoOfSeats.setText(mSelectedSeatsList.size() + " " + getString(R.string.Seat) + "\n" + Arrays.toString(mSelectedSeatsList.toArray()));

                    } else {
                        txtNoOfSeats.setText(getString(R.string.nooftickets));
                    }
                } else {
                    QTUtils.showDialogbox(SeatSelectionActivity.this, getString(R.string.e_no_of_tickets));
                }
            }

            @Override
            public void onSeatUnselected(String seat, Double price) {
                mSelectedSeatsList.remove(seat);
                mCostList.remove(price);
                double costTicket = sum(mCostList);
                if (costTicket == 0.0) {
                    txtPay.setText(getResources().getString(R.string.C_pay));
                } else {
                    perTicketPrice = price;
                    txtPay.setText(getResources().getString(R.string.C_pay) + "  " + costTicket + " " + sessionManager.getCountryCurrency());
                }
                if (mSelectedSeatsList.size() > 1) {
                    //   tvShare.setText(mSelectedSeatsList.size() + " " + "TICKETS");
                    txtNoOfSeats.setText(mSelectedSeatsList.size() + " " + getString(R.string.seats) + "\n" + Arrays.toString(mSelectedSeatsList.toArray()));

                } else if (mSelectedSeatsList.size() == 1) {
                    //   tvShare.setText(mSelectedSeatsList.size() + " " + "TICKET");
                    txtNoOfSeats.setText(mSelectedSeatsList.size() + " " + getString(R.string.Seat) + "\n" + Arrays.toString(mSelectedSeatsList.toArray()));

                } else {
                    txtNoOfSeats.setText(getString(R.string.nooftickets));
                }
               /* tvShare.setText(mSelectedSeatsList.size() +" "+"TICKETS");
                txtNoOfSeats.setText(mSelectedSeatsList.size()+" "+ "Seats");*/
            }
        };
        setOnSeatSelectionChangedListener(mOnSeatSelectionChangedListener);
       /* recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((TextView) recyclerview.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.txt_time)).setBackground(getResources().getDrawable(R.drawable.seat_time_background));


            }
        }));*/

    }

    //normal dialogbox
    private void showDialogbox(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_alert_dialog_box, null);
        // Build the dialog
        loginAlertDialog = new Dialog(SeatSelectionActivity.this);
        loginAlertDialog.setContentView(customView);
        // dialog with bottom and with out padding
        TextView signupButton = loginAlertDialog.findViewById(R.id.max_4d_ok);
        TextView txt_max_4d = loginAlertDialog.findViewById(R.id.txt_max_4d);
        txt_max_4d.setText(message);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAlertDialog.dismiss();
                if (screen_name.equals("screen 6")) {
                    max_4dDialogbox();
                }
            }

        });

        loginAlertDialog.setCancelable(true);
        loginAlertDialog.show();

    }

    //max4d dialogbox
    private void max_4dDialogbox() {
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.max_4d_dialog_box, null);
        // Build the dialog
        loginAlertDialog = new Dialog(SeatSelectionActivity.this, R.style.MyDialogTheme);
        loginAlertDialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = loginAlertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        TextView signupButton = loginAlertDialog.findViewById(R.id.max_4d_ok);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAlertDialog.dismiss();
            }
        });

        loginAlertDialog.setCancelable(true);
        loginAlertDialog.show();
    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    public double sum(List<Double> list) {
        int sum = 0;
        for (double i : list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(SeatSelectionActivity.this, true);
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
        QTUtils.showAlertDialogbox(object1, object3, SeatSelectionActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    private void populateSeats(SeatClassModel pSeatsClassList) {
        mSeatsTableLayout.removeAllViews();
        txtPay.setText(getString(R.string.C_pay));
        txtNoOfSeats.setText(getString(R.string.nooftickets));
        mSelectedSeatsList.clear();
        grid2.removeAllViews();
        mTicket_count = original_mticket_count;
        all_seats = new ArrayList<>();
        aval_seats = new ArrayList<>();
        price = new ArrayList<>();
        imd_pre_seats = new ArrayList<>();
        is_family = new ArrayList<>();
        mCostList = new ArrayList<>();
        ischecked_seats = new ArrayList<>();
        classModels = new ArrayList<>();
        classModels = pSeatsClassList.classModel;
        index = 0;
        check_button = new ArrayList<>();
        for (ClassModel seatClass : classModels) {
            try {

                if (seatClass.name.equalsIgnoreCase("balcony")) {
                    final List<Row> rows = seatClass.row;
                    String cost = seatClass.cost;
                    String name = seatClass.name;
                    perTicketPrice = Double.parseDouble(cost);
                    for (Row row : rows) {
                        row.setmCost(cost);
                        row.setmName(name);
                        addBalconySeatRow(row);
                    }
                }

                if (seatClass.name.equalsIgnoreCase("")) {
                    final List<Row> rows = seatClass.row;
                    String cost = seatClass.cost;
                    String name = seatClass.name;
                    perTicketPrice = Double.parseDouble(cost);
                    for (Row row : rows) {
                        row.setmCost(cost);
                        row.setmName(name);
                        addSeatRow(row, screen_id);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected void addSeatRow(final Row pRow, final String screenId) {
        String ss = screenId;
        final TableRow seatsRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.item_view_movie_seats_row,
                mSeatsTableLayout, false);
        seatsRow.setGravity(Gravity.CENTER_VERTICAL);
        seatsRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT, 1f));
        seatsRow.removeAllViewsInLayout();
        final TextView rowNameTextView = new TextView(getApplicationContext());
        rowNameTextView.setText(pRow.letter);
        rowNameTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rowNameTextView.setPadding(8, 8, 8, 8);
        seatsRow.addView(rowNameTextView);
        if (pRow.isInitialGangway.equalsIgnoreCase("True")) {
            addGangwaySeats(Integer.parseInt(pRow.isInitialGangwayCount), seatsRow);
        }

        // final TextView cbSeatClass=new TextView(this);
        final List<String> allSeats = Arrays.asList(pRow.allSeats.split(","));
        all_seats.addAll(allSeats);
        String previouslyAddedSeat = "";
        for (int i = 0; i < allSeats.size(); i++) {
            is_family.add(pRow.isFamily);
        }
        for (final String seat : allSeats) {
            ischecked_seats.add(false);
            if (pRow.isGangway.equalsIgnoreCase("True")) {
                final List<String> gangwaySeats = Arrays.asList(pRow.gangwaySeats.split(","));
                if (gangwaySeats.contains(previouslyAddedSeat)) {
                    final String[] integerStrings = pRow.gangwayCounts.split(",");
                    final int[] bm = new int[integerStrings.length];
                    for (int i = 0; i < integerStrings.length; i++) {
                        bm[i] = Integer.parseInt(integerStrings[i]);
                    }
                    addGangwaySeats(bm[gangwaySeats.indexOf(previouslyAddedSeat)], seatsRow);
                }

            }
            final TextView cbSeats = (TextView) LayoutInflater.from(this).inflate(R.layout.view_movie_seat, seatsRow, false);
            cbSeats.setTag(index);
            check_button.add(cbSeats);
            index = index + 1;
            if (screenId.equals("58")) {
                selectorSeatDrawable = ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_normal_vipscntype);
            } else {
                selectorSeatDrawable = ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_normal);
            }


            if (pRow.isFamily.equalsIgnoreCase("1")) {
                if (screenId.equals("58")) {
                    selectorSeatDrawable = ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_family_vipscntype);
                } else {
                    selectorSeatDrawable = ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_family);
                }

            }
            final List<String> availableSeats = Arrays.asList(pRow.availableseats.split(","));
            final List<String> availableSeatsPrice = Arrays.asList(pRow.getmCost());
            aval_seats.addAll(availableSeats);
            price.addAll(availableSeatsPrice);
            if (!availableSeats.contains(seat)) {
                cbSeats.setEnabled(false);
                cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                //   cbSeats.setOnCheckedChangeListener(null);
            } else {
                final String TicketsCost = pRow.getmCost();
                final double mTicketsCost = Double.parseDouble(TicketsCost);
                cbSeats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnSeatSelectionChangedListener != null) {
                            int position = (int) view.getTag();
                            if (ischecked_seats.get(position)) {
                                ischecked_seats.set(position, false);
                            } else {
                                ischecked_seats.set(position, true);
                            }

                            if (ischecked_seats.get(position)) {
                                if (mSelectedSeatsList.size() < Integer.parseInt(original_mticket_count)) {
                                    if (seatCount < 10) {
                                        seatCount++;
                                        cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                                        if (screenId.equals("58")) {
                                            cbSeats.setBackground(getResources().getDrawable(R.drawable.couch_selection));
                                        } else {
                                            cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_selected_new));
                                        }
                                        setNextseatSelecting(position, mTicketsCost);
                                        if (pRow.isFamily.equalsIgnoreCase("1")) {
                                            QTUtils.showDialogbox(SeatSelectionActivity.this, getString(R.string.warning) + "\n" + "\n" + getString(R.string.w_family_seat));
                                        }
                                    } else {

                                        ischecked_seats.set(position, false);
                                        cbSeats.setTextColor(getResources().getColor(R.color.white));
                                        if (screenId.equals("58")) {
                                            cbSeats.setBackground(getResources().getDrawable(R.drawable.vip_available));
                                        } else {
                                            if (pRow.isFamily.equalsIgnoreCase("1")) {
                                                cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                                            } else {
                                                cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                            }
                                        }
                                    }

                                } else {
                                    /*alredy you have been selected tickets and again when u r clicking on new seat and for immediate selection*/
                                    for (int i = 0; i < imd_pre_seats.size(); i++) {
                                        if (!imd_pre_seats.contains(String.valueOf(position))) {
                                            if (screenId.equals("58")) {
                                                cbSeats.setBackground(getResources().getDrawable(R.drawable.vip_available));
                                            } else {
                                                if (pRow.isFamily.equalsIgnoreCase("1")) {
                                                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                                                } else {
                                                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                                }
                                            }

                                            ischecked_seats.set(Integer.parseInt(imd_pre_seats.get(i)), false);
                                            check_button.get(Integer.parseInt(imd_pre_seats.get(i))).setTextColor(getResources().getColor(R.color.colorWhite));
                                            setNextseatSelecting(position, mTicketsCost);
                                            if (pRow.isFamily.equalsIgnoreCase("1")) {
                                                QTUtils.showDialogbox(SeatSelectionActivity.this, getString(R.string.warning) + "\n" + "\n" + getString(R.string.w_family_seat));
                                            }
                                        }
                                    }
                                }
                            } else {
                                seatCount--;
                                if (screenId.equals("58")) {
                                    cbSeats.setBackground(getResources().getDrawable(R.drawable.vip_available));
                                } else {
                                    if (pRow.isFamily.equalsIgnoreCase("1")) {
                                        cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                                    } else {
                                        cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                    }
                                }
                                cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                                for (int i = 0; i < imd_pre_seats.size(); i++) {
                                    if (imd_pre_seats.get(i).equals(String.valueOf(position))) {
                                        imd_pre_seats.remove(i);
                                        mTicket_count = String.valueOf(Integer.parseInt(original_mticket_count) - imd_pre_seats.size());
                                    }
                                }
                                mOnSeatSelectionChangedListener.onSeatUnselected(seat, mTicketsCost);
                            }
                        }
                    }
                });

            }
            cbSeats.setBackground(selectorSeatDrawable);
            String seatNumber = seat.substring(1);
            cbSeats.setText(seatNumber);
            seatsRow.addView(cbSeats);
            previouslyAddedSeat = seat;
        }

        mSeatsTableLayout.addView(seatsRow);
    }

    private void setNextseatSelecting(int position, double mticketcost) {
        if (imd_pre_seats.size() == Integer.parseInt(original_mticket_count)) {
            for (int x = 0; x < imd_pre_seats.size(); x++) {
                mCostList.clear();
                mSelectedSeatsList.clear();
                if (screen_id.equals("58")) {
                    check_button.get(Integer.parseInt(imd_pre_seats.get(x))).setBackground(getResources().getDrawable(R.drawable.vip_available));
                } else {
                    if (is_family.get(Integer.parseInt(imd_pre_seats.get(x))).equals("1")) {
                        check_button.get(Integer.parseInt(imd_pre_seats.get(x))).setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                    } else {
                        check_button.get(Integer.parseInt(imd_pre_seats.get(x))).setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                    }
                }
                ischecked_seats.set(Integer.parseInt(imd_pre_seats.get(x)), false);
                check_button.get(Integer.parseInt(imd_pre_seats.get(x))).setTextColor(getResources().getColor(R.color.colorWhite));
            }
            imd_pre_seats = new ArrayList<>();
            mCostList.clear();
            mSelectedSeatsList.clear();
            mTicket_count = original_mticket_count;

        }
        if (imd_pre_seats.size() > 1) {
            mTicket_count = String.valueOf(Integer.parseInt(original_mticket_count) - imd_pre_seats.size());
        }
        for (int i = 0; i < Integer.parseInt(mTicket_count); i++) {
            try {
                if (aval_seats.contains(all_seats.get(position))) {
                    if (screen_id.equals("58")) {
                        check_button.get(position).setBackground(getResources().getDrawable(R.drawable.couch_selection));
                        mticketcost=Double.parseDouble(price.get(position));
                    } else {
                        check_button.get(position).setBackground(getResources().getDrawable(R.drawable.ic_seat_selected_new));
                        mticketcost=Double.parseDouble(price.get(position));
                    }

                    check_button.get(position).setTextColor(getResources().getColor(R.color.colorWhite));
                    ischecked_seats.set(position, true);
                    for (int f = 0; f < mSelectedSeatsList.size(); f++) {
                        if (mSelectedSeatsList.get(f).equals(all_seats.get(position))) {
                            mSelectedSeatsList.remove(f);
                            imd_pre_seats.remove(f);
                            mCostList.remove(f);
                        }
                    }
                    imd_pre_seats.add(String.valueOf(position));
                    mOnSeatSelectionChangedListener.onSeatSelected(all_seats.get(position), mticketcost);
                    position++;

                } else {
                    position++;
                    mTicket_count = String.valueOf(Integer.parseInt(mTicket_count) + 1);
                    //  mTicket_count = String.valueOf(Integer.parseInt(original_mticket_count) - imd_pre_seats.size());
                }
            } catch (Exception e) {
                mTicket_count = String.valueOf(Integer.parseInt(original_mticket_count) - imd_pre_seats.size());
                e.printStackTrace();
            }

        }


    }


    protected void addBalconySeatRow(final Row pRow) {
        grid2.setVisibility(View.VISIBLE);
        final TableRow seatsRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.item_view_movie_seats_row, mSeatsTableLayout, false);
        seatsRow.setGravity(Gravity.CENTER_VERTICAL);
        seatsRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1f));
        seatsRow.removeAllViewsInLayout();
        final TextView rowNameTextView = new TextView(getApplicationContext());
        rowNameTextView.setText(pRow.letter);
        rowNameTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rowNameTextView.setPadding(8, 8, 8, 8);
        seatsRow.addView(rowNameTextView);
        if (pRow.isInitialGangway.equalsIgnoreCase("True")) {
            addGangwaySeats(Integer.parseInt(pRow.isInitialGangwayCount), seatsRow);
        }

        // final TextView cbSeatClass=new TextView(this);

        final List<String> allSeats = Arrays.asList(pRow.allSeats.split(","));
        all_seats.addAll(allSeats);
        String previouslyAddedSeat = "";
        for (int i = 0; i < allSeats.size(); i++) {
            is_family.add(pRow.isFamily);
        }
        for (final String seat : allSeats) {
            ischecked_seats.add(false);
            if (pRow.isGangway.equalsIgnoreCase("True")) {
                final List<String> gangwaySeats = Arrays.asList(pRow.gangwaySeats.split(","));
                if (gangwaySeats.contains(previouslyAddedSeat)) {
                    final String[] integerStrings = pRow.gangwayCounts.split(",");
                    final int[] bm = new int[integerStrings.length];
                    for (int i = 0; i < integerStrings.length; i++) {
                        bm[i] = Integer.parseInt(integerStrings[i]);
                    }
                    addGangwaySeats(bm[gangwaySeats.indexOf(previouslyAddedSeat)], seatsRow);
                }

            }
            final TextView cbSeats = (TextView) LayoutInflater.from(this).inflate(R.layout.view_movie_seat, seatsRow, false);
            cbSeats.setTag(index);
            check_button.add(cbSeats);
            index = index + 1;
            Drawable selectorSeatDrawable = ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_normal);
            if (pRow.isFamily.equalsIgnoreCase("1")) {
                selectorSeatDrawable = ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_family);
            }
            final List<String> availableSeats = Arrays.asList(pRow.availableseats.split(","));
            final List<String> availableprice = Arrays.asList(pRow.getmCost());
            aval_seats.addAll(availableSeats);
            price.addAll(availableprice);
            if (!availableSeats.contains(seat)) {
                cbSeats.setEnabled(false);
                cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                //   cbSeats.setOnCheckedChangeListener(null);
            } else {
                final String TicketsCost = pRow.getmCost();
                final double mTicketsCost = Double.parseDouble(TicketsCost);
                cbSeats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnSeatSelectionChangedListener != null) {
                            int position = (int) view.getTag();
                            if (ischecked_seats.get(position)) {
                                ischecked_seats.set(position, false);
                            } else {
                                ischecked_seats.set(position, true);
                            }

                            if (ischecked_seats.get(position)) {
                                if (mSelectedSeatsList.size() < Integer.parseInt(original_mticket_count)) {
                                    if (seatCount < 10) {
                                        seatCount++;
                                        cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                                        cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_selected_new));
                                        setNextseatSelecting(position, mTicketsCost);
                                        if (pRow.isFamily.equalsIgnoreCase("1")) {
                                            QTUtils.showDialogbox(SeatSelectionActivity.this, getString(R.string.warning) + "\n" + "\n" + getString(R.string.w_family_seat));
                                        }
                                    } else {

                                        ischecked_seats.set(position, false);
                                        cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                                        if (pRow.isFamily.equalsIgnoreCase("1")) {
                                            cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                                        } else {
                                            cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                        }
                                    }

                                } else {
                                    for (int i = 0; i < imd_pre_seats.size(); i++) {
                                        if (!imd_pre_seats.contains(String.valueOf(position))) {
                                            if (is_family.get(Integer.parseInt(imd_pre_seats.get(i))).equals("1")) {
                                                check_button.get(Integer.parseInt(imd_pre_seats.get(i))).setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                                            } else {
                                                check_button.get(Integer.parseInt(imd_pre_seats.get(i))).setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                            }

                                            ischecked_seats.set(Integer.parseInt(imd_pre_seats.get(i)), false);
                                            check_button.get(Integer.parseInt(imd_pre_seats.get(i))).setTextColor(getResources().getColor(R.color.colorWhite));
                                            setNextseatSelecting(position, mTicketsCost);
                                            if (pRow.isFamily.equalsIgnoreCase("1")) {
                                                QTUtils.showDialogbox(SeatSelectionActivity.this, getString(R.string.warning) + "\n" + "\n" + getString(R.string.w_family_seat));
                                            }
                                        }
                                    }
                                }

                            } else {
                                seatCount--;
                                if (pRow.isFamily.equalsIgnoreCase("1")) {
                                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_family_new));
                                } else {
                                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                }
                                cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                                for (int i = 0; i < imd_pre_seats.size(); i++) {
                                    if (imd_pre_seats.get(i).equals(String.valueOf(position))) {
                                        imd_pre_seats.remove(i);
                                        mTicket_count = String.valueOf(Integer.parseInt(original_mticket_count) - imd_pre_seats.size());
                                    }
                                }
                                mOnSeatSelectionChangedListener.onSeatUnselected(seat, mTicketsCost);
                            }
                        }
                    }
                });
            }
            cbSeats.setBackground(selectorSeatDrawable);
            String seatNumber = seat.substring(1);
            cbSeats.setText(seatNumber);
            seatsRow.addView(cbSeats);
            previouslyAddedSeat = seat;
        }

        grid2.addView(seatsRow);
    }


    private void addGangwaySeats(final int gangwayCount, final TableRow seatsRow) {
        for (int i = 0; i < gangwayCount; i++) {
            TextView cbSeats = (TextView) LayoutInflater.from(this).inflate(R.layout.view_movie_seat, seatsRow, false);
            cbSeats.setBackground(ContextCompat.getDrawable(this, R.drawable.selector_movie_seat_normal));
            cbSeats.setEnabled(false);
            cbSeats.setVisibility(View.INVISIBLE);
            seatsRow.addView(cbSeats);
        }
    }


    public void setOnSeatSelectionChangedListener(final OnSeatSelectionChangedListener pOnSeatSelectionChangedListener) {
        mOnSeatSelectionChangedListener = pOnSeatSelectionChangedListener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            int id = view.getId();
            if (id == R.id.c1) {
                screen_id = timeList.get(i).screenId;
                mShow_ID = timeList.get(i).showId;
                show_time = timeList.get(i).showTime;
                classModels.clear();
                all_seats = new ArrayList<>();
                aval_seats = new ArrayList<>();
                price = new ArrayList<>();
                imd_pre_seats = new ArrayList<>();
                mCostList = new ArrayList<>();
                is_family = new ArrayList<>();
                ischecked_seats = new ArrayList<>();
                mSeatsTableLayout.removeAllViews();
                grid2.removeAllViews();
                mSelectedSeatsList.clear();
                txtPay.setText(getResources().getString(R.string.C_pay));
                txtNoOfSeats.setText(getString(R.string.nooftickets));
                mTicket_count = original_mticket_count;
                showPb();
                presenter.getSeatSelection(SeatSelectionActivity.this, mShow_ID);
            } else if (id == R.id.c2) {
                mTicket_count = getResources().getStringArray(R.array.ticket_arrays)[i];
                original_mticket_count = mTicket_count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected interface OnSeatSelectionChangedListener {
        void onSeatSelected(final String seat, Double mCost);

        void onSeatUnselected(final String seat, Double price);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isonbackpresed) {
            classModels = new ArrayList<>();
            all_seats = new ArrayList<>();
            aval_seats = new ArrayList<>();
            imd_pre_seats = new ArrayList<>();
            is_family = new ArrayList<>();
            mSeatsTableLayout.removeAllViews();
            grid2.removeAllViews();
            mSelectedSeatsList.clear();
            mCostList = new ArrayList<>();
            txtPay.setText(getResources().getString(R.string.C_pay));
            txtNoOfSeats.setText(getString(R.string.nooftickets));
            mTicket_count = original_mticket_count;
            if (QTUtils.progressDialog != null) {
                QTUtils.progressDialog.dismiss();
            }
            showPb();
            presenter.getSeatSelection(SeatSelectionActivity.this, mShow_ID);
            isonbackpresed = false;

        }
    }
}