package com.production.qtickets.events;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.EventTicketPriceRangeModel;

public class FilterPriceRangeContracter {
    interface View extends BaseView {
        void setPriceRangeData(final EventTicketPriceRangeModel eventTicketPriceRangeModel);
    }

    interface Presenter extends BasePresenter<FilterPriceRangeContracter.View> {
        void getPriceRangeData(Context context);

    }
}
