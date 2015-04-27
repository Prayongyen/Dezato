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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prayong on 20/4/2558.
 */

public class DessertFragment  extends ListFragment {
    private List<FoodItem> mItems = new ArrayList<FoodItem>();
    String[] list_items;
    private static String status;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DessertFragment newInstance(int sectionNumber,String status) {
        DessertFragment.status = status;
        DessertFragment fragment = new DessertFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public DessertFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        new RewardJson().execute("");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_foodlist, container, false);
        //list_items = getResources().getStringArray(R.array.string_array);
        //setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("TEST", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Log.d("TEST", "onKey Back listener is working1!!!");
                    //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return true;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
    private class RewardJson extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {

            SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String url = "http://"+ip+"/dezatoshop/rest/index.php/api/c_dz_food/foodType/id/"+status+"/format/json";

            RestService re = new RestService();
            JSONObject jsonobject =  re.doGet(url);

            return jsonobject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonobject) {
            try {

                JSONArray jsonarray;
                jsonarray = jsonobject.getJSONArray("foods");
                int lengthObj = jsonarray.length();

                for (int i = 0; i < lengthObj; i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    String food_id = jsonobject.getString("food_id");
                    String food_name = jsonobject.getString("food_name");
                    String food_type = jsonobject.getString("food_type");
                    String food_path = jsonobject.getString("food_path");
                    String food_price = jsonobject.getString("food_price");
                    FoodItem food = new FoodItem();
                    food.setFood_id(food_id);
                    food.setFood_name(food_name);
                    food.setFood_type(food_type);
                    food.setFood_path(food_path);
                    food.setFood_price(food_price);
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
                setEmptyText("Nothing!!");

            }
            else
            {
                FoodListAdapter adapter = new FoodListAdapter(getActivity(), mItems);

                setListAdapter(adapter);
            }

            super.onPostExecute(jsonobject);
        }
    }

}
