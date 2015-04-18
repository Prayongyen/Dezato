package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide the navigation bar.
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // Hide the status bar.
        decorView.setSystemUiVisibility(uiOptions);

        // status bar is hidden
        ActionBar actionBar = getActionBar();
        actionBar.hide();


        setContentView(R.layout.activity_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(LogoActivity.this, LoginActivity.class);
                LogoActivity.this.startActivity(mainIntent);
                LogoActivity.this.finish();
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
