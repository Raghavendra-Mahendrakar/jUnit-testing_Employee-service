package com.pws.employee.service;

import com.pws.employee.exception.config.PWSException;
import com.pws.employee.repository.SkillRepository;
import com.pws.employee.repository.UserSkillXrefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private UserSkillXrefRepository skillRepository;

    private EmployeeServiceImpl employeeService;

    @Test
    void fetchAllActiveSkills() throws PWSException {
        employeeService.fetchAllActiveSkills();
        verify(skillRepository).fetchAllActiveSkills();
    }

    @BeforeEach
    void setUp() {
        this.employeeService = new EmployeeServiceImpl(this.skillRepository);
    }
}