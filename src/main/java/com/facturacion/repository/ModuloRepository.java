package com.facturacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer>{

    Optional<Modulo> findByNombreModulo(String nombreModulo);

}   
