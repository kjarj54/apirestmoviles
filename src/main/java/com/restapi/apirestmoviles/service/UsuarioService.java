package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.model.UsuarioDto;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UsuarioDto convertToDto(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                null, // We don't send back the password
                usuario.getVehiculo() != null ? usuario.getVehiculo().getId() : null
        );
    }

    // Convert List of Usuario to List of UsuarioDto
    private List<UsuarioDto> convertToDtoList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Convert UsuarioDto to Usuario (for creation)
    private Usuario convertToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDto.nombre());
        usuario.setCorreo(usuarioDto.correo());
        usuario.setContrasena(passwordEncoder.encode(usuarioDto.contrasena()));
        return usuario;
    }

    // Updated service methods using conversion
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.existsByCorreo(usuarioDto.correo())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        Usuario usuario = convertToEntity(usuarioDto);
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDto(savedUsuario);
    }

    public List<UsuarioDto> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return convertToDtoList(usuarios);
    }

    public UsuarioDto getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        return convertToDto(usuario);
    }

    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        usuario.setNombre(usuarioDto.nombre());
        if (!usuario.getCorreo().equals(usuarioDto.correo()) &&
                usuarioRepository.existsByCorreo(usuarioDto.correo())) {
            throw new IllegalArgumentException("Email is already registered");
        }
        usuario.setCorreo(usuarioDto.correo());

        if (usuarioDto.contrasena() != null && !usuarioDto.contrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDto.contrasena()));
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return convertToDto(updatedUsuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        usuarioRepository.delete(usuario);
    }

    public UsuarioDto getUsuarioByCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + correo));
        return convertToDto(usuario);
    }
}
