package ss.plingpay.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.pojos.PaymentPOJO;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.ui.activities.ContactListActivity;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.adapters.TypeSpinnerAdapter;
import ss.plingpay.ui.dialogFragments.CurrencySelecter;
import ss.plingpay.ui.fragments.confirmationFragment.ConfirmationFragment;
import ss.plingpay.ui.fragments.requestConfirmation.RequestConfirmationFragment;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/18/2016.
 */

public class SendMoneyFragment extends NewBaseFragment implements Listeners.CurrencySelecter {


    private static final int CONTACT_LIST_ACTIVITY_REQUEST_CODE = 1;
    public static final String PAYMENT_POJO = "payment_pojo";
    private Unbinder unbinder;

    private UserDetails userDetails;

    @BindView(R.id.fragment_send_selectCurr)
    TextView tvCurrency;
    @BindView(R.id.fragment_send_money_ContactName)
    TextView tvContactName;
    @BindView(R.id.fragment_send_money_etAmount)
    EditText etAmount;

    private PaymentPOJO pp;
    private String paymentMethod;

    @BindView(R.id.fragment_send_money_spMethods)
    AppCompatSpinner spinner;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ((MainActivity) getActivity()).disableRightDrawer(true);
        setTitle("Find Recipient");
//        DisableDrawerToggle();
        ((MainActivity) getActivity()).disableRightDrawer(true);
        setTitle("Send Money");


        pp = new PaymentPOJO();
        pp.setCurrency("btc");
        paymentMethod = getArguments().getString(HomeFragment.METHOD);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_send_money, container, false);
        unbinder = ButterKnife.bind(this, v);
        tvCurrency.setText(getApp().getAuth().getUser().getUserinfo().getCurrancyId());
        pp.setCurrency(tvCurrency.getText().toString());
        pp.setCurr_id(getApp().getAuth().getUser().getUserinfo().getCurr_id());
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(getActivity(), Utilz.PAYMENT_METHOD);
        spinner.setAdapter(adapter);


        spinner.setSelection(getIndex(spinner, paymentMethod));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (Utilz.PAYMENT_METHOD[position].equalsIgnoreCase(Utilz.PAYMENT_METHOD[2]) ||
                        Utilz.PAYMENT_METHOD[position].equalsIgnoreCase(Utilz.PAYMENT_METHOD[3]) ||
                        Utilz.PAYMENT_METHOD[position].equalsIgnoreCase(Utilz.PAYMENT_METHOD[4])) {

                    Utilz.showAlert(getActivity(), "Error", getResources().getString(R.string.Message));
                    spinner.setSelection(getIndex(spinner, paymentMethod));

                }
                paymentMethod = Utilz.PAYMENT_METHOD[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @OnClick(R.id.fragment_send_btnNext)
    void OnNextClick() {

        if (etAmount.getText().toString().isEmpty()) {
            etAmount.setError("Enter Amount");
            return;
        } else {
            etAmount.setError(null);
        }


        if (pp.getReceiverInfo() == null) {
            Utilz.tmsg(getActivity(), "Select User");
            return;
        }


        String a = etAmount.getText().toString().replace(",", ".");
        pp.setAmount(a);

        Double amount = Double.parseDouble(pp.getAmount());

        if (amount < 5.0) {
            etAmount.setError("Please enter amount greater then 5 euro");
            return;
        } else {
            etAmount.setError(null);
        }


        Bundle b = new Bundle();
        b.putParcelable(PAYMENT_POJO, pp);

        String m = paymentMethod;
        Utilz.tmsg(getActivity(), m);

        //Invoice
        if (m.equalsIgnoreCase(Utilz.PAYMENT_METHOD[1])) {
            openFragment(R.id.activity_main_container, new RequestConfirmationFragment(),
                    RequestConfirmationFragment.class.getName(), true, b);

            // Payable
        } else if (m.equalsIgnoreCase(Utilz.PAYMENT_METHOD[0])) {
            openFragment(R.id.activity_main_container, new ConfirmationFragment(),
                    ConfirmationFragment.class.getName(), true, b);

        }


    }


    @OnClick(R.id.fragment_send_addReceiver)
    void onAddReceiver() {
        startActivityForResult(new Intent(getActivity(), ContactListActivity.class), CONTACT_LIST_ACTIVITY_REQUEST_CODE);
        pp.setReceiverInfo(null);
        tvContactName.setVisibility(View.GONE);
        tvContactName.setText("");
    }

    @OnClick(R.id.fragment_send_selectCurr)
    void onCurrSelect(View v) {
        DialogFragment dialog = new CurrencySelecter();
        dialog.show(getFragmentManager(), CurrencySelecter.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != getActivity().RESULT_OK) {
            return;
        }

        if (requestCode == CONTACT_LIST_ACTIVITY_REQUEST_CODE) {

            userDetails = data.getParcelableExtra(ContactListActivity.USER_DETAILS);
            pp.setReceiverInfo(userDetails);
            tvContactName.setVisibility(View.VISIBLE);
            tvContactName.setText(userDetails.getFname() + " " + userDetails.getLname());

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void selectedItem(String currency, String id) {
        tvCurrency.setText(currency);
        pp.setCurrency(currency);
        pp.setCurr_id(id);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
