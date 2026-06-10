package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Implementación del servicio de usuarios
 * Contiene la lógica de negocio para gestionar usuarios
 * @author Valenciano
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public User saveUser(User user) {
        // Cifrar la contraseña antes de guardar (BCrypt)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(long id, User updatedUser) {
        User existing = getUser(id);
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setAge(updatedUser.getAge());
        existing.setDni(updatedUser.getDni());
        existing.setTlf(updatedUser.getTlf());
        existing.setAddress(updatedUser.getAddress());
        existing.setBalance(updatedUser.getBalance());
        existing.setRole(updatedUser.getRole());
        // Solo actualizamos contraseña si viene en el body
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}