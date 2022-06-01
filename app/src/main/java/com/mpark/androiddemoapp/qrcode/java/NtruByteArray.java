package com.mpark.androiddemoapp.qrcode.java;

import android.util.Base64;

public class NtruByteArray {
    private final byte[] data;

    public NtruByteArray(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public String text() {
        return new String(data);
    }

    public String base64Encoded() {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}
