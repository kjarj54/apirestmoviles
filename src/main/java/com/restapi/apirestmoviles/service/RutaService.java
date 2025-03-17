package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.Ruta;
import com.restapi.apirestmoviles.model.RutaDto;
import com.restapi.apirestmoviles.model.Usuario;
import com.restapi.apirestmoviles.repository.RutaRepository;
import com.restapi.apirestmoviles.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convert entity to DTO
    private RutaDto convertToDto(Ruta ruta) {
        return new RutaDto(
                ruta.getId(),
                ruta.getUsuario().getId(),
                ruta.getOrigen(),
                ruta.getDestino(),
                ruta.getDistanciaKm(),
                ruta.getTiempoEstimadoMin(),
                ruta.getEstacionesIntermedias(),
                ruta.getCostoEstimado(),
                ruta.getEmisionesAhorradas(),
                ruta.getFechaPlanificacion(),
                ruta.getFechaCompletada(),
                ruta.getEstado()
        );
    }

    // Convert a list of entities to DTOs
    private List<RutaDto> convertToDtoList(List<Ruta> rutas) {
        return rutas.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Create a new route
    public RutaDto planRoute(RutaDto rutaDto) {
        Usuario usuario = usuarioRepository.findById(rutaDto.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + rutaDto.usuarioId()));

        Ruta ruta = new Ruta();
        ruta.setUsuario(usuario);
        ruta.setOrigen(rutaDto.origen());
        ruta.setDestino(rutaDto.destino());
        ruta.setDistanciaKm(rutaDto.distanciaKm());
        ruta.setTiempoEstimadoMin(rutaDto.tiempoEstimadoMin());
        ruta.setEstacionesIntermedias(rutaDto.estacionesIntermedias());
        ruta.setCostoEstimado(rutaDto.costoEstimado());
        ruta.setEmisionesAhorradas(rutaDto.emisionesAhorradas());
        ruta.setEstado("Planned");

        Ruta savedRuta = rutaRepository.save(ruta);
        return convertToDto(savedRuta);
    }

    // Get routes planned by a user
    public List<RutaDto> getRoutesByUser(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + usuarioId);
        }

        List<Ruta> rutas = rutaRepository.findByUsuarioId(usuarioId);
        return convertToDtoList(rutas);
    }

    // Get details of a route
    public RutaDto getRouteById(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + id));

        return convertToDto(ruta);
    }

    // Delete a planned route
    public void deleteRoute(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found with id: " + id));

        rutaRepository.delete(ruta);
    }
}
