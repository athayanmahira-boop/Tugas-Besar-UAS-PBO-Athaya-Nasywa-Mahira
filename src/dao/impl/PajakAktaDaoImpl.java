package dao.impl;

import config.DBConnection;
import dao.PajakAktaDao;
import exception.DataNotFoundException;
import model.PajakAkta;
import model.SSB;
import model.SSP;
import util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementasi PajakAktaDao (JDBC CRUD) (2h)
public class PajakAktaDaoImpl implements PajakAktaDao {

    public void insert(PajakAkta p) {
        String sql = "INSERT INTO pajak_akta (id_akta, nop, tahun_sppt, njop, tgl_ssp, nominal_ssp, tgl_ssb, nominal_ssb, keterangan) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, p.getIdAkta());
            ps.setString(2, p.getNop());
            ps.setInt(3, p.getTahunSppt());
            ps.setLong(4, p.getNjop());

            // 2c inheritance dipakai nyata
            ps.setDate(5, DateUtil.toSqlDate(p.getSsp().getTanggal()));
            ps.setLong(6, p.getSsp().getNominal());
            ps.setDate(7, DateUtil.toSqlDate(p.getSsb().getTanggal()));
            ps.setLong(8, p.getSsb().getNominal());

            ps.setString(9, p.getKeterangan());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal insert pajak_akta: " + e.getMessage());
        }
    }

    public List<PajakAkta> getAll() {
        List<PajakAkta> list = new ArrayList<PajakAkta>(); // 2g Collection

        String sql = "SELECT * FROM pajak_akta ORDER BY id_pajak DESC";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PajakAkta p = new PajakAkta();
                p.setIdPajak(rs.getInt("id_pajak"));
                p.setIdAkta(rs.getInt("id_akta"));
                p.setNop(rs.getString("nop"));
                p.setTahunSppt(rs.getInt("tahun_sppt"));
                p.setNjop(rs.getLong("njop"));

                // Ambil dari DB dan bentuk object SSP/SSB (2c)
                SSP ssp = new SSP(rs.getDate("tgl_ssp").toLocalDate(), rs.getLong("nominal_ssp"));
                SSB ssb = new SSB(rs.getDate("tgl_ssb").toLocalDate(), rs.getLong("nominal_ssb"));
                p.setSsp(ssp);
                p.setSsb(ssb);

                p.setKeterangan(rs.getString("keterangan"));

                list.add(p);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal ambil pajak_akta: " + e.getMessage());
        }

        return list;
    }

    public void update(PajakAkta p) {
        String sql = "UPDATE pajak_akta SET id_akta=?, nop=?, tahun_sppt=?, njop=?, tgl_ssp=?, nominal_ssp=?, tgl_ssb=?, nominal_ssb=?, keterangan=? " +
                     "WHERE id_pajak=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, p.getIdAkta());
            ps.setString(2, p.getNop());
            ps.setInt(3, p.getTahunSppt());
            ps.setLong(4, p.getNjop());
            ps.setDate(5, DateUtil.toSqlDate(p.getSsp().getTanggal()));
            ps.setLong(6, p.getSsp().getNominal());
            ps.setDate(7, DateUtil.toSqlDate(p.getSsb().getTanggal()));
            ps.setLong(8, p.getSsb().getNominal());
            ps.setString(9, p.getKeterangan());
            ps.setInt(10, p.getIdPajak());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DataNotFoundException("Pajak ID " + p.getIdPajak() + " tidak ditemukan.");
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal update pajak_akta: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM pajak_akta WHERE id_pajak=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DataNotFoundException("Pajak ID " + id + " tidak ditemukan.");
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal delete pajak_akta: " + e.getMessage());
        }
    }
}

