package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.CatalogoVehiculoDto;
import com.restapi.apirestmoviles.service.CatalogoVehiculoService;
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
@RequestMapping("/vehicleCatalog")
@CrossOrigin(origins = "*")
public class CatalogoVehiculoController {

    @Autowired
    private CatalogoVehiculoService catalogoVehiculoService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Retrieve all available vehicles in the catalog")
    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        try {
            List<CatalogoVehiculoDto> vehicles = catalogoVehiculoService.getAllVehicles();
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Retrieve a vehicle by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        try {
            CatalogoVehiculoDto vehicle = catalogoVehiculoService.getVehicleById(id);
            return ResponseEntity.ok(vehicle);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new vehicle in the catalog")
    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody String vehicleJson) {
        try {
            CatalogoVehiculoDto vehicleDto = convierteDatos.obtenerDatos(vehicleJson, CatalogoVehiculoDto.class);
            CatalogoVehiculoDto createdVehicle = catalogoVehiculoService.createVehicle(vehicleDto);
            return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an existing vehicle")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @RequestBody String vehicleJson) {
        try {
            CatalogoVehiculoDto vehicleDto = convierteDatos.obtenerDatos(vehicleJson, CatalogoVehiculoDto.class);
            CatalogoVehiculoDto updatedVehicle = catalogoVehiculoService.updateVehicle(id, vehicleDto);
            return ResponseEntity.ok(updatedVehicle);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a vehicle from the catalog")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        try {
            catalogoVehiculoService.deleteVehicle(id);
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
