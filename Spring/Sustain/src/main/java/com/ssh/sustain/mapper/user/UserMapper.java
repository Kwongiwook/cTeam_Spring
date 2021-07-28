package com.ssh.sustain.mapper.user;

import com.ssh.sustain.dto.user.ClaimUserDTO;
import com.ssh.sustain.dto.user.NormalUserDTO;
import com.ssh.sustain.dto.user.RegisterUserDTO;
import com.ssh.sustain.model.user.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer saveSocial(User user);

    Integer saveNormal(RegisterUserDTO user);

    User findByEmail(String email);

    ClaimUserDTO findClaimUserByEmail(String email);

    User findByPwd(String pwd);

    NormalUserDTO findNormalUserByEmail(String pwd);

    List<User> findAll();

    Integer update(User user);

    Integer updatePwd(User user);

    Integer delete(String email);

    Boolean isExists(String email);

}
