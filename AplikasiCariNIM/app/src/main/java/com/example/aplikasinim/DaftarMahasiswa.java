package com.example.aplikasinim;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

public class DaftarMahasiswa extends AppCompatActivity {
    private ListView listView;
    private SearchView searchView;
    private Button btnTambah;
    private DBHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_mahasiswa);
        listView = findViewById(R.id.listViewData);
        searchView = findViewById(R.id.searchView);
        btnTambah = findViewById(R.id.btnToast);
        dbHelper = new DBHelper(this);

        btnTambah.setOnClickListener(v -> {
            startActivity(new Intent(DaftarMahasiswa.this, TambahMahasiswa.class));
        });

        refreshData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (cursor != null && cursor.moveToPosition(position)) {
                int idIndex = cursor.getColumnIndex("_id");
                int namaIndex = cursor.getColumnIndex("nama");
                int nimIndex = cursor.getColumnIndex("nim");

                if (idIndex >= 0 && namaIndex >= 0 && nimIndex >= 0) {
                    int selectedId = cursor.getInt(idIndex);
                    String namaLama = cursor.getString(namaIndex);
                    String nimLama = cursor.getString(nimIndex);
                    showEditDialog(selectedId, namaLama, nimLama);
                } else {
                    Toast.makeText(this, "Data tidak lengkap", Toast.LENGTH_SHORT).show();
                }
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    private void showEditDialog(int id, String oldNama, String oldNim) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_input, null);
        EditText editNama = dialogView.findViewById(R.id.etNama);
        EditText editNim = dialogView.findViewById(R.id.etNim);

        editNama.setText(oldNama);
        editNim.setText(oldNim);

        new AlertDialog.Builder(this)
                .setTitle("Edit Data Mahasiswa")
                .setView(dialogView)
                .setPositiveButton("Simpan", (dialog, which) -> {
                    String newNama = editNama.getText().toString().trim();
                    String newNim = editNim.getText().toString().trim();

                    if (newNama.isEmpty() || newNim.isEmpty()) {
                        Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newNama.equals(oldNama) && dbHelper.isNamaExists(newNama, id)) {
                        Toast.makeText(this, "Nama sudah digunakan", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newNim.equals(oldNim) && dbHelper.isNimExists(newNim, id)) {
                        Toast.makeText(this, "NIM sudah digunakan", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean updated = dbHelper.updateData(id, newNama, newNim);
                    if (updated) {
                        Toast.makeText(this, "Data diperbarui", Toast.LENGTH_SHORT).show();
                        refreshData();
                    } else {
                        Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", null)
                .setNeutralButton("Hapus", (dialog, which) -> {
                    new AlertDialog.Builder(this)
                            .setTitle("Konfirmasi Hapus")
                            .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                            .setPositiveButton("Ya", (d, w) -> {
                                boolean deleted = dbHelper.deleteData(id);
                                if (deleted) {
                                    Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show();
                                    refreshData();
                                } else {
                                    Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Batal", null)
                            .show();
                })
                .show();
    }


    private void refreshData() {
        cursor = dbHelper.getAllData();
        if (cursor != null) {
            adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{"nama", "nim"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0
            );
            listView.setAdapter(adapter);
        }
    }

    private void filterData(String query) {
        Cursor filteredCursor = dbHelper.searchData(query);
        if (filteredCursor != null) {
            adapter.changeCursor(filteredCursor);
            cursor = filteredCursor;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}