package th.ac.buu.se.s55160077.s55160018.dezato;

import android.content.Context;
import android.content.SharedPreferences;
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
public class DialogBillOrderAdapter extends BaseAdapter {

    Context mContext;
    List<FoodOrderItem> mItem;
    ImageLoader imageLoader;
    OrderItem orderItem;
    int foodQty;

    public DialogBillOrderAdapter(Context context, List<FoodOrderItem> item) {
        this.mContext = context;
        this.mItem = item;
        imageLoader = new ImageLoader(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sp = mContext.getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
        final String ip = sp.getString("IP", "");

        ViewHolder viewHolder;
        final FoodOrderItem item = mItem.get(position);
        if (convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.fragment_food_order_popup, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.food_name = (TextView) convertView.findViewById(R.id.food_name);
            viewHolder.food_qty = (TextView) convertView.findViewById(R.id.food_qty);
            viewHolder.imgFood = (ImageView) convertView.findViewById(R.id.food_path);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view

        viewHolder.food_name.setText(item.getFood_name());
        viewHolder.food_qty.setText(item.getFood_qty());
        ImageView food_path = (ImageView) convertView.findViewById(R.id.food_path);
        imageLoader.DisplayImage("http://" + ip + "/dezatoshop/foodimage/" + item.getFood_path(), food_path);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgFood;
        TextView food_name;
        TextView food_qty;
    }

}
