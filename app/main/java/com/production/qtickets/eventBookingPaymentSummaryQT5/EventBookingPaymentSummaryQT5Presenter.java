package com.production.qtickets.eventBookingPaymentSummaryQT5;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.BookingQT5Model;
import com.production.qtickets.model.CouponQT5Model;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.VerifyBookingDetail;
import com.production.qtickets.utils.AppConstants;

import org.json.JSONObject;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventBookingPaymentSummaryQT5Presenter implements EventBookingPaymentSummaryQT5Contracter.Presenter{

    public EventBookingPaymentSummaryQT5Contracter.View mView;


    @Override
    public void attachView(EventBookingPaymentSummaryQT5Contracter.View view) {
        mView = view;
        mView.dismissPb();
    }

    @Override
    public void detach() {
        mView.dismissPb();
        mView = null;
    }


    @Override
    public void BookTickets(String bookingXML) {
        try{
            if (mView != null)mView.showPb();
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bookingXML);
            APIClient.getClientQT5().create(APIInterface.class).BookTicketsForApp(AppConstants.Authorization_QT5,"application/json",/*eventID,couponCode,totalAmt,qty,ticketXML*/body)
                    .enqueue(new Callback<BookingQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<BookingQT5Model> call, @NonNull retrofit2.Response<BookingQT5Model> response
                        ) {
                            if (mView != null) {
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.BookTickets(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                            mView.dismissPb();
                        }

                        @Override
                        public void onFailure(@NonNull Call<BookingQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                            mView.dismissPb();
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void getCountry() {
        try{
            if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class).GetAllCountries(AppConstants.Authorization_QT5,-1)
                    .enqueue(new Callback<AllCountryQT5Model>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<AllCountryQT5Model> call, @NonNull retrofit2.Response<AllCountryQT5Model> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCountry(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                            mView.dismissPb();
                        }

                        @Override
                        public void onFailure(@NonNull Call<AllCountryQT5Model> call, @NonNull Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, null, this, null);
                            }
                            mView.dismissPb();
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getQT5countryDropdown() {
        try{
            if (mView != null)mView.showPb();
            APIClient.getClientQT5().create(APIInterface.class)
                    .getCountryDropdown(AppConstants.Authorization_QT5,"application/json")
                    .enqueue(new Callback<GetNationalityData>() {
                        @Override
                        public void onResponse(Call<GetNationalityData> call, Response<GetNationalityData> response) {
                            if(mView != null){
                                if(response.isSuccessful() && response.body()!=null){
                                    mView.setCountryDropdownQt5(response.body());
                                }
                            } else {
                                mView.onFailure(call, null, this, null);
                            }
                            mView.dismissPb();
                        }

                        @Override
                        public void onFailure(Call<GetNationalityData> call, Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                            mView.dismissPb();
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void verifyBooking(String bookingxml,String bookingfinalxml) {

        try{
            if (mView != null)mView.showPb();
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bookingxml);
            APIClient.getClientQT5().create(APIInterface.class)
                    .verifyBookingDetails(AppConstants.Authorization_QT5,body)
                    .enqueue(new Callback<VerifyBookingDetail>() {
                        @Override
                        public void onResponse(Call<VerifyBookingDetail> call, Response<VerifyBookingDetail> response) {
                            if(mView != null){
                                if(response.isSuccessful() && response.body()!=null){
                                    mView.setVerifyBooking(response.body(),bookingfinalxml);
                                }
                            } else {
                                mView.onFailure(call, null, this, null);
                            }
                            mView.dismissPb();
                        }

                        @Override
                        public void onFailure(Call<VerifyBookingDetail> call, Throwable t) {
                            if (mView != null) {
                                mView.dismissPb();
                                mView.onFailure(call, t, this, null);
                            }
                            mView.dismissPb();
                        }
                    });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
