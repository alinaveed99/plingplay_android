
package ss.plingpay.pojos;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ExchangeListPOJO {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("exchanges")
    @Expose
    private List<Exchange> exchanges = new ArrayList<Exchange>();

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
     *     The exchanges
     */
    public List<Exchange> getExchanges() {
        return exchanges;
    }

    /**
     * 
     * @param exchanges
     *     The exchanges
     */
    public void setExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

}
