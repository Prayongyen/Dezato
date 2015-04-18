package th.ac.buu.se.s55160077.s55160018.dezato;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by prayong on 18/4/2558.
 */
public class AsyncGridLoader extends AsyncTask<PackageManager, TableItem, Integer> {

    @Override
    protected Integer doInBackground(PackageManager... params) {
        // this method executes the task in a background thread
        PackageManager packageManager = params[0]; // the PackageManager for loading the data
        List<ApplicationInfo> appInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        int index = 0;

        // load the application info
        if(appInfoList != null) {
            for(ApplicationInfo appInfo : appInfoList) {
                Drawable icon = packageManager.getApplicationIcon(appInfo);
                CharSequence label = packageManager.getApplicationLabel(appInfo);
                CharSequence label1 = packageManager.getApplicationLabel(appInfo);

                if(icon != null && label != null) {
                    // update the UI thread
                    publishProgress(new TableItem(icon, label.toString(),label1.toString()));
                    index++;
                }
            }
        }

        return index;
    }

}
