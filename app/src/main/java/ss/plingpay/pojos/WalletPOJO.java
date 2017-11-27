package ss.plingpay.pojos;

/**
 * Created by samar_000 on 6/15/2016.
 */
public class WalletPOJO {

    private String guid;
    private String password;
    private String balance;


    public WalletPOJO() {

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
