package com.facturacion.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idProducto;
    @Column(unique = true) 
    private String codigo;
    private String nombre;
    private Double precio;
    private Double stock;

    private Boolean activo;
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "producto")
    private List<DetFactura> detfacturas;

    @ManyToOne
    private Proveedor proveedor;

}
