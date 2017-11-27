package ss.plingpay.ui.activities.mainView;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.data.TaskDataSource;
import ss.plingpay.pojos.currencylayer.CurrencyLayerPojo;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 7/14/2017.
 */

public class MainPresenter extends MvpBasePresenter<MainView> {


    private TaskDataSource repo;

    private CompositeDisposable disposable = new CompositeDisposable();

    MainPresenter(@NonNull TaskDataSource repo) {
        this.repo = repo;
    }

    void convertCurrency(String to, String from, String amount) {

        disposable.add(repo.currencyConverter(Utilz.AppConstants.CURRENCYLAYER_API_CODE,
                from, to, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();

                })
                .subscribeWith(new DisposableSingleObserver<CurrencyLayerPojo>() {
                    @Override
                    public void onSuccess(CurrencyLayerPojo currencyLayerPojo) {

                        if (currencyLayerPojo != null || currencyLayerPojo.getSuccess()) {


                            if (currencyLayerPojo.getResult() != null) {

                                if (isViewAttached())
                                    getView().onSuccessfullyCurrencyConverted(Utilz.removeComma(currencyLayerPojo.getResult().toString()));

                            } else {

                                if (isViewAttached())
                                    getView().onFailedConvertingCurrency();

                            }


                        } else {

                            if (isViewAttached())
                                getView().onFailedConvertingCurrency();

                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();

                        if (isViewAttached())
                            getView().onFailedConvertingCurrency();
                    }
                }));

    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance)
            disposable.clear();

    }
}
