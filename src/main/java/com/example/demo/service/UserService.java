package com.example.demo.service;

import com.example.demo.domain.User;
import java.util.List;

/*
 * Interfaz del servicio de usuarios - define las operaciones disponibles
 * @author Valenciano
 */
public interface UserService {

    List<User> getAllUsers();

    User getUser(long id);

    User saveUser(User user);

    User updateUser(long id, User user);

    void deleteUser(long id);
}