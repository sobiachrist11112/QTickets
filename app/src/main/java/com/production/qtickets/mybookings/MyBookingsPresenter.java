package com.production.qtickets.mybookings;

import android.content.Context;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.GetHistoryData;
import com.production.qtickets.model.Items;
import com.production.qtickets.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingsPresenter implements MyBookingsContracter.Presenter {

    private MyBookingsContracter.View mView;
    @Override
    public void attachView(MyBookingsContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    //get the movie details based on the userid
    @Override
    public void getData(Context context, String userId) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getMyBookings(userId)
                    .enqueue(new Callback<Items>() {
                        @Override
                        public void onResponse(Call<Items> call, Response<Items> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Items> call, Throwable t) {
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

    //get the booking details based on the userid
    //here we are hardcoded the userid for testing
    @Override
    public void getEvents(Context context, String userId) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getMyEventsBookings(AppConstants.Authorization_QT5,userId)
                    .enqueue(new Callback<ArrayList<GetHistoryData>>() {
                        @Override
                        public void onResponse(Call<ArrayList<GetHistoryData>> call, Response<ArrayList<GetHistoryData>> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventsData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<GetHistoryData>> call, Throwable t) {
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