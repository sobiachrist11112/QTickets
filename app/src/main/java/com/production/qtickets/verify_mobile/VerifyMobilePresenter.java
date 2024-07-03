package com.production.qtickets.verify_mobile;

import android.content.Context;
import androidx.annotation.NonNull;



import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.VerifyMobileModel;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harris on 05-06-2018.
 */

public class VerifyMobilePresenter implements VerifyMobileContracter.Presenter {

    private VerifyMobileContracter.View mView;

    public void attachView(VerifyMobileContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    //api call for otp, pass userid, mobile, and country
    @Override
    public void getOtp(Context context, String user_id, String mobileno, String country) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .callOtp(user_id, mobileno, country)
                    .enqueue(new Callback<List<VerifyMobileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<VerifyMobileModel>> call, @NonNull retrofit2.Response<List<VerifyMobileModel>> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setOtp(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<VerifyMobileModel>> call, @NonNull Throwable t) {
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

    //otp verification, send country,mobile and the otp string

    @Override
    public void getVerify(Context context, String country, String mobileno, String str_otp) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getVerified(country, mobileno, str_otp)
                    .enqueue(new Callback<List<VerifyMobileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<VerifyMobileModel>> call, @NonNull retrofit2.Response<List<VerifyMobileModel>> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setVerified(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<VerifyMobileModel>> call, @NonNull Throwable t) {
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
