package com.sarfaraz.management.service;

//import com.sarfaraz.management.model.AppUserDetails;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
/*
@Service
public class AppUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;
    private PasswordEncoder encoder;

    @Autowired
    public AppUserDetailsService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }


    public UserDetails loadUserByUsername1(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findByUsername(username);
        opt.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
        return opt.map(AppUserDetails::new).get();
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findByUsername(username);
        opt.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
        return new org.springframework.security.core.userdetails.User(opt.get().getEmail(), opt.get().getPassword(), getAuthorities(opt.get()));

    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
//        System.out.print("returning UserDetails : " + user);
//        for (String role : userRoles) {
//            System.out.println(role);
//        }
        return AuthorityUtils.createAuthorityList(userRoles);
    }

}
*/