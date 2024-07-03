package com.production.qtickets.dashboard;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIClientReelCinema;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.model.BannerModel;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.CategoryModel;
import com.production.qtickets.model.DashboardModel;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.GetNewBannerEvents;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.model.StaticLinksDataModel;
import com.production.qtickets.model.StaticPageLinksModel;
import com.production.qtickets.model.TokenModel;
import com.production.qtickets.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harsh on 5/15/2018.
 */
public class DashboardPresenter implements DashboardContracter.Presenter {

    private DashboardContracter.View mView;

    @Override
    public void attachView(DashboardContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    // network call for getting spinner items list from the server
    @Override
    public void getLocationData() {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getLocation()
                    .enqueue(new Callback<List<CategoryModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<CategoryModel>> call, @NonNull retrofit2.Response<List<CategoryModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setLocationData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<CategoryModel>> call, @NonNull Throwable t) {
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

    @Override
    public void getStaticLinksdata() {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getStaticLinks(AppConstants.Authorization_QT5)
                    .enqueue(new Callback<StaticLinksDataModel>() {

                        @Override
                        public void onResponse(Call<StaticLinksDataModel> call,
                                               Response<StaticLinksDataModel> response) {

                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setStaticLinksdata(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<StaticLinksDataModel> call, @NonNull Throwable t) {
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

    //getting list of movies,events sports and leuisures based on the user country
    // have to pass ccountry name as a parameter

    @Override
    public void getDashboardData(String country_name) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getDashboard(country_name)
                    .enqueue(new Callback<DashboardModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<DashboardModel> call, @NonNull retrofit2.Response<DashboardModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setDashboardData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<DashboardModel> call, @NonNull Throwable t) {
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
    public void getNewDashboardData(String country_name) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getNewDashboard(country_name)
                    .enqueue(new Callback<DashboardModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<DashboardModel> call, @NonNull retrofit2.Response<DashboardModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setNewDashboardData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<DashboardModel> call, @NonNull Throwable t) {
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

    //For ReelCinema
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

    @Override
    public void getCountry() {
        try {
            if (mView != null) mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).GetAllCountries(AppConstants.Authorization_QT5, -1)
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBanners(String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getBanners(country)
                    .enqueue(new Callback<BannerModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<BannerModel> call, @NonNull retrofit2.Response<BannerModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setBanners(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<BannerModel> call, @NonNull Throwable t) {
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
    public void getToken(String token, String countryName) {
        try {
            APIClient.getClient().create(APIInterface.class)
                    .getToken(token, countryName)
                    .enqueue(new Callback<TokenModel>() {
                        @Override
                        public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                          /*  if(mView != null){
                                if(response.isSuccessful() && response.body()!=null){
                                    Log.d("firebaseToken response",response.body().toString());
                                }
                            } else {
                                mView.onFailure(call, null, this, null);
                            }*/
                        }

                        @Override
                        public void onFailure(Call<TokenModel> call, Throwable t) {
                            /*if (mView != null) {
                               // mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }*/
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getQT5countryDropdown() {
        try {
            APIClient.getClientQT5().create(APIInterface.class)
                    .getCountryDropdown(AppConstants.Authorization_QT5, "application/json")
                    .enqueue(new Callback<GetNationalityData>() {
                        @Override
                        public void onResponse(Call<GetNationalityData> call, Response<GetNationalityData> response) {
                            if (mView != null) {
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCountryDropdownQt5(response.body());
                                }
                            } else {
                                if (mView != null){
                                    mView.onFailure(call, null, this, null);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<GetNationalityData> call, Throwable t) {
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
    public void getAllEvents(int CountryID, int CategoryID, String startDate, String endDate, Integer min_price, Integer max_price) {
        try {
            if (mView != null) mView.showPb();
            Log.d("CatID: ", String.valueOf(CategoryID));
            APIClient.getClientQT5().create(APIInterface.class).getAllEvents(AppConstants.Authorization_QT5, CountryID, CategoryID,1, "",0,startDate, endDate,""  , min_price, max_price)
                    //     APIClient.getClientQT5().create(APIInterface.class).getAllEvents(AppConstants.Authorization_QT5, CountryID, CategoryID, startDate, endDate, 1, 0, 0, min_price, max_price)
                    .enqueue(new Callback<AllEventQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<AllEventQT5Model> call, @NonNull retrofit2.Response<AllEventQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setAllEvents(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<AllEventQT5Model> call, @NonNull Throwable t) {
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

    @Override
    public void getBannersByNewEvents(String country) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getNewEventsBanners(AppConstants.Authorization_QT5, country)
                    .enqueue(new Callback<GetNewBannerEvents>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<GetNewBannerEvents> call, @NonNull retrofit2.Response<GetNewBannerEvents> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setBannerByNewEvents(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetNewBannerEvents> call, @NonNull Throwable t) {
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