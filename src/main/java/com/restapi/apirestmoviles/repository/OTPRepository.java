package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByUsuarioIdAndCodigoAndFechaExpiracionAfter(Long usuarioId, String codigo, LocalDateTime fechaActual);

    void deleteByUsuarioId(Long usuarioId);
}
