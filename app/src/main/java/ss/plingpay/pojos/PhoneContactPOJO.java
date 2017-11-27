package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import io.realm.RealmObject;
import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by samar_000 on 8/24/2016.
 */

@Generated("org.jsonschema2pojo")
public class PhoneContactPOJO  {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("users")
    @Expose
    private List<UserDetails> users = new ArrayList<UserDetails>();

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
     * @return The users
     */
    public List<UserDetails> getUsers() {
        return users;
    }

    /**
     * @param users The users
     */
    public void setUsers(List<UserDetails> users) {
        this.users = users;
    }

}
