
package ss.plingpay.pojos;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Invoices {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("payments")
    @Expose
    private List<Payment> payments = new ArrayList<Payment>();

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
     *     The payments
     */
    public List<Payment> getPayments() {
        return payments;
    }

    /**
     * 
     * @param payments
     *     The payments
     */
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

}
