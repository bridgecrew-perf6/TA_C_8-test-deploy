package apap.tugas.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LowonganDetail {
    @JsonProperty("divisi")
    private String divisi;

    @JsonProperty("posisi")
    private String posisi;

    @JsonProperty("jumlah")
    private Integer jumlah;

    @JsonProperty("jenisLowongan")
    private JenisLowonganDetail jenisLowongan;

    @JsonProperty("eksternalUser")
    private String eksternalUser;

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public JenisLowonganDetail getJenisLowongan() {
        return jenisLowongan;
    }

    public void setJenisLowongan(JenisLowonganDetail jenisLowongan) {
        this.jenisLowongan = jenisLowongan;
    }

    public String getEksternalUser() {
        return eksternalUser;
    }

    public void setEksternalUser(String eksternalUser) {
        this.eksternalUser = eksternalUser;
    }
}
