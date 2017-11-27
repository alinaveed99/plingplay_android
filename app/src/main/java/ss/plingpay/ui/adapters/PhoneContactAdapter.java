package ss.plingpay.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.R;
import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by samar_000 on 8/24/2016.
 */

public class PhoneContactAdapter extends RecyclerView.Adapter<PhoneContactAdapter.PhoneContactListViewHolder> {

    private Context context;
    private Listeners.FavContactListener listener;
    List<UserDetails> result;


    public PhoneContactAdapter(Context context, List<UserDetails> result, Listeners.FavContactListener listener) {
        this.context = context;
        this.result = result;
        this.listener = listener;
    }

    @Override
    public PhoneContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_contact_list, parent, false);
        return new PhoneContactListViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(PhoneContactListViewHolder holder, int position) {
        holder.name.setText(result.get(position).getFname() + " " + result.get(position).getLname());

    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public class PhoneContactListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.fav_contact_list_tvName)
        TextView name;


        private Listeners.FavContactListener listener;

        public PhoneContactListViewHolder(View itemView, Listeners.FavContactListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            UserDetails user = result.get(getLayoutPosition());
            listener.onItemClick(user);
        }


        @OnClick(R.id.fav_contact_list_moreInfo)
        void onInfoClick() {
            UserDetails user = result.get(getLayoutPosition());
            listener.OnInfoClick(user);
        }


    }
}
