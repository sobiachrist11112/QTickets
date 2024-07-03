package com.production.qtickets.movies.seatselection;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.SeatModel;


public class SeatSelectionContracter {
    interface View extends BaseView {
        void setSeatselection(final SeatModel seatModel);
    }

    interface Presenter extends BasePresenter<View> {
        void getSeatSelection(Context context, String showID);

    }


}
