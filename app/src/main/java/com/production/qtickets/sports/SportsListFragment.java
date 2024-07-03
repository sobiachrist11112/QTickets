package com.production.qtickets.sports;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.Liesure;
import com.production.qtickets.searchList.SearchListActivity;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.model.EventData;
import com.production.qtickets.events.EventsListAdapter;

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

public class SportsListFragment extends Fragment implements SportListContracter.View, View.OnClickListener {

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
    EventsListAdapter adapter;
    String eventHeader,categoryId;

    ImageView ic_home, menu_icon, iv_search;
    String selectedCountry = "";

    String txt_sel_launguage = "", txt_sel_cinema = "", sel_id = "";
    Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_list_fragment, container, false);
        context = getActivity();
        unbinder = ButterKnife.bind(this, view);
        presenter = new SportListPresenter();
        presenter.attachView(this);

        ic_home = view.findViewById(R.id.ic_home);
        ic_home.setOnClickListener(this);
        menu_icon = view.findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(this);
        iv_search = view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        handler = new Handler();
       if(getArguments() != null){
            eventHeader = getArguments().getString("EventHeader");
            categoryId = getArguments().getString("categoryId");
        }
        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        QTUtils.dismissProgressDialog();
        super.onDestroyView();
        unbinder.unbind();
    }
    //parse the data from the api response and list eh sports which are available

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

    }

    //initialize the views
    @Override
    public void init() {
        sessionManager = new SessionManager(context);
        country_name = sessionManager.getCountryName();
        imgNoBookings.setImageResource(R.drawable.ic_no_events);
       /* noTxtAvailable.setText(getString(R.string.no_sports));*/
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        sportsListRecyclerview.setLayoutManager(mLayoutManager);
      //  presenter.getSportsData(country_name, "8");
        sessionManager = new SessionManager(getActivity());
       // if (selectedCountry != null) {
            if(eventHeader != null && eventHeader.equals("Business")){
                noTxtAvailable.setText(getString(R.string.no_business));
                presenter.getSportsData(country_name, "7");
            } else {
                noTxtAvailable.setText(getString(R.string.no_sports));
                presenter.getSportsData(country_name, "8");
            }

       // }


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

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
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
