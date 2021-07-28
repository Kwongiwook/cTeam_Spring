package com.ssh.sustain.security.auth.user.normal;

import com.ssh.sustain.dto.user.NormalUserDTO;
import com.ssh.sustain.security.Role;
import com.ssh.sustain.service.user.UserService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Assert.notNull(email, "Email is can't be null(Empty)");
        NormalUserDTO normalUserDTO = userService.findNormalUserByEmail(email);

        return normalUserDTO != null ? new CustomUser(normalUserDTO.toMap(),
                Collections.singleton(
                        new SimpleGrantedAuthority(normalUserDTO.getAuth()
                        )
                ))
                :
                null;
    }
}
