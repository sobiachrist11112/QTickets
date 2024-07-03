package com.production.qtickets.movies;

import androidx.annotation.NonNull;

import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIClientReelCinema;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.ReelCinemaMovieListModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Query;

/**
 * Created by Harsh on 5/22/2018.
 */
public class MovieListPresenter implements MoviContracter.Presenter {

    private MoviContracter.View mView;

    @Override
    public void attachView(MoviContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void getMovie(String countryCode) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getMoviesCnt(countryCode.toLowerCase())
                    .enqueue(new Callback<List<MovieModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<MovieModel>> call, @NonNull retrofit2.Response<List<MovieModel>> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setMovie(response.body());
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
    public void getTopMovie(String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getTopMovies(country)
                    .enqueue(new Callback<Items>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<Items> call, @NonNull retrofit2.Response<Items> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setTopMovie(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Items> call, @NonNull Throwable t) {
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
    public void getCommingSoon(String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getCommingSoon(country)
                    .enqueue(new Callback<List<MovieModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<MovieModel>> call, @NonNull retrofit2.Response<List<MovieModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCommingSoon(response.body());
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
    public void getMovieLaung(String lanuage) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getMovieListBYLaung(lanuage)
                    .enqueue(new Callback<List<MovieModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<MovieModel>> call, @NonNull retrofit2.Response<List<MovieModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setMovieByLaungauge(response.body());
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
    public void getCinemas( String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getCinemas(country)
                    .enqueue(new Callback<Items>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<Items> call, @NonNull retrofit2.Response<Items> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCinemas(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Items> call, @NonNull Throwable t) {
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
    public void getMovieCinema(String theaterID,String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getMovieListBYCinema(theaterID,country)
                    .enqueue(new Callback<List<MovieModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<MovieModel>> call, @NonNull retrofit2.Response<List<MovieModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setMovieCinema(response.body());
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
    public void getReelCinema() {
        try {
            APIClientReelCinema.getClient()
                    .create(APIInterface.class)
                    .getReelCinemaList("getfilms")
                    .enqueue(new Callback<ReelCinemaMovieListModel>() {
                        @Override
                        public void onResponse(@NonNull Call<ReelCinemaMovieListModel> call, @NonNull retrofit2.Response<ReelCinemaMovieListModel> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setReelCinemaList(response.body());
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
