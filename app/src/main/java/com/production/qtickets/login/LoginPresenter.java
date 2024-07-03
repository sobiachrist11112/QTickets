package com.production.qtickets.login;

import android.content.Context;

import androidx.annotation.NonNull;


import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.UserINfo;
import com.production.qtickets.utils.AppConstants;

import java.util.List;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;

import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.RegisterModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by Harsh on 5/16/2018.
 */
public class LoginPresenter implements LoginContracter.Presenter {
    private LoginContracter.View mView;


    @Override
    public void attachView(LoginContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    public void getRegister(String username, String lname, String email, String phoneno,
                            String password, String rep_password, String fid, String country, String gender, String androidDeviceId) {

//        try {
//            APIClient.getClient()
//                    .create(APIInterface.class)
//                    .callRegister(AppConstants.AppSource,username, email, phoneno,"+974","",gender, password,
//                            rep_password,"","","","")
//                    .enqueue(new Callback<List<RegisterModel>>() {
//                        @Override
//                        public void onResponse(
//                                @NonNull Call<List<RegisterModel>> call, @NonNull retrofit2.Response<List<RegisterModel>> response) {
//                            if (mView != null) {
//                                mView.dismissPb();
//                                if (response.isSuccessful() && response.body() != null) {
//                                    mView.setRegister(response.body());
//                                } else {
//                                    mView.onFailure(call, null, this, null);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Call<List<RegisterModel>> call, @NonNull Throwable t) {
//                            if (mView != null) {
//                                mView.dismissPb();
//                                mView.onFailure(call, null, this, null);
//                            }
//                        }
//                    });
//
//        } catch (Exception e)
//
//        {
//            e.printStackTrace();
//        }


    }


    @Override
    public void getLogin(final Context context, String username, String password) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getLogin(AppConstants.Authorization_QT5, "application/json", "text/plain", username, password)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setlogin(response.body());
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
    public void getForgotPasswprd(Context context, String username) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getForgotPassword(AppConstants.Authorization_QT5, username)
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
    public void getLoginFacebook(String facebookjson) {
        try {
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), facebookjson);
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getFacebookLogin(AppConstants.Authorization_QT5, body)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response
                        ) {
                            if (mView != null) {
                            //    mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setlogin(response.body());
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
    public void getGmailLogin(String gmailjson) {
        try {
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), gmailjson);
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getGoogleLogin(AppConstants.Authorization_QT5, body)
                    .enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setlogin(response.body());
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
    public void getUserDetails(String userID) {
        try {
            APIClient.getClientQT5()
                    .create(APIInterface.class)
                    .getUserDetails(AppConstants.Authorization_QT5, userID)
                    .enqueue(new Callback<UserINfo>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<UserINfo> call, @NonNull retrofit2.Response<UserINfo> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setUserDetails(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UserINfo> call, @NonNull Throwable t) {
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