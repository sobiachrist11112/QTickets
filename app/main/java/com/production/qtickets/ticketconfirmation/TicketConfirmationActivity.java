package com.production.qtickets.ticketconfirmation;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.TicketConfirmItem;
import com.production.qtickets.model.TicketConfirmationEventModel;
import com.production.qtickets.model.TicketConfirmationMovieModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.UserReviewModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harsh on 6/26/2018.
 */
public class TicketConfirmationActivity extends AppCompatActivity implements TicketConfirmationContracter.View {
    String order_id, rating_value, from_which_page;
    TicketConfirmationPresenter presenter;
    SessionManager sessionManager;

    /*ticketconfirmation*/
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_movie)
    ImageView imgMovie;
    @BindView(R.id.tv_movie_name)
    TextviewBold tvMovieName;
    @BindView(R.id.tv_movie_genere)
    TextviewBold tvMovieGenere;
    @BindView(R.id.tv_tickets)
    TextviewBold tvTickets;
    @BindView(R.id.txt_price)
    TextviewBold txtPrice;
    @BindView(R.id.l_sets)
    LinearLayout l_sets;
    @BindView(R.id.venuue_detailes)
    TextviewBold venuueDetailes;
    @BindView(R.id.txt_date_time)
    TextviewBold txtDateTime;
    @BindView(R.id.tv_confirmation_code)
    TextviewBold tv_confirmation_code;
    @BindView(R.id.txt_ticket)
    TextviewBold txt_ticket;
    @BindView(R.id.rel_main)
    RelativeLayout rel_main;
    @BindView(R.id.img_bar_code)
    ImageView img_bar_code;
    @BindView(R.id.txt_seat_txt)
    TextView txt_seat_txt;
    @BindView(R.id.txt_seats)
    TextView txt_seats;

    /*rating*/
    @BindView(R.id.cons_user_main_id)
    ConstraintLayout cons_user_main_id;
    @BindView(R.id.rating)
    AppCompatRatingBar rating;
    @BindView(R.id.edt_review_title)
    EditText edtReviewTitle;
    @BindView(R.id.edt_review_summary)
    EditText edtReviewSummary;
    @BindView(R.id.txt_submit)
    TextviewBold txtSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(TicketConfirmationActivity.this);
        setContentView(R.layout.activity_movie_ratings);
        ButterKnife.bind(this);
        presenter = new TicketConfirmationPresenter();
        presenter.attachView(this);
        init();

    }

    @Override
    public void setEventTicketConfirmation(List<TicketConfirmationEventModel> ticketconfirmaDetaileModels) {
        if (ticketconfirmaDetaileModels.size() > 0) {
            rel_main.setVisibility(View.VISIBLE);
            cons_user_main_id.setVisibility(View.GONE);
            Glide.with(TicketConfirmationActivity.this).load(ticketconfirmaDetaileModels.get(0).eventImageURL)
                    .thumbnail(0.5f)
                    .into(imgMovie);
            tvMovieName.setText(ticketconfirmaDetaileModels.get(0).event);
            tvMovieGenere.setVisibility(View.GONE);
            if (ticketconfirmaDetaileModels.get(0).seatsCount.equals("1")) {
                txt_ticket.setText(getString(R.string.txt_ticket));
            } else {
                txt_ticket.setText(getString(R.string.txt_tickets));
            }
            tvTickets.setText(ticketconfirmaDetaileModels.get(0).seatsCount);
            txtPrice.setText(ticketconfirmaDetaileModels.get(0).totalCost);
            l_sets.setVisibility(View.GONE);
            venuueDetailes.setText(ticketconfirmaDetaileModels.get(0).location);
            txtDateTime.setText(ticketconfirmaDetaileModels.get(0).bookedOn);
            tv_confirmation_code.setVisibility(View.VISIBLE);
            img_bar_code.setVisibility(View.GONE);
            tv_confirmation_code.setText(ticketconfirmaDetaileModels.get(0).confirmationCode);
        }
    }

    @Override
    public void setuserreviews(List<UserReviewModel> userReviewModel) {
        List<UserReviewModel> mList = userReviewModel;
        if (mList.size() > 0) {
            if (mList.get(0).status.equals("1")) {
                showToast(getString(R.string.re_success));
            } else {
                showToast(getString(R.string.re_error));
            }
        }
    }

    @Override
    public void setMovieTicketConfirmation(TicketConfirmItem bookingDetaileModels) {
        List<TicketConfirmationMovieModel> tList = bookingDetaileModels.items;
        if (tList.size() > 0) {
            rel_main.setVisibility(View.VISIBLE);
            tv_confirmation_code.setVisibility(View.GONE);
            img_bar_code.setVisibility(View.VISIBLE);
            cons_user_main_id.setVisibility(View.VISIBLE);
            Glide.with(TicketConfirmationActivity.this).load(tList.get(0).moviebannerURL)
                    .thumbnail(0.5f)
                    .into(imgMovie);
            tvMovieName.setText(tList.get(0).movie);
            tvMovieGenere.setText(tList.get(0).genre);
            if (tList.get(0).seatsCount.equals("1")) {
                txt_ticket.setText(getString(R.string.txt_ticket));
                txt_seat_txt.setText(getString(R.string.Seat));
            } else {
                txt_ticket.setText(getString(R.string.txt_tickets));
                txt_seat_txt.setText(getString(R.string.seats));
            }
            tvTickets.setText(tList.get(0).seatsCount);
            txtPrice.setText(tList.get(0).totalCost);
            venuueDetailes.setText(tList.get(0).theater);
            txtDateTime.setText(tList.get(0).showdate);
            txt_seats.setText(tList.get(0).seats);
            Glide.with(TicketConfirmationActivity.this).load(tList.get(0).barcodeURL)
                    .thumbnail(0.5f)
                    .into(img_bar_code);
            cons_user_main_id.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void init() {
        sessionManager = new SessionManager(TicketConfirmationActivity.this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            order_id = b.getString("OrderInfo");
            from_which_page = b.getString("from_which_page");
        }
        showPb();
        if (from_which_page.equals("movie")) {
            String re_order_id = order_id.replace("A", "");
            presenter.getMovieTicketConfirmation(re_order_id);
        } else {
            String re_order_id = order_id.replace("E", "");
            presenter.getEventTicketConfirmation(re_order_id);
        }
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // Do what you want
                rating_value = String.valueOf(rating);
            }
        });
    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(TicketConfirmationActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(TicketConfirmationActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, TicketConfirmationActivity.this, message);
    }

    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @OnClick({R.id.img_close, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.txt_submit:
                if (!TextUtils.isEmpty(rating_value)) {
                    if (!TextUtils.isEmpty(edtReviewTitle.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edtReviewSummary.getText().toString().trim())) {
                            showPb();
                            presenter.adduserreviews(sessionManager.getMovieId(), sessionManager.getUserId(), edtReviewSummary.getText().toString(),
                                    rating_value, edtReviewTitle.getText().toString());
                        } else {
                            edtReviewSummary.setError(getString(R.string.e_re_summ));
                            edtReviewSummary.requestFocus();
                        }
                    } else {
                        edtReviewTitle.setError(getString(R.string.e_re_title));
                        edtReviewTitle.requestFocus();
                    }
                } else {
                    showToast(getString(R.string.e_rating));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
