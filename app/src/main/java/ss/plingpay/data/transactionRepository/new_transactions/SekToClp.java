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

public class SekToClp {

    private static final String TAG = SekToClp.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private String sekAmount;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;

    private String clpAmount;
    private String euroAmount;
    private String feeEuroAmount;
    private String _transid;
    private String _desc;
    private String btcAmount;
    private String withdrawalAmount;

    private TransactionListener listener;


    public SekToClp(String sekAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
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
                feeEuroAmount = Thc.addFee(euroAmount);
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
                convertBtcToClp(btcAmount);

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


    private void convertBtcToClp(final String btcAmount) {

        apis.newBtctoClp(btcAmount, new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {

                clpAmount = val1;
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
        apis.placeOrderKraken(feeEuroAmount, new ConfirmationFragServices.CallBacks.placeOrderKrakenCallBack() {
            @Override
            public void onSuccess(String transid, String desc) {

                _transid = transid;
                _desc = desc;

                clpAddOrder();

            }

            @Override
            public void onFailed(String error) {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");

            }
        });
    }

    private void clpAddOrder() {

        apis.clpAddOrder(Utilz.convertToSatoshi(btcAmount), btcAmount, clpAmount,
                senderInfo.getId(), pp.getReceiverInfo().getId(), pp.getReceiverInfo().getExchangeid(),
                pp.getReceiverInfo().getApi_key(), pp.getReceiverInfo().getApi_secret(),
                new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {
                        addTokrakenLog();
                    }

                    @Override
                    public void onFailed() {
                        listener.onFailed("");

                    }
                });
    }


    private void addTokrakenLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(), _transid, _desc, euroAmount, clpAmount,
                btcAmount, pp.getCurr_id(), pp.getAmount(), Thc.addFee(sekAmount),
                withdrawalAmount, withdrawalAmount, "0", new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;
                        listener.onSuccess(withdrawalAmount, Thc.addFee(sekAmount), "");

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");

                    }
                });
    }


}
