package com.production.qtickets.searchList;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.model.SearchItem;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.utils.TextviewRegular;
import com.production.qtickets.moviedetailes.MovieDetailePresenter;
import com.production.qtickets.moviedetailes.MovieDetaileViewPagerAdapter;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.moviedetailes.YoutubeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Harris on 04-06-2018.
 */

public class SearchListMovieDetails extends AppCompatActivity {

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
    TextviewRegular mvCat;
    @BindView(R.id.htab_tabs)
    TabLayout htabTabs;
    @BindView(R.id.htab_viewpager)
    ViewPager htabViewpager;
    @BindView(R.id.mv_laung)
    TextviewRegular mvLaung;
    @BindView(R.id.mv_IMDB)
    TextviewBold mvIMDB;
    @BindView(R.id.mv_hr)
    TextviewRegular mvHr;
    @BindView(R.id.rating)
    AppCompatRatingBar rating;
    @BindView(R.id.videoView)
    ImageView videoView;

    //Adapter
    MovieDetaileViewPagerAdapter adapter;
    //presenter
    MovieDetailePresenter presenter;

    List<SearchItem> movie_list;
    List<UserReviewModel> uList;
    //integer
    int size = 0;

    //string
    String url, movie_Id, movie_image, description;
    //integer
    int position;
    //list
    //  List<MovieModel> movie_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QTUtils.setStatusBarGradiant(SearchListMovieDetails.this);
        setContentView(R.layout.movie_details_activity);
        ButterKnife.bind(this);
        init();
    }

    //initialize the views
    private void init() {

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        //movie_list = (List<MovieModel>) bundle.getSerializable("movie_list");
        movie_list = (List<SearchItem>) bundle.getSerializable("movie_list");
        position = bundle.getInt("position");
        movie_Id = bundle.getString("id");
        movie_image = bundle.getString("image");

        tvToolbarTitle.setText(movie_list.get(position).name);

        mvName.setText(movie_list.get(position).name);
        mvSencor.setVisibility(View.INVISIBLE);
        mvRate.setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty(movie_list.get(position).genVen)) {
            mvCat.setText(movie_list.get(position).genVen);
        }

        mvHr.setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty(movie_list.get(position).language)) {
            mvLaung.setText(movie_list.get(position).language);
        }
        if (!TextUtils.isEmpty(movie_list.get(position).poster)) {
            url = movie_list.get(position).linkPath;
            Glide.with(this)
                    .load(movie_image)
                    .thumbnail(0.5f)
                    .into(videoView);
        }


        htabTabs.setupWithViewPager(htabViewpager);
        setupViewPager(htabViewpager);
        tvShare.setVisibility(View.VISIBLE);


        htabTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

                                          {
                                              @Override
                                              public void onTabSelected(TabLayout.Tab tab) {

                                              }

                                              @Override
                                              public void onTabUnselected(TabLayout.Tab tab) {

                                              }

                                              @Override
                                              public void onTabReselected(TabLayout.Tab tab) {

                                              }
                                          }

        );
        htabViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()

                                              {
                                                  @Override
                                                  public void onPageScrolled(int position, float positionOffset,
                                                                             int positionOffsetPixels) {
                                                  }

                                                  @Override
                                                  public void onPageSelected(int position) {


                                                  }

                                                  @Override
                                                  public void onPageScrollStateChanged(int state) {

                                                  }
                                              }

        );
    }

    private void setupViewPager(ViewPager viewPager) {
        /*adapter = new MovieDetaileViewPagerAdapter(getSupportFragmentManager());
        //  adapter.addFragment(new ShowTimingsFragment(), getString(R.string.show));
        adapter.addFragment(new MovieSummaryFragment(description), getString(R.string.summary));
        if (size > 0) {
            adapter.addFragment(new UserReviewsFragment(uList, movie_list.name), getString(R.string.reviews) + " " + "(" + size + ")");
        } else {
            adapter.addFragment(new UserReviewsFragment(movie_list.name), getString(R.string.reviews));
        }
        viewPager.setAdapter(adapter);*/
    }


    @OnClick({R.id.iv_back_arrow, R.id.videoView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_arrow:
                onBackPressed();
                break;
            case R.id.videoView:
                Bundle b = new Bundle();
                b.putString("url", url);
                b.putString("tittle",url);
                Intent i = new Intent(SearchListMovieDetails.this, YoutubeActivity.class);
                i.putExtras(b);
                startActivity(i);
                break;
        }
    }


}
