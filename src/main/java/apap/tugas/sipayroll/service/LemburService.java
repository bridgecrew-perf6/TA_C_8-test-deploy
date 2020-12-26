package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.LemburModel;
import apap.tugas.sipayroll.model.UserModel;

import java.util.Date;
import java.util.List;

public interface LemburService {

    // Method untuk menambah Lembur
    LemburModel addLembur(LemburModel lembur);

    // Method untuk mendapatkan semua lembur
    List<LemburModel> getLemburList();

    // Method untuk verifikasi hari lembur
    boolean dateLemburValid(Date waktuMulai, Date waktuSelesai);

    // Method untuk verifikasi ID pegawai
    UserModel getUserLembur(LemburModel lembur);

    // Method untuk mendapatkan lembur dengan id
    LemburModel getLemburById(Integer idLembur);

    // Method untuk mengubah lembur
    LemburModel updateLembur (LemburModel lembur);

    // Method untuk menghapus by IdLembur
    LemburModel deleteLembur (LemburModel lembur);

    // Method untuk menghitung jumlah kompensasi
    Integer getTotalKompensasi (LemburModel lembur);

    // Method untuk mendapatkan list lembur dari user
    List<LemburModel> getLemburByUser(UserModel user);

}
