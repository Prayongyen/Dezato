package th.ac.buu.se.s55160077.s55160018.dezato;

/**
 * Created by prayong on 17/4/2558.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
public class TableFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_navigation, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.tableGridView);
        //gridView.setAdapter(new MyAdapter);


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainNavigation) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

