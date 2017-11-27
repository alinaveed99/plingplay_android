
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Bitcoindetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("default_address")
    @Expose
    private String defaultAddress;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("user_label")
    @Expose
    private String userLabel;
    @SerializedName("bitcoin_email")
    @Expose
    private String bitcoinEmail;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("is_default")
    @Expose
    private String isDefault;

    @SerializedName("walletpassword")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * 
     * @param guid
     *     The guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     * 
     * @return
     *     The defaultAddress
     */
    public String getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * 
     * @param defaultAddress
     *     The default_address
     */
    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
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
     *     The userLabel
     */
    public String getUserLabel() {
        return userLabel;
    }

    /**
     * 
     * @param userLabel
     *     The user_label
     */
    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    /**
     * 
     * @return
     *     The bitcoinEmail
     */
    public String getBitcoinEmail() {
        return bitcoinEmail;
    }

    /**
     * 
     * @param bitcoinEmail
     *     The bitcoin_email
     */
    public void setBitcoinEmail(String bitcoinEmail) {
        this.bitcoinEmail = bitcoinEmail;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The isDefault
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * 
     * @param isDefault
     *     The is_default
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

}
