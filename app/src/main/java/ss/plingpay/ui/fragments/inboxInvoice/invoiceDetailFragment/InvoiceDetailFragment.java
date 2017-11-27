package ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.data.transactionRepository.TransactionsInteractorImpl;
import ss.plingpay.pojos.Payment;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.activities.InboxActivity;
import ss.plingpay.ui.dialogFragments.ModifyInvoiceDialog;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/2/2016.
 */

public class InvoiceDetailFragment
        extends MvpFragment<InvoiceDetailView, InvoiceDetailPresenter>
        implements InvoiceDetailView {


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


    //Buttons
    @BindView(R.id.frag_detail_invoice_btnAccept)
    Button btnAccept;

    @BindView(R.id.frag_detail_invoice_btnModify)
    Button btnModify;

    @BindView(R.id.frag_detail_invoice_btnDeny)
    Button btnDeny;

    @BindView(R.id.goBack)
    Button btnBack;


    public static final String PAYMENT = "payment";
    private Unbinder unbinder;

    private Payment p;
    private PaymentPOJO pp;

    private ProgressDialog pd;
    private boolean disableRequest;

    @Inject
    TaskRepository taskRepository;

    @Override
    public InvoiceDetailPresenter createPresenter() {
        ((InboxActivity) getActivity()).getApp().getAppComponents().Inject(this);
        return new InvoiceDetailPresenter(new TransactionsInteractorImpl(), taskRepository);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getArguments().getParcelable(PAYMENT);
        pp = getArguments().getParcelable(Utilz.AppConstants.DATA);

        if (p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_CANCEL) ||
                p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_MODIFY_APPROVE) ||
                p.getStatus().equalsIgnoreCase(Utilz.AppConstants.INVOICE_APPROVE)) {

            disableRequest = true;
        }

        pd = Utilz.ProgressD(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detial_invoice, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        if (disableRequest) {
            btnBack.setVisibility(View.VISIBLE);
            btnAccept.setVisibility(View.GONE);
            btnDeny.setVisibility(View.GONE);
            btnModify.setVisibility(View.GONE);
        }

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



    }


    @OnClick(R.id.goBack)
    void goBack() {
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }


    @OnClick(R.id.frag_detail_invoice_btnAccept)
    void onAcceptClick() {

        Userinfo userinfo = ((InboxActivity) getActivity()).getApp().getAuth().getUser().getUserinfo();

        pd.show();
        presenter.startTransaction(userinfo.getCurrancyId(),
                pp.getReceiverInfo().getCurrancyId(), pp.getAmount(), pp, userinfo);


    }


    @OnClick(R.id.frag_detail_invoice_btnModify)
    void onModifyClick() {

        Bundle b = new Bundle();
        b.putParcelable(PAYMENT, p);
        DialogFragment dialog = new ModifyInvoiceDialog();
        dialog.setArguments(b);
        dialog.show(getActivity().getSupportFragmentManager(), ModifyInvoiceDialog.class.getName());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSuccessFullyRequestApproved() {
        Utilz.tmsgSuccess(getContext(), "Request Approved");
    }

    @Override
    public void onErrorApprovingRequest() {
        Utilz.tmsgSuccess(getContext(), "Error Approving Request");
    }

    @Override
    public void dismissDialog() {

        if (pd.isShowing())
            pd.dismiss();

    }

    @Override
    public void onSuccessFullyRequestRejected() {
        Utilz.tmsgSuccess(getContext(), "Request Rejected");
    }

    @Override
    public void onErrorRejectingRequest() {
        Utilz.tmsgError(getContext(), "Error Rejecting Request");
    }
}
