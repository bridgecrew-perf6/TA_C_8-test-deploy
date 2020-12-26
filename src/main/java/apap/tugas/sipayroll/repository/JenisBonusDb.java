package apap.tugas.sipayroll.repository;

import apap.tugas.sipayroll.model.BonusModel;
import apap.tugas.sipayroll.model.JenisBonusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JenisBonusDb extends JpaRepository<JenisBonusModel, Integer> {
    List<JenisBonusModel> findAll();
    Optional<JenisBonusModel> findById(Integer id);
}