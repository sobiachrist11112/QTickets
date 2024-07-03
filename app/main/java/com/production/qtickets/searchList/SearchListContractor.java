package com.production.qtickets.searchList;

import android.content.Context;

import com.production.qtickets.interfaces.BasePresenter;
import com.production.qtickets.interfaces.BaseView;
import com.production.qtickets.model.Items;
import com.production.qtickets.model.MovieAndEventSearchResult;
import com.production.qtickets.model.SearchListItem;

/**
 * Created by Harris on 31-05-2018.
 */

public class SearchListContractor {
    //interfaces for search list and top movies

    interface View extends BaseView {

        void setSerachList(MovieAndEventSearchResult searchListItem);

        void setTopmovies(MovieAndEventSearchResult items);


    }

    interface Presenter extends BasePresenter<View> {

        void getSearch(String country, String search);

        void callTopmovies(Context context,Integer countryID,String searchquery);


    }
}
