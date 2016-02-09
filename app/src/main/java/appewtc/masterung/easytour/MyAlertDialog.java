package appewtc.masterung.easytour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by masterUNG on 2/9/16 AD.
 */
public class MyAlertDialog {

    public void myDialog(Context context, String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(context);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }

}   // Main Class
