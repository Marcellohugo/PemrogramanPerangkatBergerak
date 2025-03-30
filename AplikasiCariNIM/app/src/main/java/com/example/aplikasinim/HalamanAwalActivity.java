package com.example.aplikasinim;

import android.content.Intent; // <-- Import Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HalamanAwalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_awal);

        Button btnToast = findViewById(R.id.btnToast);
        btnToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tampilkan Toast
                Toast.makeText(HalamanAwalActivity.this, "Halo Senopati!", Toast.LENGTH_SHORT).show();

                // Pindah ke MainActivity setelah Toast
                startActivity(new Intent(HalamanAwalActivity.this, MainActivity.class));
            }
        });
    }
}