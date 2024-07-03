package com.production.qtickets.moviedetailes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.production.qtickets.R;
import com.production.qtickets.utils.TextviewRegular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class MovieSummaryFragment extends Fragment {
    //in this class we are going to display the description of the movie
    //string
    String description,starcast;
    //id
    @BindView(R.id.txt_description)
    TextView txtDescription;
    Unbinder unbinder;
    @BindView(R.id.txt_txt_starcast)
    TextviewRegular txtTxtStarcast;
    @SuppressLint("ValidFragment")
    public MovieSummaryFragment() {
        // doesn't do anything special
    }
    @SuppressLint("ValidFragment")
    public MovieSummaryFragment( String description,String starcast)
    {
        this.description = description;
        this.starcast = starcast;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_summary_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!TextUtils.isEmpty(description)) {
            txtDescription.setText(description);
        }
        txtTxtStarcast.setText(starcast);
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();

    }
}
