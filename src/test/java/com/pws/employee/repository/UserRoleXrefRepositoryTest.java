package com.pws.employee.repository;

import com.pws.employee.entity.Role;
import com.pws.employee.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoleXrefRepositoryTest {

    @Autowired
    private UserRoleXrefRepository userRoleXrefRepository;

    @Test
    @DisplayName("Fetch All User Based On RoleId")
    void fetchAllUsersByRoleId() {
        List<User> users = userRoleXrefRepository.fetchAllUsersByRoleId(6);
        assertTrue(!users.isEmpty()); // Check if the list is not empty
    }

    @Test
    void findByRole() {
        Optional<Role> employees = userRoleXrefRepository.findByRole("employee");
        assertTrue(employees.isPresent());
    }

    @Test
    void findAllUserRoleByUserId() {
        List<Role> roles=userRoleXrefRepository.findAllUserRoleByUserId(5);
        assertTrue(!roles.isEmpty());
    }
}
