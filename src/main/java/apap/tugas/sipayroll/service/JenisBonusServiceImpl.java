package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.model.JenisBonusModel;
import apap.tugas.sipayroll.model.RoleModel;
import apap.tugas.sipayroll.repository.JenisBonusDb;
import apap.tugas.sipayroll.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JenisBonusServiceImpl implements JenisBonusService{
    @Autowired
    JenisBonusDb jenisBonusDb;

    @Override
    public List<JenisBonusModel> findAll() { return jenisBonusDb.findAll(); }

    @Override
    public JenisBonusModel getBonusByIdBonus(Integer idJenisBonus){
        return jenisBonusDb.findById(idJenisBonus).get();
    }
}
