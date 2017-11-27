package ss.plingpay.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Invoices;
import ss.plingpay.pojos.Payment;
import ss.plingpay.ui.adapters.SingleInvoiceOutboxAdapter;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment.InvoiceDetailFragment;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/12/2016.
 */

public class OutboxInvoicesFragment extends BaseFragment implements Listeners.InvoicesListener {

    private Unbinder unbinder;

    @BindView(R.id.fragment_invoices_list)
    RecyclerView list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setNavFragment(false);
        super.onCreate(savedInstanceState);
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

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog pd = Utilz.ProgressD(getActivity());
        pd.show();


        api.getOutboxRequest(getApp().getAuth().getUser().getUserinfo().getId()).enqueue(new Callback<Invoices>() {
            @Override
            public void onResponse(Call<Invoices> call, Response<Invoices> response) {

                if (response.isSuccessful()) {

                    if (response.body().getPayments().size() > 0) {


                        SingleInvoiceOutboxAdapter adapter = new SingleInvoiceOutboxAdapter(response.body().getPayments(),
                                OutboxInvoicesFragment.this);
                        list.setAdapter(adapter);

                    } else {

                    }

                } else {

                }

                pd.dismiss();

            }

            @Override
            public void onFailure(Call<Invoices> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnClick(Payment Payment) {

        Bundle b = new Bundle();
        b.putParcelable(InvoiceDetailFragment.PAYMENT, Payment);

        openFragment(R.id.activity_outbox_container, new OutboxInvoiceDetail(), OutboxInvoiceDetail.class.getName(), true, b);

    }
}