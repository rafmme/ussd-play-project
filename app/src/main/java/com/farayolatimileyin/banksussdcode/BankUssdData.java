package com.farayolatimileyin.banksussdcode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FARAYOLA-FBTS on 27/01/2018.
 */

public class BankUssdData implements Parcelable {

    private String bankName;
    private String actionName;
    private String ussdCode;

    public BankUssdData(String bankName, String actionName, String ussdCode) {
        this.actionName = actionName;
        this.ussdCode = ussdCode;
        this.bankName = bankName;
    }

    public BankUssdData(Parcel source){
        bankName = source.readString();
        actionName = source.readString();
        ussdCode = source.readString();
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



    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bankName);
        parcel.writeString(actionName);
        parcel.writeString(ussdCode);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public BankUssdData createFromParcel(Parcel in){
            return new BankUssdData(in);
        }
        public BankUssdData[] newArray(int size){
            return new BankUssdData[size];
        }
    };
}
