package com.facturacion.config;

import com.facturacion.entity.Modulo;
import com.facturacion.entity.Rol;
import com.facturacion.entity.Usuario;
import com.facturacion.repository.ModuloRepository;
import com.facturacion.repository.RolRepository;
import com.facturacion.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final ModuloRepository moduloRepository;
    private final RolRepository rolRepository;

    @PostConstruct
    public void init() {
        moduloRepository.saveAll(
            List.of(
                // en estos dos modulos están todas las actividades dentro del alcance del proceso "gestionar proveedores y clientes"
                Modulo.builder()
                .nombreModulo("crud-cliente")
                .build(),
                Modulo.builder()
                .nombreModulo("crud-proveedor")
                .build()

                // cada uno debe agregar los modulos de su proceso de forma que el rol administrador o cajero pueda acceder al modulo
            )
        );
        rolRepository.saveAll(
            List.of(
                Rol.builder()
                .nombre("administrador")
                .modulos(
                    List.of(
                        // aqui van todos los modulos a los que tiene acceso el rol administrador
                    )
                )
                .build(),
                Rol.builder()
                .nombre("cajero")
                .modulos(
                    List.of(
                        // aqui van todos los modulos a los que tiene acceso el rol cajero
                        moduloRepository.findByNombreModulo("crud-cliente").get(),
                        moduloRepository.findByNombreModulo("crud-proveedor").get()
                    )
                )
                .build()
            )
        );
        usuarioRepository.saveAll(
            List.of(
                Usuario.builder()
                .email("admin@gmail.com")
                .password("admin")
                .rol(
                    rolRepository.findByNombre("administrador").get()
                )
                .build(),
                Usuario.builder()
                .email("cajero@gmail.com")
                .password("cajero")
                .rol(
                    rolRepository.findByNombre("cajero").get()
                )
                .build()
            )
        );
    }

}
