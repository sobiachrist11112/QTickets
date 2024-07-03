package com.production.qtickets.moviedetailes;//package com.production.qtickets.moviedetailes;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.production.qtickets.R;
//import com.production.qtickets.Utils.Events;
//import com.production.qtickets.Utils.GlobalBus;
//import com.production.qtickets.Utils.QTUtils;
//import com.production.qtickets.Utils.SessionManager;
//import com.production.qtickets.Utils.TextviewBold;
//
//import java.net.ConnectException;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.Unbinder;
//
//
//public class ShowTimingsFragment extends Fragment implements MovieDetaileContracter.View, AdapterView.OnItemClickListener {
//    //list
//    ArrayList<String> dates = new ArrayList<>();
//    List<DateModel> dList;
//    List<ItemModel> mList;
//
//    //adapter
//    DatesAdapter datesAdapter;
//    DateByMallAdapter datebymallAdapter;
//
//    @BindView(R.id.tv_date)
//    TextviewBold tv;
//    @BindView(R.id.recycler_dates)
//    RecyclerView recyclerDates;
//    @BindView(R.id.recycler_malls)
//    RecyclerView recyclerMalls;
//    @BindView(R.id.iv_decrease_count)
//    ImageView ivDecreaseCount;
//    @BindView(R.id.tv_ticket_count)
//    TextView tvTicketCount;
//    @BindView(R.id.iv_increase_count)
//    ImageView ivIncreaseCount;
//    Unbinder unbinder;
//
//    //presenter
//    MovieDetailePresenter presenter;
//
//    //layout manager
//    RecyclerView.LayoutManager mLayoutManager, mallLayoutmanager;
//
//    //string
//    private String id, str_selected_date,movie_title;
//
//    //int
//    int ticketCount = 0;
//
//   //sessionmanager
//    SessionManager sessionManager;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.show_timings_fragment, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        presenter = new MovieDetailePresenter();
//        presenter.attachView(this);
//        init();
//        return view;
//    }
//
//
//    @Override
//    public void setshowDates(ShowDateModel showDateModels) {
//        dList = showDateModels.dates;
//        if (dList.size() > 0) {
//            sendmessage(dList);
//        }
//        str_selected_date = dList.get(0).date;
//        showPb();
//        presenter.getMallbydates("0", "0", "0", str_selected_date, id);
//    }
//
//    @Override
//    public void setmallbydates(MallBydateModel mallBydateModel) {
//        mList = mallBydateModel.items;
//        datebymallAdapter = new DateByMallAdapter(getActivity(), mList, movie_title);
//        recyclerMalls.setAdapter(datebymallAdapter);
//
//    }
//
//    @Override
//    public void setusermoviereviewbymovieid(List<UserReviewModel> userReviewModel) {
//
//    }
//
//    @Override
//    public void setuserreviews(List<UserReviewModel> userReviewModel) {
//
//    }
//
//    public void sendmessage(List<DateModel> list) {
//        Events.ActivityAdapterMessageList activityFragmentMessageEvent =
//                new Events.ActivityAdapterMessageList(list);
//        GlobalBus.getBus().post(activityFragmentMessageEvent);
//    }
//
//    @Override
//    public void init() {
//        sessionManager = new SessionManager(getActivity());
//        showPb();
//        id = sessionManager.getMovieId();
//        movie_title = sessionManager.getMovieId();
//        presenter.getshowDates(id);
//        SimpleDateFormat format1 = new SimpleDateFormat("MMM yyyy");
//        Calendar date1 = Calendar.getInstance();
//        tv.setText(format1.format(date1.getTime()));
//        /*getting seven dates from current date*/
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar date = Calendar.getInstance();
//        dates = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            dates.add(format.format(date.getTime()));
//            date.add(Calendar.DATE, 1);
//            System.out.println("date :" + format.format(date.getTime()));
//        }
//        //show dates
//        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerDates.setLayoutManager(mLayoutManager);
//        datesAdapter = new DatesAdapter(dates, getActivity());
//        recyclerDates.setAdapter(datesAdapter);
//        datesAdapter.setOnItemClickListener(this);
//        //malls
//        mallLayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerMalls.setLayoutManager(mallLayoutmanager);
//
//    }
//
//    @Override
//    public void dismissPb() {
//        QTUtils.progressDialog.dismiss();
//    }
//
//    @Override
//    public void showPb() {
//        QTUtils.showProgressDialog(getActivity(), true);
//    }
//
//    @Override
//    public void showToast(String message) {
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onFailure(Object call, Object throwable, Object callback, Object additionalData) {
//        if (throwable instanceof SocketTimeoutException) {
//            //  showToast("Socket Time out. Please try again.");
//            showRetryDialog(call, callback, getResources().getString(R.string.internet));
//        } else if (throwable instanceof UnknownHostException) {
//            // showToast("No Internet");
//            showRetryDialog(call, callback, getResources().getString(R.string.internet));
//        } else if (throwable instanceof ConnectException) {
//            showRetryDialog(call, callback, getResources().getString(R.string.internet));
//        } else if (throwable.equals("null")) {
//            showToast(getResources().getString(R.string.noresponse));
//        }
//    }
//
//    @Override
//    public void showRetryDialog(Object object1, Object object3, String message) {
//        showPb();
//        QTUtils.showAlertDialogbox(object1, object3, getActivity(), message);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @Override
//    public void onDestroy() {
//        presenter.detach();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        if (dList.size() > 0) {
//            sendmessage(dList);
//        }
//        str_selected_date = dList.get(i).date;
//        showPb();
//        presenter.getMallbydates("0", "0", "0", str_selected_date, id);
//    }
//
//
//    @OnClick({R.id.iv_decrease_count, R.id.iv_increase_count})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_decrease_count:
//                if(ticketCount>0) {
//                    ticketCount--;
//                    Log.v("count ", "== " + ticketCount);
//                    String tdCount = String.valueOf(ticketCount);
//                    tvTicketCount.setText(tdCount);
//                }
//                break;
//            case R.id.iv_increase_count:
//                ticketCount++;
//                Log.v("count ","== "+ticketCount);
//                String tiCount = String.valueOf(ticketCount);
//                tvTicketCount.setText(tiCount);
//                break;
//        }
//    }
//}
