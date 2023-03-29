package com.pws.employee;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pws.employee.controller.EmployeeController;
import com.pws.employee.dto.LoginDTO;
import com.pws.employee.utility.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private EmployeeController authController;

    private MockMvc mockMvc;

    @Test
    public void testGenerateToken_ValidCredentials() throws Exception {
        final String username = "testuser";
        final String password = "testpassword";
        final String token = "testtoken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, password));
        when(jwtUtil.generateToken(username)).thenReturn(token);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(username);
        loginDTO.setPassword(password);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(loginDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(post("http://localhost:8082/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}