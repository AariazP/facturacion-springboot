package com.facturacion.service;

import com.facturacion.entity.Proveedor;
import com.facturacion.entity.Usuario;
import com.facturacion.repository.ProveedorRepository;
import com.facturacion.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProveedorRepository proveedorRepository;

    public Optional<Usuario> buscarPorEmailYContrasenia(String email, String password) {
        return this.usuarioRepository.findByEmailAndPassword(email, password);
    }

    public List<Proveedor> proveedoresActivos() {
        return this.proveedorRepository.findByEstado("activo");
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        return this.proveedorRepository.save(proveedor);
    }

    public boolean tieneAcceso(String email, String modulo) {
        
        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return usuario.get().getRol().getModulos().stream().anyMatch(moduloRol -> moduloRol.getNombreModulo().equals(modulo));
        }
        return false;

    }

}
