package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.rest.PelatihanResponse;
import apap.tugas.sipayroll.rest.PelatihanUser;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PelatihanRestService {
    PelatihanResponse getPelatihan(String username);
}
