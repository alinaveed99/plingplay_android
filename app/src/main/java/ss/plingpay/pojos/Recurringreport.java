
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Recurringreport {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("senderid")
    @Expose
    private String senderid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("no_recurring")
    @Expose
    private String noRecurring;
    @SerializedName("recurring_occured")
    @Expose
    private String recurringOccured;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The senderid
     */
    public String getSenderid() {
        return senderid;
    }

    /**
     * 
     * @param senderid
     *     The senderid
     */
    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The noRecurring
     */
    public String getNoRecurring() {
        return noRecurring;
    }

    /**
     * 
     * @param noRecurring
     *     The no_recurring
     */
    public void setNoRecurring(String noRecurring) {
        this.noRecurring = noRecurring;
    }

    /**
     * 
     * @return
     *     The recurringOccured
     */
    public String getRecurringOccured() {
        return recurringOccured;
    }

    /**
     * 
     * @param recurringOccured
     *     The recurring_occured
     */
    public void setRecurringOccured(String recurringOccured) {
        this.recurringOccured = recurringOccured;
    }

}
