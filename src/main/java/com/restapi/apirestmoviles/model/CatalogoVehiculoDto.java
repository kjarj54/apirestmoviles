package com.restapi.apirestmoviles.model;

public record CatalogoVehiculoDto(Long id,
                                  String marca,
                                  String modelo,
                                  Integer autonomia) {
}
