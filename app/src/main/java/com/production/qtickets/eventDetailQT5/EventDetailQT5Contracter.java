package com.production.qtickets.eventDetailQT5;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.EventDetailQT5Model;
import com.production.qtickets.model.GetSimilarEventData;

import java.util.List;

public class EventDetailQT5Contracter {
    public interface View extends BaseView{
        void  setEventDetail(final EventDetailQT5Model eventDetailQT5Model);
        void  setSimilarEvents(final GetSimilarEventData getSimilarEventData);
    }

    interface Presenter extends BasePresenter<View>{
        void  getEventDetail(int ID);
        void  getSimilarEvents(int eventID);
    }
}
