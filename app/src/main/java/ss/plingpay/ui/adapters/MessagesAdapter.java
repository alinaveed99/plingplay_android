package ss.plingpay.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ss.plingpay.R;
import ss.plingpay.pojos.MessagesPOJO;

/**
 * Created by samar_000 on 9/9/2016.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {


    private ArrayList<MessagesPOJO> data;


    public MessagesAdapter(ArrayList<MessagesPOJO> data) {
        this.data = data;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list, parent, false);
        return new MessagesAdapter.MessagesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        public MessagesViewHolder(View itemView) {
            super(itemView);
        }
    }

}
