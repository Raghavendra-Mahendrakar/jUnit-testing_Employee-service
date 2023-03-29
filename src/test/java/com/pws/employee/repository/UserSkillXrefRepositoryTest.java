package com.pws.employee.repository;

import com.pws.employee.dto.UserSkillXrefDTO;
import com.pws.employee.entity.Skill;
import com.pws.employee.entity.User;
import com.pws.employee.entity.UserSkillXref;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static com.pws.employee.entity.UserSkillXref.Keyword.Intermediate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserSkillXrefRepositoryTest {

    @Autowired
    private UserSkillXrefRepository userSkillXrefRepository;

    @Test
    void fetchAllActiveSkills() {
        List<Skill> skills = userSkillXrefRepository.fetchAllActiveSkills();
        assertTrue(skills.isEmpty());

    }

    @Test
    void fetchAllSkillsByFlag() {
        UserSkillXref userSkillXref = new UserSkillXref();
        User user = new User(5, "R", "K", new Date(1999, 01, 01), "skill@123gmail.com", "9119119119", "skill@123", true);
        Skill skill = new Skill(1, "python", true);
        userSkillXref.setId(1);
        userSkillXref.setUser(user);
        userSkillXref.setSkill(skill);
        userSkillXref.setProficiencyevel(UserSkillXref.Keyword.Intermediate);
        userSkillXref.setIsActive(true);
        userSkillXrefRepository.save(userSkillXref);
        List<Skill> skills = userSkillXrefRepository.fetchAllSkillsByFlag(true);
        assertTrue(!skills.isEmpty());
    }

    @Test
    void fetchuserSkillsByid() {
        List<Skill> skills = userSkillXrefRepository.fetchuserSkillsByid(5);
        assertTrue(!skills.isEmpty());
    }
}