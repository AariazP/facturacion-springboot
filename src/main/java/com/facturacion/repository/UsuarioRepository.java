package com.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{

    

}
