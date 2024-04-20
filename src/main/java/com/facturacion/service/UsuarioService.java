package com.facturacion.service;

import com.facturacion.entity.Usuario;
import com.facturacion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> buscarPorEmailYContrasenia(String email, String password) {
        return this.usuarioRepository.findByEmailAndPassword(email, password);
    }

}
