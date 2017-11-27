package ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment;


import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Sammie on 6/1/2017.
 */

public interface InvoiceDetailView extends MvpView {
    void onSuccessFullyRequestApproved();

    void onErrorApprovingRequest();

    void dismissDialog();

    void onSuccessFullyRequestRejected();

    void onErrorRejectingRequest();
}
