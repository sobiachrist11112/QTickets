package com.production.qtickets.signup;

import android.content.Context;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.RegisterRequest;

/**
 * Created by Harris on 28-05-2018.
 */

public class SignUpContracter {
    //interfaces for registrations

    interface View extends BaseView {
        void setRegister(final RegisterModel registerModels);
    }

    interface Presenter extends BasePresenter<View> {
//        void getRegister(Context context, String username, String email, String phoneno,
//                         String password, String rep_password,String country, String gender,String deviceId);

                void getRegister(Context context, RegisterRequest registerRequest);
    }
}
