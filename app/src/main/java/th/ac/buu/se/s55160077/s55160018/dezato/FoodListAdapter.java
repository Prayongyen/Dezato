package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by prayong on 20/4/2558.
 */
public class FoodListAdapter extends BaseAdapter {
    Context mContext;
    List<FoodItem> mItem;
    ImageLoader imageLoader;

    public FoodListAdapter(Context context, List<FoodItem> item){
        this.mContext= context;
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

        ViewHolder viewHolder;
        FoodItem item = mItem.get(position);
        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.fragment_food_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.food_name = (TextView) convertView.findViewById(R.id.food_name);
            viewHolder.food_price = (TextView) convertView.findViewById(R.id.food_price);
            viewHolder.imgFood = (ImageView) convertView.findViewById(R.id.food_path);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view

        viewHolder.food_name.setText(item.getFood_name());
        viewHolder.food_price.setText(item.getFood_price());
        ImageView food_path = (ImageView) convertView.findViewById(R.id.food_path);
        imageLoader.DisplayImage("http://10.103.1.6/foodimage/"+item.getFood_path(), food_path);

        final Button addOrder = (Button) convertView.findViewById(R.id.addOrder);
        addOrder.setTag(position); //For passing the list item index
        final View finalConvertView = convertView;
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                dialog.setContentView(R.layout.dialog_seekbar);
                dialog.setTitle("เลือกจำนวน");
                dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.logo_small);
                dialog.setCancelable(true);
                //there are a lot of settings, for dialog, check them all out!
                dialog.show();
                SeekBar seekbar = (SeekBar) dialog.findViewById(R.id.size_seekbar);
                final TextView count = (TextView) dialog.findViewById(R.id.count);
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean b) {
                        count.setText(String.valueOf(progresValue));

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });
        return convertView;
    }
    private static class ViewHolder {
        ImageView imgFood;
        TextView food_name;
        TextView food_price;
    }

}
