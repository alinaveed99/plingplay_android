package ss.plingpay.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.CustomWebViewActivity;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Extra;
import ss.plingpay.pojos.UserLogin;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.activities.RegistrationActivity;
import ss.plingpay.ui.dialogFragments.ForgotPasswordDialog;
import ss.plingpay.ui.dialogFragments.WalletLoginDialog;
import ss.plingpay.utilz.Utilz;

import static android.content.Context.MODE_PRIVATE;
import static ss.plingpay.utilz.Utilz.AppConstants.MY_PREFS_NAME;
import static ss.plingpay.utilz.Utilz.validateField;

/**
 * Created by samar_000 on 8/16/2016.
 */

public class LoginFragment extends BaseFragment {

    private static final String TAG = LoginFragment.class.getName().toUpperCase();
    private Unbinder unbinder;

    @BindView(R.id.fragment_login_etEmail)
    EditText email;
    @BindView(R.id.fragment_login_etPassword)
    TextInputEditText password;
    private String guid;


    @BindView(R.id.fragment_login_rememberMe)
    CheckBox cbRememberMe;

    private boolean isRememberMeCheck;
    private boolean isRememberMe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setNavFragment(false);
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        isRememberMe = prefs.getBoolean("isRememberMe", false);

        if (isRememberMe) {
            String _email = prefs.getString("email", "");
            String _password = prefs.getString("password", "");


            email.setText(_email);
            password.setText(_password);
            cbRememberMe.setChecked(true);
            isRememberMeCheck = true;

        } else {

            cbRememberMe.setChecked(false);
            isRememberMeCheck = false;

        }


        cbRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> isRememberMeCheck = isChecked);

    }


    @OnClick(R.id.fragment_login_forget)
    void onForgotClick() {

        DialogFragment dialogFragment = new ForgotPasswordDialog();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "password_dialog");

    }


    @OnClick(R.id.fragment_login_btnLogin)
    void onLoginClick() {

        if (!validateField(email)) {
            Utilz.tmsgError(getActivity(), "Please Provide Email");
            return;
        }

        if (!validateField(password)) {
            Utilz.tmsgError(getActivity(), "Please Provide Password");
            return;
        }


        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Loading...");
        api.login(email.getText().toString(), password.getText().toString()).enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                if (response.body().getResponse().equalsIgnoreCase("success")) {


                    Log.d(TAG, "onResponse: " + response.body().getUserinfo().getEmail());


                    getApp().getAuth().getUser().setUserinfo(response.body().getUserinfo());
                    getApp().getAuth().getUser().setBitcoindetails(response.body().getBitcoindetails());
                    getApp().getAuth().getUser().setRecurringpayments(response.body().getRecurringpayments());
                    getApp().getAuth().getUser().setRecurringreports(response.body().getRecurringreports());

//                    DialogFragment walletLogin = new WalletLoginDialog();
//                    walletLogin.show(getFragmentManager(), WalletLoginDialog.class.getName());

                    openWallet();

                } else if (response.body().getResponse().equalsIgnoreCase("Inactive")) {

                    Utilz.showAlert(getActivity(), "Error", "Your account is not yet activated");

                } else {

                    Utilz.tmsgError(getActivity(), "Wrong Credentials");

                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Utilz.tmsgError(getActivity(), "Something Went Wrong, Please check your internet connection and try again");
                Log.e(TAG, "onFailure: " + t.toString());
                pd.dismiss();
            }
        });

        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        if (isRememberMeCheck) {
            editor.putString("email", email.getText().toString().trim());
            editor.putString("password", password.getText().toString().trim());
            editor.putBoolean("isRememberMe", true);
            editor.commit();
        } else {
            editor.putBoolean("isRememberMe", false);
            editor.commit();
        }
    }


    @OnClick(R.id.fragment_login_tvRegister)
    void onRegClick() {
//        startActivity(new Intent(getActivity(), RegistrationActivity.class));

        Intent i = new Intent(getActivity(), CustomWebViewActivity.class);
        i.putExtra("url", Utilz.AppConstants.REG_URL);
        startActivity(i);
        getActivity().finish();



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //        @BindString(R.string.user_b_wallet_password)
    String WalletPassword;

    void openWallet() {

        for (int i = 0; i < getApp().getAuth().getUser().getBitcoindetails().size(); i++) {

            if (getApp().getAuth().getUser().getBitcoindetails().get(i).getIsDefault().equalsIgnoreCase("1")) {

                guid = getApp().getAuth().getUser().getBitcoindetails().get(i).getGuid();
                WalletPassword = getApp().getAuth().getUser().getBitcoindetails().get(i).getPassword();

                getApp().getAuth().getWallet().setGuid(getApp().getAuth().getUser().getBitcoindetails().get(i).getGuid());
                getApp().getAuth().getWallet().setGuid_id(getApp().getAuth().getUser().getBitcoindetails().get(i).getId());

                break;
            }

        }


        final String password = WalletPassword;

        api = getRetrofit(Utilz.AppConstants.BASE_URL_2).create(WebServices.class);

        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Loading...");

        api.blockchainAccess(guid, password, Utilz.AppConstants.API_CODE).enqueue(new Callback<Extra.BlockChainAccess>() {
            @Override
            public void onResponse(Call<Extra.BlockChainAccess> call, Response<Extra.BlockChainAccess> response) {

                if (response.body() == null) {
                    Utilz.tmsgError(getActivity(), "Wrong Wallet Password");
                    DialogFragment walletLogin = new WalletLoginDialog();
                    walletLogin.show(getFragmentManager(), WalletLoginDialog.class.getName());
                    pd.dismiss();
                    return;
                }

                if (response.body().getSuccess().toString().equalsIgnoreCase("true")) {
                    Utilz.tmsgSuccess(getActivity(), "Success");

                    api.getWalletBalance(guid, password).enqueue(new Callback<Extra.Balance>() {
                        @Override
                        public void onResponse(Call<Extra.Balance> call, Response<Extra.Balance> response) {
                            if (response.body().getError() != null) {
                                Utilz.tmsgError(getActivity(), "Error : " + response.body().getError());


                                DialogFragment walletLogin = new WalletLoginDialog();
                                walletLogin.show(getFragmentManager(), WalletLoginDialog.class.getName());

                            } else {

                                getApp().getAuth().getWallet().setBalance(response.body().getBalance() + "");
                                getApp().getAuth().getWallet().setGuid(guid);
                                getApp().getAuth().getWallet().setPassword(password);


                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }

                            pd.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Extra.Balance> call, Throwable t) {
                            pd.dismiss();
                        }
                    });


                } else {
                    String error = response.body().getError();
                    Utilz.tmsgError(getActivity(), error);
                    pd.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Extra.BlockChainAccess> call, Throwable t) {
                Utilz.tmsgError(getActivity(), "Server not responding, please try again later.");
                Log.e(TAG, "Error : " + t.toString());
                pd.dismiss();
            }
        });
    }


}
