package com.production.qtickets.adapters;

import android.content.Context;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.production.qtickets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh on 5/22/2018.
 */
public class ViewPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    Context context;
    TextView text_title;
    ImageView img;
    RelativeLayout r1;
    int tabcount;


    public ViewPageAdapter(FragmentManager manager, Context context, int tab) {
        super(manager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        this.tabcount = tab;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public View getTabView(String title, String categoryImage) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View tabView = layoutInflater.inflate(R.layout.custom_dynamic_tablayout, null, false);
        text_title = tabView.findViewById(R.id.text_title);
        img = tabView.findViewById(R.id.img);
        r1 = tabView.findViewById(R.id.r1);
        img.setImageResource(Integer.parseInt(categoryImage));
        text_title.setText(title);
        return tabView;
    }

    //change color of selected tablayout
    public void SetOnSelectView(TabLayout tabLayout, int position, String image,String heading) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if(tab!=null) {
            View selected = tab.getCustomView();
            if(selected!=null) {
                text_title = selected.findViewById(R.id.text_title);
                text_title.setText(heading);
                r1 = selected.findViewById(R.id.r1);
                img = selected.findViewById(R.id.img);
                img.setImageResource(Integer.parseInt(image));
                r1.setBackground(ContextCompat.getDrawable(context, R.drawable.movie_cat_white_round_back));
            }
        }
        }

    public void SetUnSelectView(TabLayout tabLayout, int position, String image,String heading) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        if(tab!=null) {
            View selected = tab.getCustomView();
            if(selected != null) {
                text_title = selected.findViewById(R.id.text_title);
                text_title.setText(heading);
                r1 = selected.findViewById(R.id.r1);
                img = selected.findViewById(R.id.img);
                img.setImageResource(Integer.parseInt(image));
                r1.setBackground(ContextCompat.getDrawable(context, R.drawable.transparent_background));
            }
        }
        }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

