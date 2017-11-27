package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by samar_000 on 7/29/2016.
 */


@Generated("org.jsonschema2pojo")
public class Order_log {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("inputAddress")
    @Expose
    private String inputAddress;
    @SerializedName("transid")
    @Expose
    private String transid;
    @SerializedName("btcsend")
    @Expose
    private String btcsend;
    @SerializedName("amountinpkr")
    @Expose
    private String amountinpkr;

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
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getStatus() {
        return status;
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
     *     The inputAddress
     */
    public String getInputAddress() {
        return inputAddress;
    }

    /**
     *
     * @param inputAddress
     *     The inputAddress
     */
    public void setInputAddress(String inputAddress) {
        this.inputAddress = inputAddress;
    }

    /**
     *
     * @return
     *     The transid
     */
    public String getTransid() {
        return transid;
    }

    /**
     *
     * @param transid
     *     The transid
     */
    public void setTransid(String transid) {
        this.transid = transid;
    }

    /**
     *
     * @return
     *     The btcsend
     */
    public String getBtcsend() {
        return btcsend;
    }

    /**
     *
     * @param btcsend
     *     The btcsend
     */
    public void setBtcsend(String btcsend) {
        this.btcsend = btcsend;
    }

    /**
     *
     * @return
     *     The amountinpkr
     */
    public String getAmountinpkr() {
        return amountinpkr;
    }

    /**
     *
     * @param amountinpkr
     *     The amountinpkr
     */
    public void setAmountinpkr(String amountinpkr) {
        this.amountinpkr = amountinpkr;
    }

}
