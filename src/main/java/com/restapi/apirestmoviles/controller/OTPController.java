package com.restapi.apirestmoviles.controller;

import com.restapi.apirestmoviles.model.OTPDto;
import com.restapi.apirestmoviles.service.OTPService;
import com.restapi.apirestmoviles.util.IConvierteDatos;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/otp")
@CrossOrigin(origins = "*")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private IConvierteDatos convierteDatos;

    @Operation(summary = "Generate a new OTP code for a user")
    @PostMapping("/generate")
    public ResponseEntity<OTPDto> generateOTP(@RequestBody String otpJson) {
        Map<String, Long> request = convierteDatos.obtenerDatos(otpJson, Map.class);
        Long usuarioId = request.get("usuarioId");
        OTPDto otpDto = otpService.generateOTP(usuarioId);
        return new ResponseEntity<>(otpDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Verify OTP code")
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyOTP(@RequestBody String otpJson) {
        Map<String, Object> request = convierteDatos.obtenerDatos(otpJson, Map.class);
        Long usuarioId = Long.valueOf(request.get("usuarioId").toString());
        String codigo = request.get("codigo").toString();

        boolean isValid = otpService.verifyOTP(usuarioId, codigo);
        return ResponseEntity.ok(Map.of("valid", isValid));
    }
}
