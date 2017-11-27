package ss.plingpay.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samar_000 on 4/15/2016.
 */
public class DatePOJO implements Parcelable  {
    public static final Creator<DatePOJO> CREATOR = new Creator<DatePOJO>() {
        @Override
        public DatePOJO createFromParcel(Parcel in) {
            return new DatePOJO(in);
        }

        @Override
        public DatePOJO[] newArray(int size) {
            return new DatePOJO[size];
        }
    };
    private String day;
    private String month;
    private String year;

    public DatePOJO() {

    }

    protected DatePOJO(Parcel in) {
        day = in.readString();
        month = in.readString();
        year = in.readString();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(month);
        dest.writeString(year);
    }
}
