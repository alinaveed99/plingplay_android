package ss.plingpay.ui.activities.splashScreenView;

import android.Manifest;
import android.content.ContentResolver;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import ss.plingpay.data.TaskDataSource;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.pojos.CurrencyPOJO.CurrenciesPOJO;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.pojos.CurrencyPOJO.ExchangeList;

/**
 * Created by Sammie on 7/20/2017.
 */

public class SplashScreenPresenter extends MvpBasePresenter<SplashScreenView> {

    private TaskDataSource repo;
    private CompositeDisposable disposable = new CompositeDisposable();
    private RxPermissions rxPermissions;
    private Realm realm;

    SplashScreenPresenter(@NonNull TaskDataSource repo,
                          @NonNull RxPermissions rxPermissions, @NonNull Realm realm) {

        this.repo = repo;
        this.rxPermissions = rxPermissions;
        this.realm = realm;

    }

    void getAllCurrencies() {

        disposable.add(repo.getAllCurrencies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();

                })
                .subscribeWith(new DisposableSingleObserver<CurrenciesPOJO>() {
                    @Override
                    public void onSuccess(CurrenciesPOJO currenciesPOJO) {

                        realm.beginTransaction();
                        realm.delete(CurrencyList.class);
                        realm.commitTransaction();

                        for (int i = 0; i < currenciesPOJO.getCurrencyList().size(); i++) {
                            realm.beginTransaction();
                            CurrencyList curr = realm.createObject(CurrencyList.class);
                            curr.setCurrancyName(currenciesPOJO.getCurrencyList().get(i).getCurrancyName());
                            curr.setId(currenciesPOJO.getCurrencyList().get(i).getId());
                            realm.commitTransaction();
                        }

                        realm.beginTransaction();
                        realm.delete(ExchangeList.class);
                        realm.commitTransaction();

                        for (int i = 0; i < currenciesPOJO.getExchangeList().size(); i++) {
                            realm.beginTransaction();
                            ExchangeList curr = realm.createObject(ExchangeList.class);
                            curr.setId(currenciesPOJO.getExchangeList().get(i).getId());
                            curr.setCurrancyId(currenciesPOJO.getExchangeList().get(i).getCurrancyId());
                            curr.setTitle(currenciesPOJO.getExchangeList().get(i).getTitle());
                            curr.setUrl(currenciesPOJO.getExchangeList().get(i).getUrl());
                            realm.commitTransaction();
                        }


                        if (isViewAttached())
                            getView().onSuccessfulCurrenciesAdded();


                    }

                    @Override
                    public void onError(Throwable throwable) {

                        throwable.printStackTrace();

                        if (isViewAttached())
                            getView().onErrorGettingCurrencies();


                    }
                }));

    }

    void getCountries() {

        disposable.add(repo.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();

                })
                .subscribeWith(new DisposableSingleObserver<List<CountriesList>>() {
                    @Override
                    public void onSuccess(List<CountriesList> countriesLists) {

                        realm.beginTransaction();
                        realm.delete(CountriesList.class);
                        realm.commitTransaction();

                        for (int i = 0; i < countriesLists.size(); i++) {
                            realm.beginTransaction();
                            CountriesList curr = realm.createObject(CountriesList.class);
                            curr.setCountryName(countriesLists.get(i).getCountryName());
                            curr.setId(countriesLists.get(i).getId());
                            realm.commitTransaction();
                        }


                        if (isViewAttached())
                            getView().onSuccessfulCountriesAdded();


                    }

                    @Override
                    public void onError(Throwable throwable) {

                        throwable.printStackTrace();

                        if (isViewAttached())
                            getView().onErrorGettingCountries();

                    }
                }));

    }

    void getContactPermission() {
        rxPermissions
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe(granted -> {

                    if (granted) {

                        if (isViewAttached())
                            getView().onContactPermissionGranted();

                    } else {

                        if (isViewAttached())
                            getView().onContactPermissionNotGranted();

                    }

                });

    }



    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance)
            disposable.clear();
    }
}
