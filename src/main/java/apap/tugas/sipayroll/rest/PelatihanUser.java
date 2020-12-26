package apap.tugas.sipayroll.rest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PelatihanUser {
    private Integer idPelatihan;
    private String namaPelatihan;
    private Date tanggalMulai;
    private Date tanggalSelesai;

    public Integer getIdPelatihan() {
        return idPelatihan;
    }

    public void setIdPelatihan(Integer idPelatihan) {
        this.idPelatihan = idPelatihan;
    }

    public String getNamaPelatihan() {
        return namaPelatihan;
    }

    public void setNamaPelatihan(String namaPelatihan) {
        this.namaPelatihan = namaPelatihan;
    }

    public Date getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(Date tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public Date getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(Date tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }
}
