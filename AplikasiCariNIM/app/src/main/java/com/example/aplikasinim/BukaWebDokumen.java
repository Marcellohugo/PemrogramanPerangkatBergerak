package com.example.aplikasinim;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class BukaWebDokumen extends AppCompatActivity {

    Button btnWeb, btnDokumen, btnAplikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buka_web_dokumen);

        btnWeb = findViewById(R.id.btnWeb);
        btnDokumen = findViewById(R.id.btnDokumen);
        btnAplikasi = findViewById(R.id.btnAplikasi);

        // Buka Web
        btnWeb.setOnClickListener(v -> {
            String url = "https://portal.its.ac.id/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        // Buka Dokumen PDF
        btnDokumen.setOnClickListener(v -> {
            File file = new File(getExternalFilesDir(null), "Module-Handbook-Bachelor-of-Informatics-Program-ITS.pdf");
            Uri uri = FileProvider.getUriForFile(
                    BukaWebDokumen.this,
                    getPackageName() + ".provider",
                    file
            );
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });

        // Buka Aplikasi Lain (YouTube)
        btnAplikasi.setOnClickListener(v -> {
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage("com.google.android.youtube");
            if (intent != null) {
                startActivity(intent);
            } else {
                // YouTube tidak ditemukan
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://youtube.com"));
                startActivity(browserIntent);
            }
        });
    }
}
