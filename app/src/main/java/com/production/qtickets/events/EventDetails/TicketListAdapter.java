package com.production.qtickets.events.EventDetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.TicketsData;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.TextviewBold;

import java.util.ArrayList;
import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketViewHolder> {

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
    private ArrayList<EventTicketDetails> eventsList;

    private ArrayList<SelectEventType> selectEventTypes = new ArrayList<>();

    String ticketIDs = "";

    SharedPreferences prefmPrefs;
    SharedPreferences.Editor editor;

    private TicketCountSelectionChangeListener mTicketCountSelectionChangeListener;

    public TicketListAdapter(Context context, List<TicketsData> ticketsDataArrayList) {
        this.context = context;
        this.ticketsDataArrayList = ticketsDataArrayList;
    }

    public void setTicketCountSelectionChangeListener(final TicketCountSelectionChangeListener pTicketCountSelectionChangeListener) {
        mTicketCountSelectionChangeListener = pTicketCountSelectionChangeListener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ticketlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_ticket_row, parent, false);
        TicketViewHolder ticketViewHolder = new TicketViewHolder(ticketlist);
        return ticketViewHolder;
    }

    int dubcount = 0;

    @Override
    public void onBindViewHolder(@NonNull final TicketViewHolder holder, final int position) {
        final TicketsData ticketsData = ticketsDataArrayList.get(position);
        eventDate = ticketsData.Date;
        ticketMaxCount = Integer.parseInt(ticketsData.NoOfTicketsPerTransaction);
        if(ticketsData.Availability.equals("0")){
         holder.rel_sold_out.setVisibility(View.VISIBLE);
         holder.rel_quantity.setVisibility(View.GONE);
        }else {
            holder.rel_sold_out.setVisibility(View.GONE);
            holder.rel_quantity.setVisibility(View.VISIBLE);
        }
        holder.tv_ticketName.setText(ticketsData.ticketname);
        holder.tv_ticketPrice.setText(ticketsData.TicketPrice);
        ticketsData.setTicket_count(0);

//        final EventTicketDetails eventTicketDetails = eventsList.get(position);

        final String ticketType = ticketsData.ticketname;
//        final int ticketPrice = Integer.valueOf(ticketsData.TicketPrice);
//        final double ticketServicePrice = Double.valueOf(ticketsData.ServiceCharge);

        prefmPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefmPrefs.edit();

        final EventTicket eventTicketCost = new EventTicket(ticketType, ticketPrice, ticketServicePrice);

        holder.iv_increase_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eventTicketCost.getCount() >= ticketMaxCount) {

                    Toast.makeText(context, "Tickets count should not exceed " + ticketMaxCount, Toast.LENGTH_LONG).show();
                } else {

//                    int count = eventTicketCost.getCount();
//                    int totalPeapleCount = eventTicketCost.getTotalCount();
//                    count = count + 1;
//                    totalPeapleCount = totalPeapleCount + 1;
//                    holder.tv_ticket_count.setText(String.valueOf(totalPeapleCount));
//                    eventTicketCost.setCount(count);
//                    eventTicketCost.setTotalCount(count);
//                    String tktpriceid = eventTicketDetails.tktpriceid;
//
//                    SelectEventType selectEventType = selectEventTypes.get(position);
//                    selectEventType.setCount(count);
//                    selectEventType.setEventMasterId(eventTicketDetails.tktmasterid);
//                    selectEventType.setTktpriceid(tktpriceid);
//                    String tktServicepriceid = eventTicketDetails.ServiceCharge;
//                    selectEventType.setTktServicepriceid(tktServicepriceid);
//                    mTicketCountSelectionChangeListener.onTicketCountChanged(eventTicketCost);
//
//                    totalCost = (int) (ticketPrice * count);
//                    ticket_id = Integer.parseInt(ticketsData.tktmasterid);
//                    eventTicketCost.setCount(count);
//                    ticketsData.setTotalTicketCost(totalTicketsCost);
//                    sentPriceToEventFragment(eventTicketCost.getCount(), ticketPrice, totalCost, ticketServicePrice, ticket_id);

                    String totTicketPrice = prefmPrefs.getString(AppConstants.TOTAL_TICKET_COST, "");
                    String totTicketCount = prefmPrefs.getString(AppConstants.TOTAL_TICKET_COUNT, "");

                    if (ticketsData.getTicket_count() >= ticketMaxCount) {

                        Toast.makeText(context, "Tickets count should not exceed " + ticketMaxCount, Toast.LENGTH_LONG).show();
                    } else {
                        ticketPrice = Integer.parseInt(ticketsData.TicketPrice);
                        ticketServicePrice = Double.parseDouble(ticketsData.ServiceCharge);
                        ticketsCount = ticketsData.getTicket_count();
                        ticketsCount = ticketsCount + 1;

                        int sharedTicketCount = Integer.valueOf(totTicketCount);
                        sharedTicketCount = sharedTicketCount + 1;
                        totTicketCount = String.valueOf(sharedTicketCount);

                        int sharedTicketCost = Integer.valueOf(totTicketPrice);
                        sharedTicketCost = sharedTicketCost + ticketPrice;
                        totTicketPrice = String.valueOf(sharedTicketCost);

                        String tktpriceid = ticketsData.tktpriceid;
//                        String countandTicketPriceID = tktpriceid + "-" + ticketsCount;
                        //Toast.makeText(context, "countandTicketPriceID = " + countandTicketPriceID, Toast.LENGTH_LONG).show();

                        String ticketPriceID = ticketsData.getTicketPriceID();

//                        SelectEventType selectEventType = selectEventTypes.get(position);
//                        selectEventType.setCount(ticketsCount);
//                        selectEventType.setTktpriceid(tktpriceid);
//                        selectEventType.setEventMasterId(ticketsData.tktmasterid);
//                        String tktServicepriceid = ticketsData.ServiceCharge;
//                        selectEventType.setTktServicepriceid(tktServicepriceid);

//                        if (ticketPriceID.equals(tktpriceid)){
//                            ticketIDs = tktpriceid + "-" + ticketsCount;
//                        }else {
//
//                        }

                        if (ticketIDs.isEmpty()) {
                            ticketIDs = tktpriceid;
                        } else {
                            ticketIDs = ticketIDs + "," + tktpriceid;
                            Log.v("ticketsPricesID", "" + ticketIDs);
                        }

                        String[] ticketsPrices = ticketIDs.split(",");
                        int tktPriceIdsLenght = ticketsPrices.length;

                        int arraylength = ticketsDataArrayList.size();

                        String[][] ticketIDandCount = new String[arraylength][arraylength];
                        if (ticketIDandCount.length == 0) {

                        } else {
                            for (int i = 0; i < ticketIDandCount.length; i++) {
                                for (int j = 0; j < ticketIDandCount.length; j++) {

                                }
                            }
                        }

//                        int itemsCount = diffValues(ticketsPrices);
//                        Toast.makeText(context, String.valueOf(itemsCount), Toast.LENGTH_SHORT).show();
//                        System.out.println("Duplicate elements from array using HashSet data structure");
//                        Set<String> store = new HashSet<>();
//
//                        for (String name : ticketsPrices) {
//                            if (store.add(name) == false) {
//                                System.out.println("found a duplicate element in array : " + name);
//                                String valueName = name;
//                            }
//                        }
//                        System.out.println("Finding duplicate elements in array using brute force method");
//                        for (int i = 0; i < ticketsPrices.length; i++) {
//                            for (int j = i + 1; j < ticketsPrices.length; j++) {
//                                if (ticketsPrices[i].equals(ticketsPrices[j]) ) {
//                                    // got the duplicate element
//                                    dubcount++;
//                                }
//                            }
//                        }


//                        for (int i = 0; i<ticketsPrices.length; i++){
//                            Log.v("ticketsPricesID",""+ticketsPrices[i]);
//                        }
//
//                        if ( ArrayUtils.contains( ticketsPrices, "id" ) ) {
//                            // Do some stuff.
//                        }
//
                        String storedTicketPriceID = ticketsData.getTktPriceIDandCount();


                        String countandTicketPriceID = tktpriceid + "-" + ticketsCount;
                        ticketsData.setTktPriceIDandCount(countandTicketPriceID);


                        List<String> priceIDandCount = new ArrayList<String>();
//                        Log.v("priceIDandCount == ",""+priceIDandCount);
                        priceIDandCount.add(countandTicketPriceID);


//                        Set<String> unique = new HashSet<String>(priceIDandCount);
//                        for (String key : unique) {
//                            System.out.println(key + ": " + Collections.frequency(priceIDandCount, key));
//                            Log.v("priceIDandCount == ",""+Collections.frequency(priceIDandCount, key));
//                        }
//
//                        String priceIDandCount[] = new String[20];
//                        for (int i = 0; i < priceIDandCount.length; i++){
//                            priceIDandCount[i] = countandTicketPriceID;
//                        }
//
//                        for (int i = 0; i<priceIDandCount.length; i++){
////                            System.out.println("priceIDandCount == "+priceIDandCount[i]);
//                            Log.v("priceIDandCount == ",""+priceIDandCount[i]);
//                        }

                        String ticketID = ticketsData.tktmasterid;

                        ticketsData.setTicketPriceID(ticketsData.tktpriceid);

                        String priceIDCount = ticketsData.getTktPriceIDandCount();
                        //Toast.makeText(context, priceIDCount, Toast.LENGTH_LONG).show();

                        ticketsData.setTicket_count(ticketsCount);
                        holder.tv_ticket_count.setText(String.valueOf(ticketsData.getTicket_count()));
                        totalCost = (int) (ticketPrice * ticketsCount);
                        ticket_id = Integer.parseInt(ticketsData.tktmasterid);
                        sentPriceToEventFragment(sharedTicketCount, ticketPrice, Integer.valueOf(totTicketPrice), ticketServicePrice, ticket_id, countandTicketPriceID,ticketID);

                        editor.putString(AppConstants.TOTAL_TICKET_COUNT, totTicketCount);
                        editor.putString(AppConstants.TOTAL_TICKET_COST, String.valueOf(totTicketPrice));
                        editor.commit();

//                        ticketPrice = Integer.parseInt(ticketsData.TicketPrice);
//                        ticketServicePrice = Double.parseDouble(ticketsData.ServiceCharge);
//                        ticketsCount = ticketsData.getTicket_count();
//                        ticketsCount = ticketsCount + 1;
//                        ticketsData.setTicket_count(ticketsCount);
//                        holder.tv_ticket_count.setText(String.valueOf(ticketsData.getTicket_count()));
//                        totalCost = (int) (ticketPrice * ticketsCount);
//                        ticket_id = Integer.parseInt(ticketsData.tktmasterid);
//                        sentPriceToEventFragment(ticketsData.getTicket_count(), ticketPrice, totalCost, ticketServicePrice, ticket_id);

                    }
                }
            }
        });

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

                   // String ticket_price_id = getTckPriceIdAndTckCount(tktpriceid, ticketsCount);

                    ticketsData.setTicket_count(ticketsCount);
                    holder.tv_ticket_count.setText(String.valueOf(ticketsData.getTicket_count()));

                    totalCost = (int) (ticketPrice * ticketsCount);
                    ticket_id = Integer.parseInt(ticketsData.tktmasterid);
                    sentPriceToEventFragment(sharedTicketCount, ticketPrice, Integer.valueOf(totTicketPrice), ticketServicePrice, ticket_id, countandTicketPriceID,ticketID);
                    Log.v("TotalCost", "== " + totalCost);
                    editor.putString(AppConstants.TOTAL_TICKET_COUNT, String.valueOf(sharedTicketCount));
                    editor.putString(AppConstants.TOTAL_TICKET_COST, String.valueOf(totTicketPrice));
                    editor.commit();

//                    ticketPrice = Integer.parseInt(ticketsData.TicketPrice);
//                    ticketsCount = ticketsData.getTicket_count();
//                    ticketsCount = ticketsCount - 1;
//                    ticketsData.setTicket_count(ticketsCount);
//                    holder.tv_ticket_count.setText(String.valueOf(ticketsData.getTicket_count()));
//                    totalCost = (int) (ticketPrice * ticketsCount);
//                    ticket_id = Integer.parseInt(ticketsData.tktmasterid);
//                    sentPriceToEventFragment(ticketsData.getTicket_count(), ticketPrice, totalCost, ticketServicePrice, ticket_id);

                } else {


                }
            }
        });
    }

    public static int diffValues(String[] numArray) {
        int numOfDifferentVals = 0;
        String diffvalue = "";
        int repeatCount = 0;

        ArrayList<String> diffNum = new ArrayList<>();

        for (int i = 0; i < numArray.length; i++) {
            if (!diffNum.contains(numArray[i])) {
                diffNum.add(numArray[i]);
                repeatCount++;
                if (diffvalue.isEmpty()) {
                    diffvalue = numArray[i];
                } else {
                    diffvalue = diffvalue + "," + numArray[i];
                    Log.v("diffvalue", "== " + diffvalue);
                }
                Log.v("diffvalue", "== " + repeatCount);
            }
        }

        if (diffNum.size() == 1) {
            numOfDifferentVals = 1;
        } else {
            numOfDifferentVals = diffNum.size();
        }

        return numOfDifferentVals;
    }

    private String getTckPriceIdAndTckCount(String tktpriceid, int ticketsCount) {
        String ticket_price_id = "";
        String ticketPriceIDAndCount = "";

        int finalTickePriceID = 0;

        String tckPriceIdAndCount = prefmPrefs.getString(AppConstants.TICKET_PRICE_ID_COUNT, "");

        if (tckPriceIdAndCount.equals("0")) {

            ticketPriceIDAndCount = tktpriceid + "-" + String.valueOf(ticketsCount);
            ticket_price_id = ticketPriceIDAndCount;
            editor.putString(AppConstants.TICKET_PRICE_ID_COUNT, ticketPriceIDAndCount);
            editor.commit();

        } else {
            String[] tckPriceIDsAndCounts = tckPriceIdAndCount.split(",");
            int tckIDsLength = tckPriceIDsAndCounts.length;
            Log.v("ticketsLengh", "== " + tckIDsLength);
            if (tckIDsLength > 0) {

                for (int i = 0; i < tckIDsLength; i++) {
                    String IDsCount = tckPriceIDsAndCounts[i];
                    String[] onlyIDs = IDsCount.split("-");
                    for (int j = 0; j < onlyIDs.length; j = 2) {

                        int ticketPriceID = Integer.parseInt(onlyIDs[j]);
                        int tktPriceID = Integer.valueOf(tktpriceid);
                        if (ticketPriceID == tktPriceID) {
                            Toast.makeText(context, "yes", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "no", Toast.LENGTH_LONG).show();
                        }
                        Log.v("IDsCount", "== " + ticketPriceID);
                    }
                }

                Log.v("tckPriceID", "=== " + ticketPriceIDAndCount);

                ticketPriceIDAndCount = tktpriceid + "-" + String.valueOf(ticketsCount);
                ticket_price_id = ticketPriceIDAndCount;

                ticketPriceIDAndCount = tckPriceIdAndCount + "," + ticketPriceIDAndCount;
                editor.putString(AppConstants.TICKET_PRICE_ID_COUNT, ticketPriceIDAndCount);
                editor.commit();

            } else {
                ticketPriceIDAndCount = tktpriceid + "-" + String.valueOf(ticketsCount);
                ticket_price_id = ticketPriceIDAndCount;

                ticketPriceIDAndCount = tckPriceIdAndCount + "," + ticketPriceIDAndCount;
                editor.putString(AppConstants.TICKET_PRICE_ID_COUNT, ticketPriceIDAndCount);
                editor.commit();

                Log.v("tckPriceID", "=== " + ticketPriceIDAndCount);
            }

//            for (int i = 0;i<tckPriceIDsAndCounts.length; i++){
//
//                Log.v("tckPriceIDsAndCounts","== "+tckPriceIDsAndCounts[i]);
//
//            }
        }


        return ticket_price_id;
    }

    private void sentPriceToEventFragment(int ticketsCount, int ticketPrice, int totalCost, double ticketServicePrice, int ticket_id, String ticketCountAndID,String ticketID) {
        Events.AdapterFragmentMessage adapterFragmentMessage =
                new Events.AdapterFragmentMessage(ticketsCount, ticketPrice, totalCost, ticketServicePrice, ticket_id, ticketCountAndID,ticketID);
        GlobalBus.getBus().post(adapterFragmentMessage);
    }

    @Override
    public int getItemCount() {
        return ticketsDataArrayList.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {

        TextviewBold tv_ticketName;
        TextviewBold tv_ticketPrice;

        ImageView iv_increase_count;
        ImageView iv_decrease_count;

        TextView tv_ticket_count;
        RelativeLayout rel_sold_out,rel_quantity;


        public TicketViewHolder(View itemView) {
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
