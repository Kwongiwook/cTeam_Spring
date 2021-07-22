package com.ssh.sustain.service.user;

import com.ssh.sustain.model.user.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findByEmail(String email);

    List<User> findAll();

    User update(User user);

    Boolean delete(String email);

    Boolean isExists(String email);

}
