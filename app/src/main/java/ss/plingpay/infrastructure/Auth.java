package ss.plingpay.infrastructure;

import ss.plingpay.pojos.UserLogin;
import ss.plingpay.pojos.WalletInfo;

/**
 * Created by samar_000 on 8/16/2016.
 */

public class Auth {



    private UserLogin userLogin;
    private WalletInfo wallet;
    private String phoneContacts;

    Auth() {
        userLogin = new UserLogin();
        wallet = new WalletInfo();
    }


    public WalletInfo getWallet() {
        return wallet;
    }

    public UserLogin getUser() {
        return userLogin;
    }


    public String getPhoneContacts() {
        return phoneContacts;
    }

    public void setPhoneContacts(String phoneContacts) {
        this.phoneContacts = phoneContacts;
    }
}
