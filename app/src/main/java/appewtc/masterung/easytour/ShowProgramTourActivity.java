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

        if (userLngADouble < HubServiceActivity.centerLng) {
            //North
            if (userLatADouble > HubServiceActivity.centerLat) {
                //NE
                categoryString = "NE";
            } else {
                //NW
                categoryString = "NW";
            }

        } else {
            //South
            if (userLatADouble > HubServiceActivity.centerLat) {
                //SE
                categoryString = "SE";
            } else {
                //SW
                categoryString = "SW";
            }

        } // if1

        Log.d("11March", "categoryString = " + categoryString);

    }   // receiveAndSep

}   // Main Class
