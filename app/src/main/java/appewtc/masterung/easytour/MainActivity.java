package appewtc.masterung.easytour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private MyManageTable objMyManageTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request database
        objMyManageTable = new MyManageTable(this);

    }   // Main Method

}   // Main Class
