
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Recurringpayment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("senderid")
    @Expose
    private String senderid;
    @SerializedName("receiverid")
    @Expose
    private String receiverid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("no_recurring")
    @Expose
    private String noRecurring;
    @SerializedName("recurring_occured")
    @Expose
    private String recurringOccured;
    @SerializedName("isdelete")
    @Expose
    private String isdelete;

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
     *     The receiverid
     */
    public String getReceiverid() {
        return receiverid;
    }

    /**
     * 
     * @param receiverid
     *     The receiverid
     */
    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
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
     *     The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject
     *     The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
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

    /**
     * 
     * @return
     *     The isdelete
     */
    public String getIsdelete() {
        return isdelete;
    }

    /**
     * 
     * @param isdelete
     *     The isdelete
     */
    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

}
