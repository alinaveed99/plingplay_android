package ss.plingpay.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import ss.plingpay.R;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.utilz.Listeners;

/**
 * Created by Sammie on 8/23/2017.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CurrencyViewHolder> {

    private RealmResults<CountriesList> data;
    private Listeners.CountryListener listener;

    public CountryAdapter(RealmResults<CountriesList> data, Listeners.CountryListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public CountryAdapter.CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_country, parent, false);
        return new CountryAdapter.CurrencyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CountryAdapter.CurrencyViewHolder holder, int position) {
        holder.name.setText(data.get(position).getCountryName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_country_name)
        TextView name;

        CurrencyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnClick(data.get(getLayoutPosition()).getCountryName(), data.get(getLayoutPosition()).getId());
        }
    }
}
