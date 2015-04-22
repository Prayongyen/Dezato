package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CheckBillActivity extends Activity {
    private List<FoodOrderItem> mItems = new ArrayList<FoodOrderItem>();
    private String sum = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bill);
        SharedPreferences sp = getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        String txtTableNo = sp.getString("txtTableNo","");

        TextView textView = (TextView)findViewById(R.id.textViewTableName);
        textView.setText("Table "+txtTableNo);
        new BillDataJson().execute("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_bill, menu);

        SharedPreferences sp = getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        String txtTableNo = sp.getString("txtTableNo","");
        ActionBar ab = getActionBar();
        ab.setTitle("Check Bill Table "+txtTableNo);

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
    private class BillDataJson extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {

            SharedPreferences sp = getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");

            SharedPreferences spTable = getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            String tableNo = spTable.getString("txtTableNo","");

            String url = "http://"+ip+"/rest_server/index.php/api/c_dz_bill/billData/id/"+tableNo+"/format/json";

            RestService re = new RestService();
            JSONObject jsonobject =  re.doGet(url);

            return jsonobject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonobject) {
            try {
                JSONArray jsonarray;
                sum = jsonobject.getString("summary");
                jsonarray = jsonobject.getJSONArray("bill");
                int lengthObj = jsonarray.length();

                for (int i = 0; i < lengthObj; i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    String foodname = jsonobject.getString("food_name");
                    String price = jsonobject.getString("bill_sum");
                    String qty = jsonobject.getString("QTY");
                    FoodOrderItem food = new FoodOrderItem();
                    food.setFood_name(foodname);
                    food.setFood_qty(qty);
                    food.setFood_price(price);
                    mItems.add(food);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(mItems.size() == 0){

            }
            else
            {
                TextView textView0 = (TextView) findViewById(R.id.textView0);
                textView0.setVisibility(View.GONE);
                TextView textView1 = (TextView) findViewById(R.id.textView1);
                textView1.setVisibility(View.GONE);
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setVisibility(View.GONE);



                CheckBillAdapter adapter = new CheckBillAdapter(getApplicationContext(), mItems);
                ListView listView = (ListView)findViewById(R.id.listViewOrder);
                listView.setAdapter(adapter);

                TextView textView = (TextView)findViewById(R.id.textViewSumAll);
                Locale locale = new Locale("th", "TH");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                textView.setText(fmt.format(Integer.parseInt(sum)));
            }

            super.onPostExecute(jsonobject);
        }
    }
    public String formatDecimal(float number) {
        float epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number); // sdb
        } else {
            return String.format("%10.2f", number); // dj_segfault
        }
    }
}
