package appewtc.masterung.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText, nameEditText;
    private RadioGroup positionRadioGroup;
    private RadioButton tourRadioButton, adminRadioButton;
    private Button registerButton;
    private String userString, passwordString, nameString, positionString = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Bind Widget
        bindWidget();

        //Radio Controller
        radioController();

        //Button Controller
        buttonController();


    }   // Main Method

    private void buttonController() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                nameString = nameEditText.getText().toString().trim();

                //Check Space
                if (checkSpace()) {

                    //Have Space
                    MyAlertDialog objMyAlertDialog = new MyAlertDialog();
                    objMyAlertDialog.myDialog(RegisterActivity.this,
                            "มีช่องว่าง", "กรุณากรอกทุกช่อง คะ");

                } else {

                    //No Space
                    checkUser();

                }   // if

            }   // event
        });

    }   // buttonController

    private void checkUser() {

        try {
            //User not OK
            MyManageTable objMyManageTable = new MyManageTable(this);
            String[] resultStrings = objMyManageTable.searchUser(userString);

            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.myDialog(RegisterActivity.this, "User ซ้ำ",
                    "เปลี่ยน user ใหม่มี " + resultStrings[1] + " แล้ว");

        } catch (Exception e) {
            //User OK
            confirmRegis();
        }

    }   // checkUser

    private void confirmRegis() {

    }   // confirmRegis

    private boolean checkSpace() {
        return userString.equals("") || passwordString.equals("") || nameString.equals("");
    }

    private void radioController() {

        positionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:
                        positionString = "0";
                        break;
                    case R.id.radioButton2:
                        positionString = "1";
                        break;
                }   //switch
            }   // event
        });

    }   //radioController

    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        nameEditText = (EditText) findViewById(R.id.editText5);
        positionRadioGroup = (RadioGroup) findViewById(R.id.ragPosition);
        tourRadioButton = (RadioButton) findViewById(R.id.radioButton);
        adminRadioButton = (RadioButton) findViewById(R.id.radioButton2);
        registerButton = (Button) findViewById(R.id.button8);

    }   // bindWidget

}   // Main Class
