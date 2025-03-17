package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.UsuarioEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioEventoRepository extends JpaRepository<UsuarioEvento, Long> {
    List<UsuarioEvento> findByEventoId(Long eventoId);
    boolean existsByEventoIdAndUsuarioId(Long eventoId, Long usuarioId);
}
