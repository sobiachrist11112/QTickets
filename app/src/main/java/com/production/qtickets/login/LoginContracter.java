package com.production.qtickets.login;

import android.content.Context;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;


import com.production.qtickets.model.LoginResponse;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.UserINfo;

/**
 * Created by Harsh on 5/16/2018.
 */
public class LoginContracter {
    interface View extends BaseView {
        void setRegister(final RegisterModel registerModels);

        void setlogin(final LoginResponse response);

        void setForgotPassword(LoginResponse forgotPasswordData);

        void setUserDetails(UserINfo userDetails);
    }

    interface Presenter extends BasePresenter<View> {
        void getRegister(String username, String lname, String email, String phoneno,
                         String password, String rep_password, String fid, String country, String gender, String androidDeviceId);

        void getLogin(Context context, String username, String password);

        void getForgotPasswprd(Context context, String username);

        void getLoginFacebook(String facebookjson);

        void getGmailLogin(String gmailjson);

        void getUserDetails(String userID);

    }
}
