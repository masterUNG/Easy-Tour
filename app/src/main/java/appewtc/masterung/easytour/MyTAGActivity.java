package appewtc.masterung.easytour;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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


    }   // Main Method

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
        mMap.addMarker(new MarkerOptions()
        .position(meLatLng)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.friend)));


    }   // Map Method

}   // Main Class
