package apap.tugas.sipayroll.controller;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.LemburModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.service.GajiService;
import apap.tugas.sipayroll.service.LemburService;
import apap.tugas.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LemburController {
    @Autowired
    LemburService lemburService;

    @Autowired
    UserService userService;

    @Autowired
    GajiService gajiService;

    @GetMapping("/lembur/tambah")
    public String addLemburFormPage(Model model) {
        List<UserModel> listUser = userService.getUserList();

        model.addAttribute("newLembur", new LemburModel());
        model.addAttribute("listUser", listUser);
        return "form-tambah-lembur";
    }

    @PostMapping("/lembur/tambah")
    public String addLemburSubmit(
            @ModelAttribute LemburModel lembur,
            Model model) {

        List<GajiModel> listGaji = gajiService.getGajiList();
        model.addAttribute("newLembur", new LemburModel());

        for (GajiModel gajiDatabase : listGaji) {
            if (lemburService.getUserLembur(lembur).getId().equals(gajiDatabase.getUserGaji().getId())) {
                if(lemburService.dateLemburValid(lembur.getWaktuMulai(),lembur.getWaktuSelesai())) {
                    lemburService.addLembur(lembur);
                    String msgStatus = "Berhasil";
                    model.addAttribute("msgStatus", msgStatus);
                    model.addAttribute("msg", "Lembur berhasil ditambahkan.");
                    return "tambah-lembur";
                } else {
                    String msgStatus = "Gagal";
                    model.addAttribute("msgStatus", msgStatus);
                    model.addAttribute("msg", "Lembur gagal ditambahkan, hari waktu mulai dan selesai lembur harus sama.");
                    return "tambah-lembur";
                }
            }
        }
        String msgStatus = "Gagal";
        model.addAttribute("msgStatus", msgStatus);
        model.addAttribute("msg", "Lembur gagal ditambahkan karena user belum memiliki ID pegawai pada database gaji.");
        return "tambah-lembur";
    }

    @GetMapping("/lembur")
    public String viewAllLembur(Model model) {
        UserModel user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Integer> listKompensasi = new ArrayList<>();

        if (user.getRole().getNama().equals("Kepala Departemen HR") || user.getRole().getNama().equals("Staff Payroll")) {
            List<LemburModel> listLembur = lemburService.getLemburList();

            if (listLembur==null){
                String msg = "tidak ada gaji";
                model.addAttribute("msg", msg);
                model.addAttribute("message", "Belum terdapat daftar lembur.");
            }
            else{
                for (LemburModel lembur : listLembur) {
                    Integer totalKompensasi = lemburService.getTotalKompensasi(lembur);
                    listKompensasi.add(totalKompensasi);
                }

                String msg = "ada lembur";
                model.addAttribute("msg", msg);
                model.addAttribute("listKompensasi", listKompensasi);
                model.addAttribute("listLembur", listLembur);
            }
        }
        else {
            List<LemburModel> listLemburByUser = lemburService.getLemburByUser(user);

            if (listLemburByUser==null){
                String msg = "tidak ada gaji";
                model.addAttribute("msg", msg);
                model.addAttribute("message", "Belum terdapat lembur.");
            }
            else{
                for (LemburModel lembur : listLemburByUser) {
                    Integer totalKompensasi = lemburService.getTotalKompensasi(lembur);
                    listKompensasi.add(totalKompensasi);
                }

                String msg = "ada lembur";
                model.addAttribute("msg", msg);
                model.addAttribute("listKompensasi", listKompensasi);
                model.addAttribute("listLembur", listLemburByUser);
            }
        }

        return "view-all-lembur";

    }

    @GetMapping("/lembur/change/{idLembur}")
    public String changeLemburFormPage(
            @PathVariable Integer idLembur,
            Model model
    ){
        UserModel user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        String role = user.getRole().getNama();
        model.addAttribute("role", role);
        LemburModel lembur = lemburService.getLemburById(idLembur);
        model.addAttribute("lembur", lembur);

        return "form-update-lembur";
    }

    @PostMapping("/lembur/change")
    public String changeLemburFormSubmit(
            @ModelAttribute LemburModel lembur,
            Model model
    ){
        if(lemburService.dateLemburValid(lembur.getWaktuMulai(),lembur.getWaktuSelesai())) {
            LemburModel lemburUpdated = lemburService.updateLembur(lembur);
            model.addAttribute("lembur", lemburUpdated);

            String msgStatus = "Berhasil";
            model.addAttribute("msgStatus", msgStatus);
            model.addAttribute("msg", "Lembur berhasil diupdate.");
            return "update-lembur";
        }else {
            String msgStatus = "Gagal";
            model.addAttribute("msgStatus", msgStatus);
            model.addAttribute("msg", "Lembur gagal diupdate, hari waktu mulai dan selesai lembur harus sama.");
            return "update-lembur";
        }
    }

    @GetMapping("/lembur/delete/{idLembur}")
    public String deleteLemburSubmit(
            @PathVariable Integer idLembur,
            Model model){
        LemburModel lembur = lemburService.getLemburById(idLembur);

        LemburModel targetedLembur = lemburService.deleteLembur(lembur);
        model.addAttribute("lembur", targetedLembur);
        return "delete-lembur";
    }

}