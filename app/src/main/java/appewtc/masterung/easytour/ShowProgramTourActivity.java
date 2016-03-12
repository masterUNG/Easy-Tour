package appewtc.masterung.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ShowProgramTourActivity extends AppCompatActivity {

    //Explicit
    private double userLatADouble, userLngADouble;
    private String categoryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program_tour);

        //Receive Lat,Lng and Separate Group
        receiveAndSep();


    }   // Main Method

    private void receiveAndSep() {

        userLatADouble = getIntent().getDoubleExtra("Lat", HubServiceActivity.centerLat);
        userLngADouble = getIntent().getDoubleExtra("Lng", HubServiceActivity.centerLng);

        if (userLatADouble > HubServiceActivity.centerLat) {
            //N
            if (userLngADouble > HubServiceActivity.centerLng) {
                //E
                categoryString = "NE";
            } else {
                //W
                categoryString = "NW";
            }
        } else {
            //S
            if (userLngADouble > HubServiceActivity.centerLng) {
                //E
                categoryString = "SE";
            } else {
                //W
                categoryString = "SW";
            }
        }

        Log.d("12March", "categoryString = " + categoryString);

    }   // receiveAndSep

}   // Main Class
