package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.RutaDto;
import com.restapi.apirestmoviles.service.RutaService;
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
@RequestMapping("/routes")
@CrossOrigin(origins = "*")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Plan a new route")
    @PostMapping
    public ResponseEntity<?> planRoute(@RequestBody String rutaJson) {
        try {
            RutaDto rutaDto = convierteDatos.obtenerDatos(rutaJson, RutaDto.class);
            RutaDto plannedRoute = rutaService.planRoute(rutaDto);
            return new ResponseEntity<>(plannedRoute, HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get planned routes by a user")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getRoutesByUser(@PathVariable Long id) {
        try {
            List<RutaDto> routes = rutaService.getRoutesByUser(id);
            return ResponseEntity.ok(routes);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get route details by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRouteById(@PathVariable Long id) {
        try {
            RutaDto route = rutaService.getRouteById(id);
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a planned route")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoute(@PathVariable Long id) {
        try {
            rutaService.deleteRoute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<Map<String, String>> errorResponse(String message, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", message);
        return ResponseEntity.status(status).body(response);
    }
}
