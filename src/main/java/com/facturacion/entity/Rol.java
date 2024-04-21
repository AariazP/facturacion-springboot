package com.facturacion.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idRol;

    @Column(unique = true)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "modulo_rol",
            joinColumns = @JoinColumn(name = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "id_modulo"))
    private List<Modulo> modulos;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    public Rol(String nombre, List<Modulo> modulos) {
        this.nombre = nombre;
        this.modulos = modulos;
    }

    public Rol(String nombre) {
        this.nombre = nombre;
        this.modulos = new ArrayList<>();
    }

    public void addModule(Modulo modulo) {
        this.modulos.add(modulo);
        // modulo.getRoles().add(this);
    }

}
