package dao.impl;

import config.DBConnection;
import dao.AktaDao;
import exception.DataNotFoundException;
import model.Akta;
import util.DateUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Implementasi AktaDao (JDBC CRUD) (2h)
public class AktaDaoImpl implements AktaDao {

    public void insert(Akta a) {
        String sql = "INSERT INTO akta (kode_akta, tgl_akta, bentuk_perbuatan_hukum, jenis_nomor_hak, " +
                "letak_tanah_bangunan, luas_tanah, luas_bangunan, nilai_transaksi, status_akta) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, a.getKodeAkta());
            ps.setDate(2, DateUtil.toSqlDate(a.getTglAkta()));
            ps.setString(3, a.getBentukPerbuatanHukum());
            ps.setString(4, a.getJenisNomorHak());
            ps.setString(5, a.getLetakTanahBangunan());
            ps.setDouble(6, a.getLuasTanah());
            ps.setDouble(7, a.getLuasBangunan());
            ps.setLong(8, a.getNilaiTransaksi());
            ps.setString(9, a.getStatusAkta());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal insert akta: " + e.getMessage());
        }
    }

    public List<Akta> getAll() {
        List<Akta> list = new ArrayList<Akta>(); // 2g Collection

        String sql = "SELECT * FROM akta ORDER BY id_akta DESC";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Akta a = new Akta();
                a.setIdAkta(rs.getInt("id_akta"));
                a.setKodeAkta(rs.getString("kode_akta"));
                a.setTglAkta(rs.getDate("tgl_akta").toLocalDate());
                a.setBentukPerbuatanHukum(rs.getString("bentuk_perbuatan_hukum"));
                a.setJenisNomorHak(rs.getString("jenis_nomor_hak"));
                a.setLetakTanahBangunan(rs.getString("letak_tanah_bangunan"));
                a.setLuasTanah(rs.getDouble("luas_tanah"));
                a.setLuasBangunan(rs.getDouble("luas_bangunan"));
                a.setNilaiTransaksi(rs.getLong("nilai_transaksi"));
                a.setStatusAkta(rs.getString("status_akta"));
                list.add(a);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal ambil data akta: " + e.getMessage());
        }

        return list;
    }

    public void update(Akta a) {
        String sql = "UPDATE akta SET tgl_akta=?, bentuk_perbuatan_hukum=?, jenis_nomor_hak=?, " +
                "letak_tanah_bangunan=?, luas_tanah=?, luas_bangunan=?, nilai_transaksi=?, status_akta=? " +
                "WHERE id_akta=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDate(1, DateUtil.toSqlDate(a.getTglAkta()));
            ps.setString(2, a.getBentukPerbuatanHukum());
            ps.setString(3, a.getJenisNomorHak());
            ps.setString(4, a.getLetakTanahBangunan());
            ps.setDouble(5, a.getLuasTanah());
            ps.setDouble(6, a.getLuasBangunan());
            ps.setLong(7, a.getNilaiTransaksi());
            ps.setString(8, a.getStatusAkta());
            ps.setInt(9, a.getIdAkta());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DataNotFoundException("Akta ID " + a.getIdAkta() + " tidak ditemukan.");
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal update akta: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM akta WHERE id_akta=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DataNotFoundException("Akta ID " + id + " tidak ditemukan.");
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal delete akta: " + e.getMessage());
        }
    }

    // 2e String + Date: kode AKTA-YYYY-0001 (String.format + toUpperCase)
    public String generateKodeAkta(LocalDate tglAkta) {
        int tahun = tglAkta.getYear();
        int urut = 1;

        String sql = "SELECT COUNT(*) AS total FROM akta WHERE YEAR(tgl_akta)=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tahun);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                urut = rs.getInt("total") + 1;
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal generate kode akta: " + e.getMessage());
        }

        // 2e
        String kode = "AKTA-" + tahun + "-" + String.format("%04d", urut);
        return kode.toUpperCase();
    }

    // 2d Matematika: COUNT/SUM/AVG per tahun
    public void rekapTahunan(int tahun) {
        String sql = "SELECT COUNT(*) jumlah, SUM(nilai_transaksi) total, AVG(nilai_transaksi) rata " +
                     "FROM akta WHERE YEAR(tgl_akta)=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tahun);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int jumlah = rs.getInt("jumlah");
                long total = rs.getLong("total");
                double rata = rs.getDouble("rata");

                System.out.println("\n=== REKAP TAHUN " + tahun + " ===");
                System.out.println("Jumlah Akta          : " + jumlah);
                System.out.println("Total Nilai Transaksi: Rp " + total);
                System.out.println("Rata-rata Transaksi  : Rp " + (long) rata);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal rekap tahunan: " + e.getMessage());
        }
    }
}

