package apap.tugas.sipayroll.restcontroller;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.RoleModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.rest.BaseResponse;
import apap.tugas.sipayroll.rest.LaporanDetail;
import apap.tugas.sipayroll.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LaporanPesertaPelatihanRestController {
    @Autowired
    private LaporanPesertaPelatihanRestService laporanPesertaPelatihanRestService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GajiService gajiService;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private JenisBonusService jenisBonusService;

//    @PostMapping(value = "peserta/pelatihan")
//    public Mono<String> getLaporanPesertaPelatihanRestService(
//            @RequestParam(value = "")String tes,Model model){
//        return laporanPesertaPelatihanRestService.getLaporanPesertaPelatihan();
//    }

    @PostMapping(value = "/peserta/pelatihan")
    private BaseResponse<BonusModel> createBonus(@Valid @RequestBody LaporanDetail laporan, BindingResult bindingResult)
            throws ParseException {
        BaseResponse<BonusModel> response = new BaseResponse<BonusModel>();
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "RequestBody has invalid type or missing field"
            );
        }
        else {
            String username = laporan.getUsername();
            Integer jumlahPelatihan = laporan.getJumlahPelatihan();
            String password = "password";

            // User belum ada di database SI Payroll
            if (userService.findUserByUserName(username) == null){
                // Menambah user baru dari pelatihan berdasarkan username yang diterima ke DB internal SIPAYROLL
                RoleModel roleUser = roleService.getById(6).get(); // karyawan
                UserModel userPelatihan = new UserModel();
                GajiModel gajiUserPelatihan= new GajiModel();
                userPelatihan.setRole(roleUser);
                userPelatihan.setUsername(username);
                userPelatihan.setPassword(password);
                userService.addUser(userPelatihan);

                UserModel kepalaDepartemen = userService.findUserByUserName("kepalabagian212");
                // Menambah gaji untuk user yang diterima dari SIPELATIHAN

                gajiUserPelatihan.setUserGaji(userService.findUserByUserName(username));
                gajiUserPelatihan.setGajiPokok(1000000);
                gajiUserPelatihan.setPengaju(kepalaDepartemen);
                gajiUserPelatihan.setTanggalMasuk(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-15"));
                gajiService.addGajiUserPelatihan(gajiUserPelatihan);

                userPelatihan.setGaji(gajiUserPelatihan);
                UserModel targetedUser = userService.findUserByUserName(username);
                GajiModel gajiTargetedUser = targetedUser.getGaji();

                // Menambah bonus untuk user yang diterima dari SIPELATIHAN
                BonusModel bonusUserPelatihan = new BonusModel();
                bonusUserPelatihan.setJumlahBonus(jumlahPelatihan * 150000);
                bonusUserPelatihan.setGaji(gajiTargetedUser);
                bonusUserPelatihan.setTanggalDiberikan(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-15"));
                bonusUserPelatihan.setJenisBonus(jenisBonusService.getBonusByIdBonus(3));
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(bonusUserPelatihan);
                bonusService.addBonus(bonusUserPelatihan);
                return response;
            }else{
                UserModel userLama = userService.findUserByUserName(username);
                BonusModel bonusUserPelatihan = new BonusModel();
                bonusUserPelatihan.setGaji(userLama.getGaji());
                bonusUserPelatihan.setJumlahBonus(jumlahPelatihan * 150000);
                bonusUserPelatihan.setTanggalDiberikan(new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-15"));
                bonusUserPelatihan.setJenisBonus(jenisBonusService.getBonusByIdBonus(3));
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(bonusUserPelatihan);
                bonusService.addBonus(bonusUserPelatihan);
                return response;
            }
        }
    }
}