package ss.plingpay.ui.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import retrofit2.Retrofit;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.ui.activities.BaseActivity;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/15/2016.
 */

public class BaseFragment extends Fragment {

    protected WebServices api;

    private PlingPay app;

    private Toolbar toolbar;
    private ActionBar actionBar;

    private boolean isNavFragment = true;


    private String Title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((BaseActivity) getActivity()).getApp();

        FragmentInit();
    }

    private void FragmentInit() {
        toolbar = ((BaseActivity) getActivity()).getToolbar();
        actionBar = ((BaseActivity) getActivity()).getSupportedActionbar();

    }

    private void setFragmentTitle() {

        if (getFragmentActionBar() == null)
            return;

        if (getTitle() == null) {
            setTitle("No Title");
            getFragmentActionBar().setTitle(getTitle());
        } else {
            getFragmentActionBar().setTitle(getTitle());
        }
    }


    protected PlingPay getApp() {
        return app;
    }


    protected void openFragment(@IdRes int ResId, Fragment fragment, String tag, boolean addToBackStack) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(ResId, fragment, tag);

        if (addToBackStack)
            ft.addToBackStack(null);

        ft.commit();
    }


    protected void openFragment(@IdRes int ResId, Fragment fragment, String tag, boolean addToBackStack, Bundle b) {
        fragment.setArguments(b);
        openFragment(ResId, fragment, tag, addToBackStack);
    }


    protected Retrofit getRetrofit(String url) {
        return Utilz.getRetrofit(url);
    }


    private String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


    public Toolbar getFragmentToolbar() {
        return toolbar;
    }

    public ActionBar getFragmentActionBar() {
        return actionBar;
    }


    @Override
    public void onResume() {
        super.onResume();
        setFragmentTitle();
    }


}
