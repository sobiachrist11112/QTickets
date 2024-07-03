package com.production.qtickets.eventBookingPaymentSummaryQT5;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.BookingQT5Model;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.VerifyBookingDetail;

public class EventBookingPaymentSummaryQT5Contracter {

    public interface View extends BaseView{
        void  BookTickets(final BookingQT5Model bookingQT5Model);
        void setCountry(AllCountryQT5Model response);
        void setCountryDropdownQt5(GetNationalityData response);
        void setVerifyBooking(VerifyBookingDetail response,String jsonxml);
    }

    interface Presenter extends BasePresenter<View>{
        void  BookTickets(String bookingXML);
        void  getCountry();
        void getQT5countryDropdown();
        void verifyBooking(String bookingxml,String bookingdataxml);
    }
}
