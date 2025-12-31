package model;

import java.time.LocalDate;

// Model Akta (tabel akta)
public class Akta {

    private int idAkta;
    private String kodeAkta;
    private LocalDate tglAkta;
    private String bentukPerbuatanHukum;
    private String jenisNomorHak;
    private String letakTanahBangunan;
    private double luasTanah;
    private double luasBangunan;
    private long nilaiTransaksi;
    private String statusAkta;

    // Constructor kosong (2a)
    public Akta() {}

    // Constructor (2a)
    public Akta(String kodeAkta, LocalDate tglAkta, String bentukPerbuatanHukum,
                String jenisNomorHak, String letakTanahBangunan,
                double luasTanah, double luasBangunan, long nilaiTransaksi, String statusAkta) {
        this.kodeAkta = kodeAkta;
        this.tglAkta = tglAkta;
        this.bentukPerbuatanHukum = bentukPerbuatanHukum;
        this.jenisNomorHak = jenisNomorHak;
        this.letakTanahBangunan = letakTanahBangunan;
        this.luasTanah = luasTanah;
        this.luasBangunan = luasBangunan;
        this.nilaiTransaksi = nilaiTransaksi;
        this.statusAkta = statusAkta;
    }

    // Getter & Setter
    public int getIdAkta() { return idAkta; }
    public void setIdAkta(int idAkta) { this.idAkta = idAkta; }

    public String getKodeAkta() { return kodeAkta; }
    public void setKodeAkta(String kodeAkta) { this.kodeAkta = kodeAkta; }

    public LocalDate getTglAkta() { return tglAkta; }
    public void setTglAkta(LocalDate tglAkta) { this.tglAkta = tglAkta; }

    public String getBentukPerbuatanHukum() { return bentukPerbuatanHukum; }
    public void setBentukPerbuatanHukum(String bentukPerbuatanHukum) { this.bentukPerbuatanHukum = bentukPerbuatanHukum; }

    public String getJenisNomorHak() { return jenisNomorHak; }
    public void setJenisNomorHak(String jenisNomorHak) { this.jenisNomorHak = jenisNomorHak; }

    public String getLetakTanahBangunan() { return letakTanahBangunan; }
    public void setLetakTanahBangunan(String letakTanahBangunan) { this.letakTanahBangunan = letakTanahBangunan; }

    public double getLuasTanah() { return luasTanah; }
    public void setLuasTanah(double luasTanah) { this.luasTanah = luasTanah; }

    public double getLuasBangunan() { return luasBangunan; }
    public void setLuasBangunan(double luasBangunan) { this.luasBangunan = luasBangunan; }

    public long getNilaiTransaksi() { return nilaiTransaksi; }
    public void setNilaiTransaksi(long nilaiTransaksi) { this.nilaiTransaksi = nilaiTransaksi; }

    public String getStatusAkta() { return statusAkta; }
    public void setStatusAkta(String statusAkta) { this.statusAkta = statusAkta; }
}
