package com.production.qtickets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.production.qtickets.R;
import com.production.qtickets.adapters.ViewPageAdapter;
import com.production.qtickets.eventQT5.EventHomeActivity;
import com.production.qtickets.events.EventsListFragment;
import com.production.qtickets.liesure.LeisureListFragment;
import com.production.qtickets.model.CustomModel;
import com.production.qtickets.movies.MoviesListFragment;
import com.production.qtickets.sports.SportsListFragment;
import com.production.qtickets.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //tablayout
    private TabLayout tabLayout;

    //Viewpager
    private ViewPager viewPager;

    //Adapter
    private ViewPageAdapter adapter;

    //int
    private int position = 0, pos;

    ArrayList<CustomModel> str = new ArrayList<>();

    //string
    private String image, header, categoryId, imageType;

    //textview
    private TextView txt_title;

    //sessionmnager
    private SessionManager sessionManager;

    private String currentTab = "movies";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  QTUtils.setStatusBarGradiant(MainActivity.this);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            position = b.getInt("position", 0);
            if (b.getParcelableArrayList("headerList") != null) {
                str = b.getParcelableArrayList("headerList");

                Log.d("25sep:", "headerList :" + String.valueOf(str));

            }
            if (b.getString("categoryId") != null) {
                categoryId = b.getString("categoryId");

                Log.d("26sep", "getString :" + categoryId);

            }
           /* if(b.getString("ImageType") != null){
                imageType = b.getString("ImageType");
            }*/
        }

        // here we are getting the position in the form of number to navigate to the specific position in the ViewPager to
        //make navigation to the specific category, like, movies, events,sports and luisers.
        init();

    }


    private void init() {
        sessionManager = new SessionManager(MainActivity.this);
        sessionManager.setSelectedCinema("");
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        txt_title = findViewById(R.id.txt_title);
        int tabWidth = tabLayout.getWidth() / 4;

        if (str.size() != 0) {
            for (int i = 0; i < str.size(); i++) {
                tabLayout.setMinimumWidth(tabWidth);

                Log.d("27Oct", str.get(i).getHeading());

                tabLayout.addTab(tabLayout.newTab().setText(str.get(i).getHeading()), i);
            }
            txt_title.setText(str.get(position).getHeading());
        }

        setupViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                txt_title.setText(str.get(tab.getPosition()).getHeading());
                for (int i = 0; i < str.size(); i++) {

                    if (tab.getText() != null) { //25sep
                        if (tab.getText().toString().equals("Movies")) {
                            image = String.valueOf(R.drawable.ic_movies_blue);
                        } else if (tab.getText().toString().trim().equals("Sports")) {
                            image = String.valueOf(R.drawable.ic_sports_blue);
                        } else if (tab.getText().toString().equals("Leisure")) {
                            image = String.valueOf(R.drawable.ic_leisure_blue);
                        } else if (tab.getText().toString().equals("Business")) {
                            image = String.valueOf(R.drawable.ic_sports_blue);
                        } else if (tab.getText().toString().equals("Webinar")) {
                            image = String.valueOf(R.drawable.ic_events_blue);
                        } else {
                            image = String.valueOf(R.drawable.ic_events_blue);
                        }

                    }
                }

                if (tab.getText().toString().equals("Movies")) {
                    adapter.SetOnSelectView(tabLayout, tab.getPosition(), image, tab.getText().toString());
                    viewPager.setCurrentItem(tab.getPosition());
                    Log.d("26sep", "IFFFFFFF");

                } else {
//                            intent.putExtra("category_id", selected_category_id);
//                            intent.putExtra("category_name", selected_category_name);
//                            intent.putExtra("selected_event", select_event_date);
//                            intent.putExtra("selected_event_name", select_event_name);
//                            intent.putExtra("minimum_price", min_price);
//                            intent.putExtra("maximum_price", max_price);


                    Log.d("26sep", "ELSEEEEE");

                    Intent intentss = new Intent(MainActivity.this, EventHomeActivity.class);
                    intentss.putExtra("category_name", str.get(tab.getPosition()).getHeading());

                    Log.d("26sep", "Sending" + str.get(tab.getPosition()).getHeading());

                    intentss.putParcelableArrayListExtra("headerList", str);
                    intentss.putExtra("position", 1);
                    intentss.putExtra("ImageType", "others");
                    startActivity(intentss);
                    finish();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                for (int i = 0; i < str.size(); i++) {
                    if (tab.getText() != null) {
                        if (tab.getText().toString().equals("Movies")) {
                            image = String.valueOf(R.drawable.ic_movies);
                        } else if (tab.getText().toString().trim().equals("Sports")) {
                            image = String.valueOf(R.drawable.ic_sports);
                        } else if (tab.getText().toString().equals("Leisure")) {
                            image = String.valueOf(R.drawable.ic_leisure);
                        } else if (tab.getText().toString().equals("Business")) {
                            image = String.valueOf(R.drawable.ic_sports);
                        } else if (tab.getText().toString().equals("Webinar")) {
                            image = String.valueOf(R.drawable.ic_events);
                        } else {
                            image = String.valueOf(R.drawable.ic_events);
                        }
                        adapter.SetUnSelectView(tabLayout, tab.getPosition(), image, tab.getText().toString());
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPageAdapter(getSupportFragmentManager(), getApplicationContext(), tabLayout.getTabCount());
        for (int k = 0; k < str.size(); k++) {
            TabLayout.Tab tab = tabLayout.getTabAt(k);
            switch (str.get(k).getHeading()) {
                case "Movies":
                    MoviesListFragment moviesListFragment = new MoviesListFragment();
                    Bundle sbss = new Bundle();
                    sbss.putParcelableArrayList("headerlist", str);
                    moviesListFragment.setArguments(sbss);
                    adapter.addFragment(moviesListFragment, str.get(k).getHeading());
                    if (tab != null) {
                        tab.setCustomView(adapter.getTabView(str.get(k).getHeading(), String.valueOf(R.drawable.ic_movies)));
                    }
                    break;
                case "Sports":

                    viewPager.setCurrentItem(k);
                    EventsListFragment ef = new EventsListFragment();
                    Bundle b = new Bundle();
                    b.putString("EventHeader", str.get(k).getHeading());
                    if (categoryId != null) {
                        b.putString("categoryId", categoryId);

                        Log.d("26sep", "Sports:" + categoryId);
                    }
                    ef.setArguments(b);
                    adapter.addFragment(ef, str.get(k).getHeading());
                    if (tab != null) {
                        tab.setCustomView(adapter.getTabView(str.get(k).getHeading(), String.valueOf(R.drawable.ic_sports)));
                    }

                    break;

                case "Business":
                    SportsListFragment sf = new SportsListFragment();
                    Bundle sb = new Bundle();
                    sb.putString("EventHeader", str.get(k).getHeading());
                    if (categoryId != null) {
                        sb.putString("Business", categoryId);
                    }
                    sf.setArguments(sb);
                    adapter.addFragment(sf, str.get(k).getHeading());
                    if (tab != null) {
                        tab.setCustomView(adapter.getTabView(str.get(k).getHeading(), String.valueOf(R.drawable.ic_sports)));
                    }
                    break;
                case "Leisure":
                    //  viewPager.setCurrentItem(k);
                    adapter.addFragment(new LeisureListFragment(), str.get(k).getHeading());
                    if (tab != null) {
                        Log.d("26sep", "Leisure:" + categoryId);

                        tab.setCustomView(adapter.getTabView(str.get(k).getHeading(), String.valueOf(R.drawable.ic_leisure)));
                    }
                    break;
                case "Webinar":
                    EventsListFragment webinar = new EventsListFragment();
                    Bundle web = new Bundle();
                    web.putString("EventHeader", str.get(k).getHeading());
                    if (categoryId != null) {
                        Log.d("26sep", "Webinar:" + categoryId);

                        web.putString("categoryId", "7");
                    }
                    webinar.setArguments(web);
                    adapter.addFragment(webinar, str.get(k).getHeading());
                    if (tab != null) {
                        tab.setCustomView(adapter.getTabView(str.get(k).getHeading(), String.valueOf(R.drawable.ic_events)));
                    }
                    break;

                default:
                    viewPager.setCurrentItem(k);
                    EventsListFragment efee = new EventsListFragment();
                    Bundle bumm = new Bundle();
                    bumm.putString("EventHeader", str.get(k).getHeading());
                    if (categoryId != null) {
                        bumm.putString("categoryId", categoryId);
                    }
                    efee.setArguments(bumm);
                    adapter.addFragment(efee, str.get(k).getHeading());
                    if (tab != null) {
                        tab.setCustomView(adapter.getTabView(str.get(k).getHeading(), String.valueOf(R.drawable.ic_events)));
                    }
                    break;
            }
        }
        viewPager.setAdapter(adapter);
        /*viewPager.setCurrentItem(position);*/
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                if (tab.getText() != null) {
                    if (tab.getText().toString().equals("Movies") && position == i) {
                        pos = i;
                        header = tab.getText().toString();
                        image = String.valueOf(R.drawable.ic_movies_blue);
                        tab.select();
                    } else if (tab.getText().toString().trim().equals("Sports") && position == i) {
                        pos = i;
                        header = tab.getText().toString();
                        image = String.valueOf(R.drawable.ic_sports_blue);
                        tab.select();
                    } else if (tab.getText().toString().equals("Leisure") && position == i) {
                        pos = i;
                        header = tab.getText().toString();
                        image = String.valueOf(R.drawable.ic_leisure_blue);
                        tab.select();
                    } else if (tab.getText().toString().equals("Business") && position == i) {
                        pos = i;
                        header = tab.getText().toString();
                        image = String.valueOf(R.drawable.ic_sports_blue);
                        tab.select();
                    } else if (tab.getText().toString().equals("Webinar") && position == i) {
                        pos = i;
                        header = tab.getText().toString();
                        image = String.valueOf(R.drawable.ic_events_blue);
                        tab.select();
                    } else if (position == i) {
                        pos = i;
                        header = tab.getText().toString();
                        image = String.valueOf(R.drawable.ic_events_blue);
                        tab.select();
                    }
                }
            }
        }
        viewPager.setCurrentItem(pos);
        adapter.SetOnSelectView(tabLayout, position, image, header);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionManager.setSelectedCinema("");
    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

