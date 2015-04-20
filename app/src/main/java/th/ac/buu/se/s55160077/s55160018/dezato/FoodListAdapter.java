package th.ac.buu.se.s55160077.s55160018.dezato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by prayong on 20/4/2558.
 */
public class FoodListAdapter extends BaseAdapter {
    Context mContext;
    List<FoodItem> mItem;
    //ImageLoader imageLoader;

    public FoodListAdapter(Context context, List<FoodItem> item){
        this.mContext= context;
        this.mItem = item;
        //imageLoader = new ImageLoader(context);
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

        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.fragment_food_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.food_name = (TextView) convertView.findViewById(R.id.food_name);
            viewHolder.food_price = (TextView) convertView.findViewById(R.id.food_price);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        FoodItem item = mItem.get(position);
        viewHolder.food_name.setText(item.getFood_name());
        viewHolder.food_price.setText(item.getFood_price());
        return convertView;
    }
    private static class ViewHolder {
        ImageView imgFood;
        TextView food_name;
        TextView food_price;
    }

}
