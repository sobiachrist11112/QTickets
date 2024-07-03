package com.production.qtickets.myprofile;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.MyProfileModel;
import com.production.qtickets.model.RegisterModel;
import com.production.qtickets.model.RegisterRequest;

import java.util.List;

/**
 * Created by Harsh on 5/16/2018.
 */
public class MyProfileContracter {

    //interfaces for user details getting from the server
    interface View extends BaseView {
        void setData(final List<MyProfileModel> response);
        void setCountryDropdownQt5(GetNationalityData response);
        void setUpdateprofile(RegisterModel response);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(Context context, String userId, String name, String email, String number, String str_country_code, String str_nationality);
        void getQT5countryDropdown();
        void updateprofile(final Context context, RegisterRequest registerRequest);
    }
}
