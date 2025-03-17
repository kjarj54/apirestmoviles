package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.CalificacionEstacion;
import com.restapi.apirestmoviles.model.CalificacionEstacionDto;
import com.restapi.apirestmoviles.model.EstacionCarga;
import com.restapi.apirestmoviles.repository.CalificacionEstacionRepository;
import com.restapi.apirestmoviles.repository.EstacionCargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionEstacionService {

    @Autowired
    private CalificacionEstacionRepository calificacionEstacionRepository;

    @Autowired
    private EstacionCargaRepository estacionCargaRepository;

    // Convert entity to DTO
    private CalificacionEstacionDto convertToDto(CalificacionEstacion calificacion) {
        return new CalificacionEstacionDto(
                calificacion.getId(),
                calificacion.getUsuario().getId(),
                calificacion.getEstacion().getId(),
                calificacion.getCalificacion(),
                calificacion.getFecha()
                );
    }

    // Convert DTO to entity
    private CalificacionEstacion convertToEntity(CalificacionEstacionDto calificacionDto) {
        EstacionCarga estacion = estacionCargaRepository.findById(calificacionDto.estacionId())
                .orElseThrow(() -> new IllegalArgumentException("Charging station not found with id: " + calificacionDto.estacionId()));

        CalificacionEstacion calificacion = new CalificacionEstacion();
        calificacion.setEstacion(estacion);
        calificacion.setCalificacion(calificacionDto.calificacion());
        return calificacion;
    }

    public CalificacionEstacionDto addCalificacion(CalificacionEstacionDto calificacionDto) {
        CalificacionEstacion calificacion = convertToEntity(calificacionDto);
        CalificacionEstacion savedCalificacion = calificacionEstacionRepository.save(calificacion);
        return convertToDto(savedCalificacion);
    }

    public List<CalificacionEstacionDto> getCalificacionesByEstacion(Long estacionId) {
        List<CalificacionEstacion> calificaciones = calificacionEstacionRepository.findByEstacionId(estacionId);
        return calificaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void deleteCalificacion(Long id) {
        CalificacionEstacion calificacion = calificacionEstacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
        calificacionEstacionRepository.delete(calificacion);
    }
}
