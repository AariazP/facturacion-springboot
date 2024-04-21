package com.facturacion.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facturacion.dto.ProveedorDTO;
import com.facturacion.dto.UsuarioDTO;
import com.facturacion.entity.Proveedor;
import com.facturacion.entity.Usuario;
import com.facturacion.service.UsuarioService;
import com.facturacion.util.ResponseMessageDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UsuarioController {

    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioService usuarioService;

    @GetMapping("/login")
    public ResponseEntity<ResponseMessageDTO<UsuarioDTO>> login(@RequestParam String email, @RequestParam String password) {
        
        if (email != null && password != null) {

            Optional<Usuario> usuarioAutenticado = this.usuarioService.buscarPorEmailYContrasenia(email, password);
            if (usuarioAutenticado.isPresent()) {
                return ResponseEntity.ok(new ResponseMessageDTO<UsuarioDTO>(HttpStatus.OK.value(), "Usuario autenticado exitosamente", new UsuarioDTO(usuarioAutenticado.get())));
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.UNAUTHORIZED.value(), "Credenciales incorrectas", null));
            }

        } else {
            // Falta el usuario o la contraseña
            LOGGER.info("Falta el usuario o la contraseña");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el usuario o la contraseña", null));
        }
    }

    @GetMapping("/cajero/proveedores")
    public ResponseEntity<ResponseMessageDTO<List<ProveedorDTO>>> proveedores(@RequestParam String email) {

        if (email != null) {
            
            if (this.usuarioService.tieneAcceso(email, "crud-proveedor")) {
                List<Proveedor> proveedores = this.usuarioService.proveedoresActivos();
                if (proveedores.isEmpty()) {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.NOT_FOUND.value(), "No hay proveedores", null));
                } else {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.OK.value(), "Lista de proveedores", proveedores.stream().map(ProveedorDTO::new).toList()));
                }
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.FORBIDDEN.value(), "No tiene acceso a este recurso", null));
            }

        } else {
            // Falta el email
            LOGGER.info("Falta el email");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el email", null));
        }
    }

    @PostMapping("/cajero/proveedores")
    public ResponseEntity<ResponseMessageDTO<ProveedorDTO>> crearProveedor(@RequestBody ProveedorDTO proveedorDTO, @RequestParam String email) {

        if (email != null) {
            
            if (this.usuarioService.tieneAcceso(email, "crud-proveedor")) {
                Proveedor proveedor = proveedorDTO.toEntity();
                Proveedor proveedorCreado = this.usuarioService.crearProveedor(proveedor);
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.CREATED.value(), "Proveedor creado exitosamente", new ProveedorDTO(proveedorCreado)));
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.FORBIDDEN.value(), "No tiene acceso a este recurso", null));
            }

        } else {

            LOGGER.info("Falta el email");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el email", null));
        }

    }
}

