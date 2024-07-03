package com.production.qtickets.newsfeed;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.NewsFeedModel;

import java.util.List;

public class NewsFeedContracter {

    //interfaces for news feed list
    interface View extends BaseView {
        void setData(final List<NewsFeedModel> newsFeedModels);
    }

    interface Presenter extends BasePresenter<NewsFeedContracter.View> {
        void getData();
    }
}
