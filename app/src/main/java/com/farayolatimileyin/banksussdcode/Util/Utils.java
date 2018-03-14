package com.farayolatimileyin.banksussdcode.Util;

public class Utils {
    public static String formatAccountNumber(String acctNum) {
        if (!acctNum.contains("-") && !acctNum.contains(" ") && !acctNum.contains("(") && !acctNum.contains(")")) {
            return acctNum;
        } else {
            String newAcctNum = "";
            String[] s = acctNum.split("");
            for (String i : s) {
                if ((!i.equals("-")) && (!i.equals(" ")) && (!i.equals("(")) && (!i.equals(")"))) {
                    newAcctNum += i;
                }
            }
            return newAcctNum;
        }
    }

    public static String formatPhoneNumber(String rawNumber){
        String cPN = "";
        if(rawNumber.startsWith("+234")) {
            cPN += "0";
            String[] numArray = rawNumber.substring(4,rawNumber.length()).split("");
            for(String i : numArray){
                if((!i.equals("-"))&& (!i.equals(" "))){
                    cPN += i;
                }
            }

        }

        else{
            if (rawNumber.startsWith("0")) {
                String[] numArray = rawNumber.split("");
                for (String i : numArray) {
                    if ((!i.equals("-")) && (!i.equals(" "))) {
                        cPN += i;
                    }
                }
            }

            else{
                cPN = rawNumber;
            }
        }
        return cPN;
    }

    public static String cleanAmountString(String amountWithNairaSign){
        String b = "";
        if (amountWithNairaSign.contains("₦")){
            String[] a = amountWithNairaSign.split(" ");

            for(String i : a[1].split(",")){
                b += i;
            }
            return b;
        }
        else {
            for(String i : amountWithNairaSign.split(",")){
                b += i;
            }
            return b;
        }

    }

    public static String removeNameFromContact(String contactWithName) {
        if (contactWithName.contains(":")) {
            String[] h = contactWithName.split("[:]");
            return h[1];
        } else {
            return contactWithName;
        }
    }
}
