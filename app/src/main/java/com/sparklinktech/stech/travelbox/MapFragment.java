package com.sparklinktech.stech.travelbox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends FragmentActivity implements OnMapReadyCallback
{



    private GoogleMap mMap;
    String lat,lon,name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null)
        {
            lat = b.getString("lat");
            lon = b.getString("lon");
            name = b.getString("name");
            Log.e("LAT,LON  <--> ", "" + lat + ", " + lon);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng hotel = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
       // LatLng hotel = new LatLng(15.5494, 73.7535);
        mMap.addMarker(new MarkerOptions().position(hotel).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hotel,15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}