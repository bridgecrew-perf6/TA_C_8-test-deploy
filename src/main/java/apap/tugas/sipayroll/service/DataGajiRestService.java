package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.GajiModel;

import java.util.HashMap;
import java.util.List;

public interface DataGajiRestService {
    List<HashMap<String, Object>> retrieveListGajiKaryawanOld();
}
