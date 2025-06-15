package com.example.prueba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.prueba.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
