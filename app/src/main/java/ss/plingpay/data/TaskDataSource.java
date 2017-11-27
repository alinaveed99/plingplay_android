package ss.plingpay.data;

import android.content.ContentResolver;

import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Single;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.pojos.CurrencyPOJO.CurrenciesPOJO;
import ss.plingpay.pojos.ExchangeListPOJO;
import ss.plingpay.pojos.Invoices;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.pojos.currencylayer.CurrencyLayerPojo;

/**
 * Created by Sammie on 3/21/2017.
 */

public interface TaskDataSource {


    Single<JsonElement> sendPaymentRequest(
            String authToken, String senderId, String receiverId,
            String amount, String currency, String request, String description,
            String default_address, String btcAmount);

    Single<CurrencyLayerPojo> currencyConverter(String accessKey, String from, String to, String amount);

    Single<Invoices> getUserInvoices(String authToken, String userId);

    Single<JsonElement> rejectRequest(String requestId);

    Single<JsonElement> approveRequest(String requestId);

    Single<JsonElement> addToLog(String senderId, String receiverId, String amount, String currency, String senderExchange, String receiverExchange);


    Single<JsonElement> performTransaction(String senderId,
                                           String receiverId,
                                           String senderDefaultCurrency,
                                           String receiverDefaultCurrency,
                                           String selectedCurrency,
                                           String amount,
                                           String senderExchangeId,
                                           String receiverExchangeId,
                                           String selectedCurrencyId,
                                           String isStandingOrder);

    Single<CurrenciesPOJO> getAllCurrencies();

    Single<List<CountriesList>> getCountries();

    Single<List<UserDetails>> getPhoneContacts(ContentResolver contentResolver);

    Single<JsonElement> sendPhoneContact(String users);

    Single<ExchangeListPOJO> getCurrencyExchange(String currencyId, String currencyName);

    Single<JsonElement> syncUserAccountSetting(
            String firstName, String lastName, String phone, String address1, String address2, String city, String state,
            String country_id, String zip, String currancy_id, String _long, String lat, String id, String bankaccountnumber,
            String bankname, String accounttilte, String withdrawaltype, String currancyexchange_id, String cnic,
            String address, String withdrawaltypetext, String accounttype, String rut
    );

}
