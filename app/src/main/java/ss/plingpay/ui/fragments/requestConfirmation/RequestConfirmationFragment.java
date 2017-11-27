package ss.plingpay.ui.fragments.requestConfirmation;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Userinfo;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.fragments.HomeFragment;
import ss.plingpay.ui.fragments.SendMoneyFragment;
import ss.plingpay.ui.fragments.confirmationFragment.ConfirmationFragment;
import ss.plingpay.utilz.ActivityUtil;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 9/1/2016.
 */

public class RequestConfirmationFragment
        extends MvpFragment<RequestConfirmationView, RequestConfirmationPresenter>
        implements RequestConfirmationView {

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
    @BindView(R.id.fragment_confirm_tvRequest)
    TextView tvRequest;

    private Unbinder unbinder;
    private PaymentPOJO pp;
    private String amountInBtc;


    private ProgressDialog pd;

    @Inject
    TaskRepository taskRepository;

    @Override
    public RequestConfirmationPresenter createPresenter() {
        ((MainActivity) getActivity()).getApp().getAppComponents().Inject(this);
        return new RequestConfirmationPresenter(taskRepository);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pp = getArguments().getParcelable(SendMoneyFragment.PAYMENT_POJO);
        pd = Utilz.ProgressD(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirmation_request, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        pd.show();
        presenter.convertToBtc(pp.getAmount(), pp.getCurrency());

        addNote();

        tvRecName.setText(pp.getReceiverInfo().getFname());
        tvamount.setText(pp.getAmount());
        tvCurrency.setText(pp.getCurrency());


    }

    @OnClick(R.id.fragment_confirm_btnSign)
    void onSignClick() {

        if (pp.getReceiverInfo().getDefaultAddress() == null || pp.getReceiverInfo().getDefaultAddress().isEmpty()) {
            Utilz.showAlert(getActivity(), "Error", pp.getReceiverInfo().getFname() + " didn't set his/her default address yet");
            return;
        }

        if (tvRequest.getText().toString().isEmpty()) {
            tvRequest.setError("This Field Is Required");
            return;
        }

        String notee = (tvNote.getText() == null || tvNote.getText().toString().isEmpty()) ? "" : tvNote.getText().toString();


        Userinfo userinfo = ((MainActivity) getActivity()).getApp().getAuth().getUser().getUserinfo();

        pd.show();
        presenter.sendPaymentRequest("authtoken", userinfo.getId(), pp.getReceiverInfo().getId(),
                pp.getAmount(), pp.getCurrency(), tvRequest.getText().toString(), notee, userinfo.getDefaultAddress(),
                amountInBtc);

    }

    @OnClick(R.id.fragment_confirm_btnCancel)
    void OnCancel() {

        getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
        ActivityUtil.openFragment(getFragmentManager(),
                R.id.activity_main_container, new HomeFragment(),
                HomeFragment.class.getName(), false);
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
    public void dismissDialog() {

        if (pd.isShowing())
            pd.dismiss();

    }

    @Override
    public void onFailedSendingRequest() {
        Utilz.tmsgError(getContext(), "Error Sending Request");
    }

    @Override
    public void onSuccessfullyRequestSent() {
        Utilz.tmsgSuccess(getContext(), "Request Successfully Sent");
    }

    @Override
    public void onFailedConvertingCurrency() {
        Utilz.tmsgError(getContext(), "Error Converting Currency");
    }

    @Override
    public void onSuccessfullyCurrencyConverted(String btcAmount) {
        this.amountInBtc = btcAmount;
    }
}
