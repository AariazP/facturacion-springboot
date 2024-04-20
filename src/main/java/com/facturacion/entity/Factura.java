package com.facturacion.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idFactura;
    private Integer numeroFactura;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private String subtotal;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private String iva;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private String total;


    @JsonManagedReference
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetFactura> detFactura;

    @ManyToOne
    private Usuario usuarioCreador;

    @ManyToOne
    private Cliente cliente;

}
