package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.repository.BonusDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonusServiceImpl implements BonusService{
    @Autowired
    BonusDb bonusDb;

    @Override
    public BonusModel addBonus(BonusModel bonus) {
        return bonusDb.save(bonus);
    }

    @Override
    public BonusModel getBonusByIdBonus(Long idBonus){
        return bonusDb.findById(idBonus).get();
    }

}
