package dev.villahermosa.com.bootcamplocator;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class mapfrag extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    private MarkerOptions userMarker;
    private listfrag mListFragment;

    public mapfrag() {
        // Required empty public constructor
    }

    public static mapfrag newInstance() {
        mapfrag fragment = new mapfrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapfrag, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mListFragment = (listfrag) getActivity().getSupportFragmentManager().findFragmentById(R.id.container_locations_list);

        if (mListFragment == null) {
            mListFragment = listfrag.newInstance();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_locations_list, mListFragment)
                    .commit();
        }

        final EditText zipText = (EditText)view.findViewById(R.id.zip_text);
        zipText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {

                    String text = zipText.getText().toString();
                    int zip = Integer.parseInt(text);

                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(zipText.getWindowToken(), 0);

                    showList();
                    updateMapForZip(zip);
                    return true;
                }

                return false;
            }
        });

        hideList();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    public void setUserMarker(LatLng latLng) {
        if (userMarker == null) {
            userMarker = new MarkerOptions().position(latLng).title("Current Location");
            mMap.addMarker(userMarker);
        }

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
//            updateMapForZip(11111);
//            final int zip = Integer.parseInt(addresses.get(0).getPostalCode());
//            updateMapForZip(zip);
        } catch (IOException exception){

        }

        updateMapForZip(11111);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

    }

    private void updateMapForZip(int zipcode) {

        ArrayList<bootcmp> locations = Data.getInstance().getBootcampLocationsWithin10MilesOfZip(zipcode);

        for (int x = 0; x < locations.size(); x++) {
            bootcmp loc = locations.get(x);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(loc.getLatitude(),loc.getLongitude()));
            marker.title(loc.getLocationTitle());
            marker.snippet(loc.getLocationAddress());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mMap.addMarker(marker);
        }
    }

    private void hideList() {
        getActivity().getSupportFragmentManager().beginTransaction().hide(mListFragment).commit();
    }

    private void showList() {
        getActivity().getSupportFragmentManager().beginTransaction().show(mListFragment).commit();
    }

}
