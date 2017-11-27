package ss.plingpay.ui.dialogFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.AddressList;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.adapters.AddressListAdapter;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/29/2016.
 */

public class AddressListDialogFragment extends DialogFragment implements Listeners.AddressListListener {

    private static final String TAG = AddressListDialogFragment.class.getName().toUpperCase();
    private Unbinder unbinder;

    @BindView(R.id.fragment_address_list_recyclerView)
    RecyclerView list;

    protected WebServices api;


    private Listener listener;


    private PlingPay app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_address_list, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        app = ((MainActivity) getActivity()).getApp();

        init();
    }

    protected PlingPay getApp() {
        return app;
    }

    private void init() {
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setHasFixedSize(true);

        populateAddressList();

    }


    private void populateAddressList() {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_2).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Fetching Your Addresses...");
        api.getWalletAddresses(getApp().getAuth().getWallet().getGuid(),
                getApp().getAuth().getWallet().getPassword()).enqueue(new Callback<AddressList>() {
            @Override
            public void onResponse(Call<AddressList> call, final Response<AddressList> response) {


                if (response.isSuccessful()) {
                    response.body().getAddresses();
                    AddressListAdapter adapter = new AddressListAdapter(getActivity(), response.body().getAddresses(), AddressListDialogFragment.this);
                    list.setAdapter(adapter);

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

    @OnClick({R.id.fragment_addr_list_btnCancel, R.id.cancel})
    void onCancelClick() {
        this.getDialog().dismiss();
        listener.onCancelClick();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnClick(String address) {
        this.getDialog().dismiss();
        listener.onAddressClick(address);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public interface Listener {

        void onCancelClick();

        void onAddressClick(String address);

    }
}
