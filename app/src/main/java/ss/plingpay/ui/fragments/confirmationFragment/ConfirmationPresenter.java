package ss.plingpay.ui.fragments.confirmationFragment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.text.DecimalFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.TestValues;
import ss.plingpay.data.TaskDataSource;
import ss.plingpay.data.transactionRepository.TransactionsInteractor;
import ss.plingpay.data.transactionRepository.TransactionsInteractorImpl;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.pojos.currencylayer.CurrencyLayerPojo;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 4/25/2017.
 */

public class ConfirmationPresenter extends MvpBasePresenter<ConfirmationView> {


    @NonNull
    private final TransactionsInteractor confirmationInteractor;
    private TaskDataSource repo;

    private CompositeDisposable disposable = new CompositeDisposable();

    private PaymentPOJO pp;

    ConfirmationPresenter(@NonNull TransactionsInteractor confirmationInteractor, @NonNull TaskDataSource repo) {
        this.confirmationInteractor = confirmationInteractor;
        this.repo = repo;
    }


    public void performTransaction(PaymentPOJO paymentPOJO,
                                   Userinfo userinfo,
                                   String isStandingOrder
    ) {

        this.pp = paymentPOJO;

        disposable.add(repo.performTransaction(userinfo.getId(), paymentPOJO.getReceiverInfo().getId(), userinfo.getCurrancyId(),
                paymentPOJO.getReceiverInfo().getCurrancyId(), paymentPOJO.getCurrency(), paymentPOJO.getAmount(), userinfo.getExchangeid(),
                paymentPOJO.getReceiverInfo().getExchangeid(), paymentPOJO.getCurr_id(), isStandingOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().onFinally();

                })
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        try {

                            JsonObject obj = jsonElement.getAsJsonObject();

                            String response = obj.get("response").getAsString();
                            String message = obj.get("message").getAsString();


                            if (response.equalsIgnoreCase("success")) {

                                if (isViewAttached())
                                    getView().onSuccessTransaction(message);

                            } else if (response.equalsIgnoreCase("failed")) {

                                if (isViewAttached())
                                    getView().onErrorTransaction();

                            } else {

                                if (isViewAttached())
                                    getView().onErrorTransaction();

                            }

                        } catch (Exception e) {


                            if (isViewAttached())
                                getView().onErrorTransaction();

                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {

                        if (isViewAttached())
                            getView().onErrorTransaction();

                    }
                }));

    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance) {
            disposable.clear();
        }
    }


}
