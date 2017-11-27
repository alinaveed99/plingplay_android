package ss.plingpay.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Extra;
import ss.plingpay.utilz.Utilz;


/**
 * Created by samar_000 on 8/16/2016.
 */

public class CreateWalletActivity extends BaseActivity {


    @BindView(R.id.activity_create_wallet_etEmail)
    EditText etEmail;
    @BindView(R.id.activity_create_wallet_etLabel)
    EditText etLabel;
    @BindView(R.id.activity_create_wallet_etPassword)
    EditText etPassword;
    @BindView(R.id.activity_create_wallet_etprivateKey)
    EditText etPrivateKey;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

    }

    @OnClick(R.id.activity_create_wallet_btnCreate)
    void onCreateClick() {

        if (!Utilz.validateField(etPassword)){
            Utilz.tmsgError(this, "Please Provide Password");
            return;
        }

        api = getRetrofit(Utilz.AppConstants.BASE_URL_2).create(WebServices.class);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String priv = etPrivateKey.getText().toString();
        String label = etLabel.getText().toString();
        String api_code = Utilz.AppConstants.API_CODE;

        final ProgressDialog dialog = ProgressDialog.show(CreateWalletActivity.this, "Creating New Wallet", "loading...");

        api.createNewWallet(
                email, password, api_code,
                (priv.isEmpty()) ? "" : priv,
                (label.isEmpty()) ? "" : label
        ).enqueue(new Callback<Extra.CreateNewWallet>() {
            @Override
            public void onResponse(Call<Extra.CreateNewWallet> call, Response<Extra.CreateNewWallet> response) {


                Extra.CreateNewWallet registerResponse = null;

                Log.d("onResponse", "onResponse - Status : " + response.code());
                Gson gson = new Gson();
                TypeAdapter<Extra.CreateNewWallet> adapter = gson.getAdapter(Extra.CreateNewWallet.class);
                try {
                    if (response.errorBody() != null)

                        registerResponse = adapter.fromJson(response.errorBody().string());

                    if (registerResponse.getError().length() > 0) {
                        Utilz.showAlert(CreateWalletActivity.this, "Error", registerResponse.getError());
                    } else {

                        if (!registerResponse.getGuid().isEmpty()) {
                            saveInServer(registerResponse.getGuid(), registerResponse.getAddress(), registerResponse.getLabel());
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();


            }

            @Override
            public void onFailure(Call<Extra.CreateNewWallet> call, Throwable t) {
                Utilz.tmsgError(CreateWalletActivity.this, "Something Went Wrong, Please Try again later");
                dialog.dismiss();

            }
        });

    }

    private void saveInServer(String gui, String dAddress, String leb) {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String guid = gui;
        String defaultAddress = dAddress;
        String bitcoinEmail = getApp().getAuth().getUser().getUserinfo().getEmail();
        String label = leb;
        String userid = getApp().getAuth().getUser().getUserinfo().getId();
        String userLabel = "";

        api.saveWalletToServer(guid, defaultAddress, bitcoinEmail, label, userid, userLabel).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                JsonObject obj = response.body().getAsJsonObject();
                String status = obj.get("response").getAsString();

                if (status.equalsIgnoreCase("success")) {
//                    Utilz.tmsg(CreateWalletActivity.this, "Wallet Successfully created");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Utilz.tmsgError(CreateWalletActivity.this, "Error : Couldn't save bitcoin details on Server");
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });



    }


}
