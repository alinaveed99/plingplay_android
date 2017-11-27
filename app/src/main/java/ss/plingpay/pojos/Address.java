
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Address {

    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("total_received")
    @Expose
    private Integer totalReceived;

    /**
     * 
     * @return
     *     The balance
     */
    public Integer getBalance() {
        return balance;
    }

    /**
     * 
     * @param balance
     *     The balance
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The totalReceived
     */
    public Integer getTotalReceived() {
        return totalReceived;
    }

    /**
     * 
     * @param totalReceived
     *     The total_received
     */
    public void setTotalReceived(Integer totalReceived) {
        this.totalReceived = totalReceived;
    }

}
