package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByUsuarioId(Long usuarioId);
}
