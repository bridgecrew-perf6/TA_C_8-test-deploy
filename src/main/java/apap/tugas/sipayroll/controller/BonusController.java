package apap.tugas.sipayroll.controller;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.JenisBonusModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BonusController {
    @Qualifier("bonusServiceImpl")
    @Autowired
    BonusService bonusService;

    @Autowired
    JenisBonusService jenisBonusService;

    @Autowired
    UserService userService;

    @Autowired
    GajiService gajiService;

    @Transactional
    @GetMapping("/bonus/add")
    public String addBonusFormPage(Model model){

        model.addAttribute("listJenisBonus", jenisBonusService.findAll());
        model.addAttribute("listUUIDuser", userService.findAll());
        return "form-add-bonus";
    }

    @Transactional
    @PostMapping("/bonus/add")
    public String addBonusSubmit(
            @ModelAttribute BonusModel bonus,
            Integer jenisBonus,
            String uuid,
            String tanggalDiberikan,
            Model model) throws ParseException {
        String msgStatus = "";
        Optional<UserModel> targetedUser = userService.findUserByUUID(uuid);
        GajiModel gajiTargetedUser = targetedUser.get().getGaji();

        List<BonusModel> listBonus = gajiTargetedUser.getListBonus();

        List<JenisBonusModel> jenisBonusUser = new ArrayList<>();

        for (BonusModel bonusModel : listBonus){
            jenisBonusUser.add(bonusModel.getJenisBonus());
        }

        List<Integer> ListIdJenisBonusUser = new ArrayList<>(); // contains jenis bonus
        for (JenisBonusModel id : jenisBonusUser){
            ListIdJenisBonusUser.add(id.getId());
        }

        if (jenisBonus == 3){ // training
            bonus.setJumlahBonus(150000);
            bonus.setGaji(gajiTargetedUser);
            bonus.setTanggalDiberikan(new SimpleDateFormat("yyyy-MM-dd").parse(tanggalDiberikan));
            bonusService.addBonus(bonus);
            msgStatus = "Bonus berhasil ditambahkan";
        }else if (jenisBonus == 1 && !(ListIdJenisBonusUser.contains(jenisBonus))){ // thr
            bonus.setJumlahBonus(gajiTargetedUser.getGajiPokok());
            bonus.setGaji(gajiTargetedUser);
            bonus.setTanggalDiberikan(new SimpleDateFormat("yyyy-MM-dd").parse(tanggalDiberikan));
            bonusService.addBonus(bonus);
            msgStatus = "Bonus berhasil ditambahkan";
        }else if (jenisBonus == 2 && !(ListIdJenisBonusUser.contains(jenisBonus))){ // bonus tahunan
            bonus.setJumlahBonus(2 * gajiTargetedUser.getGajiPokok());
            bonus.setGaji(gajiTargetedUser);
            bonus.setTanggalDiberikan(new SimpleDateFormat("yyyy-MM-dd").parse(tanggalDiberikan));
            bonusService.addBonus(bonus);
            msgStatus = "Bonus berhasil ditambahkan";
        }else{
            msgStatus = "THR dan Bonus Tahunan, hanya dapat diterima satu kali dalam satu tahun";
        }
        model.addAttribute("msgStatus", msgStatus);
        return "add-bonus";
    }

}
