package ss.plingpay.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.pojos.Mylog;

public class TransactionDetailsActivity extends BaseActivity {

    @BindView(R.id.frag_trans_detail_payment)
    TextView tvPayment;
    @BindView(R.id.frag_trans_detail_RecName)
    TextView tvRecName;
    @BindView(R.id.frag_trans_detail_Amount)
    TextView tvAmount;
    @BindView(R.id.frag_trans_detail_Status)
    TextView tvStatus;
    @BindView(R.id.frag_trans_detail_Note)
    TextView tvNote;


    public static final String LOG_DATA = "log";
    private Mylog data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trans_detail);
        ButterKnife.bind(this);
        data = getIntent().getExtras().getParcelable(LOG_DATA);
        init();
    }

    private void init() {

        getToolbar().setNavigationIcon(R.drawable.ic_cross);
        getToolbar().setNavigationOnClickListener(view -> finish());

        tvAmount.setText((data.getAmount() == null) ? "" : data.getAmount());
        tvRecName.setText((data.getReceivername() == null) ? "" : data.getReceivername());
        tvPayment.setText((data.getType() == null) ? "" : data.getType());
        tvStatus.setText("Success");
        tvNote.setText((data.getMessage() == null) ? "" : data.getMessage());
    }
}
