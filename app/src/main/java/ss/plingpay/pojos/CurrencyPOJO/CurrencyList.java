
package ss.plingpay.pojos.CurrencyPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class CurrencyList extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currancyName")
    @Expose
    private String currancyName;

    public CurrencyList() {

    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The currancyName
     */
    public String getCurrancyName() {
        return currancyName;
    }

    /**
     * @param currancyName The currancyName
     */
    public void setCurrancyName(String currancyName) {
        this.currancyName = currancyName;
    }

}
