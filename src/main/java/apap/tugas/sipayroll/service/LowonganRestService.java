package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.rest.LowonganDetail;
import apap.tugas.sipayroll.rest.LowonganResponseDetail;
import reactor.core.publisher.Mono;

public interface LowonganRestService {
    Mono<LowonganResponseDetail> createLowongan(LowonganDetail lowongan);
}
