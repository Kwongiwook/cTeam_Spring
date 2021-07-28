package com.ssh.sustain.service.user;

import com.ssh.sustain.dto.user.ClaimUserDTO;
import com.ssh.sustain.dto.user.NormalUserDTO;
import com.ssh.sustain.dto.user.RegisterUserDTO;
import com.ssh.sustain.model.user.User;

import java.util.List;

public interface UserService {

    User saveSocial(User user);

    RegisterUserDTO saveNormal(RegisterUserDTO user);

    User findByEmail(String email);

    ClaimUserDTO findClaimUserByEmail(String email);

    NormalUserDTO findNormalUserByEmail(String email);

    User findByPwd(String pwd);

    List<User> findAll();

    User update(User user);

    User updatePwd(User user);

    Boolean delete(String email);

    Boolean isExists(String email);

}
