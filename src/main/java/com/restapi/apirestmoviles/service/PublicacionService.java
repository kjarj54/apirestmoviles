package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.Publicacion;
import com.restapi.apirestmoviles.model.PublicacionDto;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.ComentarioPublicacionRepository;
import com.restapi.apirestmoviles.repository.PublicacionRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComentarioPublicacionRepository comentarioRepository;

    // Convert entity to DTO
    private PublicacionDto convertToDto(Publicacion publicacion) {
        return new PublicacionDto(
                publicacion.getId(),
                publicacion.getUsuario().getId(),
                publicacion.getTema(),
                publicacion.getContenido(),
                publicacion.getFecha()
        );
    }

    // Convert DTO to entity
    private Publicacion convertToEntity(PublicacionDto publicacionDto, Usuario usuario) {
        Publicacion publicacion = new Publicacion();
        publicacion.setUsuario(usuario);
        publicacion.setTema(publicacionDto.tema());
        publicacion.setContenido(publicacionDto.contenido());
        return publicacion;
    }

    // Create a new publication
    public PublicacionDto createPublicacion(PublicacionDto publicacionDto) {
        Usuario usuario = usuarioRepository.findById(publicacionDto.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + publicacionDto.usuarioId()));

        Publicacion publicacion = convertToEntity(publicacionDto, usuario);
        Publicacion savedPublicacion = publicacionRepository.save(publicacion);
        return convertToDto(savedPublicacion);
    }

    // Retrieve all publications
    public List<PublicacionDto> getAllPublicaciones() {
        return publicacionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Retrieve a publication by ID
    public PublicacionDto getPublicacionById(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return convertToDto(publicacion);
    }

    // Delete a publication by ID
    public void deletePublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        // Delete all comments associated with the publication first
        comentarioRepository.deleteAll(comentarioRepository.findByPublicacionId(id));
        
        // Then delete the publication
        publicacionRepository.delete(publicacion);
    }
}
