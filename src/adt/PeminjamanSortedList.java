package adt;

import model.Peminjaman;

/**
 * ADT Sorted List berbasis array yang selalu menjaga elemen tetap terurut
 * berdasarkan idPeminjaman secara menaik (ascending).
 */
public class PeminjamanSortedList {
    private Peminjaman[] data;
    private int jumlah;
    private int kapasitas;

    public PeminjamanSortedList() {
        this(10);
    }

    public PeminjamanSortedList(int kapasitas) {
        if (kapasitas <= 0) {
            throw new IllegalArgumentException("Kapasitas ADT harus lebih dari 0.");
        }
        this.kapasitas = kapasitas;
        this.data = new Peminjaman[kapasitas];
        this.jumlah = 0;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public boolean isEmpty() {
        return jumlah == 0;
    }

    public boolean isFull() {
        return jumlah >= kapasitas;
    }

    public int size() {
        return jumlah;
    }

    // Menyisipkan elemen baru sambil menjaga urutan berdasarkan idPeminjaman
    public boolean insert(Peminjaman item) {
        if (item == null) {
            throw new IllegalArgumentException("Data peminjaman tidak boleh null.");
        }
        if (isFull()) {
            return false;
        }
        if (search(item.getIdPeminjaman()) != -1) {
            throw new IllegalArgumentException("ID peminjaman " + item.getIdPeminjaman() + " sudah terdaftar.");
        }

        int posisi = jumlah;
        // Geser ke kanan selama elemen sebelumnya lebih besar ID-nya
        for (int i = 0; i < jumlah; i++) {
            if (data[i].getIdPeminjaman().compareToIgnoreCase(item.getIdPeminjaman()) > 0) {
                posisi = i;
                break;
            }
        }

        for (int j = jumlah; j > posisi; j--) {
            data[j] = data[j - 1];
        }

        data[posisi] = item;
        jumlah++;
        return true;
    }

    // Menghapus elemen berdasarkan idPeminjaman dan merapatkan array
    public boolean remove(String idPeminjaman) {
        if (isEmpty() || idPeminjaman == null) {
            return false;
        }
        int idx = search(idPeminjaman.trim());
        if (idx == -1) {
            return false;
        }
        for (int i = idx; i < jumlah - 1; i++) {
            data[i] = data[i + 1];
        }
        data[jumlah - 1] = null;
        jumlah--;
        return true;
    }

    // Mencari index elemen berdasarkan idPeminjaman (mengembalikan -1 jika tidak ada)
    public int search(String idPeminjaman) {
        if (idPeminjaman == null || isEmpty()) {
            return -1;
        }
        String target = idPeminjaman.trim();
        for (int i = 0; i < jumlah; i++) {
            if (data[i].getIdPeminjaman().equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }

    // Mengambil elemen pada index tertentu dengan pengecekan batas
    public Peminjaman get(int index) {
        if (index < 0 || index >= jumlah) {
            throw new IndexOutOfBoundsException("Index di luar jangkauan data ADT.");
        }
        return data[index];
    }

    // Menampilkan seluruh isi ADT secara berurutan
    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("Daftar peminjaman (Sorted List) masih kosong.");
            return;
        }
        System.out.println("--- Daftar Peminjaman Terurut (Kapasitas: " + jumlah + "/" + kapasitas + ") ---");
        for (int i = 0; i < jumlah; i++) {
            System.out.print((i + 1) + ". ");
            data[i].tampilkanInfo();
        }
    }
}
