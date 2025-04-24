package com.restapi.apirestmoviles.service;

import com.restapi.apirestmoviles.model.CatalogoVehiculo;
import com.restapi.apirestmoviles.model.CatalogoVehiculoDto;
import com.restapi.apirestmoviles.repository.CatalogoVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogoVehiculoService {

    @Autowired
    private CatalogoVehiculoRepository catalogoVehiculoRepository;

    // Convert entity to DTO
    private CatalogoVehiculoDto convertToDto(CatalogoVehiculo vehicle) {
        return new CatalogoVehiculoDto(
                vehicle.getId(),
                vehicle.getMarca(),
                vehicle.getModelo(),
                vehicle.getAutonomia());
    }

    // Convert DTO to entity
    private CatalogoVehiculo convertToEntity(CatalogoVehiculoDto vehicleDto) {
        CatalogoVehiculo vehicle = new CatalogoVehiculo();
        vehicle.setMarca(vehicleDto.marca());
        vehicle.setModelo(vehicleDto.modelo());
        vehicle.setAutonomia(vehicleDto.autonomia());
        return vehicle;
    }

    // Convert a list of entities to DTOs
    private List<CatalogoVehiculoDto> convertToDtoList(List<CatalogoVehiculo> vehicles) {
        return vehicles.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CatalogoVehiculoDto> getAllVehicles() {
        List<CatalogoVehiculo> vehicles = catalogoVehiculoRepository.findAll();
        return convertToDtoList(vehicles);
    }

    public CatalogoVehiculoDto getVehicleById(Long id) {
        CatalogoVehiculo vehicle = catalogoVehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));
        return convertToDto(vehicle);
    }

    public CatalogoVehiculo getById(Long id) {
        return catalogoVehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));
    }

    public CatalogoVehiculoDto createVehicle(CatalogoVehiculoDto vehicleDto) {
        if (catalogoVehiculoRepository.existsByMarcaAndModelo(vehicleDto.marca(), vehicleDto.modelo())) {
            throw new IllegalArgumentException("Vehicle with brand " + vehicleDto.marca() + " and model "
                    + vehicleDto.modelo() + " already exists.");
        }
        CatalogoVehiculo vehicle = convertToEntity(vehicleDto);
        CatalogoVehiculo savedVehicle = catalogoVehiculoRepository.save(vehicle);
        return convertToDto(savedVehicle);
    }

    public CatalogoVehiculoDto updateVehicle(Long id, CatalogoVehiculoDto vehicleDto) {
        CatalogoVehiculo vehicle = catalogoVehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));

        vehicle.setMarca(vehicleDto.marca());
        vehicle.setModelo(vehicleDto.modelo());
        vehicle.setAutonomia(vehicleDto.autonomia());

        CatalogoVehiculo updatedVehicle = catalogoVehiculoRepository.save(vehicle);
        return convertToDto(updatedVehicle);
    }

    public void deleteVehicle(Long id) {
        CatalogoVehiculo vehicle = catalogoVehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));

        catalogoVehiculoRepository.delete(vehicle);
    }
}
