package com.facturacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    List<Cliente> findByEstado(String estado);

    Optional<Cliente> findByEstadoAndIdentificacion(String estado, String identificacion);

    List<Cliente> findByEstadoAndNombreContaining(String estado, String nombre);

}
