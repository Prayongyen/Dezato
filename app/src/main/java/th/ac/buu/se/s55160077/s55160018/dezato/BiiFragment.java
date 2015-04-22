package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prayong on 19/4/2558.
 */
public class BiiFragment extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<BillItem> mItems = new ArrayList<BillItem>();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BiiFragment newInstance(int sectionNumber) {
        BiiFragment fragment = new BiiFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public BiiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new billbytable().execute();
        View rootView = inflater.inflate(R.layout.fragment_table_order, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.tableGridView);
        gridView.setOnItemClickListener(this);

        return rootView;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAddOrder : {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, OrderFragment.newInstance(1))
                        .addToBackStack("tag")
                        .commit();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        String txtTableNo = sp.getString("txtTableNo","");
        ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Bill Table "+txtTableNo);
        //ab.setSubtitle("Services");
        inflater.inflate(R.menu.menu_neworder, menu);
        super.onCreateOptionsMenu(menu, inflater);
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setMenuVisibility(false);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BillItem item = mItems.get(position);
        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bill_no",item.getOrder_no() );
        editor.commit();

        DialogFragmentwWithListFragment dialogFragment = DialogFragmentwWithListFragment.newInstance();
        dialogFragment.setRetainInstance(true);
        dialogFragment.show(getFragmentManager(), "DialogFragmentwWithListFragment");
    }
    private class billbytable extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            String txtTableNo = sp.getString("txtTableNo","");
            String order_no = sp.getString("order_no","");
            SharedPreferences sf = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sf.getString("IP","");
            String url = "http://"+ip+"/rest_server/index.php/api/c_dz_order/billbytable/format/json";

            RestService re = new RestService();
            JSONObject jsonobject =  re.getOrderbyTableBill(url,txtTableNo,order_no);

            return jsonobject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonobject) {
            SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            String txtTableNo = sp.getString("txtTableNo","");

            try {
                editor.putString("order_no", jsonobject.getString("bill"));
                int length = Integer.parseInt(jsonobject.getString("bill"));
                for(int i = 1 ;i<length;i++)
                {
                    BillItem billItem = new BillItem();
                    billItem.setOrder_no(String.valueOf(i));
                    billItem.setTable_id(txtTableNo);
                    mItems.add(billItem);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            editor.commit();

            // initialize the adapter
            BillItemAdapter  billItemAdapter = new BillItemAdapter(getActivity(), mItems);

            // initialize the GridView
            GridView gridView = (GridView) getActivity().findViewById(R.id.tableGridView);
            gridView.setAdapter(billItemAdapter);


            super.onPostExecute(jsonobject);
        }
    }


}
