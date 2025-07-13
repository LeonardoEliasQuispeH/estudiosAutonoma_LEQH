package com.example.prueba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.prueba.Dto.MovimientoNombreProductoDTO;
import com.example.prueba.entities.MovimientoProducto;

public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Long>{
    
    List<MovimientoProducto> findTop30ByOrderByFechaMovimientoDesc();

    @Query(value = "SELECT " +
               "m.id_movimiento AS idMovimiento, " +
               "m.id_producto AS idProducto, " +
               "p.nombre AS nombreProducto, " +
               "m.tipo_movimiento AS tipoMovimiento, " +
               "m.fecha_movimiento AS fechaMovimiento, " +  // sin convertir
               "m.cantidad_movimiento AS cantidadMovimiento " +
               "FROM movimientoproducto m " +
               "INNER JOIN productos p ON m.id_producto = p.id",
       nativeQuery = true)
    List<MovimientoNombreProductoDTO> listarMovimientosNombreProducto();

    
}
