package com.production.qtickets.events;

import android.content.Context;
import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.EventTicketPriceRangeModel;

import retrofit2.Call;
import retrofit2.Callback;

public class FilterPriceRangePresenter implements FilterPriceRangeContracter.Presenter{

    private FilterPriceRangeContracter.View mView;


    @Override
    public void attachView(FilterPriceRangeContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }



    @Override
    public void getPriceRangeData(Context context) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getPriceRange()
                    .enqueue(new Callback<EventTicketPriceRangeModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventTicketPriceRangeModel> call, @NonNull retrofit2.Response<EventTicketPriceRangeModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setPriceRangeData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventTicketPriceRangeModel> call, @NonNull Throwable t) {
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
