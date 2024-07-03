package com.production.qtickets.events.EventFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.production.qtickets.R;
import com.production.qtickets.utils.TextviewRegular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class EventSummaryFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.tv_eventDescription)
    TextviewRegular tv_eventDescription;

    private String description;

    @SuppressLint("ValidFragment")
    public EventSummaryFragment(String description) {
        this.description = description;
    }
    public EventSummaryFragment(){

    }

    //parsing the html format text to show the drescription abount the event
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_summary_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        description = description.replace("breakhtml", "<br>");
        description = description.replace("symband", "&");
        description = description.replace("parastart", "<p>");
        description = description.replace("paraend", "</p>");
        description = description.replace("ulstart", "<ul>");
        description = description.replace("ulend", "</ul>");
        description = description.replace("listart", "<li>");
        description = description.replace("liend", "</li>");
        description = description.replace("boldstart", "<b>");
        description = description.replace("boldend", "</b>");
        description = description.replace("dblQuote", "''");
        tv_eventDescription.setText(Html.fromHtml(description));

//        encodedDescripltion.Replace("breakhtml", "<br>").Replace("symband", "&").Replace("parastart", "<p>").Replace("paraend", "</p>")
//                .Replace("ulstart", "<ul>").Replace("ulend", "</ul>").Replace("listart", "<li>").Replace("liend", "</li>")
//                .Replace("boldstart", "<b>").Replace("boldend", "</b>").Replace("dblQuote", "''");

        return view;
    }
}
