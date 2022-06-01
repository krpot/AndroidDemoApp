package com.mpark.androiddemoapp.qrcode.java;

import androidx.lifecycle.MutableLiveData;

public class QRCodeUiStateHolder {
    private QRCodeUiState state = new QRCodeUiState();
    private final MutableLiveData<QRCodeUiState> uiState = new MutableLiveData<>(state);

    public MutableLiveData<QRCodeUiState> getUiState() {
        return uiState;
    }

    public void postError(Throwable e) {
        state = state
                .setLoading(false)
                .setError(e);
        uiState.postValue(state);
    }

    public byte[] getEncryptedBytes() {
        //state.getEncrypted().getData();
        return state.getQrCodeData().getEncryptedBytes();
    }

    public void postQRCode(QRCodeData qrCode) {
        state = state
                .setLoading(false)
                .setError(null)
                .setQrCodeData(qrCode);
        uiState.postValue(state);
    }

    public void postDecrypted(NtruByteArray decrypted) {
        state = state
                .setLoading(false)
                .setError(null)
                .setDecrypted(decrypted);
        uiState.postValue(state);
    }
}
