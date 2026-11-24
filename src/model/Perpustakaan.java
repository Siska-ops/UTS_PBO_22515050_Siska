package model;

import adt.PeminjamanSortedList;
import java.util.ArrayList;

/**
 * Kelas pengelola utama perpustakaan mini yang mengoordinasikan
 * data buku, anggota, dan riwayat peminjaman (ADT Sorted List).
 */
public class Perpustakaan {
    private String namaPerpustakaan;
    private String alamat;
    private ArrayList<Buku> daftarBuku;
    private ArrayList<Anggota> daftarAnggota;
    private PeminjamanSortedList riwayatPeminjaman;
    private boolean buka;

    public Perpustakaan() {
        this.namaPerpustakaan = "Perpustakaan Mini";
        this.alamat = "Kendari";
        this.daftarBuku = new ArrayList<>();
        this.daftarAnggota = new ArrayList<>();
        this.riwayatPeminjaman = new PeminjamanSortedList(10);
        this.buka = true;
    }

    public Perpustakaan(String namaPerpustakaan, String alamat, int kapasitasPeminjaman) {
        setNamaPerpustakaan(namaPerpustakaan);
        setAlamat(alamat);
        this.daftarBuku = new ArrayList<>();
        this.daftarAnggota = new ArrayList<>();
        this.riwayatPeminjaman = new PeminjamanSortedList(kapasitasPeminjaman);
        this.buka = true;
    }

    public String getNamaPerpustakaan() {
        return namaPerpustakaan;
    }

    public void setNamaPerpustakaan(String namaPerpustakaan) {
        if (namaPerpustakaan == null || namaPerpustakaan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama perpustakaan tidak boleh kosong.");
        }
        this.namaPerpustakaan = namaPerpustakaan.trim();
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat perpustakaan tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    public boolean isBuka() {
        return buka;
    }

    public void setBuka(boolean buka) {
        this.buka = buka;
    }

    public PeminjamanSortedList getRiwayatPeminjaman() {
        return riwayatPeminjaman;
    }

    // Tambah buku baru ke koleksi perpustakaan
    public boolean tambahBuku(Buku buku) {
        if (buku == null || cariBukuByKode(buku.getKodeBuku()) != null) {
            return false;
        }
        daftarBuku.add(buku);
        return true;
    }

    // Tambah anggota baru ke perpustakaan
    public boolean tambahAnggota(Anggota anggota) {
        if (anggota == null || cariAnggotaById(anggota.getIdAnggota()) != null) {
            return false;
        }
        daftarAnggota.add(anggota);
        return true;
    }

    public Buku cariBukuByKode(String kode) {
        if (kode == null) return null;
        for (Buku b : daftarBuku) {
            if (b.getKodeBuku().equalsIgnoreCase(kode.trim())) {
                return b;
            }
        }
        return null;
    }

    public Anggota cariAnggotaById(String id) {
        if (id == null) return null;
        for (Anggota a : daftarAnggota) {
            if (a.getIdAnggota().equalsIgnoreCase(id.trim())) {
                return a;
            }
        }
        return null;
    }

    public void tampilkanSemuaBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println("Belum ada data buku.");
            return;
        }
        System.out.println("=== Daftar Buku ===");
        for (Buku b : daftarBuku) {
            b.tampilkanInfo();
        }
    }

    public void tampilkanSemuaAnggota() {
        if (daftarAnggota.isEmpty()) {
            System.out.println("Belum ada data anggota.");
            return;
        }
        System.out.println("=== Daftar Anggota ===");
        for (Anggota a : daftarAnggota) {
            a.tampilkanInfo();
        }
    }

    // Memproses transaksi peminjaman dengan memeriksa semua aturan bisnis
    public String prosesPinjam(String idPinjam, String kodeBuku, String idAnggota, int lamaHari) {
        if (!buka) {
            return "Gagal: Perpustakaan sedang tutup.";
        }
        if (riwayatPeminjaman.isFull()) {
            return "Gagal: Kapasitas penyimpanan peminjaman (ADT) sudah penuh.";
        }

        Buku buku = cariBukuByKode(kodeBuku);
        if (buku == null) {
            return "Gagal: Buku dengan kode " + kodeBuku + " tidak ditemukan.";
        }

        Anggota anggota = cariAnggotaById(idAnggota);
        if (anggota == null) {
            return "Gagal: Anggota dengan ID " + idAnggota + " tidak ditemukan.";
        }

        // Aturan wajib 1: maksimal 3 buku per anggota
        if (!anggota.bisaPinjamLagi()) {
            return "Gagal: Anggota " + anggota.getNama() + " sudah mencapai batas maksimal 3 pinjaman aktif.";
        }

        // Aturan wajib 2: stok buku tidak boleh negatif / harus tersedia
        if (buku.getStok() <= 0) {
            return "Gagal: Stok buku '" + buku.getJudul() + "' habis.";
        }

        try {
            Peminjaman transaksi = new Peminjaman(idPinjam, buku, anggota, lamaHari);
            buku.kurangiStok(1);
            anggota.tambahPinjamanAktif();
            boolean tersimpan = riwayatPeminjaman.insert(transaksi);
            if (!tersimpan) {
                // Rollback perubahan jika gagal masuk ke ADT
                buku.tambahStok(1);
                anggota.kurangiPinjamanAktif();
                return "Gagal: Data peminjaman tidak dapat disimpan ke ADT.";
            }
            return "Berhasil: Peminjaman " + transaksi.getIdPeminjaman() + " tercatat dan terurut.";
        } catch (IllegalArgumentException | IllegalStateException e) {
            return "Gagal: " + e.getMessage();
        }
    }

    public void tampilkanRingkasan() {
        int totalStok = 0;
        for (Buku b : daftarBuku) {
            totalStok += b.getStok();
        }

        int totalPinjamanAktif = 0;
        for (int i = 0; i < riwayatPeminjaman.size(); i++) {
            Peminjaman p = riwayatPeminjaman.get(i);
            if (!p.isSudahDikembalikan()) {
                totalPinjamanAktif++;
            }
        }

        System.out.println("=== Ringkasan " + namaPerpustakaan + " ===");
        System.out.println("Alamat              : " + alamat);
        System.out.println("Jumlah Judul Buku   : " + daftarBuku.size());
        System.out.println("Total Stok Buku     : " + totalStok);
        System.out.println("Jumlah Anggota      : " + daftarAnggota.size());
        System.out.println("Total Data di ADT   : " + riwayatPeminjaman.size() + "/" + riwayatPeminjaman.getKapasitas());
        System.out.println("Peminjaman Aktif    : " + totalPinjamanAktif);
    }
}
