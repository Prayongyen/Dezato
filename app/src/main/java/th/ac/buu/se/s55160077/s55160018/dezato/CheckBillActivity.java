package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class CheckBillActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bill);

        String[] foodName = {
                "American Set","Bali Set","Cheese Stick","Maccaroni","American Set","Bali Set","Cheese Stick","Maccaroni","American Set","Bali Set","Cheese Stick","Maccaroni"
        };
        String[] QTY = {
                "1","2","50","5","1","2","50","5","1","2","50","5"
        };
        String[] price = {
                "200","123","1412","13","200","123","1412","13","200","123","1412","13"
        };

        CheckBillAdapter adapter = new CheckBillAdapter(getApplicationContext(), foodName, QTY, price);

        ListView listView = (ListView)findViewById(R.id.listViewOrder);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_bill, menu);
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
