package ss.plingpay.ui.activities.mainView;


import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by Sammie on 7/14/2017.
 */

public interface MainView extends MvpView {

    public void onFailedConvertingCurrency();

    void onSuccessfullyCurrencyConverted(String s);

    void dismissDialog();
}
