package com.ssh.sustain.service;

import com.ssh.sustain.domain.UserTest;

public interface UserTestService {

    UserTest findByEmail(String email);

    void save(UserTest userTest);

    void update(UserTest userTest);
}
