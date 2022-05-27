package com.mpark.androiddemoapp.qrcode.java;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.mpark.androiddemoapp.common.java.AppExecutors;

import net.sf.ntru.encrypt.EncryptionKeyPair;
import net.sf.ntru.encrypt.EncryptionParameters;
import net.sf.ntru.encrypt.NtruEncrypt;
import net.sf.ntru.exception.NtruException;
import net.sf.ntru.sign.NtruSign;
import net.sf.ntru.sign.SignatureKeyPair;
import net.sf.ntru.sign.SignatureParameters;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

public class QRCodeRepository {
    interface Listener {
        void onQRCodeGenerated(QRCodeData qrCodeData);

        void onDecrypted(NtruByteArray decrypted);

        void onFailure(Throwable e);
    }

    private final NtruEncrypt ntruEncrypt = new NtruEncrypt(EncryptionParameters.APR2011_439_FAST);
    private final NtruSign ntruSign = new NtruSign(SignatureParameters.APR2011_439_PROD);
    private final MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    private EncryptionKeyPair encKeyPair;
    private SignatureKeyPair signKeyPair;

    private final Executor diskIO = new AppExecutors().diskIO();

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void encrypt(@NotNull String message) {
        diskIO.execute(() -> {
            try {
                encKeyPair = generateKeyPair();
                byte[] encrypted = ntruEncrypt.encrypt(message.getBytes(StandardCharsets.UTF_8), encKeyPair.getPublic());
                String base64EncodeStr = new NtruByteArray(encrypted).base64Encoded();
                QRCodeData qrCode = generateQrCode(base64EncodeStr);
                if (listener != null) listener.onQRCodeGenerated(qrCode);
            } catch (NtruException | WriterException e) {
                if (listener != null) listener.onFailure(e);
            }
        });
    }

    private QRCodeData generateQrCode(String base64Str) throws WriterException {
        BitMatrix bitMatrix = multiFormatWriter.encode(base64Str, BarcodeFormat.QR_CODE, 500, 500);
        return new QRCodeData(base64Str, bitMatrix);
    }

    public void decrypt(byte[] encrypted) {
        diskIO.execute(() -> {
            try {
                byte[] decrypted = ntruEncrypt.decrypt(encrypted, encKeyPair);
                if (listener != null)
                    listener.onDecrypted(new NtruByteArray(decrypted));
            } catch (RuntimeException e) {
                if (listener != null) listener.onFailure(e);
            }
        });
    }

    private EncryptionKeyPair generateKeyPair() {
        if (encKeyPair != null) return encKeyPair;
        return ntruEncrypt.generateKeyPair();
    }
}
