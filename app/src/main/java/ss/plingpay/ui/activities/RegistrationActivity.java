package ss.plingpay.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.soundcloud.android.crop.Crop;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import ss.plingpay.R;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.ui.dialogFragments.AgreementDialog;
import ss.plingpay.ui.dialogFragments.CurrencySelectorReg;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 10/3/2016.
 */

public class RegistrationActivity extends BaseActivity implements Listeners.CurrencySelecter {

    //    private static final String URDU_BIT = "UrduBit";
    private static final String TAG = RegistrationActivity.class.getName().toUpperCase();

    private static final int REQUEST_SELECT_IMAGE = 100;
    protected Realm realm;


    //Mandatory Fields
    @BindView(R.id.fragment_reg_etFirstName)
    EditText etFirstName;
    @BindView(R.id.fragment_reg_etLastName)
    EditText etLastName;
    @BindView(R.id.fragment_reg_etEmail)
    EditText etEmail;
    @BindView(R.id.fragment_reg_etPassword)
    EditText etPassword;
    @BindView(R.id.fragment_reg_etCompany)
    EditText etCompany;
    @BindView(R.id.fragment_reg_etAddress)
    EditText etAddress;
    @BindView(R.id.fragment_reg_etPhone)
    EditText etPhone;

    //Optional Fields
    @BindView(R.id.fragment_reg_showOPtionalText)
    TextView tvShowOptionalText;
    @BindView(R.id.fragment_reg_arrow_down)
    ImageView ivDownArrow;
    @BindView(R.id.fragment_reg_arrow_up)
    ImageView ivUpArrow;

    @BindView(R.id.fragment_register_optionalFields)
    LinearLayout llOptionalFields;

    @BindView(R.id.fragment_reg_etCountry)
    EditText etCountry;
    @BindView(R.id.fragment_reg_etCity)
    EditText etCity;
    @BindView(R.id.fragment_reg_etState)
    EditText etState;
    @BindView(R.id.fragment_reg_etAddress2)
    EditText etAddress2;
    @BindView(R.id.fragment_reg_etZipCode)
    EditText etZipCode;


    //    AppCompatSpinner spCurrency;
//    @BindView(R.id.fragment_reg_spn_exchange)
//    AppCompatSpinner spExchange;
//    @BindView(R.id.fragment_reg_spn_exchange_method)
//    AppCompatSpinner spExchangeMethod;

    //Urdubit Fields
    @BindView(R.id.fragment_reg_exchange_urdubit)
    LinearLayout llUrduBitFields;

    @BindView(R.id.fragment_reg_et_bank_accountNumber)
    EditText etAccountNumber;
    @BindView(R.id.fragment_reg_et_accountTitle)
    EditText etAccountTitle;
    @BindView(R.id.fragment_reg_et_bankName)
    EditText etBankName;
    @BindView(R.id.fragment_reg_et_bank_cnic)
    EditText etBankCnic;
    @BindView(R.id.fragment_reg_et_bank_address)
    EditText etBankAddress;

    @BindView(R.id.fragment_reg_til_bank_cninc)
    TextInputLayout tilCinc;


    @BindView(R.id.fragment_reg_til_bank_acctype)
    TextInputLayout tilAcctype;

    @BindView(R.id.fragment_reg_til_bank_rut)
    TextInputLayout tilRut;


    @BindView(R.id.fragment_reg_et_bank_acctype)
    EditText etBankAcctype;

    @BindView(R.id.fragment_reg_et_bank_rut)
    EditText etBankRut;


    //CheckBoxes
    @BindView(R.id.fragment_reg_agreement1)
    AppCompatCheckBox cbAggreement1;
    @BindView(R.id.fragment_reg_agreement)
    AppCompatCheckBox cbAggreement;


    //Image Uploading
    @BindView(R.id.uploadImageOne)
    ImageView ivImageOne;
    @BindView(R.id.uploadImagetwo)
    ImageView ivImageTwo;
    @BindView(R.id.uploadImagethree)
    ImageView ivImageThree;


    @BindView(R.id.fragment_reg_tilFName)
    TextInputLayout tilFname;
    @BindView(R.id.fragment_reg_tilLName)
    TextInputLayout tilLname;
    @BindView(R.id.fragment_reg_tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.fragment_reg_tilPassword)
    TextInputLayout tilPassword;


    @BindView(R.id.fragment_reg_til_bank_accountNumber)
    TextInputLayout tilAccountNumber;
    @BindView(R.id.fragment_reg_til_accountTitle)
    TextInputLayout tilAccountTitle;
    @BindView(R.id.fragment_reg_til_bankName)
    TextInputLayout tilBankName;

//    @BindView(R.id.til_createWallet_password)
//    TextInputLayout tilCreateWalletPassword;

    @BindView(R.id.fragment_reg_selectCurrency)
    TextView tvSelectCurrency;

    @BindView(R.id.fragment_registration_secretQuestions)
    Spinner secretQuestionSpinner;

    @BindView(R.id.fragment_registration_secretAnswer)
    EditText etAnsweer;


    private boolean isImageOneLoaded = false;
    private boolean isImageTwoLoaded = false;
    private boolean isImageThreeLoaded = false;


    private int loadImage;


    private boolean isOptionalFieldVisible = false;
    private boolean isWalletCreateEnable = true;

    private boolean isCurrencySelected = false;

    private RealmResults<CurrencyList> result;
    private String currency;
    //    private String exchange_id;
    private boolean isAgreementCheck;
    private boolean isAgreement2Check;
    //    private boolean isUrdubitSelected = false;
    private File tempOutputFile;
    private File imageOne;
    private File imageTwo;
    private File imageThree;
//    private String selectedCurrency = "USD";

    private ProgressDialog pg;
    private boolean isPkrSelected = false;
    private boolean isClpSelected = false;
//    private List<Exchange> exchangeList;
//    private String exchangeId;
//    private List<String> methods;
//    private String exchangeMethod;
//    private boolean isPkrJSSeleceted = false;

//    private boolean isClp = false;
//    private String secretQuestion;
//    private boolean isSecretAnswerEnable;
//    private boolean isExchangeSelected = false;
//    private boolean isExchangeMethodSelected = false;

    private RxPermissions rxPermissions;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {


        rxPermissions = new RxPermissions(this);

//        secretQuestion();

        pg = new ProgressDialog(this);
        pg.setTitle("Please Wait");
        pg.setMessage("Please Wait");


        realm = Realm.getDefaultInstance();
        result = realm.where(CurrencyList.class).findAll();


        cbAggreement.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                DialogFragment dialog = new AgreementDialog();
                dialog.show(getSupportFragmentManager(), AgreementDialog.class.getName());


            } else {


            }

            isAgreementCheck = isChecked;


        });

        cbAggreement1.setOnCheckedChangeListener((buttonView, isChecked) -> isAgreement2Check = isChecked);
    }


    @OnClick(R.id.fragment_reg_selectCurrency)
    void onSelectCurrencyClick() {
        CurrencySelectorReg dialog = new CurrencySelectorReg();
        dialog.show(getSupportFragmentManager(), CurrencySelectorReg.class.getName());
    }

    @Override
    public void selectedItem(final String currency, String id) {
        tvSelectCurrency.setText(currency);
//        spExchange.setVisibility(View.GONE);
//        spExchangeMethod.setVisibility(View.GONE);


        this.currency = currency;
        isCurrencySelected = true;


        if (currency.equalsIgnoreCase("pkr")) {
            etBankCnic.setVisibility(View.VISIBLE);
            isPkrSelected = true;
        } else {
            etBankCnic.setVisibility(View.GONE);
            isPkrSelected = false;
        }

        if (currency.equalsIgnoreCase("clp")) {
            isClpSelected = true;

            tilAcctype.setVisibility(View.VISIBLE);
            tilRut.setVisibility(View.VISIBLE);

        } else {
            isClpSelected = false;

            tilAcctype.setVisibility(View.GONE);
            tilRut.setVisibility(View.GONE);
        }


//        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);
//
//        final ProgressDialog d = ProgressDialog.show(this, "Please Wait", "Loading...");
//
//        api.getExchangeList(id, "").enqueue(new Callback<ExchangeListPOJO>() {
//            @Override
//            public void onResponse(Call<ExchangeListPOJO> call, Response<ExchangeListPOJO> response) {
//
//                if (!response.isSuccessful() || response.body() == null) {
//                    Utilz.tmsg(RegistrationActivity.this, "Something Went Wrong");
//                    d.dismiss();
//                    return;
//                }
//
//
//                if (response.body().getResponse().equalsIgnoreCase("success")) {
//
//
//                    if (response.body().getExchanges().size() < 0) {
////                        spExchange.setVisibility(View.GONE);
//                        Utilz.tmsg(RegistrationActivity.this, "No Exchange Found");
//                        d.dismiss();
//                        return;
//                    }
//
////                    exchangeList = response.body().getExchanges();
////                    Exchange obj = new Exchange();
////                    obj.setmTitle("Select Exchange");
////                    exchangeList.add(0, obj);
//
//                    populateExchangeSpn(response.body().getExchanges(), tvCurrency);
//                }
//
//                d.dismiss();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ExchangeListPOJO> call, Throwable t) {
//                Utilz.tmsg(RegistrationActivity.this, "Something Went Wrong, Please check your internet connection");
//                Log.e(TAG, "onFailure: ", t);
//                d.dismiss();
//
//
//            }
//        });


    }


    @OnClick(R.id.fragment_reg_showOPtional)
    void onOptionalFieldsClicked() {
        if (isOptionalFieldVisible) {


            tvShowOptionalText.setText("Show Optional Fields");
            ivDownArrow.setVisibility(View.VISIBLE);
            ivUpArrow.setVisibility(View.GONE);
            isOptionalFieldVisible = false;
            llOptionalFields.setVisibility(View.GONE);


        } else {

            tvShowOptionalText.setText("Hide Optional Fields");
            ivDownArrow.setVisibility(View.GONE);
            ivUpArrow.setVisibility(View.VISIBLE);
            isOptionalFieldVisible = true;
            llOptionalFields.setVisibility(View.VISIBLE);

        }
    }


    @OnClick(R.id.fragment_reg_uploadImage1)
    void onUploadImage1Click() {


        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {

                    if (granted) {
                        tempOutputFile = new File(getExternalCacheDir(), "temp-image1.jpg");
                        changeAvatar();
                        loadImage = 1;
                    }


                });


    }

    @OnClick(R.id.fragment_reg_uploadImage2)
    void onUploadImage2Click() {

        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {

                    if (granted) {
                        tempOutputFile = new File(getExternalCacheDir(), "temp-image2.jpg");
                        changeAvatar();
                        loadImage = 2;
                    }


                });


//        tempOutputFile = new File(getExternalCacheDir(), "temp-image2.jpg");
//
//        changeAvatar();
//
//        loadImage = 2;

    }


    @OnClick(R.id.fragment_reg_uploadImage3)
    void onUploadImage3Click() {


        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {

                    if (granted) {
                        tempOutputFile = new File(getExternalCacheDir(), "temp-image3.jpg");
                        changeAvatar();
                        loadImage = 3;
                    }


                });


//        tempOutputFile = new File(getExternalCacheDir(), "temp-image3.jpg");
//
//        changeAvatar();
//
//        loadImage = 3;

    }


    private void changeAvatar() {


        List<Intent> otherImageCaptureIntents = new ArrayList<>();
        List<ResolveInfo> otherImageCaptureActivities = getPackageManager()
                .queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);

        for (ResolveInfo info : otherImageCaptureActivities) {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));
            otherImageCaptureIntents.add(captureIntent);
        }

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);
        selectImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectImageIntent, "Chooser Avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, otherImageCaptureIntents.toArray(new Parcelable[otherImageCaptureActivities.size()]));

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            tempOutputFile.delete();
            return;
        }

        Uri tempFileUri = Uri.fromFile(tempOutputFile);
        if (requestCode == REQUEST_SELECT_IMAGE) {
            Uri outputFileUri;

            if (data != null && (data.getAction() == null ||
                    !data.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE)))
                outputFileUri = data.getData();

            else

                outputFileUri = tempFileUri; // User took a picture

            Crop.of(outputFileUri, tempFileUri)
                    .asSquare()
                    .start(this);

        } else if (requestCode == Crop.REQUEST_CROP) {


            if (loadImage == 1) {

                ivImageOne.setImageResource(0); // Force ImageView to refresh image despite its Uri not changed
                ivImageOne.setImageURI(Uri.fromFile(tempOutputFile));
                imageOne = tempOutputFile;
                isImageOneLoaded = true;

            } else if (loadImage == 2) {

                ivImageTwo.setImageResource(0); // Force ImageView to refresh image despite its Uri not changed
                ivImageTwo.setImageURI(Uri.fromFile(tempOutputFile));
                imageTwo = tempOutputFile;
                isImageTwoLoaded = true;

            } else if (loadImage == 3) {

                ivImageThree.setImageResource(0); // Force ImageView to refresh image despite its Uri not changed
                ivImageThree.setImageURI(Uri.fromFile(tempOutputFile));
                imageThree = tempOutputFile;
                isImageThreeLoaded = true;

            }


        }


    }


    @OnClick(R.id.fragment_reg_btnReg)
    void onRegClick() {

        if (etFirstName.getText().toString().trim().isEmpty()) {
            tilFname.setError("This Field Is Required");
            return;
        } else {
            tilFname.setError(null);
        }

        if (etLastName.getText().toString().trim().isEmpty()) {
            tilLname.setError("This Field Is Required");
            return;
        } else {
            tilLname.setError(null);
        }

        if (etEmail.getText().toString().trim().isEmpty()) {
            tilEmail.setError("This Field Is Required");
            return;
        } else {
            tilEmail.setError(null);
        }


        if (etPassword.getText().toString().trim().isEmpty()) {
            tilPassword.setError("This Field Is Required");
            return;
        } else {
            tilPassword.setError(null);
        }


        if (etPassword.getText().toString().trim().length() < 10) {
            tilPassword.setError("Must be at least 10 characters in length.");
            return;
        } else {
            tilPassword.setErrorEnabled(false);
        }


        //Optional Fields


        if (isOptionalFieldVisible) {
            if (etCompany.getText().toString().trim().isEmpty()) {
                etCompany.setText("");
            }

            if (etAddress.getText().toString().trim().isEmpty()) {
                etAddress.setText("");
            }

            if (etPhone.getText().toString().trim().isEmpty()) {
                etPhone.setText("");
            }


            if (etCountry.getText().toString().trim().isEmpty()) {
                etCountry.setText("");
            }

            if (etCity.getText().toString().trim().isEmpty()) {
                etCity.setText("");
            }

            if (etState.getText().toString().trim().isEmpty()) {
                etState.setText("");
            }

            if (etAddress2.getText().toString().trim().isEmpty()) {
                etAddress2.setText("");
            }

            if (etZipCode.getText().toString().trim().isEmpty()) {
                etZipCode.setText("");
            }


        }


        if (!isCurrencySelected) {
            Utilz.tmsg(this, "Please Select Currency");
            return;
        }


//        if (isUrdubitSelected) {
//
//            if (!isExchangeSelected) {
//                Utilz.tmsgError(this, "Please Select Currency Exchange");
//                return;
//            }
//
//            if (!isExchangeMethodSelected) {
//                Utilz.tmsgError(this, "Please Select Currency Exchange Method");
//                return;
//            }
//
//            if (isPkrJSSeleceted) {
//                if (etBankCnic.getText().toString().trim().isEmpty()) {
//                    etBankCnic.setError("This Field Is Required");
//                    return;
//                }
//
//            }


//            if (etBankAddress.getText().toString().trim().isEmpty()) {
//                etBankAddress.setError("This Field Is Required");
//                return;
//            }

        if (isPkrSelected) {

            if (etBankCnic.getText().toString().trim().isEmpty()) {
                etBankCnic.setError("This Field Is Required");
                return;
            } else {
                etBankCnic.setError(null);
            }


        }


        if (etAccountNumber.getText().toString().trim().isEmpty()) {
            tilAccountNumber.setError("This Field Is Required");
            return;
        } else {
            tilAccountNumber.setError(null);
        }


        if (etAccountTitle.getText().toString().trim().isEmpty()) {
            tilAccountTitle.setError("This Field Is Required");
            return;
        } else {
            tilAccountTitle.setError(null);
        }

        if (etBankName.getText().toString().trim().isEmpty()) {
            tilBankName.setError("This Field Is Required");
            return;
        } else {
            tilBankName.setError(null);
        }


        if (isClpSelected) {

            if (etBankAcctype.getText().toString().trim().isEmpty()) {
                tilAcctype.setError("This Field Is Required");
                return;
            } else {
                tilAcctype.setError(null);
            }

            if (etBankRut.getText().toString().trim().isEmpty()) {
                tilRut.setError("This Field Is Required");
                return;
            } else {
                tilRut.setError(null);
            }

        }


//        }


        if (!isImageOneLoaded || !isImageTwoLoaded || !isImageThreeLoaded) {
            Utilz.tmsgError(this, "Please Upload all Images");
            return;
        }


        if (!isAgreement2Check) {
            cbAggreement1.setError("Error");
            return;
        }

        if (!isAgreementCheck) {
            cbAggreement.setError("Error");
            return;
        }


//        if (isPkrJSSeleceted)
//            accountTitleWithJs = etAccountTitle.getText().toString() + " (JSBANKCOC)";
//        else
//            accountTitleWithJs = etAccountTitle.getText().toString();


        new RegUser().execute();
//        Utilz.tmsgSuccess(this, "Done");
//        regUser();
    }


//    String accountTitleWithJs;

    @BindString(R.string.new_user_message)
    String newUserMessage;

    class RegUser extends AsyncTask<Void, Void, String> {

        private OkHttpClient client;
        private String URL = Utilz.AppConstants.BASE_URL_1 + "users/add";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.show();

            client = new OkHttpClient();
        }


        @Override
        protected String doInBackground(Void... b) {


            String response = null;
            try {
                response = post();
            } catch (IOException e) {
                e.printStackTrace();
                response = null;
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            pg.dismiss();

            if (s != null) {
                try {
                    JSONObject obj = new JSONObject(s);
                    String response = obj.getString("return");

                    if (response.equalsIgnoreCase("success")) {


                        Utilz.tmsg(RegistrationActivity.this, "You are successfully registered to PlingPay");

                        if (isWalletCreateEnable) {
                            String id = obj.getString("id");

//                            onCreateClick(id);

                            pg.setMessage("Creating Wallet");
                            pg.dismiss();


                        }

                        Utilz.showAlert(RegistrationActivity.this, "Note", newUserMessage, () -> {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        });


                        pg.dismiss();


                    } else if (response.equalsIgnoreCase("exists")) {

                        Utilz.tmsgError(RegistrationActivity.this, "This Email already exist, please try something different");
                        pg.dismiss();


                    } else {

                        Utilz.tmsgError(RegistrationActivity.this, "Registration Failed");
                        pg.dismiss();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pg.dismiss();
                }

                Log.d(TAG, "onPostExecute: " + s);
            }
        }

        private String post() throws IOException {

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("tvFirstName", etFirstName.getText().toString())
                    .addFormDataPart("tvLastName", etLastName.getText().toString())
                    .addFormDataPart("email", etEmail.getText().toString())
                    .addFormDataPart("password", etPassword.getText().toString())


                    .addFormDataPart("company", etCompany.getText().toString())
                    .addFormDataPart("address1", etAddress.getText().toString())
                    .addFormDataPart("phone", etPhone.getText().toString())
                    .addFormDataPart("country", etCountry.getText().toString())
                    .addFormDataPart("city", etCity.getText().toString())
                    .addFormDataPart("state", etState.getText().toString())
                    .addFormDataPart("address2", etAddress2.getText().toString())
                    .addFormDataPart("zip", etZipCode.getText().toString())
                    .addFormDataPart("accountnumber", etAccountNumber.getText().toString())
//                    .addFormDataPart("accounttitle", accountTitleWithJs)
                    .addFormDataPart("accounttitle", "")
                    .addFormDataPart("bankname", etBankName.getText().toString())
                    .addFormDataPart("cnic", etBankCnic.getText().toString())
                    .addFormDataPart("address", etBankAddress.getText().toString())
//                    .addFormDataPart("withdrawaltype", (exchangeMethod == null) ? "" : exchangeMethod)
                    .addFormDataPart("withdrawaltype", "")
                    .addFormDataPart("user_type", "client")
                    .addFormDataPart("secret_question", "")
                    .addFormDataPart("secret_answer", (etAnsweer.getText().toString() == null) ? "" : etAnsweer.getText().toString())
//                    .addFormDataPart("bitcoin_email", etWalletInfoEmail.getText().toString())
//                    .addFormDataPart("label", etWalletInfoLabel.getText().toString())
                    .addFormDataPart("currancy", currency)
                    .addFormDataPart("lat", "0")
                    .addFormDataPart("long", "0")
//                    .addFormDataPart("exchange_id", (exchangeId == null) ? "" : exchangeId)
                    .addFormDataPart("exchange_id", "")


//                    .addFormDataPart("accounttype", (exchangeId == null) ? "" : exchangeId)
                    .addFormDataPart("accounttype", "")
//                    .addFormDataPart("rut", (exchangeId == null) ? "" : exchangeId)
                    .addFormDataPart("rut", "")


                    .addFormDataPart("id_card_image", "id_card_image.png",
                            RequestBody.create(MEDIA_TYPE_PNG, imageOne))
                    .addFormDataPart("bill_image", "bill_image.png",
                            RequestBody.create(MEDIA_TYPE_PNG, imageTwo))
                    .addFormDataPart("id_card_closeup_image", "id_card_closeup_image.png",
                            RequestBody.create(MEDIA_TYPE_PNG, imageThree))
                    .build();


            Request request = new Request.Builder()
                    .url(URL)
                    .post(body)
                    .build();

            okhttp3.Response response = client.newCall(request).execute();

            return response.body().string();

        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
