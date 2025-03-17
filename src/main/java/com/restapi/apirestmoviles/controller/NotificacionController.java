package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.NotificacionDto;
import com.restapi.apirestmoviles.service.NotificacionService;
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
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Create a new notification for a user")
    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody String notificacionJson) {
        try {
            NotificacionDto notificacionDto = convierteDatos.obtenerDatos(notificacionJson, NotificacionDto.class);
            NotificacionDto createdNotification = notificacionService.createNotification(notificacionDto);
            return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get notifications by user ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getNotificationsByUser(@PathVariable Long id) {
        try {
            List<NotificacionDto> notifications = notificacionService.getNotificationsByUser(id);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Mark a notification as read")
    @PutMapping("/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        try {
            NotificacionDto updatedNotification = notificacionService.markAsRead(id);
            return ResponseEntity.ok(updatedNotification);
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
