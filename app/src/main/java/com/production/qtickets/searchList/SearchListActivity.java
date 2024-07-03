package com.production.qtickets.searchList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.activity.ShowWebView;
import com.production.qtickets.activity.ShowWebView2;
import com.production.qtickets.eventDetailQT5.EventDetailQT5Activity;
import com.production.qtickets.model.MovieAndEventSearchResult;
import com.production.qtickets.model.SearchItem;
import com.production.qtickets.model.SearchListItem;
import com.production.qtickets.moviedetailes.YoutubeActivity;
import com.production.qtickets.utils.AppConstants;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.RecyclerItemClickListener;
import com.production.qtickets.utils.SessionManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.production.qtickets.utils.TextviewBold;

import com.production.qtickets.events.EventDetails.EventDetailsActivity;
import com.production.qtickets.events.EventDetails.EventWebviewActivity;
import com.production.qtickets.moviedetailes.MovieDetailsActivity;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.Items;

/**
 * Created by Harris on 30-05-2018.
 */

public class SearchListActivity extends AppCompatActivity implements View.OnClickListener,
        SearchListContractor.View, SearchView.OnQueryTextListener {

    RecyclerView recyclerView, recyclerView2;
    SessionManager sessionManager;
    private CustomAdapter adapter;
    private TopMoviesAdapter topMoviesAdapter;
    SearchListPresenter presenter;
    String search = "aa", sType, movie_id, key;
    List<MovieAndEventSearchResult.Datum> filterdNames = new ArrayList<>();
    ImageView close, iv_back_arrow;

    //searchview
    SearchView searchView;
    EditText searchEditText;
    TextviewBold topmovies;
    List<MovieAndEventSearchResult.Datum> moviList;
    TextView txt_no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_movie_header);
        QTUtils.setStatusBarGradiant(this);
        presenter = new SearchListPresenter();
        presenter.attachView(this);
        init();
    }

    //initialize the views
    public void init() {
        sessionManager = new SessionManager(SearchListActivity.this);
        txt_no_data = findViewById(R.id.txt_no_data);
        close = findViewById(R.id.img_close);
        close.setVisibility(View.GONE);
        close.setOnClickListener(this);
        topmovies = findViewById(R.id.tv_topmovies);
        iv_back_arrow = findViewById(R.id.iv_back_arrow);
        iv_back_arrow.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycler_list);
        recyclerView2 = findViewById(R.id.recycler_list_2);
        recyclerView.setHasFixedSize(true);
        recyclerView2.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        doTopmovies();
        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(SearchListActivity.this,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, final int position) {
                                // key = filterdNames.get(position).webViewUrl;
                                if (filterdNames.get(position).type == 1) {
                                    sessionManager.setMovieId(String.valueOf(filterdNames.get(position).id));
                                    Intent movieDetails = new Intent(SearchListActivity.this, MovieDetailsActivity.class);
                                    if (filterdNames.get(position).path != null) {
                                        Bundle b = new Bundle();
                                        b.putString("url", filterdNames.get(position).path);
                                        b.putString("tittle", filterdNames.get(position).name);
                                        movieDetails.putExtras(b);
                                    }
                                    startActivity(movieDetails);
//                                    if (filterdNames.get(position).path != null) {
//                                        Bundle b = new Bundle();
//                                        b.putString("url", filterdNames.get(position).path);
//                                        b.putString("tittle", filterdNames.get(position).name);
//                                        Intent i = new Intent(SearchListActivity.this, YoutubeActivity.class);
//                                        i.putExtras(b);
//                                        startActivity(i);
//                                    } else {
//                                        Intent movieDetails = new Intent(SearchListActivity.this, MovieDetailsActivity.class);
//                                        startActivity(movieDetails);
//                                    }

                                } else {

                                    //https://verify.q-tickets.com/eventDetail/3875635406/KIDZMONDO

                                    if (!filterdNames.get(position).isWebVeiw) {
                                        Intent eventIntent = new Intent(SearchListActivity.this, EventDetailQT5Activity.class);
                                        eventIntent.putExtra(AppConstants.EVENT_ID, Integer.parseInt(filterdNames.get(position).id));
//                                        eventIntent.putExtra(AppConstants.EVENT_ID, 20685);
                                        startActivity(eventIntent);
                                    } else {
                                        Bundle b = new Bundle();
                                        // b.putString("webViewURL", "https://verify.q-tickets.com/eventDetail/3875635406/KIDZMONDO");
                                        b.putString("webViewURL", filterdNames.get(position).webViewUrl);
                                        b.putString("pagename", filterdNames.get(position).name);
                                        Intent i = new Intent(SearchListActivity.this, ShowWebView2.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        i.putExtras(b);
                                        startActivity(i);
                                    }
                                }

                            }

                        })
        );

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchListActivity.this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        if (moviList.get(position).type == 1) {
                            sessionManager.setMovieId(String.valueOf(moviList.get(position).id));

                            if (moviList.get(position).path != null) {
                                Bundle b = new Bundle();
                                b.putString("url", moviList.get(position).path);
                                b.putString("tittle", moviList.get(position).name);
                                Intent i = new Intent(SearchListActivity.this, YoutubeActivity.class);
                                i.putExtras(b);
                                startActivity(i);
                            } else {
                                Intent movieDetails = new Intent(SearchListActivity.this, MovieDetailsActivity.class);
                                startActivity(movieDetails);
                            }

                        }

                        if (moviList.get(position).type == 2) {
                            if (!moviList.get(position).isWebVeiw) {
                                Intent eventIntent = new Intent(SearchListActivity.this, EventDetailQT5Activity.class);
                                eventIntent.putExtra(AppConstants.EVENT_ID, Integer.parseInt(moviList.get(position).id));
                                startActivity(eventIntent);
                            } else {
                                Bundle b = new Bundle();
                                b.putString("webViewURL", moviList.get(position).webViewUrl);
                                b.putString("pagename", moviList.get(position).name);
                                Intent i = new Intent(SearchListActivity.this, ShowWebView2.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtras(b);
                                startActivity(i);
                            }
                        }

                    }
                })
        );

        searchView = findViewById(R.id.et_search);
        searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorWhite));
        searchEditText.setTextSize(14);
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorGray));
        search = searchEditText.getText().toString().trim();

        searchView.setOnQueryTextListener(this);
        // where params are (left,top,right,bottom)
        searchEditText.setHint(getString(R.string.txt_serch_hint));
        searchView.setIconified(false);
        searchView.clearFocus();

    }

    private void doTopmovies() {
        showPb();
        presenter.callTopmovies(SearchListActivity.this, sessionManager.getCountryID(), "");
    }

    @Override
    public void setTopmovies(MovieAndEventSearchResult response) {
        try {
            moviList = new ArrayList<>();
            moviList = response.data;
            if (moviList.size() > 0) {
                topMoviesAdapter = new TopMoviesAdapter(SearchListActivity.this, moviList);
                recyclerView.setAdapter(topMoviesAdapter);
            } else {
                QTUtils.showDialogbox(this, "Error!!");
                //Toast.makeText(LoginActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }


    private void doSearch() {
        search = searchEditText.getText().toString().trim();
        //showPb();
        presenter.getSearch(String.valueOf(sessionManager.getCountryID()), search);
    }

    @Override
    public void setSerachList(MovieAndEventSearchResult response) {
        try {
            filterdNames = response.data;
            if (filterdNames.size() > 0) {
                if (filterdNames != null) {
                    txt_no_data.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    adapter = new CustomAdapter(SearchListActivity.this, filterdNames);
                    recyclerView2.setAdapter(adapter);
                } else {
                    QTUtils.showDialogbox(this, "Error!!");
                    //Toast.makeText(LoginActivity.this, getString(R.string.noresponse), Toast.LENGTH_LONG).show();
                }
            } else {
                recyclerView2.setVisibility(View.GONE);
                txt_no_data.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch
            // block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String newText = query;
        sType = "2";
        // adapter.filter(newText, sType);
        recyclerView2.setVisibility(View.VISIBLE);
        topmovies.setText("Showing Results");
        topmovies.setTextColor(Color.WHITE);
        recyclerView.setVisibility(View.GONE);
        doSearch();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        try {
            if (newText.length() > 2) {
                recyclerView.setAdapter(adapter);
                close.setVisibility(View.VISIBLE);
                sType = "1";
                /// adapter.filter(newText, sType);

                recyclerView2.setVisibility(View.VISIBLE);
                topmovies.setText("Showing Results");
                topmovies.setTextColor(Color.WHITE);
                recyclerView.setVisibility(View.GONE);
                doSearch();

                if (filterdNames.size() == 0) {
                    recyclerView2.setVisibility(View.GONE);
                    txt_no_data.setVisibility(View.VISIBLE);
                } else {
                    txt_no_data.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                }

            } else {
                txt_no_data.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public void onClick(View view) {

        Intent i;
        switch (view.getId()) {
            case R.id.img_close:
                searchEditText.setText("");
                searchEditText.clearComposingText();
                topmovies.setText("TOP MOVIES");
                topmovies.setTextColor(Color.WHITE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                txt_no_data.setVisibility(View.GONE);
                doTopmovies();
                close.setVisibility(View.GONE);
                break;
            case R.id.iv_back_arrow:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void dismissPb() {
        QTUtils.progressDialog.dismiss();
    }

    @Override
    public void showPb() {
        QTUtils.showProgressDialog(SearchListActivity.this, true);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
        if (throwable instanceof SocketTimeoutException) {
            //  showToast("Socket Time out. Please try again.");
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof UnknownHostException) {
            //  showToast("No Internet");
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
        QTUtils.showAlertDialogbox(object1, object3, SearchListActivity.this, message);
    }


    @Override
    public void onDestroy() {
        QTUtils.dismissProgressDialog();
        presenter.detach();
        super.onDestroy();

    }


}