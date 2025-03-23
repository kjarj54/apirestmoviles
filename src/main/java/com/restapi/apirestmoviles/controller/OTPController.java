package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.OTPDto;
import com.restapi.apirestmoviles.model.UsuarioDto;
import com.restapi.apirestmoviles.service.OTPService;
import com.restapi.apirestmoviles.service.UsuarioService;
import com.restapi.apirestmoviles.util.IConvierteDatos;
import com.restapi.apirestmoviles.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/otp")
@CrossOrigin(origins = "*")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Generate a new OTP code for a user")
    @PostMapping("/generate")
    public ResponseEntity<OTPDto> generateOTP(@RequestBody String otpJson) {
        @SuppressWarnings("unchecked")
        Map<String, Long> request = (Map<String, Long>) convierteDatos.obtenerDatos(otpJson, Map.class);
        Long usuarioId = request.get("usuarioId");
        OTPDto otpDto = otpService.generateOTP(usuarioId);
        return new ResponseEntity<>(otpDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Verify OTP code and generate token if valid")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOTP(@RequestBody String otpJson) {
        Map<String, Object> request = convierteDatos.obtenerDatos(otpJson, Map.class);
        Long usuarioId = Long.valueOf(request.get("usuarioId").toString());
        String codigo = request.get("codigo").toString();

        boolean isValid = otpService.verifyOTP(usuarioId, codigo);

        if (isValid) {
            UsuarioDto usuario = usuarioService.getUsuarioById(usuarioId);
            String token = jwtUtil.generateToken(usuario.correo());

            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("token", token);
            response.put("id", usuario.id());
            response.put("correo", usuario.correo());
            response.put("nombre", usuario.nombre());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid or expired OTP"));
        }
    }
}
