package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.EstacionCargaDto;
import com.restapi.apirestmoviles.service.EstacionCargaService;
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
@RequestMapping("/chargingStations")
@CrossOrigin(origins = "*")
public class EstacionCargaController {

    @Autowired
    private EstacionCargaService estacionCargaService;

    @Autowired
    private IConvierteDatos convierteDatos;    @Operation(summary = "Retrieve all charging stations")
    @GetMapping
    public ResponseEntity<?> getAllStations() {
        try {
            List<EstacionCargaDto> stations = estacionCargaService.getAllStations();
            return ResponseEntity.ok(stations);
        } catch (Exception e) {
            System.err.println("Error in getAllStations: " + e.getMessage());
            e.printStackTrace();
            return errorResponse("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Retrieve a charging station by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStationById(@PathVariable Long id) {
        try {
            EstacionCargaDto station = estacionCargaService.getStationById(id);
            return ResponseEntity.ok(station);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new charging station")
    @PostMapping
    public ResponseEntity<?> createStation(@RequestBody String stationJson) {
        try {
            EstacionCargaDto stationDto = convierteDatos.obtenerDatos(stationJson, EstacionCargaDto.class);
            EstacionCargaDto createdStation = estacionCargaService.createStation(stationDto);
            return new ResponseEntity<>(createdStation, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    @Operation(summary = "Update a charging station")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody String stationJson) {
        try {
            EstacionCargaDto stationDto = convierteDatos.obtenerDatos(stationJson, EstacionCargaDto.class);
            EstacionCargaDto updatedStation = estacionCargaService.updateStation(id, stationDto);
            return ResponseEntity.ok(updatedStation);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    @Operation(summary = "Update charging station availability")
    @PutMapping("/{id}/availability")
    public ResponseEntity<?> updateStationAvailability(@PathVariable Long id, @RequestBody String availabilityJson) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> availabilityData = convierteDatos.obtenerDatos(availabilityJson, Map.class);
            Boolean availability = (Boolean) availabilityData.get("disponibilidad");
            
            if (availability == null) {
                return errorResponse("Missing 'disponibilidad' field", HttpStatus.BAD_REQUEST);
            }
            
            EstacionCargaDto updatedStation = estacionCargaService.updateStationAvailability(id, availability);
            return ResponseEntity.ok(updatedStation);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a charging station")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        try {
            estacionCargaService.deleteStation(id);
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
