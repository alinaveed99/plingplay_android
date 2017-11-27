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

public class SekToEuro {

    private static final String TAG = SekToEuro.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private String sekAmount;
    private String feeSekAmount;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;
    private TransactionListener listener;
    private String euroAmount;
    private String _transid;
    private String _desc;
    private String btcAmount;
    private String withdrawalAmount;


    public SekToEuro(String sekAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.sekAmount = sekAmount;
        this.feeSekAmount = Thc.addFee(sekAmount);
        this.pp = pp;
        this.senderInfo = senderInfo;
        this.listener = listener;


        //1 step convert sek to euro
        apis.converter("SEK", "EUR", Utilz.removeComma(sekAmount), new ConfirmationFragServices.CallBacks.CallBack() {
            @Override
            public void onSuccess(String response) {

                //2 step convert euro to btc using kraken
                euroAmount = Utilz.removeComma(response);
                convertEuroToBtc(euroAmount);


            }

            @Override
            public void onFailed() {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");
            }

        });


    }

    private void convertEuroToBtc(String euroAmount) {
        apis.newEuroToBtc(Utilz.removeComma(euroAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {
                btcAmount = Thc.findAab(val1);
                convertBtcToEuro(btcAmount);

            }

            @Override
            public void onFailed(String error) {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");

            }

            @Override
            public void onServiceDown() {

            }
        });

    }

    private void convertBtcToEuro(String btcAmount) {


        apis.newBtcToEuro(Utilz.removeComma(btcAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {

                euroAmount = val1;
                withdrawalAmount = Thc.findAw(Double.parseDouble(btcAmount), Double.parseDouble(val2));


                addTokrakenLog();
            }

            @Override
            public void onFailed(String error) {
                listener.onFailed("");
            }

            @Override
            public void onServiceDown() {

            }
        });

    }


    private void addTokrakenLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(),
                _transid, _desc, euroAmount,
                euroAmount,
                btcAmount, pp.getCurr_id(),
                pp.getAmount(), feeSekAmount,
                withdrawalAmount, withdrawalAmount,
                "0",
                new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;
                        listener.onSuccess(withdrawalAmount, feeSekAmount, "");

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");

                    }
                });
    }


}
