package ss.plingpay.ui.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import io.realm.Realm;
import retrofit2.Retrofit;
import ss.plingpay.R;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 7/14/2017.
 */

public abstract class BaseMvpActivity<v extends MvpView, p extends MvpPresenter<v>> extends MvpActivity<v, p> {

    protected WebServices api;
    private PlingPay appApplication;

    protected Toolbar toolbar;
    protected Realm realm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appApplication = (PlingPay) getApplication();
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public p createPresenter() {
        return gifPresenter();
    }

    abstract public p gifPresenter();

    public void openFragment(@IdRes int ResId, Fragment fragment, String tag, boolean addToBackStack) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(ResId, fragment, tag);

        if (addToBackStack)
            ft.addToBackStack(null);

        ft.commit();
    }


    public void openFragment(@IdRes int ResId, Fragment fragment, String tag, boolean addToBackStack, Bundle b) {
        fragment.setArguments(b);
        openFragment(ResId, fragment, tag, addToBackStack);
    }


    void InitializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        if (toolbar != null)
            setSupportActionBar(toolbar);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        InitializeToolbar();
    }


    Retrofit getRetrofit(String url) {
        return Utilz.getRetrofit(url);
    }


    public PlingPay getApp() {
        return appApplication;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ActionBar getSupportedActionbar() {
        return getSupportActionBar();
    }


}

