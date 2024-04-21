package com.facturacion.dto;

import com.facturacion.entity.Cliente;

public record ClienteDTO(
    String identificacion,
    String nombre,
    String direccion,
    String correo,
    String fechaCreacion
) {

    public ClienteDTO(Cliente cliente) {
        this(cliente.getIdentificacion(), cliente.getNombre(), cliente.getDireccion(), cliente.getCorreo(), cliente.getFechaCreacion().toString());
    }

    public Cliente toEntity() {
        return Cliente.builder()
            .identificacion(this.identificacion)
            .nombre(this.nombre)
            .direccion(this.direccion)
            .correo(this.correo)
            .fechaCreacion(java.time.LocalDate.parse(this.fechaCreacion))
            .estado("activo")
            .build();
    }

}
