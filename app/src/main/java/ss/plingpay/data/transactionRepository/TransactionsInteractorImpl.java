package ss.plingpay.data.transactionRepository;

import android.support.annotation.NonNull;
import android.util.Log;

import ss.plingpay.TestValues;
import ss.plingpay.data.transactionRepository.new_transactions.EuroToClp;
import ss.plingpay.data.transactionRepository.new_transactions.EuroToPkr;
import ss.plingpay.data.transactionRepository.new_transactions.EuroToRon;
import ss.plingpay.data.transactionRepository.new_transactions.EuroToSek;
import ss.plingpay.data.transactionRepository.new_transactions.EuroToVnd;
import ss.plingpay.data.transactionRepository.new_transactions.SekToClp;
import ss.plingpay.data.transactionRepository.new_transactions.SekToEuro;
import ss.plingpay.data.transactionRepository.new_transactions.SekToPkr;
import ss.plingpay.data.transactionRepository.new_transactions.SekToRon;
import ss.plingpay.data.transactionRepository.new_transactions.SekToVnd;
import ss.plingpay.data.transactionRepository.new_transactions.TransactionListener;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;


/**
 * Business Logic class for confirmation view
 */

public class TransactionsInteractorImpl implements TransactionsInteractor, TransactionListener {


    private static final String TAG = TransactionsInteractorImpl.class.getName().toUpperCase();


    private ConfirmationBLListener listener;

    public TransactionsInteractorImpl() {
    }


    @Override
    public void startTransaction(String senderCurrency,
                                 String receiverCurrency,
                                 String enteredAmount,
                                 PaymentPOJO paymentPOJO,
                                 Userinfo userinfo, ConfirmationBLListener listener) {

        this.listener = listener;

        //sender eur & receiver pkr
        if (senderCurrency.equalsIgnoreCase("eur") &&
                receiverCurrency.equalsIgnoreCase("pkr")) {

            Log.d(TAG, " EURO TO PKR");

            new EuroToPkr(enteredAmount, paymentPOJO, userinfo, this);


        } else if (senderCurrency.equalsIgnoreCase("eur") &&
                receiverCurrency.equalsIgnoreCase("vnd")) {

            Log.d(TAG, " EURO TO VND");

            new EuroToVnd(enteredAmount, paymentPOJO, userinfo, this);

        } else if (senderCurrency.equalsIgnoreCase("sek") &&
                receiverCurrency.equalsIgnoreCase("pkr")) {

            Log.d(TAG, " SEK TO PKR");

            new SekToPkr(enteredAmount, paymentPOJO, userinfo, this);

        } else if (senderCurrency.equalsIgnoreCase("sek") &&
                receiverCurrency.equalsIgnoreCase("vnd")) {

            Log.d(TAG, " SEK TO VND");

            new SekToVnd(enteredAmount, paymentPOJO, userinfo, this);


        } else if (senderCurrency.equalsIgnoreCase("eur") &&
                receiverCurrency.equalsIgnoreCase("clp")) {

            Log.d(TAG, " EURO TO CLP");


            new EuroToClp(enteredAmount, paymentPOJO, userinfo, this);

        } else if (senderCurrency.equalsIgnoreCase("sek") &&
                receiverCurrency.equalsIgnoreCase("clp")) {

            Log.d(TAG, " SEK TO CLP");

            new SekToClp(enteredAmount, paymentPOJO, userinfo, this);

        } else if (senderCurrency.equalsIgnoreCase("sek") &&
                receiverCurrency.equalsIgnoreCase("eur")) {

            Log.d(TAG, " SEK TO EURO");

            new SekToEuro(enteredAmount, paymentPOJO, userinfo, this);


        } else if (senderCurrency.equalsIgnoreCase("eur") &&
                receiverCurrency.equalsIgnoreCase("sek")) {

            Log.d(TAG, " EURO TO SEK");

            new EuroToSek(enteredAmount, paymentPOJO, userinfo, this);


        } else if (senderCurrency.equalsIgnoreCase("sek") &&
                receiverCurrency.equalsIgnoreCase("ron")) {

            Log.d(TAG, " SEK TO RON");

            new SekToRon(paymentPOJO, userinfo, this);


        } else if (senderCurrency.equalsIgnoreCase("eur") &&
                receiverCurrency.equalsIgnoreCase("ron")) {


            new EuroToRon(paymentPOJO, userinfo, this);

        } else {


        }

    }

    @Override
    public void onSuccess(@NonNull String val1, @NonNull String val2, @NonNull String val3) {
        listener.onSuccess(val1, val2, val3);
    }

    @Override
    public void onFailed(@NonNull String errorMessage) {
        listener.onFailed(errorMessage);
    }

    @Override
    public void onServiceDown() {
        listener.onServiceDown();
    }

    @Override
    public void Test(TestValues v) {
        listener.Test(v);
    }

    public interface ConfirmationBLListener {
        void onSuccess(@NonNull String val1, @NonNull String val2, @NonNull String val3);

        void onFailed(@NonNull String errorMessage);

        void onServiceDown();

        void Test(@NonNull TestValues v);
    }
}
