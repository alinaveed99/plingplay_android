package ss.plingpay.utilz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * Created by samar_000 on 8/15/2016.
 */

public class Utilz {

    public class AppConstants {

        public final static String CURRENCYLAYER_API_CODE = "51efb685e08e45db9307d2692f0eb0a3";
        public final static String CURRENCYLAYER_BASE_URL = "https://apilayer.net/api/";
        public final static String FULL_CURRENCYLAYER_BASE_URL = "https://apilayer.net/api/convert";

        public final static String API_CODE = "ddcd937b-cb25-4e49-a824-684fc1753de3";

        public final static String BASE_URL_1 = "http://bit4m.org/admin/";
        public final static String BASE_URL_2 = "http://52.26.178.135:3000/";
        public final static String BASE_URL_3 = "https://blockchain.info/";


        public final static String JSBANKCOC = " (JSBANKCOC)";

        public final static String TYPE_NORMAL_TRANSACTION = "0";
        public final static String TYPE_INVOICE_TRANSACTION = "1";
        public final static String TYPE_FEE_TRANSACTION = "2";


        public final static String INVOICE_PENDING = "0";
        public final static String INVOICE_APPROVE = "1";
        public final static String INVOICE_MODIFY_APPROVE = "2";
        public final static String INVOICE_CANCEL = "3";


        public final static String MY_PREFS_NAME = "mypref";

        public final static String BTC = "BTC";


        public static final String NOT_SUPPORTED = "Transaction Not Supported";
        public static final String FAILED_TRANSACTION = "failed";
        public static final String DATA = "data";

        public static final String REG_URL = "https://docs.google.com/forms/d/e/1FAIpQLSffrXzfPWYzW2Y2Q2idJH09YLrldzLaPcHMVK3fXUxYhFRzwA/viewform";
    }

    public final static String[] PAYMENT_METHOD = new String[]{"Payment", "Request", "Rec...", "Rec...", "BuyBtc"};

    public static void tmsg(Context context, String message) {
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Toasty.normal(context, message, Toast.LENGTH_LONG).show();
    }

    public static void tmsgError(Context context, String message) {
        Toasty.error(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void tmsgSuccess(Context context, String message) {
        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void tmsgInfo(Context context, String message) {
        Toasty.info(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showAlert(Context c, String t, String m) {
        AlertDialog dialog = new AlertDialog.Builder(c).
                setTitle(t)
                .setMessage(m)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();
        dialog.show();

    }

    public static void showAlert(Context c, String t, String m, Listeners.DialogListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(c).
                setTitle(t)
                .setMessage(m)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    listener.OnOkClick();
                    dialogInterface.dismiss();
                })
                .setCancelable(false)
                .create();
        dialog.show();

    }

    public static void showAlertWithCancel(Context c, String t, String m, Listeners.DialogListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(c).
                setTitle(t)
                .setMessage(m)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    listener.OnOkClick();
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancel", ((dialog1, which) -> dialog1.dismiss()))
                .setCancelable(false)
                .create();
        dialog.show();

    }

    public static void showAlertWithOKCancel(Context c, String t, String m, Listeners.NewDialogListener listener) {
        AlertDialog dialog = new AlertDialog.Builder(c).
                setTitle(t)
                .setMessage(m)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    listener.OnOkClick();
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancel", (dialog1, which) -> {

                    listener.OnCancelClick();
                })
                .setCancelable(false)
                .create();
        dialog.show();

    }


    public static boolean checkString(String s) {
        return s == null || s.isEmpty() || s.equalsIgnoreCase("null");
    }

    public static String calculatePercentage(String amountInBtc) {

        checkNotNull(amountInBtc);

        double bitcoin = Double.parseDouble(Utilz.removeComma(amountInBtc));

        double companyFeee = (bitcoin / 100) * 0.5;

        double total = bitcoin + companyFeee;

        return total + "";

    }

    public static String calculatePercentageEuro(String amountInEuro) {

        checkNotNull(amountInEuro);

        double euro = Double.parseDouble(Utilz.removeComma(amountInEuro));

        double companyFeee = (euro / 100) * 0.5;

        double total = euro + companyFeee;

        return total + "";

    }


    public static String calculatePercentageOfAmount(String amount) {

        checkNotNull(amount);

        double am = Double.parseDouble(Utilz.removeComma(amount));

        double companyFeee = (am / 100) * 0.5;

        double total = am + companyFeee;

        return total + "";

    }


    public static boolean validateField(EditText et) {

        return !et.getText().toString().isEmpty();

    }


    public static String satoshiToBtc(double currentBalance) {

        double sotashi = 100000000.0;
        double balance = currentBalance / sotashi;

        NumberFormat formatter = new DecimalFormat("#0.00000000");

        return formatter.format(balance);
    }

    public static String convertToSatoshi(String Btc) {
        String a = Btc.replace(",", ".");

        int calculated = (int) (Double.valueOf(a) * 100000000);

        return String.valueOf(calculated);

    }

    @NonNull
    public static Retrofit getRetrofit(@NonNull String url) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(provideOkHttpClient())
                .build();
    }


    @NonNull
    public static Retrofit getRxRetrofit(String url) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();
    }


    public static ProgressDialog ProgressD(Context c) {
        ProgressDialog pd = new ProgressDialog(c);
        pd.setMessage("Loading...");
        pd.setTitle("Please Wait");
        return pd;
    }


    public static String removeComma(String s) {
        checkNotNull(s);
        return s.replace(",", ".");
    }


    public static String getCountryCurrency(String country) {

        String currency = "";

        switch (country) {

            case "Pakistan":
                currency = "PKR";
                break;

            case "Australia":
                currency = "AUD";
                break;

            case "Brazil":
                currency = "brl";
                break;

            case "Canada":
                currency = "CAD";
                break;

            case "Switzerland":
                currency = "CHF";
                break;

            case "Chile":
                currency = "PKR";
                break;

            case "China":
                currency = "CNY";
                break;

            case "Denmark":
                currency = "DKK";
                break;

            case "Europe":
                currency = "EUR";
                break;

            case "United Kingdom":
                currency = "GBP";
                break;

            case "Hong Kong":
                currency = "HKD";
                break;

            case "Iceland":
                currency = "ISK";
                break;

            case "Japan":
                currency = "JPY";
                break;

            case "South Korea":
                currency = "USD";
                break;

            case "New Zealand":
                currency = "NZD";
                break;

            case "Poland":
                currency = "PLN";
                break;

            case "Russia":
                currency = "RUB";
                break;

            case "Sweden":
                currency = "SEK";
                break;

            case "Singapore":
                currency = "SGD";
                break;

            case "Thailand":
                currency = "YHB";
                break;

            case "Taiwan":
                currency = "YWD";
                break;

            default:
                currency = "BTC";

        }


        return currency;


    }


    public static List<String> getQuestionList() {

        List<String> questions = new ArrayList<>();

        questions.add("None");
        questions.add("What is your childhood nickname?");
        questions.add("What is your favorite animal?");
        questions.add("What is your favorite color?");
        questions.add("What is your pet's name?");

        return questions;
    }


    public static void hideKeyboard(Activity a) {
        InputMethodManager inputManager = (InputMethodManager)
                a.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
