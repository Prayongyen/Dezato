package th.ac.buu.se.s55160077.s55160018.dezato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import th.ac.buu.se.s55160077.s55160018.dezato.R;

/**
 * Created by LUKHINNN on 18/04/2015.
 */
public class MenuAdapter extends BaseAdapter {
    Context mContext;
    String[] strName;
    int[] resId;

    public MenuAdapter(Context context, String[] strName, int[] resId){
        this.mContext= context;
        this.strName = strName;
        this.resId = resId;
    }

    public int getCount() {
        return strName.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.fragment_list_item, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.menuTitle);
        textView.setText(strName[position]);

        ImageView imageView = (ImageView)view.findViewById(R.id.menuIcon);
        imageView.setBackgroundResource(resId[position]);

        return view;
    }
}
