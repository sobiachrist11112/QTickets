package com.production.qtickets.eventBookingSummaryQT5;

import android.util.Log;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.CouponQT5Model;
import com.production.qtickets.model.CuponCodeModel;
import com.production.qtickets.model.PromoCode;
import com.production.qtickets.utils.AppConstants;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class EventBookingSummaryQT5Presenter implements EventBookingSummaryQT5Contracter.Presenter {

    public EventBookingSummaryQT5Contracter.View mView;


    @Override
    public void attachView(EventBookingSummaryQT5Contracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    @Override
    public void applyCoupon(String eventID, String couponCode, String email, double totalamount) {
        try {
            if (mView != null) mView.showPb();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("voucherName", couponCode);
            jsonObject.put("eventId", eventID);
            jsonObject.put("totalAmount", totalamount);

            Log.d("27dec", "totalamount " + totalamount);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            APIClient.getClientQT5().create(APIInterface.class).getEventCouponDetails(
                            AppConstants.Authorization_QT5, body)

                    .enqueue(new Callback<CuponCodeModel>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<CuponCodeModel> call, @NonNull retrofit2.Response<CuponCodeModel> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setCouponDetail(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<CuponCodeModel> call, @NonNull Throwable t) {
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
    public void applyPromoCOde(int eventID, String promocde, String ticketXML, String totalamount) {
        try {
            if (mView != null) mView.showPb();
            JSONObject jsonObject = new JSONObject();


            jsonObject.put("eventId", eventID);
            jsonObject.put("promoCodeName", promocde);
            jsonObject.put("ticketDetails", ticketXML);
            jsonObject.put("totalAmount", Double.valueOf(totalamount));

            Log.d("4Jan", " sending :" + Double.valueOf(totalamount));

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            APIClient.getClientQT5().create(APIInterface.class).getApplyPromocode(AppConstants.Authorization_QT5, "application/json", body)
                    .enqueue(new Callback<PromoCode>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<PromoCode> call, @NonNull retrofit2.Response<PromoCode> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setPromocodeDetails(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<PromoCode> call, @NonNull Throwable t) {
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
