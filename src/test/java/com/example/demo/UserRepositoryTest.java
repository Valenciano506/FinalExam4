package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        user = new User();
        user.setName("Carlos Valenciano");
        user.setEmail("carlos@example.com");
    }

    @Test
    void shouldSaveUser() {
        User saved = userRepository.save(user);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Carlos Valenciano");
    }

    @Test
    void shouldFindUserById() {
        User saved = userRepository.save(user);

        Optional<User> found = userRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("carlos@example.com");
    }

    @Test
    void shouldFindAllUsers() {
        userRepository.save(user);

        User otro = new User();
        otro.setName("Ana García");
        otro.setEmail("ana@example.com");
        userRepository.save(otro);

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
    }

    @Test
    void shouldDeleteUser() {
        User saved = userRepository.save(user);

        userRepository.deleteById(saved.getId());

        Optional<User> deleted = userRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    void shouldUpdateUser() {
        User saved = userRepository.save(user);

        saved.setName("Carlos Actualizado");
        userRepository.save(saved);

        Optional<User> updated = userRepository.findById(saved.getId());
        assertThat(updated.get().getName()).isEqualTo("Carlos Actualizado");
    }
}