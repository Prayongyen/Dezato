package th.ac.buu.se.s55160077.s55160018.dezato;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by LUKHINNN on 22/04/2015.
 */
public class CheckBillAdapter extends BaseAdapter {

    Context mContext;
    String[] foodName;
    String[] QTY;
    String[] price;

    public CheckBillAdapter(Context context, String[] foodName, String[] QTY, String[] price){
        this.mContext= context;
        this.foodName = foodName;
        this.QTY = QTY;
        this.price = price;
    }

    public int getCount() {
        return this.foodName.length;
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
            view = mInflater.inflate(R.layout.activity_check_bill_item, parent, false);

        TextView textViewOrder = (TextView)view.findViewById(R.id.textViewFoodName);
        textViewOrder.setText(foodName[position]);

        TextView textViewQTY = (TextView)view.findViewById(R.id.textViewFoodQTY);
        textViewQTY.setText(QTY[position]);

        TextView textViewPrice = (TextView)view.findViewById(R.id.textViewFoodPrice);
        textViewPrice.setText(price[position]);
        return view;
    }
}
