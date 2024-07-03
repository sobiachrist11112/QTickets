package com.production.qtickets.searchList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.model.MovieAndEventSearchResult;
import com.production.qtickets.model.SearchItem;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Harris on 30-05-2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    //lsit of seearch items based on the search keaywords
    Context context;
    String msg, movieId;
    MovieAndEventSearchResult.Datum searchItem;
    private List<MovieAndEventSearchResult.Datum> searchItems;
    private ArrayList<MovieAndEventSearchResult.Datum> names;
    SessionManager sessionManager;

    public CustomAdapter(List<SearchItem> searchItems) {

    }

    public CustomAdapter(Context context,List<MovieAndEventSearchResult.Datum> searchItems) {
        this.searchItems = searchItems;
        this.names = new ArrayList<MovieAndEventSearchResult.Datum>();
        this.names.addAll(searchItems);
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        sessionManager = new SessionManager(context);
        searchItem = searchItems.get(position);
        holder.textViewName.setText(searchItem.name);
        msg = searchItem.webViewUrl;
        movieId = String.valueOf(searchItem.id);

    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public void filter(String newText, String sType) {
        newText = newText.toLowerCase(Locale.getDefault());
        searchItems.clear();
        if (newText.length() == 0) {
            searchItems.addAll(names);
        } else {
            for (MovieAndEventSearchResult.Datum wp : names) {
                if (wp.name.toLowerCase(Locale.getDefault()).contains(newText.toLowerCase())) {
                    searchItems.add(wp);
                }
            }
            if (searchItems.size() == 0) {

                if (sType.equals("2")) {
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                }

            } else {

                if (sType.equals("2")) {
                    if(searchItem.webViewUrl.equalsIgnoreCase("movies")) {
                        sessionManager.setMovieId(movieId);
                        Intent i = new Intent(context, MovieDetailsActivity.class);
                        context.startActivity(i);

                    }else {
                        if(searchItem.isWebVeiw) {
                            Bundle b = new Bundle();
                            sessionManager.setEventId(searchItem.id);
                            b.putString("eventID", searchItem.id);
                            b.putString("eventSource", "eventPage");
                            Intent i = new Intent(context, EventDetailsActivity.class);
                            i.putExtras(b);
                            context.startActivity(i);
                        }else {
                            String eventWebViewUrl = searchItem.webViewUrl;
                            String event_title = searchItem.name;
                            Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
                            webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                            webViewIntent.putExtra("title",event_title);
                            context.startActivity(webViewIntent);
                        }
                    }

                }

            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        LinearLayout l1;

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_search_list);
            l1 = itemView.findViewById(R.id.l1);
        }
    }

}