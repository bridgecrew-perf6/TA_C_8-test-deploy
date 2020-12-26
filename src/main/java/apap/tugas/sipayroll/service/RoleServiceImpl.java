package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.RoleModel;
import apap.tugas.sipayroll.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() { return roleDb.findAll(); }

    @Override
    public Optional<RoleModel> getByNama(String nama) {
        return roleDb.findByNama(nama);
    }

    @Override
    public Optional<RoleModel> getById(Integer id) {
        return roleDb.findById(id);
    }
}