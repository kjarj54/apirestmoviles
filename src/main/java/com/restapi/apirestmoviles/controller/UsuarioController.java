package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.UsuarioDto;
import com.restapi.apirestmoviles.service.OTPService;
import com.restapi.apirestmoviles.service.UsuarioService;
import com.restapi.apirestmoviles.util.IConvierteDatos;
import com.restapi.apirestmoviles.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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

    @Operation(summary = "Login user", security = @SecurityRequirement(name = "bearer-jwt"))
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String loginJson) {
        try {
            UsuarioDto credentials = convierteDatos.obtenerDatos(loginJson, UsuarioDto.class);

            if (credentials.correo() == null || credentials.contrasena() == null) {
                return errorResponse("Email and password are required", HttpStatus.BAD_REQUEST);
            }

            try {
                UsuarioDto usuario = usuarioService.getUsuarioByCorreo(credentials.correo());
                Boolean encodedPassword = passwordEncoder.matches(credentials.contrasena(), usuario.contrasena());
                if (usuario != null && encodedPassword) {
                    otpService.generateOTP(usuario.id());

                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "OTP has been sent to your email");
                    response.put("usuarioId", usuario.id());

                    return ResponseEntity.ok(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return errorResponse("Invalid credentials", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, "Authentication error", e);
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
