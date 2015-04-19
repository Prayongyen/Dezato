package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * Created by prayong on 19/4/2558.
 */
public class InternetChecking{

    private static Context mContext;

    public InternetChecking(Context mContext)
    {
        this.mContext = mContext;
    }

    public  boolean isInternetConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
        if(networkinfo != null && networkinfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public void ShowAlertNetwork()
    {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(mContext);
        alertDlg.setMessage("Application require internet. Please on internet...")
                .setTitle("Show usage")
                .setIcon(R.drawable.logo_small)
                .setCancelable(false)
                .setPositiveButton("To Network Setting",
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                ((Activity)mContext).startActivityForResult(intent,0);
                            }
                        });
        alertDlg.setNegativeButton("Close",
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                        System.exit(0);
                    }
                });
        AlertDialog dialog = alertDlg.create();
        dialog.show();

    }


}
