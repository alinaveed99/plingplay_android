package ss.plingpay.utilz;

import ss.plingpay.pojos.Payment;
import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by samar_000 on 8/16/2016.
 */

public class Listeners {

    public interface WalletLogin {
        void onCreateWallet();
    }


    public interface SendPaymentListener {
        void onSuccessTransaction();

        void onErrorTransaction(String errorMsg);
    }

    public interface FavContactListener {
        void onItemClick(UserDetails user);

        void onRemoveClick(UserDetails user);

        void OnInfoClick(UserDetails user);
    }

    public interface ContactListener {
        void onItemClick(UserDetails user);

        void onRemoveClick(UserDetails user);

        void OnInfoClick(UserDetails user);
    }

    public interface CurrencySelecter {
        void selectedItem(String currency, String id);
    }

    public interface TransactionTask {
        void onTransactionCompleted();

        void onError(String errorMsg);
    }

    public interface AddressListListener {
        void OnClick(String address);
    }

    public interface InvoicesListener {
        void OnClick(Payment Payment);
    }


    public interface CurrencyListener {
        void OnClick(String currency, String id);
    }

    public interface CountryListener {
        void OnClick(String name, String id);
    }


    public interface WalletChangeListener {
        void onChangeClick(String id, String address);
    }


    public interface DialogListener {
        void OnOkClick();
    }

    public interface NewDialogListener {
        void OnOkClick();
        void OnCancelClick();
    }


}
