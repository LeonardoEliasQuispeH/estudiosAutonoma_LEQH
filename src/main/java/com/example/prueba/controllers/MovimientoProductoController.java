package com.example.prueba.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.entities.MovimientoProducto;
import com.example.prueba.repositories.MovimientoProductoRepository;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/movimientos")
public class MovimientoProductoController {

    private final MovimientoProductoRepository movimientoProductoRepository;

    public MovimientoProductoController(MovimientoProductoRepository movimientoProductoRepository) {
        this.movimientoProductoRepository = movimientoProductoRepository;
    }

    // GET /movimientos/listar
    @GetMapping("/listar")
    public List<MovimientoProducto> listarMovimientos() {
        return movimientoProductoRepository.findAll();
    }

    // POST: Crear un nuevo movimiento
    @PostMapping("/crear")
    public ResponseEntity<MovimientoProducto> crearMovimiento(@RequestBody MovimientoProducto movimiento) {
        if (movimiento.getFechaMovimiento() == null) {
            movimiento.setFechaMovimiento(LocalDate.now());
        }
        MovimientoProducto guardado = movimientoProductoRepository.save(movimiento);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping("/exportar-csv")
    public void exportarUltimos30MovimientosCSV(HttpServletResponse response) throws IOException {
        List<MovimientoProducto> movimientos = movimientoProductoRepository.findTop30ByOrderByFechaMovimientoDesc();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=movimientos.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID Movimiento,ID Producto,Tipo Movimiento,Fecha Movimiento,Cantidad Movimiento");

        for (MovimientoProducto mov : movimientos) {
            writer.printf("%d,%d,%s,%s,%d%n",
                    mov.getIdMovimiento(),
                    mov.getIdProducto(),
                    mov.getTipoMovimiento(),
                    mov.getFechaMovimiento(),
                    mov.getCantidadMovimiento()
            );
        }

        writer.flush();
        writer.close();
    }
    
}
