package appewtc.masterung.easytour;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/1/16 AD.
 */
public class MyManageTable {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String table_user = "userTABLE";
    public static final String column_id = "_id";
    public static final String column_user = "User";
    public static final String column_password = "Password";
    public static final String column_name = "Name";
    public static final String column_status = "Status";

    public MyManageTable(Context context) {

        //Create & Connected
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public String[] searchUser(String strUser) {

        try {

            String[] resultStrings = null;
            Cursor objCursor = readSqLiteDatabase.query(table_user,
                    new String[]{column_id, column_user, column_password, column_name, column_status},
                    column_user + "=?",
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);

            if (objCursor != null) {
                if (objCursor.moveToFirst()) {

                    resultStrings = new String[5];
                    for (int i=0;i<5;i++) {
                        resultStrings[i] = objCursor.getString(i);
                    }   //for
                }   // if2
            }   //if1
            objCursor.close();
            return resultStrings;

        } catch (Exception e) {
            return null;
        }
        //return new String[0];
    }


    public long addUser(String strUser,
                        String strPassword,
                        String strName,
                        String strStatus) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(column_user, strUser);
        objContentValues.put(column_password, strPassword);
        objContentValues.put(column_name, strName);
        objContentValues.put(column_status, strStatus);

        return writeSqLiteDatabase.insert(table_user, null, objContentValues);
    }


}   // Main Class
