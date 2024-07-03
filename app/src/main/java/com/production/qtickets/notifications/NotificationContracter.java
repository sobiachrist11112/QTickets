package com.production.qtickets.notifications;
import java.util.List;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.NotificationModel;


public class NotificationContracter {
    //interfaces for notifications list
    interface View extends BaseView {
        void setData(final List<NotificationModel> notificationModel);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(String country);
    }
}
