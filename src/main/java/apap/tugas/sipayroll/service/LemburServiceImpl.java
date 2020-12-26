package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.LemburModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.repository.LemburDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LemburServiceImpl implements LemburService{
    @Autowired
    LemburDb lemburDb;

    @Autowired
    UserService userService;


    @Override
    public LemburModel addLembur(LemburModel lembur) {
        try {
            lembur.setKompensasiPerJam(120000);
            lembur.setStatusPersetujuan(0);
            lembur.setGaji(getUserLembur(lembur).getGaji());
            lemburDb.save(lembur);
            return lembur;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public List<LemburModel> getLemburList() {
        return lemburDb.findAll();
    }

    @Override
    public boolean dateLemburValid(Date waktuMulai, Date waktuSelesai) {
        if (waktuMulai.getDay() == waktuSelesai.getDay()) {
            return true;
        }
        return false;
    }

    @Override
    public UserModel getUserLembur(LemburModel lembur) {
        return userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public LemburModel getLemburById(Integer idLembur) { return lemburDb.findById(idLembur).get(); }

    @Override
    public LemburModel updateLembur(LemburModel lembur){
        LemburModel targetLembur = lemburDb.findById(lembur.getId()).get();

        try{
            targetLembur.setStatusPersetujuan(lembur.getStatusPersetujuan());
            targetLembur.setWaktuMulai(lembur.getWaktuMulai());
            targetLembur.setWaktuSelesai(lembur.getWaktuSelesai());
            lemburDb.save(targetLembur);
            return targetLembur;
        } catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public LemburModel deleteLembur(LemburModel lembur) {
        LemburModel targetLembur = lemburDb.findById(lembur.getId()).get();
        lemburDb.delete(targetLembur);
        return targetLembur;
    }

    @Override
    public Integer getTotalKompensasi(LemburModel lembur) {
        Integer kompensasiTotal = (lembur.getWaktuSelesai().getHours() - lembur.getWaktuMulai().getHours())*120000;
        return kompensasiTotal;
    }

    @Override
    public List<LemburModel> getLemburByUser(UserModel user) {
        List<LemburModel> listLemburUser = new ArrayList<LemburModel>();
        for (LemburModel lembur : this.getLemburList()) {
            if(user.getGaji() == lembur.getGaji()) {
                listLemburUser.add(lembur);
            }
        }
        return listLemburUser;
    }
}
