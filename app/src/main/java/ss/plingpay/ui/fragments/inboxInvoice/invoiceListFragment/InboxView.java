package ss.plingpay.ui.fragments.inboxInvoice.invoiceListFragment;


import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ss.plingpay.pojos.Payment;
import ss.plingpay.pojos.PaymentPOJO;

/**
 * Created by Sammie on 6/1/2017.
 */

public interface InboxView extends MvpView {
    void onSuccessfullyInvoiceFound(List<Payment> payments);

    void onNoInvoiceFound();

    void onErrorGettingList();

    void dismissDialog();

    void openDetailFragment(PaymentPOJO paymentPOJO, Payment payment);
}
