package com.production.qtickets.contactus;

import android.content.Context;

import androidx.annotation.NonNull;

import com.production.qtickets.changepassword.ChangePwdContracter;
import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.ContactResponse;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class ContactUsPresenter implements ContactusContracter.Presenter {

    ContactusContracter.View mView;


    public void attachView(ContactusContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    // here we are calling the network call to change th password, using retrofit

    @Override
    public void sendContactDetails(String name, String mobile, String email, String purpose, String message) {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",name);
            jsonObject.put("mobile",mobile);
            jsonObject.put("email",email);
            jsonObject.put("purpose",purpose);
            jsonObject.put("message",message);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .sendContactUsDetails(AppConstants.Authorization_QT5,body)// userid,currentPassword, newPassword
                    .enqueue(new Callback<ContactResponse>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<ContactResponse> call, @NonNull retrofit2.Response<ContactResponse> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    // here we will get the response of the network call.
                                    mView.onSuccessfullySendDetails(response.body());
                                } else {

                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ContactResponse> call, @NonNull Throwable t) {
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

