package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.OTP;
import com.restapi.apirestmoviles.model.OTPDto;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.OTPRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    private String generateOTPCode() {
        Random random = new Random();
        int otpCode = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(otpCode);
    }

    private OTPDto convertToDto(OTP otp) {
        return new OTPDto(otp.getId(), otp.getUsuario().getId(), otp.getCodigo(), otp.getFechaExpiracion());
    }

    private OTP convertToEntity(OTPDto otpDto, Usuario usuario) {
        OTP otp = new OTP();
        otp.setUsuario(usuario);
        otp.setCodigo(otpDto.codigo());
        otp.setFechaExpiracion(otpDto.fechaExpiracion());
        return otp;
    }

    @Transactional
    public OTPDto generateOTP(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + usuarioId));

        otpRepository.deleteByUsuarioId(usuarioId);

        String otpCode = generateOTPCode();

        OTP otp = new OTP();
        otp.setUsuario(usuario);
        otp.setCodigo(otpCode);
        otp.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));

        OTP savedOtp = otpRepository.save(otp);

        emailService.sendOtpEmail(usuario.getCorreo(), otpCode);

        return convertToDto(savedOtp);
    }

    @Transactional
    public boolean verifyOTP(Long usuarioId, String codigo) {
        Optional<OTP> otpOptional = otpRepository.findByUsuarioIdAndCodigoAndFechaExpiracionAfter(usuarioId, codigo,
                LocalDateTime.now());

        if (otpOptional.isPresent()) {
            otpRepository.deleteByUsuarioId(usuarioId);
            return true;
        } else {
            return false;
        }
    }
}
