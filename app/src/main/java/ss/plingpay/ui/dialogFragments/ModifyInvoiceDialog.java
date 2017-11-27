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
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import ss.plingpay.pojos.Payment;
import ss.plingpay.pojos.TransactionAttr;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceDetailFragment.InvoiceDetailFragment;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

import static ss.plingpay.utilz.Utilz.convertToSatoshi;

/**
 * Created by samar_000 on 9/12/2016.
 */

public class ModifyInvoiceDialog extends DialogFragment {

    private static final String TAG = ModifyInvoiceDialog.class.getName().toUpperCase();
    private static final String TEXT = "Loading..";
    private Unbinder unbinder;
    private Payment p;

    protected WebServices api;


    @BindView(R.id.dialog_modify_inv_newAmount)
    EditText etAmount;
    @BindView(R.id.dialog_modify_inv_currency)
    TextView tvCurrency;
    @BindView(R.id.dialog_modify_inv_message)
    EditText etMessage;
    private String amountInBtc;
    private String amountInSotashi;


    private PlingPay app;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getArguments().getParcelable(InvoiceDetailFragment.PAYMENT);
        app = ((MainActivity) getActivity()).getApp();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_modify_inv, container, false);
        unbinder = ButterKnife.bind(this, v);
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

        String curr = (p.getCurrancyvalue() == null || p.getCurrancyvalue().isEmpty()) ? "null" : p.getCurrancyvalue();
        tvCurrency.setText(curr);

        String amount = (p.getAmount() == null || p.getAmount().isEmpty()) ? "null" : p.getAmount();
        etAmount.setText(amount);

    }

    @OnClick(R.id.dialog_modify_inv_btnSend)
    void onClick() {

        if (etAmount.getText().toString().isEmpty()) {
            etAmount.setError("Enter Amount");
            return;
        }

        if (etMessage.getText().toString().isEmpty()) {
            etMessage.setError("Enter Message");
            return;
        }

        if (p.getCurrancyvalue().equalsIgnoreCase("pkr")) {
            convertPkrToBtc(p.getAmount());
        } else {
            convertToBtc(p.getAmount(), p.getCurrancyvalue());
        }

    }

    void convertToBtc(String amount, String currency) {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_3).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", TEXT);
        api.getCurrencyValue(currency, amount).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                amountInBtc = response.body();
                amountInSotashi = convertToSatoshi(response.body());
                performTransaction();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();

            }
        });
    }


    private void convertPkrToBtc(String s) {
        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final String ss = s.replace(",", ".");
        final ProgressDialog sp = ProgressDialog.show(getActivity(), "", TEXT);
        api.PkrToBtc("allow", ss).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null && !response.body().isEmpty()) {
                    String convertedAmount = response.body().replace(",", ".");

                    amountInBtc = convertedAmount;
                    amountInSotashi = convertToSatoshi(convertedAmount);

                    performTransaction();

                    sp.dismiss();

                } else {
                    Utilz.tmsg(getActivity(), "Something Went Wrong");
                    sp.dismiss();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utilz.tmsg(getActivity(), "Something Went Wrong");
                Log.d(TAG, "onFailure: " + t.toString());

                sp.dismiss();
            }
        });
    }


    private void performTransaction() {

        TransactionAttr t = new TransactionAttr(getApp().getAuth().getWallet().getGuid(), getApp().getAuth().getWallet().getPassword(),
                "", p.getRequester().getDefaultAddress(), p.getAmount(), getApp().getAuth().getUser().getUserinfo().getDefaultAddress(),
                etMessage.getText().toString(), p.getRequester().getId(), getApp().getAuth().getUser().getUserinfo().getId(),
                Utilz.AppConstants.TYPE_INVOICE_TRANSACTION, p.getCurrancyvalue(), p.getRequest(), amountInBtc, "null",
                getApp().getAuth().getUser().getUserinfo().getDefaultAddress(),
                p.getRequester().getDefaultAddress(), "", "", amountInSotashi, getApp().getAuth().getUser().getUserinfo().getExchangeid(), p.getExchangeId());




    }

    private void modifyingRequest() {

        api = Utilz.getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", TEXT);

        api.addMessage(p.getId(), etAmount.getText().toString(), etMessage.getText().toString(), amountInBtc).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                JsonObject obj = response.body().getAsJsonObject();
                String status = obj.get("response").getAsString();

                if (status.equalsIgnoreCase("success")) {
                    Utilz.tmsg(getActivity(), "Modify and accept Successfully");
                }

                getDialog().dismiss();

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dialog.dismiss();
                Log.e(TAG, "onFailure: ", t);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
