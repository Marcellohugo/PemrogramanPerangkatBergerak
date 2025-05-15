package com.example.aplikasinim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HalamanAwal extends AppCompatActivity {

    Button btnNIM, btnBukaWebDokumen, btnbukaDariAPI, btnBukaCamera ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halaman_awal);

        btnNIM = findViewById(R.id.btnNIM);
        btnBukaWebDokumen = findViewById(R.id.btnBukaWebDokumen);
        btnbukaDariAPI = findViewById(R.id.btnBukaDariAPI);
        btnBukaCamera = findViewById(R.id.btnBukaCamera);

        // Buka Aplikasi NIM
        btnNIM.setOnClickListener(v -> {
            startActivity(new Intent(HalamanAwal.this, DaftarMahasiswa.class));
        });

        // Buka Aplikasi NIM
        btnBukaWebDokumen.setOnClickListener(v -> {
            startActivity(new Intent(HalamanAwal.this, BukaWebDokumen.class));
        });

        // Buka Aplikasi NIM
        btnbukaDariAPI.setOnClickListener(v -> {
            startActivity(new Intent(HalamanAwal.this, BukaDariAPI.class));
        });

        // Buka Aplikasi NIM
        btnBukaCamera.setOnClickListener(v -> {
            startActivity(new Intent(HalamanAwal.this, BukaCamera.class));
        });

    }
}
