package com.production.qtickets.contactus;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.ContactResponse;


/**
 * Created by Harris on 29-05-2018.
 */

public class ContactusContracter {
    //in this contractor, we are creating two interfaces for getting and setting the change password functions

    public interface View extends BaseView {

        void onSuccessfullySendDetails(final ContactResponse contactResponse);

        //void setChangePwd(ChangePwdModel response);
    }

    interface Presenter extends BasePresenter<View> {

        void sendContactDetails(String name, String mobile, String email,String purpose,String message);
    }
}
