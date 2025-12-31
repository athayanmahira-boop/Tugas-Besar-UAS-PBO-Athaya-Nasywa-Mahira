package app;

import dao.AktaDao;
import dao.PajakAktaDao;
import dao.impl.AktaDaoImpl;
import dao.impl.PajakAktaDaoImpl;
import exception.InvalidInputException;
import model.Akta;
import model.PajakAkta;
import model.SSB;
import model.SSP;
import util.InputUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// Kelas ConsoleMenu berfungsi sebagai antarmuka aplikasi berbasis console
// Kelas ini mengatur alur menu, input pengguna, serta pemanggilan proses CRUD
public class ConsoleMenu {

    // Scanner untuk membaca input dari user
    private Scanner sc = new Scanner(System.in);

    // DAO untuk mengelola data akta dan pajak akta
    private AktaDao aktaDao = new AktaDaoImpl();
    private PajakAktaDao pajakDao = new PajakAktaDaoImpl();

    // ================= LOGIN =================
    // Method login untuk validasi username dan password
    // Digunakan untuk membatasi akses ke sistem
    private boolean login() {
        System.out.println("=== LOGIN SISTEM BUKU DAFTAR AKTA PPAT ===");

        String username = InputUtil.inputString(sc, "Username: ").toLowerCase();
        String password = InputUtil.inputString(sc, "Password: ");

        if (username.equals("admin") && password.equals("ppat123")) {
            System.out.println("Login berhasil! (Role: ADMIN)\n");
            return true;
        } else if (username.equals("staff") && password.equals("staff123")) {
            System.out.println("Login berhasil! (Role: STAFF)\n");
            return true;
        } else {
            System.out.println("Login gagal! Username atau password salah.\n");
            return false;
        }
    }

    // ================= START PROGRAM =================
    // Method start sebagai pengendali utama program
    // Berisi login, menu utama, dan perulangan program
    public void start() {

        // Percobaan login maksimal 3 kali (perulangan)
        int percobaan = 0;
        while (percobaan < 3 && !login()) {
            percobaan++;
        }

        // Jika login gagal 3 kali, program berhenti
        if (percobaan >= 3) {
            System.out.println("Terlalu banyak percobaan login. Program berhenti.");
            return;
        }

        int pilih = -1;

        // Perulangan menu utama menggunakan do-while
        do {
            System.out.println("===== MENU UTAMA =====");
            System.out.println("1. Tambah Akta");
            System.out.println("2. Lihat Akta");
            System.out.println("3. Update Akta");
            System.out.println("4. Hapus Akta");
            System.out.println("5. Tambah Pajak Akta");
            System.out.println("6. Lihat Pajak Akta");
            System.out.println("7. Update Pajak Akta");
            System.out.println("8. Hapus Pajak Akta");
            System.out.println("9. Rekap Tahunan");
            System.out.println("0. Keluar");

            try {
                // Input pilihan menu
                pilih = InputUtil.inputInt(sc, "Pilih menu: ");

                // Percabangan menggunakan switch-case
                switch (pilih) {
                    case 1:
                        tambahAkta();
                        break;
                    case 2:
                        lihatAkta();
                        break;
                    case 3:
                        updateAkta();
                        break;
                    case 4:
                        deleteAkta();
                        break;
                    case 5:
                        tambahPajak();
                        break;
                    case 6:
                        lihatPajak();
                        break;
                    case 7:
                        updatePajak();
                        break;
                    case 8:
                        deletePajak();
                        break;
                    case 9:
                        rekapTahunan();
                        break;
                    case 0:
                        System.out.println("Terima kasih telah menggunakan sistem.");
                        break;
                    default:
                        System.out.println("Menu tidak tersedia.\n");
                        break;
                }

            } catch (InvalidInputException e) {
                // Menangani kesalahan input user
                System.out.println("Error input: " + e.getMessage() + "\n");
            } catch (Exception e) {
                // Menangani error umum
                System.out.println("Terjadi kesalahan: " + e.getMessage() + "\n");
            }

        } while (pilih != 0);
    }

    // ================= MENU AKTA =================

    // Method untuk menambah data akta ke database
    private void tambahAkta() {
        System.out.println("\n=== TAMBAH AKTA ===");

        // Input data akta
        LocalDate tgl = InputUtil.inputDate(sc, "Tanggal akta");
        String kode = aktaDao.generateKodeAkta(tgl); // Generate kode akta otomatis

        String bentuk = InputUtil.inputString(sc, "Bentuk perbuatan hukum: ").toUpperCase();
        String jenisHak = InputUtil.inputString(sc, "Jenis & nomor hak: ");
        String letak = InputUtil.inputString(sc, "Letak tanah/bangunan: ");

        double luasTanah = InputUtil.inputDouble(sc, "Luas tanah: ");
        double luasBangunan = InputUtil.inputDouble(sc, "Luas bangunan: ");
        long nilai = InputUtil.inputLong(sc, "Nilai transaksi (Rp): ");

        // Perhitungan luas total (operasi matematika)
        double luasTotal = luasTanah + luasBangunan;
        System.out.println("Luas total (tanah + bangunan): " + luasTotal);

        // Pemilihan status akta menggunakan angka
        System.out.println("Status Akta:");
        System.out.println("1. DIPROSES");
        System.out.println("2. SELESAI");
        System.out.println("3. PENDING");

        int pilihStatus = InputUtil.inputInt(sc, "Pilih status (1-3): ");

        String status;
        switch (pilihStatus) {
            case 2:
                status = "SELESAI";
                break;
            case 3:
                status = "PENDING";
                break;
            default:
                status = "DIPROSES";
                break;
        }

        // Membuat objek Akta
        Akta akta = new Akta(
                kode, tgl, bentuk, jenisHak, letak,
                luasTanah, luasBangunan, nilai, status
        );

        // Menyimpan data ke database
        aktaDao.insert(akta);

        System.out.println("Akta berhasil ditambahkan.");
        System.out.println("Kode Akta: " + kode + "\n");
    }

    // Method untuk menampilkan seluruh data akta
    private void lihatAkta() {
        System.out.println("\n=== DATA AKTA ===");

        List<Akta> list = aktaDao.getAll(); // Menggunakan Collection (List)

        if (list.isEmpty()) {
            System.out.println("Data akta masih kosong.\n");
            return;
        }

        for (Akta a : list) {
            System.out.println(
                    "ID: " + a.getIdAkta() +
                    " | Kode: " + a.getKodeAkta() +
                    " | Tgl: " + a.getTglAkta() +
                    " | Nilai: Rp " + a.getNilaiTransaksi() +
                    " | Status: " + a.getStatusAkta()
            );
        }
        System.out.println();
    }

    // Method untuk mengupdate data akta
    private void updateAkta() {
        System.out.println("\n=== UPDATE AKTA ===");

        int id = InputUtil.inputInt(sc, "ID akta: ");
        LocalDate tgl = InputUtil.inputDate(sc, "Tanggal baru");

        String bentuk = InputUtil.inputString(sc, "Bentuk perbuatan hukum: ").toUpperCase();
        String jenisHak = InputUtil.inputString(sc, "Jenis & nomor hak: ");
        String letak = InputUtil.inputString(sc, "Letak tanah/bangunan: ");

        double luasTanah = InputUtil.inputDouble(sc, "Luas tanah: ");
        double luasBangunan = InputUtil.inputDouble(sc, "Luas bangunan: ");
        long nilai = InputUtil.inputLong(sc, "Nilai transaksi: ");

        // Pilihan status saat update
        System.out.println("Status Akta:");
        System.out.println("1. DIPROSES");
        System.out.println("2. SELESAI");
        System.out.println("3. PENDING");

        int pilihStatus = InputUtil.inputInt(sc, "Pilih status (1-3): ");

        String status;
        switch (pilihStatus) {
            case 2:
                status = "SELESAI";
                break;
            case 3:
                status = "PENDING";
                break;
            default:
                status = "DIPROSES";
                break;
        }

        Akta a = new Akta();
        a.setIdAkta(id);
        a.setTglAkta(tgl);
        a.setBentukPerbuatanHukum(bentuk);
        a.setJenisNomorHak(jenisHak);
        a.setLetakTanahBangunan(letak);
        a.setLuasTanah(luasTanah);
        a.setLuasBangunan(luasBangunan);
        a.setNilaiTransaksi(nilai);
        a.setStatusAkta(status);

        aktaDao.update(a);
        System.out.println("Akta berhasil diupdate.\n");
    }

    // Method untuk menghapus data akta
    private void deleteAkta() {
        System.out.println("\n=== HAPUS AKTA ===");
        int id = InputUtil.inputInt(sc, "ID akta: ");
        aktaDao.delete(id);
        System.out.println("Akta berhasil dihapus.\n");
    }

    // ================= MENU PAJAK =================

    // Method untuk menambah data pajak akta
    private void tambahPajak() {
        System.out.println("\n=== TAMBAH PAJAK AKTA ===");

        int idAkta = InputUtil.inputInt(sc, "ID akta: ");

        // Manipulasi string NOP
        String nopInput = InputUtil.inputString(sc, "NOP (boleh spasi/strip): ");
        String nop = nopInput.replace(" ", "").replace("-", "");

        String wilayah = (nop.length() >= 4) ? nop.substring(0, 4) : nop;

        System.out.println("NOP bersih      : " + nop);
        System.out.println("Kode wilayah    : " + wilayah);

        int tahunSppt = InputUtil.inputInt(sc, "Tahun SPPT: ");
        long njop = InputUtil.inputLong(sc, "NJOP: ");

        // Membuat objek SSP dan SSB (inheritance)
        SSP ssp = new SSP(
                InputUtil.inputDate(sc, "Tanggal SSP"),
                InputUtil.inputLong(sc, "Nominal SSP")
        );

        SSB ssb = new SSB(
                InputUtil.inputDate(sc, "Tanggal SSB"),
                InputUtil.inputLong(sc, "Nominal SSB")
        );

        // Perhitungan total pajak
        long totalPajak = ssp.getNominal() + ssb.getNominal();
        System.out.println("Total Pajak (SSP + SSB): Rp " + totalPajak);

        String ket = InputUtil.inputString(sc, "Keterangan: ");

        PajakAkta p = new PajakAkta(idAkta, nop, tahunSppt, njop, ssp, ssb, ket);
        pajakDao.insert(p);

        System.out.println("Pajak akta berhasil ditambahkan.\n");
    }

    // Method untuk menampilkan data pajak akta
    private void lihatPajak() {
        System.out.println("\n=== DATA PAJAK AKTA ===");

        List<PajakAkta> list = pajakDao.getAll();

        if (list.isEmpty()) {
            System.out.println("Data pajak masih kosong.\n");
            return;
        }

        for (PajakAkta p : list) {
            System.out.println(
                    "ID Pajak: " + p.getIdPajak() +
                    " | ID Akta: " + p.getIdAkta() +
                    " | NOP: " + p.getNop() +
                    " | Tahun: " + p.getTahunSppt() +
                    " | NJOP: " + p.getNjop() +
                    " | SSP: " + p.getSsp().getTanggal() + " Rp" + p.getSsp().getNominal() +
                    " | SSB: " + p.getSsb().getTanggal() + " Rp" + p.getSsb().getNominal()
            );
        }
        System.out.println();
    }

    // Method untuk mengupdate data pajak akta
    private void updatePajak() {
        System.out.println("\n=== UPDATE PAJAK AKTA ===");

        int idPajak = InputUtil.inputInt(sc, "ID pajak: ");
        int idAkta = InputUtil.inputInt(sc, "ID akta: ");

        String nop = InputUtil.inputString(sc, "NOP: ").replace(" ", "").replace("-", "");
        int tahun = InputUtil.inputInt(sc, "Tahun SPPT: ");
        long njop = InputUtil.inputLong(sc, "NJOP: ");

        SSP ssp = new SSP(
                InputUtil.inputDate(sc, "Tanggal SSP"),
                InputUtil.inputLong(sc, "Nominal SSP")
        );

        SSB ssb = new SSB(
                InputUtil.inputDate(sc, "Tanggal SSB"),
                InputUtil.inputLong(sc, "Nominal SSB")
        );

        String ket = InputUtil.inputString(sc, "Keterangan: ");

        PajakAkta p = new PajakAkta(idAkta, nop, tahun, njop, ssp, ssb, ket);
        p.setIdPajak(idPajak);

        pajakDao.update(p);
        System.out.println("Pajak akta berhasil diupdate.\n");
    }

    // Method untuk menghapus data pajak akta
    private void deletePajak() {
        System.out.println("\n=== HAPUS PAJAK AKTA ===");
        int id = InputUtil.inputInt(sc, "ID pajak: ");
        pajakDao.delete(id);
        System.out.println("Pajak akta berhasil dihapus.\n");
    }

    // Method untuk menampilkan rekap tahunan akta
    private void rekapTahunan() {
        System.out.println("\n=== REKAP TAHUNAN ===");
        int tahun = InputUtil.inputInt(sc, "Tahun: ");
        aktaDao.rekapTahunan(tahun); // COUNT / SUM / AVG
        System.out.println();
    }
}

