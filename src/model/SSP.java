package model;

import java.time.LocalDate;

// Subclass SSP extends Pajak (2c)
public class SSP extends Pajak {
    public SSP() {}
    public SSP(LocalDate tanggal, long nominal) {
        super(tanggal, nominal);
    }
}

