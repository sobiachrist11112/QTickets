package com.production.qtickets.novocinema;

import androidx.annotation.NonNull;

import com.production.qtickets.model.NovoTicketSeletionModel;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.interfaces.APIClientNovo;
import com.production.qtickets.interfaces.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harsh on 7/12/2018.
 */
public class NovoTicketSelectionPresenter implements NovoTicketSelectionContracter.Presenter {

    private NovoTicketSelectionContracter.View mView;

    @Override
    public void attachView(NovoTicketSelectionContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }


    public void getNovoTicketSelection(String country_id, String cinemaid, String sessionid) {

        try {
            APIInterface loginService =
                    APIClientNovo.createService(APIInterface.class);
            NovoTicketPostBody task = new NovoTicketPostBody(AppConstants.novo_country_id, cinemaid, sessionid);
            Call<NovoTicketSeletionModel> call = loginService.getTicketDetailes(task);
            call.enqueue(new Callback<NovoTicketSeletionModel>() {
                @Override
                public void onResponse(Call<NovoTicketSeletionModel> call, Response<NovoTicketSeletionModel> response) {
                    if (mView != null) {
                        mView.dismissPb();
                        if (response.isSuccessful() && response.body() != null) {

                            mView.setNovoTicketSelection(response.body());
                        } else {
                            mView.onFailure(call, null, this, null);

                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<NovoTicketSeletionModel> call, @NonNull Throwable t) {
                    if (mView != null) {
                        mView.dismissPb();
                        mView.onFailure(call, null, this, null);
                    }
                }
            });

        } catch (Exception e)

        {
            e.printStackTrace();
        }


    }
}