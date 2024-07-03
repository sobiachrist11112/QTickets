package com.production.qtickets.cmspages;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.StatusModel;

/**
 * Created by Harsh on 5/16/2018.
 */
public class CMSpageContracter {
    // creating the interfaces for getter and setter of the cms data for network calls
    interface View extends BaseView {
        void setCMSData(final StatusModel statusModel);
    }

    interface Presenter extends BasePresenter<View> {
        void getCMSData(Context context);

    }
}
