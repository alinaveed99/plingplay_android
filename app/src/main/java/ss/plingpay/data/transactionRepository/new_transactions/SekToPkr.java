package ss.plingpay.data.transactionRepository.new_transactions;

import android.util.Log;

import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.fragments.confirmationFragment.Services.ConfirmationFragServices;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 4/6/2017.
 */
@Deprecated

public class SekToPkr {

    private static final String TAG = SekToPkr.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private String sekAmount;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;
    private TransactionListener listener;
    private String pkrAmount;
    private String euroAmount;
    private String _transid;
    private String _desc;
    private String btcAmount;
    private String withdrawalAmount;


    public SekToPkr(String sekAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.sekAmount = sekAmount;
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


    //Ro will be exchange rate in this api
    private void convertEuroToBtc(String euroAmount) {
        apis.newEuroToBtc(Utilz.removeComma(euroAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {
//                Utilz.tmsgInfo(activity, );
                btcAmount = Thc.findAab(val1);
                convertBtcToPkr(btcAmount);

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

    private void convertBtcToPkr(final String btcAmount) {

        apis.newConvertBtcToPkr(Utilz.removeComma(btcAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {

            @Override
            public void onSuccess(String val1, String val2) {

                pkrAmount = val1;
                withdrawalAmount = Thc.findAw(Double.parseDouble(btcAmount), Double.parseDouble(val2));

                placeOrderKraken();
            }

            @Override
            public void onFailed(String error) {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");

            }

            @Override
            public void onServiceDown() {
                listener.onServiceDown();
            }

        });

    }


    private void placeOrderKraken() {
        apis.placeOrderKraken(euroAmount, new ConfirmationFragServices.CallBacks.placeOrderKrakenCallBack() {
            @Override
            public void onSuccess(String transid, String desc) {

                _transid = transid;
                _desc = desc;

                createNewOrder();

            }

            @Override
            public void onFailed(String error) {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
//                listener.onFailed("");
                listener.onServiceDown();
            }
        });
    }

    private void createNewOrder() {
        apis.createNewOrder(
                btcAmount,
                Utilz.convertToSatoshi(btcAmount),
                pkrAmount, senderInfo.getId(),
                pp.getReceiverInfo().getId(),
                pp.getReceiverInfo().getExchangeid(),

                new ConfirmationFragServices.CallBacks.CallBack() {

                    @Override
                    public void onSuccess(String response) {

                        addTokrakenLog();

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");

                    }

                });
    }


    private void addTokrakenLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(), _transid, _desc, euroAmount, pkrAmount,
                btcAmount, pp.getCurr_id(), pp.getAmount(), sekAmount,
                withdrawalAmount, withdrawalAmount, "0" , new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;
                        listener.onSuccess(withdrawalAmount, "", "");

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");

                    }
                });
    }

}
