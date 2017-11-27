package ss.plingpay.ui.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import ss.plingpay.R;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.utilz.Listeners;

/**
 * Created by samar_000 on 9/22/2016.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {


    private RealmResults<CurrencyList> data;
    private AppCompatActivity activity;
    private Listeners.CurrencyListener listener;


    public CurrencyAdapter(RealmResults<CurrencyList> data, AppCompatActivity activity, Listeners.CurrencyListener listener) {
        this.data = data;
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_currency, parent, false);
        return new CurrencyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {

        holder.name.setText(data.get(position).getCurrancyName());


        switch (data.get(position).getCurrancyName()) {
            case "AUD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.aud));
                holder.nametext.setText("Australian Dollar");
                break;

            case "BRL":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.brl));
                holder.nametext.setText("Brazilian Real");
                break;

            case "BTC":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.btc));
                holder.nametext.setText("Bitcoin");
                break;

            case "CAD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.cad));
                holder.nametext.setText("Canadian Dollar");
                break;

            case "CHF":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.chf));
                holder.nametext.setText("Swiss Franc");
                break;

            case "CLP":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.clp));
                holder.nametext.setText("Chilean Peso");
                break;

            case "CNY":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.cny));
                holder.nametext.setText("Chinese Yen");
                break;

            case "DKK":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.dkk));
                holder.nametext.setText("Danish Krona");
                break;

            case "EUR":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.eur));
                holder.nametext.setText("Euro");
                break;

            case "GBP":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.gbp));
                holder.nametext.setText("British Pound");
                break;

            case "HKD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.hkd));
                holder.nametext.setText("Hong Kong Dollar");
                break;

            case "ISK":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.isk));
                holder.nametext.setText("Icelandic Krona");
                break;

            case "PKR":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pkr));
                holder.nametext.setText("Pakistani Rupee");
                break;


            case "JPY":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.jpy));
                holder.nametext.setText("Japanese Yen");
                break;

            case "KRW":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.krw));
                holder.nametext.setText("South Korean Won");
                break;

            case "NZD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.nzd));
                holder.nametext.setText("New Zealand Dollar");
                break;

            case "PLN":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pln));
                holder.nametext.setText("Polish Zioty");
                break;

            case "RUB":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.rub));
                holder.nametext.setText("Russian Ruble");
                break;

            case "SEK":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.sek));
                holder.nametext.setText("Swedish Krona");
                break;

            case "SGD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.sgd));
                holder.nametext.setText("Singapore Dollar");
                break;

            case "THB":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.thb));
                holder.nametext.setText("Thai Baht");
                break;


            case "TWD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.twd));
                holder.nametext.setText("New Taiwan Dollar");
                break;


            case "USD":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.usd));
                holder.nametext.setText("Us Dollar");
                break;

            case "VND":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.dong));
                holder.nametext.setText("Vietnamese Dong");
                break;

            case "RON":
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.leu));
                holder.nametext.setText("Romanian leu");
                break;


            default:
                holder.icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon));
                holder.nametext.setText("Not Found");
                break;
        }
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_currency_icon)
        ImageView icon;
        @BindView(R.id.list_currency_name)
        TextView name;
        @BindView(R.id.list_currency_nametext)
        TextView nametext;


        private Listeners.CurrencyListener listener;

        public CurrencyViewHolder(View itemView, Listeners.CurrencyListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnClick(data.get(getLayoutPosition()).getCurrancyName(), data.get(getLayoutPosition()).getId());
        }
    }
}
