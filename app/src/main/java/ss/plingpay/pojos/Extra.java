package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import io.realm.RealmObject;
import ss.plingpay.pojos.Users.UserDetails;

/**
 * Created by samar_000 on 6/14/2016.
 */
public class Extra {

    @Generated("org.jsonschema2pojo")
    public class BlockChainAccess {

        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("error")
        @Expose
        private String error;

        /**
         * @return The guid
         */
        public String getGuid() {
            return guid;
        }

        /**
         * @param guid The guid
         */
        public void setGuid(String guid) {
            this.guid = guid;
        }

        /**
         * @return The success
         */
        public Boolean getSuccess() {
            return success;
        }

        /**
         * @param success The success
         */
        public void setSuccess(Boolean success) {
            this.success = success;
        }

        /**
         * @return The error
         */
        public String getError() {
            return error;
        }

        /**
         * @param error The error
         */
        public void setError(String error) {
            this.error = error;
        }

    }

    @Generated("org.jsonschema2pojo")
    public class Balance {

        @SerializedName("balance")
        @Expose
        private Integer balance;
        @SerializedName("error")
        @Expose
        private String error;

        /**
         * @return The balance
         */
        public Integer getBalance() {
            return balance;
        }

        /**
         * @param balance The balance
         */
        public void setBalance(Integer balance) {
            this.balance = balance;
        }

        /**
         * @return The error
         */
        public String getError() {
            return error;
        }

        /**
         * @param error The error
         */
        public void setError(String error) {
            this.error = error;
        }

    }

    @Generated("org.jsonschema2pojo")
    public class Transaction {

        @SerializedName("error")
        @Expose
        private String error;

        /**
         * @return The error
         */
        public String getError() {
            return error;
        }

        /**
         * @param error The error
         */
        public void setError(String error) {
            this.error = error;
        }

    }


    @Generated("org.jsonschema2pojo")
    public class CreateNewWallet  {

        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("label")
        @Expose
        private String label;
        @SerializedName("error")
        @Expose
        private String error;


        /**
         * @return The guid
         */
        public String getError() {
            return error;
        }

        /**
         * @param guid The guid
         */
        public void setError(String error) {
            this.error = error;
        }

        /**
         * @return The guid
         */
        public String getGuid() {
            return guid;
        }

        /**
         * @param guid The guid
         */
        public void setGuid(String guid) {
            this.guid = guid;
        }

        /**
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return The label
         */
        public String getLabel() {
            return label;
        }

        /**
         * @param label The label
         */
        public void setLabel(String label) {
            this.label = label;
        }

    }


    @Generated("org.jsonschema2pojo")
    public class withDrawalPojo  {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("description")
        @Expose
        private String description;

        public withDrawalPojo() {

        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }





}
