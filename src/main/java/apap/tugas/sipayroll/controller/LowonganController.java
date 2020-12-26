package apap.tugas.sipayroll.controller;

import apap.tugas.sipayroll.rest.LowonganDetail;
import apap.tugas.sipayroll.rest.LowonganResponseDetail;
import apap.tugas.sipayroll.service.LowonganRestService;
import apap.tugas.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LowonganController {
    @Autowired
    LowonganRestService lowonganRestService;

    @Autowired
    UserService userService;

    @GetMapping("/lowongan/tambah")
    public String addLowonganFormPage (Model model) {
        model.addAttribute("lowongan", new LowonganDetail());
        return "form-tambah-lowongan";
    }

    @PostMapping("/lowongan/tambah")
    public String addLowonganSubmit(
            @ModelAttribute LowonganDetail lowongan,
            Model model) {
        LowonganResponseDetail lowonganResponse = lowonganRestService.createLowongan(lowongan).block();

        model.addAttribute("msg", lowonganResponse.getMessage());
        model.addAttribute("lowongan", new LowonganDetail());

        return "tambah-lowongan";
    }
}
