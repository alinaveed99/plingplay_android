package ss.plingpay.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ss.plingpay.R;
import ss.plingpay.pojos.Address;

/**
 * Created by samar_000 on 8/11/2016.
 */

public class CustomSpinnerAdapter extends BaseAdapter {

    Activity context;
    List<Address> address;

    public CustomSpinnerAdapter(Activity activity, List<Address> address) {
        this.context = activity;
        this.address = address;
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, parent);
    }

    @Override
    public Address getItem(int position) {
        return address.get(position);
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

        Address b = address.get(position);

        currName.setText(b.getAddress());

        return view;
    }
}
