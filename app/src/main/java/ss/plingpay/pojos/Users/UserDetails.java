
package ss.plingpay.pojos.Users;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

//@Generated("org.jsonschema2pojo")
public class UserDetails extends RealmObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("currancy_id")
    @Expose
    private String currancyId;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("default_address")
    @Expose
    private String defaultAddress;
    @SerializedName("isfavourite")
    @Expose
    private String isfavourite;
    @SerializedName("favouriteuserid")
    @Expose
    private String favouriteuserid;


    @SerializedName("accountnumber")
    @Expose
    private String accountnumber;

    @SerializedName("bankname")
    @Expose
    private String bankname;

    @SerializedName("branchcode")
    @Expose
    private String branchcode;

    @SerializedName("accounttilte")
    @Expose
    private String accounttilte;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("response")
    @Expose
    private String response;

    @SerializedName("exchangeid")
    @Expose
    private String exchangeid;


    @SerializedName("api_key")
    @Expose
    private String api_key;

    @SerializedName("api_secret")
    @Expose
    private String api_secret;


    public UserDetails() {

    }

    protected UserDetails(Parcel in) {
        id = in.readString();
        fname = in.readString();
        lname = in.readString();
        email = in.readString();
        phone = in.readString();
        zip = in.readString();
        city = in.readString();
        state = in.readString();
        country = in.readString();
        currancyId = in.readString();
        company = in.readString();
        lat = in.readString();
        _long = in.readString();
        defaultAddress = in.readString();
        isfavourite = in.readString();
        favouriteuserid = in.readString();
        status = in.readString();

        accountnumber = in.readString();
        bankname = in.readString();
        branchcode = in.readString();
        accounttilte = in.readString();

        response = in.readString();
        exchangeid = in.readString();

        api_key = in.readString();
        api_secret = in.readString();


    }

    public String getExchangeid() {
        return exchangeid;
    }

    public void setExchangeid(String exchangeid) {
        this.exchangeid = exchangeid;
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel in) {
            return new UserDetails(in);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrancyId() {
        return currancyId;
    }

    public void setCurrancyId(String currancyId) {
        this.currancyId = currancyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String get_long() {
        return _long;
    }

    public void set_long(String _long) {
        this._long = _long;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(String isfavourite) {
        this.isfavourite = isfavourite;
    }

    public String getFavouriteuserid() {
        return favouriteuserid;
    }

    public void setFavouriteuserid(String favouriteuserid) {
        this.favouriteuserid = favouriteuserid;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getAccounttilte() {
        return accounttilte;
    }

    public void setAccounttilte(String accounttilte) {
        this.accounttilte = accounttilte;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_secret() {
        return api_secret;
    }

    public void setApi_secret(String api_secret) {
        this.api_secret = api_secret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(fname);
        parcel.writeString(lname);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(zip);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
        parcel.writeString(currancyId);
        parcel.writeString(company);
        parcel.writeString(lat);
        parcel.writeString(_long);
        parcel.writeString(defaultAddress);
        parcel.writeString(isfavourite);
        parcel.writeString(favouriteuserid);
        parcel.writeString(accountnumber);
        parcel.writeString(bankname);
        parcel.writeString(branchcode);
        parcel.writeString(accounttilte);
        parcel.writeString(status);
        parcel.writeString(response);
        parcel.writeString(exchangeid);

        parcel.writeString(api_key);
        parcel.writeString(api_secret);
    }
}
