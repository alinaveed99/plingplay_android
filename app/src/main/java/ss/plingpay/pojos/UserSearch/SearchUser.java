
package ss.plingpay.pojos.UserSearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SearchUser {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("userinfo")
    @Expose
    private ArrayList<Userinfo> userinfo = new ArrayList<Userinfo>();

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
     * @return The userinfo
     */
    public ArrayList<Userinfo> getUserinfo() {
        return userinfo;
    }

    /**
     * @param userinfo The userinfo
     */
    public void setUserinfo(ArrayList<Userinfo> userinfo) {
        this.userinfo = userinfo;
    }

}
