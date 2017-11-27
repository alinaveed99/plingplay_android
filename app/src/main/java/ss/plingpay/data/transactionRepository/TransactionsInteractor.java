package ss.plingpay.data.transactionRepository;

import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;

/**
 * Created by Sammie on 4/25/2017.
 */

public interface TransactionsInteractor {

    void startTransaction(String senderCurrency,
                          String receiverCurrency,
                          String enteredAmount,
                          PaymentPOJO paymentPOJO,
                          Userinfo userinfo, TransactionsInteractorImpl.ConfirmationBLListener listener);

}
