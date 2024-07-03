package com.production.qtickets.novocinema.seatinglayout;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.NovoBlockSeatsModel;
import com.production.qtickets.model.NovoSeatingModel;

/**
 * Created by Harsh on 7/13/2018.
 */
public class NovoSeatingContracter {
    interface View extends BaseView {
        void setNovoSeating(final NovoSeatingModel novoTicketSeletionModels);
        void setNovoBlockSeating(final NovoBlockSeatsModel novoBlockSeatsModel);
    }

    interface Presenter extends BasePresenter<NovoSeatingContracter.View> {

        void getNovoSeating(NovoSeatingLayoutActivity novoSeatingLayoutActivity, String cinemaid, String sessionid, String cencor, String selectedtickettypes);
        void doNovoBlockSeating(String countryId,String cinemaId, String usersessionId, String sessionId, String selectedTicketTypes, String selectedSeats, String ticketInfo);

    }
}
