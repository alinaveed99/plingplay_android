package ss.plingpay.ui.fragments.inboxInvoice.invoiceListFragment;


import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.data.TaskDataSource;
import ss.plingpay.pojos.Invoices;
import ss.plingpay.pojos.Payment;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by Sammie on 6/1/2017.
 */

public class InboxInvoicePresenter extends MvpBasePresenter<InboxView> {


    private final TaskDataSource repo;


    public InboxInvoicePresenter(TaskDataSource repo) {
        this.repo = repo;
    }


    void getInvoiceUserList(String userId) {


        repo.getUserInvoices("authtoken", userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();


                })
                .subscribeWith(new DisposableSingleObserver<Invoices>() {
                    @Override
                    public void onSuccess(Invoices invoices) {

                        if (invoices != null && invoices.getResponse().equalsIgnoreCase("valid token")) {

                            if (invoices.getPayments().size() > 0) {

                                if (isViewAttached())
                                    getView().onSuccessfullyInvoiceFound(invoices.getPayments());

                            } else {

                                if (isViewAttached())
                                    getView().onNoInvoiceFound();

                            }

                        } else {

                            if (isViewAttached())
                                getView().onErrorGettingList();

                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();


                        if (isViewAttached())
                            getView().onErrorGettingList();

                    }
                });


    }


    public void onListItemClicked(Payment payment) {


        PaymentPOJO pp = new PaymentPOJO();

        UserDetails receiverInfo = new UserDetails();
        receiverInfo.setId(payment.getRequester().getId());
        receiverInfo.setExchangeid(payment.getExchangeId());
        receiverInfo.setCurrancyId(payment.getCurrancyvalue());

        pp.setReceiverInfo(receiverInfo);
        pp.setAmount(payment.getAmount());

        if (isViewAttached())
            getView().openDetailFragment(pp, payment);
    }
}
