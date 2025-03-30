package com.example.aplikasinim;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText etNama, etNim;
    private TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi DBHelper
        dbHelper = new DBHelper(this);

        // Hubungkan komponen UI
        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        tvHasil = findViewById(R.id.tvHasil);

        // Tombol Submit (Create)
        btnSubmit.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String nim = etNim.getText().toString().trim();

            if (validasiInput(nama, nim)) {
                if (dbHelper.insertData(nama, nim)) {
                    Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                    tampilkanData();
                } else {
                    Toast.makeText(this, "NIM sudah terdaftar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Tombol Update
        btnUpdate.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String nim = etNim.getText().toString().trim();

            if (validasiInput(nama, nim)) {
                if (dbHelper.updateData(nama, nim)) {
                    Toast.makeText(this, "Data diperbarui", Toast.LENGTH_SHORT).show();
                    tampilkanData();
                } else {
                    Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Tombol Delete
        btnDelete.setOnClickListener(v -> {
            String nim = etNim.getText().toString().trim();

            if (!nim.isEmpty()) {
                if (dbHelper.deleteData(nim)) {
                    Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show();
                    tampilkanData();
                } else {
                    Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Masukkan NIM", Toast.LENGTH_SHORT).show();
            }
        });

        tampilkanData();
    }

    private boolean validasiInput(String nama, String nim) {
        if (nama.isEmpty() || nim.isEmpty()) {
            Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void tampilkanData() {
        StringBuilder data = new StringBuilder();
        Cursor cursor = dbHelper.getAllData();

        if (cursor.getCount() == 0) {
            data.append("Belum ada data");
        } else {
            while (cursor.moveToNext()) {
                data.append("ID: ").append(cursor.getString(0))
                        .append("\nNama: ").append(cursor.getString(1))
                        .append("\nNIM: ").append(cursor.getString(2))
                        .append("\n\n");
            }
        }
        cursor.close();
        tvHasil.setText(data.toString());
    }
}