package org.bienkowski.psi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="tasks")

@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tsk")
    private int idTsk;

    @Column
    private String value;

    @ManyToOne
    @JoinColumn(name = "id_usr", nullable = false)
    private User user;

}

