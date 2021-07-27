package com.ssh.sustain.service.user;

import com.ssh.sustain.model.user.User;

import java.util.List;

public interface UserService {

    User saveSocial(User user);

    User saveNormal(User user);

    User findByEmail(String email);

    User findByPwd(String pwd);

    List<User> findAll();

    User update(User user);

    User updatePwd(User user);

    Boolean delete(String email);

    Boolean isExists(String email);

}
