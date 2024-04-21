package com.facturacion.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idCliente;
    private String nombreModulo;

    @ManyToMany(// fetch = FetchType.LAZY
      /* cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      } */
    )
    @JoinTable(name = "modulo_rol",
            joinColumns = @JoinColumn(name = "id_modulo"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private List<Rol> roles;

}
