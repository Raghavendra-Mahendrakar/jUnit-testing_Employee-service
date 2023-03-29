package com.pws.employee;


import com.pws.employee.controller.EmployeeController;
import com.pws.employee.dto.SignUpDTO;
import com.pws.employee.exception.config.PWSException;
import com.pws.employee.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpTest {

    @InjectMocks
    private EmployeeController adminController;

    @Mock
    private EmployeeService adminService;

    @Test
    public void testSignup() throws PWSException {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setFirstName("John");
        signUpDTO.setLastName("Doe");
        signUpDTO.setEmail("johndoe@example.com");
        signUpDTO.setPassword("P@ssw0rd");
        signUpDTO.setPhoneNumber("1234567890");
        signUpDTO.setDateOfBirth("12-10-1998");


        // Mock the behavior of the adminService
        doNothing().when(adminService).UserSignUp(signUpDTO);

        // Call the signup method in the adminController
        ResponseEntity<Object> responseEntity = adminController.signup(signUpDTO);

        // Verify that the adminService's UserSignUp method was called once
        verify(adminService, times(1)).UserSignUp(signUpDTO);

        // Verify that the response entity's status code is HttpStatus.OK
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        System.out.println("signed up successfully");
    }

    @Test(expected = PWSException.class)
    public void testSignupWithExistingEmail() throws PWSException {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setFirstName("John");
        signUpDTO.setLastName("Doe");
        signUpDTO.setEmail("johndoe@example.com"); // existing email
        signUpDTO.setPassword("P@ssw0rd");
        signUpDTO.setPhoneNumber("1234567890");
        signUpDTO.setDateOfBirth("12-10-1998");

        doThrow(new PWSException("User Already Exist with Email : " + signUpDTO.getEmail())).when(adminService).UserSignUp(signUpDTO);

        adminController.signup(signUpDTO);
        System.out.println("Test Passed for exiting email");
    }
}