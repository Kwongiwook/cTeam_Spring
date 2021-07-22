package com.ssh.sustain.mapper.user;

import com.ssh.sustain.model.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    Integer save(User user);

    User findByEmail(String email);

    List<User> findAll();

    Integer update(User user);

    Integer delete(String email);

    Boolean isExists(String email);

}
