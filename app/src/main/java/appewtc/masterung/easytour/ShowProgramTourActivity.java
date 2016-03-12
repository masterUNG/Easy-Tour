package appewtc.masterung.easytour;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        //Read All Where
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tourTABLE WHERE Category = " + "'" + categoryString + "'", null);
        cursor.moveToFirst();

        int intCount = cursor.getCount();

        String[] nameStrings = new String[intCount];
        String[] provinceStrings = new String[intCount];
        String[] timeUseStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            nameStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_name));
            provinceStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_Province));
            timeUseStrings[i] = cursor.getString(cursor.getColumnIndex(MyManageTable.column_TimeUse));

            cursor.moveToNext();
        }   //for
        cursor.close();

        TourAdapter tourAdapter = new TourAdapter(ShowProgramTourActivity.this,
                nameStrings, provinceStrings, timeUseStrings);
        tourListView.setAdapter(tourAdapter);


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
