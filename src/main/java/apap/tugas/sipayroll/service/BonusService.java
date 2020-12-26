package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.BonusModel;

public interface BonusService {
    BonusModel addBonus(BonusModel bonus);

    BonusModel getBonusByIdBonus(Long idBonus);
}
