package com.production.qtickets.changepassword.otp;

import android.content.Context;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.login.LoginContracter;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.ResetPasswordSucessFully;
import com.production.qtickets.utils.AppConstants;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class OTPPresenter implements OTPContracted.Presenter {

    private OTPContracted.View mView;

    @Override
    public void changedForgotPassword(RequestBody requestBody) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .resetPassword(AppConstants.Authorization_QT5,requestBody)
                    .enqueue(new Callback<ResetPasswordSucessFully>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<ResetPasswordSucessFully> call, @NonNull retrofit2.Response<ResetPasswordSucessFully> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                   mView.setChangePwd(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResetPasswordSucessFully> call, @NonNull Throwable t) {
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
    public void getForgotPasswprd(Context context, String username) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getForgotPassword(AppConstants.Authorization_QT5,username)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setForgotPassword(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
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
    public void attachView(OTPContracted.View view) {
        mView= view;
    }

    @Override
    public void detach() {
        mView = null;
    }
}
