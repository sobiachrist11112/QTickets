package com.production.qtickets.activity;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.DeleteAccount;

public class NavigationContracter {


    public interface View extends BaseView {
        void setDeleteAccount(DeleteAccount response);
    }

    interface Presenter extends BasePresenter<NavigationContracter.View> {
        void deleteAccount(String userID);
    }
}
