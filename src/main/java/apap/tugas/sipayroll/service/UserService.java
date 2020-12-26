package apap.tugas.sipayroll.service;

import apap.tugas.sipayroll.model.UserModel;

import java.util.List;
import java.util.Optional;


public interface UserService {
    UserModel addUser(UserModel user);
    String encrypt(String password);
    UserModel findUserByUserName(String username);
    boolean matchNewAndOldPassword(String newPassword, String oldPassword);

    // Dua Method untuk mendapatkan daftar semua User
    List<UserModel> getUserList();
    List<UserModel> findAll();

    // Method mendapatkan user by uuid
    Optional<UserModel> findUserByUUID(String id);
}
