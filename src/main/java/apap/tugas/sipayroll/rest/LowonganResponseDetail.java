package apap.tugas.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LowonganResponseDetail {
    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
