package com.production.qtickets.ticketconfirmation;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.TicketConfirmItem;
import com.production.qtickets.model.TicketConfirmationEventModel;
import com.production.qtickets.model.UserReviewModel;

import java.util.List;

/**
 * Created by Harsh on 6/27/2018.
 */
public class TicketConfirmationContracter {
    public interface View extends BaseView {
        void setEventTicketConfirmation(final List<TicketConfirmationEventModel> bookingDetaileModels);
        void setuserreviews(final List<UserReviewModel> userReviewModel);
        void setMovieTicketConfirmation(final TicketConfirmItem bookingDetaileModels);
        }

    interface Presenter extends BasePresenter<TicketConfirmationContracter.View> {
        void getEventTicketConfirmation(String order_id);
        void adduserreviews(String movie_id, String user_id, String user_review, String user_rating, String review_title);
        void getMovieTicketConfirmation(String order_id);
        }
}
