package ss.plingpay.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.R;
import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by samar_000 on 6/22/2016.
 */
public class FavContactListAdapter extends RecyclerView.Adapter<FavContactListAdapter.FavContactListViewHolder> {

    private Context context;
    private ArrayList<UserDetails> users;
    private Listeners.FavContactListener listener;

    public FavContactListAdapter(Context context, ArrayList<UserDetails> users, Listeners.FavContactListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @Override
    public FavContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_contact_list, parent, false);
        return new FavContactListViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(FavContactListViewHolder holder, int position) {
        holder.name.setText(users.get(position).getFname() + " " + users.get(position).getLname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }



    class FavContactListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.fav_contact_list_tvName)
        TextView name;


        private Listeners.FavContactListener listener;

        public FavContactListViewHolder(View itemView, Listeners.FavContactListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            UserDetails user = users.get(getLayoutPosition());
            listener.onItemClick(user);
        }


        @OnClick(R.id.fav_contact_list_moreInfo)
        void onInfoClick() {
            UserDetails user = users.get(getLayoutPosition());
            listener.OnInfoClick(user);
        }

    }


}