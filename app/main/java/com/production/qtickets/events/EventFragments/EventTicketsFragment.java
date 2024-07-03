package com.production.qtickets.events.EventFragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.OfferList;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.ticketbookingdetaile.EventBookingDetailsActivity;
import com.production.qtickets.events.EventDetails.EventDetaileContracter;
import com.production.qtickets.events.EventDetails.EventDetailePresenter;
import com.production.qtickets.events.EventDetails.TicketListAdapter;
import com.production.qtickets.model.TicketsData;
import com.production.qtickets.model.TicketsModel;

import org.greenrobot.eventbus.Subscribe;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class EventTicketsFragment extends Fragment implements EventDetaileContracter.View, View.OnClickListener {

    Unbinder unbinder;

    EventDetailePresenter presenter;


    List<TicketsModel> ticketsDataArrayList;
    List<TicketsData> dList;

    List<TicketsData> ticketsList = new ArrayList<>();

    @BindView(R.id.recycler_tickets)
    RecyclerView recycler_tickets;

    //layout manager
    RecyclerView.LayoutManager mLayoutManager;

    TicketListAdapter ticketListAdapter;


    @BindView(R.id.tv_noOfGuests)
    TextView tv_noOfGuests;

    @BindView(R.id.tv_totalAmount)
    TextView tv_totalAmount;

    @BindView(R.id.btn_select_date)
    TextView btn_select_date;

    @BindView(R.id.ll_paynow)
    LinearLayout ll_paynow;

    //string
    private String id, eventName, eventBannerUrl, eventStartTime, eventEndTime, eventVenue, str_date = "";
    int ticket_id;

    String eventID = "";

    int ticketCount = 0;
    int ticketPrice = 0;
    int totalCost = 0;
    double ticketServiceCharge = 0;
    //datepiker
    DatePickerDialog datePickerDialog;

    @SuppressLint("ValidFragment")
    public EventTicketsFragment(String id, String eventName, String eventBannerUrl, String eventStartTime, String eventEndTime, String eventVenue) {
        this.id = id;
        this.eventName = eventName;
        this.eventBannerUrl = eventBannerUrl;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventVenue = eventVenue;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_tickets_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new EventDetailePresenter();
        presenter.attachView(this);

        recycler_tickets.setItemViewCacheSize(9);
        eventID = id;
        Log.v("evenID", "== " + eventID);

        init();

        return view;
    }

    @Subscribe
    public void getMessage(Events.AdapterFragmentMessage adapterFragmentMessage) {
        ticketCount = adapterFragmentMessage.getTicketsCount();
        ticketPrice = adapterFragmentMessage.getTicketPrice();
        totalCost = adapterFragmentMessage.getTotalCost();
        ticketServiceCharge = adapterFragmentMessage.getTicketServicePrice();
        tv_noOfGuests.setText(String.valueOf(ticketCount));
        tv_totalAmount.setText(String.valueOf(totalCost));
        ticket_id = adapterFragmentMessage.getTicket_id();


    }


    @Override
    public void setTicketDetails(TicketsModel ticketsModel) {
        dismissPb();
        try {
            ticketsList = ticketsModel.data;
            if (ticketsList.size() > 0) {
                ticketListAdapter = new TicketListAdapter(getContext(), ticketsList);
                recycler_tickets.setAdapter(ticketListAdapter);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }


    }

    @Override
    public void setTicketDetailsss(OfferList response) {
        dismissPb();
    }

    @Override
    public void init() {
        GlobalBus.getBus().register(this);
        showPb();
        presenter.getTicketDetails(id);

//        tv_noOfGuests.setText(ticketsSessionManager.getTIcketCount());
//        tv_totalAmount.setText(ticketsSessionManager.getTotalPrice());

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_tickets.setLayoutManager(mLayoutManager);

        btn_select_date.setOnClickListener(this);
        ll_paynow.setOnClickListener(this);
    }

    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(getActivity(), true);
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
        } else  {
            showToast(getResources().getString(R.string.noresponse));
        }
    }

    @Override
    public void showRetryDialog(Object object1, Object object3, String message) {
        showPb();
        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_select_date:
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog


                //datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                // set day of month , month and year value in the edit text
                              /*  txt_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                                );
*/
                                str_date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                btn_select_date.setText(str_date);


                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
                break;

            case R.id.ll_paynow:
                if (!TextUtils.isEmpty(str_date)) {
                    if (ticketCount > 0) {
                        Intent detailsIntnet = new Intent(getContext(), EventBookingDetailsActivity.class);
                        detailsIntnet.putExtra("ticketCount", String.valueOf(ticketCount));
                        detailsIntnet.putExtra("ticketPrice", String.valueOf(ticketPrice));
                        detailsIntnet.putExtra("totalCost", totalCost);
                        detailsIntnet.putExtra("ticketServiceCharge", ticketServiceCharge);
                        detailsIntnet.putExtra("eventID", eventID);
                        detailsIntnet.putExtra("eventName", eventName);
                        detailsIntnet.putExtra("eventBannerUrl", eventBannerUrl);
                        detailsIntnet.putExtra("eventVenue", eventVenue);
                        detailsIntnet.putExtra("eventStartTime", eventStartTime);
                        detailsIntnet.putExtra("eventEndTime", eventEndTime);
                        detailsIntnet.putExtra("ticket_id", ticket_id);
                        detailsIntnet.putExtra("str_date", str_date);
                        startActivity(detailsIntnet);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.e_ticket_count), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.selectdate), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
