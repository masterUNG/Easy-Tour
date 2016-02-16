package appewtc.masterung.easytour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class HubServiceActivity extends AppCompatActivity {

    //Explicit
    private TextView showNameTextView;
    private Button authenButton, listuserButton, warnButton, trackButton,
            recommentButton, listTourButton;
    private String nameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub_service);

        //Bind Widget
        bindWidget();

        //Show Name
        showName();

        //Button Controller
        buttonController();

    }   // Main Method

    private void showName() {

        nameString = getIntent().getStringExtra("Name");
        showNameTextView.setText("Welcome : " + nameString);

    }   // showName

    private void buttonController() {



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

}   // Main Class
