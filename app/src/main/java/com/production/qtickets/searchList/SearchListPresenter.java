package com.production.qtickets.searchList;

import android.content.Context;
import androidx.annotation.NonNull;


import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.MovieAndEventSearchResult;
import com.production.qtickets.model.SearchListItem;
import com.production.qtickets.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harris on 31-05-2018.
 */

public class SearchListPresenter implements SearchListContractor.Presenter {
    private SearchListContractor.View mView;


    public void attachView(SearchListContractor.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    //get top movies
    @Override
    public void callTopmovies(Context context,Integer countryID,String searchquery) {

        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getMoviesandEvents(AppConstants.Authorization_QT5,countryID,searchquery)
                    .enqueue(new Callback<MovieAndEventSearchResult>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<MovieAndEventSearchResult> call, @NonNull retrofit2.Response<MovieAndEventSearchResult> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setTopmovies(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MovieAndEventSearchResult> call, @NonNull Throwable t) {
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


    //get the search result based on the search keyword

    @Override
    public void getSearch(String country, String search) {

        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getMoviesandEvents(AppConstants.Authorization_QT5, Integer.valueOf(country),search)
                    .enqueue(new Callback<MovieAndEventSearchResult>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<MovieAndEventSearchResult> call, @NonNull retrofit2.Response<MovieAndEventSearchResult> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setSerachList(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MovieAndEventSearchResult> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        } catch (Exception e)

        {
            e.printStackTrace();
        }


    }


}
