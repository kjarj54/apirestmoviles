package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.UsuarioDto;
import com.restapi.apirestmoviles.service.UsuarioService;
import com.restapi.apirestmoviles.util.IConvierteDatos;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private IConvierteDatos convierteDatos;


    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<UsuarioDto> createUsuario(@RequestBody String usuarioJson) {
        UsuarioDto usuarioDto = convierteDatos.obtenerDatos(usuarioJson, UsuarioDto.class);
        UsuarioDto createdUsuario = usuarioService.createUsuario(usuarioDto);
        return new ResponseEntity<>(createdUsuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsuarios() {
        List<UsuarioDto> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> getUsuarioById(@PathVariable Long id) {
        UsuarioDto usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable Long id, @RequestBody String usuarioJson) {
        UsuarioDto usuarioDto = convierteDatos.obtenerDatos(usuarioJson, UsuarioDto.class);
        UsuarioDto updatedUsuario = usuarioService.updateUsuario(id, usuarioDto);
        return ResponseEntity.ok(updatedUsuario);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        // Add delete method in UsuarioService first
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Returns a greeting message")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

}
