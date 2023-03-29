package com.pws.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pws.employee.ApiSuccess;
import com.pws.employee.EmployeeApplication;
import com.pws.employee.dto.*;
import com.pws.employee.entity.Skill;
import com.pws.employee.entity.User;
import com.pws.employee.entity.UserRoleXref;
import com.pws.employee.entity.UserSkillXref;
import com.pws.employee.exception.config.PWSException;
import com.pws.employee.repository.SkillRepository;
import com.pws.employee.repository.UserRepository;
import com.pws.employee.service.EmployeeService;
import com.pws.employee.utility.JwtUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmployeeApplication.class)
@SpringBootTest
public class EmployeeControllerTest {

    @Configuration
    public class MockMvcConfig {
        @Autowired
        private WebApplicationContext webApplicationContext;

        @Bean
        public MockMvc mockMvc() {
            return MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
    }

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    ObjectMapper om = new ObjectMapper();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Test
    public void testAddSkillToUser() throws Exception {

        UserSkillXrefDTO userSkillXrefDTO = new UserSkillXrefDTO();
        userSkillXrefDTO.setId(1);
        userSkillXrefDTO.setUserId(5);
        userSkillXrefDTO.setSkillId(1);
        userSkillXrefDTO.setSkilllevel(UserSkillXref.Keyword.valueOf("Intermediate"));
        userSkillXrefDTO.setIsActive(true);

        doNothing().when(employeeService).addSkillToUser(userSkillXrefDTO);

        mockMvc.perform(post("http://localhost:8082/private/skill/add/to/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userSkillXrefDTO)))
                .andExpect(status().isOk());
        System.out.println("Skill added to user successfully");

    }

    @Test
    public void testauthenticateUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("ram123@gmail.com");
        loginDTO.setPassword("ram@1234");

        mockMvc.perform(post("http://localhost:8082/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginDTO)))
                .andExpect(status().isOk());
        System.out.println("Authenticated successfully");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateUserPassword() throws Exception {
        // Create a mock UpdatePasswordDTO
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setUserEmail("ram123@gmail.com");
        updatePasswordDTO.setOldPassword("ram@1234");
        updatePasswordDTO.setNewPassword("ram@123");
        updatePasswordDTO.setConfirmNewPassword("ram@123");

        // Mock the employeeService.updateUserPassword() method
        doNothing().when(employeeService).updateUserPassword(updatePasswordDTO);

        // Send a PUT request to the API endpoint
        mockMvc.perform(put("http://localhost:8082/private/update/user/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatePasswordDTO)))
                .andExpect(status().isOk()); // Check for a 200 OK status code
        System.out.println("password Updated Successfully");
    }

    @Test
    public void testSignUp() throws Exception {
        // Create a new SignUpDTO object
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setFirstName("John");
        signUpDTO.setLastName("Doe");
        signUpDTO.setDateOfBirth("13/11/2000");
        signUpDTO.setEmail("johndoeee@example.com");
        signUpDTO.setPhoneNumber("1234567890");
        signUpDTO.setPassword("password");
        signUpDTO.setRoleName("employee");

        // Mock the employeeService.updateUserPassword() method
        doNothing().when(employeeService).UserSignUp(signUpDTO);

//        // Convert SignUpDTO object to JSON
//        String jsonRequest = objectMapper.writeValueAsString(signUpDTO);

        // Perform POST request and assert response
        MvcResult result = mockMvc.perform(post("http://localhost:8082/public/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(signUpDTO)))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println("SignUp Successfully");
    }

    @Test
    public void testGetUserBasicInfoAfterLoginSuccess() throws Exception {
        // Mock the employeeService.getUserBasicInfoAfterLoginSuccess() method
        UserBasicDetailsDTO mockUserBasicDetailsDTO = new UserBasicDetailsDTO();
        when(employeeService.getUserBasicInfoAfterLoginSuccess("test123@gmail.com")).thenReturn(mockUserBasicDetailsDTO);

        // Send a GET request to the API endpoint
        mockMvc.perform(get("http://localhost:8082/private/userdetails/")
                        .param("email", "test123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Check for a 200 OK status code
        System.out.println("Test Passed");
    }

    @Test
    public void testfetchAllSkills() throws Exception {
        // Send a GET request to the API endpoint
        mockMvc.perform(get("/private/skill/fetchall/5/1/id")
//                        .param("offset", "5")
//                        .param("pageSize", "1")
//                        .param("field", "id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Check for a 200 OK status code
        System.out.println("Test Passed");
    }

    @Test
    public void testfetchAllUserSkills() throws Exception {
        // Send a GET request to the API endpoint
        mockMvc.perform(get("/private/user/fetchall/skill")
                        .param("page", "1")
                        .param("size", "")
                        .param("sort", "id")
                        .param("order", "descending")
                        .param("id", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Check for a 200 OK status code
        System.out.println("Test Passed");
    }

    @Test
    public void testFetchAllSkillsByid() throws Exception {
        // Mock the employeeService.fetchAllSkillsByid() method
        Skill mockSkill = new Skill();
        when(employeeService.fetchAllSkillsByid(1)).thenReturn(Optional.of(mockSkill));

        // Send a GET request to the API endpoint
        mockMvc.perform(get("/private/skill/fetchby/id")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Check for a 200 OK status code
                .andExpect(jsonPath("$.status").value("OK")) // Check for a success status in the response body
                .andExpect(jsonPath("$.data.id").value(1)); // Check that the ID of the returned Skill object is 1
        System.out.println("Test Passed");
    }

    @Test
    public void testFetchAllActiveSkills() throws Exception {
        // Send a GET request to the API endpoint
        mockMvc.perform(get("http://localhost:8082/private/skill/fetch/allactive")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Check for a 200 OK status code
                .andExpect(jsonPath("$.status").value("OK"));// Check for a success status in the response body
        System.out.println("Test Passed");
    }

    @Test
    public void testFetchAllSkillsByFlag() throws Exception {
        // Send a GET request to the API endpoint
        mockMvc.perform(get("/private/skill/fetch/allinactive?flag=false")
//                        .param("flag", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Check for a 200 OK status code
                .andExpect(jsonPath("$.status").value("OK")); // Check for a success status in the response body
        System.out.println("Test Passed");
    }

    @Test
    public void testdeactivateOrActivateAssignedRoleToUser() throws Exception {
        Integer id = 4;
        Boolean flag = false;

        // Mock the employeeService.updateUserPassword() method
        doNothing().when(employeeService).deactivateOrActivateSkillUserXref(id, flag);

        // Send a PUT request to the API endpoint
        mockMvc.perform(put("/private/skill/Activate/deactivate")
                        .param("id", id.toString())
                        .param("flag", flag.toString()))
                .andExpect(status().isOk());
        System.out.println("Test Passed");
    }

    @Test
    public void testsaveOrUpdateskilluserXref() throws Exception {
        // Create a mock UpdatePasswordDTO
        UserSkillXrefDTO userSkillXrefDTO = new UserSkillXrefDTO();
        userSkillXrefDTO.setId(11);
        userSkillXrefDTO.setUserId(5);
        userSkillXrefDTO.setSkilllevel(UserSkillXref.Keyword.valueOf("Intermediate"));
        userSkillXrefDTO.setSkillId(1);
        userSkillXrefDTO.setIsActive(true);

        // Mock the employeeService.updateUserPassword() method
        doNothing().when(employeeService).saveOrUpdateskilluserXref(userSkillXrefDTO);

        // Send a PUT request to the API endpoint
        mockMvc.perform(post("http://localhost:8082/private/skill/saveorupdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userSkillXrefDTO)))
                .andExpect(status().isOk()); // Check for a 200 OK status code
        System.out.println("Test passed");
    }
}

