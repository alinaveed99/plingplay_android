package ss.plingpay.pojos;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TransactionHistoryPOJO {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("mylogs")
    @Expose
    private List<Mylog> mylogs = new ArrayList<Mylog>();

    /**
     *
     * @return
     * The response
     */
    public String getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     *
     * @return
     * The mylogs
     */
    public List<Mylog> getMylogs() {
        return mylogs;
    }

    /**
     *
     * @param mylogs
     * The mylogs
     */
    public void setMylogs(List<Mylog> mylogs) {
        this.mylogs = mylogs;
    }

}