package com.production.qtickets.eventBookingPaymentSummaryQT5;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.BookingQT5Model;
import com.production.qtickets.model.CreateDTCMUserModel;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.VerifyBookingDetail;

import okhttp3.RequestBody;

public class EventBookingPaymentSummaryQT5Contracter {

    public interface View extends BaseView{
        void  BookTickets(final BookingQT5Model bookingQT5Model,boolean isDTCM);
        void setCountry(AllCountryQT5Model response);
        void setCountryDropdownQt5(GetNationalityData response);
        void setVerifyBooking(VerifyBookingDetail response,String jsonxml);

        void setUserCreateDTCMData(CreateDTCMUserModel response);
    }

    interface Presenter extends BasePresenter<View>{
        void  BookTickets(String bookingXML,boolean isDTCM);
        void  getCountry();
        void getQT5countryDropdown();
        void verifyBooking(String bookingxml,String bookingdataxml);
        void createUserForDTCM(RequestBody bookindxml);
    }
}
