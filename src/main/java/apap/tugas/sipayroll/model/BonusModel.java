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
@Table(name="bonus")
public class BonusModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="jumlahBonus", nullable = false)
    private Integer jumlahBonus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idJenisBonus", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisBonusModel jenisBonus;

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

    public Integer getJumlahBonus() {
        return jumlahBonus;
    }

    public void setJumlahBonus(Integer jumlahBonus) {
        this.jumlahBonus = jumlahBonus;
    }

    public JenisBonusModel getJenisBonus() {
        return jenisBonus;
    }

    public void setJenisBonus(JenisBonusModel jenisBonus) {
        this.jenisBonus = jenisBonus;
    }

    public GajiModel getGaji() {
        return gaji;
    }

    public void setGaji(GajiModel gaji) {
        this.gaji = gaji;
    }

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="tanggalDiberikan", nullable = false)
    private Date tanggalDiberikan;

    public Date getTanggalDiberikan() {
        return tanggalDiberikan;
    }

    public void setTanggalDiberikan(Date tanggalDiberikan) {
        this.tanggalDiberikan = tanggalDiberikan;
    }
}
