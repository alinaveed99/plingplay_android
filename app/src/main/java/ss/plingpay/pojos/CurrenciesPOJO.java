package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

/**
 * Created by samar_000 on 6/16/2016.
 */

@Generated("org.jsonschema2pojo")
public class CurrenciesPOJO extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currancyName")
    @Expose
    private String currancyName;


    public CurrenciesPOJO() {

    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The currancyName
     */
    public String getCurrancyName() {
        return currancyName;
    }

    /**
     *
     * @param currancyName
     * The currancyName
     */
    public void setCurrancyName(String currancyName) {
        this.currancyName = currancyName;
    }

}
