
package ss.plingpay.pojos.CurrencyPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class ExchangeList extends RealmObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("currancy_id")
    @Expose
    private String currancyId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;

    public ExchangeList() {

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
     * @return The currancyId
     */
    public String getCurrancyId() {
        return currancyId;
    }

    /**
     * @param currancyId The currancy_id
     */
    public void setCurrancyId(String currancyId) {
        this.currancyId = currancyId;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
