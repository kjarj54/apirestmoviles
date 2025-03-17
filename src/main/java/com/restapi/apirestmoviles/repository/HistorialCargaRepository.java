package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.HistorialCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialCargaRepository extends JpaRepository<HistorialCarga, Long> {
    List<HistorialCarga> findByUsuarioId(Long usuarioId);
    List<HistorialCarga> findByEstacionId(Long estacionId);
}
