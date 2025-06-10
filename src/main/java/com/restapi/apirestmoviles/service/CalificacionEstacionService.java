package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.CalificacionEstacion;
import com.restapi.apirestmoviles.model.CalificacionEstacionDto;
import com.restapi.apirestmoviles.model.EstacionCarga;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.CalificacionEstacionRepository;
import com.restapi.apirestmoviles.repository.EstacionCargaRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionEstacionService {
    
    @Autowired
    private CalificacionEstacionRepository calificacionEstacionRepository;

    @Autowired
    private EstacionCargaRepository estacionCargaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;// Convert entity to DTO
    private CalificacionEstacionDto convertToDto(CalificacionEstacion calificacion) {
        return new CalificacionEstacionDto(
                calificacion.getId(),
                calificacion.getUsuario().getId(),
                calificacion.getEstacion().getId(),
                calificacion.getCalificacion(),
                calificacion.getComentario(),
                calificacion.getFecha(),
                calificacion.getUsuario().getNombre()
                );
    }    // Convert DTO to entity
    private CalificacionEstacion convertToEntity(CalificacionEstacionDto calificacionDto) {
        Usuario usuario = usuarioRepository.findById(calificacionDto.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + calificacionDto.usuarioId()));

        EstacionCarga estacion = estacionCargaRepository.findById(calificacionDto.estacionId())
                .orElseThrow(() -> new IllegalArgumentException("Charging station not found with id: " + calificacionDto.estacionId()));

        CalificacionEstacion calificacion = new CalificacionEstacion();
        calificacion.setUsuario(usuario);
        calificacion.setEstacion(estacion);
        calificacion.setCalificacion(calificacionDto.calificacion());
        calificacion.setComentario(calificacionDto.comentario());
        return calificacion;    }

    public CalificacionEstacionDto addCalificacion(CalificacionEstacionDto calificacionDto) {
        CalificacionEstacion calificacion = convertToEntity(calificacionDto);
        CalificacionEstacion savedCalificacion = calificacionEstacionRepository.save(calificacion);
        return convertToDto(savedCalificacion);
    }    public List<CalificacionEstacionDto> getCalificacionesByEstacion(Long estacionId) {
        List<CalificacionEstacion> calificaciones = calificacionEstacionRepository.findByEstacionId(estacionId);
        return calificaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<CalificacionEstacionDto> getCalificacionesByEstacion(Long estacionId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecha"));
        Page<CalificacionEstacion> calificacionesPage = calificacionEstacionRepository.findByEstacionId(estacionId, pageable);
        return calificacionesPage.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void deleteCalificacion(Long id) {
        CalificacionEstacion calificacion = calificacionEstacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
        calificacionEstacionRepository.delete(calificacion);
    }
}
