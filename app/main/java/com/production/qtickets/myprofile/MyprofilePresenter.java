package com.production.qtickets.myprofile;

import android.content.Context;
import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.MyProfileModel;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.RegisterRequest;
import com.production.qtickets.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harsh on 5/16/2018.
 */
public class MyprofilePresenter implements MyProfileContracter.Presenter {

    private MyProfileContracter.View mView;


    @Override
    public void attachView(MyProfileContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    //store the user details in teh server based on the user id
    @Override
    public void getData(final Context context, String userId, String name, String email, String number, String str_country_code, String str_nationality) {

            try {
                APIClient.getClient()
                        .create(APIInterface.class)
                        .getProfileDetails(userId, name, email, number, str_country_code, str_nationality)
                        .enqueue(new Callback<List<MyProfileModel>>() {
                            @Override
                            public void onResponse(
                                    @NonNull Call<List<MyProfileModel>> call, @NonNull retrofit2.Response<List<MyProfileModel>> response
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
                            public void onFailure(@NonNull Call<List<MyProfileModel>> call, @NonNull Throwable t) {
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
    public void getQT5countryDropdown() {
        try{
            APIClient.getClientQT5().create(APIInterface.class)
                    .getCountryDropdown(AppConstants.Authorization_QT5,"application/json")
                    .enqueue(new Callback<GetNationalityData>() {
                        @Override
                        public void onResponse(Call<GetNationalityData> call, Response<GetNationalityData> response) {
                            if(mView != null){
                                if(response.isSuccessful() && response.body()!=null){
                                    mView.setCountryDropdownQt5(response.body());
                                }
                            } else {
                                mView.onFailure(call, null, this, null);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetNationalityData> call, Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateprofile(final Context context, RegisterRequest registerRequest) {
        try{
            APIClient.getClientQT5().create(APIInterface.class)
                    .updateproFile(AppConstants.Authorization_QT5,"application/json",registerRequest)
                    .enqueue(new Callback<RegisterModel>() {
                        @Override
                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                            if(mView != null){
                                if(response.isSuccessful() && response.body()!=null){
                                    mView.setUpdateprofile(response.body());
                                }
                            } else {
                                mView.onFailure(call, null, this, null);
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterModel> call, Throwable t) {
                            if (mView != null) {
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}