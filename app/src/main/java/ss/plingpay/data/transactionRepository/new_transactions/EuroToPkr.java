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
public class EuroToPkr {


    private static final String TAG = EuroToClp.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;

    private String pkrAmount;
    private String euroAmount;
    private String _transid;
    private String _desc;
    private String btcAmount;
    private String withdrawalAmount;

    private TransactionListener listener;


    public EuroToPkr(String euroAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.euroAmount = Utilz.removeComma(euroAmount);
        this.pp = pp;
        this.senderInfo = senderInfo;
        this.listener = listener;


        apis.newEuroToBtc(euroAmount, new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {

                btcAmount = Thc.findAab(val1);
                convertBtcToPkr(btcAmount);

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

    private void convertBtcToPkr(String btcAmount) {

        apis.newConvertBtcToPkr(btcAmount, new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {

                pkrAmount = val1;
                withdrawalAmount = Thc.findAw(Double.parseDouble(btcAmount), Double.parseDouble(val2));
                createNewOrder();

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

                        placeOrderKraken();

                    }

                    @Override
                    public void onFailed() {

                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");
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

                addTokrakenLog();

            }

            @Override
            public void onFailed(String error) {

                Log.e(TAG, "onFailed: ", new Throwable("Error"));
                listener.onFailed("");
                listener.onServiceDown();

            }
        });
    }


    private void addTokrakenLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(), _transid, _desc, euroAmount, pkrAmount,
                btcAmount, pp.getCurr_id(), pp.getAmount(), euroAmount,
                withdrawalAmount, withdrawalAmount, "0", new ConfirmationFragServices.CallBacks.CallBack() {
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
