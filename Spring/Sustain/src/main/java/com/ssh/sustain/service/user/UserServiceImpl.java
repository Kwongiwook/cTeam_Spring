package com.ssh.sustain.service.user;

import com.ssh.sustain.mapper.user.UserMapper;
import com.ssh.sustain.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public User save(User user) {
        if (mapper.save(user).equals(1)) {
            return user;
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return mapper.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return mapper.findAll();
    }

    @Override
    public User update(User user) {
        if (mapper.update(user).equals(1)) {
            return user;
        }
        return null;
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
