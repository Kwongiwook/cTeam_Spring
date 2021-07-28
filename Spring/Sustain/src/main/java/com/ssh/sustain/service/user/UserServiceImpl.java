package com.ssh.sustain.service.user;

import com.ssh.sustain.config.SecurityConfig;
import com.ssh.sustain.dto.user.ClaimUserDTO;
import com.ssh.sustain.dto.user.NormalUserDTO;
import com.ssh.sustain.dto.user.RegisterUserDTO;
import com.ssh.sustain.mapper.user.UserMapper;
import com.ssh.sustain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public User saveSocial(User user) {
        return mapper.saveSocial(user).equals(1) ? user : null;
    }

    @Override
    public RegisterUserDTO saveNormal(RegisterUserDTO user) {
        user.setPwd(encoder.encode(user.getPwd()));
        return mapper.saveNormal(user).equals(1) ? user : null;
    }

    @Override
    public User findByEmail(String email) {
        return mapper.findByEmail(email);
    }

    @Override
    public ClaimUserDTO findClaimUserByEmail(String email) {
        return mapper.findClaimUserByEmail(email);
    }

    @Override
    public NormalUserDTO findNormalUserByEmail(String email) {
        return mapper.findNormalUserByEmail(email);
    }

    @Override
    public User findByPwd(String pwd) {
        return mapper.findByPwd(pwd);
    }

    @Override
    public List<User> findAll() {
        return mapper.findAll();
    }

    @Override
    public User update(User user) {
        return mapper.update(user).equals(1) ? user : null;
    }

    @Override
    public User updatePwd(User user) {
        return mapper.updatePwd(user).equals(1) ? user : null;
    }

    @Override
    public Boolean delete(String email) {
        return mapper.delete(email).equals(1);
    }

    @Override
    public Boolean isExists(String email) {
        return mapper.isExists(email);
    }

}