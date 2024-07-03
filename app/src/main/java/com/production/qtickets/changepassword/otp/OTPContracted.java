package com.production.qtickets.changepassword.otp;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.ChangePasswordData;
import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.ResetPasswordSucessFully;

import okhttp3.RequestBody;

public class OTPContracted {

    interface View extends BaseView {
        void setChangePwd(final ResetPasswordSucessFully changePwdModels);
        void setForgotPassword(LoginResponse forgotPasswordData);
    }

    interface Presenter extends BasePresenter<OTPContracted.View> {
        void changedForgotPassword(RequestBody requestBody);
        void getForgotPasswprd(Context context, String username);
    }

}
