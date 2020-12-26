package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.GajiModel;
import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user){
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public UserModel findUserByUserName(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public boolean matchNewAndOldPassword(String newPassword, String oldPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(newPassword, oldPassword);
    }

    @Override
    public List<UserModel> getUserList() {
        return userDb.findAll();
    }

    @Override
    public List<UserModel> findAll() { return userDb.findAll(); }

    @Override
    public Optional<UserModel> findUserByUUID(String id) {
        return userDb.findById(id);
    }
}