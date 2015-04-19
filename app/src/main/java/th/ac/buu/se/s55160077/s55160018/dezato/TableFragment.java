package th.ac.buu.se.s55160077.s55160018.dezato;

/**
 * Created by prayong on 17/4/2558.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TableFragment extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    private List<TableItem> mItems;        // GridView items list
    private TableAdapter mAdapter;        // GridView adapter
    private Activity mActivity;                // the parent activity
    private AsyncTableGridLoader mLoader;    // the application info loader
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TableFragment newInstance(int sectionNumber) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TableFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the items list
        mItems = new ArrayList<TableItem>();
        Resources resources = getResources();
        new TableJson().execute("");


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_table, container, false);

        // initialize the adapter
        mAdapter = new TableAdapter(getActivity(), mItems);

        // initialize the GridView
        GridView gridView = (GridView) rootView.findViewById(R.id.tableGridView);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);

        //start loading
       mLoader = new AsyncTableGridLoader();
        mLoader.execute(mActivity.getPackageManager());


        return rootView;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        ((MainNavigation) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TableItem item = mItems.get(position);
        // do something
        Toast.makeText(getActivity(), item.getTxtTableNo(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TableItem item = mItems.get(position);
        // do something
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, ReserveFragment.newInstance(position + 1))
                .commit();
        Toast.makeText(getActivity(), item.getTxtTableNo()+"HOLD", Toast.LENGTH_SHORT).show();
        return true;
    }


    private class AsyncTableGridLoader extends AsyncGridLoader {
        @Override
        protected void onProgressUpdate(TableItem... values) {

            // check that the fragment is still attached to activity
            if(mActivity != null) {
                // add the new item to the data set
                //mItems.add(values[0]);

                // notify the adapter
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            // check that the fragment is still attached to activity
            if(mActivity != null) {
                //Toast.makeText(mActivity, "55", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class TableJson extends AsyncTask<String, Integer, String> {
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
        protected String doInBackground(String... params) {
            Drawable imgTable;
            String txtTableMessage = null;
            JSONArray jsonarray;
            Resources resources = getResources();
            String url = "http://10.103.1.12/rest_server/index.php/api/c_dz_table/tables/format/json";
            RestService re = new RestService();
            JSONObject jsonobject =  re.doGet(url);
            try {
                jsonarray = jsonobject.getJSONArray("tables");
                int lengthObj = jsonarray.length();
                for (int i = 0; i < lengthObj; i++) {
                    int c = (i * 100 )/ lengthObj;
                    publishProgress(c);
                    jsonobject = jsonarray.getJSONObject(i);
                    String TableStatus = jsonobject.getString("table_status");
                    String txtTableNo = jsonobject.getString("table_no");

                    if(TableStatus.equals("F"))
                    {
                        imgTable = resources.getDrawable(R.drawable.table_free);
                        txtTableMessage = "FREE";
                    }
                    else if(TableStatus.equals("E"))
                    {
                        imgTable = resources.getDrawable(R.drawable.table_eating);
                        txtTableMessage = "EAT";
                    }
                    else
                    {
                        imgTable = resources.getDrawable(R.drawable.table_reserved);
                        String Time = jsonobject.getString("res_time");

                        DateFormat df = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
                        try {
                            Date date = null;
                            date = df.parse(Time);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            txtTableMessage = sdf.format(date);

                        } catch (ParseException e) {
                            // To change body of catch statement use File | Settings | File Templates.
                            e.printStackTrace();
                        }

                    }
                    mItems.add(new TableItem(imgTable, txtTableNo,txtTableMessage));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pd.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            super.onPostExecute(s);
        }
    }
}

