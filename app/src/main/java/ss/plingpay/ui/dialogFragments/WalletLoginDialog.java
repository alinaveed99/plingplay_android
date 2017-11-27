package ss.plingpay.ui.dialogFragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Bitcoindetail;
import ss.plingpay.pojos.Extra;
import ss.plingpay.ui.activities.LoginActivity;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.adapters.WalletAdapter;
import ss.plingpay.utilz.Utilz;

public class WalletLoginDialog extends DialogFragment {

    private static final String TAG = WalletLoginDialog.class.getName().toUpperCase();
    private Unbinder unbinder;

    private List<Bitcoindetail> b;

    protected WebServices api;

    private Listeners.WalletLogin listener;


    @BindView(R.id.dialog_wallet_login_AddressList)
    Spinner guids;

    @BindView(R.id.dialog_wallet_login_etPassword)
    EditText etPassword;

    private PlingPay app;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = ((LoginActivity) getActivity()).getApp();

        listener = (LoginActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_wallet_login, container, false);
        unbinder = ButterKnife.bind(this, v);
        b = getApp().getAuth().getUser().getBitcoindetails();
        return v;
    }

    protected PlingPay getApp() {
        return app;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        WalletAdapter adapter = new WalletAdapter(getActivity(), b);
        guids.setAdapter(adapter);

        guids.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getApp().getAuth().getWallet().setGuid(b.get(position).getGuid());
                getApp().getAuth().getWallet().setGuid_id(b.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick(R.id.dialog_wallet_login_btnOpen)
    void onOpenWallet() {

        if (etPassword.getText().toString().isEmpty()) {
            Utilz.tmsg(getActivity(), "Enter Password");
            return;
        }

        final String guid = getApp().getAuth().getWallet().getGuid();
        final String password = etPassword.getText().toString();

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_2).create(WebServices.class);

        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Loading...");

        api.blockchainAccess(guid, password, Utilz.AppConstants.API_CODE).enqueue(new Callback<Extra.BlockChainAccess>() {
            @Override
            public void onResponse(Call<Extra.BlockChainAccess> call, Response<Extra.BlockChainAccess> response) {

                if (response.body() == null) {
                    Utilz.tmsg(getActivity(), "Wrong Wallet Password");
                    pd.dismiss();
                    return;
                }

                if (response.body().getSuccess().toString().equalsIgnoreCase("true")) {
                    Utilz.tmsg(getActivity(), "Success");

                    api.getWalletBalance(guid, password).enqueue(new Callback<Extra.Balance>() {
                        @Override
                        public void onResponse(Call<Extra.Balance> call, Response<Extra.Balance> response) {
                            if (response.body().getError() != null) {
                                Utilz.tmsg(getActivity(), "Error : " + response.body().getError());
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
                    Utilz.tmsg(getActivity(), error);
                    pd.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Extra.BlockChainAccess> call, Throwable t) {
                Utilz.tmsg(getActivity(), "Something Went Wrong");
                Log.e(TAG, "Error : " + t.toString());
                pd.dismiss();
            }
        });
    }


    @OnClick(R.id.cancel)
    void onCancel() {
        this.getDialog().dismiss();
    }

    @OnClick(R.id.dialog_wallet_login_tvCreate)
    void onCreateClick() {
        listener.onCreateWallet();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
