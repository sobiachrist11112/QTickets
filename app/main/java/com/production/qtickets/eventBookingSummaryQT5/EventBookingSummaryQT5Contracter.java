package com.production.qtickets.eventBookingSummaryQT5;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.CouponQT5Model;
import com.production.qtickets.model.CuponCodeModel;
import com.production.qtickets.model.EventDetailQT5Model;
import com.production.qtickets.model.PromoCode;

public class EventBookingSummaryQT5Contracter {

    public interface View extends BaseView{
        void  setCouponDetail(final CuponCodeModel couponQT5Model);
        void setPromocodeDetails(final PromoCode promocodeDetails);
    }

    interface Presenter extends BasePresenter<View>{
        void  applyCoupon(String eventID,String couponCode,String email,double totalamount );
        void applyPromoCOde(int eventID,String promocde,String ticketXML);
    }
}

