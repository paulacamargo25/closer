package com.example.paula.closerapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Map;

//Dani
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.joda.time.DateTime;

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
    private static  Integer[] monday_1 = new Integer[]{Place.TYPE_BANK, Place.TYPE_SCHOOL, Place.TYPE_GYM, Place.TYPE_CAFE};
    private static  Integer[] monday_2 = new Integer[]{Place.TYPE_RESTAURANT};
    private static  Integer[] monday_3 = new Integer[]{};
    private static  Integer[] tuesday_1 = new Integer[]{Place.TYPE_CAFE, Place.TYPE_SCHOOL, Place.TYPE_GYM};
    private static  Integer[] tuesday_2 = new Integer[]{Place.TYPE_RESTAURANT };
    private static  Integer[] tuesday_3 = new Integer[]{};
    private static  Integer[] wednesday_1 = new Integer[]{Place.TYPE_CAFE, Place.TYPE_SCHOOL, Place.TYPE_GYM};
    private static  Integer[] wednesday_2 = new Integer[]{Place.TYPE_RESTAURANT};
    private static  Integer[] wednesday_3 = new Integer[]{};
    private static  Integer[] thursday_1 = new Integer[]{Place.TYPE_CAFE, Place.TYPE_SCHOOL, Place.TYPE_GYM};
    private static  Integer[] thursday_2 = new Integer[]{Place.TYPE_RESTAURANT};
    private static  Integer[] thursday_3 = new Integer[]{Place.TYPE_CAFE, Place.TYPE_SCHOOL, Place.TYPE_GYM};
    private static  Integer[] friday_1 = new Integer[]{Place.TYPE_CAFE, Place.TYPE_SCHOOL, Place.TYPE_GYM};
    private static  Integer[] friday_2 = new Integer[]{Place.TYPE_RESTAURANT, Place.TYPE_CAFE};
    private static  Integer[] friday_3 = new Integer[]{Place.TYPE_CASINO, Place.TYPE_BAR, Place.TYPE_CAFE, Place.TYPE_MEAL_DELIVERY, Place.TYPE_NIGHT_CLUB, Place.TYPE_LIQUOR_STORE};
    private static  Integer[] saturday_1 = new Integer[]{Place.TYPE_GYM, Place.TYPE_LAUNDRY};
    private static  Integer[] saturday_2 = new Integer[]{Place.TYPE_RESTAURANT, Place.TYPE_CAFE, Place.TYPE_MOVIE_THEATER, Place.TYPE_CLOTHING_STORE};
    private static  Integer[] saturday_3 = new Integer[]{Place.TYPE_CASINO, Place.TYPE_BAR, Place.TYPE_CAFE, Place.TYPE_MEAL_DELIVERY, Place.TYPE_NIGHT_CLUB, Place.TYPE_MOVIE_THEATER, Place.TYPE_LIQUOR_STORE};
    private static  Integer[] sunday_1 = new Integer[]{Place.TYPE_CHURCH, Place.TYPE_PARK, Place.TYPE_LAUNDRY, Place.TYPE_CAFE, Place.TYPE_ZOO, Place.TYPE_SHOPPING_MALL, Place.TYPE_SPA, Place.TYPE_GROCERY_OR_SUPERMARKET, Place.TYPE_VETERINARY_CARE, };
    private static  Integer[] sunday_2 = new Integer[]{Place.TYPE_CHURCH, Place.TYPE_RESTAURANT, Place.TYPE_CAFE, Place.TYPE_MEAL_DELIVERY, Place.TYPE_MOVIE_THEATER};
    private static  Integer[] sunday_3 = new Integer[]{Place.TYPE_RESTAURANT, Place.TYPE_MEAL_DELIVERY};

    private static Map<String, Integer[]> monday = new HashMap<String, Integer[]>();
    private Map<String, Integer[]> tuesday = new HashMap<String, Integer[]>();
    private Map<String, Integer[]> wednesday = new HashMap<String, Integer[]>();
    private Map<String, Integer[]> thursday = new HashMap<String, Integer[]>();
    private Map<String, Integer[]> friday = new HashMap<String, Integer[]>();
    private Map<String, Integer[]> saturday = new HashMap<String, Integer[]>();
    private Map<String, Integer[]> sunday = new HashMap<String, Integer[]>();

    private Map<String, Map<String, Integer[]> > placesTypes = new HashMap<String, Map<String, Integer[]>>();


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

        monday.put("man", monday_1);
        monday.put("tar", monday_2);
        monday.put("noc", monday_3);
        tuesday.put("man", tuesday_1);
        tuesday.put("tar", tuesday_2);
        tuesday.put("noc", tuesday_3);
        wednesday.put("man", wednesday_1);
        wednesday.put("tar", wednesday_2);
        wednesday.put("noc", wednesday_3);
        thursday.put("man", thursday_1);
        thursday.put("tar", thursday_2);
        thursday.put("noc", thursday_3);
        friday.put("man", friday_1);
        friday.put("tar", friday_2);
        friday.put("noc", friday_3);
        saturday.put("man", saturday_1);
        saturday.put("tar", saturday_2);
        saturday.put("noc", saturday_3);
        sunday.put("man", sunday_1);
        sunday.put("tar", sunday_2);
        sunday.put("noc", sunday_3);

        placesTypes.put("monday", monday);
        placesTypes.put("tuesday", tuesday);
        placesTypes.put("wednesday", wednesday);
        placesTypes.put("thursday", thursday);
        placesTypes.put("friday", friday);
        placesTypes.put("saturday", saturday);
        placesTypes.put("sunday", sunday);

        placesTypes.get("monday").get("man");

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
                final ArrayList<MyPlace> placesList = new ArrayList<>();

                final PlaceAdapter adapter = new PlaceAdapter(MainActivity.this, placesList);

                final List<LatLng> placesLocations = new ArrayList<>();

                if (task.isSuccessful()) {

                    Log.d(TAG, "Places success");
                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                    for (final PlaceLikelihood placeLikelihood : likelyPlaces) {
                        for (int placeType : placeLikelihood.getPlace().freeze().getPlaceTypes()) {
                            List<Integer> listPlacesTypes = Arrays.asList(placesTypes.get("tuesday").get("man"));
//                            if (placeType == Place.TYPE_RESTAURANT) {
                            if(listPlacesTypes.contains(placeType)){
                                final Task<PlacePhotoMetadataResponse> photoMetadataResponse = geoDataClient.getPlacePhotos(placeLikelihood.getPlace().freeze().getId());
                                final String placeName = placeLikelihood.getPlace().freeze().getName().toString();
                                final String placeAddress = placeLikelihood.getPlace().freeze().getAddress().toString();

                                Log.d(TAG, "OJO Name: " + placeName);
                                Log.d(TAG, "OJO Address: " + placeAddress);

                                photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
                                    @Override
                                    public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                                        // Get the list of photos.
                                        PlacePhotoMetadataResponse photos = task.getResult();
                                        // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                                        PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                                        // Get the first photo in the list.
                                        if(photoMetadataBuffer.getCount()>0) {
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
                                                    Log.d(TAG, "Image Width: " + bitmap.getWidth());
                                                    Log.d(TAG, "Image Height: " + bitmap.getHeight());
                                                    Log.d(TAG, "Name: " + placeName);
                                                    Log.d(TAG, "Address: " + placeAddress);

                                                    placesList.add(new MyPlace(bitmap, placeName, placeAddress));
                                                    PlacesListView.setAdapter(adapter);
                                                }
                                            });
                                        }
                                        else {
                                            Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.googleplaces);
                                            placesList.add(new MyPlace(defaultImage, placeName, placeAddress));
                                            PlacesListView.setAdapter(adapter);
                                        }
                                        photoMetadataBuffer.release();
                                    }
                                });



                                placesLocations.add(placeLikelihood.getPlace().freeze().getLatLng());
                            }

                        }
                    }
                    likelyPlaces.release();

                    Log.d(TAG, "Size: " + placesList.size());



                    PlacesListView.setAdapter(adapter);

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
