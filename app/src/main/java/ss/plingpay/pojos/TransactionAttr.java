package ss.plingpay.pojos;

/**
 * Created by samar_000 on 8/29/2016.
 */

public class TransactionAttr {

    private String guid;
    private String password;
    private String second_password;
    private String to;
    private String amount;
    private String from;
    private String note;

    private String recId;
    private String SenderId;
    private String type;
    private String currencyId;
    private String request;
    private String amountInBtc;
    private String amountInBtcRemaining;

    private String SenderAddress;
    private String RecAddress;

    private String amountInBtcCompany;
    private String hash;
    private String amountInSatoshi;

    private String senderExchange;
    private String receiverExchange;


    public TransactionAttr(String guid, String password, String second_password, String to, String amount,
                           String from, String note, String recId, String senderId, String type, String currencyId,
                           String request, String amountInBtc, String amountInBtcRemaining, String senderAddress,
                           String recAddress, String amountInBtcCompany, String hash, String amountInSatoshi, String senderExchange, String receiverExchange) {
        this.guid = guid;
        this.password = password;
        this.second_password = second_password;
        this.to = to;
        this.amount = amount;
        this.from = from;
        this.note = note;
        this.recId = recId;
        SenderId = senderId;
        this.type = type;
        this.currencyId = currencyId;
        this.request = request;
        this.amountInBtc = amountInBtc;
        this.amountInBtcRemaining = amountInBtcRemaining;
        SenderAddress = senderAddress;
        RecAddress = recAddress;
        this.amountInBtcCompany = amountInBtcCompany;
        this.hash = hash;
        this.amountInSatoshi = amountInSatoshi;
        this.senderExchange = senderExchange;
        this.receiverExchange = receiverExchange;
    }

    public TransactionAttr() {

    }


    public String getSenderExchange() {
        return senderExchange;
    }

    public void setSenderExchange(String senderExchange) {
        this.senderExchange = senderExchange;
    }

    public String getReceiverExchange() {
        return receiverExchange;
    }

    public void setReceiverExchange(String receiverExchange) {
        this.receiverExchange = receiverExchange;
    }

    public String getSenderAddress() {
        return SenderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        SenderAddress = senderAddress;
    }

    public String getRecAddress() {
        return RecAddress;
    }

    public void setRecAddress(String recAddress) {
        RecAddress = recAddress;
    }

    public String getAmountInSatoshi() {
        return amountInSatoshi;
    }

    public void setAmountInSatoshi(String amountInSatoshi) {
        this.amountInSatoshi = amountInSatoshi;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecond_password() {
        return second_password;
    }

    public void setSecond_password(String second_password) {
        this.second_password = second_password;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getAmountInBtc() {
        return amountInBtc;
    }

    public void setAmountInBtc(String amountInBtc) {
        this.amountInBtc = amountInBtc;
    }

    public String getAmountInBtcRemaining() {
        return amountInBtcRemaining;
    }

    public void setAmountInBtcRemaining(String amountInBtcRemaining) {
        this.amountInBtcRemaining = amountInBtcRemaining;
    }

    public String getAmountInBtcCompany() {
        return amountInBtcCompany;
    }

    public void setAmountInBtcCompany(String amountInBtcCompany) {
        this.amountInBtcCompany = amountInBtcCompany;
    }
}
