package com.example.prueba.Dto;

import java.time.LocalDate;

public interface MovimientoNombreProductoDTO {
    Long getIdMovimiento();
    Long getIdProducto();
    String getNombreProducto();
    String getTipoMovimiento();
    LocalDate getFechaMovimiento();  // <--- directamente como datetime
    Integer getCantidadMovimiento();
}
