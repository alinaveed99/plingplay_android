
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserLogin {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("userinfo")
    @Expose
    private Userinfo userinfo;
    @SerializedName("bitcoindetails")
    @Expose
    private List<Bitcoindetail> bitcoindetails = new ArrayList<Bitcoindetail>();
    @SerializedName("recurringpayments")
    @Expose
    private List<Recurringpayment> recurringpayments = new ArrayList<Recurringpayment>();
    @SerializedName("recurringreports")
    @Expose
    private List<Recurringreport> recurringreports = new ArrayList<Recurringreport>();

    /**
     * 
     * @return
     *     The response
     */
    public String getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * 
     * @return
     *     The userinfo
     */
    public Userinfo getUserinfo() {
        return userinfo;
    }

    /**
     * 
     * @param userinfo
     *     The userinfo
     */
    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    /**
     * 
     * @return
     *     The bitcoindetails
     */
    public List<Bitcoindetail> getBitcoindetails() {
        return bitcoindetails;
    }

    /**
     * 
     * @param bitcoindetails
     *     The bitcoindetails
     */
    public void setBitcoindetails(List<Bitcoindetail> bitcoindetails) {
        this.bitcoindetails = bitcoindetails;
    }

    /**
     * 
     * @return
     *     The recurringpayments
     */
    public List<Recurringpayment> getRecurringpayments() {
        return recurringpayments;
    }

    /**
     * 
     * @param recurringpayments
     *     The recurringpayments
     */
    public void setRecurringpayments(List<Recurringpayment> recurringpayments) {
        this.recurringpayments = recurringpayments;
    }

    /**
     * 
     * @return
     *     The recurringreports
     */
    public List<Recurringreport> getRecurringreports() {
        return recurringreports;
    }

    /**
     * 
     * @param recurringreports
     *     The recurringreports
     */
    public void setRecurringreports(List<Recurringreport> recurringreports) {
        this.recurringreports = recurringreports;
    }

}
