package appewtc.masterung.easytour;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyTAGActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double meLatADouble, meLngADouble;
    private LatLng meLatLng;

    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean GPSABoolean, networkABoolean;
    private double latADouble, lngADouble;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tag);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Get Value From Intent
        getLatLngFormIntent();

        //Setup LatLng
        setupLatLng();

        //Get Location
        getLocation();


    }   // Main Method

    @Override
    protected void onResume() {
        super.onResume();

        objLocationManager.removeUpdates(objLocationListener);
        latADouble = 0;
        lngADouble = 0;

        Location networkLocation = requestLocation(LocationManager.NETWORK_PROVIDER, "No Internet");

        if (networkLocation != null) {
            latADouble = networkLocation.getLatitude();
            lngADouble = networkLocation.getLongitude();
        }   // if

        Location GPSLocation = requestLocation(LocationManager.GPS_PROVIDER, "No GPS card");

        if (GPSLocation != null) {
            latADouble = GPSLocation.getLatitude();
            lngADouble = GPSLocation.getLongitude();
        }   // if

        //Show Log
        Log.d("easyTour", "Lat ==> " + latADouble);
        Log.d("easyTour", "Lng ==> " + lngADouble);

    }   // onResume

    @Override
    protected void onStart() {
        super.onStart();

        GPSABoolean = objLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); // มี Card GPS จะเป็น True

        if (!GPSABoolean) {

            networkABoolean = objLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!networkABoolean) {

                Toast.makeText(MyTAGActivity.this, "ไม่สามารถหาพิกัดได้", Toast.LENGTH_SHORT).show();

            }   // if

        }   // if

    }   // onStart

    @Override
    protected void onStop() {
        super.onStop();

        objLocationManager.removeUpdates(objLocationListener);

    }


    public Location requestLocation(String strProvider, String strError) {

        Location objLocation = null;

        if (objLocationManager.isProviderEnabled(strProvider)) {

            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener);
            objLocation = objLocationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("Tour", strError);
        }   // if

        return objLocation;
    }


    //Create Class
    public final LocationListener objLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latADouble = location.getLatitude();
            lngADouble = location.getLongitude();

            //Show Log
            Log.d("easyTour", "Lat ==> " + latADouble);
            Log.d("easyTour", "Lng ==> " + lngADouble);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };  // class


    private void getLocation() {

        //Open Service
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);


    }   // getLocation

    private void setupLatLng() {

        //for Start App
        meLatLng = new LatLng(meLatADouble, meLngADouble);

    }

    private void getLatLngFormIntent() {
        meLatADouble = getIntent().getDoubleExtra("Lat", 14.47723421);
        meLngADouble = getIntent().getDoubleExtra("Lng", 100.64575195);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //กำหนดจุดเริ่มต้นให้แผนที่
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meLatLng, 16));

        createMarkerMe();

        myLoopCrateMarker();


    }   // Map Method

    private void myLoopCrateMarker() {

        Log.d("31March", "Lat ==> " + latADouble);
        Log.d("31March", "Lng ==> " + lngADouble);

        meLatLng = new LatLng(latADouble, lngADouble);

        mMap.clear();

        createMarkerMe();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoopCrateMarker();
            }
        }, 3000);


    }   // myLoop

    private void createMarkerMe() {

        //Marker Me
        mMap.addMarker(new MarkerOptions()
                .position(meLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.friend)));


    }   // createMarkerMe

}   // Main Class
