package com.nlogax.banking.utils;

public class Utils {
    public static String capitalizeFirstLetter (String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }
}
