package ss.plingpay.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.pojos.Payment;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment.InvoiceDetailFragment;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/12/2016.
 */

public class OutboxInvoiceDetail extends BaseFragment {


    private static final String TAG = InvoiceDetailFragment.class.getName().toUpperCase();
    @BindView(R.id.frag_detail_invoice_tvAmount)
    TextView tvAmount;
    @BindView(R.id.frag_detail_invoice_tvBtc)
    TextView tvBtc;
    @BindView(R.id.frag_detail_invoice_tvCurrency)
    TextView tvCurrency;
    @BindView(R.id.frag_detail_invoice_tvDetails)
    TextView tvDetails;
    @BindView(R.id.frag_detail_invoice_tvName)
    TextView tvName;
    @BindView(R.id.frag_detail_invoice_tvSubject)
    TextView tvSubject;
    @BindView(R.id.frag_detail_invoice_tvStatus)
    TextView tvStatus;


    @BindView(R.id.goBack)
    Button btnBack;


    public static final String PAYMENT = "payment";
    private Unbinder unbinder;

    private Payment p;

    private ProgressDialog dialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setNavFragment(false);
        super.onCreate(savedInstanceState);
        p = getArguments().getParcelable(PAYMENT);
        setTitle("Invoices");



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_outbox_detial_invoice, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {



        tvAmount.setText((p.getAmount() == null) ? "" : p.getAmount());
        tvBtc.setText((p.getAmountinbtc() == null) ? "" : p.getAmountinbtc());
        tvCurrency.setText((p.getCurrancyvalue() == null) ? "" : p.getCurrancyvalue());
        tvDetails.setText((p.getDescription() == null) ? "" : p.getDescription());
        tvSubject.setText((p.getRequest() == null) ? "" : p.getRequest());


        if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_PENDING)) {
            tvStatus.setText("Pending");
        } else if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_APPROVE)) {
            tvStatus.setText("Approved");
        } else if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_MODIFY_APPROVE)) {
            tvStatus.setText("Modified and Approved");
        } else if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_CANCEL)) {
            tvStatus.setText("Canceled");
        }

        String firtname = (p.getRequester().getFirstName() == null) ? "" : p.getRequester().getFirstName();
        String lastName = (p.getRequester().getLastName() == null) ? "" : p.getRequester().getLastName();

        tvName.setText(firtname + " " + lastName);

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading...");


    }


    @OnClick(R.id.goBack)
    void goBack() {
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
