package ss.plingpay.ui.fragments.inboxInvoice.invoiceListFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.pojos.Payment;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.ui.activities.InboxActivity;
import ss.plingpay.ui.adapters.SingleInvoiceAdapter;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment.InvoiceDetailFragment;
import ss.plingpay.utilz.ActivityUtil;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/2/2016.
 */

public class InboxInvoicesFragment extends MvpFragment<InboxView, InboxInvoicePresenter>
        implements Listeners.InvoicesListener, InboxView {

    private Unbinder unbinder;

    @BindView(R.id.fragment_invoices_list)
    RecyclerView list;

    @Inject
    TaskRepository taskRepository;

    ProgressDialog pd;

    @Override
    public InboxInvoicePresenter createPresenter() {
        ((InboxActivity) getActivity()).getApp().getAppComponents().Inject(this);
        return new InboxInvoicePresenter(taskRepository);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pd = Utilz.ProgressD(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_invoices, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        pd.show();
        presenter.getInvoiceUserList(((InboxActivity) getActivity()).getApp().getAuth().getUser().getUserinfo().getId());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnClick(Payment payment) {
        presenter.onListItemClicked(payment);
    }


    @Override
    public void onSuccessfullyInvoiceFound(List<Payment> payments) {

        SingleInvoiceAdapter adapter = new SingleInvoiceAdapter(payments, InboxInvoicesFragment.this);
        list.setAdapter(adapter);

    }

    @Override
    public void onNoInvoiceFound() {

        Utilz.tmsgInfo(getContext(), "No Invoice Found");

    }

    @Override
    public void onErrorGettingList() {

        Utilz.tmsgError(getContext(), "Error");

    }

    @Override
    public void dismissDialog() {

        if (pd.isShowing())
            pd.dismiss();

    }

    @Override
    public void openDetailFragment(PaymentPOJO paymentPOJO, Payment payment) {
        Bundle b = new Bundle();
        b.putParcelable(InvoiceDetailFragment.PAYMENT, payment);
        b.putParcelable(Utilz.AppConstants.DATA, paymentPOJO);
        ActivityUtil.openFragment(getFragmentManager(), R.id.activity_inbox_container, new InvoiceDetailFragment(),
                InvoiceDetailFragment.class.getName(), true, b);
    }
}

