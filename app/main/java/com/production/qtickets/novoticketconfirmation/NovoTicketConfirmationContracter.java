package com.production.qtickets.novoticketconfirmation;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.NovoTicketConfirmationModel;
import com.production.qtickets.model.NovoTicketDetailesModel;
import com.production.qtickets.model.NovoTicketPaymentModel;
import com.production.qtickets.model.NovoTicketPaymentStausModel;
import com.production.qtickets.model.UserReviewModel;

import java.util.List;

/**
 * Created by Harsh on 7/20/2018.
 */
public class NovoTicketConfirmationContracter {
    public interface View extends BaseView {
        void setMovieTicketConfirmationStatus(final NovoTicketConfirmationModel bookingDetaileModels);
        void  setNovoPaymentResponse(final List<NovoTicketPaymentModel> novoPaymentResponse);
        void setuserreviews(final List<UserReviewModel> userReviewModel);
        void set_qtickets_novo_confirmation(final NovoTicketPaymentStausModel novoTicketPaymentModel);
        void set_qtickets_novo_ticket_detailes(final NovoTicketDetailesModel novo_ticket_detailes);
    }

    interface Presenter extends BasePresenter<NovoTicketConfirmationContracter.View> {
        void getMovieTicketConfirmationStatus(String order_id);
        void getNovoPaymentResponse(String userseesionid,String c_name,String c_email,String c_phone_number,String c_prifix);
        void adduserreviews(String movie_id, String user_id, String user_review, String user_rating, String review_title);
        void do_qtickets_novo_confirmation(String novo_id,String Screen_name,String Language,String Rating,String bookID,
                                           String Cinema_Class,String barcodeImgUrl);
        void do_qtickets_novo_ticket_detailes(String booking_id);
    }
}
