package appewtc.masterung.easytour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

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

        String[] positionStrings = {"ลูกทัวร์", "มัคุเทศ"};
        int intIndex = Integer.parseInt(positionString);

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_myaccount);
        objBuilder.setTitle("โปรดตรวจสอบข้อมูล");
        objBuilder.setMessage("User = " + userString + "\n" +
                "Password = " + passwordString + "\n" +
                "Name = " + nameString + "\n" +
                "Position = " + positionStrings[intIndex]);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateToMySQL();
                dialogInterface.dismiss();
            }
        });
        objBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();

    }   // confirmRegis

    private void updateToMySQL() {

        //Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_user, userString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_password, passwordString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_name, nameString));
            objNameValuePairs.add(new BasicNameValuePair(MyManageTable.column_status, positionString));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/puk/php_add_user_master.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);

            Toast.makeText(RegisterActivity.this, "อัพเดทข้อมูลเรียนร้อยแล้ว",
                    Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "ไม่สามารถเชื่อมต่อ Server ได้",
                    Toast.LENGTH_SHORT).show();
        }

    }   //updateToMySQL

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
