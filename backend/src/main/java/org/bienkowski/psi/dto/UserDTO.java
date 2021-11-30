package org.bienkowski.psi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private int idUsr;
    private String name;
    private String surname;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;

}
