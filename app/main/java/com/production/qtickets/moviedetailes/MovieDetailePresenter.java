package com.production.qtickets.moviedetailes;

import androidx.annotation.NonNull;

import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIClientReelCinema;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.MallBydateModel;
import com.production.qtickets.model.ShowDateModel;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harsh on 5/25/2018.
 */
public class MovieDetailePresenter implements MovieDetaileContracter.Presenter {

    private MovieDetaileContracter.View mView;

    @Override
    public void attachView(MovieDetaileContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    //get the show dates based on the movie id,

    @Override
    public void getshowDates(String movie_id,String countryName) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getshowDates(movie_id,countryName)
                    .enqueue(new Callback<ShowDateModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<ShowDateModel> call, @NonNull retrofit2.Response<ShowDateModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setshowDates(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ShowDateModel> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get the malls based on the dates in which movies are available
    // country_id,city_id,Cinema_id,Movie_date, movie_id, these are the parameters
    @Override
    public void getMallbydatesQt(String Country_id, String City_Id, String Cinema_Id, String Movie_Date, String Movie_Id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getshowbymoviedateQt(Country_id, City_Id, Cinema_Id, Movie_Date, Movie_Id)
                    .enqueue(new Callback<MallBydateModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<MallBydateModel> call, @NonNull retrofit2.Response<MallBydateModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setmallbydatesQt(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MallBydateModel> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
     public void getMallbydatesFlick(String Country_id, String City_Id, String Cinema_Id, String Movie_Date, String Movie_Id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getshowbymoviedateFlick(Country_id, City_Id, Cinema_Id, Movie_Date, Movie_Id)
                    .enqueue(new Callback<MallBydateModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<MallBydateModel> call, @NonNull retrofit2.Response<MallBydateModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setmallbydatesFlick(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MallBydateModel> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMallbydatesNovo(String Country_id, String City_Id, String Cinema_Id, String Movie_Date, String Movie_Id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getshowbymoviedateNovo(Country_id, City_Id, Cinema_Id, Movie_Date, Movie_Id)
                    .enqueue(new Callback<MallBydateModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<MallBydateModel> call, @NonNull retrofit2.Response<MallBydateModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setmallbydatesNovo(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MallBydateModel> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get the list of reviews given by the users who have seen the movie
    // movie id is the parameter
    @Override
    public void getusermoviereviewbymovieid(String movieid) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getusermoviereviewbymovieid(movieid)
                    .enqueue(new Callback<List<UserReviewModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<UserReviewModel>> call, @NonNull retrofit2.Response<List<UserReviewModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setusermoviereviewbymovieid(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<UserReviewModel>> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // add the user review who seen the movie
    // movieid, user_id, user_review, user_rating, review_title
    @Override
    public void adduserreviews(String movie_id, String user_id, String user_review, String user_rating, String review_title) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .adduserreviews(movie_id, user_id, user_review, user_rating, review_title)
                    .enqueue(new Callback<List<UserReviewModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<UserReviewModel>> call, @NonNull retrofit2.Response<List<UserReviewModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setuserreviews(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<UserReviewModel>> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get the details of the movie based on the movie id
    @Override
    public void getMovieDetaile(String movie_id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getMoviesDetailes(movie_id)
                    .enqueue(new Callback<List<MovieModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<MovieModel>> call, @NonNull retrofit2.Response<List<MovieModel>> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setMovieDetaile(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<MovieModel>> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReelcinemaMovieDetaile(String movieId) {
        try {
            APIClientReelCinema.getClient()
                    .create(APIInterface.class)
                    .getReelCinemaMovieDetails("getsessions", movieId)
                    .enqueue(new Callback<ReelCinemaMovieListModel>() {
                        @Override
                        public void onResponse(@NonNull Call<ReelCinemaMovieListModel> call, @NonNull retrofit2.Response<ReelCinemaMovieListModel> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setReelCinemaMovieDetails(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ReelCinemaMovieListModel> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}