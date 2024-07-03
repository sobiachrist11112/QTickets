package com.production.qtickets.eventQT5;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.production.qtickets.R;
import com.production.qtickets.model.AllEventQT5Model;
import com.production.qtickets.utils.DateTimeUtils;
import com.production.qtickets.utils.ItemOffsetDecoration;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * Created by bhagya naik on 21/05/2018.
 */

public class EventsListQT5Fragment extends Fragment implements View.OnClickListener, EventFragmentListContracter.View {

    int selection = 0;
    TextView tv_all_events, tv_this_week, tv_this_month;
    EventFragmentListPresenter presenter;
    RecyclerView event_list_recyclerview;
    int CategoryID = -1;
    int CategoryID22 = -1;
    int CategoryID33 = -1;
    int CategoryID444 = -1;
    int CategoryID555 = -1;
    int CategoryID666 = -1;

    Integer selected_cat_id = -1;
    Integer selected_min_price = 0, selected_max_price = 0;
    TextView tv_nofilterapply;
    LinearLayout ll_filterapplied, ll_filtercontainer;
    SharedPreferences prefs;
    ArrayList<String> filerapplies = new ArrayList<>();
    ImageView iv_closefilter;
    LinearLayout ll_rootfilterview;
    ImageView iv_datanotfound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle;
        bundle = getArguments();
        if (bundle != null) {
            CategoryID = bundle.getInt("CategoryID");
            CategoryID22 = bundle.getInt("CategoryID22");
            CategoryID33 = bundle.getInt("CategoryID33");
            CategoryID444 = bundle.getInt("selected_event");
            CategoryID555 = bundle.getInt("minimum_price");
        }
        prefs = requireActivity().getSharedPreferences("CategoryFilter", MODE_PRIVATE);
        if (!prefs.getString("category_id", "").equals("")) {
            selected_cat_id = Integer.valueOf(prefs.getString("category_id", ""));
            selected_min_price = prefs.getInt("minimum_price", 0);
            selected_max_price = prefs.getInt("maximum_price", 0);
            selection = prefs.getInt("selected_event", 0);
            String event_name = prefs.getString("selected_event_name", "");
        }

        if (prefs.getString("category_id", "").equals("")) {
            selected_cat_id = -1;
        }
        View view = inflater.inflate(R.layout.event_list_fragment_qt5, container, false);
        return view;
    }

    private void getPreferenceData() {

        if (prefs == null) {
            prefs = requireActivity().getSharedPreferences("CategoryFilter", MODE_PRIVATE);
        }
        filerapplies.clear();

        if (!prefs.getString("selected_event_name", "").equals("")) {
            filerapplies.add(prefs.getString("selected_event_name", ""));
        }

        if (!prefs.getString("selected_event_name", "").equals("")) {
            if (!prefs.getString("category_id", "").isEmpty() && prefs.getString("category_id", "") != null) {
                selected_cat_id = Integer.valueOf(prefs.getString("category_id", ""));
            }
        }

        if (prefs.getInt("selected_event", 0) != 0) {
            selection = prefs.getInt("selected_event", 0);
        }
        if (!prefs.getString("category_name", "").equals("")) {
            filerapplies.add(prefs.getString("category_name", ""));
        }

        if (prefs.getInt("minimum_price", 0) != 0) {
            if (prefs.getInt("maximum_price", 0) != 0) {
                filerapplies.add("QAR " + String.valueOf(prefs.getInt("minimum_price", 0)) + " - QAR " + String.valueOf(prefs.getInt("maximum_price", 0)));
            }
        }

        if (filerapplies.size() > 0) {
            ll_filterapplied.setVisibility(View.VISIBLE);
            tv_nofilterapply.setVisibility(View.GONE);
            if (ll_filtercontainer != null) {
                ll_filtercontainer.removeAllViews();
            }
            for (int i = 0; i < filerapplies.size(); i++) {
                View v = LayoutInflater.from(requireActivity()).inflate(R.layout.item_filterview, null, false);
                TextView tv_filterview = (TextView) v.findViewById(R.id.tv_filterview);
                tv_filterview.setText(filerapplies.get(i));
                ll_filtercontainer.addView(v);
            }
        } else {
            tv_nofilterapply.setVisibility(View.VISIBLE);
            ll_filterapplied.setVisibility(View.GONE);
            ll_rootfilterview.setVisibility(View.GONE);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceData();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new EventFragmentListPresenter();
        presenter.attachView(this);

        ll_filterapplied = view.findViewById(R.id.ll_filterapplied);
        ll_filtercontainer = view.findViewById(R.id.ll_filtercontainer);

        iv_closefilter = view.findViewById(R.id.iv_closefilter);
        ll_rootfilterview = view.findViewById(R.id.ll_rootfilterview);

        iv_datanotfound = view.findViewById(R.id.iv_datanotfound);
        tv_nofilterapply = view.findViewById(R.id.tv_nofilterapply);
        tv_all_events = view.findViewById(R.id.tv_all_events);
        tv_this_week = view.findViewById(R.id.tv_this_week);
        tv_this_month = view.findViewById(R.id.tv_this_month);
        event_list_recyclerview = view.findViewById(R.id.event_list_recyclerview);
        event_list_recyclerview.setHasFixedSize(true);
        event_list_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        event_list_recyclerview.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.two, R.dimen.two, R.dimen.two, R.dimen.two));

        tv_all_events.setOnClickListener(this);
        tv_this_week.setOnClickListener(this);
        tv_this_month.setOnClickListener(this);
        iv_closefilter.setOnClickListener(this);
        getPreferenceData();
        callEventListApi();

    }


    public void callEventListApi() {
        QTUtils.showProgressDialog(requireActivity(), true);
        int CountryID = new SessionManager(getContext()).getCountryID();
        switch (selection) {
            case 0: {
                presenter.getAllEvents(requireActivity(), CountryID, selected_cat_id, "", "", selected_min_price, selected_max_price);
//                presenter.getAllEvents(-1,CategoryID,"","");
            }
            break;
            case 1: {
                presenter.getAllEvents(requireActivity(), CountryID, selected_cat_id, DateTimeUtils.getTodayDate(), DateTimeUtils.getWeekLastDate(), selected_min_price, selected_max_price);
//                presenter.getAllEvents(-1,CategoryID, DateTimeUtils.getTodayDate(),DateTimeUtils.getWeekLastDate());
            }
            break;
            case 2: {
                presenter.getAllEvents(requireActivity(), CountryID, selected_cat_id, DateTimeUtils.getTodayDate(), DateTimeUtils.getMonthLastDate(), selected_min_price, selected_max_price);
//                presenter.getAllEvents(-1,CategoryID, DateTimeUtils.getTodayDate(),DateTimeUtils.getMonthLastDate());
            }
            break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_closefilter:
                SharedPreferences.Editor editor = requireActivity().getSharedPreferences("CategoryFilter", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                selection = 0;
                selected_cat_id = -1;
                selected_min_price = 0;
                selected_max_price = 0;
                ll_rootfilterview.setVisibility(View.GONE);
                callEventListApi();
                ((EventHomeActivity) getActivity()).getCarouselBanners();
                break;

            case R.id.tv_all_events: {
                if (selection == 0) return;
                selection = 0;
                tv_all_events.setBackgroundResource(R.drawable.selected_tab_with_corners);
                tv_this_week.setBackgroundResource(R.drawable.unselected_button_with_corners);
                tv_this_month.setBackgroundResource(R.drawable.unselected_button_with_corners);
                callEventListApi();
            }
            break;
            case R.id.tv_this_week: {
                if (selection == 1) return;
                selection = 1;
                tv_all_events.setBackgroundResource(R.drawable.unselected_button_with_corners);
                tv_this_week.setBackgroundResource(R.drawable.selected_tab_with_corners);
                tv_this_month.setBackgroundResource(R.drawable.unselected_button_with_corners);
                callEventListApi();
            }
            break;
            case R.id.tv_this_month: {
                if (selection == 2) return;
                selection = 2;
                tv_all_events.setBackgroundResource(R.drawable.unselected_button_with_corners);
                tv_this_week.setBackgroundResource(R.drawable.unselected_button_with_corners);
                tv_this_month.setBackgroundResource(R.drawable.selected_tab_with_corners);
                callEventListApi();
            }
            break;
        }
    }

    @Override
    public void setAllEvents(AllEventQT5Model allEventQT5Model) {

        Log.d("14nov:", "setAllEvents: Eventssss" );

        if (allEventQT5Model.statusCode.equals("200")) {
            if (allEventQT5Model.data != null && !allEventQT5Model.data.isEmpty()) {
                EventsListQT5Adapter eventsListQT5Adapter = new EventsListQT5Adapter(getContext(), allEventQT5Model.data);
                event_list_recyclerview.setAdapter(eventsListQT5Adapter);
                event_list_recyclerview.setVisibility(View.VISIBLE);
                iv_datanotfound.setVisibility(View.GONE);
            } else {
                event_list_recyclerview.setVisibility(View.GONE);
                QTUtils.showDialogbox(getContext(), "" + allEventQT5Model.message);
            }
        } else if (allEventQT5Model.statusCode.equals("404")) {
            event_list_recyclerview.setVisibility(View.GONE);
            QTUtils.showDialogbox(getContext(), "" + allEventQT5Model.message);
            iv_datanotfound.setVisibility(View.VISIBLE);
        }
        QTUtils.dismissProgressDialog();
    }

    @Override
    public void init() {

    }

    @Override
    public void dismissPb() {

    }

    @Override
    public void showPb() {
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, getContext(), message);
    }
}
