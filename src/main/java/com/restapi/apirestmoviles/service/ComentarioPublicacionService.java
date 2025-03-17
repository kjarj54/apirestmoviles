package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.ComentarioPublicacion;
import com.restapi.apirestmoviles.model.ComentarioPublicacionDto;
import com.restapi.apirestmoviles.model.Publicacion;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.ComentarioPublicacionRepository;
import com.restapi.apirestmoviles.repository.PublicacionRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioPublicacionService {

    @Autowired
    private ComentarioPublicacionRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Convert entity to DTO
    private ComentarioPublicacionDto convertToDto(ComentarioPublicacion comentario) {
        return new ComentarioPublicacionDto(
                comentario.getId(),
                comentario.getUsuario().getId(),
                comentario.getPublicacion().getId(),
                comentario.getContenido(),
                comentario.getFecha()
        );
    }

    // Convert DTO to entity
    private ComentarioPublicacion convertToEntity(ComentarioPublicacionDto comentarioDto) {
        Usuario usuario = usuarioRepository.findById(comentarioDto.usuarioId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + comentarioDto.usuarioId()));

        Publicacion publicacion = publicacionRepository.findById(comentarioDto.publicacionId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + comentarioDto.publicacionId()));

        ComentarioPublicacion comentario = new ComentarioPublicacion();
        comentario.setUsuario(usuario);
        comentario.setPublicacion(publicacion);
        comentario.setContenido(comentarioDto.contenido());

        return comentario;
    }

    public ComentarioPublicacionDto addComment(ComentarioPublicacionDto comentarioDto) {
        ComentarioPublicacion comentario = convertToEntity(comentarioDto);
        ComentarioPublicacion savedComment = comentarioRepository.save(comentario);
        return convertToDto(savedComment);
    }

    public List<ComentarioPublicacionDto> getCommentsByPost(Long publicacionId) {
        List<ComentarioPublicacion> comentarios = comentarioRepository.findByPublicacionId(publicacionId);
        return comentarios.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void deleteComment(Long id) {
        ComentarioPublicacion comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + id));

        comentarioRepository.delete(comentario);
    }
}
