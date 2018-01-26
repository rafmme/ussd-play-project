package com.farayolatimileyin.banksussdcode;

/**
 * Created by FARAYOLA-FBTS on 25/01/2018.
 */

public class BanksData {
    private String bankName;
    private String bankIcon;
    private String bankUssdCode;

    public BanksData(String bankName,String bankIcon, String bankUssdCode){
        this.bankIcon = bankIcon;
        this.bankName = bankName;
        this.bankUssdCode = bankUssdCode;
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankUssdCode() {
        return bankUssdCode;
    }

    @Override
    public String toString() {
        return bankIcon+":"+bankName+":"+bankUssdCode;
    }
}
