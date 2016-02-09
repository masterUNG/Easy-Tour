package appewtc.masterung.easytour;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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


    }   // Main Method

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

        }   // if


    }   //clickLogin

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
        while (intTABLE <= 1) {

            //1.Create InputStream
            InputStream objInputStream = null;
            String strURLuser = "http://swiftcodingthai.com/puk/php_get_user_master.php";
            HttpPost objHttpPost = null;

            try {

                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTABLE) {
                    case 1:
                        objHttpPost = new HttpPost(strURLuser);
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
    }

    private void testAddValue() {
        objMyManageTable.addUser("testUser", "testPass", "testName", "testStatus");
    }

}   // Main Class
