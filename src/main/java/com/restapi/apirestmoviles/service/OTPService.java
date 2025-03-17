package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.OTP;
import com.restapi.apirestmoviles.model.OTPDto;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.OTPRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

//     Metodo para generar código OTP aleatorio
    private String generateOTPCode() {
        Random random = new Random();
        int otpCode = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(otpCode);
    }

    // Convertir de Entidad a DTO
    private OTPDto convertToDto(OTP otp) {
        return new OTPDto(otp.getId(), otp.getUsuario().getId(), otp.getCodigo(), otp.getFechaExpiracion());
    }

    // Convertir de DTO a Entidad
    private OTP convertToEntity(OTPDto otpDto, Usuario usuario) {
        OTP otp = new OTP();
        otp.setUsuario(usuario);
        otp.setCodigo(otpDto.codigo());
        otp.setFechaExpiracion(otpDto.fechaExpiracion());
        return otp;
    }

    public OTPDto generateOTP(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + usuarioId));

        // Borrar OTPs previos antes de generar uno nuevo
        otpRepository.deleteByUsuarioId(usuarioId);

        // Crear y guardar el nuevo OTP
        OTP otp = new OTP();
        otp.setUsuario(usuario);
        otp.setCodigo(generateOTPCode());
        otp.setFechaExpiracion(LocalDateTime.now().plusMinutes(5)); // Expira en 5 minutos

        OTP savedOtp = otpRepository.save(otp);
        return convertToDto(savedOtp);
    }

    public boolean verifyOTP(Long usuarioId, String codigo) {
        Optional<OTP> otpOptional = otpRepository.findByUsuarioIdAndCodigoAndFechaExpiracionAfter(usuarioId, codigo, LocalDateTime.now());

        if (otpOptional.isPresent()) {
            otpRepository.deleteByUsuarioId(usuarioId); // Eliminar OTP después de su uso
            return true;
        } else {
            return false;
        }
    }
}
