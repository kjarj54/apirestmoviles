package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.CalificacionEstacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionEstacionRepository extends JpaRepository<CalificacionEstacion, Long> {
    List<CalificacionEstacion> findByEstacionId(Long estacionId);
    
    Page<CalificacionEstacion> findByEstacionId(Long estacionId, Pageable pageable);
    
    // Consulta optimizada para obtener solo estadísticas de calificación sin cargar todas las entidades
    @Query("SELECT AVG(c.calificacion), COUNT(c) FROM CalificacionEstacion c WHERE c.estacion.id = :estacionId")
    Object[] findRatingStatsById(@Param("estacionId") Long estacionId);
}
