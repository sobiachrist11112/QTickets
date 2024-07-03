package com.production.qtickets.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.production.qtickets.R;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.moviedetailes.ShowTimingActivity;
import com.production.qtickets.moviedetailes.YoutubeActivity;

import java.util.List;


/**
 * Created by hemanth on 4/12/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {
    List<MovieModel> movieListArrayList;
    Context context;
    int differenciate;
    SessionManager sessionManager;
    String commingsoon = "";
    String url;


    public MovieListAdapter(Context context, List<MovieModel> movieListArrayList, int differenciate) {
        this.movieListArrayList = movieListArrayList;
        this.context = context;
        this.differenciate = differenciate;
    }

    public MovieListAdapter(Context context, List<MovieModel> movieListArrayList, int differenciate, String commingsoon) {
        this.movieListArrayList = movieListArrayList;
        this.context = context;
        this.differenciate = differenciate;
        this.commingsoon = commingsoon;

    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieListViewHolder movieListViewHolder;
        if (differenciate == 100) {
            View movieList = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item_row_two, parent, false);
            movieListViewHolder = new MovieListViewHolder(movieList);
        } else {
            View movieList = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items_row_with_vertical_scroll_two, parent, false);
            movieListViewHolder = new MovieListViewHolder(movieList);
        }

        return movieListViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, final int position) {
        final MovieModel mList = movieListArrayList.get(position);
        sessionManager = new SessionManager(context);
        holder.tv_movie_name.setText(mList.name);

        if(TextUtils.isEmpty(mList.iMDBRating) || mList.iMDBRating.equalsIgnoreCase("N/A")|| mList.iMDBRating.contains("0")){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(10);
        //   holder.pgLayout.setLayoutParams(params);
          holder.imdb_layout.setVisibility(View.GONE);
            holder.imdb_view.setVisibility(View.GONE);
        } else {
            holder.imdb_layout.setVisibility(View.VISIBLE);
           holder.imdb_view.setVisibility(View.VISIBLE);
            holder.tv_imdb_rating.setText(mList.iMDBRating + "/10");
        }

        if (!TextUtils.isEmpty(mList.censor)){
            holder.tv_sensor_rating.setText(mList.censor);
        }else {
            holder.tv_sensor_rating.setText(" ");
        }

        if (!TextUtils.isEmpty(mList.genre)) {
            holder.tv_movie_genre.setText(mList.genre);
        } else {
            holder.tv_movie_genre.setText(mList.m_genre);
        }

        if (!TextUtils.isEmpty(mList.iphonethumb)) {
            Glide.with(context.getApplicationContext()).load(mList.iphonethumb)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        } else if (!TextUtils.isEmpty(mList.thumb)) {
            Glide.with(context.getApplicationContext()).load(mList.thumb)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        } else if (!TextUtils.isEmpty(mList.thumbURL)) {
            Glide.with(context.getApplicationContext()).load(mList.thumbURL)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        } else {
            Glide.with(context.getApplicationContext()).load(mList.thumbnail)
                    .thumbnail(0.5f)
                    .into(holder.iv_movie_image);
        }
        if (TextUtils.isEmpty(commingsoon)) {
            if (sessionManager.getCountryName().equals("Qatar")) {
              //  holder.txt_book.setText(context.getString(R.string.book));
//                holder.img_tic_watch.setImageResource(R.drawable.ic_ticket);
                holder.iv_movie_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionManager.setMovieId(mList.id);
                        Intent movieDetails = new Intent(context, MovieDetailsActivity.class);
                        context.startActivity(movieDetails);
                    }
                });
                holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!TextUtils.isEmpty(mList.Age_restrict_rating)) {
                            sessionManager.setAgeRestrict_Censor(mList.Age_restrict_rating,mList.censor);
                        }else {
                            sessionManager.setAgeRestrict_Censor(mList.ageRestrictRating,mList.censor);
                        }
                        if (!TextUtils.isEmpty(mList.ipadthumb)) {
                            url = mList.ipadthumb;
                        } else if (!TextUtils.isEmpty(mList.thumbURL)) {
                            url = mList.thumbURL;
                        } else if(!TextUtils.isEmpty(mList.iphonethumb)){
                            url = mList.iphonethumb;
                        } else {
                            url = mList.thumb;
                        }
                        sessionManager.setMovieId(mList.id);
                        Bundle bundle = new Bundle();
                        bundle.putString("movie_title", mList.name);
                        bundle.putString("duration", mList.duration);
                        bundle.putString("movie_type", mList.movieType);
                        bundle.putString("movie_img_url", url);
                        bundle.putString("cencor", mList.censor);
                        Intent movieDetails = new Intent(context, ShowTimingActivity.class);
                        movieDetails.putExtras(bundle);
                        context.startActivity(movieDetails);
                    }
                });
            } else {
                if (sessionManager.getCountryName().equals("Dubai")) {
                 //   holder.txt_book.setText(context.getString(R.string.book));
                  //  holder.img_tic_watch.setVisibility(View.GONE);
                }else{
                  //  holder.img_tic_watch.setVisibility(View.VISIBLE);
                 //   holder.txt_book.setText(context.getString(R.string.watch));
                 //   holder.img_tic_watch.setImageResource(R.drawable.ic_play_button);
                }
                holder.tv_booknow.setOnClickListener(view -> {
                    if (sessionManager.getCountryName().equals("Dubai")){
                        String eventWebViewUrl="";
                        if (mList.clickURL==null){
                            eventWebViewUrl="https://www.q-tickets.com/Movies/MoviesListDetails/"+mList.id+"/"+sessionManager.getCountryName().toLowerCase()+"/alone-english";
                        }else{                        String event_title = mList.name;

                            eventWebViewUrl = mList.clickURL+"/"+sessionManager.getCountryName().toLowerCase()+"/alone-english";
                        }
                        Log.e("URL====== ",""+mList.clickURL+"/"+sessionManager.getCountryName().toLowerCase()+"/alone-english");
                        Intent webViewIntent = new Intent(context, EventWebviewActivity.class);
                        webViewIntent.putExtra("webViewURL", eventWebViewUrl);
                        webViewIntent.putExtra("title",mList.name);
                        webViewIntent.putExtra("pagename",mList.name);
                        context.startActivity(webViewIntent);
                    }else {

                        if (!TextUtils.isEmpty(mList.trailerURL)) {
                            Bundle b = new Bundle();
                            b.putString("webViewURL", mList.trailerURL);
                            b.putString("tittle",mList.name);
                            b.putString("pagename",mList.name);
                            Intent i = new Intent(context, YoutubeActivity.class);
                            i.putExtras(b);
                            context.startActivity(i);
                        } else {
                            QTUtils.showDialogbox(context, context.getString(R.string.notrailerurl));
                        }
                    }
                });
            }

        } else {
           // holder.img_tic_watch.setImageResource(R.drawable.ic_play_button);
           holder.txt_book.setText(context.getString(R.string.watch));
            holder.tv_booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(mList.trailerURL)) {
                        Bundle b = new Bundle();
                        b.putString("url", mList.trailerURL);
                        b.putString("tittle",mList.name);
                        Intent i = new Intent(context, YoutubeActivity.class);
                        i.putExtras(b);
                        context.startActivity(i);
                    } else {
                        QTUtils.showDialogbox(context, context.getString(R.string.notrailerurl));
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return movieListArrayList.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_movie_image, img_tic_watch;
        TextView tv_imdb_rating, txt_book,tv_sensor_rating;
        TextView tv_movie_name;
        TextView tv_movie_genre,tv_booknowtittle;
        View imdb_view;
        LinearLayout tv_booknow,imdb_layout,pgLayout;

        public MovieListViewHolder(View itemView) {
            super(itemView);
            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
            tv_sensor_rating = itemView.findViewById(R.id.tv_sensor_rating);
            tv_imdb_rating = itemView.findViewById(R.id.tv_imdb_rating);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);
            tv_movie_genre = itemView.findViewById(R.id.tv_movie_genre);
            tv_booknow = itemView.findViewById(R.id.tv_booknow);
            txt_book = itemView.findViewById(R.id.txt_book);
            tv_booknowtittle = itemView.findViewById(R.id.tv_booknowtittle);
            img_tic_watch = itemView.findViewById(R.id.img_tic_watch);
            imdb_layout = itemView.findViewById(R.id.imdb_layout);
          //  pgLayout = itemView.findViewById(R.id.pgLayout);
            imdb_view = itemView.findViewById(R.id.view);
        }
    }
}
