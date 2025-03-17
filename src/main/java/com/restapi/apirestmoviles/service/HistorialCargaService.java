package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.*;
import com.restapi.apirestmoviles.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialCargaService {

    @Autowired
    private HistorialCargaRepository historialCargaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstacionCargaRepository estacionCargaRepository;

    // Convert entity to DTO
    private HistorialCargaDto convertToDto(HistorialCarga historialCarga) {
        return new HistorialCargaDto(
                historialCarga.getId(),
                historialCarga.getUsuario().getId(),
                historialCarga.getEstacion().getId(),
                historialCarga.getEnergiaConsumida(),
                historialCarga.getCostoTotal(),
                historialCarga.getFecha()
        );
    }

    // Convert a list of entities to DTOs
    private List<HistorialCargaDto> convertToDtoList(List<HistorialCarga> historialCargas) {
        return historialCargas.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Register a new charge
    public HistorialCargaDto registerCharge(HistorialCargaDto historialCargaDto) {
        Usuario usuario = usuarioRepository.findById(historialCargaDto.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + historialCargaDto.usuarioId()));

        EstacionCarga estacion = estacionCargaRepository.findById(historialCargaDto.estacionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Charging station not found with id: " + historialCargaDto.estacionId()));

        HistorialCarga historialCarga = new HistorialCarga();
        historialCarga.setUsuario(usuario);
        historialCarga.setEstacion(estacion);
        historialCarga.setEnergiaConsumida(historialCargaDto.energiaConsumida());
        historialCarga.setCostoTotal(historialCargaDto.costoTotal());

        HistorialCarga savedHistorialCarga = historialCargaRepository.save(historialCarga);
        return convertToDto(savedHistorialCarga);
    }

    // Get charge history by user
    public List<HistorialCargaDto> getChargeHistoryByUser(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + usuarioId);
        }

        List<HistorialCarga> historialCargas = historialCargaRepository.findByUsuarioId(usuarioId);
        return convertToDtoList(historialCargas);
    }

    // Get charge history by station
    public List<HistorialCargaDto> getChargeHistoryByStation(Long estacionId) {
        if (!estacionCargaRepository.existsById(estacionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Charging station not found with id: " + estacionId);
        }

        List<HistorialCarga> historialCargas = historialCargaRepository.findByEstacionId(estacionId);
        return convertToDtoList(historialCargas);
    }
}
