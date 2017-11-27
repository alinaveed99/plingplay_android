package ss.plingpay.ui.fragments.confirmationFragment;


import com.hannesdorfmann.mosby3.mvp.MvpView;

import ss.plingpay.TestValues;

/**
 * Created by Sammie on 4/25/2017.
 */

public interface ConfirmationView extends MvpView {

    void onSuccessTransaction(String a);

    void onErrorTransaction();

    void onFinally();

}
