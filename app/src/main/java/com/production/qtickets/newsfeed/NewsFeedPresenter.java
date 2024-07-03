package com.production.qtickets.newsfeed;

import androidx.annotation.NonNull;

import com.production.qtickets.interfaces.APIClient;
import com.production.qtickets.interfaces.APIInterface;
import com.production.qtickets.model.NewsFeedModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsFeedPresenter implements NewsFeedContracter.Presenter {
    private NewsFeedContracter.View mView;


    @Override
    public void attachView(NewsFeedContracter.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    //get the news feed from the server
    @Override
    public void getData() {
        try {
            APIClient.getClient()
                    .create(APIInterface.class)
                    .getNewsFeeds()
                    .enqueue(new Callback<List<NewsFeedModel>>() {
                        @Override
                        public void onResponse(
                                @NonNull Call<List<NewsFeedModel>> call, @NonNull retrofit2.Response<List<NewsFeedModel>> response
                        ) {
                            if (mView != null) {
                                mView.dismissPb();
                                if (response.isSuccessful() && response.body() != null) {
                                    mView.setData(response.body());
                                } else {
                                    mView.onFailure(call, null, this, null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<NewsFeedModel>> call, @NonNull Throwable t) {
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
