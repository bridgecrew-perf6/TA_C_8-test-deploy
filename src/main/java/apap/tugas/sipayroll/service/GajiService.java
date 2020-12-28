package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.UserModel;

import java.util.List;

public interface GajiService {
    // Method untuk mendapatkan daftar semua Gaji
    List<GajiModel> getGajiList();

    // Method untuk menambah Gaji
    void addGaji(GajiModel gaji);

    boolean pernahTraining(GajiModel gaji);

    GajiModel getGajiById(Integer idGaji);

    Integer updateStatusGaji(GajiModel gaji);

    // Method untuk mendapatkan Gaji dari UUID User tertentu
    GajiModel getGajiByUser(UserModel user);

    // Method untuk menghitung Total Pendapatan
    Integer getTotalPendapatan(GajiModel gaji);

    // Method untuk menghapus Gaji
    void deleteGaji(GajiModel gaji);

    // Method untuk mengubah data Gaji
    GajiModel updateGaji(GajiModel gaji);

    Integer getTotalBonus(GajiModel gaji);
    Integer getTotalLembur(GajiModel gaji);

    // Method untuk add Gaji user yang diterima dari SIPELATIHAN
    void addGajiUserPelatihan (GajiModel gaji);

}
