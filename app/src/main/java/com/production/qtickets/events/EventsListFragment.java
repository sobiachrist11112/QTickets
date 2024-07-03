package com.production.qtickets.events;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar;
import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.model.DataModel;
import com.production.qtickets.model.EventData;
import com.production.qtickets.model.EventModel;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.utils.TextviewBold;
import com.production.qtickets.adapters.FilterAdapter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.searchList.SearchListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class EventsListFragment extends Fragment implements EventsContracter.View, View.OnClickListener {
    List<EventData> eventList = new ArrayList<>();
    // List<EventFilterData> eventList = new ArrayList<>();web
    RecyclerView.LayoutManager mvennuelayoutmanager, mLayoutManager;
    String selectedCountry = "", txt_sel_vennue;
    EventsListAdapter eventsListAdapter;
    ArrayList<String> vennues = new ArrayList<>();
    HashSet<String> hashSet_remove_duplicated_items = new HashSet<String>();
    Dialog vennuDialog, filterdialog;
    FilterAdapter filter_adapter;
    BubbleThumbRangeSeekbar range_seekbar;
    SessionManager sessionManager;
    EventsPresenter presenter;
    @BindView(R.id.eventlist_recyclerview)
    @Nullable
    RecyclerView eventlistRecyclerview;

    @BindView(R.id.lin_filter)
    @Nullable
    LinearLayout linFilter;

    @BindView(R.id.lin_vennue)
    LinearLayout linVennue;
    @BindView(R.id.filter_layout)
    @Nullable
    LinearLayout filterLayout;

    @BindView(R.id.tv_all_events)
    TextviewBold tv_all_events;

    @BindView(R.id.tv_this_week)
    TextviewBold tv_this_week;

    @BindView(R.id.tv_this_month)
    TextviewBold tv_this_month;

    Unbinder unbinder;
    TextView txt_cancel, txt_done, txt_header_title, txt_min, txt_max;
    RecyclerView vennu_recyclerview;
    ImageView iv_back_arrow;

    String range = "";
    @BindView(R.id.nested_scroll)
    NestedScrollView nestedScroll;
    @BindView(R.id.img_no_bookings)
    ImageView imgNoBookings;
    @BindView(R.id.no_txt_available)
    TextviewBold noTxtAvailable;
    @BindView(R.id.c1)
    ConstraintLayout c1;
    @BindView(R.id.no_txt_available1)
    TextView no_txt_available;


    ImageView ic_home, menu_icon, iv_search,ic_movies,ic_event;
    private  TextView tv_hometittle,tv_moviewtittle,tv_eventtittle,tv_more;


    String startDate = "", endDate = "", startPrice = "0", endPrice = "0";
    String dayNameToPass = ""; //TODO Display day name
    String eventHeader,categoryId;
    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;
    Handler handler;
    private  LinearLayout ll_home,ll_movies,ll_events,ll_more;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
//        GlobalBus.getBus().register(this);
        presenter = new EventsPresenter();
        presenter.attachView(this);

        ic_home =(ImageView) view.findViewById(R.id.ic_home);
        ic_event =(ImageView) view.findViewById(R.id.ic_event);
        ic_movies =(ImageView) view.findViewById(R.id.ic_movies);

        ll_home=view.findViewById(R.id.ll_home);
        ll_movies=view.findViewById(R.id.ll_movies);
        ll_events=view.findViewById(R.id.ll_events);
        ll_more=view.findViewById(R.id.ll_more);

        ll_home.setOnClickListener(this);
        ll_movies.setOnClickListener(this);
        ll_events.setOnClickListener(this);
        ll_more.setOnClickListener(this);

        tv_hometittle=view.findViewById(R.id.tv_hometittle);
        tv_moviewtittle=view.findViewById(R.id.tv_moviewtittle);
        tv_eventtittle=view.findViewById(R.id.tv_eventtittle);
        tv_more=view.findViewById(R.id.tv_more);



        ic_home.setOnClickListener(this);
        ic_event.setOnClickListener(this);
        ic_movies.setOnClickListener(this);
        menu_icon = view.findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(this);
        iv_search = view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        if(getArguments() != null){
            eventHeader = getArguments().getString("EventHeader");
            categoryId = getArguments().getString("categoryId");
        }

        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
        setAllHomescreenicons();
        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = prefmPrefs.edit();
        editor.putString(AppConstants.TOTAL_TICKET_COUNT, "0");
        editor.putString(AppConstants.TOTAL_TICKET_COST, "0");
        if(eventHeader != null && eventHeader.equals("Events")){
            linFilter.setVisibility(View.VISIBLE);
        } else {
            linFilter.setVisibility(View.GONE);
        }
        editor.commit();
        handler = new Handler();
        return view;
    }


    private void setAllHomescreenicons() {
        ic_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        tv_hometittle.setTextColor(getResources().getColor(R.color.plane_white));

        ic_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movies));
        tv_moviewtittle.setTextColor(getResources().getColor(R.color.plane_white));


        ic_event.setImageDrawable(getResources().getDrawable(R.drawable.ic_eventyellow));
        tv_eventtittle.setTextColor(getResources().getColor(R.color.colorAccent));

        menu_icon.setImageDrawable(getResources().getDrawable(R.drawable.more));
        tv_more.setTextColor(getResources().getColor(R.color.plane_white));

    }

//    @Subscribe
//    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
//        txt_sel_vennue = activityFragmentMessage.getMessage();
//        vennuDialog.dismiss();
//        showPb();
//        presenter.getEventData(getActivity(), sessionManager.getCountryName());
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // unregister the registered event.
//        GlobalBus.getBus().unregister(this);
//        unbinder.unbind();
//    }

    //list of venues for the events

    private void getVennues() {
        hashSet_remove_duplicated_items = new HashSet<String>();
        if (eventList.size() > 0) {
            for (int i = 0; i < eventList.size(); i++) {
                vennues.add(eventList.get(i).venue);
                hashSet_remove_duplicated_items.addAll(vennues);
                vennues.clear();
                vennues.addAll(hashSet_remove_duplicated_items);
            }
        }
    }

    //showing the list of venues dialog box
    private void VennuesPopupWindow(int animationSource) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_all_launguage, null);
        // Build the dialog
        vennuDialog = new Dialog(getActivity(), R.style.MyDialogTheme);
        vennuDialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = vennuDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        //animation
        vennuDialog.getWindow().getAttributes().windowAnimations = animationSource; //style id
        vennuDialog.show();
        vennuDialog.setCanceledOnTouchOutside(false);
        vennuinit(vennuDialog);
    }

    //populating the list of venues
    private void vennuinit(Dialog customDialog) {
        txt_cancel = customDialog.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vennuDialog.dismiss();
            }
        });
        txt_done = customDialog.findViewById(R.id.txt_done);
        vennu_recyclerview = customDialog.findViewById(R.id.laung_recyclerview);
        mvennuelayoutmanager = new LinearLayoutManager(getActivity());
        vennu_recyclerview.setLayoutManager(mvennuelayoutmanager);
        if (vennues.size() > 0) {
            filter_adapter = new FilterAdapter(vennues, getActivity(), txt_sel_vennue);
            vennu_recyclerview.setAdapter(filter_adapter);
        }

    }

    private void filterpopupwindow(int animationSource) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_event_filter, null);
        // Build the dialog
        filterdialog = new Dialog(getActivity(), R.style.MyDialogTheme);
        filterdialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = filterdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        //animation
        filterdialog.getWindow().getAttributes().windowAnimations = animationSource; //style id
        filterdialog.show();
        filterdialog.setCanceledOnTouchOutside(false);
        filterinit(filterdialog);

    }

    private void filterinit(Dialog customdialog) {
        txt_header_title = customdialog.findViewById(R.id.tv_toolbar_title);
        txt_header_title.setText(getResources().getString(R.string.filter));
        iv_back_arrow = customdialog.findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterdialog.dismiss();
            }
        });
        range_seekbar = customdialog.findViewById(R.id.range_seekbar);
        txt_min = customdialog.findViewById(R.id.txt_min);
        txt_max = customdialog.findViewById(R.id.txt_max);
        range_seekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                txt_min.setText(getString(R.string.min) + String.valueOf(minValue));
                txt_max.setText(getString(R.string.max) + String.valueOf(maxValue));
            }
        });
    }

    //get the list of events from the server and populate the events list
    @Override
    public void setEventData(EventModel eventModel) {
        dismissPb();
        eventlistRecyclerview.setVisibility(View.VISIBLE);
        try {
            eventList = eventModel.data;
            if (eventList.size() > 0) {
                c1.setVisibility(View.GONE);
                nestedScroll.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(getContext(), eventList, 200);
                eventlistRecyclerview.setAdapter(eventsListAdapter);
               // getVennues();
            }else {
                c1.setVisibility(View.VISIBLE);
                nestedScroll.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }

    }
    @Override
    public void setFreeToGoData(DataModel eventModel) {
        dismissPb();
        eventlistRecyclerview.setVisibility(View.VISIBLE);
        try {
            eventList = eventModel.data;
            if (eventList.size() > 0) {
                c1.setVisibility(View.GONE);
                nestedScroll.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(getContext(), eventList, 200,eventHeader);
                eventlistRecyclerview.setAdapter(eventsListAdapter);
                // getVennues();
            }else {
                c1.setVisibility(View.VISIBLE);
                nestedScroll.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }


    //get the list of events based on the resulf of filtering
    @Override
    public void setFilterEventData(EventModel eventModel) {
        dismissPb();
        eventlistRecyclerview.setVisibility(View.VISIBLE);
        try {
            eventList = eventModel.data;
            if (eventList.size() > 0) {
                no_txt_available.setVisibility(View.GONE);
                eventlistRecyclerview.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(getContext(), eventList, 200);
                eventlistRecyclerview.setAdapter(eventsListAdapter);
            }else {
                no_txt_available.setVisibility(View.VISIBLE);
                eventlistRecyclerview.setVisibility(View.GONE);
            }
           // getVennues();
        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }

    // by passing the start date,end date, start price and endprice event filter will happen,
    //based on the above conditions get the data and populate the list
    @Override
    public void setDetailFilterEventData(EventModel eventModel) {
        dismissPb();
        try {

            eventList = eventModel.data;
            if (eventList.size() > 0) {
                eventlistRecyclerview.setVisibility(View.VISIBLE);
                eventsListAdapter = new EventsListAdapter(getContext(), eventList, 200);
                eventlistRecyclerview.setAdapter(eventsListAdapter);
            }else {
                eventlistRecyclerview.setVisibility(View.GONE);
                showToast("no data found");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }

    }



    // initialize the views in the activity
    @Override
    public void init() {

        imgNoBookings.setImageResource(R.drawable.ic_no_events);
        noTxtAvailable.setText(getString(R.string.no_events));
        tv_all_events.setOnClickListener(this);
        tv_this_week.setOnClickListener(this);
        tv_this_month.setOnClickListener(this);

        sessionManager = new SessionManager(getActivity());
        if (selectedCountry != null) {
            if(eventHeader != null && eventHeader.equals("Events")){
                presenter.getEventData(getActivity(), sessionManager.getCountryName());
            } else {
                presenter.getFreeToGoEvent(sessionManager.getCountryName(),categoryId);
            }

        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        eventlistRecyclerview.setLayoutManager(mLayoutManager);

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
        }else {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            presenter.detach();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @OnClick( {R.id.lin_filter, R.id.lin_vennue, R.id.filter_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            // filtering will happen based on the below parematers
            case R.id.lin_filter:
                //filterpopupwindow(R.style.DialogAnimation);
                Intent filterIntent = new Intent(getContext(), EventFilterActivity.class);
                filterIntent.putExtra("startDate", "");
                filterIntent.putExtra("endDate", "");
                filterIntent.putExtra("startPrice", "0");
                filterIntent.putExtra("endPrice", "0");
                startActivityForResult(filterIntent, 1);
                break;
            case R.id.lin_vennue:
                VennuesPopupWindow(R.style.DialogAnimation);
                break;
            case R.id.filter_layout:
                break;
        }
    }

    // getting the user input from the user and calling the api for result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 1 && resultCode == RESULT_OK && data != null)) {
            startPrice = data.getStringExtra("startPrice");
            endPrice = data.getStringExtra("endPrice");
            startDate = data.getStringExtra("startDate");
            dayNameToPass = data.getStringExtra("dayName");
            endDate = data.getStringExtra("endDate");
            //            eventList.clear();
            if (selectedCountry != null) {
                showPb();
                presenter.getDetailEventFilterData(getActivity(),startPrice,endPrice,startDate,endDate, sessionManager.getCountryName());
            }

        }

    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent i;

        switch (id) {

            case R.id.tv_all_events:
             //   linFilter.setVisibility(View.VISIBLE);
                eventList.clear();
                range = "all";
                if (selectedCountry != null) {
                    if(eventHeader != null && eventHeader.equals("Events")){
                        linFilter.setVisibility(View.VISIBLE);
                        showPb();
                        presenter.getEventData(getActivity(), sessionManager.getCountryName());
                       // presenter.getEventFilterData(getActivity(), sessionManager.getCountryName(), range);
                    } else {
                        linFilter.setVisibility(View.GONE);
                        presenter.getFreeToGoEvent(sessionManager.getCountryName(),categoryId);
                    }
                }
                mLayoutManager = new LinearLayoutManager(getActivity());
                eventlistRecyclerview.setLayoutManager(mLayoutManager);

                tv_all_events.setBackground(getResources().getDrawable(R.drawable.button_background));
                tv_this_week.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                tv_this_month.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                tv_all_events.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_this_week.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_this_month.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.tv_this_week:
                linFilter.setVisibility(View.GONE);
                eventList.clear();
                range = "week";
                if (selectedCountry != null) {
                    if(eventHeader != null && eventHeader.equals("Events")){
                        showPb();
                        presenter.getEventFilterData(getActivity(), sessionManager.getCountryName(), range);
                    } else {
                        presenter.getFreeToGoEvent(sessionManager.getCountryName(),categoryId);
                    }

                }
                mLayoutManager = new LinearLayoutManager(getActivity());
                eventlistRecyclerview.setLayoutManager(mLayoutManager);

                tv_this_week.setBackground(getResources().getDrawable(R.drawable.button_background));
                tv_all_events.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                tv_this_month.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                tv_this_week.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_all_events.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_this_month.setTextColor(getResources().getColor(R.color.colorWhite));
                break;

            case R.id.tv_this_month:
                linFilter.setVisibility(View.GONE);
                eventList.clear();
                range = "month";
                if (selectedCountry != null) {
                    if(eventHeader != null && eventHeader.equals("Events")){
                        showPb();
                        presenter.getEventFilterData(getActivity(), sessionManager.getCountryName(), range);
                    } else {
                        presenter.getFreeToGoEvent(sessionManager.getCountryName(),categoryId);
                    }
                }
                mLayoutManager = new LinearLayoutManager(getActivity());
                eventlistRecyclerview.setLayoutManager(mLayoutManager);

                tv_this_month.setBackground(getResources().getDrawable(R.drawable.button_background));
                tv_all_events.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                tv_this_week.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                tv_this_month.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_all_events.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_this_week.setTextColor(getResources().getColor(R.color.colorWhite));
                break;

            case R.id.ll_home:
                Intent intent = new Intent(requireActivity(), DashBoardActivity.class);
                startActivity(intent);
                requireActivity().finish();
                break;

            case R.id.ll_movies:
                Intent intentsss = new Intent(getActivity(), MainActivity.class);
                intentsss.putExtra("categoryId", "15");
                //   intent.putStringArrayListExtra("headerList", heading);
                intentsss.putExtra("position", 0);
                intentsss.putExtra("ImageType", "others");
                startActivity(intentsss);
                requireActivity().finish();
                break;

            case R.id.ll_events:
                i = new Intent(getActivity(), MainActivity.class);
                i.putExtra("position",0);
                startActivity(i);
                requireActivity().finish();
                break;

            case R.id.ll_more:
                i = new Intent(getActivity(), NavigationDrawerActivity.class);
                startActivity(i);
                break;

//            case R.id.ic_event:
//                i = new Intent(getActivity(), MainActivity.class);
//                i.putExtra("position",0);
//                startActivity(i);
//                break;
//
//            case R.id.ic_movies:
//                Intent intentss = new Intent(getActivity(), MainActivity.class);
//                intentss.putExtra("categoryId", "15");
//             //   intent.putStringArrayListExtra("headerList", heading);
//                intentss.putExtra("position", 0);
//                intentss.putExtra("ImageType", "others");
//                startActivity(intentss);
//                break;
//
//
//            case R.id.ic_home:
//                i = new Intent(getActivity(), DashBoardActivity.class);
//                startActivity(i);
//                break;
//            case R.id.menu_icon:
//
//                i = new Intent(getActivity(), NavigationDrawerActivity.class);
//                startActivity(i);
//                break;
            case R.id.iv_search:


                i = new Intent(getActivity(), SearchListActivity.class);
                startActivity(i);

                break;


        }
    }
}
