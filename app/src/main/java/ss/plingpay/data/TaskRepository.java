package ss.plingpay.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.pojos.CurrencyPOJO.CurrenciesPOJO;
import ss.plingpay.pojos.ExchangeListPOJO;
import ss.plingpay.pojos.Invoices;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.pojos.currencylayer.CurrencyLayerPojo;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 3/21/2017.
 */

public class TaskRepository implements TaskDataSource {

    final WebServices api;

    public TaskRepository(Retrofit retrofit) {
        this.api = retrofit.create(WebServices.class);
    }

    @Override
    public Single<JsonElement> sendPaymentRequest(String authToken, String senderId, String receiverId, String amount, String currency, String request, String description, String default_address, String btcAmount) {
        return api.sendPaymentRequest(authToken, senderId, receiverId, amount,
                currency, request, description, default_address, btcAmount).map(response -> {


            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<CurrencyLayerPojo> currencyConverter(String accessKey, String from, String to, String amount) {
        return api.currencyConverter(Utilz.AppConstants.FULL_CURRENCYLAYER_BASE_URL, accessKey, from, to, amount).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<Invoices> getUserInvoices(String authToken, String userId) {
        return api.getUserInvoiceRequest(authToken, userId).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<JsonElement> rejectRequest(String requestId) {
        return api.RejectRequest("authtoken", requestId).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<JsonElement> approveRequest(String requestId) {
        return api.ApproveRequest("authtoken", requestId).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<JsonElement> addToLog(String senderId, String receiverId, String amount, String currency, String senderExchange, String receiverExchange) {
        return api.AddToLogRx(senderId, receiverId, "", "", amount, "", senderExchange, receiverExchange,
                "", "", "", currency, "", "", "", "", "1")
                .map(response -> {

                    if (response == null || !response.isSuccessful()) {
                        throw new RuntimeException("Error In Response");
                    }

                    return response.body();
                });
    }

    @Override
    public Single<JsonElement> performTransaction(String senderId, String receiverId, String senderDefaultCurrency, String receiverDefaultCurrency,
                                                  String selectedCurrency, String amount, String senderExchangeId,
                                                  String receiverExchangeId, String selectedCurrencyId, String isStandingOrder) {
        return api.performTransaction(senderId, receiverId, senderDefaultCurrency,
                receiverDefaultCurrency, selectedCurrency, amount, senderExchangeId,
                receiverExchangeId, selectedCurrencyId, isStandingOrder).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }


            return response.body();
        });
    }

    @Override
    public Single<CurrenciesPOJO> getAllCurrencies() {
        return api.getCurrenciesRx("bit4m").map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }


            return response.body();
        });
    }

    @Override
    public Single<List<CountriesList>> getCountries() {
        return api.getCountriesListRx("bit4m").map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<List<UserDetails>> getPhoneContacts(ContentResolver contentResolver) {
        return Single.fromCallable(() -> getPhoneContactsFromDb(contentResolver));
    }

    @Override
    public Single<JsonElement> sendPhoneContact(String users) {
        return api.sendPhoneContact(users).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<ExchangeListPOJO> getCurrencyExchange(String currencyId, String currencyName) {
        return api.getCurrencyExchange(currencyId, "").map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();
        });
    }

    @Override
    public Single<JsonElement> syncUserAccountSetting(String firstName, String lastName, String phone,
                                                      String address1, String address2, String city, String state,
                                                      String country_id, String zip, String currancy_id, String _long,
                                                      String lat, String id, String bankaccountnumber, String bankname,
                                                      String accounttilte, String withdrawaltype, String currancyexchange_id,
                                                      String cnic, String address, String withdrawaltypetext,
                                                      String accounttype, String rut) {
        return api.syncUserAccountSetting(firstName, lastName, phone, address1, address2, city, state, country_id, zip, currancy_id,
                _long, lat, id, bankaccountnumber, bankname, accounttilte, withdrawaltype, currancyexchange_id, cnic,
                address, withdrawaltypetext, accounttype, rut).map(response -> {

            if (response == null || !response.isSuccessful()) {
                throw new RuntimeException("Error In Response");
            }

            return response.body();

        });
    }

    private ArrayList<UserDetails> getPhoneContactsFromDb(ContentResolver contentResolver) {

        UserDetails mPhoneUser;

        ArrayList<UserDetails> phoneUsers = new ArrayList<>();

        Cursor cur = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                mPhoneUser = new UserDetails();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);

                    // get the phone number
                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println("phone " + phone);

                        mPhoneUser.setPhone(phone);
                    }
                    pCur.close();


                    // get email and type

                    Cursor emailCur = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        System.out.println("Email " + email + " Email Type : " + emailType);
                        mPhoneUser.setEmail(email);
                    }
                    emailCur.close();

                    phoneUsers.add(mPhoneUser);

                }
            }
        }


        return phoneUsers;

    }


}
