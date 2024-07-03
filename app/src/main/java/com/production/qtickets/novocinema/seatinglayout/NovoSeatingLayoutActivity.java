package com.production.qtickets.novocinema.seatinglayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.Arealist;
import com.production.qtickets.model.NovoBlockSeatsModel;
import com.production.qtickets.model.NovoSeatingModel;
import com.production.qtickets.model.Rowlist;
import com.production.qtickets.model.Seatlist;
import com.production.qtickets.model.TicketlistModel;
import com.production.qtickets.ticketbookingdetaile.BookingDetaileActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.production.qtickets.utils.QTUtils.showDialogbox;

/**
 * Created by Harsh on 7/13/2018.
 */
public class NovoSeatingLayoutActivity extends AppCompatActivity implements NovoSeatingContracter.View {
    //presenter
    NovoSeatingPresenter presenter;
    //string
    String selectedtickettypes, cid, session_id, cencor, movie_title, mall_name, currency, usersessionId, show_date,show_datetoview,
            duration, movie_type, movie_img_url, screenName, show_timimg, selected_ticket_cost, selectedtickettypeswithamount,showDate;
    //integer
    int no_of_tickets, original_mticket_count, totalamount;
    //list
    List<Arealist> arealistList = new ArrayList<>();
    List<Arealist> classModels;
    List<TextView> check_button;
    int index;
    double perTicketPrice;
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.tv_toolbar_location)
    TextviewBold tvToolbarLocation;
    @BindView(R.id.tv_share)
    TextviewBold tvShare;
    @BindView(R.id.edit_ticket)
    LinearLayout editTicket;
    @BindView(R.id.iv_screen_flik)
    LinearLayout ivScreenFlik;
    @BindView(R.id.grid)
    TableLayout grid;
    @BindView(R.id.tv_tickets)
    TextView tv_tickets;
    @BindView(R.id.txt_no_of_seats)
    TextView txt_no_of_seats;
    @BindView(R.id.txt_pay)
    TextView txt_pay;

    private OnSeatSelectionChangedListener mOnSeatSelectionChangedListener;
    private List<String> mSelectedSeatsList;
    private List<String> mSelectedSeatsList_ticketid;
    private List<String> mselectedseatTicketInfo;
    private List<Double> mCostList;
    List<String> imd_pre_seats = new ArrayList<>();
    List<String> imd_pre_seats_ticket_type = new ArrayList<>();
    List<String> all_seats = new ArrayList<>();
    List<String> ticket_info = new ArrayList<>();
    List<String> aval_seats = new ArrayList<>();
    List<Boolean> ischecked_seats = new ArrayList<>();
    private int tickettype = 0;
    private HashMap<String, Integer> seatTypeHashMap;
    List<Seatlist> mseat_List = new ArrayList<>();
    StringBuilder csvticket_cost = new StringBuilder();
    StringBuilder csvBuilder = new StringBuilder();
    StringBuilder csvBuilder_ticket_info = new StringBuilder();
    private static final String SEPARATOR = ",";
    String mtotal_amount;
    private AlertDialog alertDialog;
    String selected_seats;
    String screen_Type;
    String show_time, showTiming;
    String selected_ticket_info;
    String booking_info_id;
    String booking_user_session_id;
    Integer booking_total_price_value;
    String booking_fee;
    String str_seat_list;
    String str_novo_currency;
    SessionManager sessionManager;
    List<TicketlistModel> ticket_list;
    double total_amount;
    String str15, str18, strPg, str13, pg;

    //alertdialog
    private Dialog loginAlertDialog, timealertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_novo_seating_layout);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        presenter = new NovoSeatingPresenter();
        presenter.attachView(this);
        init();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void init() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedtickettypes = b.getString("selectedtickettypes");
            cid = b.getString("cid");
            session_id = b.getString("session_id");
            cencor = b.getString("cencor");
            no_of_tickets = b.getInt("no_of_tickets");

            movie_title = b.getString("movie_title");
            mall_name = b.getString("mall_name");
            duration = b.getString("duration");
            movie_type = b.getString("movie_type");
            movie_img_url = b.getString("movie_img_url");
            screenName = b.getString("screen_name");
            screen_Type = b.getString("Screen_Type");
            show_time = b.getString("show_time");
            show_datetoview = b.getString("show_date");
            selectedtickettypeswithamount = b.getString("selectedtickettypeswithamount");
            ticket_list =
                    (List<TicketlistModel>) b.getSerializable("ticketlist");
            original_mticket_count = no_of_tickets;
            total_amount = b.getDouble("total_amount");
        }
        ivScreenFlik.setVisibility(View.VISIBLE);
        editTicket.setVisibility(View.GONE);
        tvToolbarTitle.setText(movie_title);
        tvToolbarLocation.setText(mall_name);
        if (no_of_tickets > 1) {
            tv_tickets.setText(getString(R.string.no_of_seats) + "  " + String.valueOf(no_of_tickets));
        } else {
            tv_tickets.setText(getString(R.string.no_of_seat) + "  " + String.valueOf(no_of_tickets));
        }

        showPb();
        presenter.getNovoSeating(this, cid, session_id, cencor, selectedtickettypes);
        showTiming = show_time.substring(0, 2);
        if (screen_Type.contains("7 STAR") && Integer.parseInt(show_time.substring(0, 2)) < 18) {
            showpgDialogbox7star(this, "\n" + sessionManager.getAgeRistrict()/*"  For your comfort, the following minimum age limits apply to 7-Star theaters:\n"
                    + "Shows starting at 6pm onwards on Thursday to Saturday and Public holidays: 15 years and above.\n" + "Movie rating rules apply."*/ +
                    "\n \n" + ":من أجل راحتك، الحد الأدنى للسن المسموح بالدخول إلى صالات 7 نجوم" + "- في العروض التي تبدأ عند الساعة 6 مساءً من الخميس إلى السبت والعطلات" +
                    "الرسمية: 15 عامًا فما فوق." +
                    "- جميع أوقات العرض الأخرى: 7 سنوات وما فوق." +
                    "تطبق قواعد تصنيف الفيلم.");
        }else
        if (sessionManager.getCensor().equals("18+")) {
            str18 = "\n" +
                    "أنت تحاول حجز فيلم تصنيفه 18+ يمنع الدخول لمن تقل اعمارهم عن 18 ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين.\n";
            showpgDialogbox(this, sessionManager.getAgeRistrict() + "\n" + "\n" +
                    str18);
            //  showpgDialogbox(this, str18);

        } else if (sessionManager.getCensor().equals("PG 15")) {
            strPg = "\n" +
                    "\n" +
                    " أنت تحاول حجز فيلم تصنيف " + " PG 15 " + "لا يُسمح بالدخول لشخص أقل من 15 عامًا دون توجيه الوالدين. يحتفظ المشرف بالحق في الرفض دون استرداد";
            showpgDialogbox(this, sessionManager.getAgeRistrict() + "\n" + "\n" +
                    strPg);
            // showpgDialogbox(this, strPg);

        } else if (sessionManager.getCensor().equals("15+")) {
            str15 = "\n" +
                    "\n" +
                    " نت تحاول حجز فيلم تصنيفة 15+ يمنع الدخول لمن تقل اعمارهم عن 15 ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين. ";
            showpgDialogbox(this, sessionManager.getAgeRistrict() + "\n" + "\n" +
                    str15);
            // showpgDialogbox(this, str15);
        } else if (sessionManager.getCensor().equals("PG 13")) {
            str13 = "\n" +
                    " \n" +
                    "PG-13أنت تحاول حجز فيلم تصنيفه" +
                    "يسمح الدخول لمن تقل أعمارهم عن  تحت إشراف الوالدين" +
                    ".ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين ";
            showpgDialogbox(this, sessionManager.getAgeRistrict() + "\n" + "\n" +
                    str13);
        } else if (sessionManager.getCensor().equals("PG")) {
            pg = " \n" +
                    "PGأنت تحاول حجز فيلم تصنيفه الأطفال مع توجيه الوالدين" +
                    " .ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين";
            showpgDialogbox(this, sessionManager.getAgeRistrict() + "\n" + "\n" +
                    pg);

        }else{
            showpgDialogbox(this, "\n" + sessionManager.getAgeRistrict() + "\n" + "\n" + sessionManager.getCensor() + " تحت إشراف الوالدين أنت تحاول حجز فيلم تصنيفه" + sessionManager.getCensor().replaceAll("\\D+", "") + ".ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين يسمح الدخول لمن تقل أعمارهم عن");
        }
        /*showpgDialogbox(this, "\n" + sessionManager.getAgeRistrict() + "\n" + "\n" + sessionManager.getCensor() + " تحت إشراف الوالدين أنت تحاول حجز فيلم تصنيفه" + sessionManager.getCensor().replaceAll("\\D+", "") + ".ويحتفظ مشرف السينما بالحق في رفض دخول الفيلم دون إرجاع سعر التذكرة في حال مخالفة القوانين يسمح الدخول لمن تقل أعمارهم عن");*/
        // showpgDialogbox(this, "\n" + sessionManager.getAgeRistrict() + "\n" + "\n" + "أنت تحاول حجز فيلم تصنيف PG 15. لا يُسمح بالدخول لشخص أقل من 15 عامًا دون توجيه الوالدين. يحتفظ المشرف بالحق في الرفض دون استرداد." );

        mSelectedSeatsList = new ArrayList<>();
        mCostList = new ArrayList<>();
        mSelectedSeatsList_ticketid = new ArrayList<>();
        mselectedseatTicketInfo = new ArrayList<>();
        mOnSeatSelectionChangedListener = new OnSeatSelectionChangedListener() {
            @Override
            public void onSeatSelected(String seat_position_tickettype, Double mCost, String seat, String selected_ticket_info) {
                if (mSelectedSeatsList.size() < original_mticket_count) {
                    mSelectedSeatsList.add(seat_position_tickettype);
                    mSelectedSeatsList_ticketid.add(seat);
                    mselectedseatTicketInfo.add(selected_ticket_info);
                    mCostList.add(mCost);
                    double costTicket = sum(mCostList);
                    // txt_pay.setText(getResources().getString(R.string.C_pay) + "  " + costTicket + " " + currency);
                    if (mSelectedSeatsList.size() > 1) {
                        txt_no_of_seats.setText(mSelectedSeatsList_ticketid.size() + " " + getString(R.string.seats) + "\n" + Arrays.toString(mSelectedSeatsList_ticketid.toArray()));
                    } else {
                        txt_no_of_seats.setText(mSelectedSeatsList_ticketid.size() + " " + getString(R.string.Seat) + "\n" + Arrays.toString(mSelectedSeatsList_ticketid.toArray()));

                    }
                } else {
                    showDialogbox(NovoSeatingLayoutActivity.this, getString(R.string.e_no_of_tickets));
                }
            }

            @Override
            public void onSeatUnselected(String seat_position_tickettype, Double price, String seat, String selected_ticket_info) {
                mSelectedSeatsList.remove(seat_position_tickettype);
                mSelectedSeatsList_ticketid.remove(seat);
                mCostList.remove(price);
                mselectedseatTicketInfo.remove(selected_ticket_info);
                double costTicket = sum(mCostList);
//                if (costTicket == 0.0) {
//                    txt_pay.setText(getResources().getString(R.string.C_pay));
//                } else {
//                    txt_pay.setText(getResources().getString(R.string.C_pay) + "  " + costTicket + " " + currency);
//                }
                if (mSelectedSeatsList.size() > 1) {
                    //   tvShare.setText(mSelectedSeatsList.size() + " " + "TICKETS");
                    txt_no_of_seats.setText(mSelectedSeatsList_ticketid.size() + " " + getString(R.string.seats) + "\n" + Arrays.toString(mSelectedSeatsList_ticketid.toArray()));

                } else if (mSelectedSeatsList.size() == 1) {
                    //   tvShare.setText(mSelectedSeatsList.size() + " " + "TICKET");
                    txt_no_of_seats.setText(mSelectedSeatsList_ticketid.size() + " " + getString(R.string.Seat) + "\n" + Arrays.toString(mSelectedSeatsList_ticketid.toArray()));

                } else {
                    txt_no_of_seats.setText(getString(R.string.nooftickets));
                }
               /* tvShare.setText(mSelectedSeatsList.size() +" "+"TICKETS");
                txtNoOfSeats.setText(mSelectedSeatsList.size()+" "+ "Seats");*/
            }
        };
        setOnSeatSelectionChangedListener(mOnSeatSelectionChangedListener);
    }

    //normal dialogbox
    private void showpgDialogbox(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_alert_dialog_box, null);
        // Build the dialog
        loginAlertDialog = new Dialog(NovoSeatingLayoutActivity.this);
        loginAlertDialog.setContentView(customView);
        // dialog with bottom and with out padding
        TextView signupButton = loginAlertDialog.findViewById(R.id.max_4d_ok);
        TextView txt_max_4d = loginAlertDialog.findViewById(R.id.txt_max_4d);
        txt_max_4d.setText(message);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAlertDialog.dismiss();

            }

        });

        loginAlertDialog.setCancelable(true);
        loginAlertDialog.show();

    }

    private void showpgDialogbox7star(Context context, String message) {
        LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_alert_dialog_box, null);
        // Build the dialog
        timealertDialog = new Dialog(NovoSeatingLayoutActivity.this);
        timealertDialog.setContentView(customView);
        // dialog with bottom and with out padding
        TextView signupButton = timealertDialog.findViewById(R.id.max_4d_ok);
        TextView txt_max_4d = timealertDialog.findViewById(R.id.txt_max_4d);
        txt_max_4d.setText(message);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timealertDialog.dismiss();

            }

        });

        timealertDialog.setCancelable(true);
        timealertDialog.show();

    }

    @Override
    public void setNovoSeating(NovoSeatingModel novoTicketSeletionModels) {
        if (novoTicketSeletionModels.arealist != null) {
            arealistList = novoTicketSeletionModels.arealist;
            currency = novoTicketSeletionModels.currency;
            totalamount = novoTicketSeletionModels.totalAmount;
            usersessionId = novoTicketSeletionModels.userSessionId;
            txt_pay.setText(getResources().getString(R.string.C_pay) + "  " + totalamount + " " + currency);
            if (arealistList.size() > 0) {
                populateSeats(novoTicketSeletionModels);
            } else {
                finishshowDialogbox(this, getString(R.string.novosession));
            }
        } else {
//            if(novoTicketSeletionModels.maxTickets==0) {
//                finishshowDialogbox(this, getString(R.string.e_novo_seating));
//            }else {
            finishshowDialogbox(this, getString(R.string.novosession));
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
                            Intent i = new Intent(NovoSeatingLayoutActivity.this, DashBoardActivity.class);
//                            i.putExtra("position",0);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
        }
    }

    private void populateSeats(NovoSeatingModel pSeatsClassList) {
        classModels = new ArrayList<>();
        classModels = pSeatsClassList.arealist;
        index = 0;
        tickettype = 0;
        seatTypeHashMap = new HashMap<>();
        check_button = new ArrayList<>();
        for (Arealist seatClass : classModels) {
            try {
                addrowname(seatClass.ticketTypeDescription, seatClass);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected void addrowname(String ticket_type_description, final Arealist seatClass) {
        TableRow seatsRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.item_view_movie_seats_row, grid, false);
        seatsRow.setGravity(Gravity.CENTER_VERTICAL);
        seatsRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1f));
        final TextView rowNameTextView = new TextView(getApplicationContext());
        rowNameTextView.setText(ticket_type_description);
        rowNameTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rowNameTextView.setPadding(20, 20, 20, 20);
        seatsRow.addView(rowNameTextView);
        grid.addView(seatsRow);
        final List<Rowlist> rows = seatClass.rowlist;
        String cost = String.valueOf(seatClass.ticketPrice);
        String name = seatClass.areaDescription;
        String areacode = seatClass.ticketTypeCode;
        Integer ticket_qty = seatClass.selectedTicketQty;
        if (!seatTypeHashMap.containsKey(seatClass.ticketTypeCode)) {
            tickettype = tickettype + 1;
            seatTypeHashMap.put(seatClass.ticketTypeCode, tickettype);
            seatClass.setTicket_type(tickettype);
            perTicketPrice = Double.parseDouble(cost);
            for (Rowlist row : rows) {
                row.setmCost(cost);
                row.setmName(name);
                addSeatRow(row, areacode, ticket_qty, tickettype);
            }
        } else {
            seatClass.setTicket_type(seatTypeHashMap.get(seatClass.ticketTypeCode));
            perTicketPrice = Double.parseDouble(cost);
            for (Rowlist row : rows) {
                row.setmCost(cost);
                row.setmName(name);
                addSeatRow(row, areacode, ticket_qty, seatTypeHashMap.get(seatClass.ticketTypeCode));
            }
        }



    }

    protected void addSeatRow(final Rowlist pRow, String areacode, Integer ticket_qty, final Integer tickettype) {
        TableRow seatsRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.item_view_movie_seats_row, grid, false);
        seatsRow.setGravity(Gravity.CENTER_VERTICAL);
        seatsRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 2f));
        final TextView rowNameTextView = new TextView(getApplicationContext());
        rowNameTextView.setText(pRow.physicalName);
        rowNameTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rowNameTextView.setPadding(20, 20, 20, 20);
        seatsRow.addView(rowNameTextView);
        final List<Seatlist> allSeats = pRow.seatlist;
        mseat_List.addAll(allSeats);
        for (final Seatlist seat : allSeats) {
            seat.ticketqty = ticket_qty;
            seat.original_ticketqty = ticket_qty;
            ischecked_seats.add(false);
            seat.setMticket_type(tickettype);
            seat.ischecked = false;
            final TextView cbSeats = (TextView) LayoutInflater.from(this).inflate(R.layout.view_novo_movie_seat, seatsRow, false);
            cbSeats.setTag(index);
            check_button.add(cbSeats);
            index = index + 1;
            if (seat.id.equals("")) {
                all_seats.add("0");
                ticket_info.add("0");
            } else {
                all_seats.add(seat.id);
                ticket_info.add(seat.passingValue);
            }

            if (seat.status.equals("Space")) {
                cbSeats.setVisibility(View.INVISIBLE);
            } else {
                cbSeats.setVisibility(View.VISIBLE);
            }
            if (areacode.equals("")) {
                cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_booked_new));
                cbSeats.setTextColor(getResources().getColor(R.color.white));
            } else {
                if (seat.status.equals("Empty")) {
                    aval_seats.add(seat.id);
                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                    cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                    final String TicketsCost = pRow.getmCost();
                    final double mTicketsCost = Double.parseDouble(TicketsCost);
                    cbSeats.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String selected_Seat = seat.id;
                            String selected_ticket_info = seat.passingValue;
                            Integer str_original_ticket_qty = seat.original_ticketqty;
                            Integer str_ticket_type = seat.getMticket_type();
                            if (mOnSeatSelectionChangedListener != null) {
                                int position = (int) view.getTag();
                                String selected_seat_with_tickettype = str_ticket_type + "-" + position;
                                if (ischecked_seats.get(position)) {
                                    seat.ischecked = false;
                                    ischecked_seats.set(position, false);
                                } else {
                                    seat.ischecked = true;
                                    ischecked_seats.set(position, true);
                                }


                                if (ischecked_seats.get(position)) {
                                    if (mSelectedSeatsList.size() < original_mticket_count) {
                                        imd_pre_seats_ticket_type = new ArrayList<>();

                                        for (int i = 0; i < mSelectedSeatsList.size(); i++) {
                                            /*to get tickettype*/
                                            String str = mSelectedSeatsList.get(i);
                                            String kept = str.substring(0, str.indexOf("-"));
                                            if (Integer.parseInt(kept) == str_ticket_type) {
                                                imd_pre_seats_ticket_type.add(str);
                                            }
                                        }

                                        /*alredy we selected seats for ticket type and again when we are clicking on a new seat in the same
                                         * ticket type select next immedite sets by making empty of previous seats*/
                                        if (imd_pre_seats_ticket_type.size() == str_original_ticket_qty) {
                                            for (int z = 0; z < imd_pre_seats_ticket_type.size(); z++) {
                                                Log.e("imd_pre",mSelectedSeatsList.toString()+"===== "+imd_pre_seats_ticket_type.get(z));
                                                //we have two remove the selected tickettype seats not all seats
                                                if (mSelectedSeatsList.contains(imd_pre_seats_ticket_type.get(z))) {
                                                    mSelectedSeatsList.remove(imd_pre_seats_ticket_type.get(z));
                                                    imd_pre_seats.remove(imd_pre_seats_ticket_type.get(z));
                                                    String text = imd_pre_seats_ticket_type.get(z).replaceAll(".*-", "");
                                                    String final_text = text.replaceAll("-", "");
                                                    imd_pre_seats_ticket_type.set(z, final_text);
                                                    ///  mCostList.remove(z);
                                                    mSelectedSeatsList_ticketid.remove(all_seats.get(Integer.parseInt(final_text)));
                                                    mselectedseatTicketInfo.remove(ticket_info.get(Integer.parseInt(final_text)));
                                                    check_button.get(Integer.parseInt(imd_pre_seats_ticket_type.get(z))).setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                                    seat.ischecked = false;
                                                    ischecked_seats.set(Integer.parseInt(imd_pre_seats_ticket_type.get(z)), false);
                                                    check_button.get(Integer.parseInt(imd_pre_seats_ticket_type.get(z))).setTextColor(getResources().getColor(R.color.colorWhite));
                                                    imd_pre_seats_ticket_type.remove(z);
                                                }

                                            }
                                            for (int j = 0; j < mseat_List.size(); j++) {
                                                if (mseat_List.get(j).getMticket_type().equals(str_ticket_type)) {
                                                    mseat_List.get(j).ticketqty = str_original_ticket_qty;
                                                }
                                            }

                                        }

                                        Integer str_no_of_tickets = str_original_ticket_qty - imd_pre_seats_ticket_type.size();
                                        /*immedite seat selection*/
                                        setNextseatSelecting(position, mTicketsCost, str_no_of_tickets, str_ticket_type, str_original_ticket_qty);

                                    } else {
                                        imd_pre_seats_ticket_type = new ArrayList<>();
                                        for (int i = 0; i < mSelectedSeatsList.size(); i++) {
                                            String str = mSelectedSeatsList.get(i);
                                            String kept = str.substring(0, str.indexOf("-"));
                                            if (Integer.parseInt(kept) == str_ticket_type) {
                                                imd_pre_seats_ticket_type.add(mSelectedSeatsList.get(i));
                                            }
                                        }
                                        if (imd_pre_seats_ticket_type.size() == str_original_ticket_qty) {
                                            for (int z = 0; z < imd_pre_seats_ticket_type.size(); z++) {
                                                String text = imd_pre_seats_ticket_type.get(z).replaceAll(".*-", "");
                                                String final_text = text.replaceAll("-", "");
                                                check_button.get(Integer.parseInt(final_text)).setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                                seat.ischecked = false;
                                                ischecked_seats.set(Integer.parseInt(final_text), false);
                                                check_button.get(Integer.parseInt(final_text)).setTextColor(getResources().getColor(R.color.colorWhite));
                                                for (int k = 0; k < mSelectedSeatsList.size(); k++) {
                                                    if (mSelectedSeatsList.get(k).equals(imd_pre_seats_ticket_type.get(z))) {
                                                        mSelectedSeatsList.remove(imd_pre_seats_ticket_type.get(z));
                                                        imd_pre_seats.remove(imd_pre_seats_ticket_type.get(z));
                                                    }
                                                }

                                            }
                                            mSelectedSeatsList_ticketid.clear();
                                            mselectedseatTicketInfo.clear();
                                            for (int l = 0; l < mSelectedSeatsList.size(); l++) {
                                                String text = mSelectedSeatsList.get(l).replaceAll(".*-", "");
                                                String final_text = text.replaceAll("-", "");
                                                mSelectedSeatsList_ticketid.add(all_seats.get(Integer.parseInt(final_text)));
                                                mselectedseatTicketInfo.add(ticket_info.get(Integer.parseInt(final_text)));
                                            }
                                            no_of_tickets = seat.original_ticketqty;
                                            for (int j = 0; j < mseat_List.size(); j++) {
                                                if (mseat_List.get(j).getMticket_type().equals(str_ticket_type)) {
                                                    mseat_List.get(j).ticketqty = no_of_tickets;
                                                }
                                            }
                                            setNextseatSelecting(position, mTicketsCost, no_of_tickets, str_ticket_type, str_original_ticket_qty);
                                        }

                                    }
                                }
                                else {
                                    /*unselecting seats*/
                                    imd_pre_seats_ticket_type = new ArrayList<>();
                                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_empty_new));
                                    cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                                    ischecked_seats.set(position, false);
                                    for (int i = 0; i < imd_pre_seats.size(); i++) {
                                        String str = imd_pre_seats.get(i);
                                        String kept = str.substring(0, str.indexOf("-"));
                                        if (Integer.parseInt(kept) == str_ticket_type) {
                                            imd_pre_seats_ticket_type.add(imd_pre_seats.get(i));
                                        }
                                    }
                                    for (int i = 0; i < imd_pre_seats.size(); i++) {
                                        if (imd_pre_seats.get(i).equals(str_ticket_type + "-" + String.valueOf(position))) {
                                            for (int k = 0; k < imd_pre_seats_ticket_type.size(); k++) {
                                                if (imd_pre_seats.get(i).equals(imd_pre_seats_ticket_type.get(k))) {
                                                    imd_pre_seats.remove(i);
                                                    imd_pre_seats_ticket_type.remove(k);
                                                    no_of_tickets = seat.original_ticketqty - imd_pre_seats_ticket_type.size();
                                                }
                                            }

                                        }
                                    }
                                    for (int j = 0; j < mseat_List.size(); j++) {
                                        if (mseat_List.get(j).getMticket_type().equals(str_ticket_type)) {
                                            mseat_List.get(j).ticketqty = no_of_tickets;
                                        }
                                    }

                                    mOnSeatSelectionChangedListener.onSeatUnselected(selected_seat_with_tickettype, mTicketsCost, selected_Seat, selected_ticket_info);

                                }


                            }

                        }

                    });

                } else {
                    cbSeats.setBackground(getResources().getDrawable(R.drawable.ic_seat_booked_new));
                    cbSeats.setTextColor(getResources().getColor(R.color.colorWhite));
                }
            }

            cbSeats.setText(seat.id);
            seatsRow.addView(cbSeats);

        }

        grid.addView(seatsRow);
    }

    private void setNextseatSelecting(int position, double mticketcost, Integer ticketqty, Integer tickettype, Integer str_original_ticket_qty) {
        for (int i = 0; i < ticketqty; i++) {
            try {
                //immediate selection of next seats
                if (aval_seats.contains(all_seats.get(position))) {
                    check_button.get(position).setBackground(getResources().getDrawable(R.drawable.ic_seat_selected_new));
                    check_button.get(position).setTextColor(getResources().getColor(R.color.white));
                    ischecked_seats.set(position, true);
                    for (int f = 0; f < mSelectedSeatsList_ticketid.size(); f++) {
                        if (mSelectedSeatsList_ticketid.get(f).equals(all_seats.get(position))) {
                            mSelectedSeatsList_ticketid.remove(f);
                            mselectedseatTicketInfo.remove(f);
                            mSelectedSeatsList.remove(f);
                            imd_pre_seats.remove(f);
                        }
                    }
                    imd_pre_seats.add(tickettype + "-" + String.valueOf(position));
                    mOnSeatSelectionChangedListener.onSeatSelected(tickettype + "-" + String.valueOf(position), mticketcost, all_seats.get(position), ticket_info.get(position));
                    position++;
                } else {
                    //if seats with same ticket type
                    if (mseat_List.get(position).getMticket_type().equals(tickettype)) {
                        ticketqty = ticketqty + 1;
                        position++;
                    } else {
                        //to set remaining seats should get select for next click
                        imd_pre_seats_ticket_type = new ArrayList<>();
                        for (int k = 0; k < imd_pre_seats.size(); k++) {
                            String str = imd_pre_seats.get(k);
                            String kept = str.substring(0, str.indexOf("-"));
                            if (Integer.parseInt(kept) == tickettype) {
                                imd_pre_seats_ticket_type.add(imd_pre_seats.get(k));
                            }
                        }
                        no_of_tickets = str_original_ticket_qty - imd_pre_seats_ticket_type.size();
                        for (int j = 0; j < mseat_List.size(); j++) {
                            if (mseat_List.get(j).getMticket_type().equals(tickettype)) {
                                mseat_List.get(j).ticketqty = no_of_tickets;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                //when we are clicking on last seat this will work to select remaining seats
                imd_pre_seats_ticket_type = new ArrayList<>();
                for (int k = 0; k < imd_pre_seats.size(); k++) {
                    String str = imd_pre_seats.get(k);
                    String kept = str.substring(0, str.indexOf("-"));
                    if (Integer.parseInt(kept) == tickettype) {
                        imd_pre_seats_ticket_type.add(imd_pre_seats.get(k));
                    }
                }
                no_of_tickets = str_original_ticket_qty - imd_pre_seats_ticket_type.size();
                for (int j = 0; j < mseat_List.size(); j++) {
                    if (mseat_List.get(j).getMticket_type().equals(tickettype)) {
                        mseat_List.get(j).ticketqty = no_of_tickets;
                    }
                }
                e.printStackTrace();
            }

        }


    }

    @OnClick({R.id.iv_back_arrow, R.id.txt_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
            case R.id.txt_pay:
                selected_seats = "";
                selected_ticket_info = "";
                if (mSelectedSeatsList_ticketid.size() == original_mticket_count) {
                    for (String ticketid : mSelectedSeatsList_ticketid) {
                        csvBuilder.append(ticketid);
                        csvBuilder.append(SEPARATOR);
                    }
                    selected_seats = csvBuilder.toString();
                    if (selected_seats.endsWith(",")) {
                        selected_seats = selected_seats.substring(0, selected_seats.length() - 1);
                    }

                    for (String ticketinfo : mselectedseatTicketInfo) {
                        csvBuilder_ticket_info.append(ticketinfo);
                    }
                    selected_ticket_info = csvBuilder_ticket_info.toString();
                    selected_ticket_info = selected_ticket_info + "^" + selectedtickettypeswithamount;
                    String s = cid;
                    showPb();
                    presenter.doNovoBlockSeating(AppConstants.novo_country_id, cid, usersessionId, session_id, selectedtickettypes, selected_seats, selected_ticket_info);
                } else {
                    if (mSelectedSeatsList.size() == 0) {
                        showDialogbox(this, getString(R.string.e_pay_now2));
                    } else {
                        showDialogbox(this, getString(R.string.e_pay_now));
                    }
                }
                break;
        }
    }

    @Override
    public void setNovoBlockSeating(NovoBlockSeatsModel novoBlockSeatsModel) {
        List<String> cinemaClass = novoBlockSeatsModel.cinemaClass;
        show_timimg = novoBlockSeatsModel.showTime;
        show_date = novoBlockSeatsModel.showDate;
        booking_info_id = novoBlockSeatsModel.bookingInfoId;
        booking_user_session_id = novoBlockSeatsModel.userSessionID;
        booking_total_price_value = novoBlockSeatsModel.totalPriceValue;
        booking_fee = novoBlockSeatsModel.bookingFee;
        str_seat_list = novoBlockSeatsModel.seatList;
        str_novo_currency = novoBlockSeatsModel.currency;
        mtotal_amount = novoBlockSeatsModel.totalPrice;
        for (String city : cinemaClass) {
            csvticket_cost.append(city);
            csvticket_cost.append(SEPARATOR);
        }
        selected_ticket_cost = csvticket_cost.toString();
        /*remove comma at the end of text*/
        if (selected_ticket_cost.endsWith(",")) {
            selected_ticket_cost = selected_ticket_cost.substring(0, selected_ticket_cost.length() - 1);
        }
        Bundle b = new Bundle();
        b.putString("strdiff", "novo");
        b.putString("MOVIE_NAME", movie_title);
        b.putString("MOVIE_MALL", mall_name);
        b.putString("MOVIE_DATE", show_datetoview);
        b.putString("MOVIE_DATE_TOSEND", show_date);
        b.putStringArrayList("TOTAL PERSON", (ArrayList<String>) mSelectedSeatsList_ticketid);
        b.putString("TOTAL_Amount", mtotal_amount);
        b.putString("duration", duration);
        b.putString("movie_type", movie_type);
        b.putString("movie_img_url", movie_img_url);
        b.putString("tickettypes", selected_ticket_cost);
        b.putString("screen_name", screenName);
        b.putString("show_time", show_timimg);
        b.putString("session_id", session_id);
        b.putString("booking_info_id", booking_info_id);
        b.putString("userSessionID", usersessionId);
        b.putString("seat_list", str_seat_list);
        b.putString("selected_ticket_info", selected_ticket_info);
        b.putDouble("booking_total_price_value", booking_total_price_value);
        b.putString("cid", cid);
        b.putString("currency", str_novo_currency);
        b.putString("type","novo");
        b.putSerializable("ticketlist", (Serializable) ticket_list);
        Intent i = new Intent(NovoSeatingLayoutActivity.this, BookingDetaileActivity.class);
        i.putExtras(b);
        startActivity(i);
    }

    protected interface OnSeatSelectionChangedListener {
        void onSeatSelected(final String seat_position_tickettype, Double mCost, String saet, String selected_ticket_info);

        void onSeatUnselected(final String seat_position_tickettype, Double price, String seat, String selected_ticket_info);
    }


    public void setOnSeatSelectionChangedListener(final OnSeatSelectionChangedListener pOnSeatSelectionChangedListener) {
        mOnSeatSelectionChangedListener = pOnSeatSelectionChangedListener;
    }

    public double sum(List<Double> list) {
        int sum = 0;
        for (double i : list) {
            sum += i;
        }
        return sum;
    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(NovoSeatingLayoutActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(NovoSeatingLayoutActivity.this, message, Toast.LENGTH_SHORT).show();
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
            finishshowDialogbox(this, getString(R.string.novosession));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, NovoSeatingLayoutActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
