package com.production.qtickets.searchList;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.model.MovieAndEventSearchResult;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.model.MovieModel;

import java.util.List;


/**
 * Created by Harris on 08-06-2018.
 */

public class TopMoviesAdapter extends RecyclerView.Adapter<TopMoviesAdapter.ViewHolder> {
    Context context;

    String msg;
    List<MovieAndEventSearchResult.Datum> loginList;
    MovieAndEventSearchResult.Datum movieModel;
    SessionManager sessionManager;

    //declare interface
    private OnItemClicked onClick;

    //make interface like this
    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public TopMoviesAdapter(Context context, List<MovieAndEventSearchResult.Datum> loginList) {
        this.context = context;
        this.loginList = loginList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        sessionManager = new SessionManager(context);
        movieModel = loginList.get(position);
        holder.textViewName.setText(movieModel.name);


//        holder.textViewName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // onClick.onItemClick(position);
//                sessionManager.setMovieId(movieModel.id);
//                Intent i = new Intent(context.getApplicationContext(), MovieDetailsActivity.class);
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return loginList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        LinearLayout l1;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.tv_search_list);
            l1 = itemView.findViewById(R.id.l1);
        }
    }
}
