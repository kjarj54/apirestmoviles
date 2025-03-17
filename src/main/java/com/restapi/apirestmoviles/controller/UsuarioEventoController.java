package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.UsuarioEventoDto;
import com.restapi.apirestmoviles.service.UsuarioEventoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userEvents")
@CrossOrigin(origins = "*")
public class UsuarioEventoController {

    @Autowired
    private UsuarioEventoService usuarioEventoService;

    @Operation(summary = "Register a user for an event")
    @PostMapping
    public ResponseEntity<?> registerForEvent(@RequestParam Long eventoId, @RequestParam Long usuarioId) {
        try {
            UsuarioEventoDto registeredEvent = usuarioEventoService.registerForEvent(eventoId, usuarioId);
            return new ResponseEntity<>(registeredEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get participants of an event")
    @GetMapping("/event/{id}")
    public ResponseEntity<?> getParticipantsByEvent(@PathVariable Long id) {
        try {
            List<UsuarioEventoDto> participants = usuarioEventoService.getParticipantsByEvent(id);
            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Cancel a user's participation in an event")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelParticipation(@PathVariable Long id) {
        try {
            usuarioEventoService.cancelParticipation(id);
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
