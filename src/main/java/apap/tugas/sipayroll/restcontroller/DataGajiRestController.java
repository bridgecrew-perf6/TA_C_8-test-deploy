package apap.tugas.sipayroll.restcontroller;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.service.DataGajiRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DataGajiRestController {
    @Autowired
    private DataGajiRestService dataGajiRestService;

    @GetMapping(value = "/gaji")
    private List<HashMap<String, Object>> retrieveGaji(){
        try{
            return dataGajiRestService.retrieveListGajiKaryawanOld();
        }catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tidak ada karyawan yang telah bekerja lebih dari 2 tahun");
        }
    }
}
