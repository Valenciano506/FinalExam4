package com.example.demo.repository;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repositorio de Product - acceso a datos con Spring Data JPA
 * @author Valenciano
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Sin queries personalizadas - el filtrado se hace en el servicio
}