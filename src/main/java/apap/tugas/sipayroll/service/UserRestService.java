package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.RoleModel;
import apap.tugas.sipayroll.rest.PegawaiDetail;
import org.json.JSONObject;

import java.util.Map;

public interface UserRestService {
    String postUser(PegawaiDetail requestBody);
    Map<String, Object> getAllUserProfile();
    Map<String, String> getUserProfile(String username);
}
