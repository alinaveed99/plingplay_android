package ss.plingpay.data.transactionRepository.new_transactions;

import android.support.annotation.NonNull;

import ss.plingpay.TestValues;


/**
 * Created by Sammie on 4/26/2017.
 */

@Deprecated
public interface TransactionListener {

    void onSuccess(@NonNull String val1, @NonNull String val2, @NonNull String val3);

    void onFailed(@NonNull String errorMessage);

    void onServiceDown();

    void Test(TestValues v);

}
