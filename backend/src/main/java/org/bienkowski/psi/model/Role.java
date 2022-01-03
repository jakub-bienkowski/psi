package org.bienkowski.psi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bienkowski.psi.enums.ERole;

import javax.persistence.*;

@Entity(name="roles")

@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private int idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole name;



}
