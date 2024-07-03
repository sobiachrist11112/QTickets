package com.production.qtickets.events;

import android.content.Context;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.EventModel;
import com.production.qtickets.utils.Events;

public class EventsContracter {
    public interface View extends BaseView {
        void setEventData(final EventModel eventModel);
        void setFilterEventData(final EventModel eventModel);
        void setDetailFilterEventData(final EventModel eventData);
        void setFreeToGoData(final DataModel eventModel);

    }

    interface Presenter extends BasePresenter<View> {
        void getEventData(Context context, String country_name);
        void getEventFilterData(Context context, String country_name, String range);
        void getDetailEventFilterData(Context context,String minPrice,String maxPrice,String startDate,
                                      String endDate,String country);
        void getFreeToGoEvent(String country_name,String category_id);
    }



}
