package com.ssh.sustain.repository;

import com.ssh.sustain.model.user.Verify;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataRedisTest
public class VerifyRepositoryTest {

    @Autowired
    private VerifyRepository verifyRepository;

    @Test
    public void save() {
        Verify verify = new Verify("test@naver.com", "1234", 180L);
        verifyRepository.save(verify);
    }

}