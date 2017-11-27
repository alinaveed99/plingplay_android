package ss.plingpay.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.pojos.Payment;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/12/2016.
 */

public class SingleInvoiceOutboxAdapter extends RecyclerView.Adapter<SingleInvoiceOutboxAdapter.SingleInvoiceVh>{


    private List<Payment> data;
    private Listeners.InvoicesListener listener;
    private String id;


    public SingleInvoiceOutboxAdapter(List<Payment> data, Listeners.InvoicesListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public SingleInvoiceOutboxAdapter.SingleInvoiceVh onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_invoices, parent, false);
        return new SingleInvoiceOutboxAdapter.SingleInvoiceVh(v, listener);
    }

    @Override
    public void onBindViewHolder(SingleInvoiceOutboxAdapter.SingleInvoiceVh holder, int position) {

        Payment p = data.get(position);

            holder.tvName.setText(p.getRequester().getFirstName() + " " + p.getRequester().getLastName());
            holder.tvAmount.setText(p.getAmount() + " " + p.getCurrancyvalue());
            holder.tvNote.setText(p.getRequest());
            holder.tvTimeStamp.setText(p.getRequesteddate());



        if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_PENDING)) {
            holder.tvStatus.setText("Pending");
        } else if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_APPROVE)) {
            holder.tvStatus.setText("Approved");
        } else if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_MODIFY_APPROVE)) {
            holder.tvStatus.setText("Modified and Approved");
        } else if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_CANCEL)) {
            holder.tvStatus.setText("Canceled");
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SingleInvoiceVh extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.list_invoices_tvName)
        TextView tvName;
        @BindView(R.id.list_invoices_tvTimeStamp)
        TextView tvTimeStamp;
        @BindView(R.id.list_invoices_tvNote)
        TextView tvNote;
        @BindView(R.id.list_invoices_tvAmount)
        TextView tvAmount;
        @BindView(R.id.list_invoices_tvStatus)
        TextView tvStatus;


        private Listeners.InvoicesListener listener;

        public SingleInvoiceVh(View itemView, Listeners.InvoicesListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Payment Payment = data.get(getLayoutPosition());
            listener.OnClick(Payment);
        }
    }
}
