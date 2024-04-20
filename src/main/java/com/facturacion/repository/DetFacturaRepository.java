package com.facturacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.entity.DetFactura;

@Repository
public interface DetFacturaRepository extends JpaRepository<DetFactura, Integer>{

}
