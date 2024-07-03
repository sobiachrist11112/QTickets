package com.production.qtickets.events.EventFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.production.qtickets.R;
import com.production.qtickets.utils.TextviewBold;


public class EventContactFragment extends Fragment implements OnMapReadyCallback {
    private String eventEmail;
    TextviewBold tv_cantactEmail;
    ImageView iv_event_location;
    String eventLongitude = "";
    String eventLatitude = "";
    String eventVenue = "";
    double latitude = 0.0;
    double longitude = 0.0;
    private GoogleMap mGoogleMap;
    LinearLayout mapView_layout;
    TextviewBold tv_getDirections;


    public EventContactFragment(String eventLongitude, String eventLatitude, String eventVenue) {
        this.eventLatitude = eventLatitude;
        this.eventLongitude = eventLongitude;
        this.eventVenue = eventVenue;
    }
    public EventContactFragment() {
    }

    //enabling the map function in contact page
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_contact_fragment, container, false);

        latitude = Double.valueOf(eventLatitude);
        longitude = Double.valueOf(eventLongitude);

        // Gets the MapView from the XML layout and creates it
        tv_getDirections = (TextviewBold) view.findViewById(R.id.tv_getDirections);
        tv_getDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String strUri = "http://maps.google.com/maps?q=loc:" + eventLatitude + "," + eventLongitude + " (" + eventVenue + ")";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(requireActivity(), "download google map", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tv_cantactEmail = (TextviewBold) view.findViewById(R.id.tv_cantactEmail);
        tv_cantactEmail.setText("info@q-tickets.com");

        iv_event_location = (ImageView) view.findViewById(R.id.iv_event_location);
        Log.v("LatLong", "== " + eventLatitude);
        Log.v("LatLong", "== " + eventLongitude);

        mapView_layout = (LinearLayout) view.findViewById(R.id.mapView_layout);
        mapView_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String strUri = "http://maps.google.com/maps?q=loc:" + eventLatitude + "," + eventLongitude + " (" + eventVenue + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(requireActivity(), "download google map", Toast.LENGTH_SHORT).show();
                }

            }
        });

        iv_event_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                String strUri = "http://maps.google.com/maps?q=loc:" + eventLatitude + "," + eventLongitude + " (" + eventVenue + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(requireActivity(), "download google map", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(location)
                .title("Qtickets"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
