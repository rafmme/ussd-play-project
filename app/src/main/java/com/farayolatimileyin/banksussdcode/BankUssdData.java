package com.farayolatimileyin.banksussdcode;

/**
 * Created by FARAYOLA-FBTS on 27/01/2018.
 */

public class BankUssdData {

    private String bankName;
    private String actionName;
    private String ussdCode;

    public BankUssdData(String bankName, String actionName, String ussdCode) {
        this.actionName = actionName;
        this.ussdCode = ussdCode;
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public String getActionName() {
        return actionName;
    }

    public String getUssdCode() {
        return ussdCode;
    }
}
