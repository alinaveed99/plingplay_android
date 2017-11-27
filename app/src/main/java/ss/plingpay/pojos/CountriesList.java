package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

/**
 * Created by samar_000 on 8/11/2016.
 */

@Generated("org.jsonschema2pojo")
public class CountriesList extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("countryName")
    @Expose
    private String countryName;

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
     * The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName
     * The countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
