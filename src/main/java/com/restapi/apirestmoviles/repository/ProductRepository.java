package com.restapi.apirestmoviles.repository;

import com.restapi.apirestmoviles.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}