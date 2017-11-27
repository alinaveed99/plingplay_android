
package ss.plingpay.pojos.CurrencyPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CurrenciesPOJO {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("currencyList")
    @Expose
    private List<CurrencyList> currencyList = new ArrayList<CurrencyList>();
    @SerializedName("exchangeList")
    @Expose
    private List<ExchangeList> exchangeList = new ArrayList<ExchangeList>();

    /**
     * @return The response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return The currencyList
     */
    public List<CurrencyList> getCurrencyList() {
        return currencyList;
    }

    /**
     * @param currencyList The currencyList
     */
    public void setCurrencyList(List<CurrencyList> currencyList) {
        this.currencyList = currencyList;
    }

    /**
     * @return The exchangeList
     */
    public List<ExchangeList> getExchangeList() {
        return exchangeList;
    }

    /**
     * @param exchangeList The exchangeList
     */
    public void setExchangeList(List<ExchangeList> exchangeList) {
        this.exchangeList = exchangeList;
    }

}
