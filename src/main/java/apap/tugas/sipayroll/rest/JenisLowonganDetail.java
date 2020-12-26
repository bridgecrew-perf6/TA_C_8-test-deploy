package apap.tugas.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JenisLowonganDetail {
    @JsonProperty("id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
