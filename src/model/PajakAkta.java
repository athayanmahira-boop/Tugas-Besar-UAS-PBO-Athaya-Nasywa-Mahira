package model;

// Model PajakAkta (tabel pajak_akta)
public class PajakAkta {

    private int idPajak;
    private int idAkta;
    private String nop;
    private int tahunSppt;
    private long njop;

    // Inheritance dipakai nyata (2c)
    private SSP ssp;
    private SSB ssb;

    private String keterangan;

    public PajakAkta() {}

    public PajakAkta(int idAkta, String nop, int tahunSppt, long njop, SSP ssp, SSB ssb, String keterangan) {
        this.idAkta = idAkta;
        this.nop = nop;
        this.tahunSppt = tahunSppt;
        this.njop = njop;
        this.ssp = ssp;
        this.ssb = ssb;
        this.keterangan = keterangan;
    }

    public int getIdPajak() { return idPajak; }
    public void setIdPajak(int idPajak) { this.idPajak = idPajak; }

    public int getIdAkta() { return idAkta; }
    public void setIdAkta(int idAkta) { this.idAkta = idAkta; }

    public String getNop() { return nop; }
    public void setNop(String nop) { this.nop = nop; }

    public int getTahunSppt() { return tahunSppt; }
    public void setTahunSppt(int tahunSppt) { this.tahunSppt = tahunSppt; }

    public long getNjop() { return njop; }
    public void setNjop(long njop) { this.njop = njop; }

    public SSP getSsp() { return ssp; }
    public void setSsp(SSP ssp) { this.ssp = ssp; }

    public SSB getSsb() { return ssb; }
    public void setSsb(SSB ssb) { this.ssb = ssb; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}

