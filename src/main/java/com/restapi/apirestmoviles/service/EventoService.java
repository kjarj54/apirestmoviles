package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.Evento;
import com.restapi.apirestmoviles.model.EventoDto;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.EventoRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convert entity to DTO
    private EventoDto convertToDto(Evento evento) {
        return new EventoDto(
                evento.getId(),
                evento.getUsuarioCreador().getId(),
                evento.getTitulo(),
                evento.getDescripcion(),
                evento.getFechaEvento(),
                evento.getUbicacion(),
                evento.getEstado());
    }

    private Evento convertToEntity(EventoDto eventoDto) {
        Usuario organizador = usuarioRepository.findById(eventoDto.usuarioCreadorId())
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with id: " + eventoDto.usuarioCreadorId()));

        Evento evento = new Evento();
        evento.setUsuarioCreador(organizador);
        evento.setTitulo(eventoDto.titulo());
        evento.setDescripcion(eventoDto.descripcion());
        evento.setFechaEvento(eventoDto.fechaEvento());
        evento.setUbicacion(eventoDto.ubicacion());
        evento.setEstado(eventoDto.estado());

        return evento;
    }

    public EventoDto createEvent(EventoDto eventoDto) {
        Evento evento = convertToEntity(eventoDto);
        Evento savedEvent = eventoRepository.save(evento);
        return convertToDto(savedEvent);
    }

    public void updatedEvent(Long id, String newStatus) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));

        evento.setEstado(newStatus);
        eventoRepository.save(evento);
    }

    public EventoDto editEvent(Long id, EventoDto updatedData) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));

        evento.setTitulo(updatedData.titulo());
        evento.setDescripcion(updatedData.descripcion());
        evento.setUbicacion(updatedData.ubicacion());
        evento.setFechaEvento(updatedData.fechaEvento());

        return convertToDto(eventoRepository.save(evento));
    }

    public List<EventoDto> getAllEvents() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EventoDto getEventById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        return convertToDto(evento);
    }

    public void deleteEvent(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));

        eventoRepository.delete(evento);
    }
}
