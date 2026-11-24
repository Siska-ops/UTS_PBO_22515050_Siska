package model;

/**
 * Merepresentasikan entitas buku di perpustakaan mini.
 */
public class Buku {
    public static final double TARIF_DENDA_DEFAULT = 2000.0;

    private String kodeBuku;
    private String judul;
    private String penulis;
    private int stok;
    private double dendaPerHari;
    private boolean tersedia;

    // Konstruktor default
    public Buku() {
        this.kodeBuku = "BK000";
        this.judul = "Tanpa Judul";
        this.penulis = "Tidak Diketahui";
        this.stok = 0;
        this.dendaPerHari = TARIF_DENDA_DEFAULT;
        this.tersedia = false;
    }

    // Konstruktor berparameter
    public Buku(String kodeBuku, String judul, String penulis, int stok, double dendaPerHari) {
        setKodeBuku(kodeBuku);
        setJudul(judul);
        setPenulis(penulis);
        setStok(stok);
        setDendaPerHari(dendaPerHari);
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public void setKodeBuku(String kodeBuku) {
        if (kodeBuku == null || kodeBuku.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode buku tidak boleh kosong.");
        }
        this.kodeBuku = kodeBuku.trim().toUpperCase();
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul buku tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        if (penulis == null || penulis.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama penulis tidak boleh kosong.");
        }
        this.penulis = penulis.trim();
    }

    public int getStok() {
        return stok;
    }

    // Validasi agar stok tidak pernah bernilai negatif
    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok buku tidak boleh negatif.");
        }
        this.stok = stok;
        this.tersedia = this.stok > 0;
    }

    public double getDendaPerHari() {
        return dendaPerHari;
    }

    public void setDendaPerHari(double dendaPerHari) {
        if (dendaPerHari < 0) {
            throw new IllegalArgumentException("Denda per hari tidak boleh negatif.");
        }
        this.dendaPerHari = dendaPerHari;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    // Method perilaku 1: menambah stok buku
    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah penambahan stok harus lebih dari 0.");
        }
        setStok(this.stok + jumlah);
    }

    // Method perilaku 2: mengurangi stok saat dipinjam (cek invariant stok >= 0)
    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pengurangan stok harus lebih dari 0.");
        }
        if (this.stok - jumlah < 0) {
            throw new IllegalStateException("Stok buku tidak mencukupi (tidak boleh negatif).");
        }
        setStok(this.stok - jumlah);
    }

    // Method perilaku 3: menampilkan informasi detail buku
    public void tampilkanInfo() {
        System.out.printf("[%s] %s - %s | Stok: %d | Denda/hari: Rp%.0f | Status: %s%n",
                kodeBuku, judul, penulis, stok, dendaPerHari, (tersedia ? "Tersedia" : "Habis"));
    }
}
