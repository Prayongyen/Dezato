package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);

        EditText editIP = (EditText)findViewById(R.id.editIP);
        EditText editName = (EditText)findViewById(R.id.editUser);

        editIP.setText(sp.getString("IP",""));
        editName.setText(sp.getString("USERNAME",""));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void onLoginClick(View v) {



        SharedPreferences sp = getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        EditText IP = (EditText)findViewById(R.id.editIP);
        EditText Username = (EditText)findViewById(R.id.editUser);

        editor.putString("IP", IP.getText().toString());
        editor.putString("USERNAME", Username.getText().toString());
        editor.commit();

        final Intent mainIntent = new Intent(this, MainNavigation.class);
        startActivity(mainIntent);
    }

//    public boolean chkLogin(String ip,String user,String pw)
//    {
//
//        try {
//            // 1. create HttpClient
//            HttpClient httpclient = new DefaultHttpClient();
//            // 2. make POST request to the given URL
//
//            HttpPost httpPut = new
//                    HttpPost("http://"+ip+"/rest_server/index.php/api/c_dz_user/user/name/"+user+"/");
//
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.get("user_name");
//            jsonObject.get("user_password");
//
//        } catch (Exception e) {
//            //Log.d("InputStream", e.getLocalizedMessage());
//        }
//    }
}