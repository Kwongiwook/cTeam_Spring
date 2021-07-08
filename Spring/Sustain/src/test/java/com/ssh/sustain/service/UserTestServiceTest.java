package com.ssh.sustain.service;

import com.ssh.sustain.config.RootConfig;
import com.ssh.sustain.config.SecurityConfig;
import com.ssh.sustain.config.ServletConfig;
import lombok.extern.log4j.Log4j;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        RootConfig.class, ServletConfig.class, SecurityConfig.class
})
@Log4j
public class UserTestServiceTest {

}
