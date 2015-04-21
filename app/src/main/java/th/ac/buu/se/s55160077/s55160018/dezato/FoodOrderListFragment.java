package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prayong on 20/4/2558.
 */
public class FoodOrderListFragment  extends ListFragment {
    String[] list_items;
    FoodOrderItem foodOrderItem;
    private List<FoodOrderItem> mItems = new ArrayList<FoodOrderItem>();
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FoodOrderListFragment newInstance(int sectionNumber) {
        FoodOrderListFragment fragment = new FoodOrderListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodOrderListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        new getFoodOrderlist().execute();
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_foodlist, container, false);

        return rootView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    private class getFoodOrderlist extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            String txtTableNo = sp.getString("txtTableNo","");
            String order_no = sp.getString("order_no","");
            SharedPreferences sf = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sf.getString("IP","");
            String url = "http://"+ip+"/rest_server/index.php/api/c_dz_order/ordertablebybillno/format/json";

            RestService re = new RestService();
            JSONObject jsonobject =  re.getOrderbyTableBill(url,txtTableNo,order_no);

            return jsonobject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonobject) {

            try {

                JSONArray jsonarray;
                jsonarray = jsonobject.getJSONArray("orders");

                int lengthObj = jsonarray.length();
                for (int i = 0; i < lengthObj; i++) {
                    Log.d("DATA",String.valueOf(i));
                    jsonobject = jsonarray.getJSONObject(i);
                    Log.d("DATA",jsonobject.toString());
                    String table_id = jsonobject.getString("table_id");
                    String food_id = jsonobject.getString("food_id");
                    String food_name = jsonobject.getString("food_name");
                    String food_type = jsonobject.getString("food_type");
                    String food_qty = jsonobject.getString("order_qty");
                    String order_no = jsonobject.getString("order_no");
                    String food_path = jsonobject.getString("food_path");

                    FoodOrderItem food = new FoodOrderItem();
                    food.setFood_id(food_id);
                    food.setFood_name(food_name);
                    food.setFood_type(food_type);
                    food.setFood_path(food_path);
                    food.setTable_id(table_id);
                    food.setOrder_no(order_no);
                    food.setFood_qty(food_qty);
                    mItems.add(food);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            int listSize = mItems.size();
//            Log.d("mItems", String.valueOf(listSize));
//            for (int i = 0; i<listSize; i++){
//                Log.d("mItems", String.valueOf(mItems.get(i)));
//            }
            if(mItems.size() == 0){
                list_items = getResources().getStringArray(R.array.string_array);
                setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));
            }
            else
            {
                FoodOrderListAdapter adapter = new FoodOrderListAdapter(getActivity(), mItems);
                setListAdapter(adapter);
            }

            super.onPostExecute(jsonobject);
        }
    }
}