package ss.plingpay.ui.activities.splashScreenView;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by Sammie on 7/20/2017.
 */

public interface SplashScreenView extends MvpView{
    void dismissDialog();

    void onErrorGettingCurrencies();

    void onErrorGettingCountries();

    void onSuccessfulCurrenciesAdded();

    void onSuccessfulCountriesAdded();

    void onContactPermissionGranted();

    void onContactPermissionNotGranted();

}
