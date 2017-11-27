package ss.plingpay.ui.fragments.confirmationFragment.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.utilz.Utilz;

import static ss.plingpay.utilz.Utilz.checkString;
import static ss.plingpay.utilz.Utilz.convertToSatoshi;

/**
 * All the services needed for transactions
 */


@Deprecated
public class ConfirmationFragServices {

    private Activity activity;
    private WebServices api;

    @Deprecated
    public ConfirmationFragServices(Activity activity) {
        this.activity = activity;

    }

    public ConfirmationFragServices() {
    }

    public void checkKrakenBalance(final String balance, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");


        api.krakenBalance("allow").enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }

                JsonObject obj = response.body().getAsJsonObject();

                String res = obj.get("response").getAsString();

                if (res.equalsIgnoreCase("success")) {
                    String balanceInEuro = obj.get("euro").getAsString();

                    String am1 = balance.replace(",", ".");
                    Double currentAmount = Double.parseDouble(am1);

                    String am2 = balanceInEuro.replace(",", ".");
                    Double krakenAmoount = Double.parseDouble(am2);

                    if (currentAmount > krakenAmoount) {
                        Utilz.showAlert(activity, "Error", "kraken : Insufficient buffer amount");
                        callBack.onFailed();
                    } else {
                        callBack.onSuccess("");
                    }

                }

                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("krakenBalance", "onFailure: ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });
    }


    public void vbtcaddorder(String amountInSatochi,
                             String amountInBtc,
                             String amountInVnd,
                             String senderId,
                             String receiverId,
                             String exchangeId,
                             final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.vbtcOrder(amountInSatochi, amountInBtc, amountInVnd,
                senderId, receiverId, exchangeId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (!response.isSuccessful() || response == null) {
                    callBack.onFailed();
                    return;
                }

                try {

                    JsonObject obj = response.body().getAsJsonObject();

                    String status = obj.get("status").getAsString();

                    if (status.equalsIgnoreCase("200")) {
                        callBack.onSuccess("response");
                    } else {
                        callBack.onFailed();
                    }


                } catch (Exception e) {

                    callBack.onFailed();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("vbtcaddorder", "onFailure: ", t);
                callBack.onFailed();
            }
        });

    }


    public void btctovnd(String btc, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String btcA = btc.replace(",", ".");

        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        api.btctovnd("allow", btcA).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }

                String convertedAmount = response.body().replace(",", ".");

                if (convertedAmount == null || convertedAmount.equalsIgnoreCase("null") || convertedAmount.isEmpty()) {
                    convertedAmount = "0.00";
                }

                String a = convertedAmount.replace(",", ".");

                double am = Double.parseDouble(a);

                String am1 = new DecimalFormat("##.##").format(am);
                String am2 = "";

                if (am1.contains(",")) {
                    am2 = am1.replace(",", ".");
                } else {
                    am2 = am1;
                }


                am = Double.parseDouble(am2);

                callBack.onSuccess(am + "");

                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("btctovnd", "onFailure: ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });

    }

    public void newBtctovnd(String btc, final CallBacks.NewApiCallback callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String btcA = btc.replace(",", ".");

        api.newbtctovnd("allow", btcA).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed("Error");
                    return;
                }


                try {
                    JsonObject jsonObject = response.body().getAsJsonObject();

                    String btcAmount = jsonObject.get("amountinvnd").getAsString();
                    String exchangerate = jsonObject.get("exchangerate").getAsString();

                    if (checkString(btcAmount) || checkString(exchangerate))
                        callBack.onServiceDown();
                    else
                        callBack.onSuccess(btcAmount, exchangerate);

                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onFailed("Error");

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("btctovnd", "onFailure: ", t);
                callBack.onFailed("");
            }
        });

    }


    public void vndToBtc(String vnd, final CallBacks.BtcCallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        api.vndtobtc("allow", vnd).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }


                String convertedAmount = response.body().replace(",", ".");

                callBack.onSuccess(convertedAmount, convertToSatoshi(convertedAmount));


                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("vndToBtc", "onFailure: ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });

    }


    public void placeOrderKraken(String amount, final CallBacks.placeOrderKrakenCallBack callBack) {


        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.karakenPlaceOrder(amount).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                try {

                    JsonObject obj = response.body().getAsJsonObject();

                    String status = obj.get("response").getAsString();

                    if (status.equalsIgnoreCase("success")) {
                        callBack.onSuccess(obj.get("transid").getAsString(), obj.get("desc").getAsString());

                    } else if (status.equalsIgnoreCase("low balance")) {


                        String m = "Unable to process your order right now due to high volumes, please try again later";

                        callBack.onFailed(m);


                    } else {

                        callBack.onFailed(obj.get("desc").getAsString());

                    }

                } catch (Exception e) {
                    Log.e("placeOrderKraken", "onResponse: ", e);
                    callBack.onFailed("Error");

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("placeOrderKraken", "onFailure: ", t);
                callBack.onFailed("Error");
            }
        });
    }


    public void addToKrakenLog(Userinfo senderInfo, UserDetails receiverInfo,
                               String transid, String desc, String amountInEuro,
                               String amountInVnd, String amountInBtc, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        api.krakenAddToLog(senderInfo.getId(),
                receiverInfo.getId(), transid, desc, amountInEuro, amountInVnd,
                senderInfo.getExchangeid(),
                receiverInfo.getExchangeid(), "0", "", amountInBtc).enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                try {
                    Log.d("ADD KRAKEN LOG", "Raw Json Response: " + response.raw().body().toString());

                    JsonObject obj = response.body().getAsJsonObject();
                    String result = obj.get("response").getAsString();

                    if (result.equalsIgnoreCase("success")) {
                        callBack.onSuccess("response");
                    } else {
                        Utilz.tmsgError(activity, "Failed");
                        callBack.onFailed();
                    }

                } catch (Exception e) {
                    Log.e("addToKrakenLog", "onResponse: ", e);
                    Utilz.tmsgError(activity, "Failed");
                }


                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("addToKrakenLog", "Add To kraken Log Fee : onFailure : ", t);
                Utilz.tmsgError(activity, "Add To kraken Log: Failed");
                callBack.onFailed();
                progressDialog.dismiss();

            }

        });

    }


    public void addToLog(Userinfo senderInfo, UserDetails receiverInfo,
                         String transid, String desc, String amountInEuro,
                         String amountInVnd, String amountInBtc, String curr_id, String enteredAmount,
                         String convertedAmount, String recevAmount, String withdrawalamount, String type,
                         final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.AddToLog(senderInfo.getId(),
                receiverInfo.getId(), transid, desc, amountInEuro, amountInVnd,
                senderInfo.getExchangeid(),
                receiverInfo.getExchangeid(), "0", "",
                amountInBtc, curr_id, enteredAmount, convertedAmount,
                recevAmount, withdrawalamount, type).enqueue(new Callback<JsonElement>() {

            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {


                try {

                    Log.d("ADD LOG", "Raw Json Response: " + response.raw().body().toString());

                    JsonObject obj = response.body().getAsJsonObject();
                    String result = obj.get("response").getAsString();

                    if (result.equalsIgnoreCase("success")) {
                        callBack.onSuccess("response");
                    } else {
                        callBack.onFailed();
                    }

                } catch (Exception e) {
                    Log.e("addToLog", "onResponse: ", e);
                    callBack.onFailed();

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("addToLog", "Add To Log Fee : onFailure : ", t);
                callBack.onFailed();

            }

        });

    }


    public void btcToEuro(String amountInBtc, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        api.btcToEuro(amountInBtc, "allow").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response == null || !response.isSuccessful()) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }

                String convertedAmount = response.body().replace(",", ".");

                callBack.onSuccess(convertedAmount);

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("btcToEuro", "onFailure : ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });

    }


    public void newBtcToEuro(String amountInBtc, final CallBacks.NewApiCallback callback) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.newBtcToEuro(amountInBtc, "allow").enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response == null || !response.isSuccessful()) {
                    callback.onFailed("Error");
                    return;
                }


                JsonObject jsonObject = response.body().getAsJsonObject();

                String euroAmount = jsonObject.get("btctoeuro").getAsString();
                String exchangerate = jsonObject.get("exchangerate").getAsString();

                callback.onSuccess(euroAmount, exchangerate);

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("btcToEuro", "onFailure : ", t);
                callback.onFailed("Error");
            }
        });

    }


    public void convertBtcToPkr(final String amountInBtc, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final String ss = amountInBtc.replace(",", ".");

        final ProgressDialog sp = ProgressDialog.show(activity, "Please Wait", "Loading...");
        api.BtcToPkr("allow", ss).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null && !response.body().isEmpty()) {
                    String convertedAmount = response.body().replace(",", ".");

                    double am = Double.parseDouble(convertedAmount);

                    String am1 = new DecimalFormat("##.##").format(am);
                    String am2 = "";

                    if (am1.contains(",")) {
                        am2 = am1.replace(",", ".");
                    } else {
                        am2 = am1;
                    }

                    am = Double.parseDouble(am2);

                    String amountInBtcToPkr = am + "";
//                    placeOrderKraken();

                    callBack.onSuccess(amountInBtcToPkr);

                    sp.dismiss();

                } else {
                    callBack.onFailed();
                    sp.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("convertBtcToPkr", "onFailure: " + t.toString());
                callBack.onFailed();
                sp.dismiss();
            }
        });
    }


    public void newConvertBtcToPkr(final String amountInBtc, final CallBacks.NewApiCallback callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final String ss = amountInBtc.replace(",", ".");

        api.newBtcToPkr("allow", ss).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {


                    JsonObject jsonObject = response.body().getAsJsonObject();

                    String btcAmount = jsonObject.get("amountinpkr").getAsString();
                    String exchangerate = jsonObject.get("exchangerate").getAsString();


                    if (Utilz.checkString(btcAmount) || Utilz.checkString(exchangerate))
                        callBack.onServiceDown();
                    else
                        callBack.onSuccess(btcAmount, exchangerate);

                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onFailed("Error");
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("convertBtcToPkr", "onFailure: " + t.toString());
                callBack.onFailed("Error");
            }
        });


    }


    public void createNewOrder(String amountInBtc, String amountInSotashi, String amountInBtcToPkr,
                               String senderId, String receiverId, String receiverExchangeId,
                               final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.createNewOrder(amountInSotashi,
                amountInBtc,
                amountInBtcToPkr,
                senderId,
                receiverId,
                receiverExchangeId

        ).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {

                    JsonObject obj = response.body().getAsJsonObject();

                    String status = obj.get("status").getAsString();

                    if (status.equalsIgnoreCase("200")) {

                        callBack.onSuccess("");

                    } else {
                        callBack.onFailed();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onFailed();


                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("createNewOrder", "onFailure: " + t.toString());
                callBack.onFailed();

            }
        });
    }


    public void euroToBtc(String amountInEuro, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");


        api.euroToBtc(amountInEuro).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response == null || !response.isSuccessful()) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }


                String convertedAmount = response.body().replace(",", ".");

                callBack.onSuccess(convertedAmount);


                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("EuroToBtc", "onFailure: ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });

    }


    public void newEuroToBtc(String amountInEuro, final CallBacks.NewApiCallback callback) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        api.newEuroToBtc(amountInEuro).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response == null || !response.isSuccessful()) {
                    Utilz.tmsgError(activity, "Error");
                    callback.onFailed("Error");
                    return;
                }


                JsonObject jsonObject = response.body().getAsJsonObject();

                String btcAmount = jsonObject.get("amountinbtc").getAsString();
                String exchangerate = jsonObject.get("exchangerate").getAsString();

                callback.onSuccess(btcAmount, exchangerate);


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("EuroToBtc", "onFailure: ", t);
                callback.onFailed("Error");
            }
        });

    }


    public void convertToBtc(String amount, String currency, final CallBacks.BtcCallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_3).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(activity, "Please Wait", "loading...");
        api.getCurrencyValue(currency, amount).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                amountInBtc = response.body();
//                amountInSotashi = convertToSatoshi(response.body());

                callBack.onSuccess(response.body(), convertToSatoshi(response.body()));

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();

            }
        });
    }


    public void btctoClp(String btc, final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String btcA = btc.replace(",", ".");

        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        api.btcToClp("allow", btcA).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }

                String convertedAmount = response.body().replace(",", ".");

                if (convertedAmount == null || convertedAmount.equalsIgnoreCase("null") || convertedAmount.isEmpty()) {
                    convertedAmount = "0.00";
                }

                String a = convertedAmount.replace(",", ".");

                double am = Double.parseDouble(a);

                String am1 = new DecimalFormat("##.##").format(am);
                String am2 = "";

                if (am1.contains(",")) {
                    am2 = am1.replace(",", ".");
                } else {
                    am2 = am1;
                }


                am = Double.parseDouble(am2);

                callBack.onSuccess(am + "");

                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("btcToClp", "onFailure: ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });

    }


    public void newBtctoClp(String btc, final CallBacks.NewApiCallback callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String btcA = btc.replace(",", ".");

        api.newBtcToClp("allow", btcA).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed("Error");
                    return;
                }

                try {

                    JsonObject jsonObject = response.body().getAsJsonObject();

                    String clpAmount = jsonObject.get("btctoclp").getAsString();
                    String exchangerate = jsonObject.get("exchangerate").getAsString();

                    if (checkString(clpAmount) || checkString(exchangerate))
                        callBack.onServiceDown();
                    else
                        callBack.onSuccess(clpAmount, exchangerate);


                } catch (Exception e) {
                    e.printStackTrace();
                    callBack.onFailed("Error");

                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("btcToClp", "onFailure: ", t);
                callBack.onFailed("Error");

            }
        });

    }


    public void clpToBtc(String clp, final CallBacks.BtcCallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog progressDialog = ProgressDialog.show(activity, "Please Wait", "Loading...");

        api.clpToBtc("allow", clp).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (!response.isSuccessful() || response == null) {
                    Utilz.tmsgError(activity, "Error");
                    callBack.onFailed();
                    progressDialog.dismiss();
                    return;
                }


                String convertedAmount = response.body().replace(",", ".");

                callBack.onSuccess(convertedAmount, convertToSatoshi(convertedAmount));


                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("clpToBtc", "onFailure: ", t);
                callBack.onFailed();
                progressDialog.dismiss();
            }
        });

    }


    public void clpAddOrder(String amountInSatochi,
                            String amountInBtc,
                            String amountInClp,
                            String senderId,
                            String receiverId,
                            String exchangeId,
                            String apiKey,
                            String apiSecret,
                            final CallBacks.CallBack callBack) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.clpAddOrder(amountInSatochi, amountInBtc, amountInClp,
                senderId, receiverId, exchangeId, apiKey, apiSecret).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                try {

                    if (response == null || !response.isSuccessful()) {
                        Utilz.tmsgError(activity, "Error");
                        callBack.onFailed();
                        return;
                    }

                    try {

                        JsonObject obj = response.body().getAsJsonObject();

                        String status = obj.get("status").getAsString();

                        if (status.equalsIgnoreCase("200")) {
                            Utilz.tmsgSuccess(activity, "Order Placed Successfully");
                            callBack.onSuccess("response");
                        } else {
                            Utilz.tmsgError(activity, "Error" + obj.get("description").getAsString());
                            callBack.onFailed();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        callBack.onFailed();

                    }


                } catch (Exception e) {
                    Log.e("clpAddOrder", "onResponse: ", e);
                    callBack.onFailed();
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("clpaddorder", "onFailure: ", t);
                callBack.onFailed();
            }
        });

    }


    public void converter(String from, String to, String amount, CallBacks.CallBack l) {


        WebServices api = Utilz.getRxRetrofit(Utilz.AppConstants.CURRENCYLAYER_BASE_URL).create(WebServices.class);


        api.currencyLayerConverter(Utilz.AppConstants.CURRENCYLAYER_API_CODE,
                from.toUpperCase(), to.toUpperCase(), amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    l.onFailed();
                    Log.e("converter", "CurrencyConverterToBtc: ", throwable);
                })
                .subscribe(data -> {

                    try {

                        if (data == null || !data.isSuccessful()) {
                            l.onFailed();
                            return;
                        }


                        if (data.body().getSuccess()) {

                            String a = data.body().getResult().toString().replace(",", ".");
                            l.onSuccess(a);

                        } else {
                            l.onFailed();
                        }

                    } catch (Exception e) {
                        l.onFailed();
                        Log.e("converter", "CurrencyConverterToBtc: ", e);
                    }

                });


    }


    public static class CallBacks {

        public interface CallBack {
            void onSuccess(String response);

            void onFailed();
        }

        public interface BtcCallBack {
            void onSuccess(String amountInBtc,
                           String amountInSatoshi);

            void onFailed();
        }

        public interface placeOrderKrakenCallBack {
            void onSuccess(String transid,
                           String desc);

            void onFailed(String error);
        }


        public interface NewApiCallback {
            void onSuccess(String val1,
                           String val2);

            void onFailed(String error);

            void onServiceDown();
        }
    }
}
