package com.production.qtickets.changepassword;

import android.content.Context;
import androidx.annotation.NonNull;


import java.util.List;

import com.google.gson.JsonObject;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.ChangePwdModel;
import com.production.qtickets.utils.AppConstants;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harris on 29-05-2018.
 */

public class ChangePwdPresenter implements ChangePwdContracter.Presenter {

    ChangePwdContracter.View mView;


    public void attachView(ChangePwdContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    // here we are calling the network call to change th password, using retrofit
    @Override
    public void getChangepwd(Context context, String user_id, String str_current_pwd, String str_confirm_pwd) {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId",user_id);
            jsonObject.put("password",str_current_pwd);
            jsonObject.put("confirmPassword",str_confirm_pwd);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getChangePwd(AppConstants.Authorization_QT5,body)// userid,currentPassword, newPassword
                    .enqueue(new Callback<ChangePasswordData>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<ChangePasswordData> call, @NonNull retrofit2.Response<ChangePasswordData> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    // here we will get the response of the network call.
                                    mView.setChangePwd(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ChangePasswordData> call, @NonNull Throwable t) {
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

