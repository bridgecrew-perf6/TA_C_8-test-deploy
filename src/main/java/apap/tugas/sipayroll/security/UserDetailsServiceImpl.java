package apap.tugas.sipayroll.security;

import apap.tugas.sipayroll.model.UserModel;
import apap.tugas.sipayroll.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userDb.findByUsername(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getNama()));
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
