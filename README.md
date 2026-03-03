# Pemrograman Perangkat Bergerak

Kumpulan tugas dan proyek mata kuliah **Pemrograman Perangkat Bergerak** (Mobile Programming) — pengembangan aplikasi Android.

## Deskripsi

Repository ini berisi aplikasi Android yang dikembangkan selama perkuliahan, termasuk proyek utama **AplikasiCariNIM** — aplikasi pencarian dan manajemen data mahasiswa.

## AplikasiCariNIM

Aplikasi Android untuk mencari dan mengelola data mahasiswa berdasarkan NIM (Nomor Induk Mahasiswa).

### Fitur
- **Halaman Awal** — Landing page aplikasi
- **Daftar Mahasiswa** — Menampilkan seluruh data mahasiswa
- **Tambah Mahasiswa** — Form input data mahasiswa baru
- **Cari dari API** — Pencarian data mahasiswa melalui API
- **Buka Kamera** — Integrasi kamera perangkat
- **Buka Web Dokumen** — Akses dokumen melalui WebView
- **SQLite Database** — Penyimpanan data lokal menggunakan DBHelper

### Struktur Proyek

```
AplikasiCariNIM/
└── app/src/main/java/com/example/aplikasinim/
    ├── HalamanAwal.java         # Activity halaman utama
    ├── DaftarMahasiswa.java     # Activity daftar mahasiswa
    ├── TambahMahasiswa.java     # Activity form tambah mahasiswa
    ├── BukaDariAPI.java         # Activity pencarian via API
    ├── BukaCamera.java          # Activity kamera
    ├── BukaWebDokumen.java      # Activity WebView
    └── DBHelper.java            # SQLite database helper
```

## Tech Stack

| Teknologi | Keterangan |
|-----------|------------|
| Java & Kotlin | Bahasa pemrograman |
| Android SDK | Framework mobile |
| SQLite | Database lokal |
| Gradle | Build system |

## Cara Menjalankan

1. Buka proyek menggunakan **Android Studio**
2. Sync Gradle dependencies
3. Jalankan pada emulator atau perangkat fisik (min. API 24)

## Penulis

**Marco Marcello Hugo** — 5025221102  
Teknik Informatika, Institut Teknologi Sepuluh Nopember
