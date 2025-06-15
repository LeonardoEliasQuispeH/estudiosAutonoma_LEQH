package com.example.prueba.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.entities.Producto;
import com.example.prueba.repositories.ProductoRepository;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    //-----------------------------------------------------------------------------

    // Endpoint GET para obtener todos los productos
    @GetMapping("/listar")
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    //-----------------------------------------------------------------------------
    
    // Endpoint POST para crear un nuevo producto
    @PostMapping("/crear")
    public ResponseEntity<?> crearProducto(@RequestBody Producto nuevoProducto) {
        try {
            Producto productoGuardado = productoRepository.save(nuevoProducto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al guardar el producto: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //-----------------------------------------------------------------------------

    // Endpoint GET para buscar un producto por su ID
    /*@GetMapping("/buscar/{id}")
    public Producto getProductoById(@PathVariable Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }*/

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        return productoRepository.findById(id)
            .<ResponseEntity<?>>map(producto -> ResponseEntity.ok(producto))
            .orElseGet(() -> {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Producto no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            });
    }


    //-----------------------------------------------------------------------------

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);

        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setCantidad(productoActualizado.getCantidad());
            producto.setTralla(productoActualizado.getTralla());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setEstado(productoActualizado.getEstado());
            productoRepository.save(producto);
            return ResponseEntity.ok(producto);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Producto no se actualizo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //-----------------------------------------------------------------------------

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> deleteProducto(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            response.put("mensaje", "Producto eliminado correctamente");
            return ResponseEntity.ok(response); // 200 OK con mensaje
        } else {
            response.put("mensaje", "Producto no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404 con mensaje
        }
    }

}
