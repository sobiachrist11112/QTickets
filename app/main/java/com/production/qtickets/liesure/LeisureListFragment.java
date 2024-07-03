package com.production.qtickets.liesure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.Liesure;
import com.production.qtickets.model.LiesureData;
import com.production.qtickets.searchList.SearchListActivity;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.model.EventData;
import com.production.qtickets.events.EventsListAdapter;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.sports.SportListContracter;
import com.production.qtickets.sports.SportListPresenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hemanth on 4/12/2018.
 */

public class LeisureListFragment extends Fragment implements SportListContracter.View, View.OnClickListener  {

    @BindView(R.id.sports_list_recyclerview)
    RecyclerView sportsListRecyclerview;
    @BindView(R.id.img_no_bookings)
    ImageView imgNoBookings;
    @BindView(R.id.no_txt_available)
    TextviewBold noTxtAvailable;
    @BindView(R.id.c1)
    ConstraintLayout c1;
    @BindView(R.id.l1_filter_layout)
    LinearLayout l1FilterLayout;
    Unbinder unbinder;
    //presenter
    SportListPresenter presenter;
    //layoutmanager
    LinearLayoutManager mLayoutManager;
    //sessionmanager
    SessionManager sessionManager;
    //string
    String country_name;
    //context
    Context context;
    //adapter
    EventsListAdapter adapter = new EventsListAdapter();

    ImageView ic_home, menu_icon, iv_search;
    String nextpage;
    int currentpage=0,totalpage,pagesize,totalItemcount;
    boolean mIsLoading,mIsLastPage,isFirstpage;
    RelativeLayout progress_bar;
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_list_fragment, container, false);
        context = getActivity();
        unbinder = ButterKnife.bind(this, view);
        presenter = new SportListPresenter();
        presenter.attachView(this);
        progress_bar = view.findViewById(R.id.progress_bar);
        ic_home = view.findViewById(R.id.ic_home);
        ic_home.setOnClickListener(this);
        menu_icon = view.findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(this);
        iv_search = view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        handler = new Handler();
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //set date for leisures based on the category id
    @Override
    public void setSportsData(DataModel dataModel) {
        try {
            List<EventData> eventList = dataModel.data;
            if (eventList.size() > 0) {
                sportsListRecyclerview.setVisibility(View.VISIBLE);
                c1.setVisibility(View.GONE);
                l1FilterLayout.setVisibility(View.GONE);
                adapter = new EventsListAdapter(getActivity(), eventList, 200);
                sportsListRecyclerview.setAdapter(adapter);
            } else {
                l1FilterLayout.setVisibility(View.GONE);
                sportsListRecyclerview.setVisibility(View.GONE);
                c1.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setLeisureData(Liesure liesure) {
        List<EventData> eventList = liesure.tourDetails;
        if (eventList.size() > 0) {
            //currentpage =  liesure.pagingDetails.currentPage;
            totalpage = liesure.pagingDetails.totalPages;
            pagesize = liesure.pagingDetails.pageSize;
            nextpage = liesure.pagingDetails.nextPage;
            totalItemcount = liesure.pagingDetails.totalCount;
            sportsListRecyclerview.setVisibility(View.VISIBLE);
            c1.setVisibility(View.GONE);
            l1FilterLayout.setVisibility(View.GONE);
             // adapter = new EventsListAdapter(getActivity(), eventList, 200);
            if(!country_name.equals("Dubai")){
                adapter = new EventsListAdapter(getActivity(), eventList, 200);
            } else {
                if(!isFirstpage){
                    adapter.addAll(getActivity(), eventList, 200);
                } else {
                    progress_bar.setVisibility(View.GONE);
                    sportsListRecyclerview.getLayoutManager().scrollToPosition(sportsListRecyclerview.getAdapter().getItemCount());
                    adapter.setList(getActivity(), eventList, 200);
                }
            }
            sportsListRecyclerview.setAdapter(adapter);

        } else {
            l1FilterLayout.setVisibility(View.GONE);
            sportsListRecyclerview.setVisibility(View.GONE);
            c1.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void init() {
        sessionManager = new SessionManager(context);
        country_name = sessionManager.getCountryName();
        imgNoBookings.setImageResource(R.drawable.ic_no_events);
        noTxtAvailable.setText(getString(R.string.no_leisure));
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sportsListRecyclerview.setLayoutManager(mLayoutManager);
        if(country_name.equals("Dubai")){
            loadMoreItems(country_name);
        }else {
            loadMoreItems("Others");
        }

        sportsListRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)) {
                    if(country_name.equals("Dubai")){
                        loadMoreItems(null);
                    }
                }
            }
        });

    }

    public void loadMoreItems(String firstapi) {
        if(firstapi != null && firstapi.equals("Others")){
            presenter.getSportsData(country_name,"12");
        }else {
            if(firstapi != null && firstapi.equals("Dubai")){
                presenter.getSportsData(country_name,"12");
                nextpage ="Yes";
            } else {
                    currentpage = currentpage + 1;
                    isFirstpage = true;
                    if (nextpage != null && nextpage.equals("Yes")) {
                        progress_bar.setVisibility(View.VISIBLE);
                    } else {
                        progress_bar.setVisibility(View.GONE);
                    }
                    presenter.getLeisureData(12,currentpage);

            }
        }

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent i;
        switch (id) {
            case R.id.ic_home:
                i = new Intent(getActivity(), DashBoardActivity.class);
                startActivity(i);
                break;
            case R.id.menu_icon:
                i = new Intent(getActivity(), NavigationDrawerActivity.class);
                startActivity(i);
                break;
            case R.id.iv_search:
                i = new Intent(getActivity(), SearchListActivity.class);
                startActivity(i);
                break;
        }
    }


    @Override
    public void dismissPb() {
        final Runnable r = new Runnable() {
            public void run() {
                if (QTUtils.progressDialog != null && QTUtils.progressDialog.isShowing()) {
                    QTUtils.progressDialog.dismiss();
                }
            }
        };
        handler.postDelayed(r, 3000);
    }

    @Override
    public void showPb() {
        QTUtils.showmovies_ProgressDialog(getActivity(), true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
        Activity activity = getActivity();
        if(activity != null) {
            if (throwable instanceof SocketTimeoutException) {
                //  showToast("Socket Time out. Please try again.");
                showRetryDialog(call, callback, getResources().getString(R.string.internet));
            } else if (throwable instanceof UnknownHostException) {
                // showToast("No Internet");
                showRetryDialog(call, callback, getResources().getString(R.string.internet));
            } else if (throwable instanceof ConnectException) {
                showRetryDialog(call, callback, getResources().getString(R.string.internet));
            } else {
                showToast(getResources().getString(R.string.noresponse));
            }
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
    }
    @Override
    public void onDestroy(){
        try {
            super.onDestroy();
            QTUtils.dismissProgressDialog();
            presenter.detach();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
