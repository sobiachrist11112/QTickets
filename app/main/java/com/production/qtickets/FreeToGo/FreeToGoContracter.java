package com.production.qtickets.FreeToGo;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;

public class FreeToGoContracter {

    public interface View extends BaseView{

    }
    interface Presenter extends BasePresenter<FreeToGoContracter.View>{

    }

}
