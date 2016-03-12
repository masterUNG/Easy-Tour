package appewtc.masterung.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ShowProgramTourActivity extends AppCompatActivity {

    //Explicit
    private double userLatADouble, userLngADouble;
    private String categoryString;
    private TextView showCatTextView;
    private ListView tourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_program_tour);

        //Bind Widget
        bindWidget();

        //Receive Lat,Lng and Separate Group
        receiveAndSep();

        //ShowView
        showView();


    }   // Main Method

    private void bindWidget() {
        showCatTextView = (TextView) findViewById(R.id.textView11);
        tourListView = (ListView) findViewById(R.id.listView);
    }

    private void showView() {

        showCatTextView.setText(getResources().getString(R.string.listtour) + " " + categoryString);



    }   // showView

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
