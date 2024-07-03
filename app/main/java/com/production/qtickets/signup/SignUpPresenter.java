package com.production.qtickets.signup;

import android.content.Context;
import androidx.annotation.NonNull;


import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.RegisterRequest;
import com.production.qtickets.utils.AppConstants;

import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harris on 28-05-2018.
 */

public class SignUpPresenter implements SignUpContracter.Presenter {

    private SignUpContracter.View mView;


    @Override
    public void attachView(SignUpContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    //registration process where we are passing all the parameters for the registrations


//    public void getRegister(final Context context, String username, String email, String phoneno,
//                            String password, String rep_password, String country, String gender,String androidDeviceId) {

    public void getRegister(final Context context, RegisterRequest registerRequest) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .callRegister(AppConstants.Authorization_QT5,"application/json",registerRequest)
                    .enqueue(new Callback<RegisterModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<RegisterModel> call, @NonNull retrofit2.Response<RegisterModel> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setRegister(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable t) {
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