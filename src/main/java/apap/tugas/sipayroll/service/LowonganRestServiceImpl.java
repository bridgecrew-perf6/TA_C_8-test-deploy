package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.rest.LowonganDetail;
import apap.tugas.sipayroll.rest.LowonganResponseDetail;
import apap.tugas.sipayroll.rest.Setting;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@Transactional
public class LowonganRestServiceImpl implements LowonganRestService {
    private final WebClient webClient;

    public LowonganRestServiceImpl(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.lowonganUrl)
                .defaultHeader("x-mock-match-request-body", "true")
                .build();

    }

    @Override
    public Mono<LowonganResponseDetail> createLowongan(LowonganDetail lowongan) {
            return webClient.post()
                    .uri("/rest/lowongan/add")
                    .body(Mono.just(lowongan), LowonganDetail.class)
                    .retrieve()
                    .bodyToMono(LowonganResponseDetail.class);
    }
}
