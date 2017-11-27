package ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.TestValues;
import ss.plingpay.data.TaskDataSource;
import ss.plingpay.data.transactionRepository.TransactionsInteractor;
import ss.plingpay.data.transactionRepository.TransactionsInteractorImpl;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;

/**
 * Created by Sammie on 6/1/2017.
 */

public class InvoiceDetailPresenter extends MvpBasePresenter<InvoiceDetailView>
        implements TransactionsInteractorImpl.ConfirmationBLListener {

    private final TransactionsInteractor TransactionRepo;
    private TaskDataSource taskRepo;

    public InvoiceDetailPresenter(TransactionsInteractor TransactionRepo, TaskDataSource taskRepo) {
        this.TransactionRepo = TransactionRepo;
        this.taskRepo = taskRepo;
    }


    void startTransaction(String senderCurrency, String receiverCurrency, String enteredAmount, PaymentPOJO paymentPojo,
                          Userinfo userinfo) {
        TransactionRepo.startTransaction(senderCurrency, receiverCurrency, enteredAmount,
                paymentPojo, userinfo, this);
    }


    @Override
    public void onSuccess(@NonNull String val1, @NonNull String val2, @NonNull String val3) {

    }

    @Override
    public void onFailed(@NonNull String errorMessage) {

    }

    @Override
    public void onServiceDown() {

    }

    @Override
    public void Test(@NonNull TestValues v) {

    }


    void approveRequest(String requestId) {
        taskRepo.approveRequest(requestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();

                })
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        JsonObject obj = jsonElement.getAsJsonObject();
                        String status = obj.get("response").getAsString();

                        if (status.equalsIgnoreCase("success")) {

                            if (isViewAttached())
                                getView().onSuccessFullyRequestApproved();


                        } else {

                            if (isViewAttached())
                                getView().onErrorApprovingRequest();

                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        throwable.printStackTrace();

                        if (isViewAttached())
                            getView().onErrorApprovingRequest();

                    }
                });
    }

    void rejectRequest(String requestId) {
        taskRepo.rejectRequest(requestId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();

                })
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        JsonObject obj = jsonElement.getAsJsonObject();
                        String status = obj.get("response").getAsString();

                        if (status.equalsIgnoreCase("success")) {

                            if (isViewAttached())
                                getView().onSuccessFullyRequestRejected();


                        } else {

                            if (isViewAttached())
                                getView().onErrorRejectingRequest();

                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {

                        throwable.printStackTrace();

                        if (isViewAttached())
                            getView().onErrorRejectingRequest();

                    }
                });
    }


}
