package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.CalificacionEstacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionEstacionRepository extends JpaRepository<CalificacionEstacion, Long> {
    List<CalificacionEstacion> findByEstacionId(Long estacionId);
}
