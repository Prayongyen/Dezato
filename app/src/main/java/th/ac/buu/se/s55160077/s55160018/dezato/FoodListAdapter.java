package th.ac.buu.se.s55160077.s55160018.dezato;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by prayong on 20/4/2558.
 */
public class FoodListAdapter extends BaseAdapter {
    Context mContext;
    List<FoodItem> mItem;
    ImageLoader imageLoader;
    OrderItem orderItem;
    int foodQty;

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
        SharedPreferences sp = mContext.getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
        final String ip = sp.getString("IP","");

        ViewHolder viewHolder;
        final FoodItem item = mItem.get(position);
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
        imageLoader.DisplayImage("http://"+ip+"/dezatoshop/foodimage/"+item.getFood_path(), food_path);
        food_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = mContext.getSharedPreferences("IMG", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("PATH", "http://"+ip+"/dezatoshop/foodimage/"+item.getFood_path());
                editor.commit();

                final Intent mainIntent = new Intent(mContext, ImageActivity.class);
                mContext.startActivity(mainIntent);
            }
        });

        final Button addOrder = (Button) convertView.findViewById(R.id.addOrder);
        addOrder.setTag(position); //For passing the list item index
        final View finalConvertView = convertView;

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(mContext);
//                dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                dialog.setContentView(R.layout.dialog_seekbar);
//                dialog.setTitle("เลือกจำนวน");
//                dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.logo_small);
                dialog.setCancelable(true);

                //there are a lot of settings, for dialog, check them all out!
                dialog.show();
                final SeekBar seekbar = (SeekBar) dialog.findViewById(R.id.size_seekbar);
                final ImageView foodImg = (ImageView) dialog.findViewById(R.id.foodImg);
                imageLoader.DisplayImage("http://"+ip+"/dezatoshop/foodimage/"+item.getFood_path(), foodImg);
                final TextView count = (TextView) dialog.findViewById(R.id.count);
                final ImageButton imageButtonLeft = (ImageButton) dialog.findViewById(R.id.imageButtonLeft);
                imageButtonLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seekbar.incrementProgressBy(-1);
                    }
                });
                final ImageButton imageButtonRight = (ImageButton) dialog.findViewById(R.id.imageButtonRight);
                imageButtonRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seekbar.incrementProgressBy(1);
                    }
                });
                final Button conbtn = (Button) dialog.findViewById(R.id.conbtn);
                conbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(foodQty == 0)
                        {
                            Toast.makeText(mContext,"Order Can't Be Zero" , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            SharedPreferences sp = mContext.getSharedPreferences("TABLE_INFO", Context.MODE_PRIVATE);
                            String txtTableNo = sp.getString("txtTableNo","");
                            String order_no = sp.getString("order_no","");
                            orderItem = new OrderItem();
                            orderItem.setFood_id(item.getFood_id());
                            orderItem.setOrder_no(order_no);
                            orderItem.setOrder_qty(String.valueOf(foodQty));
                            orderItem.setTable_id(txtTableNo);
                            new AddFoodOrder().execute(orderItem);
                            dialog.dismiss();
                        }
                    }
                });
                count.setText(item.getFood_name()+" : 0 Order");
                seekbar.setMax(20);
                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean b) {
                        foodQty = progresValue;
                        count.setText(item.getFood_name()+" : " +String.valueOf(progresValue)+" Order");
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
    private class AddFoodOrder extends AsyncTask<OrderItem, Integer, JSONObject> {
        @Override
        protected JSONObject doInBackground(OrderItem... params) {

            SharedPreferences sp = mContext.getSharedPreferences("IP_USERNAME", Context.MODE_PRIVATE);
            String ip = sp.getString("IP","");
            String url = "http://"+ip+"/dezatoshop/rest/index.php/api/c_dz_order/order/format/json";
            RestService re = new RestService();
            JSONObject jsonobject =  re.AddFoodOrder(url,params[0]);

            return jsonobject;
        }


        @Override
        protected void onPostExecute(JSONObject jsonobject) {
            try {
                if(jsonobject.getString("message").equals("OK"))
                    Toast.makeText(mContext,"Add Order Success" , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mContext,"Add Order Fail" , Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(jsonobject);
        }
    }

}
