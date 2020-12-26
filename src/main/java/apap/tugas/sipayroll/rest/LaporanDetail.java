package apap.tugas.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LaporanDetail {
    @JsonProperty("username")
    private String username;

    @JsonProperty("jumlah-pelatihan")
    private Integer jumlahPelatihan;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getJumlahPelatihan() {
        return jumlahPelatihan;
    }

    public void setJumlahPelatihan(Integer jumlahPelatihan) {
        this.jumlahPelatihan = jumlahPelatihan;
    }
}
