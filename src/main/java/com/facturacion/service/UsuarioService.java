package com.facturacion.service;

import com.facturacion.dto.ClienteDTO;
import com.facturacion.dto.ProveedorDTO;
import com.facturacion.entity.Cliente;
import com.facturacion.entity.Proveedor;
import com.facturacion.entity.Usuario;
import com.facturacion.repository.ClienteRepository;
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
    private final ClienteRepository clienteRepository;

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

    public Optional<Proveedor> proveedorActivoPorNIT(String NIT) {
        
        return this.proveedorRepository.findByEstadoAndNIT("activo", NIT);
    }

    public List<Proveedor> proveedoresActivosPorNombre(String nombre) {

        return this.proveedorRepository.findByEstadoAndNombreContaining("activo", nombre);
    }

    public String actualizarProveedorActivo(String NIT, ProveedorDTO proveedorDTO) {
        
        Optional<Proveedor> proveedor = this.proveedorRepository.findByEstadoAndNIT("activo", NIT);
        if (proveedor.isPresent()) {
            proveedor.get().setNombre(proveedorDTO.nombre());
            proveedor.get().setDireccion(proveedorDTO.direccion());
            proveedor.get().setTelefono(proveedorDTO.telefono());
            this.proveedorRepository.save(proveedor.get());
            return NIT;
        } else {
            return "0000";
        }
    }

    public List<Cliente> clientesActivos() {
        return this.clienteRepository.findByEstado("activo");
    }

    public Cliente crearCliente(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> clienteActivoPorIdentificacion(String identificacion) {
        return this.clienteRepository.findByEstadoAndIdentificacion("activo", identificacion);
    }

    public List<Cliente> clientesActivosPorNombre(String nombre) {
        return this.clienteRepository.findByEstadoAndNombreContaining("activo", nombre);
    }

    public String actualizarClienteActivo(String identificacion, ClienteDTO clienteDTO) {

        Optional<Cliente> cliente = this.clienteRepository.findByEstadoAndIdentificacion("activo", identificacion);
        if (cliente.isPresent()) {
            cliente.get().setCorreo(clienteDTO.correo());
            cliente.get().setNombre(clienteDTO.nombre());
            cliente.get().setDireccion(clienteDTO.direccion());
            this.clienteRepository.save(cliente.get());
            return identificacion;
        } else {
            return "0000";
        }

    }
}
