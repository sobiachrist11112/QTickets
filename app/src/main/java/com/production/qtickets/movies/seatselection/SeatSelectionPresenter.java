package com.production.qtickets.movies.seatselection;

import android.content.Context;
import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.SeatModel;

import retrofit2.Call;
import retrofit2.Callback;

public class SeatSelectionPresenter implements SeatSelectionContracter.Presenter {

    private SeatSelectionContracter.View mView;
    @Override
    public void attachView(SeatSelectionContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void getSeatSelection(final Context context,String showID) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getSeatLayout(showID)
                    .enqueue(new Callback<SeatModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<SeatModel> call, @NonNull retrofit2.Response<SeatModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setSeatselection(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SeatModel> call, @NonNull Throwable t) {
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