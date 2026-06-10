package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repositorio de User - acceso a datos con Spring Data JPA
 * @author Valenciano
 */
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar usuario por email (necesario para el login)
    Optional<User> findByEmail(String email);

    // Comprobar si ya existe un email
    boolean existsByEmail(String email);
}