package com.mpark.androiddemoapp.qrcode.java;

import android.graphics.Bitmap;
import android.util.Base64;

import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeData {
    private final BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    private final String base64EncryptedStr;
    private final BitMatrix bitMatrix;

    public QRCodeData(String base64EncryptedStr, BitMatrix bitMatrix) {
        this.base64EncryptedStr = base64EncryptedStr;
        this.bitMatrix = bitMatrix;
    }

    public Bitmap bitmap() {
        return barcodeEncoder.createBitmap(bitMatrix);
    }

    public String getBase64EncryptedStr() {
        return base64EncryptedStr;
    }

    public byte[] getEncryptedBytes() {
        return Base64.decode(base64EncryptedStr, Base64.DEFAULT);
    }
}
