package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * Created by prayong on 19/4/2558.
 */
public class OrderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static OrderFragment newInstance(int sectionNumber) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public OrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        String txtTableNo = sp.getString("txtTableNo","");
        ActionBar ab = getActivity().getActionBar();
        ab.setTitle("Add Order Table "+txtTableNo);
        //ab.setSubtitle("Services");
        inflater.inflate(R.menu.menu_accept, menu);
//        menu.findItem(R.id.navigation_drawer).setEnabled(false);
        super.onCreateOptionsMenu(menu, inflater);
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setMenuVisibility(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAccept : {
                getFragmentManager().popBackStack();

                Toast.makeText(getActivity(), "สร้างรายการสำเร็จ", Toast.LENGTH_SHORT).show();
                new addBill().execute();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);
        SectionsPagerAdapter mSectionsPagerAdapter;
        ViewPager mViewPager;
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.Foodpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        return rootView;
    }
    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }


    /**
     * A {@link android.support.v13.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0)
                return FoodOrderListFragment.newInstance(position);
            else if(position == 1)
                return FoodFragment.newInstance(position,"F");
            else if(position == 2)
                return DessertFragment.newInstance(position,"D");
            else
                return DrinkFragment.newInstance(position,"W");


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "ORDER";
                case 1:
                    return getString(R.string.title_food1).toUpperCase(l);
                case 2:
                    return getString(R.string.title_food2).toUpperCase(l);
                case 3:
                    return getString(R.string.title_food3).toUpperCase(l);
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    private class addBill extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {

            SharedPreferences sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String name = sp.getString("USERNAME","");
            SharedPreferences sd = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            String txtTableNo = sd.getString("txtTableNo","");
            String order_no = sd.getString("order_no","");
            String url = "http://"+ip+"/rest_server/index.php/api/c_dz_order/updatebill/table_id/"+txtTableNo+"/order_no/"+order_no+"/format/json";
Log.d("HELLO",url);
            RestService re = new RestService();
            re.doGet(url);
            return null;
        }
    }
}
