package com.production.qtickets.notifications;

import androidx.annotation.NonNull;

import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;


public class NotificationPresenter implements NotificationContracter.Presenter {

    private NotificationContracter.View mView;


    @Override
    public void attachView(NotificationContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    // get the notifications list from the server without parameter
    @Override
    public void getData(String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getNotifications(country)
                    .enqueue(new Callback<List<NotificationModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<NotificationModel>> call, @NonNull retrofit2.Response<List<NotificationModel>> response
                        ) {
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
                        public void onFailure(@NonNull Call<List<NotificationModel>> call, @NonNull Throwable t) {
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