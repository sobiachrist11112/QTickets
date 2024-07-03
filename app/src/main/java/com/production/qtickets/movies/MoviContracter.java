package com.production.qtickets.movies;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.ReelCinemaMovieListModel;

/**
 * Created by Harsh on 5/22/2018.
 */
public class MoviContracter {
    public interface View extends BaseView {
        void setMovie(final List<MovieModel> movie);
        void setTopMovie(final Items topMoviesModel);
        void setCommingSoon(final List<MovieModel> commingSoon);
        void setMovieByLaungauge(final List<MovieModel> movibylaung);
        void setCinemas(final Items cinemas);
        void setMovieCinema(final List<MovieModel> moviebycinema);

        void setReelCinemaList(ReelCinemaMovieListModel response);
    }

    interface Presenter extends BasePresenter<View> {
        void getMovie(String countryCode);
        void  getTopMovie(String country);
        void getCommingSoon(String country);
        void getMovieLaung(String lanuage);
        void  getCinemas(String country);
        void  getMovieCinema(String theaterID,String country);

        void getReelCinema();
    }
}