package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

        InternetChecking internetChecking = new InternetChecking(getApplicationContext());
        if(internetChecking.isInternetConnected())
        {
            if (sp.getBoolean("LOGIN",false) == true){
                Intent mainIntent = new Intent(getApplicationContext(),MainNavigation.class);
                startActivity(mainIntent);
                finish();
            }
        }
        else
        {
            internetChecking.ShowAlertNetwork();
        }

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
        new UserJson().execute("");


    }

    private class UserJson extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences sp = getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP", "");
            String url = "http://" + ip + "/rest_server/index.php/api/c_dz_user/checklogin/";
            RestService re = new RestService();

            String Username = sp.getString("USERNAME", "");
            EditText Password = (EditText) findViewById(R.id.editPW);

            JSONObject resultJson = re.putLogin(url, Username, Password.getText().toString());

            return resultJson;
        }

        @Override
        protected void onPostExecute(JSONObject resultJson) {
            try {
                if (resultJson.getString("message").equals("OK")){
                    SharedPreferences sp = getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("LOGIN", true);
                    editor.commit();

                    Toast toast = Toast.makeText(getApplicationContext(), "Login OK", Toast.LENGTH_SHORT);
                    toast.show();

                    Intent mainIntent = new Intent(getApplicationContext(),MainNavigation.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Login FAIL", Toast.LENGTH_SHORT);
                    toast.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(resultJson);
        }
    }
}