
package ss.plingpay.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class Payment implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("requester")
    @Expose
    private Requester requester;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("amountinbtc")
    @Expose
    private String amountinbtc;
    @SerializedName("currancyvalue")
    @Expose
    private String currancyvalue;
    @SerializedName("default_address")
    @Expose
    private String defaultAddress;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("requesteddate")
    @Expose
    private String requesteddate;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("requesterexchange")
    @Expose
    private String exchangeId;

    protected Payment(Parcel in) {
        id = in.readString();
        request = in.readString();
        amount = in.readString();
        amountinbtc = in.readString();
        currancyvalue = in.readString();
        defaultAddress = in.readString();
        description = in.readString();
        requesteddate = in.readString();
        status = in.readString();
        exchangeId = in.readString();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

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
     *     The requester
     */
    public Requester getRequester() {
        return requester;
    }

    /**
     * 
     * @param requester
     *     The requester
     */
    public void setRequester(Requester requester) {
        this.requester = requester;
    }

    /**
     * 
     * @return
     *     The request
     */
    public String getRequest() {
        return request;
    }

    /**
     * 
     * @param request
     *     The request
     */
    public void setRequest(String request) {
        this.request = request;
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
     *     The amountinbtc
     */
    public String getAmountinbtc() {
        return amountinbtc;
    }

    /**
     * 
     * @param amountinbtc
     *     The amountinbtc
     */
    public void setAmountinbtc(String amountinbtc) {
        this.amountinbtc = amountinbtc;
    }

    /**
     * 
     * @return
     *     The currancyvalue
     */
    public String getCurrancyvalue() {
        return currancyvalue;
    }

    /**
     * 
     * @param currancyvalue
     *     The currancyvalue
     */
    public void setCurrancyvalue(String currancyvalue) {
        this.currancyvalue = currancyvalue;
    }

    /**
     * 
     * @return
     *     The defaultAddress
     */
    public String getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * 
     * @param defaultAddress
     *     The default_address
     */
    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The requesteddate
     */
    public String getRequesteddate() {
        return requesteddate;
    }

    /**
     * 
     * @param requesteddate
     *     The requesteddate
     */
    public void setRequesteddate(String requesteddate) {
        this.requesteddate = requesteddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(request);
        parcel.writeString(amount);
        parcel.writeString(amountinbtc);
        parcel.writeString(currancyvalue);
        parcel.writeString(defaultAddress);
        parcel.writeString(description);
        parcel.writeString(requesteddate);
        parcel.writeString(status);
        parcel.writeString(exchangeId);
    }
}
