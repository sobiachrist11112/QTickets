package com.production.qtickets.dashboard;

import java.util.List;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.AllCountryQT5Model;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.model.BannerModel;
import com.production.qtickets.model.CategoryModel;
import com.production.qtickets.model.DashboardModel;
import com.production.qtickets.model.GetNationalityData;
import com.production.qtickets.model.GetNewBannerEvents;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.model.StaticLinksDataModel;
import com.production.qtickets.model.StaticPageLinksModel;
import com.production.qtickets.model.TokenModel;

/**
 * Created by Harsh on 5/15/2018.
 */
public class DashboardContracter {
    //interfaces for locations and dashboard data, that is movies, events, sports......
    public interface View extends BaseView {
        void setLocationData(final List<CategoryModel> categoryModel);
        void setStaticLinksdata(final StaticLinksDataModel staticLinksdata);
        void  setDashboardData(final DashboardModel dashboardModel);
        void  setNewDashboardData(final DashboardModel dashboardModel);
        void setBanners(final BannerModel bannerModel);
        void setBannerByNewEvents(final GetNewBannerEvents getNewBannerEvents);
        void setToken(TokenModel tokenModel);
        void setReelCinemaList(ReelCinemaMovieListModel response);
        void setCountry(AllCountryQT5Model response);
        void setCountryDropdownQt5(GetNationalityData response);
        void  setAllEvents(final AllEventQT5Model allEventQT5Model);
    }

    interface Presenter extends BasePresenter<View> {
        void getLocationData();
        void getStaticLinksdata();
        void  getDashboardData(String country_name);
        void  getNewDashboardData(String country_name);

        void getReelCinema();
        void getCountry();
        void getBanners(String country);
        void getToken(String token,String countryname);
        void getQT5countryDropdown();
        void  getAllEvents(int CountryID,int CategoryID, String startDate, String endDate,Integer min_price,Integer max_price);
        void getBannersByNewEvents(String country);

    }

}
