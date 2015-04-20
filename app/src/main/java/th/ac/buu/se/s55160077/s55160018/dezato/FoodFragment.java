package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by prayong on 20/4/2558.
 */
public class FoodFragment  extends ListFragment {
    String[] list_items;
    private static String status;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FoodFragment newInstance(int sectionNumber,String status) {
        FoodFragment.status = status;
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public FoodFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_foodlist, container, false);
        list_items = getResources().getStringArray(R.array.string_array);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list_items));

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("TEST", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Log.d("TEST", "onKey Back listener is working1!!!");
                    //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

}
