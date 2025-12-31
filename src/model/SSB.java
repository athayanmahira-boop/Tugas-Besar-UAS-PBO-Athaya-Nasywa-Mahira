package model;

import java.time.LocalDate;

// Subclass SSB extends Pajak (2c)
public class SSB extends Pajak {
    public SSB() {}
    public SSB(LocalDate tanggal, long nominal) {
        super(tanggal, nominal);
    }
}

