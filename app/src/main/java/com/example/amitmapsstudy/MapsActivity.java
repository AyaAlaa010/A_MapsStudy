package com.example.amitmapsstudy;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        geocoder=new Geocoder(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng amit = new LatLng(29.9844162, 31.2311051);
        MarkerOptions markerOptions= new MarkerOptions().position(amit).title("Amit");
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amit,15));
        mMap.setOnMapClickListener(this);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(amit,15.5f));
    }
LatLng latLng=null;
    @Override
    public void onMapClick(LatLng latLng) {

        this.latLng=latLng;
        try {
      List<Address> addresses=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
      if(addresses.isEmpty()) return;
      Address address=addresses.get(0);
     String streetName= address.getAddressLine(0);
            Log.i(TAG, "onMapClick: "+streetName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onMapClick: "+latLng.longitude+"'"+latLng.latitude);
        mMap.clear();
        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("your Location");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.5f));
    }

    public void confirmLocation(View view) {
        if(latLng==null){

            Toast.makeText(this,"please Select Location",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent= new Intent();
intent.putExtra("lating",latLng);
setResult(RESULT_OK,intent);
finish();
    }

}