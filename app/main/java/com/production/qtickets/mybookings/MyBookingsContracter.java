package com.production.qtickets.mybookings;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.GetHistoryData;
import com.production.qtickets.model.Items;

import java.util.ArrayList;

public class MyBookingsContracter {
    //interfaces for movies and events booking details
    interface View extends BaseView {
        void setData(final Items myBookingsModelList);
        void setEventsData(final ArrayList<GetHistoryData> myBookingsModelListss);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(Context context, String userId);
        void getEvents(Context context, String userId);


    }
}
