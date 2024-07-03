package com.production.qtickets.ticketconfirmation;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.TicketConfirmItem;
import com.production.qtickets.model.TicketConfirmationEventModel;
import com.production.qtickets.model.UserReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harsh on 6/27/2018.
 */
public class TicketConfirmationPresenter implements TicketConfirmationContracter.Presenter {

    private TicketConfirmationContracter.View mView;

    @Override
    public void attachView(TicketConfirmationContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void getEventTicketConfirmation(String order_id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getEventTicketConfirmation(order_id)
                    .enqueue(new Callback<List<TicketConfirmationEventModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<TicketConfirmationEventModel>> call, @NonNull retrofit2.Response<List<TicketConfirmationEventModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventTicketConfirmation(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<TicketConfirmationEventModel>> call, @NonNull Throwable t) {
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
    public void adduserreviews(String movie_id, String user_id, String user_review, String user_rating, String review_title) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .adduserreviews(movie_id,user_id,user_review,user_rating,review_title)
                    .enqueue(new Callback<List<UserReviewModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<UserReviewModel>> call, @NonNull retrofit2.Response<List<UserReviewModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setuserreviews(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<UserReviewModel>> call, @NonNull Throwable t) {
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
    public void getMovieTicketConfirmation(String order_id) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getMovieTicketConfirmation(order_id)
                    .enqueue(new Callback<TicketConfirmItem>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<TicketConfirmItem> call, @NonNull retrofit2.Response<TicketConfirmItem> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setMovieTicketConfirmation(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<TicketConfirmItem> call, @NonNull Throwable t) {
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
}