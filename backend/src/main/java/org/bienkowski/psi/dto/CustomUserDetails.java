package org.bienkowski.psi.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Integer idUsr;
    private final String email;
    private final String name;
    private final String surname;

    public CustomUserDetails(Integer idUsr, String email, String name, String surname, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.idUsr = idUsr;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
