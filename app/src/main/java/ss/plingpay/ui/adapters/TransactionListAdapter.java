package ss.plingpay.ui.adapters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.pojos.Mylog;
import ss.plingpay.ui.activities.TransactionDetailsActivity;
import ss.plingpay.ui.dialogFragments.TransactionDetailDialog;
import ss.plingpay.utilz.Utilz;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransListViewHolder> {

    private List<Mylog> logs;
    private AppCompatActivity context;

    public TransactionListAdapter(AppCompatActivity context, List<Mylog> logs) {
        this.logs = logs;
        this.context = context;
    }

    @Override
    public TransListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_list, parent, false);
        return new TransListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransListViewHolder holder, int position) {

        Mylog log = logs.get(position);

        holder.tvName.setText(log.getReceivername());

        DecimalFormat precision = new DecimalFormat("#.##");

        String am = precision.format(Double.parseDouble(log.getAmount()));


//        String asd = precision.format(Double.parseDouble("51.1412321325"));
//        Log.d("TAG", "onBindViewHolder: " + asd);


        if (log.getCurrancyId().equalsIgnoreCase("6")) {
            holder.tvAmount.setText(am + " EURO");
        } else {
            holder.tvAmount.setText(am + " " + log.getCurrancyId());
        }


        String getDate = getDateText(log.getDate());
        holder.tvTime.setText(getDate);


        if (log.getType().equalsIgnoreCase(Utilz.AppConstants.TYPE_NORMAL_TRANSACTION)) {
            holder.tvType.setText("Payment");
        } else if (log.getType().equalsIgnoreCase(Utilz.AppConstants.TYPE_INVOICE_TRANSACTION)) {
            holder.tvType.setText("Invoice");
        }


    }

    private String getDateText(String date) {

        String month = "";
        String[] dateArray = date.split("-");


        switch (dateArray[1]) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }


        return dateArray[0] + " " + month + " " + dateArray[2];
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class TransListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trans_list_timeStamp)
        TextView tvTime;
        @BindView(R.id.trans_list_Name)
        TextView tvName;
        @BindView(R.id.trans_list_type)
        TextView tvType;
        @BindView(R.id.trans_list_amount)
        TextView tvAmount;


        public TransListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent i = new Intent(context, TransactionDetailsActivity.class);
            i.putExtra(TransactionDetailDialog.LOG_DATA, logs.get(getLayoutPosition()));
            context.startActivity(i);


        }
    }

}
