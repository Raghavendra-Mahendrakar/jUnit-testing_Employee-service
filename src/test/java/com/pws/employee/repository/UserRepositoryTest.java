package com.pws.employee.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pws.employee.entity.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    PasswordEncoder encoder = new BCryptPasswordEncoder(8);

    @Test
    public void testFindUserByEmail() {
        // Given
        User user = new User();
//        user.setId(2);
        user.setFirstName("R");
        user.setLastName("M");
        user.setDateOfBirth(new Date(1990, 1, 1));
        user.setEmail("demoEmail@1245.com");
        user.setPhoneNumber("9119119119");
        user.setPassword(encoder.encode("test@1234"));
        user.setIsActive(true);
//        userRepository.save(user);

        // When
        Optional<User> result = userRepository.findUserByEmail("demoEmail@124.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals("demoEmail@124.com", result.get().getEmail());
//        boolean matches = encoder.matches("test@1234", user.getPassword());
//        System.out.println(matches);
//        assertEquals("test@1234", result.get().getPassword());
    }

    @BeforeEach
    void setUp() {
        System.out.println("called setup");
    }

//    @AfterEach
//    void tearDown() {
//        System.out.println("TearDown called");
//        userRepository.deleteById(21);
//    }
}
