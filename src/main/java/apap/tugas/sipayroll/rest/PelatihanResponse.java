package apap.tugas.sipayroll.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PelatihanResponse {
    private String status;
    private String message;
    private List<PelatihanUser> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PelatihanUser> getResult() {
        return result;
    }

    public void setResult(List<PelatihanUser> result) {
        this.result = result;
    }
}
