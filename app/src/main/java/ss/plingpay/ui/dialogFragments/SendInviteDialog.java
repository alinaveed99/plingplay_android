package ss.plingpay.ui.dialogFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.R;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 1/30/2017.
 */

public class SendInviteDialog extends DialogFragment {


    private Unbinder unbinder;

    private PlingPay app;

    protected WebServices api;

    @BindView(R.id.dialog_invite_etEmail)
    EditText etEmail;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((MainActivity) getActivity()).getApp();


    }

    protected PlingPay getApp() {
        return app;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_invite, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

    }


    @OnClick(R.id.dialog_invite_btnInvite)
    void onInviteClick() {

        if (etEmail.getText() == null || etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Required Field");
            return;
        } else {
            etEmail.setError(null);
        }

        ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Loading...");


        api = Utilz.getRxRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        api.sendInvite(etEmail.getText().toString().trim(), getApp().getAuth().getUser().getUserinfo().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                })
                .subscribe(data -> {
                    dialog.dismiss();

                    JsonObject obj = data.getAsJsonObject();
                    String status = obj.get("response").getAsString();

                    if (status.equalsIgnoreCase("success")) {

                        Toast.makeText(getActivity(), "Invitation has been Sent", Toast.LENGTH_SHORT).show();

                    } else if (status.equalsIgnoreCase("exists")) {

                        Toast.makeText(getActivity(), "Email Already Exists", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                    }

                    this.getDialog().dismiss();


                });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
