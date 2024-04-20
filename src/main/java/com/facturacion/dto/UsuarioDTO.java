package com.facturacion.dto;

import java.util.List;

import com.facturacion.entity.Usuario;

public record UsuarioDTO(
    String email,
    List<String> modulos
) {

    public UsuarioDTO(String email, List<String> modulos) {
        this.email = email;
        this.modulos = modulos;
    }

    public UsuarioDTO(Usuario usuario) {
        this(usuario.getEmail(), usuario.getRol().getModulos().stream().map(modulo -> modulo.getNombreModulo()).toList());
    }

}
