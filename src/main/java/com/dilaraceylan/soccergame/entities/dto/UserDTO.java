package com.dilaraceylan.soccergame.entities.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dilaraceylan.soccergame.entities.concrete.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author dilara.ceylan
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    private Long roleId;

    private String roleName;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDTO(Long id,
                   String username,
                   String email,
                   String password,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public UserDTO(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static UserDTO build(User user) {
        //    List<GrantedAuthority> authorities = user.getRoles().stream()
        //        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        //        .collect(Collectors.toList());

        List<GrantedAuthority> authorities = null;
        if (Objects.nonNull(user) && Objects.nonNull(user.getRole())) {
            authorities = new ArrayList<GrantedAuthority>(
                            Arrays.asList(new SimpleGrantedAuthority(user.getRole().getName())));
        }

        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
                        authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDTO user = (UserDTO) o;
        return Objects.equals(id, user.id);
    }
}
