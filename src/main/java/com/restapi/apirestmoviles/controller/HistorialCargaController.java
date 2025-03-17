package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.HistorialCargaDto;
import com.restapi.apirestmoviles.service.HistorialCargaService;
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
@RequestMapping("/chargeHistory")
@CrossOrigin(origins = "*")
public class HistorialCargaController {

    @Autowired
    private HistorialCargaService historialCargaService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Register a new charge")
    @PostMapping
    public ResponseEntity<?> registerCharge(@RequestBody String historialCargaJson) {
        try {
            HistorialCargaDto historialCargaDto = convierteDatos.obtenerDatos(historialCargaJson, HistorialCargaDto.class);
            HistorialCargaDto registeredCharge = historialCargaService.registerCharge(historialCargaDto);
            return new ResponseEntity<>(registeredCharge, HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get charge history by user")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getChargeHistoryByUser(@PathVariable Long id) {
        try {
            List<HistorialCargaDto> chargeHistory = historialCargaService.getChargeHistoryByUser(id);
            return ResponseEntity.ok(chargeHistory);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get charge history by station")
    @GetMapping("/station/{id}")
    public ResponseEntity<?> getChargeHistoryByStation(@PathVariable Long id) {
        try {
            List<HistorialCargaDto> chargeHistory = historialCargaService.getChargeHistoryByStation(id);
            return ResponseEntity.ok(chargeHistory);
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
