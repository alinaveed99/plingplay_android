package ss.plingpay.ui.dialogFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ss.plingpay.R;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.AddressList;
import ss.plingpay.pojos.Bitcoindetail;
import ss.plingpay.pojos.Extra;
import ss.plingpay.ui.activities.SettingActivity;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.adapters.CustomSpinnerAdapter;
import ss.plingpay.ui.adapters.WalletAdapter;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 10/24/2016.
 */

public class WalletChangeDialog extends DialogFragment {

    private static final String TAG = WalletLoginDialog.class.getName().toUpperCase();
    private Unbinder unbinder;

    private List<Bitcoindetail> b;


    @BindView(R.id.dialog_wallet_login_WalletList)
    Spinner guids;


    @BindView(R.id.dialog_wallet_login_AddressList)
    Spinner addresses;

    @BindView(R.id.dialog_wallet_login_etPassword)
    EditText etPassword;


    @BindView(R.id.dialog_wallet_login_btnOpen)
    Button btnOpen;

    @BindView(R.id.dialog_wallet_login_btnGetAddress)
    Button btnGetAddresses;

    private Listeners.WalletChangeListener listener;
    private String defaultAddress;

    protected WebServices api;

    private PlingPay app;
    protected Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((MainActivity) getActivity()).getApp();
        realm = Realm.getDefaultInstance();
        listener = (SettingActivity) getActivity();
    }

    protected PlingPay getApp() {
        return app;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_wallet_change, container, false);
        unbinder = ButterKnife.bind(this, v);
        b = getApp().getAuth().getUser().getBitcoindetails();
        return v;
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

    private void populateAddressSpinner(String guid, String password) {
        api = getRetrofit(Utilz.AppConstants.BASE_URL_2).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");
        api.getWalletAddresses(guid, password).enqueue(new Callback<AddressList>() {
            @Override
            public void onResponse(Call<AddressList> call, final Response<AddressList> response) {

                if (response.isSuccessful()) {
                    response.body().getAddresses();
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), response.body().getAddresses());
                    addresses.setAdapter(adapter);

                    addresses.setVisibility(View.VISIBLE);
                    btnGetAddresses.setVisibility(View.GONE);
                    btnOpen.setVisibility(View.VISIBLE);


                    addresses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            defaultAddress = response.body().getAddresses().get(i).getAddress();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddressList> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                dialog.dismiss();
            }
        });
    }


    @OnClick(R.id.dialog_wallet_login_btnGetAddress)
    void onGetAddressClick() {

        if (etPassword.getText().toString().isEmpty()) {
            Utilz.tmsg(getActivity(), "Enter Password");
            return;
        }

        populateAddressSpinner(getApp().getAuth().getWallet().getGuid(), etPassword.getText().toString().trim());
    }


    Retrofit getRetrofit(String url) {
        return Utilz.getRetrofit(url);
    }


    @OnClick(R.id.dialog_wallet_login_btnOpen)
    void onOpenWallet() {

        if (etPassword.getText().toString().isEmpty()) {
            Utilz.tmsg(getActivity(), "Enter Password");
            return;
        }

        final String guid = getApp().getAuth().getWallet().getGuid();
        final String password = etPassword.getText().toString();

        api = getRetrofit(Utilz.AppConstants.BASE_URL_2).create(WebServices.class);

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
                                pd.dismiss();
                            } else {

                                getApp().getAuth().getWallet().setBalance(response.body().getBalance() + "");
                                getApp().getAuth().getWallet().setGuid(guid);
                                getApp().getAuth().getWallet().setPassword(password);


//                                startActivity(new Intent(getActivity(), MainActivity.class));
//                                getActivity().finish();
                                pd.dismiss();
                                listener.onChangeClick(getApp().getAuth().getWallet().getGuid_id(), defaultAddress);

                            }


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
