package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.ComentarioPublicacionDto;
import com.restapi.apirestmoviles.service.ComentarioPublicacionService;
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
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
public class ComentarioPublicacionController {

    @Autowired
    private ComentarioPublicacionService comentarioService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Add a comment to a post")
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody String commentJson) {
        try {
            ComentarioPublicacionDto commentDto = convierteDatos.obtenerDatos(commentJson, ComentarioPublicacionDto.class);
            ComentarioPublicacionDto createdComment = comentarioService.addComment(commentDto);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all comments for a post")
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long id) {
        try {
            List<ComentarioPublicacionDto> comments = comentarioService.getCommentsByPost(id);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return errorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a comment")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            comentarioService.deleteComment(id);
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
