
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Userinfo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("authtoken")
    @Expose
    private String authtoken;
    @SerializedName("currancy_id")
    @Expose
    private String currancyId;
    @SerializedName("default_address")
    @Expose
    private String defaultAddress;
    //    @SerializedName("secret_question")
//    @Expose
//    private String secretQuestion;
//    @SerializedName("secret_answer")
//    @Expose
//    private String secretAnswer;
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("currancyexchange_id")
    @Expose
    private String exchangeid;

    @SerializedName("bankaccountnumber")
    @Expose
    private String bankaccountnumber;

    @SerializedName("bankname")
    @Expose
    private String bankname;

    @SerializedName("accounttilte")
    @Expose
    private String accounttilte;

    @SerializedName("curr_id")
    @Expose
    private String curr_id;

    @SerializedName("pincode")
    @Expose
    private String pincode;


    @SerializedName("cnic")
    @Expose
    private String cnic;


    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("withdrawaltypetext")
    @Expose
    private String withdrawaltypetext;


    @SerializedName("accounttype")
    @Expose
    private String accounttype;

    @SerializedName("rut")
    @Expose
    private String rut;


    @SerializedName("secret_question")
    @Expose
    private String secret_question;

    @SerializedName("secret_answer")
    @Expose
    private String secret_answer;


    @SerializedName("api_key")
    @Expose
    private String api_key;

    @SerializedName("api_secret")
    @Expose
    private String api_secret;


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

    public String getSecret_question() {
        return secret_question;
    }

    public void setSecret_question(String secret_question) {
        this.secret_question = secret_question;
    }

    public String getSecret_answer() {
        return secret_answer;
    }

    public void setSecret_answer(String secret_answer) {
        this.secret_answer = secret_answer;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getWithdrawaltypetext() {
        return withdrawaltypetext;
    }

    public void setWithdrawaltypetext(String withdrawaltypetext) {
        this.withdrawaltypetext = withdrawaltypetext;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCurr_id() {
        return curr_id;
    }

    public void setCurr_id(String curr_id) {
        this.curr_id = curr_id;
    }

    public String getBankaccountnumber() {
        return bankaccountnumber;
    }

    public void setBankaccountnumber(String bankaccountnumber) {
        this.bankaccountnumber = bankaccountnumber;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccounttilte() {
        return accounttilte;
    }

    public void setAccounttilte(String accounttilte) {
        this.accounttilte = accounttilte;
    }

    public String getExchangeid() {
        return exchangeid;
    }

    public void setExchangeid(String exchangeid) {
        this.exchangeid = exchangeid;
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
     * @return The tvFirstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The tvFirstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The tvLastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The tvLastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType The user_type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return The company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * @return The _long
     */
    public String getLong() {
        return _long;
    }

    /**
     * @param _long The long
     */
    public void setLong(String _long) {
        this._long = _long;
    }

    /**
     * @return The authtoken
     */
    public String getAuthtoken() {
        return authtoken;
    }

    /**
     * @param authtoken The authtoken
     */
    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
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
     * @return The defaultAddress
     */
    public String getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * @param defaultAddress The default_address
     */
    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    /**
     * @return The secretQuestion
     */
//    public String getSecretQuestion() {
//        return secretQuestion;
//    }
//
//    /**
//     * @param secretQuestion The secret_question
//     */
//    public void setSecretQuestion(String secretQuestion) {
//        this.secretQuestion = secretQuestion;
//    }
//
//    /**
//     * @return The secretAnswer
//     */
//    public String getSecretAnswer() {
//        return secretAnswer;
//    }
//
//    /**
//     * @param secretAnswer The secret_answer
//     */
//    public void setSecretAnswer(String secretAnswer) {
//        this.secretAnswer = secretAnswer;
//    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    public OnValueChanged Currencylistener;
    public OnValueChanged FirstNamelistener;

    public void getCurrencyIdWithListener(OnValueChanged listener) {
        this.Currencylistener = listener;
    }

    public void setCurrencyIdWithListener(String currancyId) {
        this.currancyId = currancyId;
        Currencylistener.newValue(this.currancyId);
    }

    public void getFirstNameWithListener(OnValueChanged listener) {
        this.FirstNamelistener = listener;
    }

    public void setFirstNameWithListener(String firstName) {
        this.firstName = firstName;
        FirstNamelistener.newValue(this.firstName);
    }


    public interface OnValueChanged {
        void newValue(String text);
    }


}
