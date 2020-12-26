package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.repository.GajiDb;
import apap.tugas.sipayroll.rest.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class DataGajiRestServiceImpl implements DataGajiRestService {

//    private final WebClient webClient;

    @Autowired
    private GajiDb gajiDb;

//    public DataGajiRestServiceImpl(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl(Setting.gajiUrl).build();
//    }

    @Override
    public List<HashMap<String, Object>> retrieveListGajiKaryawanOld() {
        List<GajiModel> listGaji = gajiDb.findAll();
        if(!listGaji.isEmpty()) {
            List<HashMap<String, Object>> listGajiKaryawanTua = new ArrayList<HashMap<String, Object>>();
            for(GajiModel gaji : listGaji) {
                Date date = new Date();
                LocalDate tanggalHariIni = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Integer bulanIni = tanggalHariIni.getMonthValue();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(gaji.getTanggalMasuk());
                Integer bulanMasuk = calendar.get(Calendar.MONTH);

                Integer selisihTahun = Year.now().getValue() - calendar.get(Calendar.YEAR);

                if(selisihTahun > 2 ) {
                    HashMap<String, Object> temp = new HashMap<String, Object>();
                    temp.put("username", gaji.getUserGaji().getUsername());
                    temp.put("gaji", gaji.getGajiPokok());
                    temp.put("tahun", selisihTahun);
                    listGajiKaryawanTua.add(temp);
                }
                if(selisihTahun == 2 ) {
                    if(bulanIni < bulanMasuk) {
                        throw new NoSuchElementException();
                    }
                    HashMap<String, Object> temp = new HashMap<String, Object>();
                    temp.put("username", gaji.getUserGaji().getUsername());
                    temp.put("gaji", gaji.getGajiPokok());
                    temp.put("tahun", selisihTahun);
                    listGajiKaryawanTua.add(temp);
                }
            }
//            if (listGajiKaryawanTua.isEmpty()) {
//                HashMap<String, Object> temp = new HashMap<String, Object>();
//                temp.put("Error", "Tidak ada karyawan yang telah bekerja lebih dari 2 tahun");
//                listGajiKaryawanTua.add(temp);
//            }
            return listGajiKaryawanTua;
        }else {
            throw new NoSuchElementException();
        }

    }
}
