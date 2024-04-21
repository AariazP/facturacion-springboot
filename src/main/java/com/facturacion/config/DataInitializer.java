package com.facturacion.config;

import com.facturacion.entity.Modulo;
import com.facturacion.entity.Rol;
import com.facturacion.entity.Usuario;
import com.facturacion.repository.ModuloRepository;
import com.facturacion.repository.RolRepository;
import com.facturacion.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final ModuloRepository moduloRepository;
    private final RolRepository rolRepository;

    @PostConstruct
    public void init() {
        // si encuenta algún modulo con ese nombre no lo crea
        moduloRepository.findByNombreModulo("crud-cliente").ifPresentOrElse(
            modulo -> {},
            () -> {
                moduloRepository.save(
                    Modulo.builder()
                        .nombreModulo("crud-cliente")
                        .build()
                );
            }
        );
        moduloRepository.findByNombreModulo("crud-proveedor").ifPresentOrElse(
            modulo -> {},
            () -> {
                moduloRepository.save(
                    Modulo.builder()
                        .nombreModulo("crud-proveedor")
                        .build()
                );
            }
        );
        // si encuenta algún rol con ese nombre no lo crea
        rolRepository.findByNombre("administrador").ifPresentOrElse(
            rol -> {},
            () -> {
                Rol rol = new Rol("administrador");
                rolRepository.save(rol);
            }
        );

        Rol rol2 = null;

        rolRepository.findByNombre("cajero").ifPresentOrElse(
            rol -> {},
            () -> {
                
                Rol rol = new Rol("cajero");

                // rol2 = rol;

                Modulo modulo1 = moduloRepository.findByNombreModulo("crud-cliente").get();
                Modulo modulo2 = moduloRepository.findByNombreModulo("crud-proveedor").get();

                rol.addModule(modulo1);
                rol.addModule(modulo2);

                // modulo1.getRoles().add(rol);
                // modulo2.getRoles().add(rol);

                rolRepository.save(rol);
                
            }
        );
        // si encuenta algún usuario con ese email no lo crea
        usuarioRepository.findByEmail("admin@gmail.com").ifPresentOrElse(
            usuario -> {},
            () -> {
                usuarioRepository.save(
                    Usuario.builder()
                        .email("admin@gmail.com")
                        .password("admin")
                        .rol(rolRepository.findByNombre("administrador").get())
                        .build()
                );
            }
        );
        usuarioRepository.findByEmail("cajero@gmail.com").ifPresentOrElse(
            usuario -> {},
            () -> {
                usuarioRepository.save(
                    Usuario.builder()
                        .email("cajero@gmail.com")
                        .password("cajero")
                        .rol(rolRepository.findByNombre("cajero").get())
                        .build()
                );
            }
        );
    }

}
