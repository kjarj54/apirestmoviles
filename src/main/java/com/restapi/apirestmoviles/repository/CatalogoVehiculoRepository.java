package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.CatalogoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoVehiculoRepository extends JpaRepository<CatalogoVehiculo, Long> {
    boolean existsByMarcaAndModelo(String marca, String modelo);
}
