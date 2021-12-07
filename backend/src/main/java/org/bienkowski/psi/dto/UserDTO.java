package org.bienkowski.psi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private int idUsr;
    private String name;
    private String surname;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    private String token;
    private List<String> roles;

}
