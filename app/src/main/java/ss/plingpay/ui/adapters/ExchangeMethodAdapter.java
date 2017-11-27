package ss.plingpay.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ss.plingpay.R;
import ss.plingpay.pojos.Exchange;

/**
 * Created by samar_000 on 10/19/2016.
 */

public class ExchangeMethodAdapter extends BaseAdapter {

    Activity context;
    List<String > names;

    public ExchangeMethodAdapter(Activity activity, List<String > names) {
        this.context = activity;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Override
    public String  getItem(int position) {
        try {

            return names.get(position);

        } catch (Exception e) {

            Log.e("ERROR", "getItem: ", e);

        }

        return "";
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


        currName.setText(names.get(position));

        return view;
    }
}


