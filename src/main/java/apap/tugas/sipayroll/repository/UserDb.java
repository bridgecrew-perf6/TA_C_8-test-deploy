package apap.tugas.sipayroll.repository;

import apap.tugas.sipayroll.model.JenisBonusModel;
import apap.tugas.sipayroll.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserDb extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);
    List<UserModel> findAll();
}