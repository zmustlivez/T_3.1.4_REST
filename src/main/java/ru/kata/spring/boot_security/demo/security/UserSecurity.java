package ru.kata.spring.boot_security.demo.security;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.stream.Collectors;
public class UserSecurity implements UserDetails {//proxy User
    private final User user;

    public UserSecurity(User user) {
        this.user = user;
    }


//@Fetch(FetchMode.JOIN)

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(e -> new RoleSecurity(e))
                .collect(Collectors.toList());
        //TODO вместо new RoleSecurity можно использовать SimpleGrantedAuthority(e.getRolename)
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
