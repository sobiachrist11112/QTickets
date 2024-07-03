package com.production.qtickets.ticketbookingdetaile;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIClientQticketsNovo;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.BookingDetaileModel;
import com.production.qtickets.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Harsh on 6/4/2018.
 */
public class BookingDetailePresenter implements BookingDetaileContracter.Presenter {

    private BookingDetaileContracter.View mView;

    @Override
    public void attachView(BookingDetaileContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void getBlockseat(String show_id, String seats) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .doblockseat(show_id, seats, AppConstants.AppSource)
                    .enqueue(new Callback<List<BookingDetaileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<BookingDetaileModel>> call, @NonNull retrofit2.Response<List<BookingDetaileModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setBlockSeat(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<BookingDetaileModel>> call, @NonNull Throwable t) {
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
    public void doseatLock(String transaction_id, String name, String email, String number, String prefix, String VoucherCodes) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .doseatlock(transaction_id, name, email, number, prefix, VoucherCodes)
                    .enqueue(new Callback<List<BookingDetaileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<BookingDetaileModel>> call, @NonNull retrofit2.Response<List<BookingDetaileModel>> response
                        ) {
                            if (mView != null) {
                              //  mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setSeatLock(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<BookingDetaileModel>> call, @NonNull Throwable t) {
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
    public void doApplyCouponcode(String email, String couponcode,String androidDeviceId) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .doapplycouponcode(email, couponcode)
                    .enqueue(new Callback<List<BookingDetaileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<BookingDetaileModel>> call, @NonNull retrofit2.Response<List<BookingDetaileModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCouponCode(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<BookingDetaileModel>> call, @NonNull Throwable t) {
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
    public void doSeatLockConfirmation(String transactionid) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .doSeatLock(transactionid, AppConstants.AppSource, "1.0")
                    .enqueue(new Callback<List<BookingDetaileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<BookingDetaileModel>> call, @NonNull retrofit2.Response<List<BookingDetaileModel>> response
                        ) {
                            if (mView != null) {

                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setSeatLockConfirmation(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<BookingDetaileModel>> call, @NonNull Throwable t) {
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
    public void getEventBooking(String eventid, String ticket_id, String amount, String tkt_count, String NoOftktPerid, String camount, String email, String name, String phone, String prefix, String bdate, String btime, String balamount, String couponcodes) {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getEventTicketBooking(eventid, ticket_id, amount, tkt_count, NoOftktPerid, camount, email, name, phone, prefix
                            , bdate, btime, balamount, couponcodes, AppConstants.AppSource, "1.0")
                    .enqueue(new Callback<List<BookingDetaileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<BookingDetaileModel>> call, @NonNull retrofit2.Response<List<BookingDetaileModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setEventBooking(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<List<BookingDetaileModel>> call, @NonNull Throwable t) {
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
    public void donovoQticketsTicketConfirmation(String CinemId, String CinemaName, String MovieId, String MovieName, String MovieUrl, String SessionId, String bookingInfoId, String userSessionID, String Show_date, String Show_time, String No_of_tickets,String servicecharge, String ticket_price, String total_paid, String seats_info, String seats_id) {
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .getNovoConfirmation("insert_novoBooking", "0", CinemId, CinemaName, MovieId, MovieName, MovieUrl, SessionId, bookingInfoId, userSessionID
                            , Show_date, Show_time, No_of_tickets, servicecharge, ticket_price, total_paid, seats_info,
                            seats_id, "0", AppConstants.novo_country_id)
                    .enqueue(new Callback<NovoBookingConfirmationModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<NovoBookingConfirmationModel> call, @NonNull retrofit2.Response<NovoBookingConfirmationModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setnovoQticketsTicketConfirmation(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<NovoBookingConfirmationModel> call, @NonNull Throwable t) {
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
    public void doNovoApplyCouponCode(String coupon, String email){
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .doNovoapplycouponcode("16",email, coupon)
                    .enqueue(new Callback<List<BookingDetaileModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<BookingDetaileModel>> call, @NonNull retrofit2.Response<List<BookingDetaileModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setNovoCouponCode(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }
                        @Override
                        public void onFailure(@NonNull Call<List<BookingDetaileModel>> call, @NonNull Throwable t) {
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
    public void doNovopay(String user_id, String CinemId, String CinemaName, String MovieId, String MovieName,
                          String MovieUrl, String SessionId, String bookingInfoId, String userSessionID,
                          String Show_date, String Show_time, String No_of_tickets, String serchr, String ticket_price,
                          String total_paid, String seats_info, String seats_id, String BookingID,
                          String Customer_name, String Customer_phone, String Customer_email, String Customer_prefix) {
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .getNovoproceddtopay("update_novoBooking", user_id, CinemId, CinemaName, MovieId, MovieName, MovieUrl, SessionId,
                            bookingInfoId, userSessionID
                            , Show_date, Show_time, No_of_tickets, serchr, ticket_price, total_paid, seats_info,
                            seats_id, "Pending", AppConstants.novo_country_id, BookingID, Customer_name, Customer_phone
                            , Customer_email, Customer_prefix)
                    .enqueue(new Callback<NovoBookingConfirmationModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<NovoBookingConfirmationModel> call, @NonNull retrofit2.Response<NovoBookingConfirmationModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setNovoPay(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<NovoBookingConfirmationModel> call, @NonNull Throwable t) {
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
    public void doNovoUpdateCouponCode(String coupon, String email, String booking_id) {
        try {
            APIClientQticketsNovo.getClient()
                    .create(APIInterface.class)
                    .doNovoupdateouponcode("17",email, coupon,booking_id)
                    .enqueue(new Callback<BookingDetaileModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<BookingDetaileModel> call, @NonNull retrofit2.Response<BookingDetaileModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setNovoUpdateCouponCode(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<BookingDetaileModel> call, @NonNull Throwable t) {
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