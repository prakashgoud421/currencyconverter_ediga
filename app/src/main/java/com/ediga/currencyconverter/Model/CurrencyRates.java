
package com.ediga.currencyconverter.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyRates {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("terms")
    @Expose
    private String terms;
    @SerializedName("privacy")
    @Expose
    private String privacy;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("quotes")
    @Expose
    private String quotes;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

}
