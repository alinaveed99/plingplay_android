package ss.plingpay;

import java.util.Scanner;

/**
 * Created by Sammie on 6/12/2017.
 */

public class TestValues {
    private String senderDefaultCurrency;
    private String receiverDefaultCurrency;
    private String enteredAmount;
    private String selectedCurrency;

    private String sekToEur;
    private String euroToBtc;
    private String euroToBtcAfterFormula;
    private String btcToVndAmount;
    private String btcToVndExchange;
    private String withdrawalAmount;


    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(String withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }


    public String getBtcToVndAmount() {
        return btcToVndAmount;
    }

    public void setBtcToVndAmount(String btcToVndAmount) {
        this.btcToVndAmount = btcToVndAmount;


    }

    public String getBtcToVndExchange() {
        return btcToVndExchange;
    }

    public void setBtcToVndExchange(String btcToVndExchange) {
        this.btcToVndExchange = btcToVndExchange;
    }

    public String getSekToEur() {
        return sekToEur;
    }

    public void setSekToEur(String sekToEur) {
        this.sekToEur = sekToEur;
    }

    public String getSenderDefaultCurrency() {
        return senderDefaultCurrency;
    }

    public void setSenderDefaultCurrency(String senderDefaultCurrency) {
        this.senderDefaultCurrency = senderDefaultCurrency;
    }

    public String getReceiverDefaultCurrency() {
        return receiverDefaultCurrency;
    }

    public void setReceiverDefaultCurrency(String receiverDefaultCurrency) {
        this.receiverDefaultCurrency = receiverDefaultCurrency;
    }

    public String getEnteredAmount() {
        return enteredAmount;
    }

    public void setEnteredAmount(String enteredAmount) {
        this.enteredAmount = enteredAmount;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public String getEuroToBtc() {
        return euroToBtc;
    }

    public void setEuroToBtc(String euroToBtc) {
        this.euroToBtc = euroToBtc;
    }

    public String getEuroToBtcAfterFormula() {
        return euroToBtcAfterFormula;
    }

    public void setEuroToBtcAfterFormula(String euroToBtcAfterFormula) {
        this.euroToBtcAfterFormula = euroToBtcAfterFormula;
    }
}
