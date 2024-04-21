package com.facturacion.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{

    List<Proveedor> findByEstado(String estado);

    Optional<Proveedor> findByEstadoAndNIT(String estado, String NIT);

    List<Proveedor> findByEstadoAndNombreContaining(String string, String nombre);

}
