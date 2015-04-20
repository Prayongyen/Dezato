package th.ac.buu.se.s55160077.s55160018.dezato;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LUKHINNN on 19/04/2015.
 */

public class IncomeListAdapter extends BaseAdapter {
    Context mContext;
    List<IncomeItem> mItem;

    public IncomeListAdapter(Context context, List<IncomeItem> item){
        this.mContext= context;
        this.mItem = item;
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TEST",  String.valueOf(position));
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.fragment_income_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.textViewDate);
            viewHolder.txtMoney = (TextView) convertView.findViewById(R.id.textViewMoney);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        IncomeItem item = mItem.get(position);
        viewHolder.txtDate.setText(item.getTxtDate());
        viewHolder.txtMoney.setText(item.getTxtMoney());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtDate;
        TextView txtMoney;
    }
}
