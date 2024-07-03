package com.production.qtickets.novoticketconfirmation;

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
import com.production.qtickets.model.NovoTicketConfirmationModel;
import com.production.qtickets.model.NovoTicketDetailesModel;
import com.production.qtickets.model.NovoTicketPaymentModel;
import com.production.qtickets.model.NovoTicketPaymentStausModel;
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
 * Created by Harsh on 7/20/2018.
 */
public class NovoTicketConfirmationActivity extends AppCompatActivity implements NovoTicketConfirmationContracter.View {
    //presenter
    NovoTicketConfirmationPresenter presenter;
    //string
    String booking_id, userSessionID, rating_value,movie_img_url;
    String description, nov_bookingID,barcodeImgUrl, movieTitle, movieRating, language, cinemaName, cinemaClass, screenName, showDate, showTime, noofSeats, seatList, totalTicketPrice, bookingInfoId;
    //sessionmanager
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
        setContentView(R.layout.activity_movie_ratings);
        ButterKnife.bind(this);
        presenter = new NovoTicketConfirmationPresenter();
        presenter.attachView(this);
        init();
    }

    @Override
    public void setMovieTicketConfirmationStatus(NovoTicketConfirmationModel bookingDetaileModels) {
        String status = bookingDetaileModels.status;
        if (status.equals("Pending")) {
            showPb();
            presenter.getNovoPaymentResponse(userSessionID,sessionManager.getName(),sessionManager.getEmail(),sessionManager.getNumber(),sessionManager.getCountryCode());
        }
    }

    @Override
    public void setNovoPaymentResponse(List<NovoTicketPaymentModel> novoPaymentResponse) {
        if (novoPaymentResponse.size() > 0) {
            description = novoPaymentResponse.get(0).status.description;
            nov_bookingID = novoPaymentResponse.get(0).bookingID;
            movieTitle = novoPaymentResponse.get(0).movieTitle;
            movieRating = novoPaymentResponse.get(0).movieRating;
            language = novoPaymentResponse.get(0).language;
            cinemaName = novoPaymentResponse.get(0).cinemaName;
            cinemaClass = novoPaymentResponse.get(0).cinemaClass;
            screenName = novoPaymentResponse.get(0).screenName;
            showDate = novoPaymentResponse.get(0).showDate;
            showTime = novoPaymentResponse.get(0).showTime;
            noofSeats = novoPaymentResponse.get(0).noofSeats;
            seatList = novoPaymentResponse.get(0).seatList;
            totalTicketPrice = novoPaymentResponse.get(0).totalTicketPrice;
            bookingInfoId = novoPaymentResponse.get(0).bookingInfoId;
            barcodeImgUrl= novoPaymentResponse.get(0).barcodeImgUrl;
            if(!TextUtils.isEmpty(nov_bookingID)){
                showPb();
             presenter.do_qtickets_novo_confirmation(nov_bookingID,screenName,language,movieRating,booking_id,cinemaClass,
                     barcodeImgUrl);
            }
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
    public void set_qtickets_novo_confirmation(NovoTicketPaymentStausModel novoTicketPaymentModel) {
        if(novoTicketPaymentModel.Status.equals("success")){
          showPb();
          presenter.do_qtickets_novo_ticket_detailes(booking_id);
        }

    }

    @Override
    public void set_qtickets_novo_ticket_detailes(NovoTicketDetailesModel novo_ticket_detailes) {
        if(!TextUtils.isEmpty(novo_ticket_detailes.ccode)) {
            rel_main.setVisibility(View.VISIBLE);
            tv_confirmation_code.setVisibility(View.GONE);
            img_bar_code.setVisibility(View.VISIBLE);
            cons_user_main_id.setVisibility(View.VISIBLE);
            Glide.with(NovoTicketConfirmationActivity.this).load(novo_ticket_detailes.thumb)
                    .thumbnail(0.5f)
                    .into(imgMovie);
            tvMovieName.setText(novo_ticket_detailes.movieName);
            tvMovieGenere.setVisibility(View.GONE);
            // tvMovieGenere.setText(tList.get(0).genre);
            if (noofSeats.equals(novo_ticket_detailes.noOfTickets)) {
                txt_ticket.setText(getString(R.string.txt_ticket));
                txt_seat_txt.setText(getString(R.string.Seat));
            } else {
                txt_ticket.setText(getString(R.string.txt_tickets));
                txt_seat_txt.setText(getString(R.string.seats));
            }
            tvTickets.setText(novo_ticket_detailes.noOfTickets);
            txtPrice.setText(novo_ticket_detailes.amount);
            venuueDetailes.setText(novo_ticket_detailes.cinemaName);
            txtDateTime.setText(novo_ticket_detailes.sdate);
            txt_seats.setText(novo_ticket_detailes.showTime);
            Glide.with(NovoTicketConfirmationActivity.this).load(novo_ticket_detailes.qrCodeImg)
                    .thumbnail(0.5f)
                    .into(img_bar_code);
            cons_user_main_id.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void init() {
        sessionManager = new SessionManager(NovoTicketConfirmationActivity.this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            movie_img_url = b.getString("movie_img_url");
            booking_id = b.getString("booking_id");
            userSessionID = b.getString("userSessionID");
        }
        showPb();
        presenter.getMovieTicketConfirmationStatus(booking_id);
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
        QTUtils.showProgressDialog(NovoTicketConfirmationActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(NovoTicketConfirmationActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, NovoTicketConfirmationActivity.this, message);
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
