package com.example.paula.closerapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ButtonMap;
    private TextView LocationText;
    private ListView PlacesListView;
    private double longitude;
    private double latitude;

    public static final String TAG = "CurrentLocNearByPlaces";
    private static final int LOC_REQ_CODE = 1;

    protected GeoDataClient geoDataClient;
    protected PlaceDetectionClient placeDetectionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonMap = findViewById(R.id.buttonMap);
        ButtonMap.setOnClickListener(this);
        LocationText = findViewById(R.id.location_tv);
        PlacesListView = findViewById(R.id.list_places);
        
        //Obtiene mes,día,hora del móvil
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateTimeInstance().format(calendar.getTime()).toString();
        Date today = Calendar.getInstance().getTime();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        String time = DateFormat.getTimeInstance().format(calendar.getTime());
        String day = DateFormat.getDateInstance().format(calendar.getTime());

        String theDate = calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + " " + calendar.get(Calendar.AM_PM);
        String mes = calendar.get(Calendar.MONTH)+ ""; //0-11
        String año = calendar.get(Calendar.YEAR)+ "";
        String dia = calendar.get(Calendar.DAY_OF_MONTH)+ "";
        String hora = calendar.get(Calendar.HOUR_OF_DAY)+ ""; //HOUR = 6 HoUR_OF_DAY = 18
        String am_pm = calendar.get(Calendar.AM_PM)+ ""; //0 if AM, 1 if PM

        //FIN

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        placeDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //makeUseOfNewLocation(location);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location last = locationManager.getLastKnownLocation(locationProvider);
        LocationText.setText(last.toString());
        getActivity();

    }

    @SuppressLint("MissingPermission")
    private void getActivity() {
        ArrayList<String> filters = new ArrayList<>();
        filters.add(Place.TYPE_UNIVERSITY+"");

        PlaceFilter placeFilter = new PlaceFilter(false, filters);

        Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.
                getCurrentPlace(placeFilter);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                Log.d(TAG, "current location places info");
                List<String> placesList = new ArrayList<>();
                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                    placesList.add(placeLikelihood.getPlace().freeze().getPlaceTypes().toString());
                }
                likelyPlaces.release();
                ListAdapter arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, R.id.textView, placesList);
                PlacesListView.setAdapter(arrayAdapter);
            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.buttonMap:
                i = new Intent(this, MapsActivity.class); startActivity(i); break;
        }
    }
}
