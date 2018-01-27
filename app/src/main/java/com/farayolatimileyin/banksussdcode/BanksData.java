package com.farayolatimileyin.banksussdcode;

/**
 * Created by FARAYOLA-FBTS on 25/01/2018.
 */

public class BanksData {
    private String bankName;
    private String bankIcon;

    public BanksData(String bankName,String bankIcon){
        this.bankIcon = bankIcon;
        this.bankName = bankName;
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public String getBankName() {
        return bankName;
    }


    @Override
    public String toString() {
        return bankIcon;
    }
}
