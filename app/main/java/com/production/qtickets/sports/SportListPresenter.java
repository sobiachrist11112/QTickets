package com.production.qtickets.sports;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.Liesure;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harsh on 6/22/2018.
 */
public class SportListPresenter implements SportListContracter.Presenter {

    private SportListContracter.View mView;

    @Override
    public void attachView(SportListContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    //here we are getting the detailed list of sports by passing the country name and category id
    @Override
    public void getSportsData(String country_name, String category_id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getSportsList(country_name, category_id)
                    .enqueue(new Callback<DataModel>() {
                        @Override
                        public void onResponse(@NonNull Call<DataModel> call, @NonNull retrofit2.Response<DataModel> response) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setSportsData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<DataModel> call, @NonNull Throwable t) {
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
    public void getLeisureData(int type, int pagenumber) {
        try{
            APIClient.getLiesureClient()
                    .create(APIInterface.class)
                    .getLiesure(type, pagenumber)
                    .enqueue(new Callback<Liesure>() {
                        @Override
                        public void onResponse(Call<Liesure> call, Response<Liesure> response) {
                           if(mView != null) {
                               mView.dismissPb();
                               if(response.body() != null && response.isSuccessful()){
                                  mView.setLeisureData(response.body());
                               }

                           }
                        }

                        @Override
                        public void onFailure(Call<Liesure> call, Throwable t) {

                        }
                    });
        } catch (Exception e){

        }
    }

}