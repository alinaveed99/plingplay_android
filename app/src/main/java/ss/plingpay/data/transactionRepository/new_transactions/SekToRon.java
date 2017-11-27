package ss.plingpay.data.transactionRepository.new_transactions;

import android.util.Log;

import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.fragments.confirmationFragment.Services.ConfirmationFragServices;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 4/24/2017.
 */
@Deprecated

public class SekToRon {

    private static final String TAG = SekToRon.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;
    private TransactionListener listener;
    private String ronAmount;
    private String euroAmount;
    private String btcAmount;


    public SekToRon(PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.pp = pp;
        this.senderInfo = senderInfo;
        this.listener = listener;
        apis = new ConfirmationFragServices();


        //1 step convert sek to euro
        apis.converter("SEK", "BTC", Utilz.removeComma(pp.getAmount()), new ConfirmationFragServices.CallBacks.CallBack() {
            @Override
            public void onSuccess(String response) {

                //2 step convert euro to btc using kraken
                btcAmount = Utilz.removeComma(response);
                convertToEuro();


            }

            @Override
            public void onFailed() {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");
            }

        });


    }

    void convertToEuro() {

        apis.converter("SEK", "EUR", Utilz.removeComma(pp.getAmount()), new ConfirmationFragServices.CallBacks.CallBack() {
            @Override
            public void onSuccess(String response) {

                //2 step convert euro to btc using kraken
                euroAmount = Utilz.removeComma(response);
                convertToRon();


            }

            @Override
            public void onFailed() {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");
            }

        });

    }

    void convertToRon() {

        apis.converter("SEK", "RON", Utilz.removeComma(pp.getAmount()), new ConfirmationFragServices.CallBacks.CallBack() {
            @Override
            public void onSuccess(String response) {

                //2 step convert euro to btc using kraken
                ronAmount = Utilz.removeComma(response);
                addToLog();


            }

            @Override
            public void onFailed() {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");
            }

        });

    }


    private void addToLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(), "nil", "nil",
                euroAmount, ronAmount, btcAmount, pp.getCurr_id(), pp.getAmount(), "", ronAmount, "", "1",
                new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;
                        listener.onSuccess(ronAmount, "", "");


                    }

                    @Override
                    public void onFailed() {
                        listener.onFailed("");
                    }
                });
    }


}
