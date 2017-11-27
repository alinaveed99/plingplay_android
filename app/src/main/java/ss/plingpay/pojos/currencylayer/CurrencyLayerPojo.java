
package ss.plingpay.pojos.currencylayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyLayerPojo {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("privacy")
    @Expose
    private String privacy;
//    @SerializedName("query")
//    @Expose
//    private Query query;
//    @SerializedName("info")
//    @Expose
//    private Info info;

    @SerializedName("result")
    @Expose
    private Double result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
//
//    public String getTerms() {
//        return terms;
//    }
//
//    public void setTerms(String terms) {
//        this.terms = terms;
//    }
//
//    public String getPrivacy() {
//        return privacy;
//    }
//
//    public void setPrivacy(String privacy) {
//        this.privacy = privacy;
//    }
//
//    public Query getQuery() {
//        return query;
//    }
//
//    public void setQuery(Query query) {
//        this.query = query;
//    }
//
//    public Info getInfo() {
//        return info;
//    }
//
//    public void setInfo(Info info) {
//        this.info = info;
//    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

}
