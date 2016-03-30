package appewtc.masterung.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowDetailTourActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private TextView dateTextView, nameTextView, provinceTextView,
            typeTextView, timeUseTextView, descripTextView;
    private Button setTimeButton, addMyProgramButton, cancelButton;
    private String tourDateString, nameString, provinceString,
            typeString, timeUseString, descripString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_tour);

        //Bind Widget
        bindWidget();

        //Get Current Time & Show
        getTimeAndShow();

        //Show TextView
        showTextView();

        //Button Controller
        buttonController();


    }   // Main Method

    private void buttonController() {
        setTimeButton.setOnClickListener(this);
        addMyProgramButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    private void showTextView() {

        nameString = getIntent().getStringExtra("Name");
        provinceString = getIntent().getStringExtra("Province");
        typeString = getIntent().getStringExtra("Type");
        timeUseString = getIntent().getStringExtra("TimeUse");
        descripString = getIntent().getStringExtra("Descrip");

        nameTextView.setText(nameString);
        provinceTextView.setText(provinceString);
        typeTextView.setText(typeString);
        timeUseTextView.setText(timeUseString);
        descripTextView.setText(descripString);


    }   // showTextView

    private void getTimeAndShow() {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        tourDateString = dateFormat.format(date);
        showDate(tourDateString);

    }   // getTime

    private void showDate(String showDate) {
        dateTextView.setText(showDate);
    }

    private void bindWidget() {

        dateTextView = (TextView) findViewById(R.id.textView12);
        nameTextView = (TextView) findViewById(R.id.textView15);
        provinceTextView = (TextView) findViewById(R.id.textView17);
        typeTextView = (TextView) findViewById(R.id.textView19);
        timeUseTextView = (TextView) findViewById(R.id.textView21);
        descripTextView = (TextView) findViewById(R.id.textView22);
        setTimeButton = (Button) findViewById(R.id.button9);
        addMyProgramButton = (Button) findViewById(R.id.button10);
        cancelButton = (Button) findViewById(R.id.button11);


    }   // bindWidget

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button9:
                //Set Time
                break;
            case R.id.button10:
                //Add My Program
                break;
            case R.id.button11:
                //Cancel
                finish();
                break;
        }   // switch

    }   // onClick

}   // Main Class
