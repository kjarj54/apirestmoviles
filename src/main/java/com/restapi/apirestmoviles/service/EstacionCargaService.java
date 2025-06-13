package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.EstacionCarga;
import com.restapi.apirestmoviles.model.EstacionCargaDto;
import com.restapi.apirestmoviles.repository.EstacionCargaRepository;
import com.restapi.apirestmoviles.repository.CalificacionEstacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionCargaService {
    
    @Autowired
    private EstacionCargaRepository estacionCargaRepository;

    @Autowired
    private CalificacionEstacionRepository calificacionEstacionRepository;    // Convert entity to DTO
    private EstacionCargaDto convertToDto(EstacionCarga estacion) {
        double calificacionPromedio = 0.0;
        int cantidadResenas = 0;

        try {
            // Usar consulta directa sin relaciones complejas para obtener calificaciones
            List<com.restapi.apirestmoviles.model.CalificacionEstacion> calificaciones = 
                calificacionEstacionRepository.findByEstacionId(estacion.getId());
            
            if (!calificaciones.isEmpty()) {
                // Calcular promedio manualmente
                double suma = 0.0;
                for (com.restapi.apirestmoviles.model.CalificacionEstacion cal : calificaciones) {
                    suma += cal.getCalificacion();
                }
                calificacionPromedio = suma / calificaciones.size();
                cantidadResenas = calificaciones.size();
            }
            
            // Log para debug (puedes comentar después)
            System.out.println("Estación: " + estacion.getNombre() + " - Rating: " + calificacionPromedio + " - Count: " + cantidadResenas);
            
        } catch (Exception e) {
            System.err.println("Error calculando estadísticas para estación " + estacion.getId() + ": " + e.getMessage());
            calificacionPromedio = 0.0;
            cantidadResenas = 0;
        }
        
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
                estacion.getHorario(),
                calificacionPromedio,
                cantidadResenas
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
