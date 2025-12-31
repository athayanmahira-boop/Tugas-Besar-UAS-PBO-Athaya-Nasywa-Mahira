package model;

import java.time.LocalDate;

// Superclass Pajak (2c inheritance)
public class Pajak {

    protected LocalDate tanggal;
    protected long nominal;

    public Pajak() {}

    public Pajak(LocalDate tanggal, long nominal) {
        this.tanggal = tanggal;
        this.nominal = nominal;
    }

    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }

    public long getNominal() { return nominal; }
    public void setNominal(long nominal) { this.nominal = nominal; }
}

