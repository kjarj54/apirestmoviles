package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.PublicacionDto;
import com.restapi.apirestmoviles.service.PublicacionService;
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
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Create a new post")
    @PostMapping
    public ResponseEntity<?> createPublicacion(@RequestBody String publicacionJson) {
        try {
            PublicacionDto publicacionDto = convierteDatos.obtenerDatos(publicacionJson, PublicacionDto.class);
            PublicacionDto createdPost = publicacionService.createPublicacion(publicacionDto);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    @Operation(summary = "Retrieve all posts")
    @GetMapping
    public ResponseEntity<?> getAllPublicaciones() {
        try {
            List<Map<String, Object>> publicaciones = publicacionService.getAllPublicaciones();
            return ResponseEntity.ok(publicaciones);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Retrieve a post by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicacionById(@PathVariable Long id) {
        try {
            Map<String, Object> publicacion = publicacionService.getPublicacionById(id);
            return ResponseEntity.ok(publicacion);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a post by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublicacion(@PathVariable Long id) {
        try {
            publicacionService.deletePublicacion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Utility method for error responses
    private ResponseEntity<Map<String, String>> errorResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.status(status).body(response);
    }
}
