package ss.plingpay.pojos;

/**
 * Created by samar_000 on 8/15/2016.
 */

public class WalletInfo {

    private String guid;
    private String password;
    private String guid_id;
    private String balance;


    public WalletInfo() {

    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGuid_id() {
        return guid_id;
    }

    public void setGuid_id(String guid_id) {
        this.guid_id = guid_id;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
