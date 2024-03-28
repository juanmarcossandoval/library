package com.bibliotek.library.services;

import com.bibliotek.library.entities.MyUserDetails;
import com.bibliotek.library.entities.User;
import com.bibliotek.library.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuario = this.userRepo.findByUsername(username);
        if(usuario.isEmpty()) throw new UsernameNotFoundException("No existe un socio con el nombre de usuario: " + username);
        return new MyUserDetails(usuario.get());
    }

}
