package com.kids_clothing.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReposne {
    private Long id;

    private String username;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

}
