package model;

/**
 * Merepresentasikan transaksi peminjaman yang menghubungkan Buku dan Anggota.
 */
public class Peminjaman {
    private String idPeminjaman;
    private Buku buku;
    private Anggota anggota;
    private int lamaHari;
    private boolean sudahDikembalikan;

    public Peminjaman() {
        this.idPeminjaman = "PJM000";
        this.buku = null;
        this.anggota = null;
        this.lamaHari = 1;
        this.sudahDikembalikan = false;
    }

    public Peminjaman(String idPeminjaman, Buku buku, Anggota anggota, int lamaHari) {
        setIdPeminjaman(idPeminjaman);
        setBuku(buku);
        setAnggota(anggota);
        setLamaHari(lamaHari);
        this.sudahDikembalikan = false;
    }

    public String getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(String idPeminjaman) {
        if (idPeminjaman == null || idPeminjaman.trim().isEmpty()) {
            throw new IllegalArgumentException("ID peminjaman tidak boleh kosong.");
        }
        this.idPeminjaman = idPeminjaman.trim().toUpperCase();
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        if (buku == null) {
            throw new IllegalArgumentException("Objek buku tidak boleh null.");
        }
        this.buku = buku;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        if (anggota == null) {
            throw new IllegalArgumentException("Objek anggota tidak boleh null.");
        }
        this.anggota = anggota;
    }

    public int getLamaHari() {
        return lamaHari;
    }

    public void setLamaHari(int lamaHari) {
        if (lamaHari <= 0) {
            throw new IllegalArgumentException("Lama hari pinjam minimal 1 hari.");
        }
        this.lamaHari = lamaHari;
    }

    public boolean isSudahDikembalikan() {
        return sudahDikembalikan;
    }

    public void setSudahDikembalikan(boolean sudahDikembalikan) {
        this.sudahDikembalikan = sudahDikembalikan;
    }

    // Method perilaku 1: menghitung estimasi denda jika terlambat
    public double hitungEstimasiDenda(int hariTerlambat) {
        if (hariTerlambat <= 0 || buku == null) {
            return 0.0;
        }
        return hariTerlambat * buku.getDendaPerHari();
    }

    // Method perilaku 2: menandai peminjaman sudah selesai dikembalikan
    public void selesaikanPengembalian() {
        if (this.sudahDikembalikan) {
            throw new IllegalStateException("Peminjaman ini sudah dikembalikan sebelumnya.");
        }
        this.sudahDikembalikan = true;
        if (anggota != null) {
            anggota.kurangiPinjamanAktif();
        }
        if (buku != null) {
            buku.tambahStok(1);
        }
    }

    // Method perilaku 3: mencetak detail transaksi peminjaman
    public void tampilkanInfo() {
        String infoBuku = (buku != null) ? buku.getKodeBuku() + " - " + buku.getJudul() : "-";
        String infoAnggota = (anggota != null) ? anggota.getIdAnggota() + " - " + anggota.getNama() : "-";
        String status = sudahDikembalikan ? "Dikembalikan" : "Dipinjam";
        System.out.printf("ID: %s | Peminjam: %s | Buku: %s | Lama: %d hari | Status: %s%n",
                idPeminjaman, infoAnggota, infoBuku, lamaHari, status);
    }
}
