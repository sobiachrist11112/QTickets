package com.production.qtickets.novocinema;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.NovoTicketSeletionModel;

/**
 * Created by Harsh on 7/12/2018.
 */
public class NovoTicketSelectionContracter {
    interface View extends BaseView {
        void setNovoTicketSelection(final NovoTicketSeletionModel novoTicketSeletionModels);
    }

    interface Presenter extends BasePresenter<NovoTicketSelectionContracter.View> {

       void getNovoTicketSelection(String country_id,String cinemaid,String sessionid);

    }
}
