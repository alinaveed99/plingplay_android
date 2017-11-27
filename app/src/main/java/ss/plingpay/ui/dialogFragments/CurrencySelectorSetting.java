package ss.plingpay.ui.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import ss.plingpay.R;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.ui.activities.SettingActivity;
import ss.plingpay.ui.adapters.CurrencyAdapter;
import ss.plingpay.utilz.Listeners;

/**
 * Created by samar_000 on 10/27/2016.
 */

public class CurrencySelectorSetting extends DialogFragment implements Listeners.CurrencyListener {


    @BindView(R.id.dialog_currency_picker_list)
    RecyclerView list;

    protected Realm realm;


    private Unbinder unbinder;
    private Listeners.CurrencySelecter listerner;
    private String[] data;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listerner = (SettingActivity) getActivity();
        realm = Realm.getDefaultInstance();

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_currency_picker, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void init() {

        RealmResults<CurrencyList> result = realm.where(CurrencyList.class).findAll();

        list.setLayoutManager(new LinearLayoutManager(getActivity()));

        CurrencyAdapter adapter = new CurrencyAdapter(result, (AppCompatActivity) getActivity(), this);
        list.setAdapter(adapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnClick(String currency, String id) {
        listerner.selectedItem(currency, id);
        this.getDialog().dismiss();
    }
}

