package ss.plingpay.data.transactionRepository.new_transactions;

import android.util.Log;

import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.fragments.confirmationFragment.Services.ConfirmationFragServices;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 4/25/2017.
 */
@Deprecated

public class EuroToSek {

    private static final String TAG = SekToRon.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;
    private TransactionListener listener;
    private String euroAmount;
    private String btcAmount;
    private String sekAmount;
    private String withdrawalAmount;


    public EuroToSek(String euroAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.euroAmount = Utilz.removeComma(euroAmount);
        this.pp = pp;
        this.senderInfo = senderInfo;
        this.listener = listener;

        listener.onFailed("");


//        convertEuroToBtc(euroAmount);


    }

    private void convertEuroToBtc(String euroAmount) {
        apis.newEuroToBtc(Utilz.removeComma(euroAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {
                btcAmount = Thc.findAab(val1);
                convertBtcToSek(btcAmount);

            }

            @Override
            public void onFailed(String error) {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));

            }

            @Override
            public void onServiceDown() {

            }
        });

    }


    //there is no btc to ron service so i cant find withdrawal amount through formula
    private void convertBtcToSek(String btcAmount) {



    }


    private void addTokrakenLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(), "", "",
                euroAmount, sekAmount,
                btcAmount, pp.getCurr_id(),
                pp.getAmount(),
                euroAmount,
                withdrawalAmount,
                withdrawalAmount, "0" , new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));

                    }
                });
    }


}
