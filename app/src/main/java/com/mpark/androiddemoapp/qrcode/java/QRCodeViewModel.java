package com.mpark.androiddemoapp.qrcode.java;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class QRCodeViewModel extends ViewModel implements QRCodeRepository.Listener, DefaultLifecycleObserver {

    private final QRCodeRepository repository = new QRCodeRepository();
    private final QRCodeUiStateHolder stateHolder = new QRCodeUiStateHolder();

    private LifecycleOwner owner;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (owner != null) owner.getLifecycle().removeObserver(this);
    }

    public LiveData<QRCodeUiState> getUiState() {
        return stateHolder.getUiState();
    }

    public void encrypt(String message) {
        repository.encrypt(message);
    }

    public void decrypt() {
        repository.decrypt(stateHolder.getEncryptedBytes());
    }

    @Override
    public void onQRCodeGenerated(QRCodeData qrCodeData) {
        stateHolder.postQRCode(qrCodeData);
    }

//    @Override
//    public void onEncrypted(String message, NtruByteArray encrypted) {
//        stateHolder.postEncrypted(message, encrypted);
//        generateQrCode(message, encrypted);
//    }

//    private void generateQrCode(String message, NtruByteArray encrypted) {
//        repository.generateQrCode(encrypted.base64Encoded());
//    }

    @Override
    public void onDecrypted(NtruByteArray decrypted) {
        stateHolder.postDecrypted(decrypted);
    }

    @Override
    public void onFailure(Throwable e) {
        stateHolder.postError(e);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        this.owner = owner;
        repository.setListener(this);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        repository.setListener(null);
    }

}
