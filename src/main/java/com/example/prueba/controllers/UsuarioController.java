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


import com.example.prueba.entities.Usuario;
import com.example.prueba.repositories.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    // Constructor que inyecta el repositorio
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //-----------------------------------------------------------------------------

    // Endpoint GET para obtener todos los usuarios
    @GetMapping("/listar")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    //-----------------------------------------------------------------------------
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario nuevoUsuario) {
        try {
            Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al crear el usuario");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //-----------------------------------------------------------------------------

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
            .<ResponseEntity<?>>map(usuario -> ResponseEntity.ok(usuario))
            .orElseGet(() -> {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Usuario no encontrado con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            });
    }

    //-----------------------------------------------------------------------------

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setUsuario(usuarioActualizado.getUsuario());
            usuario.setClave(usuarioActualizado.getClave());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setCorreo(usuarioActualizado.getCorreo());
            usuario.setDni(usuarioActualizado.getDni());
            usuario.setCelular(usuarioActualizado.getCelular());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(usuario);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Usuario no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    //-----------------------------------------------------------------------------

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            response.put("mensaje", "Usuario eliminado correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    //-----------------------------------------------------------------------------

    


}
