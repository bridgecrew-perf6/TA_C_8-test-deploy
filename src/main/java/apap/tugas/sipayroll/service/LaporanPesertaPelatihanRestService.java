package apap.tugas.sipayroll.service;

import reactor.core.publisher.Mono;

public interface LaporanPesertaPelatihanRestService{
    Mono<String> getLaporanPesertaPelatihan();
}
