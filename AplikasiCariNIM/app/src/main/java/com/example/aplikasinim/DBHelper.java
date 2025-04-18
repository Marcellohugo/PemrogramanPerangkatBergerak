package com.example.aplikasinim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mahasiswa.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "mahasiswa";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_NIM = "nim";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA + " TEXT, " +
                COLUMN_NIM + " TEXT UNIQUE)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CREATE
    public boolean insertData(String nama, String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_NIM, nim);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Cek apakah NIM sudah ada (selain milik ID tertentu)
    public boolean isNimExists(String nim, int excludeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NAME + " WHERE nim = ? AND id != ?", new String[]{nim, String.valueOf(excludeId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    // Cek apakah nama sudah ada (selain milik ID tertentu)
    public boolean isNamaExists(String nama, int excludeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NAME + " WHERE nama = ? AND id != ?", new String[]{nama, String.valueOf(excludeId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    // READ ALL
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, nama, nim FROM mahasiswa", null);
    }

    // READ BY ID
    public Cursor getDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    // SEARCH
    public Cursor searchData(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, nama, nim FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ?",
                new String[]{"%" + query + "%", "%" + query + "%"});
    }

    // UPDATE
    public boolean updateData(int id, String nama, String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMA, nama);
        values.put(COLUMN_NIM, nim);
        int result = db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        return result > 0;
    }

    // DELETE
    public boolean deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(
                TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        return result > 0;
    }
}