package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.Evento;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.model.UsuarioEvento;
import com.restapi.apirestmoviles.model.UsuarioEventoDto;
import com.restapi.apirestmoviles.repository.EventoRepository;
import com.restapi.apirestmoviles.repository.UsuarioEventoRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioEventoService {

    @Autowired
    private UsuarioEventoRepository usuarioEventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    // Convert entity to DTO
    private UsuarioEventoDto convertToDto(UsuarioEvento usuarioEvento) {
        return new UsuarioEventoDto(
                usuarioEvento.getId(),
                usuarioEvento.getEvento().getId(),
                usuarioEvento.getUsuario().getId(),
                usuarioEvento.getFechaParticipacion()
        );
    }

    // Register user for an event
    public UsuarioEventoDto registerForEvent(Long eventoId, Long usuarioId) {
        if (usuarioEventoRepository.existsByEventoIdAndUsuarioId(eventoId, usuarioId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already registered for this event");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + usuarioId));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + eventoId));

        UsuarioEvento usuarioEvento = new UsuarioEvento();
        usuarioEvento.setUsuario(usuario);
        usuarioEvento.setEvento(evento);

        UsuarioEvento savedUsuarioEvento = usuarioEventoRepository.save(usuarioEvento);
        return convertToDto(savedUsuarioEvento);
    }

    // Get participants by event
    public List<UsuarioEventoDto> getParticipantsByEvent(Long eventoId) {
        if (!eventoRepository.existsById(eventoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found with id: " + eventoId);
        }

        List<UsuarioEvento> participantes = usuarioEventoRepository.findByEventoId(eventoId);
        return participantes.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Cancel participation
    public void cancelParticipation(Long id) {
        UsuarioEvento usuarioEvento = usuarioEventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participation not found with id: " + id));

        usuarioEventoRepository.delete(usuarioEvento);
    }
}
