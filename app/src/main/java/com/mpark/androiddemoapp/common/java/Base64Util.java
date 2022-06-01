package com.mpark.androiddemoapp.common.java;

import android.util.Base64;

public class Base64Util {

    public static String encode(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static byte[] decode(String encodeString) {
        return Base64.decode(encodeString, Base64.DEFAULT);
    }
}
