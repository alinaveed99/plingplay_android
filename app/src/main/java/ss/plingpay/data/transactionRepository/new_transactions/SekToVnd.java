package ss.plingpay.data.transactionRepository.new_transactions;

import android.util.Log;

import ss.plingpay.TestValues;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.fragments.confirmationFragment.Services.ConfirmationFragServices;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 4/24/2017.
 */
@Deprecated

public class SekToVnd {

    private static final String TAG = SekToVnd.class.getName().toUpperCase();
    private ConfirmationFragServices apis;
    private String sekAmount;
    private final PaymentPOJO pp;
    private Userinfo senderInfo;
    private TransactionListener listener;
    private String vndAmount;
    private String euroAmount;
    private String _transid;
    private String _desc;
    private String btcAmount;
    private String withdrawalAmount;

    private TestValues v;


    public SekToVnd(String sekAmount, PaymentPOJO pp, Userinfo senderInfo, TransactionListener listener) {
        apis = new ConfirmationFragServices();
        this.sekAmount = sekAmount;
        this.pp = pp;
        this.senderInfo = senderInfo;
        this.listener = listener;

        v = new TestValues();

        v.setEnteredAmount(pp.getAmount());
        v.setSelectedCurrency(pp.getCurrency());
        v.setSenderDefaultCurrency(senderInfo.getCurrancyId());
        v.setReceiverDefaultCurrency(pp.getReceiverInfo().getCurrancyId());

        //1 step convert sek to euro
        apis.converter("SEK", "EUR", Utilz.removeComma(sekAmount), new ConfirmationFragServices.CallBacks.CallBack() {
            @Override
            public void onSuccess(String response) {

                //2 step convert euro to btc using kraken
                euroAmount = Utilz.removeComma(response);
                v.setSekToEur(euroAmount);
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
                v.setEuroToBtc(val1);
                btcAmount = Thc.findAabVnd(val1);
                v.setEuroToBtcAfterFormula(btcAmount);
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
                v.setBtcToVndAmount(val1);
                v.setBtcToVndExchange(val2);
                vndAmount = val1;
                withdrawalAmount = Thc.findAwVnd(Double.parseDouble(btcAmount), Double.parseDouble(val2));
                v.setWithdrawalAmount(withdrawalAmount);
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
                btcAmount, pp.getCurr_id(), pp.getAmount(), sekAmount,
                withdrawalAmount, withdrawalAmount, "0" , new ConfirmationFragServices.CallBacks.CallBack() {
                    @Override
                    public void onSuccess(String response) {

                        String a = response;
                        listener.onSuccess(withdrawalAmount, "", "");
                        listener.Test(v);

                    }

                    @Override
                    public void onFailed() {
                        Log.e(TAG, "onFailed: ", new Throwable("Error"));
                        listener.onFailed("");

                    }
                });
    }


}
