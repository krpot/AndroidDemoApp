package com.mpark.androiddemoapp.qrcode.java;

import android.view.View;

public class QRCodeUiState {
    private boolean isLoading;
    private Throwable error;
    private NtruByteArray encrypted = new NtruByteArray(new byte[]{});
    private NtruByteArray decrypted = new NtruByteArray(new byte[]{});
    private QRCodeData qrCodeData;

    public boolean isLoading() {
        return isLoading;
    }

    public QRCodeUiState setLoading(boolean loading) {
        isLoading = loading;
        return this;
    }

    public int getLoadingVisibility() {
        return isLoading ? View.VISIBLE : View.GONE;
    }

    public Throwable getError() {
        return error;
    }

    public QRCodeUiState setError(Throwable e) {
        this.error = e;
        return this;
    }

    public NtruByteArray getDecrypted() {
        return decrypted;
    }

    public QRCodeUiState setDecrypted(NtruByteArray decrypted) {
        this.decrypted = decrypted;
        return this;
    }

    public QRCodeData getQrCodeData() {
        return qrCodeData;
    }

    public QRCodeUiState setQrCodeData(QRCodeData qrCodeData) {
        this.qrCodeData = qrCodeData;
        return this;
    }
}
