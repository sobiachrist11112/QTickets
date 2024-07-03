package com.production.qtickets.moviedetailes;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.MallBydateModel;
import com.production.qtickets.model.ShowDateModel;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;


/**
 * Created by Harsh on 5/25/2018.
 */
public class MovieDetaileContracter {
    //interfaces for dates, malls, user reviews

    interface View extends BaseView {
        void setshowDates(final ShowDateModel showDateModels);
        void setmallbydatesQt(final MallBydateModel mallBydateModel);
        void setmallbydatesFlick(final MallBydateModel mallBydateModel);
        void setmallbydatesNovo(final MallBydateModel mallBydateModel);
        void setusermoviereviewbymovieid(final List<UserReviewModel> userReviewModel);
        void setuserreviews(final List<UserReviewModel> userReviewModel);
        void setMovieDetaile(final List<MovieModel> movieModels);

        void setReelCinemaMovieDetails(ReelCinemaMovieListModel response);
    }

    interface Presenter extends BasePresenter<View> {
        void getshowDates(String movie_id,String countryName);
        void getMallbydatesQt(String Country_id, String City_Id, String Cinema_Id, String Movie_Date, String Movie_Id);
        void getMallbydatesFlick(String Country_id, String City_Id, String Cinema_Id, String Movie_Date, String Movie_Id);
        void getMallbydatesNovo(String Country_id, String City_Id, String Cinema_Id, String Movie_Date, String Movie_Id);
        void getusermoviereviewbymovieid(String movieid);
        void adduserreviews(String movie_id, String user_id, String user_review, String user_rating, String review_title);
        void getMovieDetaile(String movie_id);
        void getReelcinemaMovieDetaile(String movieId);
    }
}
