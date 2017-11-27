package ss.plingpay.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.pojos.Exchange;
import ss.plingpay.pojos.ExchangeListPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.adapters.AdapterExchangeList;
import ss.plingpay.ui.adapters.ExchangeMethodAdapter;
import ss.plingpay.ui.adapters.SpinnerCountries;
import ss.plingpay.ui.dialogFragments.CurrencySelectorSetting;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/24/2016.
 */

@Deprecated
public class SettingActivity extends BaseActivity implements Listeners.WalletChangeListener, Listeners.CurrencySelecter {


    private static final String TAG = SettingActivity.class.getName().toUpperCase();
    public static final String CASHJSBANK = "Cash withdrawal at Js Bank";


    @BindView(R.id.frag_setting_firstName)
    EditText etFirstname;
    @BindView(R.id.frag_setting_lastName)
    EditText etLirstname;
    @BindView(R.id.frag_setting_phoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.frag_setting_address)
    EditText etAddress;
    @BindView(R.id.frag_setting_address2)
    EditText etAddress2;
    @BindView(R.id.frag_setting_city)
    EditText etCity;
    @BindView(R.id.frag_setting_state)
    EditText etStatename;
    @BindView(R.id.frag_setting_zipCode)
    EditText etZipCodename;

    @BindView(R.id.frag_setting_bankaccountnumber)
    EditText accountNumber;

    @BindView(R.id.frag_setting_bankname)
    EditText bankName;

    @BindView(R.id.frag_setting_bankccountTitle)
    EditText accountTitle;


    @BindView(R.id.frag_setting_bankaccountAddress)
    EditText accountAddress;
    @BindView(R.id.frag_setting_bankAccountCnic)
    EditText acountCnic;

    @BindView(R.id.frag_setting_selectCountry)
    Spinner spinnerCountry;

    @BindView(R.id.activity_setting_selectCurrency)
    TextView tvCurrency;


    @BindView(R.id.fragment_setting_spn_exchange)
    Spinner spinnerExchange;

    @BindView(R.id.fragment_setting_spn_exchange_method)
    Spinner spinnerMthod;

    @BindView(R.id.urduBitFields)
    LinearLayout urdubitFields;

    private String currr_id;

    @BindView(R.id.tilCnic)
    TextInputLayout tilCnic;


    @BindView(R.id.frag_setting_bankaccountRut)
    EditText bankAccountRutForClp;
    @BindView(R.id.frag_setting_bankaccountAccountType)
    EditText bankAccountTypeForClp;

    @BindView(R.id.frag_setting_bankaccountRuttil)
    TextInputLayout tilbankAccountRutForClp;
    @BindView(R.id.frag_setting_bankaccountAccountTypetil)
    TextInputLayout tilbankAccountTypeForClp;


    private Userinfo info;
    private String countryId;
    private String currencyId;
    private String defaultAddress;
    private ArrayList<String> methods;
    private List<Exchange> exchangeList;
    private String exchangeId;
    private String exchangeMethod;
    private boolean isJsBankSelected = false;
    private RealmResults<CountriesList> coutryListRealm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        info = getApp().getAuth().getUser().getUserinfo();

        populateCountrySpinner();


        etFirstname.setText(info.getFirstName());
        etLirstname.setText(info.getLastName());
        etPhoneNumber.setText(info.getPhone());
        etAddress.setText(info.getAddress1());
        etAddress2.setText(info.getAddress2());
        etCity.setText(info.getCity());
        etZipCodename.setText(info.getZip());
        etStatename.setText(info.getState());


        tvCurrency.setText(info.getCurrancyId());

        _currenc = info.getCurrancyId();
        currr_id = info.getCurr_id();

        if (info.getCurrancyId().equalsIgnoreCase("pkr")) {

            getExchange(info.getCurr_id(), info.getCurrancyId());

//            urdubitFields.setVisibility(View.VISIBLE);

            //TODO makeBankFieldsOptional = false


            spinnerMthod.setVisibility(View.VISIBLE);
            spinnerExchange.setVisibility(View.VISIBLE);

            accountNumber.setText(info.getBankaccountnumber());
            bankName.setText(info.getBankname());
            accountTitle.setText(info.getAccounttilte());

            acountCnic.setText(info.getCnic());
            accountAddress.setText(info.getAddress());


            methods = new ArrayList<>();

            methods.add("Receive to bank account");
            methods.add(CASHJSBANK);

            ExchangeMethodAdapter adap = new ExchangeMethodAdapter(this, methods);
            spinnerMthod.setAdapter(adap);


            int currenct = getIndex(spinnerMthod, info.getWithdrawaltypetext());

            spinnerMthod.setSelection(currenct);


        } else if (info.getCurrancyId().equalsIgnoreCase("vnd") || info.getCurrancyId().equalsIgnoreCase("clp")) {

            getExchange(info.getCurr_id(), info.getCurrancyId());

//            urdubitFields.setVisibility(View.VISIBLE);

            //TODO makeBankFieldsOptional = false


            spinnerMthod.setVisibility(View.VISIBLE);
            spinnerExchange.setVisibility(View.VISIBLE);

            if (info.getCurrancyId().equalsIgnoreCase("clp")) {

                tilbankAccountRutForClp.setVisibility(View.VISIBLE);
                tilbankAccountTypeForClp.setVisibility(View.VISIBLE);
                bankAccountRutForClp.setText(info.getRut());
                bankAccountTypeForClp.setText(info.getAccounttype());

            } else {

                tilbankAccountRutForClp.setVisibility(View.GONE);
                tilbankAccountTypeForClp.setVisibility(View.GONE);

            }

            accountNumber.setText(info.getBankaccountnumber());
            bankName.setText(info.getBankname());
            accountTitle.setText(info.getAccounttilte());
            accountAddress.setText(info.getAddress());


            methods = new ArrayList<>();

            methods.add("Receive to bank account");

            ExchangeMethodAdapter adap = new ExchangeMethodAdapter(this, methods);
            spinnerMthod.setAdapter(adap);

            int currenct = getIndex(spinnerMthod, info.getWithdrawaltypetext());
            spinnerMthod.setSelection(currenct);


        } else {

//            urdubitFields.setVisibility(View.GONE);
            //TODO makeBankFieldsOptional = true
            getExchange(info.getCurr_id(), info.getCurrancyId());


            spinnerMthod.setVisibility(View.GONE);
            spinnerExchange.setVisibility(View.GONE);

        }


        final RealmResults<CurrencyList> result = realm.where(CurrencyList.class).findAll();

        tvCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String id = "";
                String cur = "";

                boolean isFound = false;

                for (int i = 0; i < result.size(); i++) {
                    if (tvCurrency.getText().toString().trim().equalsIgnoreCase(result.get(i).getCurrancyName())) {
                        id = result.get(i).getId();
                        cur = result.get(i).getCurrancyName();
                        isFound = true;
                    }
                }

                getExchange(id, cur);
            }
        });

    }

    @OnClick(R.id.activity_setting_selectCurrency)
    void onSelectCurrencyClick() {

//        urdubitFields.setVisibility(View.GONE);
        spinnerMthod.setVisibility(View.GONE);
        spinnerExchange.setVisibility(View.GONE);


        CurrencySelectorSetting dialog = new CurrencySelectorSetting();
        dialog.show(getSupportFragmentManager(), CurrencySelectorSetting.class.getName());
        dialog.setCancelable(false);
    }

    @Override
    public void selectedItem(final String currency, String id) {

        tvCurrency.setText(currency);
        _currenc = currency;
        currr_id = id;
        getExchange(id, currency);

    }

    private void getExchange(String id, final String currency) {
        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog d = ProgressDialog.show(this, "Please Wait", "Loading...");

        api.getExchangeList(id, "").enqueue(new Callback<ExchangeListPOJO>() {
            @Override
            public void onResponse(Call<ExchangeListPOJO> call, Response<ExchangeListPOJO> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Utilz.tmsgError(SettingActivity.this, "Something Went Wrong");
                    d.dismiss();
                    return;
                }


                if (response.body().getResponse().equalsIgnoreCase("success")) {


                    if (response.body().getExchanges().size() < 0) {
                        spinnerExchange.setVisibility(View.GONE);
                        Utilz.tmsgInfo(SettingActivity.this, "No Exchange Found");
                        d.dismiss();
                        return;
                    }


                    exchangeList = response.body().getExchanges();

                    populateExchangeSpn(response.body().getExchanges(), currency);
                }

                d.dismiss();


            }

            @Override
            public void onFailure(Call<ExchangeListPOJO> call, Throwable t) {
                Utilz.tmsgError(SettingActivity.this, "Something Went Wrong, Please check your internet connection");
                Log.e(TAG, "onFailure: ", t);
                d.dismiss();


            }
        });
    }

    private String _currenc;

    private void populateExchangeSpn(List<Exchange> exchanges, String currency) {
        spinnerExchange.setVisibility(View.VISIBLE);
        AdapterExchangeList adapter = new AdapterExchangeList(this, exchanges);
        spinnerExchange.setAdapter(adapter);

        _currenc = currency;

        if (currency.equalsIgnoreCase("pkr")) {
            spinnerMthod.setVisibility(View.VISIBLE);

            methods = new ArrayList<>();

            methods.add("Receive to bank account");
            methods.add(CASHJSBANK);

            ExchangeMethodAdapter adap = new ExchangeMethodAdapter(this, methods);
            spinnerMthod.setAdapter(adap);

            urdubitFields.setVisibility(View.VISIBLE);


            int currenct = getIndex(spinnerMthod, info.getWithdrawaltypetext());

            spinnerMthod.setSelection(currenct);

            tilbankAccountRutForClp.setVisibility(View.GONE);
            tilbankAccountTypeForClp.setVisibility(View.GONE);


        } else if (currency.equalsIgnoreCase("vnd") || currency.equalsIgnoreCase("clp")) {

            spinnerMthod.setVisibility(View.VISIBLE);


            if (currency.equalsIgnoreCase("clp")) {

                tilbankAccountRutForClp.setVisibility(View.VISIBLE);
                tilbankAccountTypeForClp.setVisibility(View.VISIBLE);

            } else {

                tilbankAccountRutForClp.setVisibility(View.GONE);
                tilbankAccountTypeForClp.setVisibility(View.GONE);

            }


            methods = new ArrayList<>();

            methods.add("Receive to bank account");

            ExchangeMethodAdapter adap = new ExchangeMethodAdapter(this, methods);
            spinnerMthod.setAdapter(adap);

            urdubitFields.setVisibility(View.VISIBLE);

            int currenct = getIndex(spinnerMthod, info.getWithdrawaltypetext());

            spinnerMthod.setSelection(currenct);

        }


    }

    @OnItemSelected(R.id.fragment_setting_spn_exchange)
    void OnItemClickExhange(int position) {
        exchangeId = exchangeList.get(position).getId();
    }

    private String withdrawaltypetext;

    @OnItemSelected(R.id.fragment_setting_spn_exchange_method)
    void OnItemClickExhangeMethod(int position) {


        withdrawaltypetext = methods.get(position);

        if (this._currenc.equalsIgnoreCase("pkr")) {

            if (methods.get(position).equalsIgnoreCase("Receive to bank account") ||
                    methods.get(position).equalsIgnoreCase("Bank Transfer") ||
                    methods.get(position).equalsIgnoreCase("Other")
                    ) {

                exchangeMethod = "other";
                isJsBankSelected = false;
                tilCnic.setVisibility(View.GONE);


            } else if (methods.get(position).equalsIgnoreCase(CASHJSBANK)) {

                exchangeMethod = "other";
                isJsBankSelected = true;

                tilCnic.setVisibility(View.VISIBLE);


            }


        } else if (this._currenc.equalsIgnoreCase("vnd") || this._currenc.equalsIgnoreCase("clp")) {

            if (methods.get(position).equalsIgnoreCase("Receive to bank account") ||
                    methods.get(position).equalsIgnoreCase("Bank Transfer")) {
                exchangeMethod = "banktransfer";
                isJsBankSelected = false;
                tilCnic.setVisibility(View.GONE);


            }

//            else if (methods.get(position).equalsIgnoreCase("Cash withdrawal at Js Bank")) {
//                exchangeMethod = "jsbankcoc";
//            }


        }


    }

    @OnClick(R.id.frag_setting_btnSync)
    void onSyncClick() {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String firstName = etFirstname.getText().toString();
        String lastName = etLirstname.getText().toString();
        String phone = etPhoneNumber.getText().toString();
        String address = etAddress.getText().toString();
        String address2 = etAddress2.getText().toString();
        String city = etCity.getText().toString();
        String zip = etZipCodename.getText().toString();
        String state = etStatename.getText().toString();

        final String bankname = bankName.getText().toString();
        final String acctNumber = accountNumber.getText().toString();


        final String acctType = accountNumber.getText().toString();
        final String rut = accountNumber.getText().toString();


        String acctTitle = null;

        if (!isJsBankSelected)

            if (accountTitle.getText().toString().contains(Utilz.AppConstants.JSBANKCOC))
                acctTitle = accountTitle.getText().toString().replace(Utilz.AppConstants.JSBANKCOC, "");
            else
                acctTitle = accountTitle.getText().toString();

        else {

            if (!accountTitle.getText().toString().contains(Utilz.AppConstants.JSBANKCOC))
                acctTitle = accountTitle.getText().toString() + Utilz.AppConstants.JSBANKCOC;
            else
                acctTitle = accountTitle.getText().toString();

        }


        final String acctAddress = accountAddress.getText().toString();
        final String cnic = acountCnic.getText().toString();


        String _long = "1";
        String lat = "1";


        final ProgressDialog dialog = ProgressDialog.show(this, "Please Wait", "Loading...");

        final String finalAcctTitle = acctTitle;
        api.syncUserDate(
                firstName, lastName, phone, address, address2,
                city, state, countryId, zip,
                tvCurrency.getText().toString().trim(), _long, lat, info.getId(), acctNumber, bankname, acctTitle,
                exchangeMethod == null ? "" : exchangeMethod,
                exchangeId == null ? "" : exchangeId, cnic, acctAddress, withdrawaltypetext
                , acctType, rut
        )
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        if (response.isSuccessful()) {
                            JsonObject obj = response.body().getAsJsonObject();
                            String status = obj.get("return").getAsString();

                            if (status.equalsIgnoreCase("success")) {
                                Utilz.tmsgSuccess(SettingActivity.this, "Saved Successfully");

                                //TODO update values in in app

                                getApp().getAuth().getUser().getUserinfo().setFirstName(etFirstname.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setLastName(etLirstname.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setPhone(etPhoneNumber.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setAddress1(etAddress.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setAddress2(etAddress2.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setCity(etCity.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setZip(etZipCodename.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setZip(etZipCodename.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setCountry(countryId);


                                getApp().getAuth().getUser().getUserinfo().setCnic(acountCnic.getText().toString());
                                getApp().getAuth().getUser().getUserinfo().setAddress(accountAddress.getText().toString());


                                getApp().getAuth().getUser().getUserinfo().setAccounttilte(finalAcctTitle);
                                getApp().getAuth().getUser().getUserinfo().setBankname(bankname);
                                getApp().getAuth().getUser().getUserinfo().setBankaccountnumber(acctNumber);

                                getApp().getAuth().getUser().getUserinfo().setCurr_id(currr_id);
                                getApp().getAuth().getUser().getUserinfo().setCurrancyId(_currenc);

                                getApp().getAuth().getUser().getUserinfo().setAccounttype(acctType);
                                getApp().getAuth().getUser().getUserinfo().setRut(rut);


                                getApp().getAuth().getUser().getUserinfo().setWithdrawaltypetext(withdrawaltypetext);


                                getApp().getAuth().getUser().getUserinfo().setCurrancyId(tvCurrency.getText().toString().trim());
                                getApp().getAuth().getUser().getUserinfo().setExchangeid(exchangeId == null ? "" : exchangeId);

                                getApp().getAuth().getUser().getUserinfo().setCurrencyIdWithListener(tvCurrency.getText().toString().trim());
                                getApp().getAuth().getUser().getUserinfo().setFirstNameWithListener(etFirstname.getText().toString());


                            } else {
                                Utilz.tmsgError(SettingActivity.this, "Saving Failed. Try Again Please");
                            }
                        }

                        dialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t);
                        Utilz.tmsgError(SettingActivity.this, "Saving Failed. Try Again Please");
                        dialog.dismiss();
                    }
                });
    }


    private void populateCountrySpinner() {

        coutryListRealm = realm.where(CountriesList.class).findAll();

        SpinnerCountries adapter = new SpinnerCountries(SettingActivity.this, coutryListRealm);
        spinnerCountry.setAdapter(adapter);

        spinnerCountry.setSelection(getIndex(spinnerCountry, info.getCountry()), true);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                countryId = coutryListRealm.get(i).getCountryName();
//                tvCurrency.setText(Utilz.getCountryCurrency(countryId));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        String a = myString;
//        String b = spinner.getItemAtPosition(4).toString();
        String b = spinner.getItemAtPosition(5).toString();


        for (int i = 0; i < spinner.getCount(); i++) {
//            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
//                index = i;
//                break;
//            }


            if (coutryListRealm.get(i).getCountryName().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }

        }
        return index;
    }


    @OnClick(R.id.fragment_setting_btnChangeGuid)
    void onChangeWallet() {
//        DialogFragment dialog = new WalletChangeDialog();
//        dialog.show(getSupportFragmentManager(), WalletChangeDialog.class.getName());
    }

    @Override
    public void onChangeClick(String id, final String address) {


        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        Call<JsonElement> call = api.makeDefaultWallet(getApp().getAuth().getUser().getUserinfo().getId(), id, address);


        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                if (response == null || !response.isSuccessful()) {
                    Utilz.tmsgError(SettingActivity.this, "Something Went Wrong");
                    return;
                }

                JsonObject obj = response.body().getAsJsonObject();
                String status = obj.get("response").getAsString();

                if (status.equalsIgnoreCase("success")) {

                    String default_address = address;
                    Userinfo userinfo = getApp().getAuth().getUser().getUserinfo();
                    userinfo.setDefaultAddress(default_address);

                    getApp().getAuth().getUser().setUserinfo(userinfo);

                    Utilz.tmsgSuccess(SettingActivity.this, "Wallet Changed Successfully");
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Utilz.tmsg(SettingActivity.this, "Something Went Wrong");
                Log.e(TAG, "onFailure: ", t);
            }
        });


        finish();
        startActivity(getIntent());
    }


}
