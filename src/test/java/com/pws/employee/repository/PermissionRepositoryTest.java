package com.pws.employee.repository;

import com.pws.employee.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PermissionRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    void getAllUserPermisonsByRoleId() {
        List<Permission> allUserPermisonsByRoleId = permissionRepository.getAllUserPermisonsByRoleId(4);
        assertTrue(!allUserPermisonsByRoleId.isEmpty());
    }
}