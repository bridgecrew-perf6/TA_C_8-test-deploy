package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.model.JenisBonusModel;

import java.util.List;

public interface JenisBonusService {
    List<JenisBonusModel> findAll();

    JenisBonusModel getBonusByIdBonus(Integer idJenisBonus);
}
