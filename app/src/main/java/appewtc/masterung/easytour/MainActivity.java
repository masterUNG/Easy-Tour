package appewtc.masterung.easytour;

import android.database.sqlite.SQLiteDatabase;
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

        //Test Add Value
        //testAddValue();

        //Delete All SQLite
        deleteAllSQLite();


    }   // Main Method

    private void deleteAllSQLite() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(MyManageTable.table_user, null, null);
    }

    private void testAddValue() {
        objMyManageTable.addUser("testUser", "testPass", "testName", "testStatus");
    }

}   // Main Class
