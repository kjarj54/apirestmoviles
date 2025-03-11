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
        // Note: vehiculo should be set separately
        return usuario;
    }

    // Updated service methods using conversion
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        if (usuarioRepository.existsByCorreo(usuarioDto.correo())) {
            throw new RuntimeException("El correo ya está registrado");
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
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return convertToDto(usuario);
    }

    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombre(usuarioDto.nombre());
        if (!usuario.getCorreo().equals(usuarioDto.correo()) &&
                usuarioRepository.existsByCorreo(usuarioDto.correo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        usuario.setCorreo(usuarioDto.correo());

        if (usuarioDto.contrasena() != null && !usuarioDto.contrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioDto.contrasena()));
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return convertToDto(updatedUsuario);
    }

}
