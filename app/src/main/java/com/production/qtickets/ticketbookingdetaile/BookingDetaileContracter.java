package com.production.qtickets.ticketbookingdetaile;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.BookingDetaileModel;
import com.production.qtickets.model.WhatsAppChargeResponse;

/**
 * Created by Harsh on 6/4/2018.
 */
public class BookingDetaileContracter {
    public interface View extends BaseView {
        void setBlockSeat(final List<BookingDetaileModel> bookingDetaileModels);

        void setSeatLock(final List<BookingDetaileModel> bookingDetaileModels);

        void setCouponCode(final List<BookingDetaileModel> bookingDetaileModels);

        void setSeatLockConfirmation(final List<BookingDetaileModel> bookingDetaileModels);

        void setEventBooking(final List<BookingDetaileModel> eventBooking);

        void setnovoQticketsTicketConfirmation(final NovoBookingConfirmationModel novoBookingConfirmationModel);

        void setNovoCouponCode(final List<BookingDetaileModel> bookingDetaileModel);

        void setNovoPay(final NovoBookingConfirmationModel1 novoBookingConfirmationModel);

        void setNovoPay();

        void setNovoUpdateCouponCode(final  BookingDetaileModel bookingDetaileModel);

        void setWhatsAppPrice(WhatsAppChargeResponse whatsAppPrice);
    }

    interface Presenter extends BasePresenter<View> {
        void getBlockseat(String showid, String seats);

        void doseatLock(String transaction_id, String name, String email, String number, String prefix, String VoucherCodes);

        void doApplyCouponcode(String email, String couponcode,String androidDeviceId);

        void doSeatLockConfirmation(int wacharge ,String transactionid);

        void getEventBooking(String eventid, String ticket_id, String amount, String tkt_count, String NoOftktPerid, String camount, String email
                , String name, String phone, String prefix, String bdate, String btime, String balamount, String couponcodes);

        void donovoQticketsTicketConfirmation(String CinemId,String CinemaName,String MovieId,String MovieName,String MovieUrl,String SessionId
        ,String bookingInfoId,String userSessionID,String Show_date,String Show_time,String No_of_tickets,String service_charge
        ,String ticket_price,String total_paid,String seats_info,String seats_id);

        void doNovoApplyCouponCode(String coupon,String email);

        void doNovopay(String user_id,String CinemId,String CinemaName,String MovieId,String MovieName,String MovieUrl,String SessionId
                ,String bookingInfoId,String userSessionID,String Show_date,String Show_time,String No_of_tickets
              , String serch ,String ticket_price,String total_paid,String seats_info,String seats_id,
                       String BookingID,String Customer_name,String Customer_phone,String Customer_email,
                       String Customer_prefix,int waprice);

        void doNovoUpdateCouponCode(String coupon,String email,String booking_id);
        void getWhatsAppPrice(String country);
    }
}
