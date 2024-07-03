package com.production.qtickets.eventQT5;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.AllEventQT5Model;

public class EventFragmentListContracter {
    public interface View extends BaseView{
        void  setAllEvents(final AllEventQT5Model allEventQT5Model);
    }

    interface Presenter extends BasePresenter<View>{
        void  getAllEvents(int CountryID,int CategoryID, String startDate, String endDate,Integer min_price,Integer max_price);

    }
}
