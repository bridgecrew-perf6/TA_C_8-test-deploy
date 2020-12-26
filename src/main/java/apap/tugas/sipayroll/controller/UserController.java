package apap.tugas.sipayroll.controller;

import apap.tugas.sipayroll.model.*;
import apap.tugas.sipayroll.rest.LaporanDetail;
import apap.tugas.sipayroll.rest.PegawaiDetail;
import apap.tugas.sipayroll.service.RoleService;
import apap.tugas.sipayroll.service.UserRestService;
import apap.tugas.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRestService userRestService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUserSubmit(@ModelAttribute UserModel user, Model model){
        userService.addUser(user);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/tambah")
    public String addUserForm(Model model){
        model.addAttribute("listRole", roleService.findAll());
        return "form-add-user-to-sipegawai";
    }

    @PostMapping("/tambah")
    public String addUserPegawaiSubmit(
            @ModelAttribute BonusModel bonus,
            String username,
            String password,
            String role,
            String nama,
            String tempatLahir,
            String tanggalLahir,
            String alamat,
            String noTelepon,
            Model model) throws ParseException {
        String msg = "";
        // Menambah ke DB internal SIPAYROLL
        if (userService.findUserByUserName(username) == null){
            RoleModel roleUser = roleService.getById(Integer.parseInt(role)).get();
            UserModel userModel = new UserModel();
            userModel.setRole(roleUser);
            userModel.setUsername(username);
            userModel.setPassword(password);
            userService.addUser(userModel);

            // Panggil addedUser diatas untuk diretrieve kembali
            UserModel addedUser = userService.findUserByUserName(username);

            // POST to SI-Pegawai
            PegawaiDetail requestBody = new PegawaiDetail();
            requestBody.setUsername(username);
            requestBody.setNama(nama);
            requestBody.setNoTelepon(noTelepon);
            requestBody.setTempatLahir(tempatLahir);
            requestBody.setTanggalLahir(new SimpleDateFormat("yyyy-MM-dd").parse(tanggalLahir));
            requestBody.setAlamat(alamat);
            requestBody.setRoleId(Integer.parseInt(role));

            model.addAttribute("user", addedUser);

            userRestService.postUser(requestBody);
            return "add-user-to-si-pegawai";
        }else{
            msg = "Username tersebut tidak dapat digunakan";
            model.addAttribute("listRole", roleService.findAll());
            model.addAttribute("msg", msg);
            return "form-add-user-to-sipegawai";
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    private String viewProfile(Model model){
        UserModel user = userService.findUserByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        String usernameUser = user.getUsername();
        Map<String,String> profile;

        Map<String, Object> allProfile = userRestService.getAllUserProfile();
        ArrayList allListUser = (ArrayList) allProfile.get("result");

        ArrayList<String> listUsername = new ArrayList<String>();
        for(int i = 0; i < allListUser.size(); i++){
            LinkedHashMap<String, Object> ab = (LinkedHashMap<String, Object>) allListUser.get(i);
            listUsername.add((String)ab.get("username"));
        }

        if(listUsername.contains(usernameUser)){
            profile = userRestService.getUserProfile(usernameUser);
        }else {
            profile = null;
        }

        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        return "view-profile";
    }

}
