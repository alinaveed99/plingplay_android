package ss.plingpay.pojos;

/**
 * Created by samar_000 on 9/1/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

//@Generated("org.jsonschema2pojo")
public class Mylog implements Parcelable {

    @SerializedName("senderid")
    @Expose
    private String senderid;
    @SerializedName("receiverid")
    @Expose
    private String receiverid;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("senderaddress")
    @Expose
    private String senderaddress;
    @SerializedName("receiveraddress")
    @Expose
    private String receiveraddress;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("amountinbtc")
    @Expose
    private String amountinbtc;
    @SerializedName("amtinbtcremaning")
    @Expose
    private String amtinbtcremaning;
    @SerializedName("amtinbtccompany")
    @Expose
    private String amtinbtccompany;
    @SerializedName("currancy_id")
    @Expose
    private String currancyId;
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("sendername")
    @Expose
    private String sendername;
    @SerializedName("receivername")
    @Expose
    private String receivername;

    @SerializedName("dateforsorting")
    @Expose
    private String dateforsorting;

    @SerializedName("date")
    @Expose
    private String date;

    protected Mylog(Parcel in) {
        senderid = in.readString();
        receiverid = in.readString();
        hash = in.readString();
        message = in.readString();
        senderaddress = in.readString();
        receiveraddress = in.readString();
        amount = in.readString();
        amountinbtc = in.readString();
        amtinbtcremaning = in.readString();
        amtinbtccompany = in.readString();
        currancyId = in.readString();
        type = in.readString();

        sendername = in.readString();
        receivername = in.readString();

        dateforsorting = in.readString();
        date = in.readString();
    }

    public static final Creator<Mylog> CREATOR = new Creator<Mylog>() {
        @Override
        public Mylog createFromParcel(Parcel in) {
            return new Mylog(in);
        }

        @Override
        public Mylog[] newArray(int size) {
            return new Mylog[size];
        }
    };

    /**
     * @return The senderid
     */
    public String getSenderid() {
        return senderid;
    }

    /**
     * @param senderid The senderid
     */
    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    /**
     * @return The receiverid
     */
    public String getReceiverid() {
        return receiverid;
    }

    /**
     * @param receiverid The receiverid
     */
    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    /**
     * @return The hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash The hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The senderaddress
     */
    public String getSenderaddress() {
        return senderaddress;
    }

    /**
     * @param senderaddress The senderaddress
     */
    public void setSenderaddress(String senderaddress) {
        this.senderaddress = senderaddress;
    }

    /**
     * @return The receiveraddress
     */
    public String getReceiveraddress() {
        return receiveraddress;
    }

    /**
     * @param receiveraddress The receiveraddress
     */
    public void setReceiveraddress(String receiveraddress) {
        this.receiveraddress = receiveraddress;
    }

    /**
     * @return The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return The amountinbtc
     */
    public String getAmountinbtc() {
        return amountinbtc;
    }

    /**
     * @param amountinbtc The amountinbtc
     */
    public void setAmountinbtc(String amountinbtc) {
        this.amountinbtc = amountinbtc;
    }

    /**
     * @return The amtinbtcremaning
     */
    public String getAmtinbtcremaning() {
        return amtinbtcremaning;
    }

    /**
     * @param amtinbtcremaning The amtinbtcremaning
     */
    public void setAmtinbtcremaning(String amtinbtcremaning) {
        this.amtinbtcremaning = amtinbtcremaning;
    }

    /**
     * @return The amtinbtccompany
     */
    public String getAmtinbtccompany() {
        return amtinbtccompany;
    }

    /**
     * @param amtinbtccompany The amtinbtccompany
     */
    public void setAmtinbtccompany(String amtinbtccompany) {
        this.amtinbtccompany = amtinbtccompany;
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
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return The type
     */
    public String getReceivername() {
        return receivername;
    }

    /**
     * @param receivername The type
     */
    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }


    /**
     * @return The type
     */
    public String getSendername() {
        return sendername;
    }

    /**
     * @param sendername The type
     */
    public void setSendername(String sendername) {
        this.sendername = sendername;
    }


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public String getDateforsorting() {
        return dateforsorting;
    }


    public void setDateforsorting(String dateforsorting) {
        this.dateforsorting = dateforsorting;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(senderid);
        parcel.writeString(receiverid);
        parcel.writeString(hash);
        parcel.writeString(message);
        parcel.writeString(senderaddress);
        parcel.writeString(receiveraddress);
        parcel.writeString(amount);
        parcel.writeString(amountinbtc);
        parcel.writeString(amtinbtcremaning);
        parcel.writeString(amtinbtccompany);
        parcel.writeString(currancyId);
        parcel.writeString(type);

        parcel.writeString(receivername);
        parcel.writeString(senderid);

        parcel.writeString(dateforsorting);
        parcel.writeString(date);
    }


    public static Comparator<Mylog> LogByNameA = (s1, s2) -> {
        String Rec1 = s1.getReceivername().toUpperCase();
        String Rec2 = s2.getReceivername().toUpperCase();

        //ascending order
        return Rec1.compareTo(Rec2);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };

    public static Comparator<Mylog> LogByNameD = (s1, s2) -> {
        String Rec1 = s1.getReceivername().toUpperCase();
        String Rec2 = s2.getReceivername().toUpperCase();

        //ascending order
        return Rec2.compareTo(Rec1);

    };


    public static Comparator<Mylog> LogByAmountA = (s1, s2) -> {

        Double Rec1 = Double.valueOf(s1.getAmount());
        Double Rec2 = Double.valueOf(s2.getAmount());

        //ascending order
        return Rec2.compareTo(Rec1);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };

    public static Comparator<Mylog> LogByAmountD = (s1, s2) -> {
        Double Rec1 = Double.valueOf(s1.getAmount());
        Double Rec2 = Double.valueOf(s2.getAmount());

        //ascending order
        return Rec1.compareTo(Rec2);


    };


    public static Comparator<Mylog> LogByTypeA = (s1, s2) -> {

        Double Rec1 = Double.valueOf(s1.getType());
        Double Rec2 = Double.valueOf(s2.getType());

        //ascending order
        return Rec2.compareTo(Rec1);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };

    public static Comparator<Mylog> LogByTypeD = (s1, s2) -> {
        Double Rec1 = Double.valueOf(s1.getType());
        Double Rec2 = Double.valueOf(s2.getType());

        //ascending order
        return Rec1.compareTo(Rec2);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };


    public static Comparator<Mylog> LogByDateA = (s1, s2) -> {
        Double Rec1 = Double.valueOf(s1.getDateforsorting());
        Double Rec2 = Double.valueOf(s2.getDateforsorting());

        //ascending order
        return Rec2.compareTo(Rec1);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };

    public static Comparator<Mylog> LogByDateD = (s1, s2) -> {
        Double Rec1 = Double.valueOf(s1.getDateforsorting());
        Double Rec2 = Double.valueOf(s2.getDateforsorting());

        //ascending order
        return Rec1.compareTo(Rec2);

        //descending order
        //return StudentName2.compareTo(StudentName1);
    };


}