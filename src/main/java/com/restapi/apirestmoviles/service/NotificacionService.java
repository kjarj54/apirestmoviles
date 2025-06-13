package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.Notificacion;
import com.restapi.apirestmoviles.model.NotificacionDto;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.NotificacionRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convert entity to DTO
    private NotificacionDto convertToDto(Notificacion notificacion) {
        return new NotificacionDto(
                notificacion.getId(),
                notificacion.getUsuario().getId(),
                notificacion.getMensaje(),
                notificacion.getTipo(),
                notificacion.getLeido(),
                notificacion.getFecha()
        );
    }

    // Convert a list of entities to DTOs
    private List<NotificacionDto> convertToDtoList(List<Notificacion> notificaciones) {
        return notificaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Create a new notification
    public NotificacionDto createNotification(NotificacionDto notificacionDto) {
        Usuario usuario = usuarioRepository.findById(notificacionDto.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + notificacionDto.usuarioId()));

        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setMensaje(notificacionDto.mensaje());
        notificacion.setTipo(notificacionDto.tipo());
        notificacion.setLeido(false);

        Notificacion savedNotificacion = notificacionRepository.save(notificacion);
        return convertToDto(savedNotificacion);
    }

    // Create notifications for all users (for new stations announcements)
    public void createBroadcastNotification(String mensaje, String tipo) {
        List<Usuario> allUsers = usuarioRepository.findAll();
        
        for (Usuario usuario : allUsers) {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(usuario);
            notificacion.setMensaje(mensaje);
            notificacion.setTipo(tipo);
            notificacion.setLeido(false);
            
            notificacionRepository.save(notificacion);
        }
    }

    // Get notifications by user
    public List<NotificacionDto> getNotificationsByUser(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + usuarioId);
        }

        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioId(usuarioId);
        return convertToDtoList(notificaciones);
    }

    // Mark a notification as read
    public NotificacionDto markAsRead(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found with id: " + id));

        notificacion.setLeido(true);
        Notificacion updatedNotificacion = notificacionRepository.save(notificacion);
        return convertToDto(updatedNotificacion);
    }
}
