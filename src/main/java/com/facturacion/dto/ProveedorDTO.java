package com.facturacion.dto;

import com.facturacion.entity.Proveedor;

public record ProveedorDTO(
    String NIT,
    String nombre,
    String direccion,
    String telefono
) {

    public ProveedorDTO(Proveedor proveedor) {
        this(proveedor.getNIT(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getTelefono());
    }

    public Proveedor toEntity() {
        return Proveedor.builder()
            .NIT(this.NIT)
            .nombre(this.nombre)
            .direccion(this.direccion)
            .telefono(this.telefono)
            .estado("activo")
            .build();
    }

}
