package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by prayong on 19/4/2558.
 */

public class ReserveFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReserveFragment newInstance(int sectionNumber) {
        ReserveFragment fragment = new ReserveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ReserveFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_reserve, container, false);
        final TextView pickTime = (TextView) rootView.findViewById(R.id.edtTime);
        pickTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(),TimePickerDialog.THEME_HOLO_LIGHT
                            , new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            if(selectedMinute == 0) {
                                pickTime.setText(selectedHour + ":" + selectedMinute+"0");
                            }
                            else
                            {
                                pickTime.setText(selectedHour + ":" + selectedMinute);
                            }
                        }

                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }

                return false;
            }
        });
        Button btnReserve = (Button) rootView.findViewById(R.id.btnReserve);
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtCustomer = (EditText) rootView.findViewById(R.id.edtCustomer);
                EditText edtTel = (EditText) rootView.findViewById(R.id.edtTel);
                EditText edtTime = (EditText) rootView.findViewById(R.id.edtTime);
                String txtCustomer = edtCustomer.getText().toString();
                String txtTel = edtTel.getText().toString();
                String txtTime = edtTime.getText().toString();
               if(TextUtils.isEmpty(txtCustomer) || TextUtils.isEmpty(txtTel) || TextUtils.isEmpty(txtTime) )
               {
                   Toast.makeText(getActivity(), "Not Empty Information",
                           Toast.LENGTH_SHORT).show();
               }
               else
               {
                   ReserveItem item = new ReserveItem();
                   item.setTxtCustomer(txtCustomer);
                   item.setTxtTel(txtTel);

                   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd "+txtTime+":ss");
                   Date date = new Date();
                   item.setTxtTime(dateFormat.format(date));
                   Log.d("Time",dateFormat.format(date));
                   new TableReserve().execute(item);
               }
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

    private class TableReserve extends AsyncTask<ReserveItem, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(ReserveItem... item) {

            SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
            String txtTableNo = sp.getString("txtTableNo", "");
            item[0].setTxtTableNo(txtTableNo);
            sp = getActivity().getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP", "");
            String url = "http://" + ip + "/rest_server/index.php/api/c_dz_table/tablereserve/id/" + txtTableNo + "/format/json";
            RestService re = new RestService();
            JSONObject jsonobject = re.putTableReserve(url,item[0]);
            return jsonobject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, TableFragment.newInstance(1))
                    .commit();
            try {
                Toast.makeText(getActivity(), "Reserve Table "+jsonObject.getString("txtTableNo"),
                        Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(jsonObject);
        }

    }
}
