package com.facturacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

    Optional<Rol> findByNombre(String nombre);

}