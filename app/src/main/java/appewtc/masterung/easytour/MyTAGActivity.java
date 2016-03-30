package appewtc.masterung.easytour;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class MyTAGActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double meLatADouble, meLngADouble;
    private LatLng meLatLng;

    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean GPSABoolean, networkABoolean;
    private double latADouble, lngADouble;
    private String meIDString;



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

        objLocationManager.removeUpdates(objLocationListener1);
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

        objLocationManager.removeUpdates(objLocationListener1);

    }


    public Location requestLocation(String strProvider, String strError) {

        Location objLocation = null;

        if (objLocationManager.isProviderEnabled(strProvider)) {

            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener1);
            objLocation = objLocationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("Tour", strError);
        }   // if

        return objLocation;
    }


    //Create Class
    public final LocationListener objLocationListener1 = new LocationListener() {
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
        meIDString = getIntent().getStringExtra("meID");
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

        Log.d("31March", "meID ==> " + meIDString);
        Log.d("31March", "Lat ==> " + latADouble);
        Log.d("31March", "Lng ==> " + lngADouble);

        updateValueToMySQL(meIDString,
                Double.toString(latADouble),
                Double.toString(lngADouble));

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

    private void updateValueToMySQL(String meIDString, String strLat, String strLng) {

        //Connected Http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            nameValuePairs.add(new BasicNameValuePair("id", meIDString));
            nameValuePairs.add(new BasicNameValuePair("Lat", strLat));
            nameValuePairs.add(new BasicNameValuePair("Lng", strLng));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://swiftcodingthai.com/puk/php_edit_location_master.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpClient.execute(httpPost);

        } catch (Exception e) {
            Log.d("31March", "Error Update ==> " + e.toString());
        }



    }   // update

    private void createMarkerMe() {

        //Marker Me
        mMap.addMarker(new MarkerOptions()
                .position(meLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.friend)));


    }   // createMarkerMe

}   // Main Class
