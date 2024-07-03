package com.production.qtickets.activity;

import com.production.qtickets.dashboard.DashboardContracter;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.CategoryModel;
import com.production.qtickets.model.DeleteAccount;
import com.production.qtickets.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NavigationPresenter implements NavigationContracter.Presenter {

    private NavigationContracter.View mView;

    @Override
    public void attachView(NavigationContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void deleteAccount(String userID) {
        APIClient.getClientQT5().create(APIInterface.class)
                .deleteAccount(AppConstants.Authorization_QT5,userID)
                .enqueue(new Callback<DeleteAccount>() {
                    @Override
                    public void onResponse(Call<DeleteAccount> call, Response<DeleteAccount> response) {
                        if (mView != null) {
                            mView.dismissPb();
                            if (response.isSuccessful() && response.body() != null) {
                                mView.setDeleteAccount(response.body());
                            } else {
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteAccount> call, Throwable throwable) {
                        if (mView != null) {
                            mView.dismissPb();
                            mView.onFailure(call, null, this, null);
                        }
                    }
                });
    }


}
