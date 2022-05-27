package com.mpark.androiddemoapp.qrcode.java;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.mpark.androiddemoapp.databinding.ActivityQrcodeBinding;

import java.util.Objects;

public class QRCodeActivity extends AppCompatActivity {

    private ActivityQrcodeBinding binding;
    private QRCodeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQrcodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupView();
        observeUiState();
    }

    private void setupView() {
        binding.encryptBtn.setOnClickListener(v -> runEncryption());
        binding.decryptBtn.setOnClickListener(v -> runDecryption());
    }

    private void observeUiState() {
        viewModel = new ViewModelProvider(this).get(QRCodeViewModel.class);
        getLifecycle().addObserver(viewModel);
        viewModel.getUiState().observe(this, this::updateUi);
    }

    private void updateUi(QRCodeUiState state) {
        bindLoading(state.getLoadingVisibility());
        bindError(state.getError());
        bindEncryptedData(state);
    }

    private void bindLoading(int visibility) {
        binding.progressBar.setVisibility(visibility);
    }

    private void bindError(Throwable error) {
        if (error == null) return;
        binding.encryptTxt.setText(error.getLocalizedMessage());
    }

    private void bindEncryptedData(QRCodeUiState state) {
        binding.decryptTxt.setText(state.getDecrypted().text());
        bindQrCode(state.getQrCodeData());
    }

    private void bindQrCode(QRCodeData qrCode) {
        if (qrCode == null) return;
        binding.encryptTxt.setText(qrCode.getBase64EncryptedStr());
        binding.qrcodeImg.setImageBitmap(qrCode.bitmap());
    }

    private void runEncryption() {
        viewModel.encrypt(getMessage());
    }

    @NonNull
    private String getMessage() {
        return Objects.requireNonNull(binding.messageEdt.getText()).toString();
    }

    private void runDecryption() {
        viewModel.decrypt();
    }
}