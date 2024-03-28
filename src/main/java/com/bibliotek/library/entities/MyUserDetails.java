package com.bibliotek.library.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MyUserDetails implements UserDetails {

    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails (User usuario){
        this.password = usuario.getPassword();
        this.username = usuario.getUsername();
        this.authorities = this.getPermisos(usuario.getRoles());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private Collection<? extends GrantedAuthority> getPermisos(Set<Role> roles)  {
        List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
        for (Role r: roles){
            permisos.add(new SimpleGrantedAuthority("ROLE_" + r.getRole().name()));
        }
        return permisos;
    }
}
