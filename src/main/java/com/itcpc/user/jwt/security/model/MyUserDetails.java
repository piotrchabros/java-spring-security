package com.itcpc.user.jwt.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user == null) return null;
        return user.roles.stream().map(x ->
        { GrantedAuthority authority = () -> x.getName(); return authority; }
        ).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getPassword() {
        if(user == null) {
            return null;
        }
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if(user == null) {
            return null;
        }
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(user == null) {
            return false;
        }
        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(user == null) {
            return false;
        }
        return user.isEnabled();
    }
}
