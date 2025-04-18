package com.example.aplikasinim;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class TambahMahasiswa extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText etNama, etNim;
    private int selectedId = -1;
    private String oldNama = "", oldNim = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_mahasiswa);

        dbHelper = new DBHelper(this);
        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        if (intent != null && "EDIT".equals(intent.getStringExtra("MODE"))) {
            selectedId = intent.getIntExtra("ID", -1);
            Cursor cursor = dbHelper.getDataById(selectedId);
            if (cursor != null && cursor.moveToFirst()) {
                int namaIndex = cursor.getColumnIndex("nama");
                int nimIndex = cursor.getColumnIndex("nim");

                if (namaIndex >= 0 && nimIndex >= 0) {
                    oldNama = cursor.getString(namaIndex);
                    oldNim = cursor.getString(nimIndex);

                    etNama.setText(oldNama);
                    etNim.setText(oldNim);
                } else {
                    Toast.makeText(this, "Kolom tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        }

        btnSubmit.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String nim = etNim.getText().toString().trim();
            if (validasiInput(nama, nim)) {
                // Cek apakah nama atau NIM sudah digunakan
                if (dbHelper.isNimExists(nim, -1)) {
                    Toast.makeText(this, "NIM sudah digunakan", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbHelper.isNamaExists(nama, -1)) {
                    Toast.makeText(this, "Nama sudah digunakan", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Simpan data kalau valid dan unik
                if (dbHelper.insertData(nama, nim)) {
                    Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validasiInput(String nama, String nim) {
        if (nama.isEmpty() || nim.isEmpty()) {
            Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}