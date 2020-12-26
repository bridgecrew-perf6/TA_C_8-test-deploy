package apap.tugas.sipayroll.repository;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GajiDb extends JpaRepository<GajiModel, Integer> {
    GajiModel findByUserGaji(UserModel user);
    List<GajiModel> findAll();
}
