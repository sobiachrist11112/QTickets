package com.production.qtickets.events.EventDetails;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.OfferList;
import com.production.qtickets.model.TicketsModel;

public class EventDetaileContracter {
    //interfaces for tickets list
    public interface View extends BaseView {
        void setTicketDetails(final TicketsModel ticketsModel);
        void setTicketDetailsss(OfferList response);
    }

    interface Presenter extends BasePresenter<View> {
        void getTicketDetails(String event_id);
        void getTicketDetailss(String eventID);
    }
}
