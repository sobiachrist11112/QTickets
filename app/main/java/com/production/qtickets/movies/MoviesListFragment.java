package com.production.qtickets.movies;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.production.qtickets.R;
import com.production.qtickets.activity.MainActivity;
import com.production.qtickets.eventQT5.EventHomeActivity;
import com.production.qtickets.model.MovieModel;
import com.production.qtickets.model.ReelCinemaMovieListModel;
import com.production.qtickets.utils.Events;
import com.production.qtickets.utils.GlobalBus;
import com.production.qtickets.utils.QTUtils;
import com.production.qtickets.utils.SessionManager;

import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.production.qtickets.activity.NavigationDrawerActivity;
import com.production.qtickets.adapters.CinemaFilterAdapter;
import com.production.qtickets.adapters.FilterAdapter;
import com.production.qtickets.dashboard.DashBoardActivity;
import com.production.qtickets.model.Items;
import com.production.qtickets.searchList.SearchListActivity;

/**
 * Created by hemanth on 4/12/2018.
 */

public class MoviesListFragment extends Fragment implements View.OnClickListener, MoviContracter.View {

    RecyclerView movielist_recyclerview, laung_recyclerview;
    RecyclerView.LayoutManager mLayoutManager, mlaunglayoutmanager;
    MovieListAdapter movieListAdapter;
    FilterAdapter filter_adapter;
    CinemaFilterAdapter cinema_filter_adapter;
    TextView tv_now_showing, tv_top_movies, tv_coming_soon, txt_done;
    boolean is_now_showing = false, is_top_movies = true, is_coming_soon = true;
    ArrayList<String> launguage = new ArrayList<>();
    Dialog customDialog;
    LinearLayout lin_all_laung, lin_cinema_all, filter_layout;
    HashSet<String> hashSet_remove_duplicated_items;
    String txt_sel_launguage = "", txt_sel_cinema = "", sel_id = "";
    MovieListPresenter presenter;
    Context context;
    List<MovieModel> mList;
    RelativeLayout main_layout;
    List<MovieModel> cList = new ArrayList<>();
    ImageView ic_home, menu_icon, iv_search,ic_movies,ic_event;
    private  TextView tv_hometittle,tv_moviewtittle,tv_eventtittle,tv_more;
    SessionManager sessionManager;
    View v1;
    ConstraintLayout c1,c1_cacel;
    ImageView img_no_bookings;
    TextView no_txt_available, txt_all_laung, txt_all_cinema;
    Handler handler;
    Dialog progressDialog;
    private  LinearLayout ll_home,ll_movies,ll_events,ll_more;

    ArrayList<String> headerlist = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
        context = getActivity();
        img_no_bookings = view.findViewById(R.id.img_no_bookings);
        no_txt_available = view.findViewById(R.id.no_txt_available);
        c1 = view.findViewById(R.id.c1);
        no_txt_available.setText(getString(R.string.no_movies));
        img_no_bookings.setImageResource(R.drawable.ic_3d_glasses);
        txt_all_laung = view.findViewById(R.id.txt_all_laung);
        txt_all_cinema = view.findViewById(R.id.txt_all_cinema);
        presenter = new MovieListPresenter();
        presenter.attachView(this);
        movielist_recyclerview = view.findViewById(R.id.movielist_recyclerview);
        main_layout = view.findViewById(R.id.main_layout);

        tv_now_showing = view.findViewById(R.id.tv_now_showing);
        tv_now_showing.setOnClickListener(this);
        tv_top_movies = view.findViewById(R.id.tv_top_movies);
        tv_top_movies.setOnClickListener(this);
        tv_coming_soon = view.findViewById(R.id.tv_coming_soon);
        tv_coming_soon.setOnClickListener(this);

        lin_all_laung = view.findViewById(R.id.lin_all_laung);
        lin_all_laung.setOnClickListener(this);
        lin_cinema_all = view.findViewById(R.id.lin_cinema_all);
        lin_cinema_all.setOnClickListener(this);



        ic_home = view.findViewById(R.id.ic_home);
        ic_event = view.findViewById(R.id.ic_event);
        ic_home.setOnClickListener(this);
        ic_event.setOnClickListener(this);
        ic_movies = view.findViewById(R.id.ic_movies);
        ic_movies.setOnClickListener(this);
        menu_icon = view.findViewById(R.id.menu_icon);
        menu_icon.setOnClickListener(this);
        iv_search = view.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);

        tv_moviewtittle=view.findViewById(R.id.tv_moviewtittle);
        tv_eventtittle=view.findViewById(R.id.tv_eventtittle);
        tv_more=view.findViewById(R.id.tv_more);
        tv_hometittle=view.findViewById(R.id.tv_hometittle);

        filter_layout = view.findViewById(R.id.filter_layout);
        filter_layout.setOnClickListener(this);
        v1 = view.findViewById(R.id.v1);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        movielist_recyclerview.setLayoutManager(mLayoutManager);
        handler = new Handler();

        ll_home=view.findViewById(R.id.ll_home);
        ll_movies=view.findViewById(R.id.ll_movies);
        ll_events=view.findViewById(R.id.ll_events);
        ll_more=view.findViewById(R.id.ll_more);

        ll_home.setOnClickListener(this);
        ll_movies.setOnClickListener(this);
        ll_events.setOnClickListener(this);
        ll_more.setOnClickListener(this);


        if(getArguments() != null){
            headerlist = getArguments().getStringArrayList("headerlist");
        }

        setAllHomescreenicons();

        try {
            init();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }


    private void setAllHomescreenicons() {
        ic_home.setImageDrawable(getResources().getDrawable(R.drawable.ic_home));
        tv_hometittle.setTextColor(getResources().getColor(R.color.plane_white));

        ic_movies.setImageDrawable(getResources().getDrawable(R.drawable.ic_movieyellow));
        tv_moviewtittle.setTextColor(getResources().getColor(R.color.colorAccent));


        ic_event.setImageDrawable(getResources().getDrawable(R.drawable.ic_events));
        tv_eventtittle.setTextColor(getResources().getColor(R.color.plane_white));

        menu_icon.setImageDrawable(getResources().getDrawable(R.drawable.more));
        tv_more.setTextColor(getResources().getColor(R.color.plane_white));

    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        sel_id = activityFragmentMessage.getId();
        customDialog.dismiss();
        lin_all_laung.setEnabled(true);
        lin_cinema_all.setEnabled(true);
        if (TextUtils.isEmpty(sel_id)) {
            txt_sel_launguage = activityFragmentMessage.getMessage();
            txt_all_laung.setText(txt_sel_launguage);
            txt_sel_cinema = "";
            txt_all_cinema.setText(getString(R.string.cinemahall));
            sessionManager.setSelectedCinema("");
            if (txt_sel_launguage.equals(getString(R.string.alllaunguages))) {
                // showPb();
                showProgressdialog(true);
                presenter.getMovie(sessionManager.getCountryCode());
            } else {
                // showPb();
                showProgressdialog(true);
                presenter.getMovieLaung(txt_sel_launguage);
            }
        } else {
            txt_sel_cinema = activityFragmentMessage.getMessage();
            if (txt_sel_cinema.equals(getString(R.string.cinemahall))) {
                txt_all_laung.setText(getString(R.string.alllaunguages));
            }
            txt_all_cinema.setText(txt_sel_cinema);

            if (txt_sel_cinema.equals(getString(R.string.cinemahall))) {
                sessionManager.setSelectedCinema("");
                // showPb();
                showProgressdialog(true);
                presenter.getMovie(sessionManager.getCountryCode());

            } else {
                sessionManager.setSelectedCinema(txt_sel_cinema);
                // showPb();
                showProgressdialog(true);
                presenter.getMovieCinema(sel_id,sessionManager.getCountryCode());
            }
            //}
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }


    private void getlaunguagelist(int differenciate) {
        launguage = new ArrayList<>();
        hashSet_remove_duplicated_items = new HashSet<String>();
        if (mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                if (differenciate == 1) {
                    launguage.add(mList.get(i).languageid);
                } else {
                    launguage.add(mList.get(i).language);
                }
            }

            hashSet_remove_duplicated_items.addAll(launguage);
            launguage.clear();
            launguage.addAll(hashSet_remove_duplicated_items);
            launguage.add(getString(R.string.alllaunguages));
        }

    }

    private void laungugesPopupWindow(int animationSource, int differenciate_filter_dialogue) {
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_all_launguage, null);
        // Build the dialog
        customDialog = new Dialog(getActivity(), R.style.MyDialogTheme);
        customDialog.setContentView(customView);
        // dialog with bottom and with out padding
        Window window = customDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        //animation
        customDialog.getWindow().getAttributes().windowAnimations = animationSource; //style id
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(false);
        customDialog.show();
        if (differenciate_filter_dialogue == 1) {
            launginit(customDialog);
        } else {
            cinemainit(customDialog);
        }
    }

    private void launginit(Dialog customDialog) {
        progressDialog.dismiss();
        c1_cacel = customDialog.findViewById(R.id.c1cacel);
        c1_cacel.setOnClickListener(this);
        txt_done = customDialog.findViewById(R.id.txt_done);
        laung_recyclerview = customDialog.findViewById(R.id.laung_recyclerview);
        mlaunglayoutmanager = new LinearLayoutManager(getActivity());
        laung_recyclerview.setLayoutManager(mlaunglayoutmanager);
        if (launguage.size() > 0) {
            filter_adapter = new FilterAdapter(launguage, getActivity(), txt_sel_launguage);
            laung_recyclerview.setAdapter(filter_adapter);
        }

    }

    private void cinemainit(Dialog customDialog) {
        progressDialog.dismiss();
        c1_cacel = customDialog.findViewById(R.id.c1cacel);
        c1_cacel.setOnClickListener(this);
        txt_done = customDialog.findViewById(R.id.txt_done);
        laung_recyclerview = customDialog.findViewById(R.id.laung_recyclerview);
        mlaunglayoutmanager = new LinearLayoutManager(getActivity());
        laung_recyclerview.setLayoutManager(mlaunglayoutmanager);
        if (cList.size() > 0) {
            cinema_filter_adapter = new CinemaFilterAdapter(cList, getActivity(), txt_sel_cinema);
            laung_recyclerview.setAdapter(cinema_filter_adapter);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent i;
        switch (id) {
            case R.id.tv_now_showing:
                txt_all_laung.setText(getString(R.string.alllaunguages));
                txt_all_cinema.setText(getString(R.string.cinemahall));
                filter_layout.setVisibility(View.VISIBLE);
//                lin_cinema_all.setVisibility(View.VISIBLE);
//                v1.setVisibility(View.VISIBLE);
                txt_sel_launguage = "";
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                if (is_now_showing) {
                    tv_top_movies.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                    tv_coming_soon.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                    tv_now_showing.setBackground(getResources().getDrawable(R.drawable.button_background));
                    tv_now_showing.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_top_movies.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_coming_soon.setTextColor(getResources().getColor(R.color.colorWhite));
                    is_top_movies = true;
                    is_coming_soon = true;
                    is_now_showing = false;
                }
                // showPb();
                showProgressdialog(true);
                presenter.getMovie(sessionManager.getCountryCode());
                break;

            case R.id.ll_home:
                Intent intent = new Intent(requireActivity(), DashBoardActivity.class);
                startActivity(intent);
                requireActivity().finish();
                break;

            case R.id.ll_movies:
                Intent intentssss = new Intent(requireActivity(), MainActivity.class);
                intentssss.putExtra("categoryId", "15");
                intentssss.putStringArrayListExtra("headerList", headerlist);
                intentssss.putExtra("position", 0);
                intentssss.putExtra("ImageType", "others");
                startActivity(intentssss);
                requireActivity().finish();
                break;

            case R.id.ll_events:
                Intent intentss = new Intent(requireActivity(), EventHomeActivity.class);
                intentss.putExtra("categoryId", "15");
                intentss.putStringArrayListExtra("headerList", headerlist);
                intentss.putExtra("position", 1);
                intentss.putExtra("ImageType", "others");
                startActivity(intentss);
                requireActivity().finish();
                break;

            case R.id.ll_more:
                i = new Intent(requireActivity(), NavigationDrawerActivity.class);
                i.putStringArrayListExtra("headerList", headerlist);
                startActivity(i);
                break;



            case R.id.tv_top_movies:
                txt_all_laung.setText(getString(R.string.alllaunguages));
                txt_all_cinema.setText(getString(R.string.cinemahall));
                filter_layout.setVisibility(View.GONE);
                txt_sel_launguage = "";
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                if (is_top_movies) {
                    tv_top_movies.setBackground(getResources().getDrawable(R.drawable.button_background));
                    tv_coming_soon.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                    tv_now_showing.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                    tv_top_movies.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_coming_soon.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_now_showing.setTextColor(getResources().getColor(R.color.colorWhite));
                    is_top_movies = false;
                    is_coming_soon = true;
                    is_now_showing = true;
                    showPb();
                    presenter.getTopMovie(sessionManager.getCountryCode());
                }
                break;
            case R.id.tv_coming_soon:
                txt_all_laung.setText(getString(R.string.alllaunguages));
                txt_all_cinema.setText(getString(R.string.cinemahall));
                filter_layout.setVisibility(View.GONE);
//                lin_cinema_all.setVisibility(View.GONE);
//                v1.setVisibility(View.GONE);
                txt_sel_launguage = "";
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                if (is_coming_soon) {
                    tv_top_movies.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                    tv_coming_soon.setBackground(getResources().getDrawable(R.drawable.button_background));
                    tv_now_showing.setBackground(getResources().getDrawable(R.drawable.back_color_without_corner));
                    tv_coming_soon.setTextColor(getResources().getColor(R.color.colorBlack));
                    tv_now_showing.setTextColor(getResources().getColor(R.color.colorWhite));
                    tv_top_movies.setTextColor(getResources().getColor(R.color.colorWhite));
                    is_top_movies = true;
                    is_coming_soon = false;
                    is_now_showing = true;
                    showPb();
                    presenter.getCommingSoon(sessionManager.getCountryCode());
                }
                break;
            case R.id.lin_all_laung:
                sessionManager.setSelectedCinema("");
                lin_all_laung.setEnabled(false);
                laungugesPopupWindow(R.style.DialogAnimation, 1);
                break;
            case R.id.lin_cinema_all:
                sessionManager.setSelectedCinema("");
                lin_cinema_all.setEnabled(false);
                //  showPb();
                showProgressdialog(true);
                presenter.getCinemas(sessionManager.getCountryName());
                break;
            case R.id.c1cacel:
                lin_all_laung.setEnabled(true);
                lin_cinema_all.setEnabled(true);
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                customDialog.dismiss();
                break;
            case R.id.ic_home:
                txt_sel_launguage = "";
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                i = new Intent(getActivity(), DashBoardActivity.class);
                startActivity(i);
                break;
            case R.id.menu_icon:
                txt_sel_launguage = "";
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                i = new Intent(getActivity(), NavigationDrawerActivity.class);
                startActivity(i);
                break;
            case R.id.iv_search:
                txt_sel_launguage = "";
                txt_sel_cinema = "";
                sessionManager.setSelectedCinema("");
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie_list", (Serializable) mList);
                //bundle.putInt("position", position);
                i = new Intent(getActivity(), SearchListActivity.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
        }
    }

    @Override
    public void init() {
        sessionManager = new SessionManager(getActivity());
        GlobalBus.getBus().register(this);
        // showPb();
        showProgressdialog(true);
        presenter.getMovie(sessionManager.getCountryCode());
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
            showRetryDialog(call, callback, getResources().getString(R.string.internet));
        } else if (throwable instanceof UnknownHostException) {
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
        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            QTUtils.dismissProgressDialog();
            if (presenter != null) {
                presenter.detach();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setMovie(List<MovieModel> response) {
        progressDialog.dismiss();
        c1.setVisibility(View.GONE);
        movielist_recyclerview.setVisibility(View.VISIBLE);
        launguage = new ArrayList<>();
        hashSet_remove_duplicated_items = new HashSet<String>();
        mList = response;
        if (mList.size() > 0) {
            c1.setVisibility(View.GONE);
            movielist_recyclerview.setVisibility(View.VISIBLE);
            movieListAdapter = new MovieListAdapter(getActivity(), mList, 200);
            movielist_recyclerview.setAdapter(movieListAdapter);
            getlaunguagelist(1);
            main_layout.setVisibility(View.VISIBLE);
        } else {
            c1.setVisibility(View.VISIBLE);
            movielist_recyclerview.setVisibility(View.GONE);
        }

    }

    @Override
    public void setTopMovie(Items movie) {
        launguage = new ArrayList<>();
        hashSet_remove_duplicated_items = new HashSet<String>();
        c1.setVisibility(View.GONE);
        movielist_recyclerview.setVisibility(View.VISIBLE);
        mList = movie.data;
        if (mList.size() > 0) {
            c1.setVisibility(View.GONE);
            movielist_recyclerview.setVisibility(View.VISIBLE);
            movieListAdapter = new MovieListAdapter(getActivity(), mList, 200);
            movielist_recyclerview.setAdapter(movieListAdapter);
            getlaunguagelist(2);
        } else {
            c1.setVisibility(View.VISIBLE);
            movielist_recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCommingSoon(List<MovieModel> response) {
        launguage = new ArrayList<>();
        hashSet_remove_duplicated_items = new HashSet<String>();
        c1.setVisibility(View.GONE);
        movielist_recyclerview.setVisibility(View.VISIBLE);
        mList = response;
        if (mList.size() > 0) {
            c1.setVisibility(View.GONE);
            movielist_recyclerview.setVisibility(View.VISIBLE);
            movieListAdapter = new MovieListAdapter(getActivity(), mList, 200, "commingsoon");
            movielist_recyclerview.setAdapter(movieListAdapter);
            getlaunguagelist(2);
        } else {
            c1.setVisibility(View.VISIBLE);
            movielist_recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void setMovieByLaungauge(List<MovieModel> movibylaung) {
        progressDialog.dismiss();
        mList = movibylaung;
        c1.setVisibility(View.GONE);
        movielist_recyclerview.setVisibility(View.VISIBLE);
        if (mList.size() > 0) {
            if (!TextUtils.isEmpty(txt_sel_launguage)) {
                txt_all_laung.setText(txt_sel_launguage);
            }
            movieListAdapter = new MovieListAdapter(getActivity(), mList, 200);
            movielist_recyclerview.setAdapter(movieListAdapter);
        } else {
            showToast(getString(R.string.noresponse));
        }
    }

    @Override
    public void setCinemas(Items cinemas) {
        progressDialog.dismiss();
        MovieModel movieModel = new MovieModel();
        movieModel.setName(getString(R.string.cinemahall));
        movieModel.setId("-2");
        cList = cinemas.data;
        if (cList.size() > 0) {
            cList.add(movieModel);
            laungugesPopupWindow(R.style.DialogAnimation, 2);
        }
    }

    @Override
    public void setMovieCinema(List<MovieModel> moviebycinema) {
        movielist_recyclerview.setVisibility(View.VISIBLE);
        //dismissPb();
        progressDialog.dismiss();
        if(customDialog.isShowing()){
            customDialog.dismiss();
        }
        List<MovieModel> filter_list = new ArrayList<>();
        mList = moviebycinema;
        if (txt_sel_launguage.equals(getString(R.string.alllaunguages))) {
            txt_sel_launguage = "";
        }
        if (mList.size() > 0) {
            sessionManager.setSelectedCinema(txt_sel_cinema);
            if (!TextUtils.isEmpty(txt_sel_launguage)) {
                txt_all_laung.setText(txt_sel_launguage);
                txt_all_cinema.setText(txt_sel_cinema);
                for (int i = 0; i < mList.size(); i++) {
                    if (TextUtils.isEmpty(mList.get(i).language)) {
                        if (mList.get(i).languageid.equals(txt_sel_launguage)) {
                            filter_list.add(mList.get(i));
                        }
                    } else {
                        if (mList.get(i).language.equals(txt_sel_launguage)) {
                            filter_list.add(mList.get(i));

                        }
                    }
                }
                if (filter_list.size() > 0) {
                    c1.setVisibility(View.GONE);
                    movielist_recyclerview.setVisibility(View.VISIBLE);
                    movieListAdapter = new MovieListAdapter(getActivity(), filter_list, 200);
                    movielist_recyclerview.setAdapter(movieListAdapter);
                } else {
                    c1.setVisibility(View.VISIBLE);
                    movielist_recyclerview.setVisibility(View.GONE);
                }

            } else {
                txt_all_laung.setText(getString(R.string.alllaunguages));
                movieListAdapter = new MovieListAdapter(getActivity(), mList, 200);
                movielist_recyclerview.setAdapter(movieListAdapter);
            }


        } else {
            movielist_recyclerview.setVisibility(View.GONE);
            // showToast(getString(R.string.noresponse));
            c1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setReelCinemaList(ReelCinemaMovieListModel response) {

    }

    private void showProgressdialog(Boolean isShow){
        progressDialog = new Dialog(context);
        progressDialog.setContentView(R.layout.dialog_progressbar);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCanceledOnTouchOutside(true);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (isShow) {
            if(!progressDialog.isShowing()) {
                progressDialog.show();
            }

        }

    }

}
