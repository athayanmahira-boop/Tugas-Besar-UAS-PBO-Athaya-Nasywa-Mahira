package dao;

import model.Akta;
import java.time.LocalDate;

// Interface khusus Akta
public interface AktaDao extends CrudDao<Akta> {
    String generateKodeAkta(LocalDate tglAkta); // (2e String+Date)
    void rekapTahunan(int tahun);               // (2d Math)
}


