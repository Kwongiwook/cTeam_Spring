package com.ssh.sustain.mapper;

import com.ssh.sustain.config.RootConfig;
import com.ssh.sustain.config.SecurityConfig;
import com.ssh.sustain.config.ServletConfig;
import com.ssh.sustain.domain.Role;
import com.ssh.sustain.domain.UserTest;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        RootConfig.class, ServletConfig.class, SecurityConfig.class
})
@Log4j
public class UserTestMapperTest {

    @Setter(onMethod_ = {@Autowired})
    private UserTestMapper mapper;

    @Test
    public void testClassExists() {
        log.info(mapper.getClass().toString());
    }

    @Test
    public void testCreate() {
        UserTest userTest = UserTest.builder()
                .name("test0")
                .picture("test0")
                .email("test0")
                .role(Role.USER).build();

        mapper.create(userTest);
    }

    @Test
    public void testRead() {
        /*
        org.mybatis.spring.MyBatisSystemException
        : nested exception is org.apache.ibatis.executor.ExecutorException
        : No constructor found in com.ssh.sustain.domain.UserTest
         matching [java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String]

         어제 발생했던 Exception과 동일. 알맞은 constructor가 없기 때문에 예외가 발생한다. 빌터 패턴은 딱히 상관 없음.
         */
        List<UserTest> userTests = mapper.read();
        userTests.forEach(log::info);
    }

    @Test
    public void testReadByEmail() {

    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testUpdatePicture() {

    }

    @Test
    public void testUpdateRole() {

    }

    @Test
    public void testDelete() {

    }

}
