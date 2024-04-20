package com.facturacion.service;

import com.facturacion.entity.Factura;
import com.facturacion.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CabFacturaService {

    private final FacturaRepository cabFacturaRepository;

    public CabFacturaService(FacturaRepository cabFacturaRepository) {
        this.cabFacturaRepository = cabFacturaRepository;
    }

    public Factura guardarCabFactura(Factura cabFactura) {
        return this.cabFacturaRepository.save(cabFactura);
    }

    public List<Factura> obtenerTodas( ) {
        Iterable<Factura> cabFacturas = this.cabFacturaRepository.findAll();
        List<Factura> listaCabFacturas = new ArrayList<>();
        cabFacturas.forEach(listaCabFacturas::add);
        return listaCabFacturas;
    }

    public Optional<Factura> obtenerPorId(Integer id) {
        return this.cabFacturaRepository.findById(id);
    }

    public void eliminarPorId(Integer id) {
        this.cabFacturaRepository.deleteById(id);
    }



    public Integer generaFactura() {
        return this.cabFacturaRepository.generaFactura();
    }
}
