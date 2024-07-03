package com.production.qtickets.novocinema.seatinglayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.production.qtickets.interfaces.APIClientNovo;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.NovoBlockSeatsModel;
import com.production.qtickets.model.NovoSeatingModel;
import com.production.qtickets.novocinema.NovoTicketPostBody;
import com.production.qtickets.novocinema.NovoTicketSelectionActivity;
import com.production.qtickets.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harsh on 7/13/2018.
 */
public class NovoSeatingPresenter implements NovoSeatingContracter.Presenter {

    private NovoSeatingContracter.View mView;
    private AlertDialog alertDialog;

    @Override
    public void attachView(NovoSeatingContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    public void getNovoSeating(final NovoSeatingLayoutActivity novoSeatingLayoutActivity, String cinemaid, String sessionid, String cencor, String selectedtickettypes) {
        try {
            APIInterface loginService =
                    APIClientNovo.createService(APIInterface.class);
            NovoTicketPostBody task = new NovoTicketPostBody(AppConstants.novo_country_id, cinemaid, sessionid, cencor, selectedtickettypes);
            Call<NovoSeatingModel> call = loginService.getSeating(task);
            call.enqueue(new Callback<NovoSeatingModel>() {
                @Override
                public void onResponse(Call<NovoSeatingModel> call, Response<NovoSeatingModel> response) {
                    if (mView != null) {
                        mView.dismissPb();
                        if (response.isSuccessful() && response.body() != null) {
                            mView.setNovoSeating(response.body());
                        } else if (response.code() == 417) {
                            try {
                                // JSONObject jObjError = new JSONObject(response.errorBody().string());
                                finishshowDialogbox(novoSeatingLayoutActivity, response.errorBody().string());
                                //  Toast.makeText(novoSeatingLayoutActivity, jObjError.getString("text"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                finishshowDialogbox(novoSeatingLayoutActivity, e.getMessage());
                            }
                        } else {
                            mView.onFailure(call, null, this, response);

                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<NovoSeatingModel> call, @NonNull Throwable t) {
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
    public void doNovoBlockSeating(String countryId, String cinemaId, String usersessionId, String sessionId, String selectedTicketTypes, String selectedSeats, String ticketInfo) {
        try {
            APIInterface loginService = APIClientNovo.createService(APIInterface.class);
            NovoBlockBody task = new NovoBlockBody(countryId, cinemaId, usersessionId, sessionId, selectedTicketTypes, selectedSeats, ticketInfo);
            Call<NovoBlockSeatsModel> call = null;
            try {
                call = loginService.getblockSeats(task);
                Log.e("call", "=========" + call.toString());
                call.enqueue(new Callback<NovoBlockSeatsModel>() {
                    @Override
                    public void onResponse(Call<NovoBlockSeatsModel> call, Response<NovoBlockSeatsModel> response) {
                        Log.e("response", "=========" + response.toString());
                        if (mView != null) {
                            mView.dismissPb();

                            if (response.isSuccessful() && response.body() != null) {
                                mView.setNovoBlockSeating(response.body());
                            } else {
                                mView.onFailure(call, null, this, null);

                            }

                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<NovoBlockSeatsModel> call, @NonNull Throwable t) {
                        Log.e("Throwable", "=========" + t.getMessage());
                        if (mView != null) {
                            mView.dismissPb();
                            mView.onFailure(call, null, this, null);
                        }
                    }
                });
            } catch (Exception e) {
                Log.e("Error", "=========" + e.toString());
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishshowDialogbox(final Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            alertDialog.dismiss();
                            Intent i = new Intent(context, NovoTicketSelectionActivity.class);
                            ((Activity) context).finish();
                        }
                    });

            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
        }
    }
}