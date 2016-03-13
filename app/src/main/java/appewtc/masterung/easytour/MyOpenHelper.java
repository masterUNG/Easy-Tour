package appewtc.masterung.easytour;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 2/1/16 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit
    public static final String database_name = "easyTour.db";
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name text, " +
            "Status text);";

    private static final String create_tour_table = "create table tourTABLE (" +
            "_id integer primary key, " +
            "Category text, " +
            "Name text, " +
            "Province text, " +
            "Description text, " +
            "Type text, " +
            "TimeUse text, " +
            "Lat text, " +
            "Lng text);";

    private static final String create_my_tour_table = "create table myTourTABLE (" +
            "_id integer primary key, " +
            "Name text, " +
            "TimeUse text, " +
            "DateStart text, " +
            "HrStart text, " +
            "HrEnd text);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_user_table);
        db.execSQL(create_tour_table);
        db.execSQL(create_my_tour_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}   // Main Class
