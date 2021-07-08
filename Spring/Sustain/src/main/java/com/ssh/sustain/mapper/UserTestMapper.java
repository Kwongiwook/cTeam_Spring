package com.ssh.sustain.mapper;

import com.ssh.sustain.domain.Role;
import com.ssh.sustain.domain.UserTest;

import java.util.List;

public interface UserTestMapper {

    void create(UserTest userTest);

    List<UserTest> read();

    UserTest findByEmail(String email);

    void update(UserTest userTest);

    void updatePicture(String email, String picture);

    void updateRole(String email, Role role);

    void delete(String email);

}
