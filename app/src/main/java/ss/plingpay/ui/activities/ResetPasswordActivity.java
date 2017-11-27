package ss.plingpay.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.ui.activities.splashScreenView.NewSplashScreenActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by Sammie on 2/9/2017.
 */

public class ResetPasswordActivity extends AppCompatActivity {


    private static final String TAG = ResetPasswordActivity.class.getName().toUpperCase();
    @BindView(R.id.activity_reset_password)
    TextInputEditText etPassword;

    @BindView(R.id.activity_reset_rePassword)
    TextInputEditText etRePassword;
    private String user_email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        Intent i = getIntent();
        String link = i.getDataString();
        user_email = link.replace("http://bit4m.org/admin/users/resetpassword/", "");


    }


    @OnClick(R.id.activity_reset_password_btnReset)
    void onBtnCLick() {

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Required Field");
            return;
        } else {
            etPassword.setError(null);
        }


        if (etRePassword.getText().toString().isEmpty()) {
            etRePassword.setError("Required Field");
            return;
        } else {
            etRePassword.setError(null);
        }


        if (!etPassword.getText().toString().trim().equalsIgnoreCase(
                etRePassword.getText().toString().trim())) {

            Utilz.tmsg(this, "Error, Passwords are different");
            return;
        }


        resetPassword(user_email, etPassword.getText().toString().trim());


    }


    void resetPassword(String email, String password) {

        WebServices api = Utilz.getRxRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        ProgressDialog dialog = ProgressDialog.show(this, "Please Wait", "Loading...");


        api.resetPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    dialog.dismiss();
                    Utilz.tmsg(this, "Error, Please Try Again");
                    Log.e(TAG, "resetPassword: ", throwable);
                })
                .subscribe(data -> {


                    if (data == null || !data.isSuccessful()) {
                        dialog.dismiss();
                        Utilz.tmsg(this, "Error, Please Try Again");
                        return;
                    }


                    try {


                        JsonObject obj = data.body().getAsJsonObject();
                        String response = obj.get("response").getAsString();

                        if (response.equalsIgnoreCase("success")) {

                            Utilz.tmsg(this, "Your password has been successfully changed");
                            startActivity(new Intent(this, NewSplashScreenActivity.class));
                            finish();

                        } else {

                            Utilz.tmsg(this, "Error, Please Try Again");

                        }


                    } catch (Exception e) {
                        Utilz.tmsg(this, "Error, Please Try Again");
                        Log.e(TAG, "resetPassword: ", e);
                    }


                    dialog.dismiss();
                });


    }

}
