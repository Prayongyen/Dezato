package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

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
        View rootView = inflater.inflate(R.layout.fragment_reserve, container, false);
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
}
