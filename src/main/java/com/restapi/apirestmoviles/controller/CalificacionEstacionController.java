package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.CalificacionEstacionDto;
import com.restapi.apirestmoviles.service.CalificacionEstacionService;
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
@RequestMapping("/ratings")
@CrossOrigin(origins = "*")
public class CalificacionEstacionController {

    @Autowired
    private CalificacionEstacionService calificacionEstacionService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Add a new rating for a charging station")
    @PostMapping
    public ResponseEntity<?> addCalificacion(@RequestBody String ratingJson) {
        try {
            CalificacionEstacionDto calificacionDto = convierteDatos.obtenerDatos(ratingJson, CalificacionEstacionDto.class);
            CalificacionEstacionDto createdCalificacion = calificacionEstacionService.addCalificacion(calificacionDto);
            return new ResponseEntity<>(createdCalificacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get ratings for a specific charging station")
    @GetMapping("/station/{id}")
    public ResponseEntity<?> getCalificacionesByEstacion(@PathVariable Long id) {
        try {
            List<CalificacionEstacionDto> calificaciones = calificacionEstacionService.getCalificacionesByEstacion(id);
            return ResponseEntity.ok(calificaciones);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a rating by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalificacion(@PathVariable Long id) {
        try {
            calificacionEstacionService.deleteCalificacion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Map<String, String>> errorResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.status(status).body(response);
    }
}
