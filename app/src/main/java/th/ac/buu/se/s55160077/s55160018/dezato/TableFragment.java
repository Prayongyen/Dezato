package th.ac.buu.se.s55160077.s55160018.dezato;

/**
 * Created by prayong on 17/4/2558.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
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
        setHasOptionsMenu(true);
        // initialize the items list
        mItems = new ArrayList<TableItem>();
        new TableJson().execute("");
    }
    public void onClickActionBar(View v)
    {
        if (v.getId() == R.id.btnRefresh) {
            Toast.makeText(getActivity(), "you click on btnRefresh",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnRefresh : {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, TableFragment.newInstance(1))
                        .commit();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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

        if(item.getTxtTableStatus().equals("F"))
        {
            item.setTxtTableStatus("E");
            new TableUpdateEating().execute(item);
        }
        else if(item.getTxtTableStatus().equals("E"))
        {
            setTableShared(item.getTxtTableNo());
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, BiiFragment.newInstance(1))
                    .addToBackStack("tag")
                    .commit();
        }
        else
        {
            setTableShared(item.getTxtTableNo());
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ReserveInfoFragment.newInstance(position))
                    .addToBackStack( "tag" )
                    .commit();
        }

        //Toast.makeText(getActivity(), item.getTxtTableNo(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final TableItem item = mItems.get(position);
        // do something
        if(item.getTxtTableStatus().equals("F"))
        {
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            popupMenu.setOnMenuItemClickListener((new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.eating)
                    {
                        item.setTxtTableStatus("E");
                        new TableUpdateEating().execute(item);
                    }
                    else if(menuItem.getItemId() == R.id.reserve)
                    {
                        setTableShared(item.getTxtTableNo());
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, ReserveFragment.newInstance(position))
                                .addToBackStack("tag")
                                .commit();
                    }
                    return false;
                }
            }));
            popupMenu.inflate(R.menu.popuptablefree_menu);
            popupMenu.show();
        }
        else if(item.getTxtTableStatus().equals("E"))
        {
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            popupMenu.setOnMenuItemClickListener((new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.orderbill)
                    {
                        setTableShared(item.getTxtTableNo());
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, BiiFragment.newInstance(1))
                                .addToBackStack("tag")
                                .commit();
                    }
                    else if((menuItem.getItemId() == R.id.ceating))
                    {
                        item.setTxtTableStatus("F");
                        new TableUpdateFree().execute(item);
                    }
                    return false;
                }
            }));
            popupMenu.inflate(R.menu.popuptable_eating);
            popupMenu.show();
        }
        else
        {
            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            popupMenu.setOnMenuItemClickListener((new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId() == R.id.eating)
                    {
                        item.setTxtTableStatus("E");
                        new TableUpdateEating().execute(item);
                    }
                    else if(menuItem.getItemId() == R.id.creserve)
                    {
                        item.setTxtTableStatus("F");
                        new TableUpdateFree().execute(item);
                    }
                    return false;
                }
            }));
            popupMenu.inflate(R.menu.popuptablereserve);
            popupMenu.show();
        }


        //Toast.makeText(getActivity(), item.getTxtTableNo()+"HOLD", Toast.LENGTH_SHORT).show();
        return true;
    }

    public void setTableShared(String txtTableNo)
    {
        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("txtTableNo", txtTableNo);
        editor.commit();
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
    private class TableUpdateFree extends AsyncTask<TableItem, Integer, JSONObject> {


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
                    Toast.makeText(getActivity(), "Table "+ s.getString("txtTableNo") +" Cancel", Toast.LENGTH_SHORT).show();
                    //REFRESH
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, TableFragment.newInstance(1))
                            .commit();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
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
            SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String url = "http://"+ip+getString(R.string.show_table);
            Log.d("TEST",url);
            RestService re = new RestService();
            JSONObject jsonobject =  re.doGet(url);
            try {
                jsonarray = jsonobject.getJSONArray("tables");
                int lengthObj = jsonarray.length();
                for (int i = 0; i < lengthObj; i++) {
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
                            // To change body of catch statement use File | Settings | File Templates..
                            e.printStackTrace();
                        }

                    }
                    mItems.add(new TableItem(imgTable, txtTableNo,txtTableMessage,TableStatus));
                    int c = (i * 100 )/ lengthObj;
                    publishProgress(c);
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
            if(mItems.size() == 0)
            {
                GridView gridView = (GridView) getActivity().findViewById(R.id.tableGridView);
                gridView.setEmptyView( getActivity().findViewById( R.id.empty_grid_view ) );
            }
            pd.dismiss();
            super.onPostExecute(s);
        }
    }
}

