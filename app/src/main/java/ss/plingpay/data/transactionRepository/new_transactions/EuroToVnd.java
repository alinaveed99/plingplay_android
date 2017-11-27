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

public class EuroToVnd {

    private static final String TAG = EuroToVnd.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;
    private TransactionListener listener;
    private String vndAmount;
    private String euroAmount;
    private String feeEuroAmount;
    private String _transid;
    private String _desc;
    private String btcAmount;
    private String withdrawalAmount;


    public EuroToVnd(String euroAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.euroAmount = Utilz.removeComma(euroAmount);
        this.feeEuroAmount = Thc.addFee(euroAmount);
        this.pp = pp;
        this.senderInfo = senderInfo;
        this.listener = listener;

        convertEuroToBtc(euroAmount);


    }


    //Ro will be exchange rate in this api
    private void convertEuroToBtc(String euroAmount) {
        apis.newEuroToBtc(Utilz.removeComma(euroAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {

                btcAmount = Thc.findAab(val1);
                convertBtcToVnd(btcAmount);

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

    private void convertBtcToVnd(final String btcAmount) {

        apis.newBtctovnd(Utilz.removeComma(btcAmount), new ConfirmationFragServices.CallBacks.NewApiCallback() {
            @Override
            public void onSuccess(String val1, String val2) {

                vndAmount = val1;
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

                placeOrderV();

            }

            @Override
            public void onFailed(String error) {
                Log.e(TAG, "onFailed: ", new Throwable("Error"));
//                listener.onFailed("");
                listener.onServiceDown();

            }
        });
    }

    private void placeOrderV() {
        apis.vbtcaddorder(Utilz.convertToSatoshi(btcAmount),
                btcAmount,
                vndAmount,
                senderInfo.getId(),
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
                        listener.onServiceDown();
                    }
                });
    }


    private void addTokrakenLog() {
        apis.addToLog(senderInfo, pp.getReceiverInfo(), _transid, _desc, euroAmount, vndAmount,
                btcAmount, pp.getCurr_id(), pp.getAmount(), feeEuroAmount,
                withdrawalAmount, withdrawalAmount, "0" , new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;
                        listener.onSuccess(withdrawalAmount, feeEuroAmount, "");

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");

                    }
                });
    }


}
