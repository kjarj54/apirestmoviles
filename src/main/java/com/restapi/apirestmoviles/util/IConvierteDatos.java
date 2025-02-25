package com.restapi.apirestmoviles.util;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
