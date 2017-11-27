
package ss.plingpay.pojos.FavoriteUsers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class FavoriteUsers {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("requestlogs")
    @Expose
    private ArrayList<Requestlog> requestlogs = new ArrayList<Requestlog>();

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
     * @return The requestlogs
     */
    public ArrayList<Requestlog> getRequestlogs() {
        return requestlogs;
    }

    /**
     * @param requestlogs The requestlogs
     */
    public void setRequestlogs(ArrayList<Requestlog> requestlogs) {
        this.requestlogs = requestlogs;
    }

}
