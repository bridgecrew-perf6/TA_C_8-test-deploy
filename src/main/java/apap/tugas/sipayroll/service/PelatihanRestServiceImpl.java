package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.rest.PelatihanResponse;
import apap.tugas.sipayroll.rest.PelatihanUser;
import apap.tugas.sipayroll.rest.Setting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PelatihanRestServiceImpl implements PelatihanRestService{
    private final WebClient webClient;
    public PelatihanRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.pelatihanUrl).build();
    }
//    @Override
//    public PelatihanResponse getPelatihan(String username) {
//        return this.webClient.get().uri("/api/pelatihan/getListPelatihan/" + username).retrieve().bodyToMono(PelatihanResponse.class).block();
//    }
    @Override
    public PelatihanUser[] getPelatihan(String username) {
        ObjectMapper mapper = new ObjectMapper();
        String json = this.webClient.get().uri("/api/pelatihan/getListPelatihan/" + username).retrieve().bodyToMono(String.class).block();
        PelatihanUser[] listPelatihan = new PelatihanUser[0];
        try {
            listPelatihan = mapper.readValue(json, PelatihanUser[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listPelatihan;
    }
}
