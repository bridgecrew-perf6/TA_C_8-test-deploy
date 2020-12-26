package apap.tugas.sipayroll.repository;

import apap.tugas.sipayroll.model.LemburModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LemburDb extends JpaRepository<LemburModel, Integer> {

}
