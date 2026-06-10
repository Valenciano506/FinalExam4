package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entidad User - cliente o administrador del concesionario
 * @author Valenciano
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    // Campos extra del proyecto personal (concesionario)
    private Float balance;          // presupuesto del cliente
    private Long dni;
    private Integer age;
    private Long tlf;
    private String address;
    private String licenseType;     // tipo de carnet: "B", "A", "B+E"...
    private String nationality;     // nacionalidad del cliente
}