package com.production.qtickets.eventQT5;
import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.AllCategoryQT5Model;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.model.CarouselBannerModel;
import com.production.qtickets.model.DashboardModel;

public class EventHomeContracter{
    public interface View extends BaseView{
        void setCarouselBanners(final CarouselBannerModel carouselBanners);
        void setCarouselBannersAgain(final CarouselBannerModel carouselBanners);
        void  setAllCategories(final AllCategoryQT5Model allCategoryQT5Model);
        void setCountry(AllCountryQT5Model response);
    }

    interface Presenter extends BasePresenter<View>{
        void getCarouselBanners(int countryID,String categoryID);
        void getCarouselBannersAgain(int countryID,String categoryID);
        void getCountry();
        void  getAllCategories();
    }
}
