package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by prayong on 18/4/2558.
 */
public class IncomeFragment extends ListFragment implements AdapterView.OnItemClickListener{
    private List<IncomeItem> mItems = new ArrayList<IncomeItem>();      // GridView items list
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static IncomeFragment newInstance(int sectionNumber) {
        IncomeFragment fragment = new IncomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public IncomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        new RewardJson().execute("");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_income, container, false);

//        IncomeListAdapter adapter = new IncomeListAdapter(getActivity(), mItems);
//        ListView listView = (ListView) rootView.findViewById(R.id.listViewIncome);
//        listView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    private class RewardJson extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {

            SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String name = sp.getString("USERNAME","");
            String url = "http://"+ip+"/rest_server/index.php/api/c_dz_user_reward/rewardlist/id/"+name+"/format/json";

            RestService re = new RestService();
            JSONObject jsonobject =  re.doGet(url);

            return jsonobject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonobject) {
            try {

                JSONArray jsonarray;
                jsonarray = jsonobject.getJSONArray("reward");
                int lengthObj = jsonarray.length();

                for (int i = 0; i < lengthObj; i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    String day = jsonobject.getString("date");
                    String moneyy = jsonobject.getString("money");
                    mItems.add(new IncomeItem(day,moneyy));
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

            }
            else
            {
                IncomeListAdapter adapter = new IncomeListAdapter(getActivity(), mItems);
                setListAdapter(adapter);
            }

            super.onPostExecute(jsonobject);
        }
    }
}
