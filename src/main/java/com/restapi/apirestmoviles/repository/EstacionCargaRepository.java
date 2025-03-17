package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.EstacionCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacionCargaRepository extends JpaRepository<EstacionCarga, Long> {
    List<EstacionCarga> findByLatitudBetweenAndLongitudBetween(Double minLat, Double maxLat, Double minLng, Double maxLng);
    boolean existsByNombreAndDireccion(String nombre, String direccion);
}
