package com.production.qtickets.eventQT5;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.AllCategoryQT5Model;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.CarouselBannerModel;
import com.production.qtickets.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;

public class EventHomePresenter implements EventHomeContracter.Presenter{

    public EventHomeContracter.View mView;


    @Override
    public void attachView(EventHomeContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void getCarouselBanners(int countryID) {
        try{
            if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getCarouselBanners(AppConstants.Authorization_QT5,countryID)
                    .enqueue(new Callback<CarouselBannerModel>() {
                @Override
                public void onResponse(
                        @NonNull Call<CarouselBannerModel> call, @NonNull retrofit2.Response<CarouselBannerModel> response
                ) {
                    if (mView != null) {
                        mView.dismissPb();
                        if (response.isSuccessful() && response.body() != null) {
                            mView.setCarouselBanners(response.body());
                        } else {
                            mView.onFailure(call, null, this, null);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CarouselBannerModel> call, @NonNull Throwable t) {
                    if (mView != null) {
                        mView.dismissPb();
                        mView.onFailure(call, null, this, null);
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void getCountry() {
        try{
            if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).GetAllCountries(AppConstants.Authorization_QT5,-1)
                    .enqueue(new Callback<AllCountryQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<AllCountryQT5Model> call, @NonNull retrofit2.Response<AllCountryQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCountry(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<AllCountryQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void getAllCategories() {
        try{
            if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getAllCategories(AppConstants.Authorization_QT5,-1,"")
                    .enqueue(new Callback<AllCategoryQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<AllCategoryQT5Model> call, @NonNull retrofit2.Response<AllCategoryQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setAllCategories(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<AllCategoryQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
