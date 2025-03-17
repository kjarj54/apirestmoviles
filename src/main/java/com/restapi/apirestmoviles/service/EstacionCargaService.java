package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.EstacionCarga;
import com.restapi.apirestmoviles.model.EstacionCargaDto;
import com.restapi.apirestmoviles.repository.EstacionCargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionCargaService {

    @Autowired
    private EstacionCargaRepository estacionCargaRepository;

    // Convert entity to DTO
    private EstacionCargaDto convertToDto(EstacionCarga estacion) {
        return new EstacionCargaDto(
                estacion.getId(),
                estacion.getNombre(),
                estacion.getLatitud() != null ? estacion.getLatitud().doubleValue() : null,
                estacion.getLongitud() != null ? estacion.getLongitud().doubleValue() : null,
                estacion.getDireccion(),
                estacion.getTipoCargador(),
                estacion.getPotencia(),
                estacion.getTarifa() != null ? estacion.getTarifa().doubleValue() : null,
                estacion.getDisponibilidad(),
                estacion.getHorario()
        );
    }

    // Convert DTO to entity
    private EstacionCarga convertToEntity(EstacionCargaDto estacionDto) {
        EstacionCarga estacion = new EstacionCarga();
        estacion.setNombre(estacionDto.nombre());
        estacion.setLatitud(estacionDto.latitud() != null ? BigDecimal.valueOf(estacionDto.latitud()) : null);
        estacion.setLongitud(estacionDto.longitud() != null ? BigDecimal.valueOf(estacionDto.longitud()) : null);
        estacion.setDireccion(estacionDto.direccion());
        estacion.setTipoCargador(estacionDto.tipoCargador());
        estacion.setPotencia(estacionDto.potencia());
        estacion.setTarifa(estacionDto.tarifa() != null ? BigDecimal.valueOf(estacionDto.tarifa()) : null);
        estacion.setDisponibilidad(estacionDto.disponibilidad());
        estacion.setHorario(estacionDto.horario());
        return estacion;
    }

    public List<EstacionCargaDto> getAllStations() {
        List<EstacionCarga> estaciones = estacionCargaRepository.findAll();
        return estaciones.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public EstacionCargaDto getStationById(Long id) {
        EstacionCarga estacion = estacionCargaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charging station not found with id: " + id));
        return convertToDto(estacion);
    }

    public EstacionCargaDto createStation(EstacionCargaDto estacionDto) {
        if (estacionCargaRepository.existsByNombreAndDireccion(estacionDto.nombre(), estacionDto.direccion())) {
            throw new IllegalArgumentException("A charging station with this name and address already exists.");
        }
        EstacionCarga estacion = convertToEntity(estacionDto);
        EstacionCarga savedStation = estacionCargaRepository.save(estacion);
        return convertToDto(savedStation);
    }

    public EstacionCargaDto updateStation(Long id, EstacionCargaDto estacionDto) {
        EstacionCarga estacion = estacionCargaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charging station not found with id: " + id));

        estacion.setNombre(estacionDto.nombre());
        estacion.setLatitud(estacionDto.latitud() != null ? BigDecimal.valueOf(estacionDto.latitud()) : null);
        estacion.setLongitud(estacionDto.longitud() != null ? BigDecimal.valueOf(estacionDto.longitud()) : null);
        estacion.setDireccion(estacionDto.direccion());
        estacion.setTipoCargador(estacionDto.tipoCargador());
        estacion.setPotencia(estacionDto.potencia());
        estacion.setTarifa(estacionDto.tarifa() != null ? BigDecimal.valueOf(estacionDto.tarifa()) : null);
        estacion.setDisponibilidad(estacionDto.disponibilidad());
        estacion.setHorario(estacionDto.horario());

        EstacionCarga updatedStation = estacionCargaRepository.save(estacion);
        return convertToDto(updatedStation);
    }

    public void deleteStation(Long id) {
        EstacionCarga estacion = estacionCargaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Charging station not found with id: " + id));

        estacionCargaRepository.delete(estacion);
    }
}
