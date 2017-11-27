package ss.plingpay.ui.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.pojos.Address;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/31/2016.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder>{


    private Activity context;
    protected List<Address> address;
    private Listeners.AddressListListener listener;

    public AddressListAdapter(Activity activity, List<Address> address, Listeners.AddressListListener listener) {
        this.context = activity;
        this.address = address;
        this.listener = listener;
    }



    @Override
    public AddressListAdapter.AddressListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addr_list, parent, false);
        return new AddressListAdapter.AddressListViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(AddressListAdapter.AddressListViewHolder holder, int position) {

        holder.tvAddress.setText(address.get(position).getAddress());
        holder.tvFinalBalance.setText( Utilz.satoshiToBtc(address.get(position).getBalance()) + " btc");


    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class AddressListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.addr_list_address)
        TextView tvAddress;
        @BindView(R.id.addr_list_finalBal)
        TextView tvFinalBalance;

        private Listeners.AddressListListener Listener;

        public AddressListViewHolder(View itemView, Listeners.AddressListListener Listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.Listener = Listener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Listener.OnClick(address.get(getLayoutPosition()).getAddress());
        }
    }
}