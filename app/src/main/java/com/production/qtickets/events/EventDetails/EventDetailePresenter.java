package com.production.qtickets.events.EventDetails;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.OfferList;
import com.production.qtickets.model.TicketsModel;

import retrofit2.Call;
import retrofit2.Callback;

public class EventDetailePresenter implements EventDetaileContracter.Presenter {

    EventDetaileContracter.View mView;



    //getting the list of tickets with respect to event id
    //here have to pass the event id as parameter
    @Override
    public void getTicketDetails(String event_id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getTicketDetails(event_id)
                    .enqueue(new Callback<TicketsModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<TicketsModel> call, @NonNull retrofit2.Response<TicketsModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setTicketDetails(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<TicketsModel> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTicketDetailss(String eventID) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getTicketDetailss(eventID)
                    .enqueue(new Callback<OfferList>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<OfferList> call, @NonNull retrofit2.Response<OfferList> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setTicketDetailsss(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);

                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<OfferList> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void attachView(EventDetaileContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }
}
