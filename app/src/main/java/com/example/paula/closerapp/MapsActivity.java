package com.example.paula.closerapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap = null;
    private TextView LocationText;
    private LatLng placeLocation;
    private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyD7zKMN1Id_RNcfgy0DAnfOl-yf3UIiUHc").setConnectTimeout(1, TimeUnit.SECONDS).setReadTimeout(1, TimeUnit.SECONDS).setWriteTimeout(1, TimeUnit.SECONDS);
    GoogleApiClient mGoogleApiClient;
    public static final String TAG = "MyPosition";

    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        placeLocation = getIntent().getExtras().getParcelable("PLACE_LOCATION");
        currentLocation = getIntent().getExtras().getParcelable("MY_LOCATION");
        LocationText = findViewById(R.id.location_tv);

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
    public void onLocationChanged(Location location) {

        if(mMap!=null) {

            mMap.clear();

            MarkerOptions mp = new MarkerOptions();

            mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

            mp.title("My position");

            Log.d(TAG, "current location info");

            mMap.addMarker(mp);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 16));
            currentLocation = location;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                //buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            } else {
                //Request Location Permission
                //checkLocationPermission();
            }
        }
        else {
            //buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);

        }

        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        //Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        LatLng current = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(current).title("Marker Label").snippet("Marker Description"));
        googleMap.addMarker(new MarkerOptions().position(placeLocation).title("Marker Label").snippet("Marker Description"));

        DateTime now = new DateTime();
        DirectionsResult result;
        try {
            result = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin(new com.google.maps.model.LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                    .destination(new com.google.maps.model.LatLng(placeLocation.latitude, placeLocation.longitude)).departureTime(now).await();
            List<LatLng> decodedPath = PolyUtil.decode(result.routes[0].overviewPolyline.getEncodedPath());
            Log.d(TAG, result.routes[0].summary);
            mMap.addPolyline(new PolylineOptions().addAll(decodedPath));

        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
}
