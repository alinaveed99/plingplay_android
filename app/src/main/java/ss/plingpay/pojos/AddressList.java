
package ss.plingpay.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AddressList {

    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = new ArrayList<Address>();

    /**
     * 
     * @return
     *     The addresses
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * 
     * @param addresses
     *     The addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
