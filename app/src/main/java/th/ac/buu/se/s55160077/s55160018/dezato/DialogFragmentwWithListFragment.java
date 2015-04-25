package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by prayong on 21/4/2558.
 */
public class DialogFragmentwWithListFragment extends DialogFragment {
    @Override
    public void onDestroy(){
        super.onDestroy();
        FragmentTransaction ft2 = getActivity().getFragmentManager()
                .beginTransaction();

        ft2.remove( getFragmentManager()
                .findFragmentById(R.id.flContent));
        ft2.commit();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (getDialog() != null && getDialog().isShowing()) {
            getDialog().dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_with_list_fragment, container);

        SharedPreferences sp = getActivity().getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
        String order_no = sp.getString("bill_no", "");
        String txtTableNo = sp.getString("txtTableNo","");
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setCancelable(true);
        getDialog().setTitle("Table "+txtTableNo +" Bill "+order_no);
        return rootView;
    }

    public static DialogFragmentwWithListFragment newInstance() {
        DialogFragmentwWithListFragment fragment = new DialogFragmentwWithListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
