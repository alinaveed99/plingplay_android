package ss.plingpay.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.ui.fragments.LoginFragment;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/16/2016.
 */

public class LoginActivity extends BaseActivity implements Listeners.WalletLogin {

    public static final java.lang.String ISLOGIN = "islogin";
    private static final int CREATE_WALLET_REQUEST_CODE = 1;
    private boolean isLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();

        isLogin = true;

        initComponents();
    }

    private void initComponents() {

        if (isLogin) {
            openFragment(R.id.activity_login_container, new LoginFragment(), LoginFragment.class.getName(), false);
        }

    }

    @Override
    public void onCreateWallet() {
        startActivityForResult(new Intent(this, CreateWalletActivity.class), CREATE_WALLET_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CREATE_WALLET_REQUEST_CODE) {
            Utilz.tmsgSuccess(this, "Wallet Successfully Create");
        }
    }
}
