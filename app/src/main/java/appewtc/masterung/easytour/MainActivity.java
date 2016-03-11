package appewtc.masterung.easytour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManageTable objMyManageTable;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean GPSABoolean, networkABoolean;
    private double latADouble, lngADouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Request database
        objMyManageTable = new MyManageTable(this);

        //Test Add Value
        //testAddValue();

        //Delete All SQLite
        deleteAllSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

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

                Toast.makeText(MainActivity.this, "ไม่สามารถหาพิกัดได้", Toast.LENGTH_SHORT).show();

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

    private int checkSelfPermission(String accessFineLocation) {
        return 0;
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
    };



    private void getLocation() {

        //Open Service
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);


    }   // getLocation

    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check Space
        if (userString.equals("") || passwordString.equals("")) {

            //Have Space
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(MainActivity.this, "มีช่องว่าง", "กรุณากรอก ให้ครบทุกช่อง คะ");

        } else {

            //No Space
            checkUser();

        }   // if


    }   //clickLogin

    private void checkUser() {

        try {

            String[] myResultStrings = objMyManageTable.searchUser(userString);

            //Check Password
            checkPassword(myResultStrings[2], myResultStrings[3], myResultStrings[4]);

            Log.d("tour", "pass ==> " + myResultStrings[2]);

        } catch (Exception e) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(MainActivity.this, "No This User",
                    "No " + userString + " in my Database");
        }

    }   //checkUser

    private void checkPassword(String strPassword, String strName, String strStatus) {

        if (passwordString.equals(strPassword)) {

            welcome(strName, strStatus);

        } else {

            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(MainActivity.this, "Password False",
                    "Please Try Again Password False");

        } //if

    }   // checkPassword

    private void welcome(final String strName, final String strStatus) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_myaccount);
        objBuilder.setTitle("Welcome");
        objBuilder.setMessage("ยินดีต้อนรับ คุณ" + strName + "\n" + checkPosition(strStatus));
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (Integer.parseInt(strStatus)) {

                    case 0:
                        Intent tourIntent = new Intent(MainActivity.this, HubTourActivity.class);
                        tourIntent.putExtra("Name", strName);
                        tourIntent.putExtra("Lat", latADouble);
                        tourIntent.putExtra("Lng", lngADouble);
                        startActivity(tourIntent);
                        break;

                    case 1:
                        Intent adminIntent = new Intent(MainActivity.this, HubServiceActivity.class);
                        adminIntent.putExtra("Name", strName);
                        adminIntent.putExtra("Lat", latADouble);
                        adminIntent.putExtra("Lng", lngADouble);
                        startActivity(adminIntent);
                        break;

                }
                finish();

            }   //event
        });
        objBuilder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userEditText.setText("");
                passwordEditText.setText("");
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();

    }   // welcome

    private String checkPosition(String strStatus) {

        int intStatus = Integer.parseInt(strStatus);
        String strPosition = null;

        switch (intStatus) {
            case 0:
                strPosition = "สถานะ : นักท่องเที่ยว";
                break;
            case 1:
                strPosition = "สถานะ : มัคคุเทศ";
                break;
        }


        return strPosition;
    }

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    private void synJSONtoSQLite() {

        //Permission Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        int intTABLE = 1; //Amount of TABLE
        String tag = "tour";
        while (intTABLE <= 2) {

            //1.Create InputStream
            InputStream objInputStream = null;
            String strURLuser = "http://swiftcodingthai.com/puk/php_get_user_master.php";
            String strURLtour = "http://swiftcodingthai.com/puk/php_get_tour_buk.php";
            HttpPost objHttpPost = null;

            try {

                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTABLE) {
                    case 1:
                        objHttpPost = new HttpPost(strURLuser);
                        break;
                    case 2:
                        objHttpPost = new HttpPost(strURLtour);
                        break;
                }   //switch
                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();


            } catch (Exception e) {
                Log.d(tag, "InputStream ==> " + e.toString());
            }

            //2.Change InputStream to JSON String
            String strJSON = null;

            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);
                }   // while
                objInputStream.close();
                strJSON = objStringBuilder.toString();

            } catch (Exception e) {
                Log.d(tag, "strJSON ==> " + e.toString());
            }

            //3.Change JSON String to SQLite
            try {

                JSONArray objJsonArray = new JSONArray(strJSON);
                for (int i=0;i<objJsonArray.length();i++) {

                    JSONObject object = objJsonArray.getJSONObject(i);
                    switch (intTABLE) {
                        case 1:

                            //For userTABLE
                            String strUser = object.getString(MyManageTable.column_user);
                            String strPassword = object.getString(MyManageTable.column_password);
                            String strName = object.getString(MyManageTable.column_name);
                            String strStatus = object.getString(MyManageTable.column_status);

                            objMyManageTable.addUser(strUser, strPassword, strName, strStatus);

                            break;
                        case 2:

                            //For tourTABLE
                            String strCategory = object.getString(MyManageTable.column_Category);
                            String strNameTour = object.getString(MyManageTable.column_name);
                            String strProvince = object.getString(MyManageTable.column_Province);
                            String strDescription = object.getString(MyManageTable.column_Description);
                            String strType = object.getString(MyManageTable.column_Type);
                            String strTimeUse = object.getString(MyManageTable.column_TimeUse);
                            String strLat = object.getString(MyManageTable.column_Lat);
                            String strLng = object.getString(MyManageTable.column_Lng);

                            objMyManageTable.addTour(strCategory, strNameTour, strProvince, strDescription,
                                    strType, strTimeUse, strLat, strLng);

                            break;
                    } //switch
                }   // for

            } catch (Exception e) {
                Log.d(tag, "Update ==> " + e.toString());
            }


            intTABLE += 1;
        }   // while

    }   // synJSONtoSQLite

    private void deleteAllSQLite() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(MyManageTable.table_user, null, null);
        objSqLiteDatabase.delete(MyManageTable.table_tour, null, null);
    }

    private void testAddValue() {
        objMyManageTable.addUser("testUser", "testPass", "testName", "testStatus");
    }

}   // Main Class
