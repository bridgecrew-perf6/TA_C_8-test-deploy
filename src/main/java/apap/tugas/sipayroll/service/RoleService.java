package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.RoleModel;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleModel> findAll();

    Optional<RoleModel> getByNama(String nama);

    Optional<RoleModel> getById(Integer id);
}
