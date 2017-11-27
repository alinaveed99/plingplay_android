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
import ss.plingpay.pojos.Bitcoindetail;


/**
 * Created by samar_000 on 6/13/2016.
 */
public class WalletAdapter extends BaseAdapter {

    Activity context;
    List<Bitcoindetail> bitcoindetails;

    public WalletAdapter(Activity activity, List<Bitcoindetail> bitcoindetails) {
        this.context = activity;
        this.bitcoindetails = bitcoindetails;
    }

    @Override
    public int getCount() {
        return bitcoindetails.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, parent);
    }

    @Override
    public Bitcoindetail getItem(int position) {
        return bitcoindetails.get(position);
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
        View view = inflater.inflate(R.layout.spinner_wallet_guids, parent, false);

        TextView lblGUID = (TextView) view.findViewById(R.id.spinner_wallet_guid);
        TextView lblLabel = (TextView) view.findViewById(R.id.spinner_wallet_label);

        Bitcoindetail b = bitcoindetails.get(position);

        lblGUID.setText(b.getGuid());
        lblLabel.setText(b.getUserLabel());


        return view;
    }
}
