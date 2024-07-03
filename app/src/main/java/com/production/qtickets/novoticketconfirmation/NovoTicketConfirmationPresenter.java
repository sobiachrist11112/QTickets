package com.production.qtickets.novoticketconfirmation;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIClientNovo;
import com.production.qtickets.interfaces.APIClientQticketsNovo;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.NovoTicketConfirmationModel;
import com.production.qtickets.model.NovoTicketDetailesModel;
import com.production.qtickets.model.NovoTicketPaymentModel;
import com.production.qtickets.model.NovoTicketPaymentStausModel;
import com.production.qtickets.model.UserReviewModel;
import com.production.qtickets.novocinema.NovoTicketPostBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harsh on 7/20/2018.
 */
public class NovoTicketConfirmationPresenter implements NovoTicketConfirmationContracter.Presenter {

    private NovoTicketConfirmationContracter.View mView;

    @Override
    public void attachView(NovoTicketConfirmationContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void getMovieTicketConfirmationStatus(String bookID) {
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .doNovoconfirmationStatus("getNOVO_userid", bookID)
                    .enqueue(new Callback<NovoTicketConfirmationModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<NovoTicketConfirmationModel> call, @NonNull retrofit2.Response<NovoTicketConfirmationModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setMovieTicketConfirmationStatus(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<NovoTicketConfirmationModel> call, @NonNull Throwable t) {
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
    public void getNovoPaymentResponse(String userseesionid,String c_name,String c_email,String c_phone_number,String c_prifix) {
        try {
            APIInterface loginService =
                    APIClientNovo.createService(APIInterface.class);
            NovoTicketPostBody task = new NovoTicketPostBody(userseesionid, "100", "false", "false", c_name, c_email
                    , c_phone_number,c_prifix);
            Call<List<NovoTicketPaymentModel>> call = loginService.doNovoPaymentResponse(task);
            call.enqueue(new Callback<List<NovoTicketPaymentModel>>() {
                @Override
                public void onResponse(
                        @NonNull Call<List<NovoTicketPaymentModel>> call, @NonNull retrofit2.Response<List<NovoTicketPaymentModel>> response
                ) {
                    if (mView != null) {
                        mView.dismissPb();
                        if (response.isSuccessful() && response.body() != null) {
                            mView.setNovoPaymentResponse(response.body());
                        } else {
                            mView.onFailure(call, null, this, null);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<NovoTicketPaymentModel>> call, @NonNull Throwable t) {
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
                    .adduserreviews(movie_id, user_id, user_review, user_rating, review_title)
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
    public void do_qtickets_novo_confirmation(String novo_id, String Screen_name, String Language, String Rating, String bookID,
                                              String Cinema_Class, String barcodeImgUrl) {
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .doNovo_confirm_booking("confirm_novoBooking", novo_id,Screen_name,Language,Rating,bookID,Cinema_Class,
                            barcodeImgUrl)
                    .enqueue(new Callback<NovoTicketPaymentStausModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<NovoTicketPaymentStausModel> call, @NonNull retrofit2.Response<NovoTicketPaymentStausModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.set_qtickets_novo_confirmation(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<NovoTicketPaymentStausModel> call, @NonNull Throwable t) {
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
    public void do_qtickets_novo_ticket_detailes(String booking_id) {
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .do_novo_ticket_detailes("get_movie_details_new_novo", booking_id)
                    .enqueue(new Callback<NovoTicketDetailesModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<NovoTicketDetailesModel> call, @NonNull retrofit2.Response<NovoTicketDetailesModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.set_qtickets_novo_ticket_detailes(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<NovoTicketDetailesModel> call, @NonNull Throwable t) {
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