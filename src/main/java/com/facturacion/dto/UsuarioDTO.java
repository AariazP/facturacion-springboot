package com.facturacion.dto;

import java.util.List;

import com.facturacion.entity.Usuario;

public record UsuarioDTO(
    String rol,
    List<String> modulos
) {

    public UsuarioDTO(String rol, List<String> modulos) {
        this.rol = rol;
        this.modulos = modulos;
    }

    public UsuarioDTO(Usuario usuario) {
        this(usuario.getRol().getNombre(), usuario.getRol().getModulos().stream().map(modulo -> modulo.getNombreModulo()).toList());
    }

}
