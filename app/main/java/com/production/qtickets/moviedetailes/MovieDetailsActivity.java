package com.production.qtickets.moviedetailes;


import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.MallBydateModel;
import com.production.qtickets.model.ShowDateModel;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.SearchItem;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MovieDetailsActivity extends AppCompatActivity implements MovieDetaileContracter.View {
    @BindView(R.id.iv_back_arrow)
    ImageView ivBackArrow;
    @BindView(R.id.tv_toolbar_title)
    TextviewBold tvToolbarTitle;
    @BindView(R.id.tv_share)
    TextviewBold tvShare;
    @BindView(R.id.mv_name)
    TextviewBold mvName;
    @BindView(R.id.mv_sencor)
    TextviewBold mvSencor;
    @BindView(R.id.mv_rate)
    TextviewBold mvRate;
    @BindView(R.id.mv_cat)
    TextView mvCat;
    @BindView(R.id.htab_tabs)
    TabLayout htabTabs;
    @BindView(R.id.htab_viewpager)
    ViewPager htabViewpager;
    @BindView(R.id.mv_laung)
    TextView mvLaung;
    @BindView(R.id.mv_IMDB)
    TextviewBold mvIMDB;
    @BindView(R.id.mv_hr)
    TextView mvHr;
    @BindView(R.id.rating)
    AppCompatRatingBar rating;
    @BindView(R.id.videoView)
    ImageView videoView;
    @BindView(R.id.img_vedio)
    ImageView img_vedio;
    @BindView(R.id.r1)
    RelativeLayout r1;
    @BindView(R.id.main)
    CoordinatorLayout main;
    @BindView(R.id.txt_book_now)
    TextView txt_book_now;
    @BindView(R.id.no_txt_available1)
    TextviewBold notxt;
    @BindView(R.id.c2)
    ConstraintLayout c2;


    //Adapter
    MovieDetaileViewPagerAdapter adapter;

    //integer
    int size = 0;

    //list
    // MovieModel movie_list;
    List<MovieModel> movie_list = new ArrayList<>();
    List<UserReviewModel> uList = new ArrayList<>();

    //string
    String url, description;

    //sessionmanager
    SessionManager sessionManager;

    List<SearchItem> movie_details;

    //string
    String select_cinema;

    //presenter
    MovieDetailePresenter presenter;

    List<ResolveInfo> resInfos;
    List<ResolveInfo> resInfosNew;
    List<String> packages = new ArrayList<String>();
    private String shareURL;
    String movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(MovieDetailsActivity.this);
        setContentView(R.layout.movie_details_activity);
        ButterKnife.bind(this);
        presenter = new MovieDetailePresenter();
        presenter.attachView(this);
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //initialize the views
    @Override
    public void init() {
        //cinema id for filtering the cineams based on the cinema
        sessionManager = new SessionManager(MovieDetailsActivity.this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            select_cinema = b.getString("select_cinema");
            movieId = b.getString("movie_id");
        }
       /* movieId = getIntent().getStringExtra("movie_id");*/
        /*deep linking*/
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            String share_url = data.getPath();
            share_url = share_url.replace("/Movies/MoviesListDetails/", "");
            showPb();
            presenter.getMovieDetaile(share_url);
//        } else if (sessionManager.getCountryName().equals(getString(R.string.country_dubai))) {
//            showPb();
//            presenter.getReelcinemaMovieDetaile(sessionManager.getMovieId());
        } else {
            if(movieId != null){
                showPb();
                presenter.getMovieDetaile(movieId);
            } else {
                if (!TextUtils.isEmpty(sessionManager.getMovieId())) {
                    showPb();
                    presenter.getMovieDetaile(sessionManager.getMovieId());
                }
            }

        }

        if (sessionManager.getCountryName().equals("Qatar")) {
            txt_book_now.setText(getString(R.string.book));
            txt_book_now.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_ticket, 0);
        } else {
            txt_book_now.setText(getString(R.string.watch));
            txt_book_now.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_play_button, 0);
        }


        htabTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        htabViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    //listing up the movie reviews given by the users
    @Override
    public void setusermoviereviewbymovieid(List<UserReviewModel> userReviewModel) {
        uList = userReviewModel;
        if (uList.size() > 0) {
            size = uList.size();
        }
        htabTabs.setupWithViewPager(htabViewpager);
        setupViewPager(htabViewpager);
    }

    @Override
    public void setuserreviews(List<UserReviewModel> userReviewModel) {

    }

    //set the movie details to the views in movie details page
    @Override
    public void setMovieDetaile(List<MovieModel> movieModels) {
        if (movieModels.size() > 0) {
            notxt.setVisibility(View.GONE);
            main.setVisibility(View.VISIBLE);
            r1.setVisibility(View.VISIBLE);
            c2.setVisibility(View.VISIBLE);
            htabTabs.setVisibility(View.VISIBLE);
            htabViewpager.setVisibility(View.VISIBLE);
            movie_list = movieModels;


            tvToolbarTitle.setText(movie_list.get(0).name);
            mvName.setText(movie_list.get(0).name);
            if (!TextUtils.isEmpty(movie_list.get(0).censor)) {
                mvSencor.setText(movie_list.get(0).censor);
            }
            if(TextUtils.isEmpty(movie_list.get(0).iMDBRating) || movie_list.get(0).iMDBRating.equalsIgnoreCase("N/A")|| movie_list.get(0).iMDBRating.contains("0")){
                mvRate.setVisibility(View.GONE);
                mvIMDB.setVisibility(View.GONE);
            } else {
                mvRate.setVisibility(View.VISIBLE);
                mvIMDB.setVisibility(View.VISIBLE);
                mvRate.setText(movie_list.get(0).iMDBRating + "/" + "10");
            }
           /* if (!TextUtils.isEmpty(movie_list.get(0).iMDBRating)) {
                mvRate.setVisibility(View.VISIBLE);
                mvIMDB.setVisibility(View.VISIBLE);
                mvRate.setText(movie_list.get(0).iMDBRating + "/" + "10");
            } else {
                *//*mvRate.setText("0" + "/" + "10");*//*
                mvRate.setVisibility(View.GONE);
                mvIMDB.setVisibility(View.GONE);
            }*/
            if (!TextUtils.isEmpty(movie_list.get(0).m_genre)) {
                mvCat.setText(movie_list.get(0).m_genre);
            }
            try {
                if (!TextUtils.isEmpty(movie_list.get(0).duration)) {
                    mvHr.setText(convertMinutesToHours(movie_list.get(0).duration));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            mvLaung.setText(movie_list.get(0).languageid + " " + "(" + movie_list.get(0).movieType + ")");
            if (!TextUtils.isEmpty(movie_list.get(0).user_rating)) {
                if (movie_list.get(0).user_rating.equalsIgnoreCase("0.000000")) {
                    rating.setVisibility(View.GONE);
                } else {
                    rating.setVisibility(View.VISIBLE);
                    rating.setRating(Float.parseFloat(movie_list.get(0).user_rating));
                }
            } else {
                rating.setRating(0);
            }


            if (!TextUtils.isEmpty(movie_list.get(0).trailerURL)) {
                img_vedio.setVisibility(View.VISIBLE);
            } else {
                img_vedio.setVisibility(View.GONE);
            }

            if(movie_list.get(0).ipadthumb.contains("https")){
                Glide.with(this).load(movie_list.get(0).banner)
                        .thumbnail(0.5f)
                        .into(videoView);
            }else {
                Glide.with(this).load("https://"+movie_list.get(0).banner)
                        .thumbnail(0.5f)
                        .into(videoView);

            }
            description = movie_list.get(0).description;

            tvShare.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(movie_list.get(0).ageRestrictRating) && !TextUtils.isEmpty(movie_list.get(0).censor)) {
                sessionManager.setAgeRestrict_Censor(movie_list.get(0).ageRestrictRating, movie_list.get(0).censor);
            }
            if(movieId != null){
                presenter.getusermoviereviewbymovieid(movieId);
            } else {
                presenter.getusermoviereviewbymovieid(sessionManager.getMovieId());
            }

        } else {
            main.setVisibility(View.VISIBLE);
            r1.setVisibility(View.GONE);
            c2.setVisibility(View.GONE);
            htabTabs.setVisibility(View.GONE);
            htabViewpager.setVisibility(View.GONE);
            notxt.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void setReelCinemaMovieDetails(ReelCinemaMovieListModel response) {

        main.setVisibility(View.VISIBLE);
        movie_list = response.data.value;

        if (response.result.equals(1)) {

            tvToolbarTitle.setText(movie_list.get(0).title);
            mvName.setText(movie_list.get(0).title);

            if (!TextUtils.isEmpty(movie_list.get(0).rating)) {
                mvSencor.setText(movie_list.get(0).rating);
            }

            mvHr.setText(convertMinutesToHours(String.valueOf(movie_list.get(0).runTime)));
            mvLaung.setText(movie_list.get(0).shortSynopsis);

            if (!TextUtils.isEmpty(movie_list.get(0).trailerUrl)) {
                img_vedio.setVisibility(View.VISIBLE);
            } else {
                img_vedio.setVisibility(View.GONE);
            }


        } else {
            showToast("No Data");
        }


    }

    //converting the movie duration into hours and minutes
    private String convertMinutesToHours(String mins) {
        int time = Integer.valueOf(mins);
        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;
        String duration = hours + " hr " + minutes + " min";
        return duration;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MovieDetaileViewPagerAdapter(getSupportFragmentManager());
        //  adapter.addFragment(new ShowTimingsFragment(), getString(R.string.show));
        adapter.addFragment(new MovieSummaryFragment(description, movie_list.get(0).castAndCrew), getString(R.string.summary));
        if (size > 0) {
            adapter.addFragment(new UserReviewsFragment(uList, movie_list.get(0).name),
                    getString(R.string.reviews) + " " + "(" + size + ")");
        } else {
            adapter.addFragment(new UserReviewsFragment(movie_list.get(0).name), getString(R.string.reviews));
        }
        viewPager.setAdapter(adapter);
    }


    @OnClick({R.id.iv_back_arrow, R.id.videoView, R.id.tv_share, R.id.r1})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_back_arrow:
                //onBackPressed();
                if(movieId != null){
                    Intent i = new Intent(MovieDetailsActivity.this, DashBoardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                   onBackPressed();
                }
                break;

            case R.id.videoView:
                //play the trailer of the movie
                if (!TextUtils.isEmpty(movie_list.get(0).trailerURL)) {
                    Bundle b = new Bundle();
                    b.putString("url", movie_list.get(0).trailerURL);
                    Intent i = new Intent(MovieDetailsActivity.this, YoutubeActivity.class);
                    i.putExtras(b);
                    startActivity(i);
                }
                break;

            case R.id.tv_share:
                shareProduct();
                break;

            case R.id.r1:
                //here we will filter the movies based on the country, only for qatar will display the show timings
                // other than qatar we just play the trailer
                if (sessionManager.getCountryName().equals("Qatar")) {
                    sessionManager.setMovieId(movie_list.get(0).id);
                    Bundle bundle = new Bundle();
                    bundle.putString("movie_title", movie_list.get(0).name);
                    bundle.putString("duration", movie_list.get(0).duration);
                    bundle.putString("movie_type", movie_list.get(0).movieType);
                    bundle.putString("movie_img_url", movie_list.get(0).ipadthumb);
                    bundle.putString("cencor", movie_list.get(0).censor);
                    Intent movieDetails = new Intent(MovieDetailsActivity.this, ShowTimingActivity.class);
                    movieDetails.putExtras(bundle);
                    startActivity(movieDetails);
                    break;
                } else {
                    if (!TextUtils.isEmpty(movie_list.get(0).trailerURL)) {
                        Bundle b = new Bundle();
                        b.putString("url", movie_list.get(0).trailerURL);
                        Intent i = new Intent(MovieDetailsActivity.this, YoutubeActivity.class);
                        i.putExtras(b);
                        startActivity(i);
                    } else {
                        QTUtils.showDialogbox(MovieDetailsActivity.this, getString(R.string.notrailerurl));
                    }
                    break;
                }


        }
    }

    //share the movie details and url for the movie of the q-tickets website
    private void shareProduct() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Qtickets");
            String sAux = sessionManager.getShareUrl() + movie_list.get(0).id;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share Via"));

           /* //reference
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            resInfosNew = new ArrayList<ResolveInfo>();
            resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
            resInfosNew.addAll(resInfos);
            packages.clear();
            if (!resInfos.isEmpty()) {
                System.out.println("Have package");
                int count = 0;
                for (ResolveInfo resInfo : resInfos) {
                    String packageName = resInfo.activityInfo.packageName;
                    packages.add(packageName);
                    count++;
                }
            }*/


        } catch (Exception e) {
            //e.toString();
            e.printStackTrace();
        }
    }


    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(MovieDetailsActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(MovieDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, MovieDetailsActivity.this, message);
    }


    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(movieId != null){
            Intent i = new Intent(MovieDetailsActivity.this, DashBoardActivity.class);
            startActivity(i);
            finish();
        } else {
            super.onBackPressed();
        }
    }
}




