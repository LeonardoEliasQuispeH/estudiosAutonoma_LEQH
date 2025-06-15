package com.example.prueba.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.prueba.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Optional<Usuario> findByUsuarioClave(String usuario, String clave);

    @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.clave = :clave")
    Optional<Usuario> findByUsuarioClave(@Param("usuario") String usuario, @Param("clave") String clave);

}