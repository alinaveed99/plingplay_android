
package ss.plingpay.pojos.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Users {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("userinfo")
    @Expose
    private ArrayList<UserDetails> userinfo = new ArrayList<UserDetails>();

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
    public ArrayList<UserDetails> getUserinfo() {
        return userinfo;
    }

    /**
     * @param userinfo The userinfo
     */
    public void setUserinfo(ArrayList<UserDetails> userinfo) {
        this.userinfo = userinfo;
    }

}
