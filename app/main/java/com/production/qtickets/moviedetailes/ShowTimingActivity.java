package com.production.qtickets.moviedetailes;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.DateModel;
import com.production.qtickets.model.ItemModel;
import com.production.qtickets.model.MallBydateModel;
import com.production.qtickets.model.ShowDateModel;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.model.MovieModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Harsh on 5/31/2018.
 */
public class ShowTimingActivity extends AppCompatActivity implements MovieDetaileContracter.View, AdapterView.OnItemClickListener {
    //list
    ArrayList<DateModel> dates = new ArrayList<>();
    List<DateModel> dList;
    List<ItemModel> mList = new ArrayList<>();
    List<ItemModel> fMList = new ArrayList<>();
    List<ItemModel> sel_cinema_mList = new ArrayList<>();
    ArrayList<String> e_date = new ArrayList<>();

    //adapter
    DatesAdapter datesAdapter;
    DateByMallAdapter datebymallAdapter;

    @BindView(R.id.tv_date)
    TextviewBold tv;
    @BindView(R.id.recycler_dates)
    RecyclerView recyclerDates;
    @BindView(R.id.recycler_malls)
    RecyclerView recyclerMalls;
    @BindView(R.id.iv_decrease_count)
    ImageView ivDecreaseCount;
    @BindView(R.id.tv_ticket_count)
    TextView tvTicketCount;
    @BindView(R.id.iv_increase_count)
    ImageView ivIncreaseCount;
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.cons_date)
    ConstraintLayout c_date;
    @BindView(R.id.tx_no_shows)
    TextView tx_no_shows;
    //presenter
    MovieDetailePresenter presenter;

    //layout manager
    RecyclerView.LayoutManager mLayoutManager, mallLayoutmanager;


    //string
    private String id, str_selected_date, movie_title, duration, movie_type, movie_img_url, flick_url, cencor;

    //int
    int ticketCount = 0;

    //sessionmanager
    SessionManager sessionManager;

    LinearLayout linear;
    @BindView(R.id.progress)
    ProgressBar progressDialog;
    @BindView(R.id.tvLoading)
    TextView tvLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(ShowTimingActivity.this);
        setContentView(R.layout.show_timings_fragment);
        ButterKnife.bind(this);
        presenter = new MovieDetailePresenter();
        presenter.attachView(this);
        init();
    }

    // show dates are inportant, here we will display the dates for next 7 days
    // we will enable the only available dates by changing the colors
    @Override
    public void setshowDates(ShowDateModel showDateModels) {
        c_date.setVisibility(View.VISIBLE);
        tx_no_shows.setVisibility(View.GONE);
        tx_no_shows.setText(getString(R.string.noshows));
        /*date should star with Api first date*/
        Calendar date = null;
        dList = showDateModels.dates;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (dList.size() > 0) {
            String mdate = dList.get(0).date;
            Date d_date = null;
            try {
                d_date = format.parse(mdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date = Calendar.getInstance();
            date.setTime(d_date);

        } else {
            date = Calendar.getInstance();
        }
        /*getting seven dates from current date*/
        dates = new ArrayList<>();
        DateModel dateModel = null;
        for (int i = 0; i < 7; i++) {
            dateModel = new DateModel(format.format(date.getTime()));
            date.add(Calendar.DATE, 1);
            System.out.println("date :" + format.format(date.getTime()));
            dates.add(dateModel);
        }
        if (dList.size() > 0) {
            sendmessage(dList);
        }
        for (int x = 0; x < dates.size(); x++) {
            String static_date = dates.get(x).date;
            for (int y = 0; y < dList.size(); y++) {
                String Api_date = dList.get(y).date;
                if (static_date.equals(Api_date)) {
                    e_date.add(static_date);
                }
            }
        }
        /*if list is */
        if (e_date.size() != dates.size()) {
            for (int i = e_date.size(); i < dates.size(); i++) {
                e_date.add("6");
            }
        }
        /*depending on size set adapter values*/
        if (dList.size() < 7) {
            datesAdapter.addstaticValuesAdapter(dates, e_date);
        } else {
            datesAdapter.addValuesAdapter(dList, e_date);
        }
        if (dList.size() > 0) {
            str_selected_date = dList.get(0).date;
            /*qtickets api call*/
            if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
                getMallbydatesQt();
            } else if (sessionManager.getSelectedCinema().equals("Flik Cinema")) {
                getMallbydatesFlick();
            } else if (sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinema")) {
                getMallbydatesNovo();
            } else if (!(sessionManager.getSelectedCinema().equals("Flik Cinema")) && !(sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinema"))) {
                getMallbydatesQt();
            }

        } else {
            tx_no_shows.setText(getString(R.string.noshows));
            c_date.setVisibility(View.GONE);
            tx_no_shows.setVisibility(View.VISIBLE);
        }
    }

    synchronized public void getMallbydatesQt() {
        showPb();
        presenter.getMallbydatesQt("0", "0", "0", str_selected_date, id);
    }

    synchronized public void getMallbydatesFlick() {
        if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
            progressDialog.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.VISIBLE);
            tvLoading.setText("Flik Cinema Loading...");
            presenter.getMallbydatesFlick("0", "0", "0", str_selected_date, id);
        }else {
            showPb();
            presenter.getMallbydatesFlick("0", "0", "0", str_selected_date, id);
        }
    }

    synchronized public void getMallbydatesNovo() {
        if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
            progressDialog.setVisibility(View.VISIBLE);
            tvLoading.setVisibility(View.VISIBLE);
            tvLoading.setText("Novo Cinema Loading...");
            presenter.getMallbydatesNovo("0", "0", "0", str_selected_date, id);
        }else {
            showPb();
            presenter.getMallbydatesNovo("0", "0", "0", str_selected_date, id);
        }
    }


    @Override
    public void setmallbydatesQt(MallBydateModel mallBydateModel) {
        /*qtickets*/
        dismissPb();
        sel_cinema_mList = new ArrayList<>();
        mList = mallBydateModel.items;
        fMList.addAll(mList);
        flick_url = mallBydateModel.shareURL;
        /*novo user name and password */
        AppConstants.user_name = mallBydateModel.un;
        AppConstants.password = mallBydateModel.pw;
        if (fMList.size() > 0) {
            c_date.setVisibility(View.VISIBLE);
            tx_no_shows.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
                for (int i = 0; i < mList.size(); i++) {
                    if (sessionManager.getSelectedCinema().equalsIgnoreCase(mList.get(i).cinemaName)) {
                        sel_cinema_mList.add(mList.get(i));
                    }
                }
                if(sel_cinema_mList.size() > 0) {
                    datebymallAdapter = new DateByMallAdapter(ShowTimingActivity.this, sel_cinema_mList, movie_title,
                            tvTicketCount.getText().toString(), duration, movie_type, movie_img_url, flick_url, cencor);
                    recyclerMalls.setAdapter(datebymallAdapter);
                }else{
                    c_date.setVisibility(View.GONE);
                    tx_no_shows.setVisibility(View.VISIBLE);
                }
            } else {
                datebymallAdapter.addValuesAdapter(fMList);
            }
        } else {
            if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
                c_date.setVisibility(View.GONE);
            }else {
                c_date.setVisibility(View.GONE);
                tx_no_shows.setVisibility(View.VISIBLE);
            }
        }
        if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
            getMallbydatesFlick();
        }

    }

    @Override
    public void setmallbydatesFlick(MallBydateModel mallBydateModel) {
        if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
            progressDialog.setVisibility(View.INVISIBLE);
            tvLoading.setVisibility(View.GONE);
            tvLoading.setText("Flik Cinema Loading...");
        }else {
            dismissPb();
        }
        mList = mallBydateModel.items;
        fMList.addAll(mList);
        /*flick*/
        if (fMList.size() > 0) {
            c_date.setVisibility(View.VISIBLE);
            tx_no_shows.setVisibility(View.GONE);
            datebymallAdapter.addValuesAdapter(fMList);
        } else {
            if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
                c_date.setVisibility(View.GONE);
            }else {
                c_date.setVisibility(View.GONE);
                 tx_no_shows.setVisibility(View.VISIBLE);
            }
        }
        if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
            getMallbydatesNovo();
        }
    }

    @Override
    public void setmallbydatesNovo(MallBydateModel mallBydateModel) {
        if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
            progressDialog.setVisibility(View.INVISIBLE);
            tvLoading.setVisibility(View.GONE);
            tvLoading.setText("Novo Cinema Loading...");
        }else {
            dismissPb();
        }
        mList = mallBydateModel.items;
        fMList.addAll(mList);
        /*novo*/
        if (fMList.size() > 0) {
            c_date.setVisibility(View.VISIBLE);
            tx_no_shows.setVisibility(View.GONE);
            datebymallAdapter.addValuesAdapter(fMList);
        } else {
            c_date.setVisibility(View.GONE);
            tx_no_shows.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void setusermoviereviewbymovieid(List<UserReviewModel> userReviewModel) {

    }

    @Override
    public void setuserreviews(List<UserReviewModel> userReviewModel) {

    }

    @Override
    public void setMovieDetaile(List<MovieModel> movieModels) {

    }

    @Override
    public void setReelCinemaMovieDetails(ReelCinemaMovieListModel response) {

    }

    public void sendmessage(List<DateModel> list) {
        Events.ActivityAdapterMessageList activityFragmentMessageEvent =
                new Events.ActivityAdapterMessageList(list);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

    @Override
    public void init() {
        sessionManager = new SessionManager(ShowTimingActivity.this);
        showPb();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            movie_title = b.getString("movie_title");
            duration = b.getString("duration");
            movie_type = b.getString("movie_type");
            movie_img_url = b.getString("movie_img_url");
            cencor = b.getString("cencor");
            tvToolbarTitle.setText(movie_title);
        }
        id = sessionManager.getMovieId();
        presenter.getshowDates(id,sessionManager.getCountryName().toLowerCase());
        SimpleDateFormat format1 = new SimpleDateFormat("MMM yyyy");
        Calendar date1 = Calendar.getInstance();
        tv.setText(format1.format(date1.getTime()));


        linear = findViewById(R.id.linear);
        if (sessionManager.getSelectedCinema().equals("Flik Cinema")) {
            linear.setVisibility(View.GONE);
        } else if (sessionManager.getSelectedCinema().equals("Novo Cinema")||sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinemas")) {
            linear.setVisibility(View.GONE);
        } else if (sessionManager.getSelectedCinema().equals("NOVO Cinema(01 Mall)")) {
            linear.setVisibility(View.GONE);
        } else {
            linear.setVisibility(View.VISIBLE);
        }


        mLayoutManager = new LinearLayoutManager(ShowTimingActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDates.setLayoutManager(mLayoutManager);
        datesAdapter = new DatesAdapter(dates, ShowTimingActivity.this);
        recyclerDates.setAdapter(datesAdapter);
        datesAdapter.setOnItemClickListener(this);
        //malls
        mallLayoutmanager = new LinearLayoutManager(ShowTimingActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerMalls.setLayoutManager(mallLayoutmanager);

        /*malls by date*/
        datebymallAdapter = new DateByMallAdapter(ShowTimingActivity.this, mList, movie_title,
                tvTicketCount.getText().toString(), duration, movie_type, movie_img_url, flick_url, cencor);
        recyclerMalls.setAdapter(datebymallAdapter);

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(ShowTimingActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(ShowTimingActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, ShowTimingActivity.this, message);
    }


    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.detach();
        presenter = new MovieDetailePresenter();
        presenter.attachView(this);
        fMList = new ArrayList<>();
        if (dList.size() <= 7) {
            str_selected_date = dates.get(i).date;
            if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
                getMallbydatesQt();
            } else if (sessionManager.getSelectedCinema().equals("Flik Cinema")) {
                getMallbydatesFlick();
            } else if (sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinema")||sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinemas")) {
                getMallbydatesNovo();
            } else if (!(sessionManager.getSelectedCinema().equals("Flik Cinema")) && !(sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinema")||sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinemas"))) {
                getMallbydatesQt();
            }
        } else {
            // sendmessage(dList);
            str_selected_date = dList.get(i).date;
            if (TextUtils.isEmpty(sessionManager.getSelectedCinema())) {
                getMallbydatesQt();
            } else if (sessionManager.getSelectedCinema().equals("Flik Cinema")) {
                getMallbydatesFlick();
            } else if (sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinema")||sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinemas")) {
                getMallbydatesNovo();
            } else if (!(sessionManager.getSelectedCinema().equals("Flik Cinema")) && !(sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinema")||sessionManager.getSelectedCinema().equalsIgnoreCase("Novo Cinemas"))) {
                getMallbydatesQt();
            }
        }


    }


    @OnClick({R.id.iv_decrease_count, R.id.iv_increase_count, R.id.iv_back_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_decrease_count:
                //here we are doing the operations for decreasing the tickets count
                if (ticketCount > 0) {
                    ticketCount--;
                    Log.v("count ", "== " + ticketCount);
                    String tdCount = String.valueOf(ticketCount);
                    tvTicketCount.setText(tdCount);
                    sendMessageToAdapter(tvTicketCount.getText().toString());

                }
                break;
            case R.id.iv_increase_count:
                //here we are doing the operations for increasing the tickets count
                if (ticketCount < 10) {
                    ticketCount++;
                    Log.v("count ", "== " + ticketCount);
                    String tiCount = String.valueOf(ticketCount);
                    tvTicketCount.setText(tiCount);
                    sendMessageToAdapter(tvTicketCount.getText().toString());
                } else {
                    Toast.makeText(this, "Maximum 10 tickets are allowed to book", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
        }
    }

    public void sendMessageToAdapter(String ticket_count) {
        Events.FragmentActivityMessage activityFragmentMessageEvent =
                new Events.FragmentActivityMessage(ticket_count);
        GlobalBus.getBus().post(activityFragmentMessageEvent);
    }

}


