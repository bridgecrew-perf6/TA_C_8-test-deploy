package apap.tugas.sipayroll.repository;

import apap.tugas.sipayroll.model.BonusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonusDb extends JpaRepository<BonusModel, Long> {
    Optional<BonusModel> findById(Long id);
}