package com.mpark.androiddemoapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mpark.androiddemoapp.databinding.ActivityMainBinding
import com.mpark.androiddemoapp.qrcode.java.QRCodeActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            qrcodeView.setOnClickListener { showQRCodeScreen() }
        }
    }

    private fun showQRCodeScreen() {
        QRCodeActivity.show(this);
    }
}