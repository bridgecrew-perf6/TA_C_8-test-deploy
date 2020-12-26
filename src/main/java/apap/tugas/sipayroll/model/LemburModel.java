package apap.tugas.sipayroll.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="lembur")
public class LemburModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name="waktuMulai", nullable = false)
    private Date waktuMulai;


    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name="waktuSelesai", nullable = false)
    private Date waktuSelesai;
    @NotNull
    @Column(name="kompensasiPerJam", nullable = false)
    private Integer kompensasiPerJam;

    @NotNull
    @Column(name="statusPersetujuan", nullable = false)
    private Integer statusPersetujuan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idGaji", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private GajiModel gaji;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(Date waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public Date getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(Date waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public Integer getKompensasiPerJam() {
        return kompensasiPerJam;
    }

    public void setKompensasiPerJam(Integer kompensasiPerJam) {
        this.kompensasiPerJam = kompensasiPerJam;
    }

    public Integer getStatusPersetujuan() {
        return statusPersetujuan;
    }

    public void setStatusPersetujuan(Integer statusPersetujuan) {
        this.statusPersetujuan = statusPersetujuan;
    }

    public GajiModel getGaji() {
        return gaji;
    }

    public void setGaji(GajiModel gaji) {
        this.gaji = gaji;
    }
}
