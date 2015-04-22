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
 * Created by LUKHINNN on 22/04/2015.
 */
public class CheckBillAdapter extends BaseAdapter {

    private Context mContext;
    private List<FoodOrderItem> mItems;

    public CheckBillAdapter(Context context, List<FoodOrderItem> items){
        this.mContext= context;
        this.mItems = items;
    }

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
            convertView = inflater.inflate(R.layout.activity_check_bill_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.food_name = (TextView) convertView.findViewById(R.id.textViewFoodName);
            viewHolder.food_QTY = (TextView) convertView.findViewById(R.id.textViewFoodQTY);
            viewHolder.food_price = (TextView) convertView.findViewById(R.id.textViewFoodPrice);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        FoodOrderItem item = mItems.get(position);
        viewHolder.food_name.setText(item.getFood_name());
        viewHolder.food_QTY.setText(item.getFood_qty());
        viewHolder.food_price.setText(item.getFood_price());

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see //developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        TextView food_name;
        TextView food_QTY;
        TextView food_price;
    }
}
