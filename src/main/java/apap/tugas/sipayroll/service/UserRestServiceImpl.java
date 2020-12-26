package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.RoleModel;
import apap.tugas.sipayroll.rest.PegawaiDetail;
import apap.tugas.sipayroll.rest.Setting;
import apap.tugas.sipayroll.repository.UserDb;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService{
    private final WebClient webClientForPost;
    private final WebClient webClient;


    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientForPost = webClientBuilder.baseUrl(Setting.sipegawaiUrl).build();
        this.webClient = webClientBuilder.baseUrl(Setting.sipegawaiUrl).build();
    }

    @Override
    public String postUser(PegawaiDetail requestBody) {
        System.out.println(requestBody);
        String jsonReqBody = requestBody.toString();
        System.out.println(jsonReqBody);

        return this.webClientForPost.post()
                .uri("/api/v1/pegawai")
                .header("Content-Type", "application/json")
                .syncBody(requestBody)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public Map<String, Object> getAllUserProfile() {
        Map<String, Object> allUserProfile = this.webClient.get().uri(Setting.getSipegawaiUrl).retrieve().bodyToMono(Map.class).block();
        return allUserProfile;
    }

    @Override
    public Map<String, String> getUserProfile(String username) {
        Map<String, String> userProfile = this.webClient.get().uri(Setting.getSipegawaiUrl + username).retrieve().bodyToMono(Map.class).block();
        return userProfile;
    }
}
