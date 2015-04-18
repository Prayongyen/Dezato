package th.ac.buu.se.s55160077.s55160018.dezato;

/**
 * Created by prayong on 17/4/2558.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TableFragment extends Fragment implements AdapterView.OnItemClickListener {
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

        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "1"));
        mItems.add(new TableItem(resources.getDrawable(R.drawable.table_free), "2"));

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

        // start loading
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class AsyncTableGridLoader extends AsyncGridLoader {
        @Override
        protected void onProgressUpdate(TableItem... values) {

            // check that the fragment is still attached to activity
            if(mActivity != null) {
                // add the new item to the data set
                mItems.add(values[0]);

                // notify the adapter
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            // check that the fragment is still attached to activity
            if(mActivity != null) {
                Toast.makeText(mActivity, "55", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

