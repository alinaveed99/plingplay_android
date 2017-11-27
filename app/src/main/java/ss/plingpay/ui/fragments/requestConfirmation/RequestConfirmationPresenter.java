package ss.plingpay.ui.fragments.requestConfirmation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.data.TaskDataSource;
import ss.plingpay.pojos.currencylayer.CurrencyLayerPojo;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 5/31/2017.
 */

public class RequestConfirmationPresenter extends MvpBasePresenter<RequestConfirmationView> {

    private final TaskDataSource repo;


    public RequestConfirmationPresenter(TaskDataSource repo) {
        this.repo = repo;
    }


    void sendPaymentRequest(String authToken, String senderId, String receiverId, String amount, String currency,
                            String request, String description, String default_address, String btcAmount) {

        repo.sendPaymentRequest(authToken, senderId, receiverId, amount,
                currency, request, description, default_address, btcAmount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                    if (isViewAttached())
                        getView().dismissDialog();

                })
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {

                        JsonObject obj = jsonElement.getAsJsonObject();
                        String status = obj.get("response").getAsString();

                        if (status.equalsIgnoreCase("success")) {

                            if (isViewAttached())
                                getView().onSuccessfullyRequestSent();

                        } else {
                            if (isViewAttached())
                                getView().onFailedSendingRequest();
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {

                        throwable.printStackTrace();


                        if (isViewAttached())
                            getView().onFailedSendingRequest();


                    }
                });
    }


    void convertToBtc(String amount, String currency) {
        repo.currencyConverter(Utilz.AppConstants.CURRENCYLAYER_API_CODE,
                currency, "BTC", amount)
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


                            if(currencyLayerPojo.getResult() != null) {

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
                });

    }




}
