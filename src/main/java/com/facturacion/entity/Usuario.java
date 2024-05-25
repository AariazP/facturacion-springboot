package com.facturacion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    private String username;
    private String password;
    private Integer intentosFallidos;
    private Byte bloqueado;

}
