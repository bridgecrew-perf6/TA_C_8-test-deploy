package apap.tugas.sipayroll.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="jenisBonus")
public class JenisBonusModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name="nama", nullable = false)
    @Size(max=200)
    private String nama;

    @OneToMany(mappedBy = "jenisBonus", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<BonusModel> listBonus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<BonusModel> getListBonus() {
        return listBonus;
    }

    public void setListBonus(List<BonusModel> listBonus) {
        this.listBonus = listBonus;
    }
}
