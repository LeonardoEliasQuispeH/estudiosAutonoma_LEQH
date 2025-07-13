package com.example.prueba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prueba.entities.MovimientoProducto;

public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Long>{
    
    List<MovimientoProducto> findTop30ByOrderByFechaMovimientoDesc();
    
}
