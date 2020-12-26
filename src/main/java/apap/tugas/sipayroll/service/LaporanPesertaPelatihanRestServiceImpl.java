package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.rest.Setting;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class LaporanPesertaPelatihanRestServiceImpl implements LaporanPesertaPelatihanRestService{
    private final WebClient webClient;

    public LaporanPesertaPelatihanRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder
                .baseUrl(Setting.laporanPesertaPelatihanUrl).build();
    }

    @Override
    public Mono<String> getLaporanPesertaPelatihan(){
        MultiValueMap<String, String> temp = new LinkedMultiValueMap<>();
        return this.webClient.get().uri(uriBuilder -> uriBuilder.queryParams(temp).build()).retrieve().bodyToMono(String.class);
    }
}
