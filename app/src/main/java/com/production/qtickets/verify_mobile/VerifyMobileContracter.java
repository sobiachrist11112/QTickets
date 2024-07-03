package com.production.qtickets.verify_mobile;

import android.content.Context;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.VerifyMobileModel;

/**
 * Created by Harris on 05-06-2018.
 */

public class VerifyMobileContracter {
    //intefaces for verify mobile number and otp

    interface View extends BaseView {

        void setVerified(final List<VerifyMobileModel> verifyMobileModels);

        void setOtp(final List<VerifyMobileModel> verifyMobileModelList);
    }


    interface Presenter extends BasePresenter<View> {

        void getOtp(Context context, String user_id, String mobileno, String country);

        void getVerify(Context context, String country, String mobileno, String str_otp);
    }
}
