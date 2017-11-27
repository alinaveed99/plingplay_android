package ss.plingpay.ui.fragments.confirmationFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.R;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.data.transactionRepository.TransactionsInteractorImpl;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.dialogFragments.ForgotPinDialog;
import ss.plingpay.ui.fragments.SendMoneyFragment;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

import static ss.plingpay.utilz.Utilz.getRxRetrofit;

/**
 * Created by samar_000 on 8/23/2016.
 */

public class ConfirmationFragment extends MvpFragment<ConfirmationView, ConfirmationPresenter>
        implements ConfirmationView {

    private static final String TAG = ConfirmationFragment.class.getName().toUpperCase();

    @BindView(R.id.fragment_confirmation_countLines)
    TextView tvCharCounts;
    @BindView(R.id.fragment_confirm_note)
    TextView tvNote;
    @BindView(R.id.fragment_confirm_RecName)
    TextView tvRecName;
    @BindView(R.id.fragment_confirm_amount)
    TextView tvamount;
    @BindView(R.id.fragment_confirm_currency)
    TextView tvCurrency;

    @BindView(R.id.fragment_confirm_etPin)
    EditText etPinCode;

    @Inject
    TaskRepository taskRepository;

    private Unbinder unbinder;
    private PaymentPOJO pp;
    private ProgressDialog showProgress;
    private PaymentPOJO obj;

    private PlingPay app;
    protected WebServices api;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((MainActivity) getActivity()).getApp();
        pp = getArguments().getParcelable(SendMoneyFragment.PAYMENT_POJO);
        obj = pp;
        showProgress = new ProgressDialog(getActivity());
        showProgress.setMessage("Loading...");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirmation, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public ConfirmationPresenter createPresenter() {
        ((MainActivity) getActivity()).getApp().getAppComponents().Inject(this);
        return new ConfirmationPresenter(new TransactionsInteractorImpl(), taskRepository);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick(R.id.fragment_confirm_forgetPin)
    void onForgotPinClick() {


        if (app.getAuth().getUser().getUserinfo().getSecret_answer() == null ||
                app.getAuth().getUser().getUserinfo().getSecret_answer().equalsIgnoreCase("none") ||
                app.getAuth().getUser().getUserinfo().getSecret_answer().isEmpty() ||
                app.getAuth().getUser().getUserinfo().getSecret_answer().equalsIgnoreCase("")
                ) {

            Utilz.showAlertWithCancel(getActivity(), "Confirmation", "Are Your Sure You Want To Change Your Pin?", this::sendNewPin);

        } else {

            DialogFragment dialogFragment = new ForgotPinDialog();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "forgotPinDialog");

        }


    }

    private void sendNewPin() {

        api = getRxRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");

        api.resetPin(app.getAuth().getUser().getUserinfo().getEmail(), "pin")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Log.e(TAG, "sendNewPin: ", throwable);
                    Utilz.tmsgError(getActivity(), "Error");
                    dialog.dismiss();
                })
                .subscribe(data -> {


                    if (data == null || !data.isSuccessful()) {
                        dialog.dismiss();
                        Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                        return;
                    }


                    try {
                        JsonObject obj = data.body().getAsJsonObject();
                        String response = obj.get("response").getAsString();

                        if (response.equalsIgnoreCase("success")) {
                            Utilz.tmsgInfo(getActivity(), "New Pin have Been Sent To Your Email");

                            String pin = obj.get("pincode").getAsString();

                            app.getAuth().getUser().getUserinfo().setPincode(pin);

                        } else {
                            Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                        }
                    } catch (Exception e) {
                        Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                        Log.e(TAG, "sendNewPin: ", e);
                    }


                    dialog.dismiss();

                });


    }

    private void init() {

        addNote();

        tvRecName.setText(pp.getReceiverInfo().getFname() + " " + pp.getReceiverInfo().getLname());

        tvamount.setText(pp.getAmount());

        tvCurrency.setText(pp.getCurrency());


    }



    @OnClick(R.id.fragment_confirm_btnSign)
    void onSignClick() {

        if (etPinCode.getText() == null) {
            etPinCode.setError("No Pin Found");
        } else {
            etPinCode.setError(null);
        }


        if (etPinCode.getText().toString().trim().isEmpty()) {
            etPinCode.setError("Please Enter Your Pin Code");
            return;
        } else {
            etPinCode.setError(null);
        }

        if (!app.getAuth().getUser().getUserinfo().getPincode().equalsIgnoreCase(etPinCode.getText().toString().trim())) {
            etPinCode.setError("Wrong Pin Code");
            return;
        } else {
            etPinCode.setError(null);
        }

        performTransaction("false");

    }

    private void performTransaction(String isStandingOrder) {
        showProgress.show();
        presenter.performTransaction(
                pp, app.getAuth().getUser().getUserinfo(), isStandingOrder);
    }


    @OnClick(R.id.fragment_confirm_btnCancel)
    void OnCancel() {

        getActivity().getSupportFragmentManager();
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        //TODO open main fragment
        //openFragment(R.id.activity_main_container, new HomeFragment(), HomeFragment.class.getName(), false);
    }

    private void addNote() {
        tvNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCharCounts.setText(tvNote.getText().length() + "/50");
                if (tvNote.getText().length() > 50) {
                    tvNote.setError("Character Limit Exceed");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSuccessTransaction(String a) {

        Utilz.showAlert(getActivity(), "Confirmation", a, () -> {
            startActivity(getActivity().getIntent());
            getActivity().finish();
        });

    }

    @Override
    public void onErrorTransaction() {
        showProgress.show();
        performTransaction("true");

//        Utilz.showAlertWithOKCancel(getActivity(), "Error", errorMessage, new Listeners.NewDialogListener() {
//            @Override
//            public void OnOkClick() {
//
//                performTransaction("true");
//
//            }
//
//            @Override
//            public void OnCancelClick() {
//                getActivity().getSupportFragmentManager();
//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            }
//        });
    }


    @Override
    public void onFinally() {
        showProgress.dismiss();
    }

    @BindString(R.string.service_down_error)
    String errorMessage;





}
