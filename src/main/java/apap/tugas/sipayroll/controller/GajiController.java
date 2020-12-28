package apap.tugas.sipayroll.controller;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.service.GajiService;
import apap.tugas.sipayroll.service.PelatihanRestService;
import apap.tugas.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class GajiController {
    @Autowired
    GajiService gajiService;

    @Autowired
    UserService userService;


    @GetMapping("/gaji/tambah")
    public String addGajiFormPage(Model model) {
        List<UserModel> listUser = userService.getUserList();

        model.addAttribute("newGaji", new GajiModel());
        model.addAttribute("listUser", listUser);
        return "form-tambah-gaji";
    }

    @PostMapping("/gaji/tambah")
    public String addGajiSubmit(
            @ModelAttribute GajiModel gaji,
            Model model) {
        List<UserModel> listUser = userService.getUserList();
        List<GajiModel> listGaji = gajiService.getGajiList();

        model.addAttribute("newGaji", new GajiModel());
        model.addAttribute("listUser", listUser);
        model.addAttribute("listGaji", listGaji);

        // Verifikasi ID User pada database Gaji
        for (GajiModel gajiDatabase : listGaji) {
            if (gaji.getUserGaji().getId().equals(gajiDatabase.getUserGaji().getId())) {
                String msgStatus = "Gagal";
                model.addAttribute("msgStatus", msgStatus);
                model.addAttribute("msg", "Gaji gagal ditambahkan karena user sudah memiliki gaji.");
                return "tambah-gaji";
            }
        }

        gajiService.addGaji(gaji);
        String msgStatus = "Berhasil";
        model.addAttribute("msgStatus", msgStatus);
        model.addAttribute("msg", "Gaji berhasil ditambahkan.");

        return "tambah-gaji";

    }

    @Transactional
    @GetMapping("/gaji/ubah-status/{idGaji}")
    public String ubahStatus(@PathVariable Integer idGaji, Model model){
        model.addAttribute("idGaji", idGaji);
        GajiModel gaji = gajiService.getGajiById(idGaji);
        model.addAttribute("gaji",gaji);
        model.addAttribute("status",2);
        model.addAttribute("newGaji", new GajiModel());
        return "form-ubah-status-gaji";
    }
    @Transactional
    @PostMapping("/gaji/ubah-status/{idGaji}")
    public String ubahStatusSubmit(@PathVariable Integer idGaji,
                                   @ModelAttribute GajiModel gajiUpdated, Model model)
    {
        model.addAttribute("idGaji", idGaji);
        GajiModel gaji = gajiService.getGajiById(idGaji);
        gaji.setStatusPersetujuan(gajiUpdated.getStatusPersetujuan());
        Integer statusGaji = gajiService.updateStatusGaji(gaji);
        model.addAttribute("newGaji", new GajiModel());
        model.addAttribute("gaji",gaji);
        model.addAttribute("status", statusGaji);

        return "form-ubah-status-gaji";
    }

    @Transactional
    @GetMapping("/gaji")
    public String viewAllGaji(Model model) {
        UserModel user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Integer> listTotalPendapatan = new ArrayList<>();

        if (user.getRole().getNama().equals("Kepala Departemen HR") || user.getRole().getNama().equals("Staff Payroll")) {
            List<GajiModel> listGaji = gajiService.getGajiList();

            if (listGaji==null){
                String msg = "tidak ada gaji";
                model.addAttribute("msg", msg);
                model.addAttribute("message", "Belum terdapat daftar gaji.");
            }
            else{
                for (GajiModel gaji : listGaji) {
                    Integer totalPendapatan = gajiService.getTotalPendapatan(gaji);
                    listTotalPendapatan.add(totalPendapatan);
                }

                HashMap<String, String> listUserGaji = new HashMap<>();
                for(GajiModel gajiUser : listGaji) {
                    listUserGaji.put(gajiUser.getUserGaji().getId(), gajiUser.getUserGaji().getUsername());
                }

                String msg = "ada gaji";
                model.addAttribute("msg", msg);
                model.addAttribute("listTotalPendapatan", listTotalPendapatan);
                model.addAttribute("listGaji", listGaji);
                model.addAttribute("listUserGaji", listUserGaji);
            }
        }
        else {
            GajiModel gajiKaryawan = gajiService.getGajiByUser(user);
            List<GajiModel> listGajiKaryawan = new ArrayList<>();
            listGajiKaryawan.add(gajiKaryawan);

            if (gajiKaryawan==null){
                String msg = "tidak ada gaji";
                model.addAttribute("msg", msg);
                model.addAttribute("message", "Anda belum memiliki gaji.");
            }
            else{
                Integer totalPendapatan = gajiService.getTotalPendapatan(gajiKaryawan);
                listTotalPendapatan.add(totalPendapatan);

                HashMap<String, String> listUserGaji = new HashMap<>();
                listUserGaji.put(user.getId(), user.getUsername());

                String msg = "ada gaji";
                model.addAttribute("msg", msg);
                model.addAttribute("listTotalPendapatan", listTotalPendapatan);
                model.addAttribute("listGaji", listGajiKaryawan);
                model.addAttribute("listUserGaji", listUserGaji);
            }
        }

        return "view-all-gaji";
    }

    @GetMapping("/gaji/{idGaji}")
    public String detailGaji(
            @PathVariable Integer idGaji, Model model
    ){
        GajiModel gaji = gajiService.getGajiById(idGaji);
        model.addAttribute("gaji",gaji);
        model.addAttribute("penyetuju", "Belum ada penyetuju");
        model.addAttribute("training", gajiService.pernahTraining(gaji));
        model.addAttribute("totalBonus", gajiService.getTotalBonus(gaji));
        model.addAttribute("totalLembur", gajiService.getTotalLembur(gaji));

        if(gaji.getPenyetuju() != null){
            model.addAttribute("penyetuju", gaji.getPenyetuju().getUsername());
        }
        return "view-detail-gaji";
    }

    @GetMapping("/gaji/hapus/{idGaji}")
    public String deleteGaji(
            @PathVariable Integer idGaji,
            Model model
    ) {
        GajiModel gaji = gajiService.getGajiById(idGaji);
        model.addAttribute("gaji", gaji);
        gajiService.deleteGaji(gaji);
        return "hapus-gaji";
    }

    @GetMapping("/gaji/ubah/{idGaji}")
    public String changeGajiFormPage(
            @PathVariable Integer idGaji,
            Model model
    ) {
        GajiModel gaji = gajiService.getGajiById(idGaji);
        List<UserModel> listUser = userService.getUserList();

        model.addAttribute("gaji", gaji);
        model.addAttribute("listUser", listUser);
        return "form-ubah-gaji";
    }

    @PostMapping("/gaji/ubah/{idGaji}")
    public String changeGajiSubmit(
            @PathVariable Integer idGaji,
            @ModelAttribute GajiModel gaji,
            Model model
    ) {
        UserModel userPengajuUpdate = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        List<UserModel> listUser = userService.getUserList();
        model.addAttribute("listUser", listUser);

        if (userPengajuUpdate.getId().equals(gaji.getUserGaji().getId())) {
            String msgStatus = "Gagal";
            model.addAttribute("msgStatus", msgStatus);
            model.addAttribute("msg", "Anda tidak dapat mengubah data gaji anda sendiri.");
            model.addAttribute("gaji", gaji);
        }
        else {
            String msgStatus = "Berhasil";
            GajiModel gajiUpdated = gajiService.updateGaji(gaji);
            model.addAttribute("gaji", gajiUpdated);
            model.addAttribute("msgStatus", msgStatus);
        }

        return "ubah-gaji";
    }

}
