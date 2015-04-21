package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;


public class ImageActivity extends Activity {

    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide the navigation bar.
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // Hide the status bar.
        decorView.setSystemUiVisibility(uiOptions);

        imageLoader = new ImageLoader(getApplicationContext(),1024);

        // status bar is hidden
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        SharedPreferences sp = getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
        final String ip = sp.getString("IP","");

        SharedPreferences spImg = getSharedPreferences("IMG", Context.MODE_PRIVATE);
        final String image = spImg.getString("PATH","");

        ImageView food_path = (ImageView)findViewById(R.id.food_image);
        imageLoader.DisplayImage(image, food_path);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
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
