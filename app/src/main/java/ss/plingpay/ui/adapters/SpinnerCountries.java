package ss.plingpay.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import io.realm.RealmResults;
import ss.plingpay.R;
import ss.plingpay.pojos.CountriesList;


/**
 * Created by samar_000 on 8/11/2016.
 */

public class SpinnerCountries extends BaseAdapter {

    Activity context;
    RealmResults<CountriesList> currencies;

    public SpinnerCountries(Activity activity, RealmResults<CountriesList> currencies) {
        this.context = activity;
        this.currencies = currencies;
    }

    @Override
    public int getCount() {
        return currencies.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, parent);
    }

    @Override
    public CountriesList getItem(int position) {
        return currencies.get(position);
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

        CountriesList b = currencies.get(position);

        currName.setText(b.getCountryName());

        return view;
    }
}
