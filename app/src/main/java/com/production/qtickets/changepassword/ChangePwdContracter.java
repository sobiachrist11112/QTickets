package com.production.qtickets.changepassword;

import android.content.Context;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.ChangePwdModel;


/**
 * Created by Harris on 29-05-2018.
 */

public class ChangePwdContracter {
    //in this contractor, we are creating two interfaces for getting and setting the change password functions

    interface View extends BaseView {

        void setChangePwd(final ChangePasswordData changePwdModels);

        //void setChangePwd(ChangePwdModel response);
    }

    interface Presenter extends BasePresenter<View> {

        void getChangepwd(Context context, String user_id, String str_current_pwd, String str_confirm_pwd);
    }
}
