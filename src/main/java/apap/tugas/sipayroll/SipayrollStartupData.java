package apap.tugas.sipayroll;

import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.repository.RoleDb;
import apap.tugas.sipayroll.service.RoleService;
import apap.tugas.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class SipayrollStartupData implements ApplicationRunner {
    @Autowired
    UserService userService;

    @Autowired
    RoleDb roleDb;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userService.findUserByUserName("admin")==null){
            UserModel user = new UserModel();
            user.setUsername("admin");
            user.setPassword("admin1234");
            user.setRole(roleDb.findById(1).get());

            userService.addUser(user);
        }
    }
}