package com.facturacion.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // ---------------------------------------------------- PRIMER MODULO - CRUD PROVEEDORES -------------------------------------------------------------------

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

    @GetMapping("/proveedores")
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

    @PostMapping("/proveedores")
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

    @GetMapping("/proveedor/{NIT}")
    public ResponseEntity<ResponseMessageDTO<ProveedorDTO>> proveedor(@RequestParam String email, @PathVariable String NIT) {

        if (email != null) {
            
            if (this.usuarioService.tieneAcceso(email, "crud-proveedor")) {
                Optional<Proveedor> proveedor = this.usuarioService.proveedorActivoPorNIT(NIT);
                if (proveedor.isPresent()) {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.OK.value(), "Proveedor encontrado", new ProveedorDTO(proveedor.get())));
                } else {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.NOT_FOUND.value(), "Proveedor no encontrado", null));
                }
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.FORBIDDEN.value(), "No tiene acceso a este recurso", null));
            }

        } else {
            LOGGER.info("Falta el email");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el email", null));
        }
    }

    @GetMapping("/proveedores/{nombre}")
    public ResponseEntity<ResponseMessageDTO<List<ProveedorDTO>>> proveedoresPorNombre(@RequestParam String email, @PathVariable String nombre) {

        if (email != null) {
            
            if (this.usuarioService.tieneAcceso(email, "crud-proveedor")) {
                List<Proveedor> proveedores = this.usuarioService.proveedoresActivosPorNombre(nombre);
                if (proveedores.isEmpty()) {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.NOT_FOUND.value(), "No hay proveedores", null));
                } else {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.OK.value(), "Lista de proveedores", proveedores.stream().map(ProveedorDTO::new).toList()));
                }
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.FORBIDDEN.value(), "No tiene acceso a este recurso", null));
            }

        } else {
            LOGGER.info("Falta el email");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el email", null));
        }
    }

    @PutMapping("/proveedor/{NIT}")
    public ResponseEntity<ResponseMessageDTO<String>> actualizarProveedor(@RequestBody ProveedorDTO proveedorDTO, @RequestParam String email, @PathVariable String NIT) {

        if (email != null) {
            
            if (this.usuarioService.tieneAcceso(email, "crud-proveedor")) {
                Optional<Proveedor> proveedor = this.usuarioService.proveedorActivoPorNIT(NIT);
                if (proveedor.isPresent()) {
                    String NITProveedorActualizado = this.usuarioService.actualizarProveedorActivo(NIT, proveedorDTO);
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.OK.value(), "Proveedor actualizado exitosamente", NITProveedorActualizado));
                } else {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.NOT_FOUND.value(), "Proveedor no encontrado", null));
                }
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.FORBIDDEN.value(), "No tiene acceso a este recurso", null));
            }

        } else {
            LOGGER.info("Falta el email");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el email", null));
        }
    }

    @DeleteMapping("/proveedor/{NIT}")
    public ResponseEntity<ResponseMessageDTO<String>> eliminarProveedor(@RequestParam String email, @PathVariable String NIT) {

        if (email != null) {
            
            if (this.usuarioService.tieneAcceso(email, "crud-proveedor")) {
                Optional<Proveedor> proveedor = this.usuarioService.proveedorActivoPorNIT(NIT);
                if (proveedor.isPresent()) {
                    proveedor.get().setEstado("inactivo");
                    this.usuarioService.crearProveedor(proveedor.get());
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.OK.value(), "Proveedor eliminado exitosamente", NIT));
                } else {
                    return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.NOT_FOUND.value(), "Proveedor no encontrado", null));
                }
            } else {
                return ResponseEntity.ok(new ResponseMessageDTO<>(HttpStatus.FORBIDDEN.value(), "No tiene acceso a este recurso", null));
            }

        } else {
            LOGGER.info("Falta el email");
            return ResponseEntity.badRequest().body(new ResponseMessageDTO<>(HttpStatus.BAD_REQUEST.value(), "Falta el email", null));
        }
    }

}

