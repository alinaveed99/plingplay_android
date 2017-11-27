package ss.plingpay.ui.dialogFragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.ui.activities.TransactionHistoryActivity;
import ss.plingpay.ui.adapters.TypeSpinnerAdapter;

/**
 * Created by samar_000 on 9/19/2016.
 */

public class FilterTransactionListDialog extends DialogFragment {

    private Unbinder unbinder;


    @BindView(R.id.dialog_filter_trans_searchName)
    EditText etSearch;

    @BindView(R.id.dialog_filter_trans_amountFilter)
    CheckBox cbAmountFilter;
    @BindView(R.id.dialog_filter_trans_amountMax)
    EditText etAmountMax;
    @BindView(R.id.dialog_filter_trans_amountMin)
    EditText etAmountMin;

    @BindView(R.id.dialog_filter_trans_dateFilter)
    CheckBox cbDateFilter;
    @BindView(R.id.dialog_filter_trans_dateStart)
    EditText etStart;
    @BindView(R.id.dialog_filter_trans_dateEnd)
    EditText etEnd;

    @BindView(R.id.dialog_filter_trans_typeFilter)
    CheckBox cbTypeFilter;
    @BindView(R.id.dialog_filter_trans_spinnerFilter)
    AppCompatSpinner spFilter;

    private TransactionFilterListener listener;

    private boolean amountEnable;
    private boolean dateEnable;
    boolean typeEnable;


    String startDate;
    String endDate;

    String maxAmount;
    String minAmount;

    String searchName;

    private String[] types = new String[]{"Payment", "Invoice"};
    private String typeName;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (TransactionHistoryActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_filter_trans, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        spFilter.setEnabled(false);


        cbAmountFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    etAmountMax.setEnabled(true);
                    etAmountMin.setEnabled(true);
                    amountEnable = true;
                } else {
                    etAmountMax.setEnabled(false);
                    etAmountMin.setEnabled(false);
                    amountEnable = false;
                }
            }
        });

        cbDateFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    etEnd.setEnabled(true);
                    etStart.setEnabled(true);
                    dateEnable = true;
                } else {
                    etEnd.setEnabled(false);
                    etStart.setEnabled(false);
                    dateEnable = false;
                }

            }
        });


        TypeSpinnerAdapter spinner = new TypeSpinnerAdapter(getActivity(), types);
        spFilter.setAdapter(spinner);


        cbTypeFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    spFilter.setEnabled(true);
                    typeEnable = true;
                } else {
                    spFilter.setEnabled(false);
                    typeEnable = false;
                }

            }
        });


        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeName = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.dialog_filter_trans_btnOk)
    void OnOkClick() {

        searchName = etSearch.getText().toString();
        startDate = etStart.getText().toString();
        endDate = etEnd.getText().toString();

        maxAmount = etAmountMax.getText().toString();
        minAmount = etAmountMin.getText().toString();

        listener.onOkClick(amountEnable, minAmount, maxAmount, dateEnable, startDate, endDate, typeEnable, typeName, searchName);
        getDialog().dismiss();
    }


    @OnClick(R.id.cancel)
    void onCancelClick() {
        getDialog().dismiss();
    }


    @OnClick(R.id.dialog_filter_trans_dateStart)
    void onDateStartClick() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog startdate = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

//                        _startDate = new DateTime(year, month + 1, (day <= 30) ? day : 30, 0, 0, 0);
                        StringBuilder builder = new StringBuilder();
                        builder.append(day + "-");
                        builder.append((month + 1) + "-");
                        builder.append(year);

                        etStart.setText(builder.toString());
                    }
                },
                mYear,
                mMonth,
                mDay);
        startdate.show();


    }

    @OnClick(R.id.dialog_filter_trans_dateEnd)
    void onDateEndClick() {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog startdate = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

//                        _startDate = new DateTime(year, month + 1, (day <= 30) ? day : 30, 0, 0, 0);
                        StringBuilder builder = new StringBuilder();
//                        builder.append(year + "-");
//                        builder.append((month + 1) + "-");
//                        builder.append(day);

                        builder.append(day + "-");
                        builder.append((month + 1) + "-");
                        builder.append(year);

                        etEnd.setText(builder.toString());
                    }
                },
                mYear,
                mMonth,
                mDay);
        startdate.show();

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

    public interface TransactionFilterListener {
        void onOkClick(boolean isAmountEnable, String minAmount, String maxAmount,
                       boolean isDateEnable, String startDate, String endDate,
                       boolean isFilterEnable, String typeText,
                       String searchName);
    }
}
