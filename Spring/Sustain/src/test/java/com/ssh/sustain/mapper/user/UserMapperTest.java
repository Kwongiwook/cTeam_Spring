package com.ssh.sustain.mapper.user;

import com.ssh.sustain.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void save() {
        User user = User.builder()
                .email("test@naver.com")
                .nickname("test")
                .auth("ROLE_GUEST")
                .build();

        Integer saved = mapper.saveSocial(user);
        assertNotNull(saved);
    }

    @Test
    void findByEmail() {
        User byEmail = mapper.findByEmail("test2@test.com");
        assertEquals(3, (int) byEmail.getId());
    }

    @Test
    void findAll() {
        List<User> userList = mapper.findAll();

        assertFalse(userList.isEmpty());
        userList.forEach(log::info);
    }

    @Test
    void update() {
        // given
        User user = User.builder()
                .email("test@naver.com")
                .nickname("test")
                .auth("ROLE_GUEST")
                .build();

        mapper.saveSocial(user);

        // when
        User update = User.builder()
                .email("test@naver.com")
                .nickname("test2")
                .auth("ROLE_AUTH")
                .build();
        Integer update1 = mapper.update(update);

        // then
        assertNotNull(update1);
    }

    @Test
    void delete() {
        // when
        mapper.delete("test@naver.com");

        // then
        User byEmail = mapper.findByEmail("test@naver.com");
        assertNull(byEmail);
    }

    @Test
    void isExists() {
        User user = User.builder()
                .email("test@naver.com")
                .nickname("test")
                .auth("ROLE_GUEST")
                .build();

        mapper.saveSocial(user);
        Boolean exists = mapper.isExists("test2@naver.com");
        assertFalse(exists);
    }
}