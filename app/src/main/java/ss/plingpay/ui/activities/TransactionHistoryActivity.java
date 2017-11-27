package ss.plingpay.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Mylog;
import ss.plingpay.pojos.TransactionHistoryPOJO;
import ss.plingpay.ui.adapters.TransactionListAdapter;
import ss.plingpay.ui.dialogFragments.FilterTransactionListDialog;
import ss.plingpay.utilz.Utilz;

public class TransactionHistoryActivity extends BaseActivity implements FilterTransactionListDialog.TransactionFilterListener {

    private static final String TAG = TransactionHistoryActivity.class.getName().toUpperCase();
    @BindView(R.id.activity_trans_all)
    TextView tvAll;
    @BindView(R.id.activity_trans_name)
    TextView tvName;
    @BindView(R.id.activity_trans_amount)
    TextView tvAmount;
    @BindView(R.id.activity_trans_type)
    TextView tvType;
    @BindView(R.id.activity_trans_list)
    RecyclerView list;

    private ArrayList<Mylog> data;
    private ArrayList<Mylog> default_data;

    private boolean NameAsc = true;
    private boolean AmountAsc = true;
    private boolean DateAcs = true;
    private boolean TypeAsc = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportedActionbar().setTitle("Transaction History");
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        getToolbar().setNavigationIcon(R.drawable.ic_cross);
        getToolbar().setNavigationOnClickListener(view -> finish());


        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        populateHistoryList();
    }


    private void populateHistoryList() {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        String id = getApp().getAuth().getUser().getUserinfo().getId();

        final ProgressDialog dialog = ProgressDialog.show(this, "Please Wait", "Getting Transaction History");

        api.getLogs(id).enqueue(new Callback<TransactionHistoryPOJO>() {
            @Override
            public void onResponse(Call<TransactionHistoryPOJO> call, Response<TransactionHistoryPOJO> response) {


                if (response.isSuccessful()) {

                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        if (response.body().getMylogs().size() <= 0) {
                            Utilz.tmsg(TransactionHistoryActivity.this, "No Transaction Found");
                            dialog.dismiss();
                            return;
                        }
                        data = (ArrayList<Mylog>) response.body().getMylogs();

                        default_data = (ArrayList<Mylog>) response.body().getMylogs();

                        list.setLayoutManager(new LinearLayoutManager(TransactionHistoryActivity.this));
                        list.setHasFixedSize(true);

                        TransactionListAdapter adapter = new TransactionListAdapter(TransactionHistoryActivity.this, default_data);
                        list.setAdapter(adapter);

                        tvAll.setEnabled(true);
                        tvName.setEnabled(true);
                        tvAmount.setEnabled(true);
                        tvType.setEnabled(true);


                    } else {
                        ifError();


                    }

                } else {
                    ifError();

                }

                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<TransactionHistoryPOJO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                ifError();
                dialog.dismiss();

            }
        });


    }

    private void ifError() {
        Utilz.tmsg(TransactionHistoryActivity.this, "Error");

        tvAll.setEnabled(false);
        tvName.setEnabled(false);
        tvAmount.setEnabled(false);
        tvType.setEnabled(false);

    }


    private void populateHistoryList2(final boolean isAmountEnable, final String minAmount, final String maxAmount,
                                      final boolean isDateEnable, final String startDate, final String endDate,
                                      final boolean isFilterEnable, final String typeText,
                                      final String searchName) {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        final ProgressDialog dialog = ProgressDialog.show(this, "Please Wait", "Getting Transaction History");

        api.getLogs(getApp().getAuth().getUser().getUserinfo().getId()).enqueue(new Callback<TransactionHistoryPOJO>() {
            @Override
            public void onResponse(Call<TransactionHistoryPOJO> call, Response<TransactionHistoryPOJO> response) {


                if (response.isSuccessful()) {

                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        if (response.body().getMylogs().size() <= 0) {
                            Utilz.tmsg(TransactionHistoryActivity.this, "No Transaction Found");
                            dialog.dismiss();
                            return;
                        }
                        data = (ArrayList<Mylog>) response.body().getMylogs();

                        default_data = (ArrayList<Mylog>) response.body().getMylogs();

                        list.setLayoutManager(new LinearLayoutManager(TransactionHistoryActivity.this));
                        list.setHasFixedSize(true);

//                        TransactionListAdapter adapter = new TransactionListAdapter(TransactionHistoryActivity.this, default_data);
//                        list.setAdapter(adapter);


                        ArrayList<Mylog> tempData = new ArrayList<>();

                        for (int i = 0; i < data.size(); i++) {

                            Mylog a = data.get(i);

//                By Name
                            if (!searchName.isEmpty()) {
                                if (!searchName.equalsIgnoreCase(data.get(i).getReceivername()))
                                    continue;
                            }

                            if (isAmountEnable) {

                                if (!(Double.parseDouble(data.get(i).getAmount()) <= Double.parseDouble(maxAmount))) {
                                    continue;
                                }

                                if (!(Double.parseDouble(data.get(i).getAmount()) >= Double.parseDouble(minAmount))) {
                                    continue;
                                }

                            }

                            if (isDateEnable) {

                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                formatter.setTimeZone(TimeZone.getDefault());
                                Date aa = null;
                                Date b = null;
                                try {

                                    aa = formatter.parse(startDate);
                                    b = formatter.parse(endDate);

                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }


                                Date d = null;
                                try {


                                    d = formatter.parse(data.get(i).getDate());

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                boolean isValidDate = aa.compareTo(d) * d.compareTo(b) > 0;

                                if (!isValidDate) {
                                    continue;
                                }

                            }


                            if (isFilterEnable) {

                                if (!typeText.isEmpty()) {
                                    if (!typeText.equalsIgnoreCase(getTypee(data.get(i).getType()))) {
                                        continue;
                                    }
                                }


                            }

                            tempData.add(a);


                        }

                        data.clear();
                        data = tempData;

                        TransactionListAdapter adapter = new TransactionListAdapter(TransactionHistoryActivity.this, data);
                        list.setAdapter(adapter);


                    } else {
                        Utilz.tmsg(TransactionHistoryActivity.this, "Error");

                    }

                } else {
                    Utilz.tmsg(TransactionHistoryActivity.this, "Error");
                }

                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<TransactionHistoryPOJO> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();

            }
        });


    }


    private String getTypee(String text) {


        String ret = "";

        if (text.equalsIgnoreCase(Utilz.AppConstants.TYPE_NORMAL_TRANSACTION)) {
            ret = Utilz.PAYMENT_METHOD[0];
        } else if (text.equalsIgnoreCase(Utilz.AppConstants.TYPE_INVOICE_TRANSACTION)) {
            ret = Utilz.PAYMENT_METHOD[1];
        }


        return ret;

    }


    @OnClick(R.id.activity_trans_all)
    void onAllClick() {

        tvAll.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlue));
        tvAmount.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvName.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvType.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));


        if (DateAcs) {
            Collections.sort(data, Mylog.LogByDateA);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            DateAcs = false;

        } else {
            Collections.sort(data, Mylog.LogByDateD);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            DateAcs = true;
        }

    }

    @OnClick(R.id.activity_trans_amount)
    void onNameClick() {

        tvAll.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvAmount.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlue));
        tvName.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvType.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));


        if (AmountAsc) {
            Collections.sort(data, Mylog.LogByAmountA);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            AmountAsc = false;

        } else {
            Collections.sort(data, Mylog.LogByAmountD);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            AmountAsc = true;
        }

    }


    //Sort By Name
    @OnClick(R.id.activity_trans_name)
    void onAmountClick() {

        tvAll.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvAmount.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvName.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlue));
        tvType.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));


        if (NameAsc) {
            Collections.sort(data, Mylog.LogByNameA);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            NameAsc = false;
        } else {
            Collections.sort(data, Mylog.LogByNameD);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            NameAsc = true;
        }


    }

    //Sort By Type
    @OnClick(R.id.activity_trans_type)
    void onTypeClick() {

        tvAll.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvAmount.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvName.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlack));
        tvType.setTextColor(ContextCompat.getColor(this, R.color.colorTextBlue));


        if (TypeAsc) {
            Collections.sort(data, Mylog.LogByTypeA);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            TypeAsc = false;
        } else {
            Collections.sort(data, Mylog.LogByTypeD);
            TransactionListAdapter adapter = new TransactionListAdapter(this, data);
            list.setAdapter(adapter);
            TypeAsc = true;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_history_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_search) {
            DialogFragment dialog = new FilterTransactionListDialog();
            dialog.show(getSupportFragmentManager(), FilterTransactionListDialog.class.getName());
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onOkClick(boolean isAmountEnable, String minAmount, String maxAmount,
                          boolean isDateEnable, String startDate, String endDate,
                          boolean isFilterEnable, String typeText,
                          String searchName)


    {

        populateHistoryList2(isAmountEnable, minAmount, maxAmount, isDateEnable, startDate, endDate, isFilterEnable, typeText, searchName);


    }
}
