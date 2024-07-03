package com.production.qtickets.moviedetailes;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.DateModel;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.TextviewBold;

import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harsh on 5/28/2018.
 */
public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.ViewHolder> {

    // in this class we re binding the dates to the available dates,
    // dates in which movies are disabled by changing the colors and making unclickable

    List<DateModel> dates;
    Context context;
    String str_result_date;
    Date date;
    List<DateModel> dList = new ArrayList<>();
    List<String> str_date = new ArrayList<String>();
    private AdapterView.OnItemClickListener onItemClickListener;
    private int mSelectedItem = 0;
    ArrayList<String> compa_dates;
    Toast mtoast;


    public DatesAdapter(ArrayList<DateModel> dates, Context context) {
        GlobalBus.getBus().register(this);
        this.context = context;
        this.dates = dates;
    }

    public void addValuesAdapter(List<DateModel> dates,ArrayList<String> compa_dates) {
        this.dates = dates;
        this.compa_dates = compa_dates;
        notifyDataSetChanged();
    }

    public void addstaticValuesAdapter(List<DateModel> dates, ArrayList<String> compa_dates) {
        this.dates = dates;
        this.compa_dates = compa_dates;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventlist = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_date, parent, false);
        ViewHolder filterViewHolder = new ViewHolder(eventlist, this);
        return filterViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvWeek.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(dates.get(position).date)) {
            dateConvertionMethod(dates.get(position).date);
            holder.tvDate.setText(str_result_date);
        }

        //parcing the dates according to the required pattern
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String inputText = dates.get(position).date;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EE"); // the day of the week abbreviated
        holder.tvWeek.setText(simpleDateformat.format(date));
        if (dList.size() > 0) {
            holder.c1.setBackground(mSelectedItem == position ? context.getResources().getDrawable(R.drawable.button_background) : context.getResources().getDrawable(R.drawable.transparent_movie_category_background));
            holder.tvDate.setTextColor(mSelectedItem == position ? context.getResources().getColor(R.color.colorBlack) : context.getResources().getColor(R.color.colorWhite));
            holder.tvWeek.setTextColor(mSelectedItem == position ? context.getResources().getColor(R.color.colorBlack) : context.getResources().getColor(R.color.colorWhite));
        }
        /*Change the color of date if it is not available in api*/
        if (dList.size() <= 7) {
            if (compa_dates.contains(dates.get(position).date)) {
                if (mSelectedItem == position) {
                    holder.tvWeek.setTextColor(context.getResources().getColor(R.color.colorBlack));
                    holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorBlack));
                    holder.c1.setBackground(context.getResources().getDrawable(R.drawable.yellow_with_corner_back));
                } else {
                    holder.tvWeek.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorWhite));
                    holder.c1.setBackground(context.getResources().getDrawable(R.drawable.transparent_movie_category_background));
                }
            } else {
                holder.tvWeek.setTextColor(context.getResources().getColor(R.color.occupied));
                holder.tvDate.setTextColor(context.getResources().getColor(R.color.occupied));
                holder.c1.setBackground(context.getResources().getDrawable(R.drawable.gray_rect_background));
            }
        } else {
            if (mSelectedItem == position) {
                holder.tvWeek.setTextColor(context.getResources().getColor(R.color.colorBlack));
                holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorBlack));
                holder.c1.setBackground(context.getResources().getDrawable(R.drawable.yellow_with_corner_back));
            } else {
                holder.tvWeek.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorWhite));
                holder.c1.setBackground(context.getResources().getDrawable(R.drawable.transparent_movie_category_background));
            }
        }


    }

    @Subscribe
    public void getMessage(Events.ActivityAdapterMessageList activityFragmentMessage) {
        dList = activityFragmentMessage.getList();
        str_date = new ArrayList<String>();
        for (int i = 0; i < dList.size(); i++) {
            str_date.add(dList.get(i).date);
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_date)
        TextviewBold tvDate;
        @BindView(R.id.tv_week)
        TextviewBold tvWeek;
        @BindView(R.id.c1)
        RelativeLayout c1;
        private DatesAdapter mAdapter;

        public ViewHolder(View itemView, final DatesAdapter mAdapter) {
            super(itemView);
            this.mAdapter = mAdapter;
            ButterKnife.bind(this, itemView);
            c1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (str_date.contains(dates.get(getAdapterPosition()).date)) {
                if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                // Updating old as well as new positions
                notifyItemChanged(mSelectedItem);
                mSelectedItem = getAdapterPosition();
                notifyItemRangeChanged(0, dates.size());
                notifyItemChanged(mSelectedItem);
                mAdapter.onItemHolderClick(ViewHolder.this);
            } else {
                QTUtils.showDialogbox(context,context.getString(R.string.no_show_date));
                /*Toast toast = Toast.makeText(context,context.getString(R.string.no_show_date), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();*/
            }


        }
    }

    // converting the date format
    private void dateConvertionMethod(String date) {
        if (date != null) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            try {
                date1 = form.parse(date);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            SimpleDateFormat postFormater = new SimpleDateFormat("MMM dd");
            str_result_date = postFormater.format(date1);

        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(ViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.c1, holder.getAdapterPosition(), holder.getItemId());


    }

}