package com.production.qtickets.sports;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.Liesure;

/**
 * Created by Harsh on 6/22/2018.
 */
public class SportListContracter {
    //interfaces for sports data
    public interface View extends BaseView {
        void  setSportsData(final DataModel dataModel);
      //  void setLeisureData(Liesure liesure,boolean isfirst);
        void setLeisureData(Liesure liesure);
    }

    interface Presenter extends BasePresenter<SportListContracter.View> {
        void  getSportsData(String country_name,String category_id);
        void getLeisureData(int type,int pagenumber);
      /*  void getLeisureData(int type,int pagenumber,boolean firstpage);*/
    }
}
