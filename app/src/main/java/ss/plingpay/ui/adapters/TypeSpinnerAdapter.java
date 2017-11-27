package ss.plingpay.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.realm.RealmResults;
import ss.plingpay.R;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/23/2016.
 */

public class TypeSpinnerAdapter extends BaseAdapter {

    Activity context;
    String[]  data;

    public TypeSpinnerAdapter(Activity activity, String[]  data) {
        this.context = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, parent);
    }



    @Override
    public String getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    private View getCustomView(int position, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_currency_list, parent, false);

        TextView currName = (TextView) view.findViewById(R.id.spinner_currency_list_name);

        if (Utilz.PAYMENT_METHOD[position].equalsIgnoreCase(Utilz.PAYMENT_METHOD[2]) ||
                Utilz.PAYMENT_METHOD[position].equalsIgnoreCase(Utilz.PAYMENT_METHOD[3]) ||
                Utilz.PAYMENT_METHOD[position].equalsIgnoreCase(Utilz.PAYMENT_METHOD[4])) {

            currName.setTextColor(context.getResources().getColor(R.color.md_grey_400));


        } else {
//            currName.setTextColor();
            currName.setTextColor(context.getResources().getColor(R.color.colorTextBlack));
        }





        String  b = data[position];

        currName.setText(b);

        return view;
    }
}

