package model;

/**
 * Merepresentasikan anggota perpustakaan yang dapat meminjam buku.
 */
public class Anggota {
    public static final int MAKS_PINJAMAN = 3;

    private String idAnggota;
    private String nama;
    private String prodi;
    private int jumlahPinjamanAktif;
    private boolean aktif;

    public Anggota() {
        this.idAnggota = "AGT000";
        this.nama = "Anggota Baru";
        this.prodi = "Sistem dan Teknologi Informasi";
        this.jumlahPinjamanAktif = 0;
        this.aktif = true;
    }

    public Anggota(String idAnggota, String nama, String prodi) {
        setIdAnggota(idAnggota);
        setNama(nama);
        setProdi(prodi);
        this.jumlahPinjamanAktif = 0;
        this.aktif = true;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        if (idAnggota == null || idAnggota.trim().isEmpty()) {
            throw new IllegalArgumentException("ID anggota tidak boleh kosong.");
        }
        this.idAnggota = idAnggota.trim().toUpperCase();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama anggota tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        if (prodi == null || prodi.trim().isEmpty()) {
            throw new IllegalArgumentException("Program studi tidak boleh kosong.");
        }
        this.prodi = prodi.trim();
    }

    public int getJumlahPinjamanAktif() {
        return jumlahPinjamanAktif;
    }

    public void setJumlahPinjamanAktif(int jumlahPinjamanAktif) {
        if (jumlahPinjamanAktif < 0 || jumlahPinjamanAktif > MAKS_PINJAMAN) {
            throw new IllegalArgumentException("Jumlah pinjaman aktif harus antara 0 sampai " + MAKS_PINJAMAN + ".");
        }
        this.jumlahPinjamanAktif = jumlahPinjamanAktif;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    // Method perilaku 1: cek apakah masih boleh meminjam (maksimal 3 buku)
    public boolean bisaPinjamLagi() {
        return aktif && jumlahPinjamanAktif < MAKS_PINJAMAN;
    }

    // Method perilaku 2: menambah jumlah pinjaman aktif saat transaksi berhasil
    public void tambahPinjamanAktif() {
        if (jumlahPinjamanAktif >= MAKS_PINJAMAN) {
            throw new IllegalStateException("Anggota sudah mencapai batas maksimal pinjaman (" + MAKS_PINJAMAN + " buku).");
        }
        this.jumlahPinjamanAktif++;
    }

    // Method perilaku 3: mengurangi jumlah pinjaman aktif saat buku dikembalikan
    public void kurangiPinjamanAktif() {
        if (jumlahPinjamanAktif <= 0) {
            throw new IllegalStateException("Tidak ada pinjaman aktif untuk dikurangi.");
        }
        this.jumlahPinjamanAktif--;
    }

    public void tampilkanInfo() {
        System.out.printf("[%s] %s (%s) | Pinjaman Aktif: %d/%d | Status: %s%n",
                idAnggota, nama, prodi, jumlahPinjamanAktif, MAKS_PINJAMAN, (aktif ? "Aktif" : "Nonaktif"));
    }
}
