package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.LemburModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.repository.GajiDb;
import apap.tugas.sipayroll.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GajiServiceImpl implements GajiService {
    @Autowired
    GajiDb gajiDb;

    @Autowired
    UserDb userDb;

    @Autowired
    PelatihanRestService pelatihanRestService;

    @Override
    public List<GajiModel> getGajiList() {
        return gajiDb.findAll();
    }

    @Override
    public void addGaji(GajiModel gaji) {
        UserModel userPengaju = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        gaji.setPengaju(userPengaju);
        gaji.setStatusPersetujuan(0);
        gaji.setPenyetuju(null);
        gajiDb.save(gaji);
    }
    @Override
    public Integer getTotalLembur(GajiModel gaji){
        Integer lembur = 0;
        for(LemburModel l : gaji.getListLembur()){
            lembur += l.getKompensasiPerJam()*hitungJamLembur(l);
        }
        System.out.println(" total lembur adalah "+ lembur);
        return lembur;
    }

    public Integer hitungJamLembur(LemburModel l){
        long jam = (l.getWaktuSelesai().getTime() - l.getWaktuMulai().getTime())/3600000;
        return (int)jam;

    }

    @Override
    public Integer getTotalBonus(GajiModel gaji){
        Integer bonus = 0;
        for(BonusModel b : gaji.getListBonus()){
            bonus += b.getJumlahBonus();
        }
        Integer bonusPelatihan = 0;
        try {
            bonusPelatihan = 150000 * pelatihanRestService.getPelatihan(gaji.getUserGaji().getUsername()).getResult().size();
        }catch (Exception e){
            bonusPelatihan = 0;
        }
        bonus += bonusPelatihan;
        //System.out.println(" total bonus adalah "+ bonus);
        return bonus;

    }
    @Override
    public boolean pernahTraining(GajiModel gaji){
        Integer bonusPelatihan = 0;
        try {
            bonusPelatihan = 150000 * pelatihanRestService.getPelatihan(gaji.getUserGaji().getUsername()).getResult().size();
        }catch (NullPointerException e){
            return false;
        }
        return true;
    }

    @Override
    public GajiModel getGajiById(Integer idGaji) {
        return gajiDb.findById(idGaji).get();
    }

    @Override
    public Integer updateStatusGaji(GajiModel gaji) {
        if(SecurityContextHolder.getContext().getAuthentication().getName().equals(gaji.getUserGaji().getUsername())){
            return 0;
        }
        gaji.setPenyetuju(userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        gajiDb.save(gaji);
        return 1;
    }

    public GajiModel getGajiByUser(UserModel user) {
        return gajiDb.findByUserGaji(user);
    }

    @Override
    public Integer getTotalPendapatan(GajiModel gaji) {
        // Ambil bulan ini
        Date date = new Date();
        LocalDate tanggalHariIni = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer bulanIni = tanggalHariIni.getMonthValue();

        Calendar calendar = Calendar.getInstance();

        // Hitung Gaji
        Date tanggalMasuk = gaji.getTanggalMasuk();
        calendar.setTime(tanggalMasuk);
        Integer bulanMasuk = calendar.get(Calendar.MONTH);

        Integer gajiPokok = 0;
        if (bulanMasuk == bulanIni-1) {
            gajiPokok = gaji.getGajiPokok();
        }

        // Hitung Bonus
        Integer bonus = 0;
        List<BonusModel> listBonus = gaji.getListBonus();

        if (!(listBonus.isEmpty())) {
            for (BonusModel bonusModel : listBonus) {
                Date tanggalDiberikan = bonusModel.getTanggalDiberikan();
                calendar.setTime(tanggalDiberikan);
                Integer bulanDiberikan = calendar.get(Calendar.MONTH);

                if (bulanDiberikan == bulanIni-1) {
                    bonus += bonusModel.getJumlahBonus();
                }
            }
        }


        // Hitung Lembur
        Integer lembur = 0;
        List<LemburModel> listLembur = gaji.getListLembur();

        if (!(listLembur.isEmpty())) {
            for (LemburModel lemburModel : listLembur) {
                Date waktuMulai = lemburModel.getWaktuMulai();
                Date waktuSelesai = lemburModel.getWaktuSelesai();
                calendar.setTime(waktuMulai);
                Integer bulanMulai = calendar.get(Calendar.MONTH);

                if (bulanMulai == bulanIni-1) {
                    Integer kompensasi = lemburModel.getKompensasiPerJam();

                    long diffTime = waktuSelesai.getTime() - waktuMulai.getTime();
                    long diffHours = (diffTime / (1000 * 60 * 60)) % 24;

                    Integer diffHoursInt = (int) diffHours;
                    Integer totalLembur = kompensasi * diffHoursInt;
                    lembur += totalLembur;
                }
            }
        }


        // Hitung total pendapatan
        Integer totalPendapatan = gajiPokok + bonus + lembur;

        return totalPendapatan;
    }

    @Override
    public void deleteGaji(GajiModel gaji) {
        gajiDb.delete(gaji);
    }

    @Override
    public GajiModel updateGaji(GajiModel gaji) {
        GajiModel targetGaji = getGajiById(gaji.getId());
        UserModel userPengajuUpdate = userDb.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        try {
            targetGaji.setGajiPokok(gaji.getGajiPokok());

            if (targetGaji.getStatusPersetujuan() == 1 || targetGaji.getStatusPersetujuan() == 2) {
                targetGaji.setStatusPersetujuan(0);
                targetGaji.setPenyetuju(null);
            }

            if (targetGaji.getPengaju() != userPengajuUpdate) {
                targetGaji.setPengaju(userPengajuUpdate);
            }

            gajiDb.save(targetGaji);
            return targetGaji;

        }
        catch (NullPointerException nullException) {
            return null;
        }

    }

    @Override
    public void addGajiUserPelatihan(GajiModel gaji) {
        gaji.setStatusPersetujuan(0);
        gaji.setPenyetuju(null);
        gajiDb.save(gaji);
    }

}
