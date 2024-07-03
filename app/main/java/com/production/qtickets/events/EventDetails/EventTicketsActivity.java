package com.production.qtickets.events.EventDetails;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.OfferList;
import com.production.qtickets.model.TicketsData;
import com.production.qtickets.model.TicketsModel;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;

import com.production.qtickets.utils.TextviewBold;


import com.production.qtickets.ticketbookingdetaile.EventBookingDetailsActivity;

import org.greenrobot.eventbus.Subscribe;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventTicketsActivity extends AppCompatActivity implements EventDetaileContracter.View, View.OnClickListener {

    Unbinder unbinder;

    EventDetailePresenter presenter;

    String ticketOne = "";
    String ticketTwo = "";
    String ticketThree = "";
    String ticketFour = "";
    String ticketFive = "";
    String ticketSix = "";
    String ticketSeven = "";
    String ticketEight = "";
    String ticketNine = "";
    String ticketTen = "";
    String ticketEleven = "";
    String ticketTwelve = "";
    String ticketThirteen = "";
    String ticketFourteen = "";
    String ticketFifteen = "";
    String ticketsixteen = "";
    String ticketSeventeen = "";
    String ticketEighteen = "";
    String ticketNineteen = "";
    String ticketTwenty = "";

    String ticketsListFormat = "";


    List<TicketsModel> ticketsDataArrayList;
    List<TicketsData> dList;

    List<TicketsData> ticketsList = new ArrayList<>();

    @BindView(R.id.recycler_tickets)
    RecyclerView recycler_tickets;

    //layout manager
    RecyclerView.LayoutManager mLayoutManager;

    TicketListAdapter ticketListAdapter;
    private Map<String, EventTicket> mEventTicketCostMap = new ArrayMap<>();
   // private EventBookingInfo mEventBookingInfo;

    QTEventDetailsAdapter qtEventDetailsAdapter;

    private int totalCount;
    private double totalPrice;
    private double totalServicePrice;
    String currencyType;

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;

    EventDetailsVO eventvo;

    @BindView(R.id.tv_noOfGuests)
    TextView tv_noOfGuests;

    @BindView(R.id.tv_totalAmount)
    TextView tv_totalAmount;

    @BindView(R.id.btn_select_date)
    TextView btn_select_date;

    @BindView(R.id.ll_paynow)
    LinearLayout ll_paynow;

    private DatePickerDialog datePicker;
    private SimpleDateFormat dateFormatter;

    //string
    private String id, eventSource, eventName, eventBannerUrl, eventStartTime, eventEndTime, eventVenue, str_date = "";
    String ticket_id;

    int eventID = 0;
    String ticketPriceAndID = "";

    int ticketCount = 0;
    int ticketPrice = 0;
    int totalCost = 0;
    double ticketServiceCharge = 0;
    //datepiker
    DatePickerDialog datePickerDialog;


    private ArrayList<EventTicketDetails> mainEvntTktDetilas = new ArrayList<>();

    TextView tv_event_name;
    ImageView iv_back_arrow;

    String eventStartDate = "";
    String eventEndDate = "";

    String stringeventID = "";

    private String startDate, endDate;

    com.production.qtickets.utils.SessionManager sessionManager ;
    String currencyCode ="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_tickets_activity);
        QTUtils.setStatusBarGradiant(EventTicketsActivity.this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        currencyType = mPrefs.getString(AppConstants.COUNTRY_TYPE_CURRENCY, null);

        unbinder = ButterKnife.bind(this);
        presenter = new EventDetailePresenter();
        presenter.attachView(this);

        //get currency code , date and time of the event
        setDateTimeField();
        sessionManager = new com.production.qtickets.utils.SessionManager(EventTicketsActivity.this);
        currencyCode = sessionManager.getCountryCurrency();

        tv_event_name = (TextView) findViewById(R.id.tv_event_name);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(AppConstants.TOTAL_TICKET_COUNT, "0");
                editor.putString(AppConstants.TOTAL_TICKET_COST, "0");
                editor.putString(AppConstants.TICKET_PRICE_ID_COUNT, "0");
                editor.commit();
                EventTicketsActivity.this.finish();
            }
        });

        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(EventTicketsActivity.this);
        editor = prefmPrefs.edit();

        //setUpEventPriceAdapter(eventvo.evntTktDetilas);

        stringeventID = getIntent().getExtras().getString("eventID");
        eventID = Integer.valueOf(stringeventID);

        String ticketID = getIntent().getExtras().getString("");
        eventName = getIntent().getExtras().getString("eventName");
        eventSource = getIntent().getExtras().getString("eventSource");
        eventStartDate = getIntent().getExtras().getString("eventStartDate");
        eventEndDate = getIntent().getExtras().getString("eventEndDate");
        eventBannerUrl = getIntent().getExtras().getString("eventBanner");
        eventStartTime = getIntent().getExtras().getString("eventStartTime");
        eventEndTime = getIntent().getExtras().getString("eventEndTime");
        eventVenue = getIntent().getExtras().getString("eventVenue");
        tv_event_name.setText(eventName);

        recycler_tickets.setItemViewCacheSize(9);
//        eventID = id;
        Log.v("evenID", "== " + eventID);

        startDate = eventStartDate;
        endDate = eventEndDate;

        // decision making on the navigation from
        // because date format is different
        if (eventSource.equals("homePage")) {

            Log.v("hemanthdate", "== " + eventStartDate);
            Log.v("hemanthdate", "== " + eventEndDate);

            String[] splitspaceStartDate = startDate.split(" ");
            String partOne = splitspaceStartDate[0];

            String[] splitStartDate = partOne.split("/");
            String part1 = splitStartDate[0];
            String part2 = splitStartDate[1];
            String part3 = splitStartDate[2];

            startDate = part3 + "-" + part1 + "-" + part2;

            String[] splitspaceEndDate = endDate.split(" ");
            String endpartOne = splitspaceEndDate[0];

            String[] splitEndDate = endpartOne.split("/");
            String endpart1 = splitEndDate[0];
            String endpart2 = splitEndDate[1];
            String endpart3 = splitEndDate[2];
            endDate = endpart3 + "-" + endpart1 + "-" + endpart2;

        } else {

            Log.v("hemanthdate", "== " + eventStartDate);
            Log.v("hemanthdate", "== " + eventEndDate);

        }


        SimpleDateFormat dfEventDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = dfEventDate.parse(startDate);
            Date endDateEvent = dfEventDate.parse(endDate);
            dfEventDate = new SimpleDateFormat("dd MMM yyyy");
            startDate = dfEventDate.format(newDate);
            endDate = dfEventDate.format(endDateEvent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        init();

    }

    // ticket details for number of tickets user selected and the price of the total tickets

    @Subscribe
    public void getMessage(Events.AdapterFragmentMessage adapterFragmentMessage) {
        ticketCount = adapterFragmentMessage.getTicketsCount();
        ticketPrice = adapterFragmentMessage.getTicketPrice();
        totalCost = adapterFragmentMessage.getTotalCost();
        ticketServiceCharge = adapterFragmentMessage.getTicketServicePrice();
        tv_noOfGuests.setText(String.valueOf(ticketCount)+(" ( "+String.valueOf(ticketCount)+" PERSONS)"));
        tv_totalAmount.setText(String.valueOf(totalCost)+" "+currencyCode);
        ticketPriceAndID = adapterFragmentMessage.getCountandTicketPriceID();
        ticket_id = adapterFragmentMessage.getTicketID();
        Log.v("ticketPriceAndID", "== " + ticketPriceAndID);

    }

    // listing the list of ticket for the specific event
    @Override
    public void setTicketDetails(TicketsModel ticketsModel) {

        dismissPb();
        try {
            ticketsList = ticketsModel.data;
            if (ticketsList.size() > 0) {
                qtEventDetailsAdapter = new QTEventDetailsAdapter(EventTicketsActivity.this, ticketsList);
                recycler_tickets.setAdapter(qtEventDetailsAdapter);
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
    public void onBackPressed() {

        editor.putString(AppConstants.TOTAL_TICKET_COUNT, "0");
        editor.putString(AppConstants.TOTAL_TICKET_COST, "0");
        editor.putString(AppConstants.TICKET_PRICE_ID_COUNT, "0");
        editor.commit();
        super.onBackPressed();
    }

    //initializing the views
    @Override
    public void init() {
        GlobalBus.getBus().register(this);
        showPb();
        presenter.getTicketDetails(stringeventID);

        qtEventDetailsAdapter = new QTEventDetailsAdapter(this, mainEvntTktDetilas);

//        tv_noOfGuests.setText(ticketsSessionManager.getTIcketCount());
//        tv_totalAmount.setText(ticketsSessionManager.getTotalPrice());

        mLayoutManager = new LinearLayoutManager(EventTicketsActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler_tickets.setLayoutManager(mLayoutManager);

        btn_select_date.setOnClickListener(this);
        ll_paynow.setOnClickListener(this);

        if (eventStartDate.equals(eventEndDate)) {
            btn_select_date.setText(eventStartDate);
            str_date = btn_select_date.getText().toString();
            btn_select_date.setVisibility(View.VISIBLE);
            btn_select_date.setOnClickListener(null);
        } else {
            btn_select_date.setOnClickListener(this);
            btn_select_date.setVisibility(View.VISIBLE);
        }

        editor.putString(AppConstants.TOTAL_TICKET_COUNT, "0");
        editor.putString(AppConstants.TOTAL_TICKET_COST, "0");
        editor.putString(AppConstants.TICKET_PRICE_ID_COUNT, "0");
        editor.commit();

    }

    //date picker for the differnt dates which are available for the event
    private void showDatePicker() {


        if (eventID == 585) {

            List<Calendar> dayslist = new LinkedList<>();
            final Calendar[] daysArray;
            Calendar cAux = Calendar.getInstance();
            cAux.add(Calendar.DAY_OF_MONTH, +1);
            int timer = 0;
            while (cAux.getTimeInMillis() >= System.currentTimeMillis() && timer < 225) {
                if (cAux.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(cAux.getTimeInMillis());
                    dayslist.add(c);
                }
                timer++;
                cAux.setTimeInMillis(cAux.getTimeInMillis() + (24 * 60 * 60 * 1000));
            }
            daysArray = new Calendar[dayslist.size()];
            String[] arrFriday = new String[daysArray.length];
            for (int i = 0; i < daysArray.length; i++) {
                daysArray[i] = dayslist.get(i);
                arrFriday[i] = daysArray[i].get(Calendar.YEAR) + "-" + (daysArray[i].get(Calendar.MONTH) + 1) + "-" + daysArray[i].get(Calendar.DAY_OF_MONTH);
                final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
                final SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
                try {
                    arrFriday[i] = output.format(input.parse(arrFriday[i]));

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            Calendar date = Calendar.getInstance();

            for (int i = 0; i < arrFriday.length; i++) {
                SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");

                try {
                    Date day = f.parse(arrFriday[i]);
                    date.setTime(day);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        } else {
            long currntTime = System.currentTimeMillis();
            String today = dateFormatter.format(currntTime);

            SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
            long millisecondsEndDate = 0;
            long millisecondStartDate = 0;
            try {
                String eDate = endDate;
                String sDate = startDate;
                Log.v("dates", "== " + endDate);
                Log.v("dates", "== " + startDate);
                Date endDay = f.parse(endDate);
                Date startDay = f.parse(startDate);
                Date currentDay = f.parse(today);

                if (startDay.after(currentDay)) {
                    //startDay
                    millisecondStartDate = startDay.getTime();
                    System.out.println("Date1 is after Date2");
                }

                if (startDay.before(currentDay)) {
                    //CurrentDay
                    millisecondStartDate = currentDay.getTime();
                    System.out.println("Date1 is before Date2");
                }

                if (startDay.equals(currentDay)) {
                    //startDay
                    millisecondStartDate = startDay.getTime();
                    System.out.println("Date1 is equal Date2");
                }


                millisecondsEndDate = endDay.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }


            datePicker.getDatePicker().setMinDate(millisecondStartDate);
            datePicker.getDatePicker().setMaxDate(millisecondsEndDate);
            datePicker.show();
        }

    }

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //parsing the start and end dates whicha re coming from the server based on the dates are enabled for user to select

    private void setDateTimeField() {
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                str_date = dateFormatter.format(newDate.getTime());
                btn_select_date.setText(str_date);

                //QTUtils.getInstance().saveToSharedPreference(EventsDetailsActivity.this, AppConstants.EVENET_SELECTED_DATE, eventSelectedDate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(EventTicketsActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(EventTicketsActivity.this, message, Toast.LENGTH_SHORT).show();
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
        QTUtils.showAlertDialogbox(object1, object3, EventTicketsActivity.this, message);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_select_date:

                showDatePicker();


                break;

            case R.id.ll_paynow:

                //get the string format for the tickest which are selected with ticket id and quantity of the tickets
                String ticketTypesAndCount = getTicketTypesAndCountMethod();


                // Toast.makeText(EventTicketsActivity.this,ticketTypesAndCount,Toast.LENGTH_LONG).show();
                ticketsListFormat = "";

                // "ticketTypesAndCount" in this variable all the ticket ids and count will come

                if (!TextUtils.isEmpty(str_date)) {
                    if (ticketCount > 0) {
                        Intent detailsIntnet = new Intent(EventTicketsActivity.this, EventBookingDetailsActivity.class);
                        detailsIntnet.putExtra("ticketCount", String.valueOf(ticketCount));
                        detailsIntnet.putExtra("ticketPrice", String.valueOf(ticketPrice));
                        detailsIntnet.putExtra("totalCost", totalCost);
                        detailsIntnet.putExtra("ticketServiceCharge", ticketServiceCharge);
                        detailsIntnet.putExtra("eventID", stringeventID);
                        detailsIntnet.putExtra("eventName", eventName);
                        detailsIntnet.putExtra("eventBannerUrl", eventBannerUrl);
                        detailsIntnet.putExtra("eventVenue", eventVenue);
                        detailsIntnet.putExtra("eventStartTime", eventStartTime);
                        detailsIntnet.putExtra("eventEndTime", eventEndTime);
                        detailsIntnet.putExtra("ticket_id", ticket_id);
                        detailsIntnet.putExtra("str_date", str_date);
                        detailsIntnet.putExtra("str_master_id", ticketTypesAndCount);
                        startActivity(detailsIntnet);
                    } else {
                        Toast.makeText(EventTicketsActivity.this, getString(R.string.e_ticket_count), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventTicketsActivity.this, getString(R.string.selectdate), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private interface TicketCountSelectionChangeListener {
        void onTicketCountChanged(final EventTicket pEventTicketCost);
    }

    //inner class for the tickets listing
    private class QTEventDetailsAdapter extends RecyclerView.Adapter<QTEventDetailsAdapter.ViewHolder> {


        private Context mContext;
        private ArrayList<EventTicketDetails> eventsList;
        private ArrayList<Integer> counts = new ArrayList<>();
        private ArrayList<SelectEventType> selectEventTypes = new ArrayList<>();


        private TicketCountSelectionChangeListener mTicketCountSelectionChangeListener;

        int ticketsCount = 0;
        int ticketMaxCount = 0;
        int ticketPrice = 0;
        double ticketServicePrice = 0;
        String eventDate = "";
        int ticket_id;
        int totalCost = 0;
        int totalTicketsCost = 0;
        Context context;
        List<TicketsData> ticketsDataArrayList;


        public QTEventDetailsAdapter(Context ctx, ArrayList<EventTicketDetails> listEvents) {
            mContext = ctx;
            eventsList = listEvents;
            for (int i = 0; i < eventsList.size(); i++) {
                SelectEventType selectEventType = new SelectEventType();
                counts.add(i);
                selectEventTypes.add(selectEventType);
            }
        }

        SharedPreferences prefmPrefs;
        SharedPreferences.Editor editor;

        public void setTicketCountSelectionChangeListener(final TicketCountSelectionChangeListener pTicketCountSelectionChangeListener) {
            mTicketCountSelectionChangeListener = pTicketCountSelectionChangeListener;
        }

        public QTEventDetailsAdapter(Context context, List<TicketsData> ticketsDataArrayList) {
            this.context = context;
            this.ticketsDataArrayList = ticketsDataArrayList;
        }


        @NonNull
        @Override
        public QTEventDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View ticketlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_ticket_row, parent, false);
            QTEventDetailsAdapter.ViewHolder ticketViewHolder = new QTEventDetailsAdapter.ViewHolder(ticketlist);
            return ticketViewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            //final EventTicketDetails eventTicketDetails = eventsList.get(position);
            final TicketsData ticketsData = ticketsDataArrayList.get(position);
            eventDate = ticketsData.Date;
            ticketMaxCount = Integer.parseInt(ticketsData.NoOfTicketsPerTransaction);
            holder.tv_ticketName.setText(ticketsData.ticketname);
            holder.tv_ticketPrice.setText(ticketsData.TicketPrice);
            ticketsData.setTicket_count(0);
            if(ticketsData.Availability.equals("0")){
                holder.rel_sold_out.setVisibility(View.VISIBLE);
                holder.rel_quantity.setVisibility(View.GONE);
            }else {
                holder.rel_sold_out.setVisibility(View.GONE);
                holder.rel_quantity.setVisibility(View.VISIBLE);
            }
//        final EventTicketDetails eventTicketDetails = eventsList.get(position);

            final String ticketType = ticketsData.ticketname;
//        final int ticketPrice = Integer.valueOf(ticketsData.TicketPrice);
//        final double ticketServicePrice = Double.valueOf(ticketsData.ServiceCharge);

            prefmPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            editor = prefmPrefs.edit();


            final EventTicket eventTicketCost = new EventTicket(ticketType, ticketPrice, ticketServicePrice);

            //increasing the tickts count based on the condition
            holder.iv_increase_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (eventTicketCost.getCount() >= ticketMaxCount) {

                        Toast.makeText(context, "Maximum "+ticketMaxCount+" Tickets are allowed for one ticket type", Toast.LENGTH_LONG).show();
                    } else {

                        String totTicketPrice = prefmPrefs.getString(AppConstants.TOTAL_TICKET_COST, "");
                        String totTicketCount = prefmPrefs.getString(AppConstants.TOTAL_TICKET_COUNT, "");

                        if (ticketsData.getTicket_count() >= ticketMaxCount) {

                            Toast.makeText(context, "Maximum "+ticketMaxCount+" Tickets are allowed for one ticket type", Toast.LENGTH_LONG).show();
                        } else {
                            ticketPrice = Integer.parseInt(ticketsData.TicketPrice);
                            ticketServicePrice = Double.parseDouble(ticketsData.ServiceCharge);
                            ticketsCount = ticketsData.getTicket_count();
                            ticketsCount = ticketsCount + 1;

                            int sharedTicketCount = Integer.valueOf(totTicketCount);
                            sharedTicketCount = sharedTicketCount + 1;
                            totTicketCount = String.valueOf(sharedTicketCount);

                            Log.v("itemPosition", "== " + position);


                            int sharedTicketCost = Integer.valueOf(totTicketPrice);
                            sharedTicketCost = sharedTicketCost + ticketPrice;
                            totTicketPrice = String.valueOf(sharedTicketCost);

                            String tktpriceid = ticketsData.tktpriceid;
                            String ticketID = ticketsData.tktmasterid;
//                        String countandTicketPriceID = tktpriceid + "-" + ticketsCount;
//                            Toast.makeText(context, "ticketID = " + ticketID, Toast.LENGTH_LONG).show();

                            String ticketPriceID = ticketsData.getTicketPriceID();

                            String storedTicketPriceID = ticketsData.getTktPriceIDandCount();

                            String countandTicketPriceID = tktpriceid + "-" + ticketsCount;
                            ticketsData.setTktPriceIDandCount(countandTicketPriceID);

                            //storing the tickets price and price id
                            if (position == 0) {
                                ticketOne = "";
                                ticketOne = countandTicketPriceID;
                            } else if (position == 1) {
                                ticketTwo = "";
                                ticketTwo = countandTicketPriceID;
                            } else if (position == 2) {
                                ticketThree = "";
                                ticketThree = countandTicketPriceID;
                            } else if (position == 3) {
                                ticketFour = "";
                                ticketFour = countandTicketPriceID;
                            } else if (position == 4) {
                                ticketFive = "";
                                ticketFive = countandTicketPriceID;
                            } else if (position == 5) {
                                ticketSix = "";
                                ticketSix = countandTicketPriceID;
                            } else if (position == 6) {
                                ticketSeven = "";
                                ticketSeven = countandTicketPriceID;
                            } else if (position == 7) {
                                ticketEight = "";
                                ticketEight = countandTicketPriceID;
                            } else if (position == 8) {
                                ticketNine = "";
                                ticketNine = countandTicketPriceID;
                            } else if (position == 9) {
                                ticketTen = "";
                                ticketTen = countandTicketPriceID;
                            } else if (position == 10) {
                                ticketEleven = "";
                                ticketEleven = countandTicketPriceID;
                            } else if (position == 11) {
                                ticketTwelve = "";
                                ticketTwelve = countandTicketPriceID;
                            } else if (position == 12) {
                                ticketThirteen = "";
                                ticketThirteen = countandTicketPriceID;
                            } else if (position == 13) {
                                ticketFourteen = "";
                                ticketFourteen = countandTicketPriceID;
                            } else if (position == 14) {
                                ticketFifteen = "";
                                ticketFifteen = countandTicketPriceID;
                            } else if (position == 15) {
                                ticketsixteen = "";
                                ticketsixteen = countandTicketPriceID;
                            } else if (position == 16) {
                                ticketSeventeen = "";
                                ticketSeventeen = countandTicketPriceID;
                            } else if (position == 17) {
                                ticketEighteen = "";
                                ticketEighteen = countandTicketPriceID;
                            } else if (position == 18) {
                                ticketNineteen = "";
                                ticketNineteen = countandTicketPriceID;
                            } else if (position == 19) {
                                ticketTwenty = "";
                                ticketTwenty = countandTicketPriceID;
                            }

                            List<String> priceIDandCount = new ArrayList<String>();
//                        Log.v("priceIDandCount == ",""+priceIDandCount);
                            priceIDandCount.add(countandTicketPriceID);


                            ticketsData.setTicketPriceID(ticketsData.tktpriceid);

                            String priceIDCount = ticketsData.getTktPriceIDandCount();
                            //Toast.makeText(context, priceIDCount, Toast.LENGTH_LONG).show();

                            ticketsData.setTicket_count(ticketsCount);
                            holder.tv_ticket_count.setText(String.valueOf(ticketsData.getTicket_count()));
                            totalCost = (int) (ticketPrice * ticketsCount);
                            ticket_id = Integer.parseInt(ticketsData.tktmasterid);
                            sentPriceToEventFragment(sharedTicketCount, ticketPrice, Integer.valueOf(totTicketPrice), ticketServicePrice, ticket_id, countandTicketPriceID, ticketID);

                            editor.putString(AppConstants.TOTAL_TICKET_COUNT, totTicketCount);
                            editor.putString(AppConstants.TOTAL_TICKET_COST, String.valueOf(totTicketPrice));
                            editor.commit();

                        }
                    }
                }
            });

            // decreasing the tickets count
            holder.iv_decrease_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (ticketsData.getTicket_count() <= 10 && ticketsData.getTicket_count() > 0) {

                        String totTicketPrice = prefmPrefs.getString(AppConstants.TOTAL_TICKET_COST, "");
                        String totTicketCount = prefmPrefs.getString(AppConstants.TOTAL_TICKET_COUNT, "");

                        ticketPrice = Integer.parseInt(ticketsData.TicketPrice);
                        ticketsCount = ticketsData.getTicket_count();
                        ticketsCount = ticketsCount - 1;

                        int sharedTicketCount = Integer.valueOf(totTicketCount);
                        sharedTicketCount = sharedTicketCount - 1;
                        totTicketCount = String.valueOf(sharedTicketCount);

                        int sharedTicketCost = Integer.valueOf(totTicketPrice);
                        sharedTicketCost = sharedTicketCost - ticketPrice;
                        totTicketPrice = String.valueOf(sharedTicketCost);

                        String tktpriceid = ticketsData.tktpriceid;
                        String countandTicketPriceID = tktpriceid + "-" + ticketsCount;
                        //Toast.makeText(context, "countandTicketPriceID = " + countandTicketPriceID, Toast.LENGTH_LONG).show();

                        String ticketID = ticketsData.tktmasterid;

//                        Toast.makeText(context, "ticketID = " + ticketID, Toast.LENGTH_LONG).show();

                        //storing the tickets count and price id for the payment
                        if (position == 0) {
                            ticketOne = "";
                            ticketOne = countandTicketPriceID;
                        } else if (position == 1) {
                            ticketTwo = "";
                            ticketTwo = countandTicketPriceID;
                        } else if (position == 2) {
                            ticketThree = "";
                            ticketThree = countandTicketPriceID;
                        } else if (position == 3) {
                            ticketFour = "";
                            ticketFour = countandTicketPriceID;
                        } else if (position == 4) {
                            ticketFive = "";
                            ticketFive = countandTicketPriceID;
                        } else if (position == 5) {
                            ticketSix = "";
                            ticketSix = countandTicketPriceID;
                        } else if (position == 6) {
                            ticketSeven = "";
                            ticketSeven = countandTicketPriceID;
                        } else if (position == 7) {
                            ticketEight = "";
                            ticketEight = countandTicketPriceID;
                        } else if (position == 8) {
                            ticketNine = "";
                            ticketNine = countandTicketPriceID;
                        } else if (position == 9) {
                            ticketTen = "";
                            ticketTen = countandTicketPriceID;
                        } else if (position == 10) {
                            ticketEleven = "";
                            ticketEleven = countandTicketPriceID;
                        } else if (position == 11) {
                            ticketTwelve = "";
                            ticketTwelve = countandTicketPriceID;
                        } else if (position == 12) {
                            ticketThirteen = "";
                            ticketThirteen = countandTicketPriceID;
                        } else if (position == 13) {
                            ticketFourteen = "";
                            ticketFourteen = countandTicketPriceID;
                        } else if (position == 14) {
                            ticketFifteen = "";
                            ticketFifteen = countandTicketPriceID;
                        } else if (position == 15) {
                            ticketsixteen = "";
                            ticketsixteen = countandTicketPriceID;
                        } else if (position == 16) {
                            ticketSeventeen = "";
                            ticketSeventeen = countandTicketPriceID;
                        } else if (position == 17) {
                            ticketEighteen = "";
                            ticketEighteen = countandTicketPriceID;
                        } else if (position == 18) {
                            ticketNineteen = "";
                            ticketNineteen = countandTicketPriceID;
                        } else if (position == 19) {
                            ticketTwenty = "";
                            ticketTwenty = countandTicketPriceID;
                        }
                        // String ticket_price_id = getTckPriceIdAndTckCount(tktpriceid, ticketsCount);

                        ticketsData.setTicket_count(ticketsCount);
                        holder.tv_ticket_count.setText(String.valueOf(ticketsData.getTicket_count()));

                        totalCost = (int) (ticketPrice * ticketsCount);
                        ticket_id = Integer.parseInt(ticketsData.tktmasterid);
                        sentPriceToEventFragment(sharedTicketCount, ticketPrice, Integer.valueOf(totTicketPrice), ticketServicePrice, ticket_id, countandTicketPriceID, ticketID);
                        Log.v("TotalCost", "== " + totalCost);
                        editor.putString(AppConstants.TOTAL_TICKET_COUNT, String.valueOf(sharedTicketCount));
                        editor.putString(AppConstants.TOTAL_TICKET_COST, String.valueOf(totTicketPrice));
                        editor.commit();


                    } else {


                    }
                }
            });
        }


        private void sentPriceToEventFragment(int ticketsCount, int ticketPrice, int totalCost, double ticketServicePrice, int ticket_id, String ticketCountAndID, String ticketID) {
            Events.AdapterFragmentMessage adapterFragmentMessage =
                    new Events.AdapterFragmentMessage(ticketsCount, ticketPrice, totalCost, ticketServicePrice, ticket_id, ticketCountAndID, ticketID);
            GlobalBus.getBus().post(adapterFragmentMessage);
        }

        @Override
        public int getItemCount() {
            return ticketsDataArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextviewBold tv_ticketName;
            TextviewBold tv_ticketPrice;

            ImageView iv_increase_count;
            ImageView iv_decrease_count;

            TextView tv_ticket_count;
            RelativeLayout rel_sold_out,rel_quantity;
            public ViewHolder(View itemView) {
                super(itemView);

                tv_ticketName = (TextviewBold) itemView.findViewById(R.id.tv_ticketName);
                tv_ticketPrice = (TextviewBold) itemView.findViewById(R.id.tv_ticketPrice);

                iv_increase_count = (ImageView) itemView.findViewById(R.id.iv_increase_count);
                iv_decrease_count = (ImageView) itemView.findViewById(R.id.iv_decrease_count);

                tv_ticket_count = (TextView) itemView.findViewById(R.id.tv_ticket_count);
                rel_sold_out = itemView.findViewById(R.id.rel_sold_out);
                rel_quantity = itemView.findViewById(R.id.rel_quantity);
            }
        }

    }

    //string formating for the tickets price and price id and ticket id
    private String getTicketTypesAndCountMethod() {
        if (!ticketOne.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketOne;
            } else {
                ticketsListFormat = ticketOne;
            }
        }
        if (!ticketTwo.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketTwo;
            } else {
                ticketsListFormat = ticketTwo;
            }
        }
        if (!ticketThree.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketThree;
            } else {
                ticketsListFormat = ticketThree;
            }
        }
        if (!ticketFour.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketFour;
            } else {
                ticketsListFormat = ticketFour;
            }
        }
        if (!ticketFive.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketFive;
            } else {
                ticketsListFormat = ticketFive;
            }
        }
        if (!ticketSix.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketSix;
            } else {
                ticketsListFormat = ticketSix;
            }
        }
        if (!ticketSeven.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketSeven;
            } else {
                ticketsListFormat = ticketSeven;
            }
        }
        if (!ticketEight.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketEight;
            } else {
                ticketsListFormat = ticketEight;
            }
        }
        if (!ticketNine.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketNine;
            } else {
                ticketsListFormat = ticketNine;
            }
        }
        if (!ticketTen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketTen;
            } else {
                ticketsListFormat = ticketTen;
            }
        }
        if (!ticketEleven.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketEleven;
            } else {
                ticketsListFormat = ticketEleven;
            }
        }
        if (!ticketTwelve.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketTwelve;
            } else {
                ticketsListFormat = ticketTwelve;
            }
        }
        if (!ticketThirteen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketThirteen;
            } else {
                ticketsListFormat = ticketThirteen;
            }
        }
        if (!ticketFourteen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketFourteen;
            } else {
                ticketsListFormat = ticketFourteen;
            }
        }
        if (!ticketFifteen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketFifteen;
            } else {
                ticketsListFormat = ticketFifteen;
            }
        }
        if (!ticketsixteen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketsixteen;
            } else {
                ticketsListFormat = ticketsixteen;
            }
        }
        if (!ticketSeventeen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketSeventeen;
            } else {
                ticketsListFormat = ticketSeventeen;
            }
        }
        if (!ticketEighteen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketEighteen;
            } else {
                ticketsListFormat = ticketEighteen;
            }
        }
        if (!ticketNineteen.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketNineteen;
            } else {
                ticketsListFormat = ticketNineteen;
            }
        }
        if (!ticketTwenty.isEmpty()) {
            if (!ticketsListFormat.isEmpty()) {
                ticketsListFormat = ticketsListFormat + "," + ticketTwenty;
            } else {
                ticketsListFormat = ticketTwenty;
            }
        }

        return ticketsListFormat;
    }

}
