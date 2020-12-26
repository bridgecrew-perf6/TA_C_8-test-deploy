package apap.tugas.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PelatihanResponse {
    private List<PelatihanUser> pelatihan;

    public List<PelatihanUser> getPelatihan() {
        return pelatihan;
    }

    public void setPelatihan(List<PelatihanUser> pelatihan) {
        this.pelatihan = pelatihan;
    }
}
