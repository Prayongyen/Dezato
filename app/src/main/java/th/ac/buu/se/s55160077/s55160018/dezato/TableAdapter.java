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
 * Created by prayong on 18/4/2558.
 */
public class TableAdapter extends BaseAdapter {
    private Context mContext;
    private List<TableItem> mItems;

    public TableAdapter(Context context, List<TableItem> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.table_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.imgTable = (ImageView) convertView.findViewById(R.id.imgTable);
            viewHolder.txtTableNo = (TextView) convertView.findViewById(R.id.txtTableNo);
            viewHolder.txtTableMessage = (TextView) convertView.findViewById(R.id.txtTableMessage);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        TableItem item = mItems.get(position);
        viewHolder.imgTable.setImageDrawable(item.getImgTable());
        viewHolder.txtTableNo.setText(item.getTxtTableNo());
        viewHolder.txtTableMessage.setText(item.getTxtTableMessage());

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see //developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        ImageView imgTable;
        TextView txtTableNo;
        TextView txtTableMessage;
    }
}
