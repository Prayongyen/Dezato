package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by prayong on 18/4/2558.
 */
public class AccountFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AccountFragment newInstance(int sectionNumber) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
        TextView editIP = (TextView) rootView.findViewById(R.id.textViewUsName);

        editIP.setText(sp.getString("USERNAME","staff"));

        new RewardJson().execute("");

        return rootView;
    }

    private class RewardJson extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {

            SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String name = sp.getString("USERNAME","");
            String url = "http://"+ip+"/dezatoshop/rest/index.php/api/c_dz_user_reward/sumreward/id/"+name+"/format/json";
            RestService re = new RestService();
            JSONObject jsonobject =  re.doGet(url);


            return jsonobject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonobject) {
            try {
                JSONArray jsonarray;
                jsonarray = jsonobject.getJSONArray("reward");
                jsonobject = jsonarray.getJSONObject(0);
                String sumrewardString = jsonobject.getString("sumreward");
                String rewardString = jsonobject.getString("sumtoday");

                TextView sumreward = (TextView) getActivity().findViewById(R.id.textViewSumreward);
                TextView reward = (TextView) getActivity().findViewById(R.id.textViewRewardToDay);

                sumreward.setText(sumrewardString);
                reward.setText(rewardString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(jsonobject);
        }
    }
}
