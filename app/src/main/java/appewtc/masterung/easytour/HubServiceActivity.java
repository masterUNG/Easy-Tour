package appewtc.masterung.easytour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HubServiceActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private TextView showNameTextView;
    private Button authenButton, listuserButton, warnButton, trackButton,
            recommentButton, listTourButton;
    private String nameString;
    public static final double centerLat = 14.47723421;
    public static final double centerLng = 100.64575195;
    private double myLat, myLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_service);

        //Bind Widget
        bindWidget();

        //Check Section
        checkSection();

        //Show Name
        showName();

        //Button Controller
        buttonController();

    }   // Main Method

    private void checkSection() {

        String tag = "Section";

        myLat = getIntent().getDoubleExtra("Lat", 0);
        myLng = getIntent().getDoubleExtra("Lng", 0);

        Log.d(tag, "myLat ==> " + myLat);
        Log.d(tag, "myLng ==> " + myLng);

    }   // checkSection

    private void showName() {

        nameString = getIntent().getStringExtra("Name");
        showNameTextView.setText("Welcome : " + nameString);

    }   // showName

    private void buttonController() {

        authenButton.setOnClickListener(this);
        listuserButton.setOnClickListener(this);
        warnButton.setOnClickListener(this);
        trackButton.setOnClickListener(this);
        recommentButton.setOnClickListener(this);
        listTourButton.setOnClickListener(this);

    }   // buttonController

    private void bindWidget() {

        showNameTextView = (TextView) findViewById(R.id.textView2);
        authenButton = (Button) findViewById(R.id.button2);
        listuserButton = (Button) findViewById(R.id.button3);
        warnButton = (Button) findViewById(R.id.button4);
        trackButton = (Button) findViewById(R.id.button5);
        recommentButton = (Button) findViewById(R.id.button6);
        listTourButton = (Button) findViewById(R.id.button7);

    }   // bindWidget

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button2:

                Intent authenIntent = new Intent(HubServiceActivity.this, RegisterActivity.class);
                startActivity(authenIntent);

                break;
            case R.id.button3:
                //โปรแกรมทัวร์
                Intent intent = new Intent(HubServiceActivity.this, ShowProgramTourActivity.class);
                intent.putExtra("Lat", myLat);
                intent.putExtra("Lng", myLng);
                startActivity(intent);

                break;
            case R.id.button4:

                Intent intent1 = new Intent(HubServiceActivity.this, MyTAGActivity.class);
                intent1.putExtra("Lat", myLat);
                intent1.putExtra("Lng", myLng);
                startActivity(intent1);

                break;
            case R.id.button5:
                break;
            case R.id.button6:
                break;
            case R.id.button7:
                break;
        }   // switch

    }   // onClick



}   // Main Class
