package com.production.qtickets.moviedetailes;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.model.ItemModel;
import com.production.qtickets.model.ShowDetailModel;
import com.production.qtickets.movies.seatselection.SeatSelectionActivity;
import com.production.qtickets.novocinema.NovoTicketSelectionActivity;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Harsh on 5/29/2018.
 */
public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    List<ShowDetailModel> timeList;
    private Context context;
    private String ticket_count, censor;
    private String movieTitle, mall_name, show_timing_id, show_timimg, show_date;
    private String cid, url, flick_movie_id, screen_id, varaiant_id, seesion_id, cencor, duration, movie_type, movie_img_url, screen_type;
    android.app.AlertDialog mid_dialog;
    String nextDate;
    private List<ItemModel> mList;
    SessionManager sessionManager;

    public TimeAdapter(Context context, List<ShowDetailModel> timeList, List<ItemModel> mList, String movieTitle, String ticket_count, String duration, String movie_type, String movie_img_url, String cencor) {
        GlobalBus.getBus().register(this);
        this.timeList = timeList;
        this.context = context;
        this.movieTitle = movieTitle;
        this.mList = mList;
        this.ticket_count = ticket_count;
        this.cencor = cencor;
        this.movie_type = movie_type;
        this.movie_img_url = movie_img_url;
        this.duration = duration;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_show_timimgs, parent, false);
        return new ViewHolder(view);
    }

    @Subscribe
    public void getMessage(Events.FragmentActivityMessage activityFragmentMessage) {
        ticket_count = activityFragmentMessage.getMessage();
    }


    public String changeDateFormmated(String inputDateString) {
        String outputDate = "";
        try {
            // Step 1: Parse the input date string into a Date object
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date inputDate = inputDateFormat.parse(inputDateString);

            // Step 2: Create a SimpleDateFormat object for the desired output format
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);

            // Step 3: Format the Date object into a string using the output format
            outputDate = outputDateFormat.format(inputDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }

    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ShowDetailModel sList = timeList.get(position);
        sessionManager = new SessionManager(context);
        censor = sessionManager.getCensor();
        if (censor.contains("+")) {
            censor = censor.replace("+", "P");
        }
        //        try {
        if (!TextUtils.isEmpty(sList.showTime)) {
            holder.txt_time.setText(sList.showTime);
        }


        if (sList.showStatus.equals("1")) {
            double amount = Double.parseDouble(sList.totalSeats);
            double res = (amount / 100.0f) * 20;
            // Toast.makeText(context, ""+res, Toast.LENGTH_SHORT).show();
            if (res > Double.parseDouble(sList.seatsAvailable)) {
                holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.filling_fast_round));
                holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
            } else {
                holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.available_round));
                holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
            }
        } else if (sList.showStatus.equals("2")) {
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.sold_out_round_back));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else if (sList.showStatus.equals("3")) {
            //midnight show
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.midnight_round));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else if (sList.showStatus.equals("4")) {
            holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.sold_out_round_back));
            holder.txt_time.setTextColor(context.getResources().getColor(R.color.colorBlack));
        }
        if (!(sList.cid.equals("0028")) || !(sList.cid.equals("0033")) || !(sList.cid.equals("0030")) || !(sList.cid.equals("0018")) || !(sList.cid.equals("014"))) {
            holder.txt_screen_type.setText(sList.screeentype);
            if (TextUtils.isEmpty(sList.screeentype)) {
                holder.txt_screen_type.setVisibility(View.GONE);
            } else {
                holder.txt_screen_type.setVisibility(View.VISIBLE);
            }
        }
        /*get utc time*/
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("UTC Time is: " + dateFormat.format(date));


        /*add plus 3 hours to current time minus 15 minutes*/
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd-MM-yyyy HH:mm");
        simpleDateFormat.setTimeZone(timeZone);
        calendar.add(Calendar.HOUR, 3);
        calendar.add(Calendar.MINUTE, +15);
        System.out.println("Time here " + calendar.getTime());
        System.out.println("Time zone: " + timeZone.getID());
        System.out.println("default time zone: " + TimeZone.getDefault().getID());
        System.out.println();
        System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
        System.out.println("Default: " + calendar.getTime());
        if (sList.cid.equals("0028") || sList.cid.equals("0033") || sList.cid.equals("0030") || sList.cid.equals("0018") || sList.cid.equals("014")) {
            String pattern = "dd-MM-yyyy HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                Date date1 = sdf.parse(simpleDateFormat.format(calendar.getTime()));
                Date date2 = sdf.parse(sList.showDate + " " + sList.showTime);
                if (date2.before(date1)) {
                    holder.txt_time.setBackground(context.getResources().getDrawable(R.drawable.sold_out_round_back));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        holder.c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cid = timeList.get(position).cid;

                Log.d("7nov", "cid : " + cid);
                Log.d("7nov", "showStatus : " + timeList.get(position).showStatus);

                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).cinemaId.equals(cid)) {
                        mall_name = mList.get(i).cinemaName;
                    }
                }

                show_timing_id = timeList.get(position).showId;
                show_timimg = timeList.get(position).showTime;
                show_date = timeList.get(position).showDate;
                //  nextDate = incrementDateByOne(show_date);
                //nextDate = show_date;
                screen_id = timeList.get(position).screenId;
                varaiant_id = timeList.get(position).variantId;
                seesion_id = timeList.get(position).sessionId;
                screen_type = timeList.get(position).screeentype;
                flick_movie_id = timeList.get(position).flik_movie_id;
                /*get utc time*/
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                System.out.println("UTC Time is: " + dateFormat.format(date));
                /*add plus 3 hours to current time minus 15 minutes*/
                TimeZone timeZone = TimeZone.getTimeZone("UTC");
                Calendar calendar = Calendar.getInstance(timeZone);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                simpleDateFormat.setTimeZone(timeZone);
                calendar.add(Calendar.HOUR, 3);
                calendar.add(Calendar.MINUTE, +15);
                System.out.println("Time here " + calendar.getTime());
                System.out.println("Time zone: " + timeZone.getID());
                System.out.println("default time zone: " + TimeZone.getDefault().getID());
                System.out.println();
                System.out.println("UTC:     " + simpleDateFormat.format(calendar.getTime()));
                System.out.println("Default: " + calendar.getTime());
                /*for novo compare time if it is less than current time hide show timings*/
                if (timeList.get(position).cid.equals("0028") ||
                        timeList.get(position).cid.equals("0033") ||
                        timeList.get(position).cid.equals("0030") ||
                        timeList.get(position).cid.equals("0018") ||
                        timeList.get(position).cid.equals("041")) {
                    String pattern = "dd-MM-yyyy HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    try {
                        Date date1 = sdf.parse(simpleDateFormat.format(calendar.getTime()));
                        Date date2 = sdf.parse(show_date + " " + show_timimg);
                        if (date2.before(date1)) {
                            timeList.get(position).showStatus = "2";
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                if (cid.equals("16")) {
                    url = "https://www.q-tickets.com/MovieSeatingLayout/KataraLayout_mob?id=" + timeList.get(position).showId;
                    Bundle b = new Bundle();
                    b.putString("url", url);
                    b.putString("pagename", movieTitle);
                    Intent i = new Intent(context, ShowWebView.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtras(b);
                    context.startActivity(i);
                    return;
                }


                if (cid.equals("022") || cid.equals("025")) { //6nov
                    if (timeList.get(position).showStatus.equals("3")) {
                        nextDate = incrementDateByOne(timeList.get(position).showDate);
                        mid_dialog = new android.app.AlertDialog.Builder(context, R.style.AlertDialogCustom)
                                .setTitle("NOTE")
                                .setMessage(context.getString(R.string.note) + "(" + nextDate + ")")
                                .setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Bundle b = new Bundle();
                                                b.putString("duration", duration);
                                                b.putString("movie_type", movie_type);
                                                b.putString("movie_img_url", movie_img_url);
                                                b.putString("screen_name", timeList.get(position).screenName);
                                                b.putString("show_time", show_timimg);
                                                b.putString("cid", cid);
                                                b.putString("session_id", seesion_id);
                                                b.putString("mall_name", mall_name);
                                                b.putString("movie_title", movieTitle);
                                                b.putString("Screen_Type", screen_type);
                                                b.putString("cencor", cencor);
                                                b.putString("show_date", changeDateFormmated(timeList.get(position).showDate));
                                                Intent i = new Intent(context, NovoTicketSelectionActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                i.putExtras(b);
                                                context.startActivity(i);
                                                return;
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        }).create();
                        mid_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
                        mid_dialog.show();


                    } else {
                        Bundle b = new Bundle();
                        b.putString("duration", duration);
                        b.putString("movie_type", movie_type);
                        b.putString("movie_img_url", movie_img_url);
                        b.putString("screen_name", timeList.get(position).screenName);
                        b.putString("show_time", show_timimg);
                        b.putString("cid", cid);
                        b.putString("session_id", seesion_id);
                        b.putString("mall_name", mall_name);
                        b.putString("movie_title", movieTitle);
                        b.putString("Screen_Type", screen_type);
                        b.putString("cencor", cencor);
                        b.putString("show_date", changeDateFormmated(timeList.get(position).showDate));
                        Intent i = new Intent(context, NovoTicketSelectionActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(b);
                        context.startActivity(i);
                        return;
                    }


                }

                if (!(cid.equals("100002")
                        || cid.equals("100006") ||
                        cid.equals("0028") ||
                        cid.equals("0030") ||
                        cid.equals("0033") ||
                        cid.equals("0018") || cid.equals("041"))) {
                    if (!timeList.get(position).showStatus.equals("2") && !timeList.get(position).showStatus.equals("4")) {
                        if (Integer.parseInt(ticket_count) > 0) {
                            if (timeList.get(position).showStatus.equals("3")) {
                                nextDate = incrementDateByOne(timeList.get(position).showDate);
                                mid_dialog = new android.app.AlertDialog.Builder(context, R.style.AlertDialogCustom)
                                        .setTitle("NOTE")
                                        .setMessage(context.getString(R.string.note) + "(" + nextDate + ")")
                                        .setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Bundle b = new Bundle();
                                                        b.putString("movie_title", movieTitle);
                                                        b.putString("show_id", show_timing_id);
                                                        b.putSerializable("show_list", (Serializable) timeList);
                                                        b.putString("mall_name", mall_name);
                                                        b.putString("ticket_count", ticket_count);
                                                        b.putString("show_date", show_date);
                                                        b.putInt("show_timimg", position);
                                                        b.putString("duration", duration);
                                                        b.putString("movie_type", movie_type);
                                                        b.putString("movie_img_url", movie_img_url);
                                                        b.putString("screen_name", timeList.get(position).screenName);
                                                        b.putString("show_time", show_timimg);
                                                        b.putString("theater_id", timeList.get(position).cid);
                                                        b.putString("screen_id", timeList.get(position).screenId);
                                                        b.putString("display_status", timeList.get(position).showStatus);
                                                        Intent i = new Intent(context, SeatSelectionActivity.class);
                                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        i.putExtras(b);
                                                        context.startActivity(i);
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                }).create();
                                mid_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
                                mid_dialog.show();


                            } else {
                                Bundle b = new Bundle();
                                b.putString("movie_title", movieTitle);
                                b.putString("show_id", show_timing_id);
                                b.putSerializable("show_list", (Serializable) timeList);
                                b.putString("mall_name", mall_name);
                                b.putString("ticket_count", ticket_count);
                                b.putString("show_date", show_date);
                                b.putInt("show_timimg", position);
                                b.putString("duration", duration);
                                b.putString("movie_type", movie_type);
                                b.putString("movie_img_url", movie_img_url);
                                b.putString("screen_name", timeList.get(position).screenName);
                                b.putString("show_time", show_timimg);
                                b.putString("theater_id", timeList.get(position).cid);
                                b.putString("screen_id", timeList.get(position).screenId);
                                b.putString("display_status", timeList.get(position).showStatus);
                                Intent i = new Intent(context, SeatSelectionActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtras(b);
                                context.startActivity(i);
                            }

                        } else {
                            QTUtils.showDialogbox(context, context.getString(R.string.e_ticket_count));
                            //Toast.makeText(context, context.getString(R.string.e_ticket_count), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        QTUtils.showDialogbox(context, context.getString(R.string.no_show_time));
                                    /*Toast toast = Toast.makeText(context,context.getString(R.string.no_show_time), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();*/
                    }
                } else {
                    if (!timeList.get(position).showStatus.equals("2") &&
                            !timeList.get(position).showStatus.equals("4")) {
                        if (cid.equals("100002") || cid.equals("100006")) {
                            if (timeList.get(position).showStatus.equals("3")) {
                                nextDate = incrementDateByOne(timeList.get(position).showDate);
                                mid_dialog = new android.app.AlertDialog.Builder(context, R.style.AlertDialogCustom)
                                        .setTitle("NOTE")
                                        .setMessage(context.getString(R.string.note) + "(" + nextDate + ")")
                                        .setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        url = "https://www.q-tickets.com/Flikcinema/SelectSeat?movie_id=" + flick_movie_id + "&show_time=" +
                                                                show_timimg + "&date=" + show_date + "&sid=" + show_timing_id + "&screenid=" + screen_id + "&variantid="
                                                                + varaiant_id + "&cinemaid=" + cid + "&cinema_screen=" + timeList.get(position).screenName + "&PgRating=" + censor;
                                                        Bundle b = new Bundle();
                                                        b.putString("url", url);
                                                        b.putString("pagename", movieTitle);
                                                        Intent i = new Intent(context, ShowWebView.class);
                                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        i.putExtras(b);
                                                        context.startActivity(i);
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).create();
                                mid_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
                                mid_dialog.show();


                            } else {
                                url = "https://www.q-tickets.com/Flikcinema/SelectSeat?movie_id=" + flick_movie_id + "&show_time=" +
                                        show_timimg + "&date=" + show_date + "&sid=" + show_timing_id + "&screenid=" + screen_id + "&variantid="
                                        + varaiant_id + "&cinemaid=" + cid + "&cinema_screen=" + timeList.get(position).screenName + "&PgRating=" + censor;
                                Bundle b = new Bundle();
                                b.putString("url", url);
                                b.putString("pagename", movieTitle);
                                Intent i = new Intent(context, ShowWebView.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtras(b);
                                context.startActivity(i);
                            }
                        } else if (cid.equals("0028") || cid.equals("0030") || cid.equals("0033") || cid.equals("0018") || cid.equals("041")) {
                            if (timeList.get(position).showStatus.equals("3")) {
                                nextDate = incrementDateByNovo(timeList.get(position).showDate);
                                mid_dialog = new android.app.AlertDialog.Builder(context, R.style.AlertDialogCustom)
                                        .setTitle("NOTE")
                                        .setMessage(context.getString(R.string.note) + "(" + nextDate + ")")
                                        .setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Bundle b = new Bundle();
                                                        b.putString("duration", duration);
                                                        b.putString("movie_type", movie_type);
                                                        b.putString("movie_img_url", movie_img_url);
                                                        b.putString("screen_name", timeList.get(position).screenName);
                                                        b.putString("show_time", show_timimg);
                                                        b.putString("cid", cid);
                                                        b.putString("session_id", seesion_id);
                                                        b.putString("mall_name", mall_name);
                                                        b.putString("movie_title", movieTitle);
                                                        b.putString("Screen_Type", screen_type);
                                                        b.putString("cencor", cencor);
                                                        b.putString("show_date", changeDateFormmated(timeList.get(position).showDate));
                                                        Intent i = new Intent(context, NovoTicketSelectionActivity.class);
                                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        i.putExtras(b);
                                                        context.startActivity(i);
                                                    }
                                                })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).create();
                                mid_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.backcolor)));
                                mid_dialog.show();


                            } else {
                                Bundle b = new Bundle();
                                b.putString("duration", duration);
                                b.putString("movie_type", movie_type);
                                b.putString("movie_img_url", movie_img_url);
                                b.putString("screen_name", timeList.get(position).screenName);
                                b.putString("show_time", show_timimg);
                                b.putString("cid", cid);
                                b.putString("session_id", seesion_id);
                                b.putString("mall_name", mall_name);
                                b.putString("movie_title", movieTitle);
                                b.putString("Screen_Type", screen_type);
                                b.putString("cencor", cencor);
                                b.putString("show_date", changeDateFormmated(timeList.get(position).showDate));
                                Intent i = new Intent(context, NovoTicketSelectionActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtras(b);
                                context.startActivity(i);
                            }
                        }
                    } else {
                        QTUtils.showDialogbox(context, context.getString(R.string.no_show_time));
                                   /* Toast toast = Toast.makeText(context,context.getString(R.string.no_show_time), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();*/
                    }

                }


            }


        });

    }

    public String incrementDateByNovo(String inputDateStr) {
        String date = "";
        String inputDateFormatStr = "dd-MM-yyyy";
        String outputDateFormatStr = "dd-MMM-yyyy( EEE )";

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormatStr, Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormatStr, Locale.US);
            Date inputDate = inputFormat.parse(inputDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputDate);
            String timeOfDay = calendar.get(Calendar.HOUR_OF_DAY) < 12 ? "Morning" : "Evening";
            String formattedDate = String.format("%s %s", outputFormat.format(inputDate), timeOfDay);
            //   calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date incrementedDate = calendar.getTime();
            timeOfDay = calendar.get(Calendar.HOUR_OF_DAY) < 12 ? "Morning" : "Evening";
            date = String.format("%s %s", outputFormat.format(incrementedDate), timeOfDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String incrementDateByOne(String inputDateStr) {
        String date = "";
        String inputDateFormatStr = "yyyy-MM-dd";
        String outputDateFormatStr = "dd-MMM-yyyy( EEE )";

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormatStr, Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormatStr, Locale.US);
            Date inputDate = inputFormat.parse(inputDateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputDate);
            String timeOfDay = calendar.get(Calendar.HOUR_OF_DAY) < 12 ? "Morning" : "Evening";
            String formattedDate = String.format("%s %s", outputFormat.format(inputDate), timeOfDay);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date incrementedDate = calendar.getTime();
            timeOfDay = calendar.get(Calendar.HOUR_OF_DAY) < 12 ? "Morning" : "Evening";
            date = String.format("%s %s", outputFormat.format(incrementedDate), timeOfDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

//    private void timeconvertionMethod(String time) throws ParseException {
//        DateFormat f1 = new SimpleDateFormat("HH:mm"); //HH for hour of the day (0 - 23)
//        Date d = f1.parse(time);
//        DateFormat f2 = new SimpleDateFormat("hh:mm a");
//        result_time = f2.format(d).toLowerCase();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView txt_time, txt_screen_type;
        RelativeLayout c1;


        public ViewHolder(View v) {
            super(v);
            view = v;
            txt_time = v.findViewById(R.id.txt_time);
            txt_screen_type = v.findViewById(R.id.txt_screen_type);
            c1 = v.findViewById(R.id.c1);

        }
    }
}
