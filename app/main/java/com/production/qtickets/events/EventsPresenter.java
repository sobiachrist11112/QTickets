package com.production.qtickets.events;

import android.content.Context;
import androidx.annotation.NonNull;



import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.EventModel;

import retrofit2.Call;
import retrofit2.Callback;

public class EventsPresenter implements EventsContracter.Presenter {
    private EventsContracter.View mView;


    @Override
    public void attachView(EventsContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void getEventData(final Context context, String country_name) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getEvents(country_name)
                    .enqueue(new Callback<EventModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventModel> call, @NonNull retrofit2.Response<EventModel> response
                        ) {
                            if (mView != null) {
                               // mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventModel> call, @NonNull Throwable t) {
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
    public void getEventFilterData(final Context context, String country_name,String range) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getEventsFilter(country_name,range)
                    .enqueue(new Callback<EventModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventModel> call, @NonNull retrofit2.Response<EventModel> response
                        ) {
                            if (mView != null) {
                              //  mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setFilterEventData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventModel> call, @NonNull Throwable t) {
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
    public void getDetailEventFilterData(Context context, String minPrice, String maxPrice, String startDate, String endDate, String country) {

        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getEventDetailFilter(minPrice,maxPrice,startDate,endDate,country)
                    .enqueue(new Callback<EventModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<EventModel> call, @NonNull retrofit2.Response<EventModel> response
                        ) {
                            if (mView != null) {
                               // mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setDetailFilterEventData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<EventModel> call, @NonNull Throwable t) {
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
    public void getFreeToGoEvent(String country_name,String category_id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getSportsList(country_name, category_id)
                    .enqueue(new Callback<DataModel>() {
                        @Override
                        public void onResponse(@NonNull Call<DataModel> call, @NonNull retrofit2.Response<DataModel> response) {
                            if (mView != null) {
                              //  mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                   mView.setFreeToGoData(response.body());
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
}
