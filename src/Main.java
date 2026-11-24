import java.util.Scanner;

import adt.PeminjamanSortedList;
import model.Anggota;
import model.Buku;
import model.Peminjaman;
import model.Perpustakaan;

/**
 * Kelas utama antarmuka console untuk aplikasi Perpustakaan Mini.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Perpustakaan perpus = new Perpustakaan("Perpustakaan Mini STI", "Jl. Ahmad Dahlan, Kendari", 5);
        seedDataAwal(perpus);

        boolean berjalan = true;
        while (berjalan) {
            tampilkanMenu();
            int pilihan = bacaInt("Pilih menu: ");

            switch (pilihan) {
                case 1 -> menuTambahBuku(perpus);
                case 2 -> perpus.tampilkanSemuaBuku();
                case 3 -> menuTambahAnggota(perpus);
                case 4 -> perpus.tampilkanSemuaAnggota();
                case 5 -> menuBuatPeminjaman(perpus);
                case 6 -> menuCariData(perpus);
                case 7 -> menuOperasiAdt(perpus);
                case 8 -> perpus.tampilkanRingkasan();
                case 0 -> {
                    berjalan = false;
                    System.out.println("Program selesai. Terima kasih!");
                }
                default -> System.out.println("Pilihan menu tidak valid.");
            }
        }
        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("================ MENU PERPUSTAKAAN MINI ================");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Tampilkan Semua Buku");
        System.out.println("3. Tambah Anggota");
        System.out.println("4. Tampilkan Semua Anggota");
        System.out.println("5. Buat Transaksi Peminjaman");
        System.out.println("6. Cari Data (Buku / Anggota / Peminjaman)");
        System.out.println("7. Operasi ADT Sorted List & Tampilkan Isi");
        System.out.println("8. Tampilkan Ringkasan");
        System.out.println("0. Keluar");
        System.out.println("========================================================");
    }

    private static void seedDataAwal(Perpustakaan perpus) {
        perpus.tambahBuku(new Buku("B01", "Dasar Pemrograman Java", "Budi Raharjo", 3, 2000));
        perpus.tambahBuku(new Buku("B02", "Struktur Data dan Algoritma", "Rinaldi Munir", 2, 2500));
        perpus.tambahAnggota(new Anggota("A01", "Siska", "Sistem dan Teknologi Informasi"));
        perpus.tambahAnggota(new Anggota("A02", "Rina", "Sistem dan Teknologi Informasi"));
    }

    private static void menuTambahBuku(Perpustakaan perpus) {
        try {
            String kode = bacaTeksNonKosong("Kode buku: ");
            String judul = bacaTeksNonKosong("Judul buku: ");
            String penulis = bacaTeksNonKosong("Penulis: ");
            int stok = bacaInt("Stok awal: ");
            double denda = bacaDouble("Denda per hari (Rp): ");

            Buku buku = new Buku(kode, judul, penulis, stok, denda);
            if (perpus.tambahBuku(buku)) {
                System.out.println("Buku berhasil ditambahkan.");
            } else {
                System.out.println("Gagal: Kode buku sudah digunakan.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Input gagal: " + e.getMessage());
        }
    }

    private static void menuTambahAnggota(Perpustakaan perpus) {
        try {
            String id = bacaTeksNonKosong("ID anggota: ");
            String nama = bacaTeksNonKosong("Nama anggota: ");
            String prodi = bacaTeksNonKosong("Program studi: ");

            Anggota anggota = new Anggota(id, nama, prodi);
            if (perpus.tambahAnggota(anggota)) {
                System.out.println("Anggota berhasil ditambahkan.");
            } else {
                System.out.println("Gagal: ID anggota sudah digunakan.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Input gagal: " + e.getMessage());
        }
    }

    private static void menuBuatPeminjaman(Perpustakaan perpus) {
        String idPinjam = bacaTeksNonKosong("ID peminjaman (misal P02): ");
        String kodeBuku = bacaTeksNonKosong("Kode buku: ");
        String idAnggota = bacaTeksNonKosong("ID anggota: ");
        int lamaHari = bacaInt("Lama pinjam (hari): ");

        String hasil = perpus.prosesPinjam(idPinjam, kodeBuku, idAnggota, lamaHari);
        System.out.println(hasil);
    }

    private static void menuCariData(Perpustakaan perpus) {
        System.out.println("1. Cari Buku berdasarkan Kode");
        System.out.println("2. Cari Anggota berdasarkan ID");
        System.out.println("3. Cari Peminjaman di ADT berdasarkan ID");
        int jenis = bacaInt("Pilih jenis pencarian: ");

        if (jenis == 1) {
            String kode = bacaTeksNonKosong("Masukkan kode buku: ");
            Buku b = perpus.cariBukuByKode(kode);
            if (b != null) b.tampilkanInfo();
            else System.out.println("Buku tidak ditemukan.");
        } else if (jenis == 2) {
            String id = bacaTeksNonKosong("Masukkan ID anggota: ");
            Anggota a = perpus.cariAnggotaById(id);
            if (a != null) a.tampilkanInfo();
            else System.out.println("Anggota tidak ditemukan.");
        } else if (jenis == 3) {
            String idP = bacaTeksNonKosong("Masukkan ID peminjaman: ");
            int idx = perpus.getRiwayatPeminjaman().search(idP);
            if (idx != -1) {
                Peminjaman p = perpus.getRiwayatPeminjaman().get(idx);
                System.out.println("Ditemukan pada index ke-" + idx + ":");
                p.tampilkanInfo();
            } else {
                System.out.println("Data peminjaman tidak ditemukan di ADT.");
            }
        } else {
            System.out.println("Pilihan pencarian tidak valid.");
        }
    }

    private static void menuOperasiAdt(Perpustakaan perpus) {
        PeminjamanSortedList adt = perpus.getRiwayatPeminjaman();
        System.out.println("1. Tampilkan seluruh isi ADT");
        System.out.println("2. Hapus data peminjaman berdasarkan ID (remove)");
        System.out.println("3. Ambil elemen berdasarkan index (get)");
        System.out.println("4. Cek status ADT (isEmpty, isFull, size)");
        int op = bacaInt("Pilih operasi: ");

        switch (op) {
            case 1 -> adt.tampilkan();
            case 2 -> {
                String idHapus = bacaTeksNonKosong("ID peminjaman yang dihapus: ");
                int idx = adt.search(idHapus);
                if (idx != -1) {
                    Peminjaman target = adt.get(idx);
                    if (!target.isSudahDikembalikan()) {
                        target.selesaikanPengembalian();
                    }
                    if (adt.remove(idHapus)) {
                        System.out.println("Data peminjaman " + idHapus + " berhasil dihapus dari ADT.");
                    }
                } else {
                    System.out.println("Data dengan ID tersebut tidak ditemukan di ADT.");
                }
            }
            case 3 -> {
                int idx = bacaInt("Masukkan index: ");
                try {
                    Peminjaman p = adt.get(idx);
                    p.tampilkanInfo();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Gagal: " + e.getMessage());
                }
            }
            case 4 -> System.out.printf("isEmpty: %b | isFull: %b | size: %d/%d%n",
                    adt.isEmpty(), adt.isFull(), adt.size(), adt.getKapasitas());
            default -> System.out.println("Operasi tidak valid.");
        }
    }

    private static String bacaTeksNonKosong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Input tidak boleh kosong, silakan ulangi.");
        }
    }

    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String baris = scanner.nextLine().trim();
            try {
                return Integer.parseInt(baris);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan harus berupa angka bulat yang valid.");
            }
        }
    }

    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String baris = scanner.nextLine().trim();
            try {
                return Double.parseDouble(baris);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan harus berupa angka yang valid.");
            }
        }
    }
}
