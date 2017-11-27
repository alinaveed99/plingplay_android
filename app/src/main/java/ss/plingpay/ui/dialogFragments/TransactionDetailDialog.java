package ss.plingpay.ui.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.pojos.Mylog;

/**
 * Created by samar_000 on 9/6/2016.
 */

public class TransactionDetailDialog extends DialogFragment {


    private Unbinder unbinder;
    public static final String LOG_DATA = "log";

    private Mylog data;

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
    @BindView(R.id.titleText)
    TextView tvTitle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getParcelable(LOG_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trans_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {



        tvAmount.setText((data.getAmount() == null) ? "" : data.getAmount());
        tvRecName.setText((data.getReceivername() == null) ? "" : data.getReceivername());
        tvPayment.setText((data.getType() == null) ? "" : data.getType());
        tvStatus.setText("Success");
        tvNote.setText((data.getMessage() == null) ? "" : data.getMessage());
        tvTitle.setText(data.getDate());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        this.dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
