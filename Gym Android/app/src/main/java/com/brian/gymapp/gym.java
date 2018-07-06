package com.brian.gymapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link gym#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gym extends Fragment implements OnMapReadyCallback{
    //variables
    GoogleMap mgoogleMap;
    MapView mapView;
    View mview;
    private RequestQueue queue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    //maps and their types
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        //initialize the map
        MapsInitializer.initialize(getContext());
        mgoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        final float map_longitude, map_latitude;
        final String map_title, map_info;

        //get locations from the DB
        //prepare a request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, public_url.gym_Locations, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            //loop through each location
                            //add the maps
                            try {
                                //add marker with the longitudes and latitudes
                                JSONObject jsonObject = response.getJSONObject(i);

                                googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Float.valueOf(jsonObject.getString("latitude")),Float.valueOf(jsonObject.getString("longitude"))))
                                    .title(jsonObject.getString("gym_name"))
                                    .snippet("Open time: "+jsonObject.getString("open_time")+".  Closing Time: "+jsonObject.getString("close_time"))
                                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.gym))
                                );

                                /*
                                googleMap.addMarker(new MarkerOptions().
                                    position(new LatLng(-1.3095416,36.8101521)).
                                    title("Home gym").snippet("Get this right")
                                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.gym))
                                );*/

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error","Error"+error);
            }
        });


        //add it to the RequestQueue
       // queue.add(jsonArrayRequest);

        //adding the string request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);


        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(-1.3095416,36.8101521)).zoom(10).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }


    public gym() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gym.
     */
    // TODO: Rename and change types and number of parameters
    public static gym newInstance(String param1, String param2) {
        gym fragment = new gym();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    //return a view on created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_gym, container, false);
        return mview;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView)mview.findViewById(R.id.map);
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event


}
