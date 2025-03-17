package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.UsuarioDto;
import com.restapi.apirestmoviles.service.UsuarioService;
import com.restapi.apirestmoviles.util.IConvierteDatos;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody String usuarioJson) {
        try {
            UsuarioDto usuarioDto = convierteDatos.obtenerDatos(usuarioJson, UsuarioDto.class);
            UsuarioDto createdUsuario = usuarioService.createUsuario(usuarioDto);
            return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {
        try {
            List<UsuarioDto> usuarios = usuarioService.getAllUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id) {
        try {
            UsuarioDto usuario = usuarioService.getUsuarioById(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody String usuarioJson) {
        try {
            UsuarioDto usuarioDto = convierteDatos.obtenerDatos(usuarioJson, UsuarioDto.class);
            UsuarioDto updatedUsuario = usuarioService.updateUsuario(id, usuarioDto);
            return ResponseEntity.ok(updatedUsuario);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get user by email")
    @GetMapping("/findByCorreo/{correo}")
    public ResponseEntity<?> getUsuarioByCorreo(@PathVariable String correo) {
        try {
            UsuarioDto usuario = usuarioService.getUsuarioByCorreo(correo);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Returns a greeting message")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

    private ResponseEntity<Map<String, String>> errorResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.status(status).body(response);
    }
}
