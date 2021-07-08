package com.ssh.sustain.service;

import com.ssh.sustain.domain.UserTest;
import com.ssh.sustain.mapper.UserTestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j
public class UserTestServiceImpl implements UserTestService {

    private final UserTestMapper mapper;

    @Override
    public UserTest findByEmail(String email) {
        /*
        org.apache.ibatis.executor.ExecutorException:
        No constructor found in com.ssh.sustain.domain.UserTest matching
        [java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String]

        Role을 String으로 보고 있기 때문에 ResultMap 만들어서 돌려줘야함.
         */
        log.info("findByEmail : " + email);
        return mapper.findByEmail(email);
    }

    @Override
    public void save(UserTest userTest) {
        log.info("UserTest Save");
        mapper.create(userTest);
    }

    @Override
    public void update(UserTest userTest) {
        log.info("UserTest Update");
        mapper.update(userTest);
    }
}
