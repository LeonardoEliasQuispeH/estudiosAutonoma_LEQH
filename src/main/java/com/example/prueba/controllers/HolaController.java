package com.example.prueba.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.prueba.Dto.LoginRequest;
import com.example.prueba.entities.Usuario;
import com.example.prueba.repositories.UsuarioRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class HolaController {

    private final UsuarioRepository usuarioRepository;

    // Constructor que inyecta el repositorio
    public HolaController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @GetMapping("/")
    public String hola() {
        return "Hola mundo";
    }

    @GetMapping("/debug")
    public String debug() {
        return "Backend actualizado con /login";
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsuarioClave(
            loginRequest.getUsuario(), loginRequest.getClave()
        );

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return ResponseEntity.ok(usuario);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario o contraseña incorrectos");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
}
