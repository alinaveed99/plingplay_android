package ss.plingpay.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import ss.plingpay.pojos.Users.UserDetails;


/**
 * Created by samar_000 on 4/5/2016.
 */

public class PaymentPOJO implements Parcelable {

//    private String Amount;
//    private String Currency;
//    private String PaymentMethod;
//    private String Receiver;
//    private boolean isBuyBitcoin;
//    private DatePOJO date;
//    private UserDetails receiverInfo;

    private String Amount;
    private String Currency;
    private String curr_id;
    private UserDetails receiverInfo;
    private boolean isBuyBitcoin;



    public PaymentPOJO() {
        //Empty Constructor
    }


    public String getCurr_id() {
        return curr_id;
    }

    public void setCurr_id(String curr_id) {
        this.curr_id = curr_id;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public UserDetails getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(UserDetails receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public boolean isBuyBitcoin() {
        return isBuyBitcoin;
    }

    public void setBuyBitcoin(boolean buyBitcoin) {
        isBuyBitcoin = buyBitcoin;
    }

    protected PaymentPOJO(Parcel in) {
        Amount = in.readString();
        Currency = in.readString();
        curr_id = in.readString();
        receiverInfo = in.readParcelable(UserDetails.class.getClassLoader());
        isBuyBitcoin = in.readByte() != 0;
    }

    public static final Creator<PaymentPOJO> CREATOR = new Creator<PaymentPOJO>() {
        @Override
        public PaymentPOJO createFromParcel(Parcel in) {
            return new PaymentPOJO(in);
        }

        @Override
        public PaymentPOJO[] newArray(int size) {
            return new PaymentPOJO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Amount);
        parcel.writeString(Currency);
        parcel.writeString(curr_id);
        parcel.writeParcelable(receiverInfo, i);
        parcel.writeByte((byte) (isBuyBitcoin ? 1 : 0));
    }
}
