package ss.plingpay.infrastructure;

import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import ss.plingpay.pojos.AddressList;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.pojos.CurrencyPOJO.CurrenciesPOJO;
import ss.plingpay.pojos.ExchangeListPOJO;
import ss.plingpay.pojos.Extra;
import ss.plingpay.pojos.Invoices;
import ss.plingpay.pojos.OrderListPojo;
import ss.plingpay.pojos.TransactionHistoryPOJO;
import ss.plingpay.pojos.UserLogin;
import ss.plingpay.pojos.Users.Users;
import ss.plingpay.pojos.currencylayer.CurrencyLayerPojo;


/**
 * Created by samar_000 on 8/15/2016.
 */

public interface WebServices {

    //Convert tvCurrency to btc
    @GET("/tobtc")
    Call<String> getCurrencyValue(@Query("tvCurrency") String currency, @Query("value") String value);

    //Login to bit4m
    @FormUrlEncoded
    @POST("users/userlogin")
    Call<UserLogin> login(@Field("email") String email, @Field("password") String password);

    //getting api acces from blockchain
    //merchant/4a5a8794-2d11-499a-b18f-9bed9064cb4e/login?password=Boje4qieboje@&api_code=ddcd937b-cb25-4e49-a824-684fc1753de3
    @GET("merchant/{guid}/login")
    Call<Extra.BlockChainAccess> blockchainAccess(@Path("guid") String guid,
                                                  @Query("password") String password,
                                                  @Query("api_code") String api_code);


    //Login to blockChain
    //http://52.26.178.135:3000/merchant/4a5a8794-2d11-499a-b18f-9bed9064cb4e/balance?password=Boje4qieboje@
    @GET("merchant/{guid}/balance")
    Call<Extra.Balance> getWalletBalance(@Path("guid") String guid,
                                         @Query("password") String password);


    @FormUrlEncoded
    @POST("currancies")
    Call<CurrenciesPOJO> getCurrencies(
            @Field("token") String token);

    @FormUrlEncoded
    @POST("currancies")
    Single<Response<CurrenciesPOJO>> getCurrenciesRx(
            @Field("token") String token);

    //Get exchange rate
    @GET("ticker")
    Call<JsonElement> getExchange();

    //Searching Users
    @FormUrlEncoded
    @POST("users/checkuser")
    Call<Users> getUsersList(@Field("keyword") String keyword);


    //Search In Favorite User
    @FormUrlEncoded
    @POST("FavouriteUsers/search")
    Call<Users> getFavoriteUsersList(@Field("keyword") String keyword, @Field("userid") String userid);


    //Searching Favorite Users
    @FormUrlEncoded
    @POST("FavouriteUsers/listfavusers")
    Call<Users> getFavoriteUsers(@Field("login_user_id") String userId);


    @FormUrlEncoded
    @POST("transactionLogs/addtolog")
    Call<JsonElement> addToLogs(@Field("hash") String hash,
                                @Field("senderid") String senderid,
                                @Field("receiverid") String receiverid,
                                @Field("amount") String amount,
                                @Field("currancy_id") String currancy_id,
                                @Field("request") String request,
                                @Field("type") String type,
                                @Field("amountinbtc") String amountinbtc,
                                @Field("amtinbtcremaning") String amtinbtcremaning,
                                @Field("amtinbtccompany") String amtinbtccompany,
                                @Field("senderaddress") String senderAddr,
                                @Field("receiveraddress") String recAddr,

                                @Field("senderexchange") String senderexchange,
                                @Field("receiverexchange") String receiverexchange);


    //Adding to favorite
    @FormUrlEncoded
    @POST("FavouriteUsers/markfavourite")
    Call<JsonElement> addToFavorite(@Field("login_user_id") String login_user,
                                    @Field("fav_user_id") String fav_user_id);

    //Removing from favorite
    @FormUrlEncoded
    @POST("FavouriteUsers/removefavourite")
    Call<JsonElement> removeFromFavorite(@Field("login_user_id") String login_user,
                                         @Field("fav_user_id") String fav_user_id);


    //Add user
    @Multipart
    @POST("users/add")
    Call<String> RegisterUer(
            @Part("tvFirstName") RequestBody firstName,
            @Part("tvLastName") RequestBody lastName,
            @Part("email") RequestBody email,
            @Part("address1") RequestBody address1,
            @Part("address2") RequestBody address2,
            @Part("city") RequestBody city,
            @Part("state") RequestBody state,
            @Part("country") RequestBody country,
            @Part("Password") RequestBody password,
            @Part("currancy") RequestBody currency,
            @Part("zip") RequestBody zip,
            @Part("lat") RequestBody lat,
            @Part("long") RequestBody longg,
            @Part("company") RequestBody company,
            @Part("phone") RequestBody phone,
            @Part("exchange_id") RequestBody exchangeId,
            @Part("accountnumber") RequestBody accountNumber,
            @Part("bankname") RequestBody bankname,
            @Part("accounttitle") RequestBody accounttitle,
            @Part("branchcode") RequestBody branchcode,

            @Part("guid") RequestBody guid,
            @Part("default_address") RequestBody default_address,
            @Part("label") RequestBody label,
            @Part("bitcoin_email") RequestBody bitcoin_email,

            @Part("id_card_image\"; filename=\"pp.png\" ") RequestBody id_card_image,
            @Part("bill_image\"; filename=\"pp.png\" \"") RequestBody bill_image,
            @Part("id_card_closeup_image\"; filename=\"pp.png\" \"") RequestBody id_card_closeup_image,

            @Part("user_type") RequestBody user_type
    );


    @FormUrlEncoded
    @POST("currancyexchanges/newaddress")
    Call<JsonElement> generateAddress(@Field("key") String key, @Field("senderid") String userId,
                                      @Field("amount") String amount, @Field("receiverid") String receiverid,
                                      @Field("currancyexchange_id") String currancyexchange_id);


    @FormUrlEncoded
    @POST("currancyexchanges/createneworder")
    Call<JsonElement> createNewOrder(@Field("amount") String amount,
                                     @Field("amountitbtc") String amountitbtc,

                                     @Field("amountinpkr") String amountinpkr,
                                     @Field("senderid") String senderid,
                                     @Field("receiverid") String receiverid,
                                     @Field("exchangeid") String exchangeid
    );


    @FormUrlEncoded
    @POST("currancyexchanges/getorderlog")
    Call<OrderListPojo> getOrderLogs(@Field("userid") String userId);


    @FormUrlEncoded
    @POST("currancyexchanges/getopenorderbyid")
    Call<JsonElement> getOpenOrderId(@Field("recordid") String recordid, @Field("transid") String transid);

    @FormUrlEncoded
    @POST("currancyexchanges/getexecutedorderbyid")
    Call<JsonElement> getexecutedorderbyid(@Field("recordid") String recordid, @Field("transid") String transid);


    @FormUrlEncoded
    @POST("currancyexchanges/getcanceledorderbyid")
    Call<JsonElement> getcanceledorderbyid(@Field("recordid") String recordid, @Field("transid") String transid);

    @FormUrlEncoded
    @POST("api/v2/create")
    Call<Extra.CreateNewWallet> createNewWallet(@Field("email") String email, @Field("password") String password,
                                                @Field("api_code") String api_code, @Field("priv") String privateKey,
                                                @Field("label") String label);


    @FormUrlEncoded
    @POST("bitcoindetails/add")
    Call<JsonElement> saveWalletToServer(@Field("guid") String guid, @Field("default_address") String default_address,
                                         @Field("bitcoin_email") String bitcoin_email, @Field("label") String label,
                                         @Field("user_id") String user_id, @Field("user_label") String user_label);


    @FormUrlEncoded
    @POST("Currancyexchanges/pkrtobtc")
    Call<String> PkrToBtc(@Field("key") String Key, @Field("amount") String amountInPkr);

    @FormUrlEncoded
    @POST("Currancyexchanges/btctopkr")
    Call<String> BtcToPkr(@Field("key") String Key, @Field("amount") String amountInBtc);

    @FormUrlEncoded
    @POST("Currancyexchanges/api_btctopkr")
    Call<JsonElement> newBtcToPkr(@Field("key") String Key, @Field("amount") String amountInBtc);

    @FormUrlEncoded
    @POST("Currancyexchanges/faitwithdrawal")
    Call<Extra.withDrawalPojo> withDrawalAmount(@Field("amount") String amount,
                                                @Field("accountname") String accountname,
                                                @Field("accountnumber") String accountnumber,
                                                @Field("bankname") String bankname,
                                                @Field("mobilenumber") String mobilenumber,
                                                @Field("senderid") String senderid,
                                                @Field("receiverid") String receiverid);


    @GET("merchant/{guid}/list")
    Call<AddressList> getWalletAddresses(@Path("guid") String guid,
                                         @Query("password") String password);

    @FormUrlEncoded
    @POST("countries/index")
    Call<List<CountriesList>> getCountriesList(@Field("token") String token);

    @FormUrlEncoded
    @POST("countries/index")
    Single<Response<List<CountriesList>>> getCountriesListRx(@Field("token") String token);

    @Deprecated
    @FormUrlEncoded
    @POST("users/edit")
    Call<JsonElement> syncUserDate(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("phone") String phone,
            @Field("address1") String address1,
            @Field("address2") String address2,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country_id,
            @Field("zip") String zip,
            @Field("currancy_id") String currancy_id,
            @Field("long") String _long,
            @Field("lat") String lat,
            @Field("id") String id,
            @Field("bankaccountnumber") String bankaccountnumber,
            @Field("bankname") String bankname,
            @Field("accounttilte") String accounttilte,
            @Field("withdrawaltype") String withdrawaltype,
            @Field("currancyexchange_id") String currancyexchange_id,
            @Field("cnic") String cnic,
            @Field("address") String address,
            @Field("withdrawaltypetext") String withdrawaltypetext,
            @Field("accounttype") String accounttype,
            @Field("rut") String rut
    );

    @FormUrlEncoded
    @POST("users/make_default")
    Call<JsonElement> saveUserDefaultAddress(@Field("userid") String userid,
                                             @Field("bit_id") String bit_id,
                                             @Field("default_address") String default_address);


    @FormUrlEncoded
    @POST("transactionLogs/view")
    Call<TransactionHistoryPOJO> getLogs(@Field("senderid") String Id);

//    @FormUrlEncoded
//    @POST("paymentRequests/request")
//    Call<JsonElement> sendPaymentRequest(@Field("authtoken") String authtoken,
//                                         @Field("senderid") String senderid,
//                                         @Field("receiverid") String receiverid,
//                                         @Field("amount") String amount,
//                                         @Field("currancy") String currancy,
//                                         @Field("request") String request,
//                                         @Field("description") String description,
//                                         @Field("default_address") String default_address,
//                                         @Field("amountinbtc") String amountinbtc);

    @FormUrlEncoded
    @POST("paymentRequests/request")
    Single<Response<JsonElement>> sendPaymentRequest(@Field("authtoken") String authtoken,
                                                     @Field("senderid") String senderid,
                                                     @Field("receiverid") String receiverid,
                                                     @Field("amount") String amount,
                                                     @Field("currancy") String currancy,
                                                     @Field("request") String request,
                                                     @Field("description") String description,
                                                     @Field("default_address") String default_address,
                                                     @Field("amountinbtc") String amountinbtc);

    @FormUrlEncoded
    @POST("paymentRequests/requesttome")
    Single<Response<Invoices>> getUserInvoiceRequest(@Field("authtoken") String authtoken, @Field("receiverid") String receiverid);


    @FormUrlEncoded
    @POST("PaymentRequests/myrequests")
    Call<Invoices> getOutboxRequest(@Field("senderid") String senderid);

    @FormUrlEncoded
    @POST("paymentRequests/rejectrequest")
    Single<Response<JsonElement>> RejectRequest(@Field("authtoken") String authtoken, @Field("id") String RequestId);

    @FormUrlEncoded
    @POST("paymentRequests/approverequest")
    Single<Response<JsonElement>> ApproveRequest(@Field("authtoken") String authtoken, @Field("id") String RequestId);


    //Modify and accept Invoice request
    @FormUrlEncoded
    @POST("Messages/add")
    Call<JsonElement> addMessage(@Field("payment_request_id") String payment_request_id,
                                 @Field("new_amount") String new_amount,
                                 @Field("message") String message,
                                 @Field("amount_inbtc") String amount_inbtc
    );


    @FormUrlEncoded
    @POST("Messages")
    Call<JsonElement> getAllMessage(@Field("payment_request_id") String payment_request_id);


    @FormUrlEncoded
    @POST("users/getexchange")
    Call<ExchangeListPOJO> getExchangeList(@Field("currencyid") String currencyid,
                                           @Field("currencyName") String currencyName
    );


    @FormUrlEncoded
    @POST("users/make_defaultwallet")
    Call<JsonElement> makeDefaultWallet(@Field("userid") String userid,
                                        @Field("id") String id,
                                        @Field("default_address") String address);


    @FormUrlEncoded
    @POST("currancyexchanges/krakenaddorder")
    Call<JsonElement> karakenPlaceOrder(@Field("amount") String amount);


    @FormUrlEncoded
    @POST("TransactionLogs/krakenaddlog")
    Call<JsonElement> krakenAddToLog(@Field("senderid") String senderid,
                                     @Field("receiverid") String receiverid,
                                     @Field("transid") String transid,
                                     @Field("transdesc") String transdesc,
                                     @Field("amountineuro") String amountineuro,
                                     @Field("amountinpkr") String amountinpkr,
                                     @Field("senderexchange") String senderexchange,
                                     @Field("receiverexchange") String receiverexchange,
                                     @Field("status") String status,

                                     @Field("urdubitaddress") String urdubitaddress,
                                     @Field("amountinbtc") String amountinbtc

    );

    @FormUrlEncoded
    @POST("TransactionLogs/addnewlog")
    Call<JsonElement> AddToLog(@Field("senderid") String senderid,
                               @Field("receiverid") String receiverid,
                               @Field("transid") String transid,
                               @Field("transdesc") String transdesc,
                               @Field("amountineuro") String amountineuro,
                               @Field("amountinpkr") String amountinpkr,
                               @Field("senderexchange") String senderexchange,
                               @Field("receiverexchange") String receiverexchange,
                               @Field("status") String status,
                               @Field("urdubitaddress") String urdubitaddress,
                               @Field("amountinbtc") String amountinbtc,
                               @Field("currencyid") String currencyid,

                               @Field("enteredamount") String enteredamount,
                               @Field("convertedamount") String convertedamount,// this
                               @Field("recevieramount") String recevieramount, // this
                               @Field("withdrawalamount") String withdrawalamount,
                               @Field("type") String type);


    @FormUrlEncoded
    @POST("TransactionLogs/addnewlog")
    Single<Response<JsonElement>> AddToLogRx(@Field("senderid") String senderid,
                                             @Field("receiverid") String receiverid,
                                             @Field("transid") String transid,
                                             @Field("transdesc") String transdesc,
                                             @Field("amountineuro") String amountineuro,
                                             @Field("amountinpkr") String amountinpkr,
                                             @Field("senderexchange") String senderexchange,
                                             @Field("receiverexchange") String receiverexchange,
                                             @Field("status") String status,
                                             @Field("urdubitaddress") String urdubitaddress,
                                             @Field("amountinbtc") String amountinbtc,
                                             @Field("currencyid") String currencyid,

                                             @Field("enteredamount") String enteredamount,
                                             @Field("convertedamount") String convertedamount,
                                             @Field("recevieramount") String recevieramount,
                                             @Field("withdrawalamount") String withdrawalamount,
                                             @Field("type") String type);


    @FormUrlEncoded
    @POST("currancyexchanges/vbtcaddorder")
    Call<JsonElement> vbtcOrder(@Field("amount") String amount,
                                @Field("amountitbtc") String amountitbtc,
                                @Field("amountinvnd") String amountinvnd,
                                @Field("senderid") String senderid,
                                @Field("receiverid") String receiverid,
                                @Field("exchangeid") String exchangeid
    );


    @FormUrlEncoded
    @POST("currancyexchanges/btctovnd")
    Call<String> btctovnd(@Field("key") String key,
                          @Field("amount") String amount
    );

    @FormUrlEncoded
    @POST("currancyexchanges/api_btctovnd")
    Call<JsonElement> newbtctovnd(@Field("key") String key,
                                  @Field("amount") String amount
    );


    @FormUrlEncoded
    @POST("currancyexchanges/vndtobtc")
    Call<String> vndtobtc(@Field("key") String key,
                          @Field("amount") String amount
    );


    @FormUrlEncoded
    @POST("currancyexchanges/krakenbalance")
    Call<JsonElement> krakenBalance(@Field("key") String key);


    @FormUrlEncoded
    @POST("currancyexchanges/btctoeuro")
    Call<String> btcToEuro(@Field("amountinbtc") String amountInBtc,
                           @Field("key") String key);

    @FormUrlEncoded
    @POST("currancyexchanges/api_btctoeuro")
    Call<JsonElement> newBtcToEuro(@Field("amountinbtc") String amountInBtc,
                                   @Field("key") String key);


    @FormUrlEncoded
    @POST("currancyexchanges/eurotobtc")
    Call<String> euroToBtc(@Field("amount") String amount);


    @FormUrlEncoded
    @POST("currancyexchanges/api_eurotobtc")
    Call<JsonElement> newEuroToBtc(@Field("amount") String amount);


    @FormUrlEncoded
    @POST("currancyexchanges/clpaddorder")
    Call<JsonElement> clpAddOrder(@Field("amount") String amount,
                                  @Field("amountitbtc") String amountitbtc,
                                  @Field("amountinclp") String amountinclp,
                                  @Field("senderid") String senderid,
                                  @Field("receiverid") String receiverid,
                                  @Field("exchangeid") String exchangeid,

                                  @Field("api_key") String api_key,
                                  @Field("api_secret") String api_secret
    );


    @FormUrlEncoded
    @POST("currancyexchanges/clptobtc")
    Call<String> clpToBtc(@Field("key") String key,
                          @Field("amount") String amountInClp);


    @FormUrlEncoded
    @POST("currancyexchanges/btctoclp")
    Call<String> btcToClp(@Field("key") String key,
                          @Field("amount") String amountBtc);

    @FormUrlEncoded
    @POST("currancyexchanges/api_btctoclp")
    Call<JsonElement> newBtcToClp(@Field("key") String key,
                                  @Field("amount") String amountBtc);


    @FormUrlEncoded
    @POST("invites/sendinvite")
    Observable<JsonElement> sendInvite(@Field("email") String email,
                                       @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("users/resetpin")
    Observable<Response<JsonElement>> resetPin(
            @Field("email") String email,
            @Field("flag") String flag
    );


    @FormUrlEncoded
    @POST("users/checkanswers")
    Observable<Response<JsonElement>> checkForSecretQuestion(@Field("email") String email);


    @FormUrlEncoded
    @POST("users/resetpassword")
    Observable<Response<JsonElement>> resetPassword(@Field("email") String email, @Field("password") String password);


    @GET("convert")
    Observable<Response<CurrencyLayerPojo>> currencyLayerConverter(
            @Query("access_key") String access_key,
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") String amount);

    @GET
    Single<Response<CurrencyLayerPojo>> currencyConverter(
            @Url String url,
            @Query("access_key") String access_key,
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") String amount);


    //New Apis from here

    @GET("convert")
    Single<Response<CurrencyLayerPojo>> newCurrencyLayerConverter(
            @Query("access_key") String access_key,
            @Query("from") String from,
            @Query("to") String to,
            @Query("amount") String amount);


    @FormUrlEncoded
    @POST("users/resetpin")
    Single<Response<JsonElement>> newResetPin(
            @Field("email") String email,
            @Field("flag") String flag
    );

    @FormUrlEncoded
    @POST("TransactionLogs/checkCases")
    Single<Response<JsonElement>> performTransaction(
            @Field("senderid") String senderId,
            @Field("receiverid") String receiverId,
            @Field("senderDefaultCurrency") String senderDefaultCurrency,
            @Field("receiverDefaultCurrency") String receiverDefaultCurrency,
            @Field("SelectedCurrency") String selectedCurrency,
            @Field("amount") String amount,
            @Field("senderExchangeId") String senderExchangeId,
            @Field("receiverExchangeId") String receiverExchangeId,
            @Field("selectedCurrencyId") String selectedCurrencyId,
            @Field("isStandingOrder") String isStandingOrder
    );


    @FormUrlEncoded
    @POST("favouriteUsers/phonecontacts")
    Single<Response<JsonElement>> sendPhoneContact(@Field("phoneusers") String users);


    @FormUrlEncoded
    @POST("users/getexchange")
    Single<Response<ExchangeListPOJO>> getCurrencyExchange(@Field("currencyid") String currencyid,
                                                           @Field("currencyName") String currencyName
    );

    @FormUrlEncoded
    @POST("users/edit")
    Single<Response<JsonElement>> syncUserAccountSetting(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("phone") String phone,
            @Field("address1") String address1,
            @Field("address2") String address2,
            @Field("city") String city,
            @Field("state") String state,
            @Field("country") String country_id,
            @Field("zip") String zip,
            @Field("currancy_id") String currancy_id,
            @Field("long") String _long,
            @Field("lat") String lat,
            @Field("id") String id,
            @Field("bankaccountnumber") String bankaccountnumber,
            @Field("bankname") String bankname,
            @Field("accounttilte") String accounttilte,
            @Field("withdrawaltype") String withdrawaltype,
            @Field("currancyexchange_id") String currancyexchange_id,
            @Field("cnic") String cnic,
            @Field("address") String address,
            @Field("withdrawaltypetext") String withdrawaltypetext,
            @Field("accounttype") String accounttype,
            @Field("rut") String rut
    );


}
