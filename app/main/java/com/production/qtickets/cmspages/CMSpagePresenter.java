package com.production.qtickets.cmspages;

import android.content.Context;
import androidx.annotation.NonNull;


import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.StatusModel;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harsh on 5/16/2018.
 */
public class CMSpagePresenter implements CMSpageContracter.Presenter {

    private CMSpageContracter.View mView;


    @Override
    public void attachView(CMSpageContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    // making the network call to get the cms content using retrofit
    @Override
    public void getCMSData(final Context context) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getCMSPages()
                    .enqueue(new Callback<StatusModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<StatusModel> call, @NonNull retrofit2.Response<StatusModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCMSData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<StatusModel> call, @NonNull Throwable t) {
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
}