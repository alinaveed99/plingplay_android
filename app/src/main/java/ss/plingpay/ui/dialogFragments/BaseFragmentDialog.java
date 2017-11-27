package ss.plingpay.ui.dialogFragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import io.realm.Realm;
import retrofit2.Retrofit;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/16/2016.
 */

public class BaseFragmentDialog extends DialogFragment {

    protected WebServices api;

    private PlingPay app;
    protected Realm realm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((MainActivity) getActivity()).getApp();
        realm = Realm.getDefaultInstance();


    }


    protected PlingPay getApp() {
        return app;
    }


    void openFragment(@IdRes int ResId, Fragment fragment, String tag) {
        getFragmentManager().beginTransaction().replace(ResId, fragment, tag).commit();
    }

    void openFragment(@IdRes int ResId, Fragment fragment, String tag, Bundle b) {
        fragment.setArguments(b);
        openFragment(ResId, fragment, tag);
    }


    Retrofit getRetrofit(String url) {
        return Utilz.getRetrofit(url);
    }


    public boolean validateField(EditText et) {
        if (et.getText().toString() == null && et.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

}
