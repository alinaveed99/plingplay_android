package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class OrderListPojo {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("logs")
    @Expose
    private List<Order_log> logs = new ArrayList<Order_log>();

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
     * @return The logs
     */
    public List<Order_log> getLogs() {
        return logs;
    }

    /**
     * @param logs The logs
     */
    public void setLogs(List<Order_log> logs) {
        this.logs = logs;
    }

}
