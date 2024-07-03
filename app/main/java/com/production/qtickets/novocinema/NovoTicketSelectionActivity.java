package com.production.qtickets.novocinema;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.NovoTicketSeletionModel;
import com.production.qtickets.model.TicketlistModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.moviedetailes.DateByMallAdapter;
import com.production.qtickets.novocinema.seatinglayout.NovoSeatingLayoutActivity;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harsh on 7/12/2018.
 */
public class NovoTicketSelectionActivity extends AppCompatActivity implements NovoTicketSelectionContracter.View {
    //presenter
    NovoTicketSelectionPresenter presenter;
    //ids
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.rec_ticket_type)
    RecyclerView recTicketType;
    @BindView(R.id.txt_no_of_tickets)
    TextviewBold txtNoOfTickets;
    @BindView(R.id.txt_total_amount)
    TextviewBold txtTotalAmount;
    @BindView(R.id.txt_book_now)
    TextviewBold txtBookNow;
    @BindView(R.id.tv_toolbar_location)
    TextviewBold tv_toolbar_location;
    @BindView(R.id.edit_ticket)
    LinearLayout edit_ticket;
    Integer no_of_tickets  = 0;
    //string
    private String str_movie_title, str_mall_name, cid, session_id, str_max_tickets,cencor,
            duration,movie_type,movie_img_url,screenName,screenType,showTime;
    //layoutmanager
    LinearLayoutManager rec_linear_layout_manager;
    //adapter
    NovoTicketSelectionAdapter adapter;
    //list
    List<TicketlistModel> ticketlist = new ArrayList<>();
    private AlertDialog alertDialog;
    private double total_amount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_ticket_selection);
        ButterKnife.bind(this);
        presenter = new NovoTicketSelectionPresenter();
        presenter.attachView(this);
        init();
    }

    @Override
    public void setNovoTicketSelection(NovoTicketSeletionModel novoTicketSeletionModels) {
        Integer maxtickets = novoTicketSeletionModels.maxTickets;
        if(maxtickets!=0) {
            ticketlist = novoTicketSeletionModels.ticketlist;
            if (ticketlist.size() > 0) {
                str_max_tickets = novoTicketSeletionModels.maxTickets.toString();
                adapter = new NovoTicketSelectionAdapter(NovoTicketSelectionActivity.this, ticketlist, str_max_tickets, txtNoOfTickets, txtTotalAmount, new NovoTicketSelectionAdapter.onTicketsChangedListener() {
                    @Override
                    public void ticketsCount(int noOfTickets, int noOfPerson) {
                        no_of_tickets=noOfPerson;
                    }
                });
                recTicketType.setAdapter(adapter);
            }
        }else {
            showToast("No tickets");
        }
    }

    @Override
    public void init() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            str_movie_title = b.getString("movie_title");
            str_mall_name = b.getString("mall_name");
            cid = b.getString("cid");
            session_id = b.getString("session_id");
            cencor = b.getString("cencor");
            duration = b.getString("duration");
            movie_type = b.getString("movie_type");
            movie_img_url = b.getString("movie_img_url");
            screenName = b.getString("screen_name");
            screenType = b.getString("Screen_Type");
            showTime = b.getString("show_time");
            }
        edit_ticket.setVisibility(View.GONE);
        tvToolbarTitle.setText(str_movie_title);
        tv_toolbar_location.setText(str_mall_name);
        rec_linear_layout_manager = new LinearLayoutManager(NovoTicketSelectionActivity.this);
        recTicketType.setLayoutManager(rec_linear_layout_manager);
        showPb();
        presenter.getNovoTicketSelection("2", cid, session_id);

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(NovoTicketSelectionActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(NovoTicketSelectionActivity.this, message, Toast.LENGTH_SHORT).show();
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
    public void finishshowDialogbox(final Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialog.dismiss();
                            Intent i = new Intent(NovoTicketSelectionActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
    }
    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, NovoTicketSelectionActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @OnClick({R.id.iv_back_arrow, R.id.txt_book_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
            case R.id.txt_book_now:
                if(ticketlist.size()>0) {
                    total_amount = 0;
                    String selectedtickettypes = "";
                    String selectedtickettypeswithamount = "";
//                    Integer no_of_tickets  = 0;//changed by sudhir
                    for (int i = 0; i < ticketlist.size(); i++) {
                        total_amount = total_amount + ticketlist.get(i).totalamount;
                        if (ticketlist.get(i).ticketcount != 0) {
                            if (TextUtils.isEmpty(selectedtickettypes)) {
                                selectedtickettypes = ticketlist.get(i).ticketTypeCode + "x" + ticketlist.get(i).ticketcount;
                                double sum_of_amount = Double.valueOf(ticketlist.get(i).ticketcount) * Double.valueOf(ticketlist.get(i).ticketPrice);
                                selectedtickettypeswithamount = ticketlist.get(i).ticketTypeCode+"#"+sum_of_amount+"#"+ticketlist.get(i).ticketcount;
                            } else {
                                selectedtickettypes = selectedtickettypes + "," + ticketlist.get(i).ticketTypeCode + "x" + ticketlist.get(i).ticketcount;
                                double sum_of_amount = Double.valueOf(ticketlist.get(i).ticketcount) * Double.valueOf(ticketlist.get(i).ticketPrice);
                                selectedtickettypeswithamount = selectedtickettypeswithamount + "~" +ticketlist.get(i).ticketTypeCode+"#"+sum_of_amount+"#"+ticketlist.get(i).ticketcount;
                            }
//                            no_of_tickets = no_of_tickets + ticketlist.get(i).ticketcount; //commented by sudhir

                        }
                    }
                    if(!TextUtils.isEmpty(selectedtickettypes)){
                        Bundle b = new Bundle();
                        b.putString("selectedtickettypes",selectedtickettypes);
                        b.putString("cid",cid);
                        b.putString("session_id",session_id);
                        b.putString("cencor",cencor);
                        b.putInt("no_of_tickets",no_of_tickets);
                        b.putString("movie_title",str_movie_title);
                        b.putString("mall_name",str_mall_name);
                        b.putString("duration", duration);
                        b.putString("movie_type", movie_type);
                        b.putString("movie_img_url", movie_img_url);
                        b.putString("screen_name", screenName);
                        b.putString("Screen_Type",screenType);
                        b.putString("show_time",showTime);
                        b.putString("selectedtickettypeswithamount",selectedtickettypeswithamount);
                        b.putSerializable("ticketlist", (Serializable) ticketlist);
                        b.putDouble("total_amount",total_amount);
                        Intent i = new Intent(NovoTicketSelectionActivity.this, NovoSeatingLayoutActivity.class);
                        i.putExtras(b);
                        startActivity(i);
                    }else {
                        showToast(getString(R.string.e_ticket_count));
                    }

                }
                break;
        }
    }
}
