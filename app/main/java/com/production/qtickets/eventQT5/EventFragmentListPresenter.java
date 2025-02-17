package com.production.qtickets.eventQT5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;

public class EventFragmentListPresenter implements EventFragmentListContracter.Presenter{

    public EventFragmentListContracter.View mView;


    @Override
    public void attachView(EventFragmentListContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void getAllEvents(int CountryID, int CategoryID, String startDate, String endDate, Integer min_price, Integer max_price) {
        try{
            Log.d("CatID: ",String.valueOf(CategoryID));
            APIClient.getClientQT5().create(APIInterface.class).getAllEvents(AppConstants.Authorization_QT5,CountryID,CategoryID,startDate,endDate,1,0,0,min_price,max_price)
                    .enqueue(new Callback<AllEventQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<AllEventQT5Model> call, @NonNull retrofit2.Response<AllEventQT5Model> response
                        ) {
                            if (mView != null) {
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setAllEvents(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<AllEventQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
