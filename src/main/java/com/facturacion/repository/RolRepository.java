package com.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{

    

}
