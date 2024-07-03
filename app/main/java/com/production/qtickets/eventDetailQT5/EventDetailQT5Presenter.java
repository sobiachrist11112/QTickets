package com.production.qtickets.eventDetailQT5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.model.EventDetailQT5Model;
import com.production.qtickets.model.GetSimilarEventData;
import com.production.qtickets.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;

public class EventDetailQT5Presenter implements EventDetailQT5Contracter.Presenter{

    public EventDetailQT5Contracter.View mView;


    @Override
    public void attachView(EventDetailQT5Contracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void getEventDetail(int ID) {
        try{
            if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getEventDetailsById(AppConstants.Authorization_QT5,ID)
                    .enqueue(new Callback<EventDetailQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventDetailQT5Model> call, @NonNull retrofit2.Response<EventDetailQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    Log.d("Shubhamevents",response.body().toString());
                                    mView.setEventDetail(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventDetailQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void getSimilarEvents(int eventID) {
        try{
           // if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).getSimilarEvent(AppConstants.Authorization_QT5,eventID)
                    .enqueue(new Callback<GetSimilarEventData>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<GetSimilarEventData> call, @NonNull retrofit2.Response<GetSimilarEventData> response
                        ) {
                            if (mView != null) {
                        //        mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setSimilarEvents(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                             //   mView.dismissPb();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GetSimilarEventData> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
