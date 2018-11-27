package com.example.paula.closerapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;

import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;

//Dani
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ButtonMap;
    private TextView LocationText;
    private ListView PlacesListView;
    private double longitude;
    private double latitude;
    private Location last;
    //Dani
    private Spinner spinner;
    //private static final String[] paths = {"item 1", "item 2", "item 3"};
    private static final List<String> paths = new ArrayList<>();

    public static final String TAG = "CurrentLocNearByPlaces";
    private static final int LOC_REQ_CODE = 1;

    protected GeoDataClient geoDataClient;
    protected PlaceDetectionClient placeDetectionClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButtonMap = findViewById(R.id.buttonMap);
        //ButtonMap.setOnClickListener(this);
        paths.add("item 1");
        paths.add("item 2");
        paths.add("item 3");
        LocationText = findViewById(R.id.location_tv);
        PlacesListView = findViewById(R.id.list_places);
        
        //Spinner Seleccionar Opcion para Filtro
        //setContentView(R.layout.activity_main);
        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, paths);

        spinner.setAdapter(adapter);
        String optionSelected = spinner.getSelectedItem().toString();



        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        placeDetectionClient = Places.getPlaceDetectionClient(this, null);
        geoDataClient = Places.getGeoDataClient(this, null);

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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationListener);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        last = locationManager.getLastKnownLocation(locationProvider);
        LocationText.setText(last.toString());

        getActivity();

    }

    private void getPhotos(String placeId) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = geoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        Bitmap bitmap = photo.getBitmap();
                    }
                });
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getActivity() {
        PlaceFilter placeFilter = new PlaceFilter(false, null);

        Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.
                getCurrentPlace(null);
        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                Log.d(TAG, "current location places info");
                List<String> placesList = new ArrayList<>();
                final List<LatLng> placesLocations = new ArrayList<>();
                if (task.isSuccessful()) {

                    Log.d(TAG, "Places success");
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        for (int placeType : placeLikelihood.getPlace().freeze().getPlaceTypes()) {
                            if (placeType == Place.TYPE_RESTAURANT) {
                                placesList.add(placeLikelihood.getPlace().freeze().getName().toString());
                                placesLocations.add(placeLikelihood.getPlace().freeze().getLatLng());
                            }
                        }
                    }
                    likelyPlaces.release();
                    ListAdapter arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_listview, R.id.textView, placesList);
                    PlacesListView.setAdapter(arrayAdapter);
                    PlacesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            intent.putExtra("PLACE_LOCATION", placesLocations.get(position));
                            intent.putExtra("MY_LOCATION", last);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.d(TAG, "Places error");
                }
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
