package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by prayong on 19/4/2558.
 */
public class ReserveInfoFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReserveInfoFragment newInstance(int sectionNumber) {
        ReserveInfoFragment fragment = new ReserveInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ReserveInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        new TableJson().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reserveinfo, container, false);
        Button button = (Button) rootView.findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
                String txtTableNo = sp.getString("txtTableNo","");
                TableItem item = new TableItem();
                item.setTxtTableStatus("E");
                item.setTxtTableNo(txtTableNo);
                new TableUpdateEating().execute(item);
            }
        });
        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        String txtTableNo = sp.getString("txtTableNo","");
        ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Table "+txtTableNo);
        //ab.setSubtitle("Services");
        //inflater.inflate(R.menu.menu_back, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private class TableJson extends AsyncTask<String, Integer, JSONObject> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("Loading...");
            pd.setMessage("Loading Table...");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.setMax(100);
            pd.setProgress(0);
            pd.show();
            super.onPreExecute();

        }
        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            String txtTableNo = sp.getString("txtTableNo", "");
            sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP", "");
            String url = "http://" + ip + "/rest_server/index.php/api/c_dz_table/table/id/" + txtTableNo + "/format/json";
            RestService re = new RestService();
            JSONObject jsonobject = re.doGet(url);
            publishProgress(100);
            return jsonobject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            try {
                JSONArray jsonarray;
                JSONObject jsonObj;
                jsonarray = jsonObject.getJSONArray("table");
                jsonObj = jsonarray.getJSONObject(0);
                String res_cusname = jsonObj.getString("res_cusname");
                String res_cusphone = jsonObj.getString("res_cusphone");
                //String res_custime = jsonObj.getString("res_time");
                String Time = jsonObj.getString("res_time");

                DateFormat df = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
                try {
                    Date date = null;
                    date = df.parse(Time);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Time = sdf.format(date);

                } catch (ParseException e) {
                    // To change body of catch statement use File | Settings | File Templates..
                    e.printStackTrace();
                }
                TextView txtres_cusname = (TextView) getActivity().findViewById(R.id.res_cusname);
                txtres_cusname.setText(res_cusname);
                TextView txtres_cusphone = (TextView) getActivity().findViewById(R.id.res_cusphone);
                txtres_cusphone.setText(res_cusphone);
                TextView txtres_custime = (TextView) getActivity().findViewById(R.id.res_custime);
                txtres_custime.setText(Time);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            pd.dismiss();
            super.onPostExecute(jsonObject);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            pd.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }

    private class TableUpdateEating extends AsyncTask<TableItem, Integer, JSONObject> {


        @Override
        protected JSONObject doInBackground(TableItem... tableItems) {
            SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String url = "http://"+ip+getString(R.string.update_table);
            RestService re = new RestService();
            JSONObject resultJson = re.putTableEating(url,tableItems[0]);
            return resultJson;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            try {

                if(s.getString("message").equals("OK"))
                {
                    Toast.makeText(getActivity(), "Table "+ s.getString("txtTableNo") +" Eating", Toast.LENGTH_SHORT).show();
                    //REFRESH
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, TableFragment.newInstance(1))
                            .commit();
                    //CALL ORDER
                    setTableShared(s.getString("txtTableNo"));
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, BiiFragment.newInstance(1))
                            .addToBackStack("tag")
                            .commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    public void setTableShared(String txtTableNo)
    {
        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("txtTableNo", txtTableNo);
        editor.commit();
    }
}
