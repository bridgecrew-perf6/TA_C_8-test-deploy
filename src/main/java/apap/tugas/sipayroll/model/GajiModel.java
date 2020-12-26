package apap.tugas.sipayroll.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gaji")
public class GajiModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="gajiPokok", nullable = false)
    private Integer gajiPokok;

    @NotNull
    @Column(name="statusPersetujuan", nullable = false)
    private Integer statusPersetujuan;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="tanggalMasuk", nullable = false)
    private Date tanggalMasuk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuidPenyetuju", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel penyetuju;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuidPengaju", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel pengaju;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuidUser", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private UserModel userGaji;

    @OneToMany(mappedBy = "gaji", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<LemburModel> listLembur;

    @OneToMany(mappedBy = "gaji", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<BonusModel> listBonus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(Integer gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public Integer getStatusPersetujuan() {
        return statusPersetujuan;
    }

    public void setStatusPersetujuan(Integer statusPersetujuan) {
        this.statusPersetujuan = statusPersetujuan;
    }

    public Date getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(Date tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public UserModel getPenyetuju() {
        return penyetuju;
    }

    public void setPenyetuju(UserModel penyetuju) {
        this.penyetuju = penyetuju;
    }

    public UserModel getPengaju() {
        return pengaju;
    }

    public void setPengaju(UserModel pengaju) {
        this.pengaju = pengaju;
    }

    public UserModel getUserGaji() {
        return userGaji;
    }

    public void setUserGaji(UserModel userGaji) {
        this.userGaji = userGaji;
    }

    public List<LemburModel> getListLembur() {
        return listLembur;
    }

    public void setListLembur(List<LemburModel> listLembur) {
        this.listLembur = listLembur;
    }

    public List<BonusModel> getListBonus() {
        return listBonus;
    }

    public void setListBonus(List<BonusModel> listBonus) {
        this.listBonus = listBonus;
    }
}