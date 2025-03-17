package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.model.ComentarioPublicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioPublicacionRepository extends JpaRepository<ComentarioPublicacion, Long> {
    List<ComentarioPublicacion> findByPublicacionId(Long publicacionId);
}
