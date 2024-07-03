package com.production.qtickets.moviedetailes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.MallBydateModel;
import com.production.qtickets.model.ShowDateModel;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.model.MovieModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class UserReviewsFragment extends Fragment implements MovieDetaileContracter.View {
    //id
    @BindView(R.id.img_pen)
    ImageView imgPen;
    @BindView(R.id.rec_user_reviews)
    RecyclerView recUserReviews;
    Unbinder unbinder;
    //add user review alert dialog box ids
    TextView txt_movi_name, txt_submit;
    EditText edt_review_title, edt_review_summary;
    RatingBar ratingBar;
    Dialog custom_dailog;
    //presenter
    MovieDetailePresenter presenter;
    //layoutmanager
    LinearLayoutManager mLayoutManager;
    //sessionmanager
    SessionManager sessionManager;
    //adapter
    UserReviewAdapter adapter;
    //list
    List<UserReviewModel> uList = new ArrayList<>();
    //string
    String movie_name, rating_value;

    @SuppressLint("ValidFragment")
    public UserReviewsFragment(List<UserReviewModel> uList, String movie_name) {
        this.uList = uList;
        this.movie_name = movie_name;
    }

    @SuppressLint("ValidFragment")
    public UserReviewsFragment(String movie_name) {
        this.movie_name = movie_name;
    }

    @SuppressLint("ValidFragment")
    public UserReviewsFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_reviews_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new MovieDetailePresenter();
        presenter.attachView(this);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_pen)
    public void onViewClicked() {
        if(!TextUtils.isEmpty(sessionManager.getUserId())) {
            addReview();
        }else {
          QTUtils.LoginAlertDailog(getActivity(),getString(R.string.alert));
        }
    }

    @Override
    public void setshowDates(ShowDateModel showDateModels) {

    }

    @Override
    public void setmallbydatesQt(MallBydateModel mallBydateModel) {

    }

    @Override
    public void setmallbydatesFlick(MallBydateModel mallBydateModel) {

    }

    @Override
    public void setmallbydatesNovo(MallBydateModel mallBydateModel) {

    }

    @Override
    public void setusermoviereviewbymovieid(List<UserReviewModel> userReviewModel) {


    }

    @Override
    public void setuserreviews(List<UserReviewModel> userReviewModel) {
        List<UserReviewModel> mList = userReviewModel;
        if(mList.size()>0){
            if(mList.get(0).status.equals("1")) {
                showToast(getString(R.string.re_success));
                custom_dailog.dismiss();
            }else {
                showToast(getString(R.string.re_error));
                }
        }

    }

    @Override
    public void setMovieDetaile(List<MovieModel> movieModels) {

    }

    @Override
    public void setReelCinemaMovieDetails(ReelCinemaMovieListModel response) {

    }

    // initialize the views
    @Override
    public void init() {
        sessionManager = new SessionManager(getActivity());
        if (uList.size() > 0) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recUserReviews.setLayoutManager(mLayoutManager);
            adapter = new UserReviewAdapter(uList, getActivity());
            recUserReviews.setAdapter(adapter);
        }

    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(getActivity(), true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
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
        } else  {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
    }

    @Override
    public void onDestroy() {
       QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    // add the reviews to the server
    private void addReview() {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.activity_add_user_reviews, null);
        // Build the dialog
        custom_dailog = new Dialog(getActivity());
        custom_dailog.setContentView(customView);
        Window window = custom_dailog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        txt_submit = custom_dailog.findViewById(R.id.txt_submit);
        txt_movi_name = custom_dailog.findViewById(R.id.txt_movie_name);
        txt_movi_name.setText(movie_name);
        edt_review_title = custom_dailog.findViewById(R.id.edt_review_title);
        edt_review_summary = custom_dailog.findViewById(R.id.edt_review_summary);
        ratingBar = custom_dailog.findViewById(R.id.rating);
       // ratingBar.setRating(1);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // Do what you want
                rating_value = String.valueOf(rating);
            }
        });
        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(rating_value)) {
                    if (!TextUtils.isEmpty(edt_review_title.getText().toString().trim())) {
                        if (!TextUtils.isEmpty(edt_review_summary.getText().toString().trim())) {
                            showPb();
                            presenter.adduserreviews(sessionManager.getMovieId(), sessionManager.getUserId(), edt_review_summary.getText().toString(),
                                    rating_value, edt_review_title.getText().toString());
                        } else {
                            edt_review_summary.setError(getString(R.string.e_re_summ));
                        }
                    } else {
                        edt_review_title.setError(getString(R.string.e_re_title));
                    }
                } else {
                    showToast(getString(R.string.e_rating));
                }
            }
        });
        custom_dailog.show();

    }
}
