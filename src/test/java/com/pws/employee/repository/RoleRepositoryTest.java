package com.pws.employee.repository;

import com.pws.employee.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByRolename() {
        Optional<Role> admin = roleRepository.findByRolename("admin");
        assertTrue(admin.isPresent());
    }
}