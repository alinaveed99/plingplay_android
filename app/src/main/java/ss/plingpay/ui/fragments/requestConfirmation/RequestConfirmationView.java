package ss.plingpay.ui.fragments.requestConfirmation;


import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Sammie on 5/31/2017.
 */

public interface RequestConfirmationView extends MvpView {
    void dismissDialog();

    void onFailedSendingRequest();

    void onSuccessfullyRequestSent();

    void onFailedConvertingCurrency();

    void onSuccessfullyCurrencyConverted(String btcAmount);
}
