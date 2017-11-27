package ss.plingpay.ui.activities.splashScreenView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import io.realm.Realm;
import ss.plingpay.R;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.services.ContactService;
import ss.plingpay.ui.activities.WelcomeActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 7/20/2017.
 */

public class NewSplashScreenActivity extends MvpActivity<SplashScreenView, SplashScreenPresenter> implements SplashScreenView {


    private static final String TAG = NewSplashScreenActivity.class.getSimpleName().toUpperCase();
    @Inject
    TaskRepository taskRepository;

    private PlingPay appApplication;


    @NonNull
    @Override
    public SplashScreenPresenter createPresenter() {
        ((PlingPay) getApplication()).getAppComponents().Inject(this);
        RxPermissions rxPermissions = new RxPermissions(this);
        Realm realm = Realm.getDefaultInstance();
        return new SplashScreenPresenter(taskRepository, rxPermissions, realm);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appApplication = (PlingPay) getApplication();
        init();

    }

    private void init() {

        presenter.getAllCurrencies();


    }

   private void _stopService() {
       stopService(new Intent(this, ContactService.class));
   }

   private void _startService() {
       startService(new Intent(this, ContactService.class));
   }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void onErrorGettingCurrencies() {
        finish();
        Utilz.tmsgError(this, "Error");
        logMessage("Error getting Currencies");
    }

    @Override
    public void onErrorGettingCountries() {
        finish();
        Utilz.tmsgError(this, "Error");
        logMessage("Error getting Currencies");
    }

    @Override
    public void onSuccessfulCurrenciesAdded() {
        presenter.getCountries();
    }

    @Override
    public void onSuccessfulCountriesAdded() {
        presenter.getContactPermission();
    }

    @Override
    public void onContactPermissionGranted() {
        _startService();
        openWelcomeActivity();
    }

    @Override
    public void onContactPermissionNotGranted() {
        openWelcomeActivity();
    }


    public PlingPay getApp() {
        return appApplication;
    }

    private void openWelcomeActivity() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    private void logMessage(String msg) {
        Log.d(TAG, "logMessage: " + msg);
    }
}
