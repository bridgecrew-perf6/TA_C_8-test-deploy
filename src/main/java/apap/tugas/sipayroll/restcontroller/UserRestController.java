package apap.tugas.sipayroll.restcontroller;

import apap.tugas.sipayroll.service.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    @Autowired
    UserRestService userRestService;

    @GetMapping(value = "/user/{username}")
    private Map<String,String> getProfile(@PathVariable String username){
        return userRestService.getUserProfile(username);
    }
}
